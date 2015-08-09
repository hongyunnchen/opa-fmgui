/**
 * INTEL CONFIDENTIAL
 * Copyright (c) 2015 Intel Corporation All Rights Reserved.
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
 *  File Name: AboutDialog.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.2  2015/03/31 21:34:55  jijunwan
 *  Archive Log:    minor adjustments
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2015/03/30 22:47:07  jijunwan
 *  Archive Log:    1) added header for AboutDialog
 *  Archive Log:    2) moved to main.view
 *  Archive Log:
 *
 *  Overview: 
 *
 *  @author: fisherma
 *
 ******************************************************************************/

package com.intel.stl.ui.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.jdesktop.swingx.JXHyperlink;

import com.intel.stl.ui.common.STLConstants;
import com.intel.stl.ui.common.UIConstants;
import com.intel.stl.ui.common.UIImages;
import com.intel.stl.ui.common.UILabels;
import com.intel.stl.ui.common.view.ImagePanel;
import com.intel.stl.ui.common.view.ImagePanel.Style;

public class AboutDialog extends JDialog {

    private static final long serialVersionUID = 7460752071806970217L;

    private JPanel rightMainPanel;

    private JPanel topBanner;

    private JLabel appNameLabel;

    private JLabel appBuildIdLabel;

    private JLabel appBuildDateLabel;

    private JPanel mainContentPanel;

    private JXHyperlink copyrightBtn;

    private JXHyperlink thirdPartyBtn;

    private JButton okBtn;

    private JEditorPane editor;

    private static AboutDialog dlgInstance;

    private final Color bgColor = UIConstants.INTEL_WHITE;

    private final Color tabBgColor = UIConstants.INTEL_SKY_BLUE;

    private final String appNameStr;

    private final String appBuildIdStr;

    private final String appBuildDateStr;

    private final String appVersion;

    private AboutDialog(JFrame parent, String appName, String appVersion,
            String appBuildId, String appBuildDate) {
        // create modal dialog
        super(parent, STLConstants.K3100_ABOUT_DIALOG.getValue() + " "
                + appName, true);

        this.appNameStr = appName;
        this.appVersion = appVersion;
        this.appBuildIdStr = appBuildId;
        this.appBuildDateStr = appBuildDate;

        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 300));
        this.setSize(600, 450);
        // this.setResizable(false);
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        Container container = getContentPane();
        container.setBackground(bgColor);
        container.setLayout(new BorderLayout(0, 0));

        //
        // Left-side Intel image
        //
        JPanel leftBanner =
                new ImagePanel(
                        UIImages.ABOUT_DIALOG_LEFT_BANNER_IMG.getImage(),
                        Style.FIT_IMAGE_WIDTH);
        leftBanner.setOpaque(false);
        container.add(leftBanner, BorderLayout.WEST);

        JPanel rightMainPanel = getRightMainPanel();
        container.add(rightMainPanel, BorderLayout.CENTER);
    }

    /**
     * 
     * <i>Description:</i> Create label to hold the title, text with copyright
     * and 3rd party JARs
     * 
     * @return
     */
    protected JPanel getRightMainPanel() {
        if (rightMainPanel == null) {
            rightMainPanel = new JPanel(new BorderLayout());
            rightMainPanel.setOpaque(false);

            JPanel topBanner = getTopBanner();
            rightMainPanel.add(topBanner, BorderLayout.NORTH);

            JPanel contentPanel = getMainContentPanel();
            rightMainPanel.add(contentPanel, BorderLayout.CENTER);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
            panel.setOpaque(false);
            okBtn = new JButton(STLConstants.K0645_OK.getValue());
            okBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            okBtn.addAncestorListener(new AncestorListener() {

                @Override
                public void ancestorAdded(AncestorEvent event) {
                    JComponent component = event.getComponent();
                    component.requestFocusInWindow();
                }

                @Override
                public void ancestorRemoved(AncestorEvent event) {
                }

                @Override
                public void ancestorMoved(AncestorEvent event) {
                }

            });
            panel.add(okBtn);
            rightMainPanel.add(panel, BorderLayout.SOUTH);

            showCopyright();
        }
        return rightMainPanel;
    }

    protected JPanel getTopBanner() {
        if (topBanner == null) {
            topBanner =
                    new ImagePanel(
                            UIImages.ABOUT_DIALOG_TOP_BANNER_IMG.getImage(),
                            Style.FIT_PANEL);
            topBanner.setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(2, 2, 2, 2);
            gc.weightx = 1;
            gc.weighty = 1;
            gc.gridwidth = GridBagConstraints.REMAINDER;
            gc.fill = GridBagConstraints.BOTH;
            appNameLabel =
                    new JLabel(UILabels.STL91000_ABOUT_APP.getDescription(
                            appNameStr, appVersion));
            appNameLabel.setFont(UIConstants.H2_FONT.deriveFont(Font.BOLD));
            appNameLabel.setForeground(UIConstants.INTEL_WHITE);
            appNameLabel.setHorizontalAlignment(JLabel.CENTER);
            appNameLabel.setVerticalAlignment(JLabel.CENTER);
            appNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                    10));
            topBanner.add(appNameLabel, gc);

            gc.weightx = 0;
            gc.weighty = 0;
            gc.gridwidth = 1;
            appBuildIdLabel =
                    new JLabel(
                            UILabels.STL91001_BUILD_ID
                                    .getDescription(appBuildIdStr));
            appBuildIdLabel.setFont(UIConstants.H5_FONT);
            appBuildIdLabel.setForeground(UIConstants.INTEL_WHITE);
            appBuildIdLabel.setHorizontalTextPosition(JLabel.LEADING);
            topBanner.add(appBuildIdLabel, gc);

            gc.weightx = 1;
            topBanner.add(Box.createGlue(), gc);

            gc.weightx = 0;
            gc.gridwidth = GridBagConstraints.REMAINDER;
            appBuildDateLabel =
                    new JLabel(
                            UILabels.STL91002_BUILD_DATE
                                    .getDescription(appBuildDateStr));
            appBuildDateLabel.setFont(UIConstants.H5_FONT);
            appBuildDateLabel.setForeground(UIConstants.INTEL_WHITE);
            appBuildDateLabel.setHorizontalTextPosition(JLabel.LEADING);
            topBanner.add(appBuildDateLabel, gc);
        }
        return topBanner;
    }

    protected JPanel getMainContentPanel() {
        if (mainContentPanel == null) {
            mainContentPanel = new JPanel(new BorderLayout());
            mainContentPanel.setOpaque(false);

            JPanel ctrPanel =
                    new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 0));
            ctrPanel.setBackground(tabBgColor);
            ctrPanel.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0,
                    UIConstants.INTEL_ORANGE));

            copyrightBtn =
                    new JXHyperlink(new AbstractAction(
                            STLConstants.K3101_COPYRIGHT.getValue()) {
                        private static final long serialVersionUID =
                                -7817099734925336713L;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showCopyright();
                        }
                    });
            copyrightBtn.setOpaque(true);
            copyrightBtn.setBorder(BorderFactory
                    .createEmptyBorder(2, 10, 3, 10));
            copyrightBtn.setFont(UIConstants.H4_FONT.deriveFont(Font.BOLD));
            ctrPanel.add(copyrightBtn);

            thirdPartyBtn =
                    new JXHyperlink(new AbstractAction(
                            STLConstants.K3102_THIRD_PARTY_LIBS.getValue()) {
                        private static final long serialVersionUID =
                                8193708188358869864L;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showThirdParty();
                        }
                    });
            thirdPartyBtn.setOpaque(true);
            thirdPartyBtn.setBorder(BorderFactory.createEmptyBorder(2, 10, 3,
                    10));
            thirdPartyBtn.setFont(UIConstants.H4_FONT.deriveFont(Font.BOLD));
            ctrPanel.add(thirdPartyBtn);
            mainContentPanel.add(ctrPanel, BorderLayout.NORTH);

            JEditorPane editor = getEditorPane();
            JScrollPane scrollPane = new JScrollPane(editor);
            scrollPane.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2,
                    UIConstants.INTEL_WHITE));
            mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        }
        return mainContentPanel;
    }

    protected JEditorPane getEditorPane() {
        if (editor == null) {
            editor = new JEditorPane();
            editor.setBackground(bgColor);
            editor.setEditable(false);

            editor.setEditorKit(JEditorPane
                    .createEditorKitForContentType("text/html"));

            //
            // This is to open the links in the browser.
            //
            editor.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().browse(e.getURL().toURI());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.err.println("Unsupported Desktop");
                        }
                    }
                }
            });
        }
        return editor;
    }

    public static void showAboutDialog(JFrame parent, String appName,
            String appVersion, String appBuildId, String appBuildDate) {
        if (dlgInstance == null) {
            // Create dialog
            dlgInstance =
                    new AboutDialog(parent, appName, appVersion, appBuildId,
                            appBuildDate);
        }
        dlgInstance.setVisible(true);
    }

    protected void showCopyright() {
        // Show Copyright text
        copyrightBtn.setForeground(UIConstants.INTEL_DARK_GRAY);
        copyrightBtn.setBackground(UIConstants.INTEL_WHITE);
        thirdPartyBtn.setForeground(UIConstants.INTEL_BLUE);
        thirdPartyBtn.setBackground(tabBgColor);

        java.net.URL copyrightURL =
                getClass().getResource("/help/Copyright.html");

        if (copyrightURL != null) {
            try {
                editor.setPage(copyrightURL);
            } catch (IOException ioe) {
                System.err.println("Copyright.html file is not found.");
                ioe.printStackTrace();
            }
        }
        okBtn.requestFocusInWindow();
    }

    protected void showThirdParty() {
        // Show third parties licenses list
        thirdPartyBtn.setForeground(UIConstants.INTEL_DARK_GRAY);
        thirdPartyBtn.setBackground(UIConstants.INTEL_WHITE);
        copyrightBtn.setForeground(UIConstants.INTEL_BLUE);
        copyrightBtn.setBackground(tabBgColor);

        java.net.URL otherJarsURL =
                getClass().getResource("/help/ThirdPartyJars.html");

        if (otherJarsURL != null) {
            try {
                editor.setPage(otherJarsURL);
            } catch (IOException ioe) {
                System.err.println("ThirdPartyJars.html file is not found.");
                ioe.printStackTrace();
            }
        }
        okBtn.requestFocusInWindow();
    }
}

class GradientPanel extends JPanel {
    private final Color top;

    private final String title;

    private final Color bottom;

    GradientPanel(Color top, Color bottom, String title) {
        // super();
        this.top = top;
        this.bottom = bottom;
        this.title = title;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        // Draw title string
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font titleFont = new Font("Dialog", Font.BOLD, 20);
        g2d.setFont(titleFont);

        this.setForeground(Color.white);
        int x = 30;
        int y = titleFont.getSize() + (h - titleFont.getSize()) / 2;
        // Draw title string over image. // TODO: Title
        g2d.drawString(title, x, y);
    }
}
