package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import smartfactoryV2.ConnectDB;

public class ChangePasswordNewUser extends javax.swing.JDialog {

    public ChangePasswordNewUser(java.awt.Frame parent, boolean modal, String username, String password) {
        super(parent, modal);
        initComponents();
        Timer time = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtLoginID.getText().isEmpty()
                        && !String.copyValueOf(txtOldPassword.getPassword()).isEmpty()
                        && !String.copyValueOf(txtNewPassword.getPassword()).isEmpty()
                        && !String.copyValueOf(txtRetypePassword.getPassword()).isEmpty()) {
                    btnValidate.setEnabled(true);
                } else {
                    btnValidate.setEnabled(false);
                }
            }
        });
        time.start();
        txtLoginID.setText(username);
        txtOldPassword.setText(password);
        txtNewPassword.requestFocus();
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icons/password16.png")).getImage());
        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        txtRetypePassword = new javax.swing.JPasswordField();
        btnCancel = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtOldPassword = new javax.swing.JPasswordField();
        txtLoginID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Password : New user");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabel5.setText("New password");

        jLabel6.setText("Reenter Password");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtRetypePassword)
                    .addComponent(txtNewPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtRetypePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit16x16.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFocusable(false);
        btnCancel.setOpaque(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnValidate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/valid.png"))); // NOI18N
        btnValidate.setText("OK");
        btnValidate.setEnabled(false);
        btnValidate.setFocusable(false);
        btnValidate.setOpaque(false);
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parameter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabel7.setText("Login name");

        jLabel4.setText("Old password");

        txtOldPassword.setEnabled(false);

        txtLoginID.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLoginID, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtLoginID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnValidate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, 0, 414, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnValidate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        quitWithoutChange();
        this.dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
        try {
            if (checkLoginValidity()) {
                if (checkOldPasswordValidity()) {
                    if (!String.copyValueOf(txtNewPassword.getPassword()).equals(String.copyValueOf(txtRetypePassword.getPassword()))) {
                        JOptionPane.showMessageDialog(this, "The entered password and the repeated one are not "
                                + "identical", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (String.valueOf(txtNewPassword.getPassword()).length() < 6) {
                            JOptionPane.showMessageDialog(this, "The password can't be less than 6 characters ..."
                                    + "", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            String statusUser = "OldUser";
                            try (PreparedStatement ps = ConnectDB.con.prepareStatement("UPDATE userlist "
                                    + "SET password =? WHERE IDuser =?")) {
                                ps.setString(1, ConnectDB.crypter(String.copyValueOf(txtNewPassword.getPassword())));
                                ps.setInt(2, Integer.parseInt(idUser));
                                if (ps.executeUpdate() == 1) {
                                    try (PreparedStatement ps1 = ConnectDB.con.prepareStatement("UPDATE userlist SET status =? WHERE IDuser =?")) {
                                        ps1.setString(1, statusUser);
                                        ps1.setInt(2, Integer.parseInt(idUser));
                                        ps1.executeUpdate();
                                        JOptionPane.showMessageDialog(this, "The password was successfully updated"
                                                + "", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                                        this.dispose();
                                    }
                                }
                            } catch (SQLException ex) {
                                ConnectDB.catchSQLException(ex);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
}//GEN-LAST:event_btnValidateActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        quitWithoutChange();
    }//GEN-LAST:event_formWindowClosing

    private void quitWithoutChange() {
        JOptionPane.showMessageDialog(this, "<html> <FONT size = 4> A new user is "
                + "forced to change the \"Password\" received from the system administrator. <br>"
                + "Security and privacy of information in this system is taken considerably. <br>"
                + "In case, you don't change your password, the next time you <br>"
                + "connect to the system, you will be asked again <font color=#ff0000>"
                + "to do so. </FONT>"
                + "</FONT> </html>", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private boolean checkOldPasswordValidity() throws SQLException {
        boolean unique = true;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT IDuser, login, password "
                + "FROM userlist WHERE login =?")) {
            ps.setString(1, txtLoginID.getText());
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (!ConnectDB.decrypter(ConnectDB.res.getString("password")).equals(
                        String.copyValueOf(txtOldPassword.getPassword()))) {
                    idUser = ConnectDB.res.getString("IDuser");
                } else {
                    JOptionPane.showMessageDialog(this, "The old password entered is not valid ...", ""
                            + "Warning", JOptionPane.WARNING_MESSAGE);
                    unique = false;
                }
            }
        }
        return unique;
    }

    private boolean checkLoginValidity() throws SQLException {
        boolean trouve = false;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT login FROM userlist")) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (ConnectDB.res.getString("login").equalsIgnoreCase(txtLoginID.getText())) {
                    trouve = true;
                    break;
                }
            }
        }
        if (!trouve) {
            JOptionPane.showMessageDialog(this, "The user \"" + txtLoginID.getText() + ""
                    + "\" does not exist in the database", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return trouve;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnValidate;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtLoginID;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JPasswordField txtOldPassword;
    private javax.swing.JPasswordField txtRetypePassword;
    // End of variables declaration//GEN-END:variables
    String idUser = "";
}
