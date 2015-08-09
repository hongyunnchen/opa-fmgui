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
package com.intel.stl.api.performance;

import java.io.Serializable;

/**
 * @author jijunwan
 * 
 */
public class FocusPortsReqBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupName;

    private ImageIdBean imageId;

    private int select;

    private int start;

    private int range;

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the imageId
     */
    public ImageIdBean getImageId() {
        return imageId;
    }

    /**
     * @param imageId
     *            the imageId to set
     */
    public void setImageId(ImageIdBean imageId) {
        this.imageId = imageId;
    }

    /**
     * @return the select
     */
    public int getSelect() {
        return select;
    }

    /**
     * @param select
     *            the select to set
     */
    public void setSelect(int select) {
        this.select = select;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range
     *            the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FocsuPortReqBean [groupName=" + groupName + ", imageId="
                + imageId + ", select=" + select + ", start=" + start
                + ", range=" + range + "]";
    }

}
