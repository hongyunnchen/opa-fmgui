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

package com.intel.stl.ui.monitor;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.intel.stl.ui.main.view.DataRateChartRangeUpdater;
import com.intel.stl.ui.main.view.IChartRangeUpdater;

public class DataRateChartScaleGroupManager extends
        ChartScaleGroupManager<TimeSeriesCollection> {
    private final static DataRateChartScaleGroupManager instance =
            new DataRateChartScaleGroupManager();

    public static DataRateChartScaleGroupManager getInstance() {
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.ui.monitor.ChartScaleGroupManager#getChartRangeUpdater()
     */
    @Override
    IChartRangeUpdater getChartRangeUpdater() {
        if (rangeUpdater == null) {
            rangeUpdater = new DataRateChartRangeUpdater();
        }
        return rangeUpdater;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.ui.monitor.ChartScaleGroupManager#getMin(org.jfree.data
     * .general.Dataset)
     */
    @Override
    long[] getMinMax(TimeSeriesCollection dataset) {
        long lower = Long.MAX_VALUE;
        long upper = Long.MIN_VALUE;

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            TimeSeries series = dataset.getSeries(i);

            lower = (long) Math.min(lower, series.getMinY());
            upper = (long) Math.max(upper, series.getMaxY());
        }

        return new long[] { lower, upper };
    }

}
