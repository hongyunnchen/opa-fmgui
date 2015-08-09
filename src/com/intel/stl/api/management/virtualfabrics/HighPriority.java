/**
 * INTEL CONFIDENTIAL
 * Copyright (c) 2015 Intel Corporation All Rights Reserved.
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
 *  File Name: HighPriority.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.2  2015/03/27 15:42:32  jijunwan
 *  Archive Log:    added installVirtualFabric to IAttribute
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2015/03/25 19:10:04  jijunwan
 *  Archive Log:    first version of VirtualFabric support
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.api.management.virtualfabrics;

import com.intel.stl.api.management.BooleanNode;
import com.intel.stl.api.management.XMLConstants;

/**
 * If set to one, this indicates high priority traffic which does not require
 * any bandwidth limiting. This would typically include management or control
 * traffic which is low bandwidth, but critical to process in a timely manner.
 * An example is SA traffic where there is no reason to restrict bandwidth since
 * it is low volume, but it needs to be serviced at a high priority.
 * When priority is set to High, any bandwidth allocation is ignored for this
 * Virtual Fabric.
 */
public class HighPriority extends BooleanNode {
    private static final long serialVersionUID = 2766585966443458500L;

    /**
     * Description:
     * 
     * @param type
     */
    public HighPriority() {
        this(false);
    }

    /**
     * Description:
     * 
     * @param type
     * @param isSelected
     */
    public HighPriority(boolean isSelected) {
        super(XMLConstants.HIGH_PRIORITY, isSelected);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.api.management.BooleanNode#copy()
     */
    @Override
    public HighPriority copy() {
        return new HighPriority(isSelected());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.api.management.BooleanNode#installVirtualFabric(com.intel
     * .stl.api.management.virtualfabrics.VirtualFabric)
     */
    @Override
    public void installVirtualFabric(VirtualFabric vf) {
        vf.setHighPriority(this);
    }

}
