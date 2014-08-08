package setting.panel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class PrivacyPanel extends javax.swing.JPanel {

    public PrivacyPanel(AbstractDialogPage page) {
        this.page = page;
        initComponents();
        collapsiblePaneProperties(collapsiblePane1);
        initValues();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        chkRememberLogin = new javax.swing.JCheckBox();
        radRememberUserName = new javax.swing.JRadioButton();
        radRememberUserNamePassword = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        collapsiblePane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane1.setTitle("Login informations");
        collapsiblePane1.setFocusable(false);

        chkRememberLogin.setText("Remember login information");
        chkRememberLogin.setFocusable(false);
        chkRememberLogin.setOpaque(false);
        chkRememberLogin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkRememberLoginItemStateChanged(evt);
            }
        });

        buttonGroup1.add(radRememberUserName);
        radRememberUserName.setSelected(true);
        radRememberUserName.setText("Remember user name only");
        radRememberUserName.setEnabled(false);
        radRememberUserName.setFocusable(false);
        radRememberUserName.setOpaque(false);
        radRememberUserName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radRememberUserNameItemStateChanged(evt);
            }
        });

        buttonGroup1.add(radRememberUserNamePassword);
        radRememberUserNamePassword.setText("Remember user name and password");
        radRememberUserNamePassword.setEnabled(false);
        radRememberUserNamePassword.setFocusable(false);
        radRememberUserNamePassword.setOpaque(false);
        radRememberUserNamePassword.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radRememberUserNamePasswordItemStateChanged(evt);
            }
        });

        jLabel1.setText("<html>Login information  can be kept in the Password Manager so that you <br>do not need to re-enter your login details every time you log in.");

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsiblePane1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radRememberUserNamePassword)
                            .addComponent(radRememberUserName)))
                    .addGroup(collapsiblePane1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(chkRememberLogin))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, collapsiblePane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(125, 125, 125))
        );
        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkRememberLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radRememberUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radRememberUserNamePassword)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void chkRememberLoginItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkRememberLoginItemStateChanged
    if (chkRememberLogin.isSelected()) {
        radRememberUserName.setEnabled(true);
        radRememberUserNamePassword.setEnabled(true);
    } else {
        radRememberUserName.setSelected(true);
        radRememberUserName.setEnabled(false);
        radRememberUserNamePassword.setEnabled(false);
    }
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_chkRememberLoginItemStateChanged

private void radRememberUserNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radRememberUserNameItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_radRememberUserNameItemStateChanged

private void radRememberUserNamePasswordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radRememberUserNamePasswordItemStateChanged
    page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
}//GEN-LAST:event_radRememberUserNamePasswordItemStateChanged

    private void initValues() {
        radRememberUserName.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.Privacy.SAVEUSERNAMETRUE, true));
        radRememberUserNamePassword.setSelected(!ConnectDB.pref.getBoolean(SettingKeyFactory.Privacy.SAVEUSERNAMETRUE, true));
        chkRememberLogin.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.Privacy.SAVELOGININFO, false));
    }

    private static void collapsiblePaneProperties(CollapsiblePane pane) {
        pane.setBackground(Color.white);
        pane.getContentPane().setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        pane.getContentPane().setOpaque(false);
        pane.getActualComponent().setBackground(Color.white);
        JComponent actualComponent = pane.getActualComponent();
        JideSwingUtilities.setOpaqueRecursively(actualComponent, false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JCheckBox chkRememberLogin;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JRadioButton radRememberUserName;
    public static javax.swing.JRadioButton radRememberUserNamePassword;
    // End of variables declaration//GEN-END:variables
    AbstractDialogPage page;
}
