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

package com.intel.stl.ui.alert;

import java.util.ArrayList;
import java.util.List;

import com.intel.stl.api.configuration.EventType;
import com.intel.stl.api.notice.EventDescription;

public abstract class EventProcesser implements IEventObserver {
    private EventType[] targetTypes;

    private final List<EventDescription> targetEvents =
            new ArrayList<EventDescription>();

    /**
     * @param targetTypes
     *            the targetTypes to set
     */
    public void setTargetTypes(EventType... targetTypes) {
        this.targetTypes = targetTypes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.intel.stl.ui.alert.IEventObserver#onEvent(com.intel.stl.api.notice
     * .EventDescription[])
     */
    @Override
    public void onEvents(EventDescription[] events) {
        targetEvents.clear();
        for (EventDescription event : events) {
            if (isTarget(event.getType())) {
                targetEvents.add(event);
            }
        }
        try {
            if (!targetEvents.isEmpty()) {
                processEvents(targetEvents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishProcess();
    }

    protected boolean isTarget(EventType type) {
        for (EventType targetType : targetTypes) {
            if (targetType == type) {
                return true;
            }
        }
        return false;
    }

    protected abstract void processEvents(List<EventDescription> evts);

    protected abstract void finishProcess();

}
