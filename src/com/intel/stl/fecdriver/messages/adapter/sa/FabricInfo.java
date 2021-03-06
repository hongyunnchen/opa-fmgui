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

package com.intel.stl.fecdriver.messages.adapter.sa;

import com.intel.stl.api.subnet.FabricInfoBean;
import com.intel.stl.fecdriver.messages.adapter.SimpleDatagram;

/**
 * 
 * ref: /ALL_EMB/IbAccess/Common/Inc/stl_sa.h v1.111
 * 
 * <pre>
 * typedef struct {
 *     uint32  NumHFIs;
 *     uint32  NumSwitches;
 *     uint32  NumInternalHFILinks;
 *     uint32  NumExternalHFILinks;
 *     uint32  NumInternalISLs;
 *     uint32  NumExternalISLs;
 *     uint32  NumDegradedHFILinks;
 *     uint32  NumDegradedISLs;
 *     uint32  NumOmittedHFILinks;
 *     uint32  NumOmittedISLs;
 *     uint32  rsvd5[92];
 * } PACK_SUFFIX STL_FABRICINFO_RECORD;
 * 
 * </pre>
 */
public class FabricInfo extends SimpleDatagram<FabricInfoBean> {

    public FabricInfo() {
        super(408);
    }

    @Override
    public FabricInfoBean toObject() {
        FabricInfoBean fabricInfo = new FabricInfoBean();
        buffer.clear();
        fabricInfo.setNumHFIs(uint2long(buffer.getInt()));
        fabricInfo.setNumSwitches(uint2long(buffer.getInt()));
        fabricInfo.setNumInternalHFILinks(uint2long(buffer.getInt()));
        fabricInfo.setNumExternalHFILinks(uint2long(buffer.getInt()));
        fabricInfo.setNumInternalISLs(uint2long(buffer.getInt()));
        fabricInfo.setNumExternalISLs(uint2long(buffer.getInt()));
        fabricInfo.setNumDegradedHFILinks(uint2long(buffer.getInt()));
        fabricInfo.setNumDegradedISLs(uint2long(buffer.getInt()));
        fabricInfo.setNumOmittedHFILinks(uint2long(buffer.getInt()));
        fabricInfo.setNumOmittedISLs(uint2long(buffer.getInt()));
        return fabricInfo;
    }

    private static long uint2long(int val) {
        return val & 0x00000000ffffffffL;
    }

}
