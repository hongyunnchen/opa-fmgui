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
package com.intel.stl.ui.configuration.view;

import static com.intel.stl.ui.common.STLConstants.K1105_SC;
import static com.intel.stl.ui.common.STLConstants.K1106_SL;
import static com.intel.stl.ui.model.DeviceProperty.SC;
import static com.intel.stl.ui.model.DeviceProperty.SL;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.intel.stl.ui.common.view.ComponentFactory;
import com.intel.stl.ui.model.DevicePropertyCategory;

public class SC2SLMTBarChartPanel extends DevicePropertyCategoryPanel {

    private static final long serialVersionUID = 1L;

    private static final Dimension PREFERRED_CHART_SIZE = new Dimension(360,
            240);

    private XYSeriesCollection dataset;

    private ChartPanel chartPanel;

    @Override
    public void modelUpdateFailed(DevicePropertyCategory model, Throwable caught) {
    }

    @Override
    public void modelChanged(DevicePropertyCategory model) {
        double[] values = (double[]) model.getProperty(SL).getObject();

        int numSCs = (Integer) model.getProperty(SC).getObject();

        final XYSeries xyseries = new XYSeries("");

        int x = 0;
        for (double v : values) {

            xyseries.add(x++, v);
        }
        dataset.addSeries(xyseries);

        chartPanel.getChart().getXYPlot().getDomainAxis().setRange(0, numSCs);
    }

    @Override
    public void initComponents() {
        dataset = new XYSeriesCollection();

        JFreeChart chart =
                ComponentFactory.createXYBarChart(K1105_SC.getValue(),
                        K1106_SL.getValue(), dataset,
                        (XYItemLabelGenerator) null);

        XYPlot plot = chart.getXYPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        final String scLabel = "<html>" + K1105_SC.getValue() + ": ";
        final String slLabel = "<br>" + K1106_SL.getValue() + ": ";

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarAlignmentFactor(0);
        renderer.setMargin(0.2);

        renderer.setSeriesToolTipGenerator(0, new XYToolTipGenerator() {
            @Override
            public String generateToolTip(XYDataset dataset, int arg1, int arg2) {
                int scNum = (int) dataset.getXValue(arg1, arg2);
                int slCount = (int) dataset.getYValue(arg1, arg2);
                return scLabel + scNum + slLabel + slCount + "</html>";
            }
        });
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(PREFERRED_CHART_SIZE);
        propsPanel.add(chartPanel);
    }
}
