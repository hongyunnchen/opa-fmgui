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

package com.intel.stl.api.management;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intel.stl.api.Utils;
import com.intel.stl.api.subnet.HostInfo;
import com.intel.stl.api.subnet.HostType;
import com.intel.stl.api.subnet.SubnetDescription;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Temporary FM Conf Helper that uses instance temporary connection
 */
public class TmpFmConfHelper extends FMConfHelper {
    private final static Logger log = LoggerFactory
            .getLogger(TmpFmConfHelper.class);

    /**
     * Description: Create a temporary instance
     * 
     * @param host
     */
    public TmpFmConfHelper(HostInfo host) {
        super(new SubnetDescription("TMP"));
        subnet.setFEList(Collections.singletonList(host));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.management.FMConfHelper#testConnection(char[])
     */
    @Override
    public void fetchConfigFile(char[] password) throws Exception {
        HostInfo hostInfo = subnet.getCurrentFE();
        String hostName = hostInfo.getHost();
        String userName = hostInfo.getSshUserName();
        int port = hostInfo.getSshPortNum();

        JSch jsch = Utils.createJSch();
        Session session = jsch.getSession(userName, hostName, port);
        session.setPassword(new String(password));
        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        tmpConfFile = File.createTempFile("~FV", null);
        tmpConfFile.deleteOnExit();
        try {
            // download file
            Channel channel = session.openChannel("sftp");
            channel.connect();
            try {
                ChannelSftp channelSftp = (ChannelSftp) channel;
                HostType hostType = determineHostType(channelSftp);
                hostInfo.setHostType(hostType);
                channelSftp.cd(hostType.getConfigLocation());
                channelSftp.get(CONF, tmpConfFile.getAbsolutePath());
                log.info("Download " + hostName + ":" + CONF + " to "
                        + tmpConfFile.getAbsolutePath() + " (size="
                        + tmpConfFile.length() + ")");
                subnet.setCurrentUser(userName);
            } finally {
                channel.disconnect();
            }
        } finally {
            session.disconnect();
        }
    }

}
