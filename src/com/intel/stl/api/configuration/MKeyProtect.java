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
 *  File Name: MKeyProtect.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.2  2015/01/21 22:51:00  jijunwan
 *  Archive Log:    improved to throw exception when we encounter unsupported value. This will help us identify problems when it happens.
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/08/22 16:39:46  fernande
 *  Archive Log:    Adding more properties for display
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: fernande
 *
 ******************************************************************************/

package com.intel.stl.api.configuration;

import com.intel.stl.api.StringUtils;

public enum MKeyProtect {

    READONLY((byte) 0),
    HIDE((byte) 1),
    SECURE((byte) 2);

    private final byte code;

    private MKeyProtect(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static MKeyProtect getMKeyProtect(byte code) {
        for (MKeyProtect key : MKeyProtect.values()) {
            if (key.code == code) {
                return key;
            }
        }
        throw new IllegalArgumentException("Unsupported MKeyProtect "
                + StringUtils.byteHexString(code));

    }
}
