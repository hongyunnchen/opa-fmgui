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
package com.intel.stl.api.performance;

import java.io.Serializable;

/**
 */
public class VFFocusPortsReqBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String vfName;

    private long vfSid;

    private ImageIdBean imageId;

    private int select;

    private int start;

    private int range;

    /**
     * @return the vfName
     */
    public String getVfName() {
        return vfName;
    }

    /**
     * @param vfName
     *            the vfName to set
     */
    public void setVfName(String vfName) {
        if (vfName.length() > PAConstants.STL_PM_GROUPNAMELEN) {
            throw new IllegalArgumentException("Invalid string length: "
                    + vfName.length() + " > " + PAConstants.STL_PM_GROUPNAMELEN);
        }

        this.vfName = vfName;
    }

    /**
     * @return the vfSid
     */
    public long getVfSid() {
        return vfSid;
    }

    /**
     * @param vfSid
     *            the vfSid to set
     */
    public void setVfSid(long vfSid) {
        this.vfSid = vfSid;
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
        return "VFFocsuPortReqBean [vfName=" + vfName + ", imageId=" + imageId
                + ", select=" + select + ", start=" + start + ", range="
                + range + "]";
    }

}
