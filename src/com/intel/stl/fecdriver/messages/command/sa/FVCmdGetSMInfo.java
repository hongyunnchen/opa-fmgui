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
package com.intel.stl.fecdriver.messages.command.sa;

import com.intel.stl.api.subnet.SAConstants;
import com.intel.stl.api.subnet.SMRecordBean;
import com.intel.stl.fecdriver.MultipleResponseCommand;
import com.intel.stl.fecdriver.messages.adapter.CommonMad;
import com.intel.stl.fecdriver.messages.adapter.sa.SAHeader;
import com.intel.stl.fecdriver.messages.adapter.sa.SMInfoRecord;
import com.intel.stl.fecdriver.messages.command.InputArgument;
import com.intel.stl.fecdriver.messages.response.sa.FVRspGetSMInfo;

/**
 */
public class FVCmdGetSMInfo extends SACommand<SMInfoRecord, SMRecordBean>
        implements MultipleResponseCommand<SMRecordBean, FVRspGetSMInfo> {

    public FVCmdGetSMInfo() {
        setResponse(new FVRspGetSMInfo());
        setInput(new InputArgument());
    }

    public FVCmdGetSMInfo(InputArgument input) {
        this();
        setInput(input);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.hpc.fv.message.command.sa.SACommand#fillCommonMad(com.intel
     * .hpc.fv.resource.stl.data.CommonMad)
     */
    @Override
    protected void fillCommonMad(CommonMad comm) {
        super.fillCommonMad(comm);
        comm.setAttributeID(SAConstants.STL_SA_ATTR_SMINFO_RECORD);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.hpc.fv.message.command.sa.SACommand#buildRecord()
     */
    @Override
    protected SMInfoRecord buildRecord() {
        SMInfoRecord rec = new SMInfoRecord();
        rec.build(true);
        return rec;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.hpc.fv.message.command.sa.SACommand#fillInput(com.intel.hpc
     * .fv.resource.stl.data.sa.SAHeader,
     * com.intel.hpc.fv.resource.stl.data.IDatagram)
     */
    @Override
    protected void fillInput(SAHeader header, SMInfoRecord record) {
        InputArgument input = getInput();

        switch (input.getType()) {
            case InputTypeNoInput:
                break;
            default:
                throw new IllegalArgumentException("Unsupported input type "
                        + input.getType());
        }
    }

}
