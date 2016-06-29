/**
 * Copyright (c) 2015, Intel Corporation
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Intel Corporation nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.intel.stl.fecdriver.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;

import com.intel.stl.api.ICertsAssistant;
import com.intel.stl.api.ISecurityHandler;
import com.intel.stl.api.StringUtils;
import com.intel.stl.api.subnet.HostInfo;
import com.intel.stl.api.subnet.SubnetDescription;

public class STLSecureConnection extends STLConnection {
    private static boolean DEBUG = false;

    public static final String[] CIPHER_LIST =
            { "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256" };

    public static final int MAX_TRIES = 5;

    public static final long TIME_OUT = 30000; // 30 sec

    private final ICertsAssistant certsAssistant;

    private SSLEngine sslEngine = null;

    private int netBufferSize;

    private ByteBuffer myNetData;

    private ByteBuffer peerNetData;

    private int appBufferSize = 4;

    private ByteBuffer peerAppData;

    private ByteBuffer handshakeAppData;

    private long expired;

    private ByteBuffer myAppData = ByteBuffer.allocate(appBufferSize);

    private HandshakeStatus handshakeStatus = HandshakeStatus.NOT_HANDSHAKING;

    /**
     * Description:
     * 
     * @param description
     * @param password
     * @param info
     * @throws IOException
     */
    protected STLSecureConnection(SubnetDescription description,
            Properties info, ISecurityHandler securityHandler,
            ICertsAssistant assistant) {
        super(description, securityHandler, null);
        certsAssistant = assistant;
    }

    @Override
    protected synchronized void prepareForHandshake() throws Exception {
        try {
            setupForHandshake();
            super.prepareForHandshake();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SSLHandshakeException) {
                SSLHandshakeException sslhe = (SSLHandshakeException) e;
                throw sslhe;
            } else {
                SSLHandshakeException sslhe =
                        new SSLHandshakeException(
                                StringUtils.getErrorMessage(e));
                sslhe.initCause(e);
                throw sslhe;
            }
        }
    }

    @Override
    protected synchronized void beginHandshake() throws Exception {
        try {
            // Start handshake process
            startHandshakeProcess();
            super.beginHandshake();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SSLHandshakeException) {
                SSLHandshakeException sslhe = (SSLHandshakeException) e;
                throw sslhe;
            } else {
                SSLHandshakeException sslhe =
                        new SSLHandshakeException(
                                StringUtils.getErrorMessage(e));
                sslhe.initCause(e);
                throw sslhe;
            }
        }
        // Then do user validation
        super.beginHandshake();
    }

    private void setupForHandshake() throws Exception {
        // init SSLEngine
        // sslEngine = certsAssistant.getSSLEngine(description);
        sslEngine = createSSLEngine();
        netBufferSize = sslEngine.getSession().getPacketBufferSize();
        myNetData = ByteBuffer.allocate(netBufferSize);
        peerNetData = ByteBuffer.allocate(netBufferSize);
        peerNetData.position(0);
        peerNetData.limit(0);

        // Create byte buffers to use for holding handshake data
        appBufferSize = sslEngine.getSession().getApplicationBufferSize();
        myAppData = ByteBuffer.allocate(appBufferSize);
        handshakeAppData = ByteBuffer.allocate(appBufferSize);

    }

    protected SSLEngine createSSLEngine() throws Exception {
        // HostInfo in the SubnetDescription may change during fail over, so it
        // must be retrieved from the description.
        final HostInfo hostInfo = description.getCurrentFE();
        String host = hostInfo.getHost();
        int port = hostInfo.getPort();
        System.setProperty("javax.net.debug", "all");
        KeyManagerFactory kmf =
                certsAssistant.getKeyManagerFactory(description);
        TrustManagerFactory tmf =
                certsAssistant.getTrustManagerFactory(description);

        if (kmf == null || tmf == null) {
            Exception e =
                    new SSLHandshakeException("Couldn't create SSLEngine");
            throw e;
        }

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
                new SecureRandom());
        SSLEngine engine = context.createSSLEngine(host, port);
        engine.setUseClientMode(true);
        return engine;
    }

    private void startHandshakeProcess() throws Exception {
        // Begin handshake
        sslEngine.setEnabledCipherSuites(CIPHER_LIST);
        sslEngine.beginHandshake();
        handshakeStatus = sslEngine.getHandshakeStatus();
        expired = System.currentTimeMillis() + TIME_OUT;
    }

    // @Override
    protected void processHandshake() throws Exception {
        // Process handshaking message
        System.out.println("handshakeStatus: " + handshakeStatus.toString());
        switch (handshakeStatus) {
            case NEED_UNWRAP:
                // Receive handshaking data from peer
                int ret = socketChannel.read(peerNetData);
                if (ret < 0) {
                    // The channel has reached end-of-stream
                }

                if (handshakeAppData.remaining() < appBufferSize) {
                    handshakeAppData =
                            resizeBuffer(handshakeAppData, appBufferSize);
                }
                // Process incoming handshaking data
                peerNetData.flip();
                SSLEngineResult res =
                        sslEngine.unwrap(peerNetData, handshakeAppData);
                peerNetData.compact();
                handshakeStatus = res.getHandshakeStatus();
                // Check status
                switch (res.getStatus()) {
                    case OK:
                        // Handle OK status
                        break;

                    case BUFFER_UNDERFLOW:
                        // Resize buffer if needed.
                        netBufferSize =
                                sslEngine.getSession().getPacketBufferSize();
                        if (netBufferSize > peerNetData.capacity()) {
                            peerNetData =
                                    resizeBuffer(peerNetData, netBufferSize);
                        }
                        break;

                    case BUFFER_OVERFLOW:
                        // Reset the application buffer size.
                        appBufferSize =
                                sslEngine.getSession()
                                        .getApplicationBufferSize();
                        break;

                    default: // CLOSED:
                        throw new IOException("Received" + res.getStatus()
                                + "during initial handshaking");
                }
                break;

            case NEED_WRAP:
                // Empty the local network packet buffer.
                myNetData.clear();

                // Generate handshaking data
                res = sslEngine.wrap(myAppData, myNetData);
                handshakeStatus = res.getHandshakeStatus();

                switch (res.getStatus()) {
                    case OK:
                        myNetData.flip();

                        // Send the handshaking data to peer
                        while (myNetData.hasRemaining()) {
                            socketChannel.write(myNetData);
                        }
                        break;

                    default: // BUFFER_OVERFLOW/BUFFER_UNDERFLOW/CLOSED:
                        throw new IOException("Received" + res.getStatus()
                                + "during initial handshaking");
                }
                break;

            case NEED_TASK:
                handshakeStatus = doTasks();
                break;

            default:
                break;
        }
        if (System.currentTimeMillis() > expired) {
            throw new TimeoutException("Handshake expired! " + this);
        }
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED
                || handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            // Processes after handshaking
            System.out.println("################## " + handshakeStatus
                    + " ###################");
            // handshakeComplete();
        }
    }

    /*
     * Do all the outstanding handshake tasks in the current Thread.
     */
    private HandshakeStatus doTasks() {
        Runnable runnable;

        /*
         * We could run this in a separate thread, but do in the current for
         * now.
         */
        while ((runnable = sslEngine.getDelegatedTask()) != null) {
            runnable.run();
        }

        return sslEngine.getHandshakeStatus();
    }

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int newSize) {
        ByteBuffer bb = ByteBuffer.allocate(newSize);
        buffer.flip();
        bb.put(buffer);
        return bb;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.fecdriver.impl.STLConnection#writeBuffers(java.nio.ByteBuffer
     * [])
     */
    // @Override
    protected long writeBuffers(ByteBuffer[] appBuffers) throws IOException {
        int retValue = 0;

        if (myNetData.hasRemaining() && !tryFlush(myNetData)) {
            return retValue;
        }

        /*
         * The data buffer is empty, we can reuse the entire buffer.
         */
        myNetData.clear();

        SSLEngineResult result = sslEngine.wrap(appBuffers, myNetData);
        retValue = result.bytesConsumed();

        myNetData.flip();

        switch (result.getStatus()) {

            case OK:
                if (result.getHandshakeStatus() == HandshakeStatus.NEED_TASK) {
                    doTasks();
                }
                break;

            default:
                throw new IOException("sslEngine error during data write: "
                        + result.getStatus());
        }

        /*
         * Try to flush the data, regardless of whether or not it's been
         * selected. Odds of a write buffer being full is less than a read
         * buffer being empty.
         */
        if (myNetData.hasRemaining()) {
            tryFlush(myNetData);
        }

        return retValue;
    }

    private boolean tryFlush(ByteBuffer bb) throws IOException {
        socketChannel.write(bb);
        return !bb.hasRemaining();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.fecdriver.impl.STLConnection#readBuffer(java.nio.ByteBuffer
     * )
     */
    // @Override
    protected int readBuffer(ByteBuffer dst) throws IOException {
        if (handshakeStatus != HandshakeStatus.FINISHED) {
            throw new IllegalStateException();
        }

        int pos = dst.position();

        if (peerAppData == null) {
            appBufferSize = sslEngine.getSession().getApplicationBufferSize();
            peerAppData = ByteBuffer.allocate(appBufferSize);
        } else {
            // read from peerAppData if we have left over data in it
            if (peerAppData.hasRemaining()) {
                while (peerAppData.hasRemaining() && dst.remaining() > 0) {
                    dst.put(peerAppData.get());
                }
            }
            if (!dst.hasRemaining()) {
                if (DEBUG) {
                    System.out.println("Read " + (dst.position() - pos)
                            + " from perrAppData");
                }
                return dst.position() - pos;
            }
            if (!peerAppData.hasRemaining()) {
                peerAppData.clear();
            }
        }

        // need to read data from socket
        int len = 0;
        if ((len = socketChannel.read(peerNetData)) == -1) {
            sslEngine.closeInbound(); // probably throws exception
            return -1;
        }

        if (DEBUG) {
            System.out.println("Read " + len + " from socket ");
        }
        SSLEngineResult result = null;
        do {
            if (peerAppData.remaining() < appBufferSize) {
                peerAppData =
                        resizeBuffer(peerAppData, peerAppData.capacity() * 2);
            }
            peerNetData.flip();
            if (DEBUG) {
                System.out.println("pre-unwrap " + peerAppData.position() + " "
                        + peerAppData.capacity());
            }
            result = sslEngine.unwrap(peerNetData, peerAppData);
            if (DEBUG) {
                System.out.println("Unwrap " + peerAppData.position()
                        + " into perrAppData " + result.getStatus());
            }
            peerNetData.compact();

            /*
             * Could check here for a renegotation, but we're only doing a
             * simple read/write, and won't have enough state transitions to do
             * a complete handshake, so ignore that possibility.
             */
            switch (result.getStatus()) {
                case BUFFER_OVERFLOW:
                    appBufferSize =
                            sslEngine.getSession().getApplicationBufferSize();
                    break;

                case BUFFER_UNDERFLOW:
                    // Resize buffer if needed.
                    netBufferSize =
                            sslEngine.getSession().getPacketBufferSize();
                    if (netBufferSize > peerNetData.capacity()) {
                        peerNetData = resizeBuffer(peerNetData, netBufferSize);

                        break; // break, next read will support larger buffer.
                    }
                case OK:
                    if (result.getHandshakeStatus() == HandshakeStatus.NEED_TASK) {
                        doTasks();
                    }
                    break;

                default:
                    throw new IOException("sslEngine error during data read: "
                            + result.getStatus());
            }
        } while ((peerNetData.position() != 0)
                && result.getStatus() != Status.BUFFER_UNDERFLOW);

        peerAppData.flip();
        while (peerAppData.hasRemaining() && dst.remaining() > 0) {
            dst.put(peerAppData.get());
        }
        if (DEBUG) {
            System.out.println("Read " + (dst.position() - pos)
                    + " from perrAppData");
        }

        return (dst.position() - pos);
    }
}
