package target;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class AddBreakName extends javax.swing.JDialog {

    public String getBreakName() {
        return breakName;
    }

    public AddBreakName(java.awt.Frame parent, boolean modal, javax.swing.JPanel paneParent) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(paneParent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAddBreakName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Break Name");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Break Name: ");

        txtAddBreakName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddBreakNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAddBreakName, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAddBreakName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddBreakNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddBreakNameActionPerformed
        try {
            breakName = ConnectDB.capitalLetter(txtAddBreakName.getText());
            if (!checkBreakNameExist(breakName)) {
                String query = "INSERT INTO breaks VALUE (?,?)";
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                    ps.setObject(1, null);
                    ps.setString(2, breakName);
                    int res = ps.executeUpdate();
                    if (res == 1) {
                        this.dispose();
                    }
                }
            } else {
                this.dispose();
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_txtAddBreakNameActionPerformed

    private boolean checkBreakNameExist(String breakName) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT BreaksName FROM breaks")) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (ConnectDB.res.getString(1).equalsIgnoreCase(breakName)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAddBreakName;
    // End of variables declaration//GEN-END:variables
    private String breakName;
}
