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
 *  File Name: AbstrctConfView.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.13  2016/03/03 17:06:28  jijunwan
 *  Archive Log:    PR 133087 - Writing VFs on multiplane are written in common section
 *  Archive Log:
 *  Archive Log:    - improved login panel with note
 *  Archive Log:
 *  Archive Log:    Revision 1.12  2015/11/18 23:54:48  rjtierne
 *  Archive Log:    PR 130965 - ESM support on Log Viewer
 *  Archive Log:    - Disable host field for configuration tasks
 *  Archive Log:
 *  Archive Log:    Revision 1.11  2015/10/19 22:30:09  jijunwan
 *  Archive Log:    PR 131091 - On an unsuccessful Failover, the Admin | Applications doesn't show the login window
 *  Archive Log:    - show login panel when not initialized properly with corresponding message
 *  Archive Log:    - added feature to fully enable/disable a login panel
 *  Archive Log:
 *  Archive Log:    Revision 1.10  2015/09/28 17:54:14  fisherma
 *  Archive Log:    PR 130425 - added cancel button to the Admin tab login page to allow user to cancel out of hung or slow ssh logins.  Cancel action terminates sftp connection and closes remote ssh session. This fix also addresses PR 130386 and 130390.
 *  Archive Log:
 *  Archive Log:    Revision 1.9  2015/08/17 18:53:52  jijunwan
 *  Archive Log:    PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log:    - changed frontend files' headers
 *  Archive Log:
 *  Archive Log:    Revision 1.8  2015/08/17 17:43:16  jijunwan
 *  Archive Log:    PR 128973 - Deploy FM conf changes on all SMs
 *  Archive Log:    - improved AbstractConfView to support deploy card
 *  Archive Log:
 *  Archive Log:    Revision 1.7  2015/08/05 02:52:56  jijunwan
 *  Archive Log:    PR 129359 - Need navigation feature to navigate within FM GUI
 *  Archive Log:    - apply undo mechanism on Admin page to track tab selection
 *  Archive Log:    - improved ConfPageController to check change when we exit one tab
 *  Archive Log:
 *  Archive Log:    Revision 1.6  2015/07/28 18:29:09  fisherma
 *  Archive Log:    PR 129219 - Admin page login dialog improvement
 *  Archive Log:
 *  Archive Log:    Revision 1.5  2015/06/25 11:54:58  jypak
 *  Archive Log:    PR 129073 - Add help action for Admin Page.
 *  Archive Log:    The help action is added to App, DG, VF,Console page and Console terminal. For now, a help ID and a content are being used as a place holder for each page. Once we get the help contents delivered by technical writer team, the HelpAction will be updated with correct help ID.
 *  Archive Log:
 *  Archive Log:    Revision 1.4  2015/04/18 01:40:00  fisherma
 *  Archive Log:    PR 127653 - FM GUI errors after connection loss.  The code changes address issue #2 reported in the bug.  Adding common dialog to display errors.  Needs further appearance improvements.
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2015/03/11 21:16:04  jijunwan
 *  Archive Log:    added remove and deploy features
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2015/03/10 22:45:37  jijunwan
 *  Archive Log:    improved to do and show validation before we save an application
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2015/03/05 17:38:18  jijunwan
 *  Archive Log:    init version to support Application management
 *  Archive Log:
 *
 *  Overview:
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.ui.admin.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListModel;

import org.jdesktop.swingx.JXLabel;

import com.intel.stl.api.subnet.SubnetDescription;
import com.intel.stl.ui.admin.IConfListener;
import com.intel.stl.ui.admin.IItemListListener;
import com.intel.stl.ui.admin.Item;
import com.intel.stl.ui.common.STLConstants;
import com.intel.stl.ui.common.UIConstants;
import com.intel.stl.ui.common.UILabels;
import com.intel.stl.ui.common.Util;
import com.intel.stl.ui.common.view.ComponentFactory;

public abstract class AbstractConfView<T, E extends AbstractEditorPanel<T>>
        extends JPanel implements ILoginListener {
    private static final long serialVersionUID = 8561299073984852795L;

    private static final String DEPLOY = "deploy";

    private static final String LOGIN = "login";

    private final String mainCardName;

    private String currentCard = null;

    protected ItemListPanel<T> selectionPanel;

    protected E editorPanel;

    protected JPanel ctrPanel;

    protected JPanel mainPanel;

    private JPanel loginCardPanel;

    protected JButton deployBtn;

    private IConfListener listener;

    private LoginPanel loginPanel;

    private JPanel deployCardPanel;

    private DeployPanel deployPanel;

    /**
     * Description:
     *
     */
    public AbstractConfView(String name) {
        super();
        this.setLayout(new CardLayout());

        this.mainCardName = name;
        JPanel panel = getEditorCardPanel();
        addViewCard(panel, mainCardName);

        panel = getLoginCardPanel();
        addViewCard(panel, LOGIN);

        panel = getDeployCardPanel();
        addViewCard(panel, DEPLOY);

        setViewCard(mainCardName);
    }

    protected JPanel getEditorCardPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout(5, 5));

            JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            selectionPanel = createItemSelectionPanel();
            selectionPanel
                    .setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            pane.setLeftComponent(selectionPanel);

            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setOpaque(false);
            editorPanel = createrEditorPanel();
            editorPanel.setBorder(BorderFactory.createLineBorder(
                    UIConstants.INTEL_TABLE_BORDER_GRAY, 1, true));
            panel.add(editorPanel, BorderLayout.CENTER);

            ctrPanel = new JPanel();
            ctrPanel.setOpaque(false);
            installButtons(ctrPanel);
            panel.add(ctrPanel, BorderLayout.SOUTH);

            pane.setRightComponent(panel);

            mainPanel.add(pane, BorderLayout.CENTER);
        }
        return mainPanel;
    }

    private JPanel getLoginCardPanel() {
        if (loginCardPanel == null) {
            loginCardPanel = new JPanel(new FlowLayout());
            loginCardPanel.setBackground(UIConstants.INTEL_WHITE);
            loginPanel = new LoginPanel(this) {
                private static final long serialVersionUID =
                        7717352774226770775L;

                /*
                 * (non-Javadoc)
                 *
                 * @see com.intel.stl.ui.admin.view.LoginPanel#initLoginPanel()
                 */
                @Override
                protected void initLoginPanel() {
                    super.initLoginPanel();

                    gc.gridx = 0;
                    gc.gridy += 1;
                    gc.insets = new Insets(0, 10, 5, 10);
                    gc.gridwidth = GridBagConstraints.REMAINDER;
                    JPanel panel = createConfNotePanel();
                    add(panel, gc);

                    setPreferredSize(new Dimension(400, 430));
                }

            };
            loginCardPanel.add(loginPanel);
        }
        return loginCardPanel;
    }

    private JPanel createConfNotePanel() {

        JPanel pnlEsmNote = new JPanel(new BorderLayout());
        pnlEsmNote.setBorder(
                BorderFactory.createLineBorder(UIConstants.INTEL_GRAY));
        JXLabel lblEsmNote =
                new JXLabel(UILabels.STL50220_FM_CONFIG_NOTE.getDescription());
        lblEsmNote.setFont(UIConstants.H5_FONT.deriveFont(Font.BOLD));
        lblEsmNote.setForeground(UIConstants.INTEL_BLUE);
        lblEsmNote.setLineWrap(true);
        lblEsmNote.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlEsmNote.add(lblEsmNote, BorderLayout.CENTER);

        return pnlEsmNote;
    }

    private JPanel getDeployCardPanel() {
        if (deployCardPanel == null) {
            deployCardPanel = new JPanel(new BorderLayout());
            deployCardPanel.setBackground(UIConstants.INTEL_WHITE);
            deployPanel = new DeployPanel();
            deployCardPanel.add(deployPanel, BorderLayout.NORTH);
        }
        return deployCardPanel;
    }

    /**
     * @return the deployPanel
     */
    public DeployPanel getDeployPanel() {
        return deployPanel;
    }

    public void setLoginEnabled(boolean b) {
        loginPanel.setEnabled(b);
    }

    public void setHostNameField(String host) {
        loginPanel.setHostNameField(host);
    }

    public void setUserNameField(String userName) {
        loginPanel.setUserNameField(userName);
    }

    public String getUserNameFieldStr() {
        return loginPanel.getUserNameFieldStr();
    }

    public String getPortFieldStr() {
        return loginPanel.getPortFieldStr();
    }

    // Allow a different panel to be added to the card layout -
    // Right now this is used to show the login
    // credentials or other data which is required to be passed to the main
    // panel
    protected void addViewCard(Component card, String name) {
        this.add(card, name);
    }

    protected void setViewCard(String name) {
        CardLayout cl = (CardLayout) (this.getLayout());
        cl.show(this, name);

        repaint();
    }

    // Notification from the loginPanel that user has provided credentials
    // to be used to ssh to FM to obtain configuration file.
    @Override
    public void credentialsReady() {
        // Call to re-trigger initData
        // for now this is needed to load applications from the server with
        // credentials provided in the login panel card.
        if (listener != null) {
            listener.prepare(loginPanel.getCredentials());
        }
    }

    @Override
    public void cancelLogin() {
        if (listener != null) {
            listener.onCancelLogin();
        }
    }

    // Returns the name of the main card of this view
    @Override
    public String getName() {
        return mainCardName;
    }

    protected ItemListPanel<T> createItemSelectionPanel() {
        return new ItemListPanel<T>(mainCardName);
    }

    protected abstract E createrEditorPanel();

    protected void installButtons(JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        deployBtn = ComponentFactory
                .getIntelActionButton(STLConstants.K2131_DEPLOY.getValue());
        deployBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onApply(false);
            }
        });
        panel.add(deployBtn);

    }

    /**
     * @return the editorPanel
     */
    public E getEditorPanel() {
        return editorPanel;
    }

    public void enableHelp(boolean b) {
        editorPanel.enableHelp(b);
    }

    public JButton getHelpButton() {
        return editorPanel.getHelpButton();
    }

    public void addItemListListener(IItemListListener listener) {
        selectionPanel.addItemListListener(listener);
    }

    public void removeItemListListener(IItemListListener listener) {
        selectionPanel.removeItemListListener(listener);
    }

    public void setConfListener(IConfListener listener) {
        this.listener = listener;
    }

    public void setListModel(ListModel<Item<T>> model) {
        selectionPanel.setListModel(model);
        selectionPanel.repaint();
    }

    /**
     * <i>Description:</i>
     *
     * @param first
     */
    public void selectItem(int index) {
        selectionPanel.selectItem(index);
        revalidate();
        repaint();
    }

    /**
     * <i>Description:</i>
     *
     */
    public void updateItems() {
        selectionPanel.repaint();
    }

    /**
     * <i>Description:</i>
     *
     */
    public int confirmDiscard() {
        return Util.showConfirmDialog(this,
                UILabels.STL50081_ABANDON_CHANGES_MESSAGE.getDescription());
    }

    /*
     * Show login card
     */
    public void showLoginCard() {
        CardLayout cl = (CardLayout) (this.getLayout());
        loginPanel.showProgress(false);
        loginPanel.setMessage(null);
        loginPanel.setHostFieldEnabled(false);
        cl.show(this, LOGIN);
        currentCard = LOGIN;
        repaint();
    }

    public void showDeployCard(SubnetDescription subnet) {
        deployPanel.setSubnet(subnet);
        CardLayout cl = (CardLayout) (this.getLayout());
        cl.show(this, DEPLOY);
        currentCard = DEPLOY;
        repaint();
    }

    public boolean isShowingDeployCard() {
        return currentCard == DEPLOY;
    }

    /*
     * Show editor card
     */
    public void showEditorCard() {
        CardLayout cl = (CardLayout) (this.getLayout());
        cl.show(this, mainCardName);
        currentCard = mainCardName;
        repaint();
    }

    /**
     * @return the currentCard
     */
    public String getCurrentCard() {
        return currentCard;
    }

    /**
     * <i>Description:</i> Clear the password text field and the password data
     * saved in the LoginBean credentials.
     *
     */
    public void clearLoginCard() {
        loginPanel.clearLoginData();
    }

    //
    // Set message text to appear at the top of the login panel.
    //
    public void setMessage(String msg) {
        loginPanel.setMessage(msg);
    }

}
