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
 *  File Name: ISubnetWizardListener.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.8  2015/08/17 18:54:04  jijunwan
 *  Archive Log:    PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log:    - changed frontend files' headers
 *  Archive Log:
 *  Archive Log:    Revision 1.7  2015/03/30 15:11:55  rjtierne
 *  Archive Log:    Passing host info to connectActionPerformed() so a new subnet can be created
 *  Archive Log:
 *  Archive Log:    Revision 1.6  2015/03/20 21:06:26  rjtierne
 *  Archive Log:    Added getSubnetModel() and updateModel()
 *  Archive Log:
 *  Archive Log:    Revision 1.5  2015/02/13 21:31:58  rjtierne
 *  Archive Log:    Multinet Wizard
 *  Archive Log:
 *  Archive Log:    Revision 1.4  2015/02/06 15:12:54  fernande
 *  Archive Log:    Changes so that the Setup Wizard depends on the Subnet Manager for all subnet-related operations
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2015/01/21 21:21:20  rjtierne
 *  Archive Log:    Supplying preferences wizard with sweep interval through Context
 *  Archive Log:    for comparison with refresh rate supplied by user input. Also providing
 *  Archive Log:    task scheduler to preferences wizard so user supplied refresh rate can
 *  Archive Log:    be updated.
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2015/01/11 21:48:15  jijunwan
 *  Archive Log:    setup wizard improvements
 *  Archive Log:    1) look and feel adjustment
 *  Archive Log:    2) secure FE support
 *  Archive Log:    3) apply wizard on current subnet
 *  Archive Log:    4) message display based on message type rather than directly specifying UI resources
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/12/10 21:31:02  rjtierne
 *  Archive Log:    New Setup Wizard based on framework
 *  Archive Log:
 *
 *  Overview: Customized interface for the Subnet Wizard
 *
 *  @author: rjtierne
 *
 ******************************************************************************/
package com.intel.stl.ui.wizards.impl.subnet;

import com.intel.stl.api.subnet.HostInfo;
import com.intel.stl.api.subnet.SubnetDescription;
import com.intel.stl.ui.wizards.model.subnet.SubnetModel;

public interface ISubnetControl {

    public void connectActionPerformed(HostInfo hostInfo);

    public void addSubnetListener(ISubnetListener listener);

    public SubnetDescription getSubnet();

    public SubnetDescription getNewSubnet();

    public SubnetModel getSubnetModel();

    public void updateModel();
}