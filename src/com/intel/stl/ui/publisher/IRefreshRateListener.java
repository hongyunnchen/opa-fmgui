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
 *  File Name: IRefreshRateChangeListener.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.2  2015/02/06 15:52:22  robertja
 *  Archive Log:    PR 126598 Add periodic refresh of Health Score, based on refresh rate, in the absence of event-driven updates.
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2015/02/05 18:04:11  robertja
 *  Archive Log:    Interface for notification of refresh rate change.
 *  Archive Log:
 *
 *  Overview: Mechanism for taking action on refresh rate change.
 *
 *  @author: robertja
 *
 ******************************************************************************/
package com.intel.stl.ui.publisher;


/**
 * @author robertja
 * 
 * Interface to provide notification of refresh rate changes.
 *
 */
public interface IRefreshRateListener {

/**
 * Description: Action(s) taken by listener on refresh rate change.
 * 
 */
public void onRefreshRateChange(int newRate);

@Override
public String toString();

}
