package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import login.Identification;
import smartfactoryV2.ConnectDB;

public class PasswordForgot extends javax.swing.JFrame {

    public PasswordForgot(javax.swing.JDialog parent) {
        ConnectDB.getConnectionInstance();
        this._parent = parent;
        parent.setVisible(false);
        initComponents();
        Timer time = new Timer(150, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtLoginID.getText().isEmpty()) {
                        if (checkLoginValidity()) {
                            if (!txtAnswer.getText().isEmpty()) {
                                btnValidate.setEnabled(true);
                            } else {
                                btnValidate.setEnabled(false);
                            }
                        }
                    } else {
                        lblMessage.setForeground(Color.BLUE);
                        lblMessage.setText("Type your login name ...");
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        time.start();
//        txtLoginID.requestFocus();
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icons/gpgsm(9).png")).getImage());
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtQuestion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtAnswer = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLoginID = new javax.swing.JTextField();
        lblMessage = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password search");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(1, 6, 30));

        jPanel4.setBackground(new java.awt.Color(1, 6, 30));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informations", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        txtQuestion.setEditable(false);
        txtQuestion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtQuestion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Answer");

        txtAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnswerActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Login Name");

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(0, 153, 51));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMessage.setText("Type your login name ...");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(23, 23, 23)
                        .addComponent(txtAnswer))
                    .addComponent(txtQuestion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLoginID)))
                .addGap(13, 13, 13))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLoginID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                        .addComponent(btnCancel))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnValidate});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        this.dispose();
        this._parent.setVisible(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
        boolean answer = false;
        String pwd = "";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT password, answer \n"
                + "FROM userlist WHERE login =?")) {
            ps.setString(1, txtLoginID.getText());
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                if (ConnectDB.decrypter(res.getString("answer")).equalsIgnoreCase(txtAnswer.getText())) {
                    answer = true;
                    pwd = ConnectDB.decrypter(res.getString("password"));
                }
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
        if (answer) {
            JOptionPane.showMessageDialog(this, new StringBuilder("Your password is ").append(pwd).toString(),
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            this._parent.setVisible(true);
            Identification.txtLogin.setText(txtLoginID.getText());
            Identification.txtPassword.setText(pwd);
        } else {
            JOptionPane.showMessageDialog(this, new StringBuilder("Your answer is not correct: ...").append(pwd).toString(),
                    "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnValidateActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this._parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnswerActionPerformed
        btnValidateActionPerformed(evt);
    }//GEN-LAST:event_txtAnswerActionPerformed

    private boolean checkLoginValidity() throws SQLException {
        boolean find = false;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT login, question FROM userlist")) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                if (res.getString("login").equalsIgnoreCase(txtLoginID.getText())) {
                    find = true;
                    lblMessage.setForeground(new Color(0, 153, 51));
                    lblMessage.setText("Answer this question :");
                    txtQuestion.setText(ConnectDB.decrypter(res.getString("question")));
//            txtAnswer.requestFocus();
                    break;
                }
            }
        }
        if (!find) {
            txtQuestion.setText("");
            lblMessage.setForeground(Color.BLACK);
            lblMessage.setText(new StringBuilder("<html>The user <FONT color=#FF0000>").
                    append(txtLoginID.getText()).append("</FONT> does not exists in the database ...</html>").toString());
        }
        return find;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnValidate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JTextField txtAnswer;
    private javax.swing.JTextField txtLoginID;
    private javax.swing.JTextField txtQuestion;
    // End of variables declaration//GEN-END:variables
    private final JDialog _parent;
}
