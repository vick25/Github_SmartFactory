package setting.panel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import javax.swing.JOptionPane;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class FontColorPanel extends javax.swing.JPanel {

    public FontColorPanel(AbstractDialogPage page) {
        this.page = page;
        initComponents();
        initValues();
        ConnectDB.collapsiblePaneProperties(collapsiblePane2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane2 = new com.jidesoft.pane.CollapsiblePane();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        RStripe21Color1 = new com.jidesoft.combobox.ColorComboBox();
        jLabel8 = new javax.swing.JLabel();
        RStripe21Color2 = new com.jidesoft.combobox.ColorComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        RStripe22Color1 = new com.jidesoft.combobox.ColorComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        RStripe22Color2 = new com.jidesoft.combobox.ColorComboBox();
        RStripe3Color1 = new com.jidesoft.combobox.ColorComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        RStripe3Color2 = new com.jidesoft.combobox.ColorComboBox();
        jLabel15 = new javax.swing.JLabel();
        RStripe3Color3 = new com.jidesoft.combobox.ColorComboBox();
        jLabel16 = new javax.swing.JLabel();
        BDefault = new javax.swing.JButton();

        collapsiblePane2.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane2.setTitle("Table row stripes colors");
        collapsiblePane2.setFocusable(false);

        jLabel4.setText("Row stripes 2 colors (1) :");

        jLabel7.setText("Color 1 :");

        RStripe21Color1.setEditable(false);
        RStripe21Color1.setFocusable(false);
        RStripe21Color1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe21Color1ItemStateChanged(evt);
            }
        });

        jLabel8.setText("Color 2 :");

        RStripe21Color2.setEditable(false);
        RStripe21Color2.setFocusable(false);
        RStripe21Color2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe21Color2ItemStateChanged(evt);
            }
        });

        RStripe22Color1.setEditable(false);
        RStripe22Color1.setFocusable(false);
        RStripe22Color1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe22Color1ItemStateChanged(evt);
            }
        });

        jLabel9.setText("Color 1 :");

        jLabel10.setText("Row stripes 2 colors (2) :");

        jLabel11.setText("Color 2 :");

        RStripe22Color2.setEditable(false);
        RStripe22Color2.setFocusable(false);
        RStripe22Color2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe22Color2ItemStateChanged(evt);
            }
        });

        RStripe3Color1.setEditable(false);
        RStripe3Color1.setFocusable(false);
        RStripe3Color1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe3Color1ItemStateChanged(evt);
            }
        });

        jLabel12.setText("Row stripes 3 colors (3) :");

        jLabel14.setText("Color 1 :");

        RStripe3Color2.setEditable(false);
        RStripe3Color2.setFocusable(false);
        RStripe3Color2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe3Color2ItemStateChanged(evt);
            }
        });

        jLabel15.setText("Color 2 :");

        RStripe3Color3.setEditable(false);
        RStripe3Color3.setFocusable(false);
        RStripe3Color3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RStripe3Color3ItemStateChanged(evt);
            }
        });

        jLabel16.setText("Color 3 :");

        javax.swing.GroupLayout collapsiblePane2Layout = new javax.swing.GroupLayout(collapsiblePane2.getContentPane());
        collapsiblePane2.getContentPane().setLayout(collapsiblePane2Layout);
        collapsiblePane2Layout.setHorizontalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RStripe3Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RStripe3Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RStripe3Color3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel12))
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(2, 2, 2)
                                .addComponent(RStripe22Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RStripe22Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(2, 2, 2)
                                .addComponent(RStripe21Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RStripe21Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        collapsiblePane2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RStripe3Color1, RStripe3Color2, RStripe3Color3});

        collapsiblePane2Layout.setVerticalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(RStripe21Color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RStripe21Color1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(RStripe22Color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RStripe22Color1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(RStripe3Color1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(RStripe3Color3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(RStripe3Color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)))
        );

        BDefault.setText("Restore default");
        BDefault.setFocusable(false);
        BDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collapsiblePane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(BDefault)
                .addContainerGap(21, Short.MAX_VALUE))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

private void RStripe21Color1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe21Color1ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe21Color1ItemStateChanged

private void RStripe21Color2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe21Color2ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe21Color2ItemStateChanged

private void RStripe22Color1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe22Color1ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe22Color1ItemStateChanged

private void RStripe22Color2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe22Color2ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe22Color2ItemStateChanged

private void RStripe3Color1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe3Color1ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe3Color1ItemStateChanged

private void RStripe3Color2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe3Color2ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe3Color2ItemStateChanged

private void RStripe3Color3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RStripe3Color3ItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_RStripe3Color3ItemStateChanged

    private void BDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDefaultActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Would you like to restore default font and color ?", "Confirm", 0) == 0) {
                        RStripe21Color1.setSelectedColor(ConnectDB.getColorFromKey("253, 253, 244"));
            RStripe21Color2.setSelectedColor(ConnectDB.getColorFromKey("230, 230, 255"));
            RStripe22Color1.setSelectedColor(ConnectDB.getColorFromKey("217, 234, 248"));
            RStripe22Color2.setSelectedColor(ConnectDB.getColorFromKey("227, 248, 210"));
            RStripe3Color1.setSelectedColor(ConnectDB.getColorFromKey("253, 253, 244"));
            RStripe3Color2.setSelectedColor(ConnectDB.getColorFromKey("230, 230, 255"));
            RStripe3Color3.setSelectedColor(ConnectDB.getColorFromKey("210, 255, 210"));

            ConnectDB.setColorFromKey(RStripe21Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE21COLOR1);
            ConnectDB.setColorFromKey(RStripe21Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE21COLOR2);
            ConnectDB.setColorFromKey(RStripe22Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE22COLOR1);
            ConnectDB.setColorFromKey(RStripe22Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE22COLOR2);
            ConnectDB.setColorFromKey(RStripe3Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR1);
            ConnectDB.setColorFromKey(RStripe3Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR2);
            ConnectDB.setColorFromKey(RStripe3Color3.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR3);
        }
    }//GEN-LAST:event_BDefaultActionPerformed

    private void initValues() {
        RStripe21Color1.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")));
        RStripe21Color2.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255")));
        RStripe22Color1.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE22COLOR1, "217, 234, 248")));
        RStripe22Color2.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE22COLOR2, "227, 248, 210")));
        RStripe3Color1.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE3COLOR1, "253, 253, 244")));
        RStripe3Color2.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE3COLOR2, "230, 230, 255")));
        RStripe3Color3.setSelectedColor(ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE3COLOR3, "210, 255, 210")));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDefault;
    public static com.jidesoft.combobox.ColorComboBox RStripe21Color1;
    public static com.jidesoft.combobox.ColorComboBox RStripe21Color2;
    public static com.jidesoft.combobox.ColorComboBox RStripe22Color1;
    public static com.jidesoft.combobox.ColorComboBox RStripe22Color2;
    public static com.jidesoft.combobox.ColorComboBox RStripe3Color1;
    public static com.jidesoft.combobox.ColorComboBox RStripe3Color2;
    public static com.jidesoft.combobox.ColorComboBox RStripe3Color3;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
    private final AbstractDialogPage page;
}
