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
 *  File Name: TreeNodeFactory.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.5  2015/02/05 21:21:44  jijunwan
 *  Archive Log:    fixed NPE issues found by klocwork
 *  Archive Log:
 *  Archive Log:    Revision 1.4  2014/10/22 01:37:31  jijunwan
 *  Archive Log:    use plura name for a tree node's name
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2014/09/03 18:10:26  jijunwan
 *  Archive Log:    new Tree Updaters
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2014/09/02 19:24:28  jijunwan
 *  Archive Log:    renamed FVTreeBuilder to tree.FVTreeManager, moved FVResourceNode and FVTreeModel  to package tree
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/09/02 19:02:59  jijunwan
 *  Archive Log:    tree update based on merge sort algorithm
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.ui.monitor.tree;

import java.util.Comparator;
import java.util.Map;

import com.intel.stl.api.subnet.NodeRecordBean;
import com.intel.stl.api.subnet.NodeType;
import com.intel.stl.ui.common.NameSorter;
import com.intel.stl.ui.model.NodeTypeViz;
import com.intel.stl.ui.monitor.TreeNodeType;

public class TreeNodeFactory {
    public static FVResourceNode createTypeNode(TreeNodeType type) {
        if (type == TreeNodeType.HCA_GROUP) {
            return new FVResourceNode(NodeTypeViz.HFI.getPluralName(),
                    TreeNodeType.HCA_GROUP, TreeNodeType.HCA_GROUP.ordinal());
        } else if (type == TreeNodeType.SWITCH_GROUP) {
            return new FVResourceNode(NodeTypeViz.SWITCH.getPluralName(),
                    TreeNodeType.SWITCH_GROUP,
                    TreeNodeType.SWITCH_GROUP.ordinal());
        } else if (type == TreeNodeType.ROUTER_GROUP) {
            return new FVResourceNode(NodeTypeViz.ROUTER.getName(),
                    TreeNodeType.ROUTER_GROUP,
                    TreeNodeType.ROUTER_GROUP.ordinal());
        } else {
            throw new IllegalArgumentException("Unsupported Device Type "
                    + type);
        }
    }

    public static FVResourceNode createGroupNode(String groupName, int id) {
        return new FVResourceNode(groupName, TreeNodeType.DEVICE_GROUP, id);
    }

    public static FVResourceNode createVfNode(String groupName, int id) {
        return new FVResourceNode(groupName, TreeNodeType.VIRTUAL_FABRIC, id);
    }

    public static FVResourceNode createNode(NodeRecordBean bean) {
        NodeType type = bean.getNodeType();

        // Create the appropriate node depending on the type
        if (type == NodeType.HFI) {
            return new FVResourceNode(bean.getNodeDesc(), TreeNodeType.HFI,
                    bean.getLid());
        } else if (type == NodeType.SWITCH) {
            return new FVResourceNode(bean.getNodeDesc(), TreeNodeType.SWITCH,
                    bean.getLid());
        } else if (type == NodeType.ROUTER) {
            return new FVResourceNode(bean.getNodeDesc(), TreeNodeType.ROUTER,
                    bean.getLid());
        } else {
            throw new RuntimeException("Unknown node type " + type);
        }
    }

    public static Comparator<FVResourceNode> getTypeNodeComparator() {
        return new Comparator<FVResourceNode>() {
            @Override
            public int compare(FVResourceNode node1, FVResourceNode node2) {
                // sorting by TreeNodeType enum. We should have a order of
                // HCA_GROUP, SWITCH_GROUP, ROUTER_GROUP
                TreeNodeType type1 = node1.getType();
                TreeNodeType type2 = node2.getType();
                return comapreTreeNodeType(type1, type2);
            }
        };
    }

    public static int comapreTreeNodeType(TreeNodeType type1, TreeNodeType type2) {
        int o1 = type1.ordinal();
        int o2 = type2.ordinal();
        return o1 > o2 ? 1 : (o1 < o2 ? -1 : 0);
    }

    public static Comparator<FVResourceNode> getGroupNodeComparator(
            final Map<String, Integer> groupIndices) {
        return new Comparator<FVResourceNode>() {
            @Override
            public int compare(FVResourceNode node1, FVResourceNode node2) {
                return compareNameByIndex(node1.getName(), node2.getName(),
                        groupIndices);
            }
        };
    }

    public static Comparator<FVResourceNode> getVfNodeComparator(
            final Map<String, Integer> vfIndices) {
        return new Comparator<FVResourceNode>() {

            @Override
            public int compare(FVResourceNode node1, FVResourceNode node2) {
                return compareNameByIndex(node1.getName(), node2.getName(),
                        vfIndices);
            }

        };
    }

    public static int compareNameByIndex(String name1, String name2,
            Map<String, Integer> nameIndices) {
        Integer o1 = nameIndices.get(name1);
        Integer o2 = nameIndices.get(name2);
        if (o1 == null) {
            return o1 == null ? 0 : -1;
        } else if (o2 == null) {
            return -1;
        }

        return o1 > o2 ? 1 : (o1 < o2 ? -1 : 0);
    }

    public static Comparator<FVResourceNode> getNodeComparator() {
        return new Comparator<FVResourceNode>() {
            @Override
            public int compare(FVResourceNode o1, FVResourceNode o2) {
                if (o1 == null) {
                    return o2 == null ? 0 : -1;
                } else if (o2 == null) {
                    return 1;
                } else {
                    return comapreNodeName(o1.getTitle(), o2.getTitle());
                }
            }
        };
    }

    public static int comapreNodeName(String name1, String name2) {
        return NameSorter.instance().compare(name1, name2);
    }

    public static TreeNodeType getTreeNodeType(NodeType type) {
        if (type == NodeType.HFI) {
            return TreeNodeType.HCA_GROUP;
        } else if (type == NodeType.SWITCH) {
            return TreeNodeType.SWITCH_GROUP;
        } else if (type == NodeType.ROUTER) {
            return TreeNodeType.ROUTER_GROUP;
        } else {
            throw new IllegalArgumentException("Unsupported NodeType " + type);
        }
    }
}
