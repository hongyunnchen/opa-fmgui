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
 *  File Name: ICategoryProcessorContext.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.1  2014/10/13 21:04:49  fernande
 *  Archive Log:    Changed GetDevicePropertiesTask to be driven by the PropertiesDisplayOptions in UserSettings instead of hard coded
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: fernande
 *
 ******************************************************************************/

package com.intel.stl.ui.configuration;

import com.intel.stl.api.configuration.IConfigurationApi;
import com.intel.stl.api.performance.IPerformanceApi;
import com.intel.stl.api.subnet.ISubnetApi;
import com.intel.stl.api.subnet.LinkRecordBean;
import com.intel.stl.api.subnet.NodeInfoBean;
import com.intel.stl.api.subnet.NodeRecordBean;
import com.intel.stl.api.subnet.PortInfoBean;
import com.intel.stl.api.subnet.PortRecordBean;
import com.intel.stl.api.subnet.SwitchInfoBean;
import com.intel.stl.api.subnet.SwitchRecordBean;
import com.intel.stl.ui.main.Context;
import com.intel.stl.ui.monitor.tree.FVResourceNode;

public interface ICategoryProcessorContext {

    /**
     * 
     * <i>Description:</i> return the resource node for this context
     * 
     * @return
     */
    FVResourceNode getResourceNode();

    /**
     * 
     * <i>Description:</i> returns the page context for this context
     * 
     * @return
     */
    Context getContext();

    ISubnetApi getSubnetApi();

    IConfigurationApi getConfigurationApi();

    IPerformanceApi getPerformanceApi();

    /**
     * 
     * <i>Description:</i> returns the node associated with the resource node
     * (the parent node if the resource node is a port)
     * 
     * @return
     */
    NodeRecordBean getNode();

    /**
     * 
     * <i>Description:</i> returns the node information for the resource node
     * (the parent node information if the resource node is a port)
     * 
     * @return
     */
    NodeInfoBean getNodeInfo();

    /**
     * 
     * <i>Description:</i> returns the switch record when the resource node is a
     * switch (null if not)
     * 
     * @return
     */
    SwitchRecordBean getSwitch();

    /**
     * 
     * <i>Description:</i> returns the switch information when the resource node
     * is a switch (null if not)
     * 
     * @return
     */
    SwitchInfoBean getSwitchInfo();

    /**
     * 
     * <i>Description:</i> returns the port record when the resource node is a
     * port (null if not)
     * 
     * @return
     */
    PortRecordBean getPort();

    /**
     * 
     * <i>Description:</i> returns the port information when the resource node
     * is a port (null if not)
     * 
     * @return
     */
    PortInfoBean getPortInfo();

    /**
     * 
     * <i>Description:</i> returns the link record associated with the port when
     * the resource node is a port (null if not)
     * 
     * @return
     */
    LinkRecordBean getLink();

    /**
     * 
     * <i>Description:</i> returns the node connected to the port when the
     * resource node is a port (null if not)
     * 
     * @return
     */
    NodeRecordBean getNeighbor();

    /**
     * 
     * <i>Description:</i> returns whether this port is an end port (a computing
     * node)
     * 
     * @return
     */
    boolean isEndPort();

}
