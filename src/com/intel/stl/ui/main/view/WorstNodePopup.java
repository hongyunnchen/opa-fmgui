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
 *  File Name: WorstNodesPopup.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.2  2014/10/23 16:33:21  jijunwan
 *  Archive Log:    minor change on timers - intend to improve timer behavior so the action will be cancelled event if it's already in EDT queue
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2014/09/18 15:00:48  jijunwan
 *  Archive Log:    Added popup window to WorstNode to support jumping to different destinations
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: jijunwan
 *
 ******************************************************************************/

package com.intel.stl.ui.main.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.Timer;

import org.jdesktop.swingx.JXHyperlink;

import com.intel.stl.api.StringUtils;
import com.intel.stl.ui.common.STLConstants;
import com.intel.stl.ui.common.UIConstants;
import com.intel.stl.ui.common.view.ComponentFactory;
import com.intel.stl.ui.event.JumpDestination;
import com.intel.stl.ui.model.NodeScore;

public class WorstNodePopup extends JPanel implements MouseListener {
    private static final long serialVersionUID = 5839148578351334245L;

    private JLabel header;

    private JPanel contentPanel;

    private JLabel lid;

    private JLabel score;

    private JXHyperlink[] jumpBtns;

    private IWorstNodesListener listener;

    private NodeScore nodeScore;

    private Popup popup;

    private Timer hideTimer;

    /**
     * Description:
     * 
     * @param score
     */
    public WorstNodePopup() {
        super();
        initComponent();
        addMouseListener(this);
    }

    /**
     * @param listener
     *            the listener to set
     */
    public void setListener(IWorstNodesListener listener) {
        this.listener = listener;
    }

    protected void initComponent() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(UIConstants.INTEL_BLUE));

        header = createHeader();
        add(header, BorderLayout.NORTH);

        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(UIConstants.INTEL_WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(2, 2, 2, 2);
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        gc.weightx = 0;
        JLabel label =
                ComponentFactory.getH5Label(STLConstants.K0026_LID.getValue(),
                        Font.BOLD);
        label.setHorizontalAlignment(JLabel.RIGHT);
        contentPanel.add(label, gc);

        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1;
        lid = ComponentFactory.getH5Label("", Font.PLAIN);
        contentPanel.add(lid, gc);

        gc.gridwidth = 1;
        gc.weightx = 0;
        label =
                ComponentFactory.getH5Label(
                        STLConstants.K0108_SCORE.getValue(), Font.BOLD);
        label.setHorizontalAlignment(JLabel.RIGHT);
        contentPanel.add(label, gc);

        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1;
        score = ComponentFactory.getH5Label("", Font.BOLD);
        contentPanel.add(score, gc);

        gc.gridwidth = 1;
        gc.gridheight = JumpDestination.values().length;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.NORTH;
        label =
                ComponentFactory.getH5Label(
                        STLConstants.K1055_INSPECT.getValue(), Font.BOLD);
        label.setHorizontalAlignment(JLabel.RIGHT);
        contentPanel.add(label, gc);

        gc.weightx = 1;
        gc.gridheight = 1;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.anchor = GridBagConstraints.CENTER;
        jumpBtns = new JXHyperlink[JumpDestination.values().length];
        for (int i = 0; i < jumpBtns.length; i++) {
            final JumpDestination dest = JumpDestination.values()[i];
            jumpBtns[i] = new JXHyperlink(new AbstractAction(dest.getName()) {
                private static final long serialVersionUID =
                        4612692450375442721L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nodeScore != null && listener != null) {
                        listener.jumpTo(nodeScore.getLid(),
                                nodeScore.getType(), dest);
                        hidePopup();
                    }
                }

            });
            contentPanel.add(jumpBtns[i], gc);
        }
        add(contentPanel, BorderLayout.CENTER);
    }

    protected JLabel createHeader() {
        JLabel header = ComponentFactory.getH4Label("", Font.BOLD);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
                UIConstants.INTEL_ORANGE));
        return header;
    }

    public void setNode(NodeScore nodeScore) {
        if (nodeScore == null) {
            return;
        }

        this.nodeScore = nodeScore;
        header.setText(nodeScore.getName());
        header.setIcon(nodeScore.getIcon());

        lid.setText(StringUtils.intHexString(nodeScore.getLid()));

        score.setText(UIConstants.DECIMAL.format(nodeScore.getScore()));
        score.setForeground(nodeScore.getColor());

        revalidate();
    }

    /**
     * @param popup
     *            the popup to set
     */
    public synchronized void setPopup(Popup popup) {
        // just in case it happens
        hidePopup();
        this.popup = popup;
    }

    protected synchronized void hidePopup() {
        if (popup != null) {
            popup.hide();
            popup = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (hideTimer != null) {
            if (hideTimer.isRunning()) {
                hideTimer.stop();
            }
            hideTimer = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (popup != null && !getVisibleRect().contains(e.getPoint())) {
            if (hideTimer == null) {
                ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (hideTimer != null) {
                            hidePopup();
                        }
                    }
                };
                hideTimer = new Timer(UIConstants.UPDATE_TIME, listener);
                hideTimer.setRepeats(false);
            }
            hideTimer.restart();
        }
    }
}
