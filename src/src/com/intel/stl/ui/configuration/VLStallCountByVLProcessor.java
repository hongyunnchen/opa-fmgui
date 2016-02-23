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
 *  File Name: VLStallCountByVLProcessor.java
 * 
 *  Archive Source: $Source$
 * 
 *  Archive Log: $Log$
 *  Archive Log: Revision 1.3  2015/08/17 18:53:50  jijunwan
 *  Archive Log: PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log: - changed frontend files' headers
 *  Archive Log:
 *  Archive Log: Revision 1.2  2015/06/10 19:58:54  jijunwan
 *  Archive Log: PR 129120 - Some old files have no proper file header. They cannot record change logs.
 *  Archive Log: - wrote a tool to check and insert file header
 *  Archive Log: - applied on backend files
 *  Archive Log:
 * 
 *  Overview:
 * 
 *  @author: jypak
 * 
 ******************************************************************************/
package com.intel.stl.ui.configuration;

import static com.intel.stl.ui.model.DeviceProperty.NUM_VL;
import static com.intel.stl.ui.model.DeviceProperty.VL_STALL_COUNT;

import java.util.ArrayList;
import java.util.List;

import com.intel.stl.api.subnet.PortInfoBean;
import com.intel.stl.api.subnet.SAConstants;
import com.intel.stl.ui.model.DevicePropertyCategory;
import com.intel.stl.ui.model.DevicePropertyItem;

public class VLStallCountByVLProcessor extends BaseCategoryProcessor {

    @Override
    public void process(ICategoryProcessorContext context,
            DevicePropertyCategory category) {
        PortInfoBean portInfo = context.getPortInfo();
        if (portInfo != null) {
            getVLStallCount(category, portInfo.getVlStallCount());
        }

    }

    private void getVLStallCount(DevicePropertyCategory category,
            byte[] vlStallCount) {
        List<Double> vlStallSeries = new ArrayList<Double>();

        for (int i = 0; i < vlStallCount.length; i++) {
            String doubleValue = dec((short) (vlStallCount[i] & 0xff));
            vlStallSeries.add(Double.parseDouble(doubleValue));
        }

        double[] series = new double[vlStallSeries.size()];
        for (int i = 0; i < vlStallSeries.size(); i++) {
            series[i] = vlStallSeries.get(i);
        }

        DevicePropertyItem property =
                new DevicePropertyItem(VL_STALL_COUNT, series);
        category.addPropertyItem(property);

        DevicePropertyItem numVLs =
                new DevicePropertyItem(NUM_VL, new Integer(
                        SAConstants.STL_MAX_VLS));
        category.addPropertyItem(numVLs);
    }

}