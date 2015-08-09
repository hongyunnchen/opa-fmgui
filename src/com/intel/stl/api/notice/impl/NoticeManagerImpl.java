/**
 * INTEL CONFIDENTIAL
 * Copyright (c) 2014 Intel Corporation All Rights Reserved.
 * The source code contained or described herein and all documents related to the source code ("Material")
 * are owned by Intel Corporation or its suppliers or licensors. Title to the Material remains with Intel
 * Corporation or its suppliers and licensors. The Material contains trade secrets and proprietary and
 * confidential information of Intel or its suppliers and licensors. The Material is protected by
 * worldwide copyright and trade secret laws and treaty provisions. No part of the Material may be used,
 * copied, reproduced, modified, published, uploaded, posted, transmitted, distributed, or disclosed in
 * any way without Intel's prior express written permission. No license under any patent, copyright,
 * trade secret or other intellectual property right is granted to or conferred upon you by disclosure
 * or delivery of the Materials, either expressly, by implication, inducement, estoppel or otherwise.
 * Any license under such intellectual property rights must be express and approved by Intel in writing.
 */

/*******************************************************************************
 *                       I N T E L   C O R P O R A T I O N
 *	
 *  Functional Group: Fabric Viewer Application
 *
 *  File Name: NoticeManagerImpl.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.19  2015/04/27 21:45:53  rjtierne
 *  Archive Log:    - Added topologyUpdateTask() to the processingService if exception is thrown due to
 *  Archive Log:    missing topology from the database.
 *  Archive Log:    - Updated constructor to accept the SAHelper so it can be passed to TopologyUpdateTask()
 *  Archive Log:    when trying to process notices without a topology in the database.
 *  Archive Log:    - Put notice processing code into a new method processNotices() and invoked when
 *  Archive Log:    topology is in the database or after a topology update if it's missing.
 *  Archive Log:
 *  Archive Log:    Revision 1.18  2015/03/27 20:35:06  fernande
 *  Archive Log:    Changed to use the interface IConnection instead of the concrete implementation
 *  Archive Log:
 *  Archive Log:    Revision 1.17  2015/03/19 13:47:37  jijunwan
 *  Archive Log:    catch exception
 *  Archive Log:
 *  Archive Log:    Revision 1.16  2015/03/10 18:43:09  jypak
 *  Archive Log:    JavaHelp System introduced to enable online help.
 *  Archive Log:
 *  
 *  Overview: A daemon thread to wait for notices from FE and start a process to
 *  save the notices to database. Even before start waiting, 
 *  initialize 'incomplete' notices in database to be 'complete'.
 *
 *  @author: jypak
 *
 ******************************************************************************/

package com.intel.stl.api.notice.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intel.stl.api.notice.IEventListener;
import com.intel.stl.api.notice.INoticeApi;
import com.intel.stl.api.notice.INoticeManager;
import com.intel.stl.api.notice.NoticeBean;
import com.intel.stl.api.subnet.SubnetDataNotFoundException;
import com.intel.stl.api.subnet.SubnetException;
import com.intel.stl.api.subnet.impl.SAHelper;
import com.intel.stl.api.subnet.impl.TopologyUpdateTask;
import com.intel.stl.configuration.CacheManager;
import com.intel.stl.configuration.ResultHandler;
import com.intel.stl.configuration.SerialProcessingService;
import com.intel.stl.datamanager.DatabaseManager;
import com.intel.stl.fecdriver.IConnection;

public class NoticeManagerImpl implements INoticeManager,
        IEventListener<NoticeBean>, Runnable {
    private static Logger log = LoggerFactory
            .getLogger(NoticeManagerImpl.class);

    private static boolean DEBUG = false;

    private final String subnetName;

    private final IConnection conn;

    private final DatabaseManager dbMgr;

    private final SerialProcessingService processingService;

    private final CacheManager cacheMgr;

    private boolean terminate = false;

    private final BlockingQueue<NoticeBean[]> queue;

    private NoticeSimulator simulator;

    private final INoticeApi noticeApi;

    private Long randomSeed;

    private final SAHelper saHelper;

    public NoticeManagerImpl(IConnection conn, CacheManager cacheMgr,
            INoticeApi noticeApi, SAHelper saHelper) {
        this.subnetName = conn.getConnectionDescription().getName();
        this.conn = conn;
        this.dbMgr = cacheMgr.getDatabaseManager();
        this.processingService = cacheMgr.getProcessingService();
        this.cacheMgr = cacheMgr;
        this.noticeApi = noticeApi;
        this.saHelper = saHelper;
        // FE will talk to STLConnection and STLConnection will call onNewEvent
        // below.
        this.queue = new ArrayBlockingQueue<NoticeBean[]>(1024);
        this.conn.addNoticeListener(this);
    }

    @Override
    public void setSeed(long seed) {
        if (simulator != null) {
            simulator.setSeed(seed);
        }
        randomSeed = seed;
    }

    @Override
    public void setRandom(boolean b) {
        if (b) {
            if (simulator == null) {
                simulator = new NoticeSimulator(cacheMgr);
                if (randomSeed != null) {
                    simulator.setSeed(randomSeed);
                }
            }
            simulator.addEventListener(this);
            simulator.run();
        } else {
            if (simulator != null) {
                simulator.removeEventListener(this);
                simulator.stop();
            }
        }
    }

    @Override
    public void cleanup() {
        if (simulator != null) {
            simulator.stop();
        }
        conn.removeNoticeListener(this);
    }

    /**
     * Description:
     * 
     * @param data
     * @throws SubnetException
     */
    protected void fireNewNotice() {
        NoticeBean[] noticeData = null;
        try {
            // Wait until notice is received and queued.
            noticeData = queue.take();
            // Generate a special(empty) notice to shutdown thread.
            // once it's received, set a flag to get out of while loop
            // in the run() method and return here without processing the
            // rest. That's a clean way to stop this thread rather than
            // interrupting it.

            if (noticeData.length == 0) {
                setShutdown(true);
                return;
            }

        } catch (InterruptedException e1) {
            log.error("NoticeManager: fireNewNotice operation was interrupted",
                    e1);
        }
        // System.out.println("Processing notice data: " + noticeData.length);

        // Don't need to use atomic reference here because only one thread is
        // submitting this task.
        NoticeSaveTask noticeSaveTask =
                new NoticeSaveTask(dbMgr, subnetName, noticeData);

        final NoticeBean[] notices = noticeData;
        processingService.submit(noticeSaveTask, new ResultHandler<Void>() {

            @Override
            public void onTaskCompleted(Future<Void> saveResult) {
                if (isShutdown()) {
                    log.info("Not processing notices due to shutdown");
                    return;
                }
                try {
                    // Let NoticeManager know if this task was successful
                    // or not so that it can at least log errors. In future, UI
                    // should also know the errors.
                    // So, should wait if necessary to get
                    // result and see if there is any exception error
                    // (InterrupedExceotion or ExecutionException) during
                    // execution of the task.

                    saveResult.get();

                } catch (InterruptedException e) {
                    log.error("notice save task was interrupted", e);
                } catch (ExecutionException e) {
                    log.error("Exception caught during notice save task",
                            e.getCause());
                }

                try {
                    // This is done to make sure that the subnet is defined in
                    // the database and that a topology has been saved for it.
                    dbMgr.getTopologyId(subnetName);

                    // If there's a topology in the DB, process the notices
                    processNotices(notices);

                } catch (SubnetDataNotFoundException e) {
                    // There was no topology in the database so save it now
                    // and then process the notices
                    TopologyUpdateTask topologyUpdateTask =
                            new TopologyUpdateTask(saHelper, dbMgr);

                    processingService.submitSerial(topologyUpdateTask,
                            new ResultHandler<Void>() {

                                @Override
                                public void onTaskCompleted(Future<Void> result) {

                                    try {
                                        // Now process the notices
                                        processNotices(notices);

                                    } catch (Exception e) {
                                        log.error(
                                                "Exception caught during topology update task",
                                                e.getCause());
                                    }

                                }
                            });
                }
            }
        });
    }

    protected void processNotices(final NoticeBean[] notices) {
        // Once the notices are saved, create a NoticeProcessTask
        // and submit it.
        // It's an asynchronous task so even though there is
        // processing speed difference between
        // NoticeSaveTask and NoticeProcessTask, we don't care.
        NoticeProcessTask noticeProcessTask =
                new NoticeProcessTask(subnetName, dbMgr, cacheMgr);

        // Note that the NoticeProcessTask runs in the serial
        // thread, which means that only one task is processed at
        // once. This is done for two reasons: first, if an outburst
        // of notices comes our way, NoticeProcessTask will pick up
        // whatever number has been enqueued and process them in one
        // task; secondly, since there is a potential to trigger a
        // copy of the whole topology if new nodes and links are
        // added, this process would need to be unique (like the
        // SaveTopologyTask, which uses the same thread) so that two
        // tasks do not step on each other.
        processingService.submitSerial(noticeProcessTask,
                new ResultHandler<Future<Boolean>>() {
                    @Override
                    public void onTaskCompleted(
                            Future<Future<Boolean>> processResult) {
                        try {
                            Future<Boolean> dbFuture = processResult.get();
                            noticeApi.addNewEventDescriptions(notices);

                            Boolean topologyChanged = dbFuture.get();
                            log.info("Topology changed as a result of processing notices: "
                                    + topologyChanged);
                        } catch (InterruptedException e) {
                            log.error("notice process task was interrupted", e);
                        } catch (ExecutionException e) {
                            Exception executionException =
                                    (Exception) e.getCause();
                            // TODO, we should inform the UI of the
                            // error (perhaps a
                            // newEventDescription?)
                            log.error(
                                    "Exception caught during notice process task",
                                    executionException);
                        } catch (Exception e) {
                            log.error(
                                    "Exception caught during notice process task",
                                    e);
                        }
                    }
                });
    }

    protected void initializeNotices() {
        try {
            dbMgr.resetNotices(subnetName);
        } catch (Exception e) {
            log.error("Exception caught during initializeNotices", e);
        }
    }

    /**
     * A separate thread to wait for notices
     * 
     */
    @Override
    public void run() {
        // even before waiting for notice, update NOTICES table
        // to set 'processed' flag to true.
        initializeNotices();
        // If this thread is interrupted, will be killed.
        // AppContextImpl#shutdown() will interrupt.
        while (!isShutdown()) {
            // Wait until the onNewEvent is invoked from the STLConnection which
            // is supposed to forward a notice
            // when it's received from the FE.
            fireNewNotice();
        }
    }

    /**
     * Synchronized to prevent a racing condition with shutdown call.
     */
    @Override
    public synchronized void onNewEvent(NoticeBean[] data) {
        // persist notices
        if (DEBUG) {
            for (NoticeBean bean : data) {
                System.out.println(bean);
            }
        }

        try {
            queue.put(data);
        } catch (InterruptedException e) {
            log.error("BlockingQueue put operation exception: onNewEvent", e);
        }

    }

    /**
     * 
     * Description: Synchronized to prevent a racing condition with onNewEvent
     * call. Put the shutdown notice to blocking queue and then, stop listening
     * for the notice through the STLConnection.
     * 
     */
    @Override
    public synchronized void shutdown() {
        NoticeBean[] shutdownNotice = new NoticeBean[0];
        // System.out.println("Notice manager being shutdown");
        try {
            queue.put(shutdownNotice);
            this.conn.removeNoticeListener(this);
        } catch (InterruptedException e) {
            log.error("BlockingQueue put operation exception: shutdown", e);
        }
    }

    /**
     * @return the shutdown
     */
    public boolean isShutdown() {
        return terminate;
    }

    public void setShutdown(boolean shutdown) {
        this.terminate = shutdown;
    }
}
