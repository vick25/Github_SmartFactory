package productionPanel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import mainFrame.MainFrame;
import smartfactoryV2.ConnectDB;

public class ProdFeature extends javax.swing.JPanel {

    public ProdFeature(AbstractDialogPage page, JDialog parent) {
        this.page = page;
        this.parent = parent;
        initComponents();
        CollapsiblePaneProperties(collapsiblePane1);
        CollapsiblePaneProperties(collapsiblePane2);
        initValues();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        jLabel5 = new javax.swing.JLabel();
        spFlagTime = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        btnRestore = new javax.swing.JButton();
        collapsiblePane2 = new com.jidesoft.pane.CollapsiblePane();
        jLabel1 = new javax.swing.JLabel();
        radShiftOn = new javax.swing.JRadioButton();
        radShiftOFF = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        radPerMin = new javax.swing.JRadioButton();
        radPerHour = new javax.swing.JRadioButton();

        collapsiblePane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane1.setTitle("Flag Time");
        collapsiblePane1.setFocusable(false);

        jLabel5.setText("Time difference");

        spFlagTime.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(10), Integer.valueOf(5), null, Integer.valueOf(1)));
        spFlagTime.setFocusable(false);
        spFlagTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spFlagTimeStateChanged(evt);
            }
        });

        jLabel6.setText("Minutes");

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spFlagTime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(spFlagTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnRestore.setText("Restore Default");
        btnRestore.setFocusable(false);
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        collapsiblePane2.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane2.setTitle("Options");
        collapsiblePane2.setFocusable(false);

        jLabel1.setText("Time shifts");

        buttonGroup1.add(radShiftOn);
        radShiftOn.setText("ON");
        radShiftOn.setFocusable(false);
        radShiftOn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radShiftOnItemStateChanged(evt);
            }
        });

        buttonGroup1.add(radShiftOFF);
        radShiftOFF.setSelected(true);
        radShiftOFF.setText("OFF");
        radShiftOFF.setFocusable(false);
        radShiftOFF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radShiftOFFItemStateChanged(evt);
            }
        });

        jLabel2.setText("Production rate");

        buttonGroup2.add(radPerMin);
        radPerMin.setSelected(true);
        radPerMin.setText("per/min");
        radPerMin.setFocusable(false);
        radPerMin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radPerMinItemStateChanged(evt);
            }
        });

        buttonGroup2.add(radPerHour);
        radPerHour.setText("per/hr");
        radPerHour.setFocusable(false);
        radPerHour.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radPerHourItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane2Layout = new javax.swing.GroupLayout(collapsiblePane2.getContentPane());
        collapsiblePane2.getContentPane().setLayout(collapsiblePane2Layout);
        collapsiblePane2Layout.setHorizontalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addComponent(radShiftOn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radShiftOFF))
                    .addGroup(collapsiblePane2Layout.createSequentialGroup()
                        .addComponent(radPerMin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radPerHour)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane2Layout.setVerticalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(radShiftOn)
                    .addComponent(radShiftOFF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(radPerHour)
                    .addComponent(radPerMin)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collapsiblePane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(collapsiblePane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btnRestore)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spFlagTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spFlagTimeStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_spFlagTimeStateChanged

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Would you like to restore default features?",
                "Settings", 0) == 0) {
            spFlagTime.setValue(10);
            radShiftOn.setSelected(false);
            radShiftOFF.setSelected(true);
            radPerMin.setSelected(true);
            radPerHour.setSelected(false);
            ConnectDB.pref.putInt(ProdStatKeyFactory.ProdFeatures.SPFLAGTIMEFRAME,
                    Integer.parseInt(ProdFeature.spFlagTime.getValue().toString()));
            if (radShiftOn.isSelected()) {
                ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, true);
            } else {
                ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false);
            }
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, radPerMin.isSelected());
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, radPerHour.isSelected());

            parent.dispose();
            if (MainFrame._documentPane.isDocumentOpened("Production")) {
                ProductionPane.setSettings();
            }
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void radShiftOnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radShiftOnItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_radShiftOnItemStateChanged

    private void radShiftOFFItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radShiftOFFItemStateChanged
        radShiftOnItemStateChanged(evt);
    }//GEN-LAST:event_radShiftOFFItemStateChanged

    private void radPerMinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radPerMinItemStateChanged
        radShiftOnItemStateChanged(evt);
    }//GEN-LAST:event_radPerMinItemStateChanged

    private void radPerHourItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radPerHourItemStateChanged
        radShiftOnItemStateChanged(evt);
    }//GEN-LAST:event_radPerHourItemStateChanged

    private void initValues() {
        spFlagTime.setValue(ConnectDB.pref.getInt(ProdStatKeyFactory.ProdFeatures.SPFLAGTIMEFRAME, 10));
        radShiftOn.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
        radPerMin.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true));
        radPerHour.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false));
    }

    private static void CollapsiblePaneProperties(CollapsiblePane pane) {
        pane.setBackground(Color.WHITE);
        pane.getContentPane().setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        pane.getContentPane().setOpaque(false);
        pane.getActualComponent().setBackground(Color.WHITE);
        JComponent actualComponent = pane.getActualComponent();
        JideSwingUtilities.setOpaqueRecursively(actualComponent, false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestore;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JRadioButton radPerHour;
    public static javax.swing.JRadioButton radPerMin;
    public static javax.swing.JRadioButton radShiftOFF;
    public static javax.swing.JRadioButton radShiftOn;
    public static javax.swing.JSpinner spFlagTime;
    // End of variables declaration//GEN-END:variables
    AbstractDialogPage page;
    JDialog parent;
}
