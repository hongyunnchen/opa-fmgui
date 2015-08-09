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

/*******************************************************************************
 *                       I N T E L   C O R P O R A T I O N
 *	
 *  Functional Group: Fabric Viewer Application
 *
 *  File Name: ShowRoutesTask.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.6  2015/04/28 14:00:33  jijunwan
 *  Archive Log:    1) improved topology viz to use TopGraph copy for outline display. This will avoid graph and outline views share internal graph view that may cause sync issues.
 *  Archive Log:    2) added more debug info in log
 *  Archive Log:
 *  Archive Log:    Revision 1.5  2015/04/10 20:20:32  fernande
 *  Archive Log:    Changed TopologyView to be passed two background services (graphService and outlineService) which now reside in FabricController and can be properly shutdown when an error occurs.
 *  Archive Log:
 *  Archive Log:    Revision 1.4  2015/02/05 17:10:28  jijunwan
 *  Archive Log:    fixed NPE issues found by klocwork
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2014/12/11 18:47:34  fernande
 *  Archive Log:    Switch from log4j to slf4j+logback
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2014/11/03 23:06:13  jijunwan
 *  Archive Log:    improvement on topology view - drawing graph on background
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/10/22 02:21:25  jijunwan
 *  Archive Log:    1) moved update tasks into task package
 *  Archive Log:    2) added topology summary panel
 *  Archive Log:    3) improved models to be able to calculate ports distribution, nodes not in fat tree etc.
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.ui.network.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intel.stl.api.StringUtils;
import com.intel.stl.api.subnet.ISubnetApi;
import com.intel.stl.api.subnet.NodeRecordBean;
import com.intel.stl.api.subnet.TraceRecordBean;
import com.intel.stl.ui.common.ICancelIndicator;
import com.intel.stl.ui.model.GraphEdge;
import com.intel.stl.ui.model.GraphNode;
import com.intel.stl.ui.model.LayoutType;
import com.intel.stl.ui.monitor.tree.FVResourceNode;
import com.intel.stl.ui.network.IModelChange;
import com.intel.stl.ui.network.LayoutChange;
import com.intel.stl.ui.network.TopGraph;
import com.intel.stl.ui.network.TopologyGraphController;
import com.intel.stl.ui.network.TopologyTreeModel;
import com.mxgraph.model.mxCell;

public class ShowRoutesTask extends TopologyUpdateTask {
    private static final Logger log = LoggerFactory
            .getLogger(ShowRoutesTask.class);

    private static final boolean DEBUG = true;

    private final List<GraphNode> nodes;

    private TopologyTreeModel tmpTreeMode;

    private Map<GraphEdge, List<GraphEdge>> traceMap;

    private List<mxCell> edges;

    private final LayoutType defaultLayout = LayoutType.TREE_SLASH;

    /**
     * Description:
     * 
     * @param controller
     * @param source
     * @param selectedResources
     * @param nodes
     */
    public ShowRoutesTask(TopologyGraphController controller, Object source,
            FVResourceNode[] selectedResources, List<GraphNode> nodes) {
        super(controller, source, selectedResources);
        this.nodes = nodes;
        setIncludeNeighbors(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.ui.network.TopologyUpdateTask#getNodes(com.intel
     * .stl.ui.common.ICancelIndicator)
     */
    @Override
    protected Collection<Integer> getInvolvedNodes(ICancelIndicator indicator,
            TopGraph oldGraph) {
        traceMap = getTraceMap(nodes, indicator);
        if (indicator != null && indicator.isCancelled()) {
            throw new CancellationException();
        }
        resourceController.showPath(traceMap);
        if (traceMap == null) {
            return Collections.emptyList();
        }

        Map<Integer, Set<Integer>> links = getUniqueLinks(traceMap);
        edges = new ArrayList<mxCell>();
        Set<Integer> involvedNodes = new HashSet<Integer>();
        for (int fromLid : links.keySet()) {
            Set<Integer> nbr = links.get(fromLid);
            for (int toLid : nbr) {
                if (indicator != null && indicator.isCancelled()) {
                    throw new CancellationException();
                }
                mxCell cell = oldGraph.getEdge(fromLid, toLid);
                edges.add(cell);
            }
            involvedNodes.add(fromLid);
            involvedNodes.addAll(nbr);
        }
        return involvedNodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.ui.network.TopologyUpdateTask#applyChanges(com.
     * intel.stl.ui.common.ICancelIndicator, com.intel.stl.ui.network.TopGraph)
     */
    @Override
    public boolean applyChanges(ICancelIndicator indicator, TopGraph newGraph) {
        tmpTreeMode = controller.getFullTopTreeModel().filterBy(newGraph);
        IModelChange change = new LayoutChange(defaultLayout, tmpTreeMode);
        change.execute(newGraph, indicator);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intel.stl.ui.network.TopologyUpdateTask#onDone(com.intel.
     * stl.ui.common.ICancelIndicator, com.intel.stl.ui.network.TopGraph)
     */
    @Override
    public void onSuccess(final ICancelIndicator indicator, TopGraph newGraph) {
        super.onSuccess(indicator, newGraph);

        controller.setTopTreeModel(tmpTreeMode);

        FVResourceNode[] selectedNodes = selectedResources;
        if (!indicator.isCancelled() && source != controller) {
            selectedNodes = controller.selectTreeNodes(nodes, indicator);
        }
        final mxCell[] cells = new mxCell[nodes.size()];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = newGraph.getVertex(nodes.get(i).getLid());
        }

        submitGraphTask(new Runnable() {
            @Override
            public void run() {
                graphView.clearEdges();

                graphView.selectEdges(edges, true);

                if (!indicator.isCancelled()) {
                    graphView.selectNodes(cells);
                }

                graphView.updateGraph();
            }
        });

        final FVResourceNode[] nodes = selectedNodes;
        submitOutlineTask(new Runnable() {
            @Override
            public void run() {
                guideView.clearEdges();

                guideView.selectEdges(edges, true);

                if (!indicator.isCancelled()) {
                    guideView.selectNodes(cells);
                }

                guideView.updateGraph();
            }
        });

        guideView.setSelectedResources(nodes);
        setLayout(defaultLayout);
    }

    protected Map<GraphEdge, List<GraphEdge>> getTraceMap(
            List<GraphNode> nodes, ICancelIndicator indicator) {
        ISubnetApi subnetApi = controller.getSubnetApi();
        Map<GraphEdge, List<GraphEdge>> traceMap =
                new LinkedHashMap<GraphEdge, List<GraphEdge>>();

        for (int i = 0; i < nodes.size() - 1; i++) {
            GraphNode source = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                if (indicator.isCancelled()) {
                    log.info("getTraceMap cancelled " + nodes);
                    return null;
                }
                GraphNode target = nodes.get(j);
                List<TraceRecordBean> trace =
                        subnetApi.getTrace(source.getLid(), target.getLid());
                if (trace == null) {
                    log.warn("No trace found on link Lid "
                            + StringUtils.intHexString(source.getLid())
                            + " - Lid "
                            + StringUtils.intHexString(target.getLid()));
                    continue;
                }

                if (DEBUG) {
                    System.out.println(source + " -> " + target);
                }
                GraphEdge edge =
                        new GraphEdge(source.getLid(), target.getLid(),
                                source.getLinkPorts(target));

                List<GraphEdge> edges =
                        new ArrayList<GraphEdge>(trace.size() - 1);
                TraceRecordBean start = null;
                NodeRecordBean startNode = null;
                for (TraceRecordBean bean : trace) {
                    if (indicator.isCancelled()) {
                        return null;
                    }
                    NodeRecordBean node = null;
                    try {
                        node = subnetApi.getNode(bean.getNodeId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    if (start == null) {
                        start = bean;
                        startNode = node;
                    } else {
                        GraphEdge nextEdge =
                                new GraphEdge(startNode.getLid(),
                                        node.getLid(),
                                        Collections.singletonMap(Integer
                                                .valueOf(start.getExitPort()),
                                                Integer.valueOf(bean
                                                        .getEntryPort())));
                        edges.add(nextEdge);
                        if (DEBUG) {
                            System.out
                                    .println("  " + startNode.getNodeDesc()
                                            + "-" + node.getNodeDesc() + " "
                                            + nextEdge);
                        }
                        start = bean;
                        startNode = node;
                    }
                }

                traceMap.put(edge, edges);
            }
        }
        return traceMap;
    }

    protected Map<Integer, Set<Integer>> getUniqueLinks(
            Map<GraphEdge, List<GraphEdge>> traceMap) {
        Map<Integer, Set<Integer>> res = new HashMap<Integer, Set<Integer>>();
        for (List<GraphEdge> trace : traceMap.values()) {
            for (GraphEdge edge : trace) {
                GraphEdge tmp = edge.normalize();
                Set<Integer> nbr = res.get(tmp.getFromLid());
                if (nbr == null) {
                    nbr = new HashSet<Integer>();
                    res.put(tmp.getFromLid(), nbr);
                }
                nbr.add(tmp.getToLid());
            }
        }
        return res;
    }
}
