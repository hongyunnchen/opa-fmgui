/**
 * Copyright (c) 2016, Intel Corporation
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

package com.intel.stl.ui.performance;

import com.intel.stl.ui.common.UILabels;
import com.intel.stl.ui.common.Util;

public class PmaFailureGroupSource extends GroupSource {
    private static final UILabels GROUP_PATTERN =
            UILabels.STL10225_GROUP_PMA_FAILURE;

    private static final UILabels VF_PATTERN = UILabels.STL10227_VF_PMA_FAILURE;

    private final boolean isVF;

    /**
     * Description:
     *
     * @param group
     */
    public PmaFailureGroupSource(String group, boolean isVF) {
        super(isVF ? VF_PATTERN.getDescription(group)
                : GROUP_PATTERN.getDescription(group));
        this.isVF = isVF;
    }

    public PmaFailureGroupSource(GroupSource group) {
        this(group.getGroup(), group instanceof VFSource);
    }

    /**
     * @return the isVF
     */
    public boolean isVF() {
        return isVF;
    }

    public static boolean isPmaFailure(String name) {
        return Util.matchPattern(GROUP_PATTERN.getDescription(), name)
                || Util.matchPattern(VF_PATTERN.getDescription(), name);
    }
}
