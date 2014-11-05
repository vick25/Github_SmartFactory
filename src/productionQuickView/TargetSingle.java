package productionQuickView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class TargetSingle extends javax.swing.JDialog {

    public static boolean isAnyChangeOccured() {
        return anyChangeOccured;
    }

    public TargetSingle(java.awt.Frame parent, boolean modal, String myMachineName) throws SQLException {
        super(parent, modal);
        initComponents();
        this.machine = myMachineName;
        this.setTitle("Add Target Value (" + ConnectDB.getDBTargetUnit() + ")");
        loadValues();
        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAddTarget = new javax.swing.JButton();
        lblMessage = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtProductionRate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTotalProduction = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Target Value (Hour)");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnAddTarget.setBackground(new java.awt.Color(255, 255, 255));
        btnAddTarget.setText("Add");
        btnAddTarget.setFocusable(false);
        btnAddTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTargetActionPerformed(evt);
            }
        });

        lblMessage.setForeground(new java.awt.Color(153, 0, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Targets", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel1.setText("Production Rate:");

        jLabel2.setText("Total Production:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProductionRate)
                    .addComponent(txtTotalProduction))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtProductionRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtTotalProduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(btnAddTarget))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddTarget))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTargetActionPerformed
        try {
            if (isValueDifferent(txtProductionRate.getText(), "rate")) {
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("UPDATE target \n"
                        + "SET TargetValue =? \n"
                        + "WHERE Machine =? AND ConfigNo =(SELECT ConfigNo FROM configuration WHERE \n"
                        + "AvMinMax =? AND HwNo =?)")) {
                    ps.setDouble(1, Double.valueOf(txtProductionRate.getText()));
                    ps.setString(2, machine);
                    ps.setString(3, "rate");
                    ps.setInt(4, ConnectDB.getIDMachine(machine));
                    ps.executeUpdate();
                    anyChangeOccured = true;
                }
            }
            if (isValueDifferent(txtTotalProduction.getText(), "cumulative")) {
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("UPDATE target \n"
                        + "SET TargetValue =? \n"
                        + "WHERE Machine =? AND ConfigNo =(SELECT ConfigNo FROM configuration WHERE \n"
                        + "AvMinMax =? AND HwNo =?)")) {
                    ps.setDouble(1, Double.valueOf(txtTotalProduction.getText()));
                    ps.setString(2, machine);
                    ps.setString(3, "cumulative");
                    ps.setInt(4, ConnectDB.getIDMachine(machine));
                    ps.executeUpdate();
                    anyChangeOccured = true;
                }
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnAddTargetActionPerformed

    private void loadValues() throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT TargetValue FROM target \n"
                + "WHERE Machine =? AND ConfigNo =(SELECT ConfigNo FROM configuration WHERE \n"
                + "AvMinMax =? AND HwNo =?)")) {
            ps.setString(1, machine);
            ps.setString(2, "Cumulative");
            ps.setInt(3, ConnectDB.getIDMachine(machine));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                txtTotalProduction.setText(resultSet.getString(1));
            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT TargetValue FROM target \n"
                + "WHERE Machine =? AND ConfigNo =(SELECT ConfigNo FROM configuration WHERE \n"
                + "AvMinMax =? AND HwNo =?)")) {
            ps.setString(1, machine);
            ps.setString(2, "Rate");
            ps.setInt(3, ConnectDB.getIDMachine(machine));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                txtProductionRate.setText(resultSet.getString(1));
            }
        }
    }

    private boolean isValueDifferent(String text, String AvMinMax) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT TargetValue FROM target \n"
                + "WHERE Machine =? AND ConfigNo =(SELECT ConfigNo FROM configuration WHERE \n"
                + "AvMinMax =? AND HwNo =?)")) {
            ps.setString(1, machine);
            ps.setString(2, AvMinMax);
            ps.setInt(3, ConnectDB.getIDMachine(machine));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (Objects.equals(Double.valueOf(text), Double.valueOf(resultSet.getString(1)))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TargetSingle dialog = new TargetSingle(new javax.swing.JFrame(), true, null);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(TargetSingle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTarget;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JTextField txtProductionRate;
    private javax.swing.JTextField txtTotalProduction;
    // End of variables declaration//GEN-END:variables
    private final String machine;
    private static boolean anyChangeOccured;
}
