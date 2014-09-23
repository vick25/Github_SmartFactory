package setting.panel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.plaf.LookAndFeelFactory;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class ThemePanel extends javax.swing.JPanel {

    public ThemePanel(AbstractDialogPage page) {
        this.page = page;
        initComponents();
        AutoCompleteDecorator.decorate(cmbTheme);
        setItemTheme(ConnectDB.pref.getInt(SettingKeyFactory.Theme.LOOKANDFEEL, LookAndFeelFactory.XERTO_STYLE));
    }

    private void setItemTheme(int value) {
        if (value == LookAndFeelFactory.OFFICE2003_STYLE) {
            cmbTheme.setSelectedItem("Office 2003 Style");
        } else if (value == LookAndFeelFactory.OFFICE2007_STYLE) {
            cmbTheme.setSelectedItem("Office 2007 Style");
        } else if (value == LookAndFeelFactory.ECLIPSE3X_STYLE) {
            cmbTheme.setSelectedItem("Eclipse 3x Style");
        } else if (value == LookAndFeelFactory.XERTO_STYLE) {
            cmbTheme.setSelectedItem("Xerto Style");
        } else if (value == LookAndFeelFactory.VSNET_STYLE) {
            cmbTheme.setSelectedItem("Vsnet Style");
        }
        start = true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTheme = new javax.swing.JComboBox();
        btnRestoreDefault = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theme", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 255))); // NOI18N

        jLabel1.setText("Theme");

        cmbTheme.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Office 2003 Style", "Office 2007 Style", "Eclipse 3x Style", "Xerto Style", "Vsnet Style" }));
        cmbTheme.setFocusable(false);
        cmbTheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbThemeItemStateChanged(evt);
            }
        });

        btnRestoreDefault.setText("Restore default");
        btnRestoreDefault.setFocusable(false);
        btnRestoreDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRestoreDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbTheme, 0, 308, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btnRestoreDefault)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void btnRestoreDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreDefaultActionPerformed
    if (JOptionPane.showConfirmDialog(this, "Would you like to restore default theme ?", "Confirm", 0) == 0) {
//        MainFrame.actionTheme(LookAndFeelFactory.OFFICE2003_STYLE);
        start = false;
        cmbTheme.setSelectedItem("Xerto Style");
        start = true;
    }
}//GEN-LAST:event_btnRestoreDefaultActionPerformed

    private void cmbThemeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbThemeItemStateChanged
        if (start) {
            page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
        }
    }//GEN-LAST:event_cmbThemeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestoreDefault;
    public static javax.swing.JComboBox cmbTheme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private final AbstractDialogPage page;
    private boolean start;
}
