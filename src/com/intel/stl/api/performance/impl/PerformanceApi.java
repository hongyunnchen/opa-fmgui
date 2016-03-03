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

/*******************************************************************************
 *                       I N T E L   C O R P O R A T I O N
 *  
 *  Functional Group: Fabric Viewer Application
 *
 *  File Name: PerformanceApi.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.44  2015/12/17 21:51:09  jijunwan
 *  Archive Log:    PR 132124 - Newly created VF not displayed after reboot of SM
 *  Archive Log:    - improved the arch to do cache reset
 *  Archive Log:
 *  Archive Log:    Revision 1.43  2015/12/17 21:07:32  jijunwan
 *  Archive Log:    PR 132124 - Newly created VF not displayed after reboot of SM
 *  Archive Log:    - added code to clear vf and dg list cache
 *  Archive Log:
 *  Archive Log:    Revision 1.42  2015/08/17 18:49:03  jijunwan
 *  Archive Log:    PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log:    - change backend files' headers
 *  Archive Log:
 *  Archive Log:    Revision 1.41  2015/08/10 17:04:46  robertja
 *  Archive Log:    PR128974 - Email notification functionality.
 *  Archive Log:
 *  Archive Log:    Revision 1.40  2015/07/31 21:04:21  fernande
 *  Archive Log:    PR 129631 - Ports table sometimes not getting performance related data. Translating a RequestCancelledException into a special PerformanceRequestCancelledException for UI consumption.
 *  Archive Log:
 *  Archive Log:    Revision 1.39  2015/07/30 13:15:07  fernande
 *  Archive Log:    PR 129437 - ImageInfo save issue. The code now update a previously saved ImageInfo record.
 *  Archive Log:
 *  Archive Log:    Revision 1.38  2015/07/10 20:44:35  fernande
 *  Archive Log:    PR 129522 - Notice is not written to database due to topology not found. Moved FE Helpers to the session object and changed the order of initialization for the SubnetContext.
 *  Archive Log:
 *  Archive Log:    Revision 1.37  2015/07/09 18:50:29  fernande
 *  Archive Log:    PR 129447 - Database size increases a lot over a short period of time. Added GroupInfoPurgeTask; it deletes GroupInfo records older that a retention time (default is 15 days) and with a certain frequency (default is daily). These values can be changed in the settings.xml file.
 *  Archive Log:
 *  Archive Log:    Revision 1.36  2015/05/26 15:34:45  fernande
 *  Archive Log:    PR 128897 - STLAdapter worker thread is in a continuous loop, even when there are no requests to service. A new FEAdapter is being added to handle requests through SubnetRequestDispatchers, which manage state for each connection to a subnet.
 *  Archive Log:
 *  Archive Log:    Revision 1.35  2015/04/17 16:41:38  jypak
 *  Archive Log:    Fix for this intermittent problem for PA short term history : For the group info trend charts, when a different short term history time scope(1h, 2h, 6h, 24h) is selected for like ten times, the time domain axis displays years (1970, 1980...) and chart doesn't update correctly. The fix is to return null for the history data bean in performance API when the image data for the bean is returned as null.  The image data was null because the FE request of the data was cancelled before any response was processed. Because this error occurs only when the request was cancelled, we can just return null history data to avoid adding '0' time stamp to group info chart. Same safeguard code lines were added for VFInfo/PortCounter/VFPortCounter history. The port counter history doesn't seem to have this intermittent problem for single port history query but multi port history request can have same problem in the future.
 *  Archive Log:
 *  Archive Log:    Revision 1.34  2015/04/09 03:29:24  jijunwan
 *  Archive Log:    updated to match FM 390
 *  Archive Log:
 *  Archive Log:    Revision 1.33  2015/03/02 15:28:05  jypak
 *  Archive Log:    History query has been done with current live image ID '0' which isn't correct. Updates here are:
 *  Archive Log:    1. Get the image ID from current image.
 *  Archive Log:    2. History queries are done with this image ID.
 *  Archive Log:
 *  Archive Log:    Revision 1.32  2015/02/23 22:24:36  jijunwan
 *  Archive Log:    added GroupConfCache
 *  Archive Log:
 *  Archive Log:    Revision 1.31  2015/02/12 19:31:26  jijunwan
 *  Archive Log:    1) improvement on get imagestamp from image info
 *  Archive Log:    2) fixed an mistake on group info history query
 *  Archive Log:
 *  Archive Log:    Revision 1.30  2015/02/10 21:26:02  jypak
 *  Archive Log:    1. Introduced SwingWorker for history query initialization for progress status updates.
 *  Archive Log:    2. Fixed the list of future for history query in TaskScheduler. Now it can have all the Future entries created.
 *  Archive Log:    3. When selecting history type, just cancel the history query not sheduled query.
 *  Archive Log:    4. The refresh rate is now from user settings not from the config api.
 *  Archive Log:
 *  Archive Log:    Revision 1.29  2015/02/09 21:29:58  jijunwan
 *  Archive Log:    added clean up to APIs that intend to close STLConections
 *  Archive Log:
 *  Archive Log:    Revision 1.28  2015/02/04 21:38:00  jijunwan
 *  Archive Log:    impoved to handle unsigned values
 *  Archive Log:     - we promote to a "bigger" data type
 *  Archive Log:     - port numbers are now short
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.api.performance.impl;

import static com.intel.stl.api.configuration.AppInfo.PROPERTIES_DATABASE;
import static com.intel.stl.api.performance.impl.GroupInfoPurgeTask.LAST_GROUPINFO_PURGE;
import static com.intel.stl.common.STLMessages.STL30048_ERROR_SAVING_GROUP_CONFIG;
import static com.intel.stl.common.STLMessages.STL30049_ERROR_GETTING_GROUP_CONFIG;
import static com.intel.stl.common.STLMessages.STL30050_ERROR_GETTING_PORT_CONFIG;
import static com.intel.stl.common.STLMessages.STL30052_ERROR_GETTING_GROUP_INFO;
import static com.intel.stl.common.STLMessages.STL60003_PERFORMANCE_DATA_FAILURE;
import static com.intel.stl.configuration.AppSettings.PERF_GROUPINFO_PURGEFREQ;
import static com.intel.stl.configuration.AppSettings.PERF_GROUPINFO_RETENTION;
import static com.intel.stl.configuration.AppSettings.PERF_GROUPINFO_SAVEBATCH;
import static com.intel.stl.configuration.AppSettings.PERF_IMAGEINFO_CACHESIZE;
import static com.intel.stl.configuration.AppSettings.PERF_IMAGEINFO_SAVEBATCH;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intel.stl.api.DatabaseException;
import com.intel.stl.api.StringUtils;
import com.intel.stl.api.configuration.impl.SubnetContextImpl;
import com.intel.stl.api.performance.FocusPortsRspBean;
import com.intel.stl.api.performance.GroupConfigRspBean;
import com.intel.stl.api.performance.GroupInfoBean;
import com.intel.stl.api.performance.GroupListBean;
import com.intel.stl.api.performance.IPerformanceApi;
import com.intel.stl.api.performance.ImageIdBean;
import com.intel.stl.api.performance.ImageInfoBean;
import com.intel.stl.api.performance.PMConfigBean;
import com.intel.stl.api.performance.PerformanceDataNotFoundException;
import com.intel.stl.api.performance.PerformanceException;
import com.intel.stl.api.performance.PerformanceRequestCancelledException;
import com.intel.stl.api.performance.PortConfigBean;
import com.intel.stl.api.performance.PortCountersBean;
import com.intel.stl.api.performance.VFConfigRspBean;
import com.intel.stl.api.performance.VFFocusPortsRspBean;
import com.intel.stl.api.performance.VFInfoBean;
import com.intel.stl.api.performance.VFListBean;
import com.intel.stl.api.performance.VFPortCountersBean;
import com.intel.stl.api.subnet.Selection;
import com.intel.stl.api.subnet.SubnetDescription;
import com.intel.stl.common.CircularBuffer;
import com.intel.stl.common.STLMessages;
import com.intel.stl.configuration.AsyncTask;
import com.intel.stl.configuration.CacheManager;
import com.intel.stl.configuration.ResultHandler;
import com.intel.stl.datamanager.DatabaseManager;
import com.intel.stl.fecdriver.session.RequestCancelledByUserException;

/**
 * @author jijunwan
 * 
 */
public class PerformanceApi implements IPerformanceApi {

    private static Logger log = LoggerFactory.getLogger(PerformanceApi.class);

    protected static int IMAGE_INFO_BUFFER_SIZE = 10;

    protected static int IMAGE_INFO_SAVE_BATCH = 20;

    protected static int GROUP_INFO_SAVE_BATCH = 10;

    // Time threshold before another ImageInfoSaveTask can be submitted (if
    // needed); in milliseconds
    private static long IMAGE_INFO_SAVE_THROTTLE = 20000;

    // Time threshold before another GroupInfoSaveTask can be submitted (if
    // needed); in milliseconds
    private static long GROUP_INFO_SAVE_THROTTLE = 20000;

    // Frequency between purges; in hours
    private static int GROUP_INFO_PURGE_FREQUENCY = 24;

    // Frequency between purges; in days
    private static int GROUP_INFO_RETENTION = 15;

    // First purge when the app is used for the first time
    private static long GROUP_INFO_PURGE_FIRST = 300000; // five minutes

    private static long MILISECONDS_PER_HOUR = 60 * 60 * 1000;

    private static long MILISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    private final int imageinfoBatchSize;

    private final int groupinfoBatchSize;

    private final long groupinfoPurgeFrequency;

    private final long groupinfoRetention;

    private final SubnetContextImpl subnetContext;

    private final CacheManager cacheMgr;

    private final DatabaseManager dbServer;

    private final PAHelper helper;

    private final CircularBuffer<ImageIdBean, ImageInfoBean> imageInfoCache;

    private final ConcurrentLinkedQueue<GroupInfoBean> groupInfoSaveBuffer;

    private List<GroupListBean> groupList;

    private final AtomicLong lastImageInfoSaveTask = new AtomicLong(0);

    private final AtomicLong lastGroupInfoSaveTask = new AtomicLong(0);

    private final AtomicLong nextGroupInfoPurgeTask = new AtomicLong(0);

    private List<VFListBean> vfList;

    private boolean addRandom;

    private final Randomizer randomizer = new Randomizer();

    public PerformanceApi(SubnetContextImpl subnetContext) {
        this.subnetContext = subnetContext;
        this.cacheMgr = subnetContext.getCacheManager();
        this.helper = subnetContext.getSession().getPAHelper();
        this.dbServer = cacheMgr.getDatabaseManager();
        int imageinfoBufferSize =
                getAppSetting(PERF_IMAGEINFO_CACHESIZE, IMAGE_INFO_BUFFER_SIZE);
        this.imageInfoCache =
                new CircularBuffer<ImageIdBean, ImageInfoBean>(
                        imageinfoBufferSize);
        this.groupInfoSaveBuffer = new ConcurrentLinkedQueue<GroupInfoBean>();
        this.imageinfoBatchSize =
                getAppSetting(PERF_IMAGEINFO_SAVEBATCH, IMAGE_INFO_SAVE_BATCH);
        this.groupinfoBatchSize =
                getAppSetting(PERF_GROUPINFO_SAVEBATCH, GROUP_INFO_SAVE_BATCH);
        this.groupinfoPurgeFrequency =
                getAppSetting(PERF_GROUPINFO_PURGEFREQ,
                        GROUP_INFO_PURGE_FREQUENCY) * MILISECONDS_PER_HOUR;
        this.groupinfoRetention =
                getAppSetting(PERF_GROUPINFO_RETENTION, GROUP_INFO_RETENTION)
                        * MILISECONDS_PER_DAY;
        resetNextGroupInfoPurgeTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.IRandomable#setSeed(long)
     */
    @Override
    public void setSeed(long seed) {
        randomizer.setSeed(seed);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.IRandomable#setRandom(boolean)
     */
    @Override
    public void setRandom(boolean b) {
        addRandom = b;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getConnectionDescription()
     */
    @Override
    public SubnetDescription getConnectionDescription() {
        return helper.getSubnetDescription();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getImageInfoBean(long, int)
     */
    @Override
    public ImageInfoBean getImageInfo(long imageNumber, int imageOffset) {
        try {
            ImageInfoBean res = helper.getImageInfo(imageNumber, imageOffset);
            if (addRandom && res != null) {
                randomizer.randomImageInfo(res, getNumNodes());
            }
            return res;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    private int numNodes = -1;

    protected int getNumNodes() {
        if (numNodes == -1) {
            List<GroupConfigRspBean> all = getGroupConfig("All");
            Set<Integer> nodes = new HashSet<Integer>();
            for (GroupConfigRspBean pc : all) {
                nodes.add(pc.getPort().getNodeLid());
            }
            numNodes = nodes.size();
        }
        return numNodes;
    }

    @Override
    public ImageInfoBean getLatestImageInfo() {
        ImageInfoBean imageInfo = null;
        try {
            imageInfo = helper.getImageInfo(0L, 0);
            if (imageInfo != null) {
                imageInfoCache.put(imageInfo.getImageId(), imageInfo);
                if (imageInfoCache.getSaveQueueSize() > imageinfoBatchSize) {
                    ImageInfoSaveTask saveTask =
                            new ImageInfoSaveTask(helper, dbServer,
                                    imageInfoCache);
                    submitWithThrottle(saveTask, lastImageInfoSaveTask,
                            IMAGE_INFO_SAVE_THROTTLE);
                }
                if (addRandom) {
                    randomizer.randomImageInfo(imageInfo, getNumNodes());
                }
            }
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
        return imageInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getGroupList()
     */
    @Override
    public synchronized List<GroupListBean> getGroupList() {
        if (groupList == null) {
            try {
                groupList = helper.getGroupList();
            } catch (Exception e) {
                throw getPerformanceException(e);
            }
        }
        return groupList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getGroupInfo(java.lang.String)
     */
    @Override
    public GroupInfoBean getGroupInfo(String name) {
        try {
            GroupInfoBean groupInfo = helper.getGroupInfo(name);
            if (groupInfo != null) {
                saveGroupInfo(groupInfo);
                if (addRandom) {
                    randomizer.randomGroupInfo(groupInfo);
                }
            }
            return groupInfo;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    @Override
    public GroupInfoBean getGroupInfoHistory(String name, long imageID,
            int imageOffset) {
        try {
            GroupInfoBean groupInfo =
                    helper.getGroupInfoHistory(name, imageID, imageOffset);

            if (groupInfo != null) {
                ImageIdBean image = groupInfo.getImageId();
                // we always get new unique image number, so directly query it
                ImageInfoBean imageInfo =
                        getImageInfo(image.getImageNumber(),
                                image.getImageOffset());
                if (imageInfo != null) {
                    groupInfo.setTimestamp(imageInfo.getSweepStart());
                } else {
                    // Don't return groupInfo without timestamp. imageInfo can
                    // be null if the FE request is cancelled while waiting for
                    // FV command response.
                    groupInfo = null;
                }

                if (addRandom && groupInfo != null) {
                    randomizer.randomGroupInfo(groupInfo);
                }

            }
            return groupInfo;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    @Override
    public void saveGroupConfig(String groupName, List<PortConfigBean> ports) {
        try {
            dbServer.saveGroupConfig(getConnectionDescription().getName(),
                    groupName, ports);
        } catch (DatabaseException e) {
            PerformanceException pe =
                    getPerformanceException(STL30048_ERROR_SAVING_GROUP_CONFIG,
                            e);
            throw pe;
        }
    }

    @Override
    public List<GroupConfigRspBean> getGroupConfig(String subnetName,
            String groupName) throws PerformanceDataNotFoundException {
        List<GroupConfigRspBean> groupConfigBean = null;
        try {
            dbServer.getGroupConfig(subnetName, groupName);
        } catch (DatabaseException e) {
            PerformanceException pe =
                    getPerformanceException(
                            STL30049_ERROR_GETTING_GROUP_CONFIG, e);
            throw pe;
        }
        return groupConfigBean;
    }

    @Override
    public List<PortConfigBean> getPortConfig(String subnetName)
            throws PerformanceDataNotFoundException {
        List<PortConfigBean> portConfigBeans = null;
        try {
            portConfigBeans = dbServer.getPortConfig(subnetName);
        } catch (DatabaseException e) {
            PerformanceException pe =
                    getPerformanceException(STL30050_ERROR_GETTING_PORT_CONFIG,
                            e);
            throw pe;
        }
        return portConfigBeans;
    }

    @Override
    public List<GroupInfoBean> getGroupInfo(String subnetName,
            String groupName, long startTime, long stopTime)
            throws PerformanceDataNotFoundException {
        try {
            return dbServer.getGroupInfo(subnetName, groupName, startTime,
                    stopTime);
        } catch (DatabaseException e) {
            PerformanceException pe =
                    getPerformanceException(STL30052_ERROR_GETTING_GROUP_INFO,
                            e);
            throw pe;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.hpc.stl.api.IPerformanceApi#getGroupConfig(java.lang.String)
     */
    @Override
    public List<GroupConfigRspBean> getGroupConfig(String name) {
        GroupConfCache gcc = cacheMgr.acquireGroupConfCache();
        try {
            return gcc.getGroupConfig(name);
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getDeviceGroup(int)
     */
    @Override
    public List<String> getDeviceGroup(int lid) {
        List<String> res = new ArrayList<String>();
        List<GroupListBean> groupList = getGroupList();
        if (groupList == null || groupList.isEmpty()) {
            return res;
        }

        for (GroupListBean group : groupList) {
            List<GroupConfigRspBean> conf =
                    getGroupConfig(group.getGroupName());
            if (conf == null) {
                continue;
            }

            for (GroupConfigRspBean port : conf) {
                if (port.getPort().getNodeLid() == lid) {
                    res.add(group.getGroupName());
                    break;
                }
            }
        }
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getDeviceGroup(int, short)
     */
    @Override
    public synchronized PortCountersBean getPortCounters(int lid, short portNum) {
        try {
            PortCountersBean bean = helper.getPortCounter(lid, portNum);
            if (bean != null) {
                ImageInfoBean imageInfo = getImageInfo(bean.getImageId());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                }

                if (addRandom) {
                    randomizer.randomPortCounters(bean);
                }
            }
            return bean;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    @Override
    public PortCountersBean getPortCountersHistory(int lid, short portNum,
            long imageID, int imageOffset) {
        try {
            PortCountersBean bean =
                    helper.getPortCounterHistory(lid, portNum, imageID,
                            imageOffset);
            if (bean != null) {
                // we always get new unique image number, so directly query it
                ImageIdBean image = bean.getImageId();
                ImageInfoBean imageInfo =
                        getImageInfo(image.getImageNumber(),
                                image.getImageOffset());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                } else {
                    // Don't return bean without timestamp. imageInfo can
                    // be null if the FE request is cancelled while waiting for
                    // FV command response.
                    bean = null;
                }
            }
            return bean;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getFocusPorts(java.lang
     * .String, com.intel.stl.fecdriver.messages.command.InputFocus.Selection,
     * int)
     */
    @Override
    public List<FocusPortsRspBean> getFocusPorts(String groupName,
            Selection selection, int n) {
        try {
            List<FocusPortsRspBean> res =
                    helper.getFocusPort(groupName, selection, n);
            if (addRandom && res != null) {
                randomizer.randomFocusPorts(res);
            }
            return res;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.stl.api.IPerformanceApi#getTopBandwidth(String, int)
     */
    @Override
    public List<FocusPortsRspBean> getTopBandwidth(String groupName, int n) {
        return getFocusPorts(groupName, Selection.UTILIZATION_HIGH, n);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.hpc.stl.api.IPerformanceApi#getTopCongestion(java.lang.String,
     * int)
     */
    @Override
    public List<FocusPortsRspBean> getTopCongestion(String groupName, int n) {
        return getFocusPorts(groupName, Selection.CONGESTION_ERRORS_HIGH, n);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.performance.IPerformanceApi#getVFList()
     */
    @Override
    public synchronized List<VFListBean> getVFList() {
        if (vfList == null) {
            try {
                vfList = helper.getVFList();
            } catch (Exception e) {
                throw getPerformanceException(e);
            }
        }
        return vfList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFInfo(java.lang.String)
     */
    @Override
    public VFInfoBean getVFInfo(String name) {
        try {
            VFInfoBean bean = helper.getVFInfo(name);
            if (bean != null) {
                ImageInfoBean imageInfo = getImageInfo(bean.getImageId());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                }
            }
            if (addRandom && bean != null) {
                randomizer.randomVFInfo(bean);
            }
            return bean;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFInfo(java.lang.String,
     * int)
     */
    @Override
    public VFInfoBean getVFInfoHistory(String name, long imageID,
            int imageOffset) {
        try {
            VFInfoBean bean =
                    helper.getVFInfoHistory(name, imageID, imageOffset);
            if (bean != null) {
                ImageInfoBean imageInfo = getImageInfo(bean.getImageId());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                } else {
                    // Don't return bean without timestamp. imageInfo can
                    // be null if the FE request is cancelled while waiting for
                    // FV command response.
                    bean = null;
                }
            }
            if (addRandom && bean != null) {
                randomizer.randomVFInfo(bean);
            }
            return bean;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFConfig(java.lang.String
     * )
     */
    @Override
    public List<VFConfigRspBean> getVFConfig(String name) {
        GroupConfCache gcc = cacheMgr.acquireGroupConfCache();
        try {
            return gcc.getVFConfig(name);
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.performance.IPerformanceApi#getVfNames(int)
     */
    @Override
    public List<String> getVFNames(int lid) {
        List<String> res = new ArrayList<String>();
        List<VFListBean> vfList = getVFList();
        if (vfList == null || vfList.isEmpty()) {
            return res;
        }

        for (VFListBean vf : vfList) {
            String name = vf.getVfName();
            List<VFConfigRspBean> conf = getVFConfig(name);
            if (conf == null) {
                continue;
            }

            for (VFConfigRspBean port : conf) {
                if (port.getPort().getNodeLid() == lid) {
                    res.add(name);
                    break;
                }
            }
        }
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFFocusPorts(java.lang
     * .String, com.intel.stl.api.subnet.Selection, int)
     */
    @Override
    public List<VFFocusPortsRspBean> getVFFocusPorts(String vfName,
            Selection selection, int n) {
        try {
            List<VFFocusPortsRspBean> res =
                    helper.getVFFocusPort(vfName, selection, n);
            if (addRandom && res != null) {
                randomizer.randomVFFocusPorts(res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFPortCounters(String,
     * int, short)
     */
    @Override
    public VFPortCountersBean getVFPortCounters(String vfName, int lid,
            short portNum) {
        try {
            VFPortCountersBean bean =
                    helper.getVFPortCounter(vfName, lid, portNum);
            if (bean != null) {
                ImageInfoBean imageInfo = getImageInfo(bean.getImageId());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                }
            }
            return bean;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw getPerformanceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.performance.IPerformanceApi#getVFPortCounters(java.
     * lang.String, int, short, int)
     */
    @Override
    public VFPortCountersBean getVFPortCountersHistory(String vfName, int lid,
            short portNum, long imageID, int imageOffset) {
        try {
            VFPortCountersBean bean =
                    helper.getVFPortCounterHistory(vfName, lid, portNum,
                            imageID, imageOffset);
            if (bean != null) {
                ImageInfoBean imageInfo = getImageInfo(bean.getImageId());
                if (imageInfo != null) {
                    bean.setTimestamp(imageInfo.getSweepStart());
                } else {
                    // Don't return bean without timestamp. imageInfo can
                    // be null if the FE request is cancelled while waiting for
                    // FV command response.
                    bean = null;
                }
            }
            return bean;
        } catch (PerformanceRequestCancelledException e) {
            throw e;
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    @Override
    public PMConfigBean getPMConfig() {
        try {
            return helper.getPMConfig();
        } catch (Exception e) {
            throw getPerformanceException(e);
        }
    }

    private void saveGroupInfo(GroupInfoBean groupInfo) throws Exception {
        ImageIdBean imageId = groupInfo.getImageId();
        ImageInfoBean imageInfo = getImageInfo(imageId);
        if (imageInfo != null) {
            groupInfo.setTimestamp(imageInfo.getSweepStart());
            GroupCache groupCache = cacheMgr.acquireGroupCache();
            if (groupCache.isGroupDefined(groupInfo.getGroupName())) {
                if (!groupInfoSaveBuffer.contains(groupInfo)) {
                    groupInfoSaveBuffer.offer(groupInfo);
                }
            }
            if (groupInfoSaveBuffer.size() > groupinfoBatchSize) {
                // Start background task to save to database
                GroupInfoSaveTask saveTask =
                        new GroupInfoSaveTask(helper, dbServer,
                                groupInfoSaveBuffer);
                submitWithThrottle(saveTask, lastGroupInfoSaveTask,
                        GROUP_INFO_SAVE_THROTTLE);
            }
            long now = System.currentTimeMillis();
            long purgeTime = nextGroupInfoPurgeTask.get();
            if (now > purgeTime) {
                // In case the purge fails, we retry after this amount of time
                long nextTry = now + GROUP_INFO_PURGE_FIRST;
                boolean swapped =
                        nextGroupInfoPurgeTask
                                .compareAndSet(purgeTime, nextTry);
                if (swapped) {
                    long ago = now - groupinfoRetention;
                    GroupInfoPurgeTask task =
                            new GroupInfoPurgeTask(helper, dbServer, ago);
                    log.info(
                            "Starting GroupInfo purge task. Purging records older than {}",
                            getFormattedTimestamp(ago));
                    subnetContext.getProcessingService().submit(task,
                            new ResultHandler<Integer>() {

                                @Override
                                public void onTaskCompleted(
                                        Future<Integer> result) {
                                    try {
                                        int deleted = result.get();
                                        resetNextGroupInfoPurgeTime();
                                        log.info(
                                                "{} GroupInfo records purged.",
                                                deleted);
                                    } catch (InterruptedException e) {
                                        log.error("GroupInfo purge task was interrupted");
                                    } catch (ExecutionException e) {
                                        log.error(
                                                "GroupInfo purge task had an error",
                                                e.getCause());
                                    }
                                }
                            });
                }
            }
        }
    }

    private ImageInfoBean getImageInfo(ImageIdBean imageId) throws Exception {
        // We make three attempts to get ImageInfo:
        // - first, we try the ImageInfo cache in memory
        // - then, we get latest ImageInfo from the FE (after all,
        // getGroupInfo is implicitly using imageNumber 0L and offset 0,
        // which means the latest for the group)
        // - last, we get the ImageInfo from the FE by ImageId (no
        // caching here because we don't know the ImageId sequence)
        ImageInfoBean imageInfo = imageInfoCache.get(imageId);
        if (imageInfo == null) {
            imageInfo = getLatestImageInfo();
            if (imageInfo != null && !imageInfo.getImageId().equals(imageId)) {
                imageInfo = null;
            }
        }
        if (imageInfo == null) {
            imageInfo = helper.getImageInfo(imageId);
        }
        return imageInfo;
    }

    private PerformanceException getPerformanceException(STLMessages msg,
            Exception e) {
        PerformanceException pe =
                new PerformanceException(msg, e, StringUtils.getErrorMessage(e));
        log.error(StringUtils.getErrorMessage(pe), e);
        return pe;
    }

    private PerformanceException getPerformanceException(Exception e) {
        PerformanceException pe;
        if (e instanceof RequestCancelledByUserException) {
            pe = new PerformanceRequestCancelledException();
        } else {
            pe =
                    new PerformanceException(STL60003_PERFORMANCE_DATA_FAILURE,
                            e, StringUtils.getErrorMessage(e));
            log.error(StringUtils.getErrorMessage(pe), e);
        }
        return pe;
    }

    private void submitWithThrottle(AsyncTask<?> task,
            AtomicLong lastSubmissionTS, long throttle) {
        long now = System.currentTimeMillis();
        long lastSubmission = lastSubmissionTS.get();
        if (now > (lastSubmission + throttle)) {
            // When multiple threads are trying to submit the same task at the
            // same time, only one will get to set it and swapped will be true
            boolean swapped =
                    lastSubmissionTS.compareAndSet(lastSubmission, now);
            if (swapped) {
                subnetContext.getProcessingService().submit(task, null);
            }
        }
    }

    private void resetNextGroupInfoPurgeTime() {
        Map<String, Properties> appProps =
                dbServer.getAppInfo().getPropertiesMap();
        Properties dbProps = appProps.get(PROPERTIES_DATABASE);
        if (dbProps == null) {
            // There is no reference point, create one after the application has
            // initialized
            nextGroupInfoPurgeTask.set(System.currentTimeMillis()
                    + GROUP_INFO_PURGE_FIRST);
        } else {
            SubnetDescription subnet = subnetContext.getSubnetDescription();
            String lastPurge =
                    dbProps.getProperty(LAST_GROUPINFO_PURGE
                            + subnet.getSubnetId());
            if (lastPurge == null) {
                nextGroupInfoPurgeTask.set(System.currentTimeMillis()
                        + GROUP_INFO_PURGE_FIRST);
            } else {
                try {
                    long lastPurgeTS = Long.parseLong(lastPurge);
                    nextGroupInfoPurgeTask.set(lastPurgeTS
                            + groupinfoPurgeFrequency);
                } catch (NumberFormatException e) {
                    nextGroupInfoPurgeTask.set(System.currentTimeMillis()
                            + GROUP_INFO_PURGE_FIRST);
                }
            }
        }
        log.info("Next GroupInfo purge task to start on {}",
                getFormattedTimestamp(nextGroupInfoPurgeTask.get()));
    }

    private String getFormattedTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return sdf.format(date);
    }

    private int getAppSetting(String settingName, int defaultValue) {
        try {
            int value =
                    Integer.parseInt(subnetContext.getAppSetting(settingName,
                            new Integer(defaultValue).toString()));
            return value;
        } catch (NumberFormatException e) {
            log.warn(
                    "Invalid value for setting '{}'; using default value of {}",
                    settingName, defaultValue, e);
            return defaultValue;
        }
    }

    // For testing
    protected CircularBuffer<ImageIdBean, ImageInfoBean> getImageInfoCache() {
        return imageInfoCache;
    }

    protected ConcurrentLinkedQueue<GroupInfoBean> getGroupInfoSaveBuffer() {
        return groupInfoSaveBuffer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.performance.IPerformanceApi#reset()
     */
    @Override
    public synchronized void reset() {
        vfList = null;
        groupList = null;
        // will reset cacheMgr in SubnetContext.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.performance.IPerformanceApi#cleanup()
     */
    @Override
    public void cleanup() {
        helper.close();
        groupInfoSaveBuffer.clear();
    }

}
