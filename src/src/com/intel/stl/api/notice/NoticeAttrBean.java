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

/*******************************************************************************
 *                       I N T E L   C O R P O R A T I O N
 *	
 *  Functional Group: Fabric Viewer Application
 *
 *  File Name: NoticeAttrBean.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.4  2015/08/17 18:48:43  jijunwan
 *  Archive Log:    PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log:    - change backend files' headers
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2014/08/22 21:37:44  fernande
 *  Archive Log:    Adding support for saving notices and imageinfos to the database
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2014/08/12 20:07:42  jijunwan
 *  Archive Log:    1) renamed HexUtils to StringUtils
 *  Archive Log:    2) added a method to StringUtils to get error message for an exception
 *  Archive Log:    3) changed all code to call StringUtils to get error message
 *  Archive Log:    4) some extra ode format change
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/05/06 15:14:03  jijunwan
 *  Archive Log:    notice and trap implementation
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2014/04/16 16:16:47  jijunwan
 *  Archive Log:    moved sharable class to com.intel.stl.api
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/04/16 15:07:33  jijunwan
 *  Archive Log:    added support to FE notice
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.api.notice;

import java.io.Serializable;

public abstract class NoticeAttrBean implements Serializable {
    private static final long serialVersionUID = 5221468130747416859L;

    private boolean isGeneric;

    private byte type;

    /**
     * @return the isGeneric
     */
    public boolean isGeneric() {
        return isGeneric;
    }

    /**
     * @param isGeneric
     *            the isGeneric to set
     */
    public void setGeneric(boolean isGeneric) {
        this.isGeneric = isGeneric;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }
}