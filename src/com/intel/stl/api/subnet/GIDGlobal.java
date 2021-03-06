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
package com.intel.stl.api.subnet;

/**
 */
public class GIDGlobal extends GIDBean {
    private static final long serialVersionUID = 1L;

    private long subnetPrefix;

    private long interfaceId;

    public GIDGlobal() {
        super();
    }

    public GIDGlobal(long subnetPrefix, long interfaceId) {
        super();
        this.subnetPrefix = subnetPrefix;
        this.interfaceId = interfaceId;
    }

    /**
     * @return the subnetPrefix
     */
    @Override
    public long getSubnetPrefix() {
        return subnetPrefix;
    }

    /**
     * @param subnetPrefix
     *            the subnetPrefix to set
     */
    @Override
    public void setSubnetPrefix(long subnetPrefix) {
        this.subnetPrefix = subnetPrefix;
    }

    /**
     * @return the interfaceID
     */
    @Override
    public long getInterfaceID() {
        return interfaceId;
    }

    /**
     * @param interfaceID
     *            the interfaceID to set
     */
    @Override
    public void setInterfaceID(long interfaceId) {
        this.interfaceId = interfaceId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GIDGlobal [subnetPrefix=0x" + Long.toHexString(subnetPrefix)
                + ", interfaceID=0x" + Long.toHexString(interfaceId) + "]";
    }

}
