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

package com.intel.stl.configuration;

import com.intel.stl.api.notice.impl.NoticeProcess;
import com.intel.stl.api.performance.impl.GroupCache;
import com.intel.stl.api.performance.impl.GroupConfCache;
import com.intel.stl.api.performance.impl.PAHelper;
import com.intel.stl.api.subnet.impl.CableCache;
import com.intel.stl.api.subnet.impl.LFTCache;
import com.intel.stl.api.subnet.impl.LinkCache;
import com.intel.stl.api.subnet.impl.MFTCache;
import com.intel.stl.api.subnet.impl.NodeCache;
import com.intel.stl.api.subnet.impl.PKeyTableCache;
import com.intel.stl.api.subnet.impl.PortCache;
import com.intel.stl.api.subnet.impl.SAHelper;
import com.intel.stl.api.subnet.impl.SC2SLMTCache;
import com.intel.stl.api.subnet.impl.SC2VLNTMTCache;
import com.intel.stl.api.subnet.impl.SC2VLTMTCache;
import com.intel.stl.api.subnet.impl.SMCache;
import com.intel.stl.api.subnet.impl.SwitchCache;
import com.intel.stl.api.subnet.impl.VLArbTableCache;
import com.intel.stl.datamanager.DatabaseManager;

public interface CacheManager {

    public static final String TOPOLOGY_UPDATE_TASK = "TopologyUpdate";

    SerialProcessingService getProcessingService();

    void startTopologyUpdateTask();

    DatabaseManager getDatabaseManager();

    NodeCache acquireNodeCache();

    LinkCache acquireLinkCache();

    PortCache acquirePortCache();

    SwitchCache acquireSwitchCache();

    LFTCache acquireLFTCache();

    MFTCache acquireMFTCache();

    PKeyTableCache acquirePKeyTableCache();

    VLArbTableCache acquireVLArbTableCache();

    SMCache acquireSMCache();

    CableCache acquireCableCache();

    GroupCache acquireGroupCache();

    GroupConfCache acquireGroupConfCache();

    SC2SLMTCache acquireSC2SLMTCache();

    SC2VLTMTCache acquireSC2VLTMTCache();

    SC2VLNTMTCache acquireSC2VLNTMTCache();

    SAHelper getSAHelper();

    PAHelper getPAHelper();

    void updateCaches(NoticeProcess notice) throws Exception;

    void reset();

    void cleanup();
}
