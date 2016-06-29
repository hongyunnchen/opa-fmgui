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

package com.intel.stl.ui.event;

import java.util.ArrayList;
import java.util.List;

public class PortsSelectedEvent extends JumpToEvent {
    private final List<Item> ports = new ArrayList<Item>();

    /**
     * Description:
     * 
     * @param origin
     * @param destination
     */
    public PortsSelectedEvent(Object origin, String destination) {
        super(origin, destination);
    }

    public PortsSelectedEvent(int lid, short portNum, Object origin,
            String destination) {
        super(origin, destination);
        addPort(lid, portNum);
    }

    public int numberOfPorts() {
        return ports.size();
    }

    public void addPort(int lid, short portNum) {
        ports.add(new Item(lid, portNum));
    }

    public int getLid(int index) {
        return ports.get(index).getLid();
    }

    public short getPortNum(int index) {
        return ports.get(index).getPortNum();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((ports == null) ? 0 : ports.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PortsSelectedEvent other = (PortsSelectedEvent) obj;
        if (ports == null) {
            if (other.ports != null) {
                return false;
            }
        } else if (!ports.equals(other.ports)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PortsSelectedEvent [ports=" + ports + "]";
    }

    protected static class Item {
        private final int lid;

        private final short portNum;

        /**
         * Description:
         * 
         * @param lid
         * @param portNum
         */
        public Item(int lid, short portNum) {
            super();
            this.lid = lid;
            this.portNum = portNum;
        }

        /**
         * @return the lid
         */
        public int getLid() {
            return lid;
        }

        /**
         * @return the portNum
         */
        public short getPortNum() {
            return portNum;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + lid;
            result = prime * result + portNum;
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Item other = (Item) obj;
            if (lid != other.lid) {
                return false;
            }
            if (portNum != other.portNum) {
                return false;
            }
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Item [lid=" + lid + ", portNum=" + portNum + "]";
        }

    }
}
