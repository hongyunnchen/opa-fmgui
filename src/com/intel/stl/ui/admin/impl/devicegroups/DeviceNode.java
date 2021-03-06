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

package com.intel.stl.ui.admin.impl.devicegroups;

import java.util.Vector;

import com.intel.stl.ui.monitor.TreeNodeType;
import com.intel.stl.ui.monitor.tree.FVResourceNode;

public class DeviceNode extends FVResourceNode {
    private final long guid;

    private int selectCount;

    /**
     * Description:
     * 
     * @param pTitle
     * @param pType
     * @param id
     */
    public DeviceNode(String pTitle, TreeNodeType pType, int id, long guid) {
        super(pTitle, pType, id);
        this.guid = guid;
    }

    /**
     * @return the guid
     */
    public long getGuid() {
        return guid;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return selectCount > 0;
    }

    /**
     * @param isSelected
     *            the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        if (isSelected) {
            selectCount += 1;
        } else {
            selectCount -= 1;
        }
        Vector<FVResourceNode> children = getChildren();
        for (FVResourceNode child : children) {
            ((DeviceNode) child).setSelected(isSelected);
        }
    }

    public void clearSelection() {
        selectCount = 0;
        Vector<FVResourceNode> children = getChildren();
        for (FVResourceNode child : children) {
            ((DeviceNode) child).clearSelection();
        }
    }
}
