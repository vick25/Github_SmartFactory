package eventsPanel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import smartfactoryV2.ConnectDB;

public class EventChartFeature extends javax.swing.JPanel {

    public EventChartFeature(AbstractDialogPage page, JDialog parent) {
        this.page = page;
        this.parent = parent;
        initComponents();
        ConnectDB.collapsiblePaneProperties(collapsiblePane3);
        ConnectDB.collapsiblePaneProperties(collapsiblePane4);
        ConnectDB.collapsiblePaneProperties(collapsiblePane6);
        ConnectDB.collapsiblePaneProperties(collapsiblePane7);
        initValues();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane3 = new com.jidesoft.pane.CollapsiblePane();
        RBLineLabel = new javax.swing.JRadioButton();
        RBSimpleLabel = new javax.swing.JRadioButton();
        RBNoLabel = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        ChBExplodedSegment = new javax.swing.JCheckBox();
        SLAngle = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        collapsiblePane4 = new com.jidesoft.pane.CollapsiblePane();
        BarChBVLine = new javax.swing.JCheckBox();
        BarChBHLine = new javax.swing.JCheckBox();
        OneColor = new javax.swing.JRadioButton();
        RandomColor = new javax.swing.JRadioButton();
        CBColor2 = new com.jidesoft.combobox.ColorComboBox();
        collapsiblePane6 = new com.jidesoft.pane.CollapsiblePane();
        LineChBVLine = new javax.swing.JCheckBox();
        LineChBHLine = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        CBColor = new com.jidesoft.combobox.ColorComboBox();
        jLabel5 = new javax.swing.JLabel();
        SPLineWidth = new javax.swing.JSpinner();
        collapsiblePane7 = new com.jidesoft.pane.CollapsiblePane();
        ChBShadow = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        RBFlat = new javax.swing.JRadioButton();
        RBRaised = new javax.swing.JRadioButton();
        RB3D = new javax.swing.JRadioButton();
        ChBRollover = new javax.swing.JCheckBox();
        ChBOutline = new javax.swing.JCheckBox();
        ChBSelectionOutline = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        cmbTime = new javax.swing.JComboBox();
        btnRestore = new javax.swing.JButton();

        collapsiblePane3.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane3.setTitle("Pie Chart");
        collapsiblePane3.setFocusable(false);

        buttonGroup1.add(RBLineLabel);
        RBLineLabel.setSelected(true);
        RBLineLabel.setText("Line Labels");
        RBLineLabel.setFocusable(false);
        RBLineLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RBLineLabelItemStateChanged(evt);
            }
        });

        buttonGroup1.add(RBSimpleLabel);
        RBSimpleLabel.setText("Simple Labels");
        RBSimpleLabel.setFocusable(false);
        RBSimpleLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RBSimpleLabelItemStateChanged(evt);
            }
        });

        buttonGroup1.add(RBNoLabel);
        RBNoLabel.setText("No Labels");
        RBNoLabel.setFocusable(false);
        RBNoLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RBNoLabelItemStateChanged(evt);
            }
        });

        jLabel1.setText("Label :");

        ChBExplodedSegment.setText("Selection shows exploded segments");
        ChBExplodedSegment.setFocusable(false);
        ChBExplodedSegment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChBExplodedSegmentItemStateChanged(evt);
            }
        });

        SLAngle.setMajorTickSpacing(90);
        SLAngle.setMaximum(360);
        SLAngle.setMinorTickSpacing(10);
        SLAngle.setPaintLabels(true);
        SLAngle.setPaintTicks(true);
        SLAngle.setValue(0);
        SLAngle.setFocusable(false);
        SLAngle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SLAngleStateChanged(evt);
            }
        });

        jLabel3.setText("Offset Angle :");

        javax.swing.GroupLayout collapsiblePane3Layout = new javax.swing.GroupLayout(collapsiblePane3.getContentPane());
        collapsiblePane3.getContentPane().setLayout(collapsiblePane3Layout);
        collapsiblePane3Layout.setHorizontalGroup(
            collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChBExplodedSegment)
                    .addGroup(collapsiblePane3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(RBLineLabel)
                        .addGap(10, 10, 10)
                        .addComponent(RBSimpleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RBNoLabel))
                    .addGroup(collapsiblePane3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(SLAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane3Layout.setVerticalGroup(
            collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane3Layout.createSequentialGroup()
                .addGroup(collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(RBLineLabel)
                    .addComponent(jLabel1)
                    .addComponent(RBSimpleLabel)
                    .addComponent(RBNoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(SLAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChBExplodedSegment))
        );

        try {
            collapsiblePane4.setCollapsed(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        collapsiblePane4.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane4.setTitle("Bar Chart");
        collapsiblePane4.setFocusable(false);

        BarChBVLine.setText("Vertical Grid Lines Visible");
        BarChBVLine.setFocusable(false);
        BarChBVLine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BarChBVLineItemStateChanged(evt);
            }
        });

        BarChBHLine.setSelected(true);
        BarChBHLine.setText("Horizontal Grid Lines Visible");
        BarChBHLine.setFocusable(false);
        BarChBHLine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BarChBHLineItemStateChanged(evt);
            }
        });

        buttonGroup3.add(OneColor);
        OneColor.setText("One Color");
        OneColor.setFocusable(false);
        OneColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OneColorItemStateChanged(evt);
            }
        });

        buttonGroup3.add(RandomColor);
        RandomColor.setSelected(true);
        RandomColor.setText("Random Color");
        RandomColor.setFocusable(false);
        RandomColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RandomColorItemStateChanged(evt);
            }
        });

        CBColor2.setSelectedColor(new java.awt.Color(0, 204, 0));
        CBColor2.setEditable(false);
        CBColor2.setEnabled(false);
        CBColor2.setFocusable(false);
        CBColor2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBColor2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane4Layout = new javax.swing.GroupLayout(collapsiblePane4.getContentPane());
        collapsiblePane4.getContentPane().setLayout(collapsiblePane4Layout);
        collapsiblePane4Layout.setHorizontalGroup(
            collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RandomColor)
                    .addGroup(collapsiblePane4Layout.createSequentialGroup()
                        .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BarChBVLine, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OneColor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BarChBHLine))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane4Layout.setVerticalGroup(
            collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane4Layout.createSequentialGroup()
                .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BarChBVLine)
                    .addComponent(BarChBHLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OneColor)
                    .addComponent(CBColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RandomColor)
                .addGap(5, 5, 5))
        );

        try {
            collapsiblePane6.setCollapsed(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        collapsiblePane6.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane6.setTitle("Line Chart");
        collapsiblePane6.setFocusable(false);

        LineChBVLine.setSelected(true);
        LineChBVLine.setText("Vertical Grid Lines Visible");
        LineChBVLine.setFocusable(false);
        LineChBVLine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LineChBVLineItemStateChanged(evt);
            }
        });

        LineChBHLine.setSelected(true);
        LineChBHLine.setText("Horizontal Grid Lines Visible");
        LineChBHLine.setFocusable(false);
        LineChBHLine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LineChBHLineItemStateChanged(evt);
            }
        });

        jLabel4.setText("Line Color :");

        CBColor.setSelectedColor(new java.awt.Color(255, 0, 0));
        CBColor.setEditable(false);
        CBColor.setFocusable(false);
        CBColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBColorItemStateChanged(evt);
            }
        });

        jLabel5.setText("Line width");

        SPLineWidth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        SPLineWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPLineWidthStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane6Layout = new javax.swing.GroupLayout(collapsiblePane6.getContentPane());
        collapsiblePane6.getContentPane().setLayout(collapsiblePane6Layout);
        collapsiblePane6Layout.setHorizontalGroup(
            collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(collapsiblePane6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBColor, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPLineWidth))
                    .addGroup(collapsiblePane6Layout.createSequentialGroup()
                        .addComponent(LineChBVLine, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LineChBHLine)))
                .addContainerGap())
        );
        collapsiblePane6Layout.setVerticalGroup(
            collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane6Layout.createSequentialGroup()
                .addGroup(collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LineChBVLine)
                    .addComponent(LineChBHLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CBColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(SPLineWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        collapsiblePane7.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane7.setTitle("Common Features");
        collapsiblePane7.setFocusable(false);

        ChBShadow.setSelected(true);
        ChBShadow.setText("Show Shadow");
        ChBShadow.setFocusable(false);
        ChBShadow.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChBShadowItemStateChanged(evt);
            }
        });

        jLabel2.setText("Chart appearence :");

        buttonGroup2.add(RBFlat);
        RBFlat.setSelected(true);
        RBFlat.setText("Flat");
        RBFlat.setFocusable(false);
        RBFlat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RBFlatItemStateChanged(evt);
            }
        });

        buttonGroup2.add(RBRaised);
        RBRaised.setText("Raised");
        RBRaised.setFocusable(false);
        RBRaised.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RBRaisedItemStateChanged(evt);
            }
        });

        buttonGroup2.add(RB3D);
        RB3D.setText("3D");
        RB3D.setFocusable(false);
        RB3D.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RB3DItemStateChanged(evt);
            }
        });

        ChBRollover.setText("Rollover");
        ChBRollover.setFocusable(false);
        ChBRollover.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChBRolloverItemStateChanged(evt);
            }
        });

        ChBOutline.setText("Always show outline");
        ChBOutline.setFocusable(false);
        ChBOutline.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChBOutlineItemStateChanged(evt);
            }
        });

        ChBSelectionOutline.setText("Selection shows outline");
        ChBSelectionOutline.setFocusable(false);
        ChBSelectionOutline.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChBSelectionOutlineItemStateChanged(evt);
            }
        });

        jLabel6.setText("Time :");

        cmbTime.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hour", "Minute", "Second" }));
        cmbTime.setFocusable(false);
        cmbTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTimeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane7Layout = new javax.swing.GroupLayout(collapsiblePane7.getContentPane());
        collapsiblePane7.getContentPane().setLayout(collapsiblePane7Layout);
        collapsiblePane7Layout.setHorizontalGroup(
            collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsiblePane7Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(RBFlat)
                        .addGap(18, 18, 18)
                        .addComponent(RBRaised, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(RB3D))
                    .addGroup(collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(collapsiblePane7Layout.createSequentialGroup()
                            .addComponent(ChBShadow)
                            .addGap(18, 18, 18)
                            .addComponent(ChBRollover)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(collapsiblePane7Layout.createSequentialGroup()
                            .addComponent(ChBSelectionOutline)
                            .addGap(29, 29, 29)
                            .addComponent(ChBOutline))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane7Layout.setVerticalGroup(
            collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane7Layout.createSequentialGroup()
                .addGroup(collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(RBFlat)
                    .addComponent(RBRaised)
                    .addComponent(RB3D))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ChBShadow)
                    .addComponent(ChBRollover)
                    .addComponent(jLabel6)
                    .addComponent(cmbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ChBSelectionOutline)
                    .addComponent(ChBOutline)))
        );

        btnRestore.setText("Restore Default");
        btnRestore.setFocusable(false);
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collapsiblePane7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRestore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ChBShadowItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChBShadowItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_ChBShadowItemStateChanged

    private void ChBRolloverItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChBRolloverItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_ChBRolloverItemStateChanged

    private void ChBOutlineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChBOutlineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_ChBOutlineItemStateChanged

    private void ChBSelectionOutlineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChBSelectionOutlineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_ChBSelectionOutlineItemStateChanged

    private void ChBExplodedSegmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChBExplodedSegmentItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_ChBExplodedSegmentItemStateChanged

    private void BarChBVLineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BarChBVLineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_BarChBVLineItemStateChanged

    private void BarChBHLineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BarChBHLineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_BarChBHLineItemStateChanged

    private void LineChBVLineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LineChBVLineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_LineChBVLineItemStateChanged

    private void LineChBHLineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LineChBHLineItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_LineChBHLineItemStateChanged

    private void RBLineLabelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RBLineLabelItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RBLineLabelItemStateChanged

    private void RBSimpleLabelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RBSimpleLabelItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RBSimpleLabelItemStateChanged

    private void RBNoLabelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RBNoLabelItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RBNoLabelItemStateChanged

    private void SLAngleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SLAngleStateChanged
        if (!EventsStatistic.btnPieChart.isEnabled()
                && EventsStatistic.getStylePieChart() != null && EventsStatistic.getChart() != null) {
            int value = SLAngle.getValue();
            EventsStatistic.getStylePieChart().setPieOffsetAngle(value);
            EventsStatistic.getChart().repaint();
        }
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_SLAngleStateChanged

    private void CBColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBColorItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_CBColorItemStateChanged

    private void RBFlatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RBFlatItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RBFlatItemStateChanged

    private void RBRaisedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RBRaisedItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RBRaisedItemStateChanged

    private void RB3DItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RB3DItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RB3DItemStateChanged

    private void SPLineWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPLineWidthStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_SPLineWidthStateChanged

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Would you like to restore default features ?", "Confirm", 0) == 0) {
            RBLineLabel.setSelected(true);
            RBSimpleLabel.setSelected(false);
            RBNoLabel.setSelected(false);
            SLAngle.setValue(0);
            ChBExplodedSegment.setSelected(false);

            OneColor.setSelected(false);
            RandomColor.setSelected(true);
            CBColor2.setSelectedColor(ConnectDB.getColorFromKey("0, 204, 0"));

            BarChBVLine.setSelected(false);
            BarChBHLine.setSelected(true);
            LineChBVLine.setSelected(true);
            LineChBHLine.setSelected(true);
            CBColor.setSelectedColor(ConnectDB.getColorFromKey("255, 0, 0"));
            SPLineWidth.setValue(2);

            RBFlat.setSelected(true);
            RBRaised.setSelected(false);
            RB3D.setSelected(false);
            ChBShadow.setSelected(true);
            ChBRollover.setSelected(false);
            ChBOutline.setSelected(false);
            ChBSelectionOutline.setSelected(false);
            cmbTime.setSelectedIndex(0);


            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBSimpleLabel, EventChartFeature.RBSimpleLabel.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBNoLabel, EventChartFeature.RBNoLabel.isSelected());
            ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.SLAngle, EventChartFeature.SLAngle.getValue());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBExplodedSegment, EventChartFeature.ChBExplodedSegment.isSelected());

            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.BarChBVLine, EventChartFeature.BarChBVLine.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.BarChBHLine, EventChartFeature.BarChBHLine.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.LineChBVLine, EventChartFeature.LineChBVLine.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.LineChBHLine, EventChartFeature.LineChBHLine.isSelected());
            ConnectDB.setColorFromKey(EventChartFeature.CBColor.getSelectedColor(), StatKeyFactory.ChartFeatures.CBColor);

            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.OneColor, EventChartFeature.OneColor.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RandomColor, EventChartFeature.RandomColor.isSelected());
            ConnectDB.setColorFromKey(EventChartFeature.CBColor2.getSelectedColor(), StatKeyFactory.ChartFeatures.CBColor2);

            ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.SPLineWidth, Integer.parseInt(EventChartFeature.SPLineWidth.getValue().toString()));

            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBFlat, EventChartFeature.RBFlat.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBRaised, EventChartFeature.RBRaised.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RB3D, EventChartFeature.RB3D.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBShadow, EventChartFeature.ChBShadow.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBRollover, EventChartFeature.ChBRollover.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, EventChartFeature.ChBOutline.isSelected());
            ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBSelectionOutline, EventChartFeature.ChBSelectionOutline.isSelected());
            ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.CMBTIME, EventChartFeature.cmbTime.getSelectedIndex());
            if (!EventsStatistic.btnBarChart.isEnabled()) {
                EventsStatistic.createBarChart();
            } else if (!EventsStatistic.btnPieChart.isEnabled()) {
                EventsStatistic.createPieChart();
            } else if (!EventsStatistic.btnLineChart.isEnabled()) {
                EventsStatistic.createLineChart();
            }
            parent.dispose();
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void CBColor2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBColor2ItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_CBColor2ItemStateChanged

    private void RandomColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RandomColorItemStateChanged
        if (RandomColor.isSelected()) {
            CBColor2.setEnabled(false);
        }
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_RandomColorItemStateChanged

    private void OneColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OneColorItemStateChanged
        if (OneColor.isSelected()) {
            CBColor2.setEnabled(true);
        }
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_OneColorItemStateChanged

    private void cmbTimeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTimeItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_cmbTimeItemStateChanged

    private void initValues() {
        RBLineLabel.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBLineLabel, true));
        RBSimpleLabel.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBSimpleLabel, false));
        RBNoLabel.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBNoLabel, false));
        SLAngle.setValue(ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.SLAngle, 0));
        ChBExplodedSegment.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBExplodedSegment, false));

        BarChBVLine.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.BarChBVLine, false));
        BarChBHLine.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.BarChBHLine, true));
        LineChBVLine.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.LineChBVLine, true));
        LineChBHLine.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.LineChBHLine, true));
        CBColor.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(StatKeyFactory.ChartFeatures.CBColor, "255, 0, 0")));
        SPLineWidth.setValue(ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.SPLineWidth, 2));

        OneColor.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.OneColor, false));
        RandomColor.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RandomColor, true));
        CBColor2.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(StatKeyFactory.ChartFeatures.CBColor2, "0, 204, 0")));

        RBFlat.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBFlat, true));
        RBRaised.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBRaised, false));
        RB3D.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RB3D, false));
        ChBShadow.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBShadow, true));
        ChBRollover.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBRollover, false));
        ChBOutline.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
        ChBSelectionOutline.setSelected(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBSelectionOutline, false));
        cmbTime.setSelectedIndex(ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.CMBTIME, 0));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox BarChBHLine;
    public static javax.swing.JCheckBox BarChBVLine;
    public static com.jidesoft.combobox.ColorComboBox CBColor;
    public static com.jidesoft.combobox.ColorComboBox CBColor2;
    public static javax.swing.JCheckBox ChBExplodedSegment;
    public static javax.swing.JCheckBox ChBOutline;
    public static javax.swing.JCheckBox ChBRollover;
    public static javax.swing.JCheckBox ChBSelectionOutline;
    public static javax.swing.JCheckBox ChBShadow;
    public static javax.swing.JCheckBox LineChBHLine;
    public static javax.swing.JCheckBox LineChBVLine;
    public static javax.swing.JRadioButton OneColor;
    public static javax.swing.JRadioButton RB3D;
    public static javax.swing.JRadioButton RBFlat;
    public static javax.swing.JRadioButton RBLineLabel;
    public static javax.swing.JRadioButton RBNoLabel;
    public static javax.swing.JRadioButton RBRaised;
    public static javax.swing.JRadioButton RBSimpleLabel;
    public static javax.swing.JRadioButton RandomColor;
    public static javax.swing.JSlider SLAngle;
    public static javax.swing.JSpinner SPLineWidth;
    private javax.swing.JButton btnRestore;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    public static javax.swing.JComboBox cmbTime;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane3;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane4;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane6;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private final AbstractDialogPage page;
    private final JDialog parent;
}
