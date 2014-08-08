package productionQuickView;

import com.jidesoft.grid.RolloverTableUtils;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelTarget;

/**
 *
 * @author Victor Kadiata
 */
public class Target extends javax.swing.JDialog {

    public Target(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        ConnectDB.getConnectionInstance();
        initComponents();
        setTableTarget();
        loadTargetTable();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                btnAddActionPerformed(null);
            }
        });
        tableTarget.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                int index = e.getColumn();
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        for (int i = firstRow; i <= lastRow; i++) {
                            System.out.println(i);
                        }
                        break;
                    case TableModelEvent.UPDATE:
                        if (firstRow == TableModelEvent.HEADER_ROW) {
                            if (index == TableModelEvent.ALL_COLUMNS) {
                                System.out.println("A column was added");
                            } else {
                                System.out.println(index + "in header changed");
                            }
                        } else {
                            for (int i = firstRow; i <= lastRow; i++) {
                                if (index == TableModelEvent.ALL_COLUMNS) {
                                    System.out.println("All columns have changed");
                                } else {
                                    if (anyChange) {
                                        System.out.println(true);
                                    }
                                    anyChange = true;
                                }
                            }
                        }
                        break;
                    case TableModelEvent.DELETE:
                        for (int i = firstRow; i <= lastRow; i++) {
                            System.out.println(i);
                        }
                        break;
                }
            }
        });
        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Production Quick View [Load Machines & Target (Hour)]");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/valider.png"))); // NOI18N
        btnAdd.setText("Load Machines");
        btnAdd.setFocusable(false);
        btnAdd.setIconTextGap(8);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("<html><font color=red>Machine(s) load with at least one target</font>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addContainerGap())
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (tableTarget.isEditing()) {
            tableTarget.getCellEditor().stopCellEditing();
        }
        if (anyChange) {
            tableRow = tableTarget.getModel().getRowCount();
            int res = 0;
            for (int i = 0; i < tableRow; i++) {
                String machine = tableTarget.getValueAt(i, 1).toString();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("INSERT INTO target\n"
                        + "VALUES (null,?,?,?)")) {
                    int j = 2;
                    double targetValue = 0d;
                    for (productionType type : productionType.values()) {
                        String query = "SELECT DISTINCT c.ConfigNo\n"
                                + "FROM configuration c, hardware h\n"
                                + "WHERE h.HwNo = c.HwNo\n"
                                + "AND c.AvMinMax = '" + type.toString() + "'\n"
                                + "AND h.Machine = '" + tableTarget.getValueAt(i, 1) + "'"
                                + "AND c.Active = 1 ORDER BY h.HwNo ASC";
                        int configNo = -1;
                        try (PreparedStatement ps1 = ConnectDB.con.prepareStatement(query)) {
                            ConnectDB.res = ps1.executeQuery();
                            while (ConnectDB.res.next()) {
                                configNo = ConnectDB.res.getInt(1);
                            }
                        }
                        if (!checkTargetExist(machine, configNo)) {
                            ps.setString(1, machine);
                            ps.setInt(2, configNo);
                            if (!tableTarget.getValueAt(i, j).toString().isEmpty()) {
                                targetValue = Double.valueOf(tableTarget.getValueAt(i, j).toString());
                            }
                            ps.setDouble(3, targetValue);
                            res = ps.executeUpdate();
                            j++;
                        } else {//update
                            try (PreparedStatement psUpdate = ConnectDB.con.prepareStatement("UPDATE target\n"
                                    + "SET TargetValue =?\n"
                                    + "WHERE TargetNo =?")) {
                                if (!tableTarget.getValueAt(i, j).toString().isEmpty()) {
                                    targetValue = Double.valueOf(tableTarget.getValueAt(i, j).toString());
                                }
                                psUpdate.setDouble(1, targetValue);
                                psUpdate.setInt(2, targetNo);
                                res = psUpdate.executeUpdate();
                                j++;
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this, "Please verify for correct inputs, not empty value",
                            "Target", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
            if (res == 1) {
                this.dispose();
            }
        }
        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void loadTargetTable() throws SQLException {
        clearTable(tableTarget);
        tableRow = tableTarget.getModel().getRowCount();
        while (tableRow > 0) {
            refModel.removeNewRow(--tableRow);
        }
        nbLigne = 1;
        n = 0;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Machine\n"
                + "FROM hardware WHERE HwNo > ? ORDER BY HwNo ASC")) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (nbLigne > tableTarget.getModel().getRowCount()) {
                    refModel.addNewRow();
                }
                tableTarget.setValueAt(nbLigne, n, 0);
                tableTarget.setValueAt(ConnectDB.res.getString(1), n, 1);
                n++;
                nbLigne++;
            }
        }
        if (tableTarget.getRowCount() > 0) {
            tableRow = tableTarget.getModel().getRowCount();
            for (int i = 0; i < tableRow; i++) {
                String machine = tableTarget.getValueAt(i, 1).toString();
                for (productionType type : productionType.values()) {
                    String _type = type.toString();
                    String query = "SELECT DISTINCT c.ConfigNo\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND c.AvMinMax = '" + _type + "'\n"
                            + "AND h.Machine = '" + machine + "'"
                            + "AND c.Active = 1 ORDER BY h.HwNo ASC";
                    int configNo = -1;
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                        ConnectDB.res = ps.executeQuery();
                        while (ConnectDB.res.next()) {
                            configNo = ConnectDB.res.getInt(1);
                        }
                    }
                    query = "SELECT targetValue FROM target\n"
                            + "WHERE ConfigNo =?";
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                        ps.setInt(1, configNo);
                        ConnectDB.res = ps.executeQuery();
                        while (ConnectDB.res.next()) {
                            if (_type.equalsIgnoreCase("rate")) {
                                tableTarget.setValueAt(ConnectDB.res.getDouble(1), i, 2);
                            } else {
                                tableTarget.setValueAt(ConnectDB.res.getDouble(1), i, 3);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkTargetExist(String machine, int configNo) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT TargetNo, Machine, ConfigNo FROM target")) {
            ConnectDB.res = ps.executeQuery();
            targetNo = -1;
            while (ConnectDB.res.next()) {
                if (ConnectDB.res.getString(2).equalsIgnoreCase(machine)
                        && ConnectDB.res.getInt(3) == configNo) {
                    targetNo = ConnectDB.res.getInt(1);
                    return true;
                }
            }
        }
        return false;
    }

    private void clearTable(SortableTable st) {
        nbLigne = 1;
        nRow = 1;
        for (int i = 0; i < st.getRowCount(); i++) {
            for (int j = 0; j < st.getColumnCount(); j++) {
                st.setValueAt("", i, j);
            }
        }
    }

    private void setTableTarget() {
        refModel = new TableModelTarget();
        tableTarget = new SortableTable(refModel);
        tableTarget.getTableHeader().setReorderingAllowed(false);
        tableTarget.setFocusable(false);
        tableTarget.setSortingEnabled(false);
        tableTarget.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTarget.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        tableTarget.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
//        tableTarget.getTableHeader().setBackground(Color.BLUE);
        tableTarget.setRowHeight(16);
        tableTarget.setShowGrid(true);
        tableTarget.setFillsViewportHeight(true);
        tableTarget.setIntercellSpacing(new Dimension(0, 0));
        RolloverTableUtils.install(tableTarget);
        setTableColumn();
        tableTarget.repaint();
        jScrollPane1.setViewportView(tableTarget);
    }

    private void setTableColumn() {
        TableColumn col = tableTarget.getColumnModel().getColumn(0);
        col.setMinWidth(45);
        col.setMaxWidth(45);
        col.setResizable(false);
    }

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                Target dialog = null;
//                try {
//                    dialog = new Target(new javax.swing.JFrame(), true);
//                } catch (SQLException ex) {
//                    ConnectDB.catchSQLException(ex);
//                }
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    JPanel _panel;
    SortableTable tableTarget;
    TableModelTarget refModel;
    int tableRow = 0, targetNo = -1, nbLigne = 1, n = 0, iRow = 0, nRow = 1;
    public static boolean anyChange, targetFound = true;

    enum productionType {

        RATE, CUMULATIVE;
    }
}
