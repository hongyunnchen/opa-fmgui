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

package com.intel.stl.ui.performance.observer;

import java.util.Date;

import com.intel.stl.api.performance.GroupInfoBean;
import com.intel.stl.api.performance.UtilStatsBean;
import com.intel.stl.ui.model.DataType;
import com.intel.stl.ui.performance.GroupSource;
import com.intel.stl.ui.performance.PmaFailureGroupSource;
import com.intel.stl.ui.performance.TopoFailureGroupSource;
import com.intel.stl.ui.performance.item.TrendItem;

public abstract class UtilTrendDataObserver
        extends AbstractDataObserver<GroupInfoBean[], TrendItem<GroupSource>> {

    public UtilTrendDataObserver(TrendItem<GroupSource> controller) {
        this(controller, DataType.ALL);
    }

    /**
     * Description:
     *
     * @param controller
     * @param type
     */
    public UtilTrendDataObserver(TrendItem<GroupSource> controller,
            DataType type) {
        super(controller, type);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.intel.stl.ui.common.performance.IDataObserver#processData(java.lang
     * .Object)
     */
    @Override
    public void processData(GroupInfoBean[] data) {
        if (data == null || data.length == 0) {
            return;
        }

        for (GroupInfoBean bean : data) {
            if (bean == null) {
                continue;
            }

            Date time = bean.getTimestampDate();
            UtilStatsBean[] utils = getUtilStatsBeans(bean, type);
            long value = 0;
            long pmaFailure = 0;
            long topoFailure = 0;
            for (UtilStatsBean util : utils) {
                value += getValue(util);
                pmaFailure += util.getPmaFailedPorts();
                topoFailure += util.getTopoFailedPorts();
            }

            GroupSource sourceName = new GroupSource(bean.getGroupName());
            controller.updateTrend(value, time, sourceName);
            if (pmaFailure != 0) {
                PmaFailureGroupSource pfSource =
                        new PmaFailureGroupSource(sourceName);
                controller.updateTrend(pmaFailure, time, pfSource);
            }
            if (topoFailure != 0) {
                TopoFailureGroupSource tfSource =
                        new TopoFailureGroupSource(sourceName);
                controller.updateTrend(topoFailure, time, tfSource);
            }
        }
    }

    protected abstract long getValue(UtilStatsBean util);

}
