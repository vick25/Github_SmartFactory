package target;

import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.RolloverTableUtils;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
                btnCloseActionPerformed(null);
            }
        });
        tableTarget.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                int firstRow = e.getFirstRow(), lastRow = e.getLastRow(), index = e.getColumn();
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
//                                    if (anyChange) {
////                                        System.out.println(true);
//                                    }
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
        tableTarget.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getSource() == tableTarget.getSelectionModel() && tableTarget.getRowSelectionAllowed()) {
                    if (tableTarget.getSelectedRow() > -1) {
                        try {
                            _machine = tableTarget.getValueAt(tableTarget.getSelectedRow(), 1).toString();
                            targetOptions = new TargetOptions(_machine);
                            scrlTargetOptions.setViewportView(targetOptions);
                            _tableTargetOption = targetOptions.getSortableTable();
                            setTargetOptionsProductionTime();
                        } catch (SQLException ex) {
                            ConnectDB.catchSQLException(ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(Target.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        scrlTargetOptions.setViewportView(null);
                    }
                }
//                else if (e.getSource() == tableTarget.getColumnModel().getSelectionModel()
//                        && tableTarget.getColumnSelectionAllowed()) {
//                    int first = e.getFirstIndex();
//                    int last = e.getLastIndex();                    
//                    System.out.println("2" + first + " -- " + last);
//                }
//                if (e.getValueIsAdjusting()) {
//                    System.out.println("The mouse button has not yet been released");
//                }
            }
        });
        if (null != ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, targetUnit)) {
            switch (ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, targetUnit)) {
                case "second":
                    radSecond.setSelected(true);
                    break;
                case "minute":
                    radMinute.setSelected(true);
                    break;
                default:
                    radHour.setSelected(true);
                    break;
            }
        }
        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnLoadSetting = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        radSecond = new javax.swing.JRadioButton();
        radMinute = new javax.swing.JRadioButton();
        radHour = new javax.swing.JRadioButton();
        scrlTargetOptions = new javax.swing.JScrollPane();
        btnClose = new com.jidesoft.swing.JideButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Machines Target Value (min)");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnLoadSetting.setBackground(new java.awt.Color(255, 255, 255));
        btnLoadSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/valider.png"))); // NOI18N
        btnLoadSetting.setText("Load Settings");
        btnLoadSetting.setFocusable(false);
        btnLoadSetting.setIconTextGap(8);
        btnLoadSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadSettingActionPerformed(evt);
            }
        });

        jLabel1.setText("<html><font color=red>Enter at least one target value per machine and choose a target unit.</font>");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Target Units", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        radSecond.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radSecond);
        radSecond.setText("per second");
        radSecond.setFocusable(false);
        radSecond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radSecondActionPerformed(evt);
            }
        });

        radMinute.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radMinute);
        radMinute.setText("per minute");
        radMinute.setFocusable(false);
        radMinute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radMinuteActionPerformed(evt);
            }
        });

        radHour.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radHour);
        radHour.setSelected(true);
        radHour.setText("per hour");
        radHour.setFocusable(false);
        radHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radHourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radSecond)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radMinute)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radHour)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(radSecond)
                .addComponent(radMinute)
                .addComponent(radHour))
        );

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit16x16.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setFocusable(false);
        btnClose.setOpaque(true);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoadSetting))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrlTargetOptions)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(scrlTargetOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoadSetting)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadSettingActionPerformed
        try {
            if (checkEmptyTable()) {
                int machineID = ConnectDB.getIDMachine(_machine);
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("DELETE FROM timebreaks\n"
                        + "WHERE HwNo =?")) {
                    ps.setInt(1, machineID);
                    ps.executeUpdate();
                }
                for (int i = 0; i < _tableTargetOption.getRowCount(); i++) {
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement("INSERT INTO timebreaks\n"
                            + "VALUE (?,?,?,?,?)")) {
                        ps.setObject(1, null);
                        ps.setInt(2, machineID);
                        ps.setInt(3, getBreaksNameNo(_tableTargetOption.getValueAt(i, 3).toString()));
                        ps.setTime(4, new java.sql.Time(sdf.parse(sdf.format(_tableTargetOption.getValueAt(i, 1))).getTime()));
                        ps.setTime(5, new java.sql.Time(sdf.parse(sdf.format(_tableTargetOption.getValueAt(i, 2))).getTime()));
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            scrlTargetOptions.setViewportView(null);
                            tableTarget.removeRowSelectionInterval(0, tableTarget.getRowCount() - 1);
                            tableTarget.repaint();
                        }
                    }
                }
                String startTime = sdf.format(targetOptions.getDsStartTime().getModel().getValue()),
                        endTime = sdf.format(targetOptions.getDsEndTime().getModel().getValue());
                if (checkMachineStartEndTimeExist(machineID)) {
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement("UPDATE startendtime\n"
                            + "SET StartTime =?,\n"
                            + "EndTime =?\n"
                            + "WHERE HwNo =?")) {
                        ps.setTime(1, new java.sql.Time(sdf.parse(startTime).getTime()));
                        ps.setTime(2, new java.sql.Time(sdf.parse(endTime).getTime()));
                        ps.setInt(3, machineID);
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            System.out.println("successful");
                        }
                    }
                } else {
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement("INSERT INTO startendtime\n"
                            + "VALUES (?,?,?,?)")) {
                        ps.setObject(1, null);
                        ps.setInt(2, machineID);
                        ps.setTime(3, new java.sql.Time(sdf.parse(startTime).getTime()));
                        ps.setTime(4, new java.sql.Time(sdf.parse(endTime).getTime()));
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            System.out.println("successful");
                        }
                    }
                }
                for (int i = 0; i < tableTarget.getRowCount(); i++) {
                    if (_machine.equals(tableTarget.getValueAt(i, 1))) {
                        tableTarget.setValueAt(startTime, i, 2);
                        tableTarget.setValueAt(endTime, i, 3);
                    }
                }
            }
        } catch (NullPointerException e) {
            return;
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(Target.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLoadSettingActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if (tableTarget.isEditing()) {
            tableTarget.getCellEditor().stopCellEditing();
        }
        if (anyChange) {
            tableRow = tableTarget.getModel().getRowCount();
            int res = 0;
            for (int row = 0; row < tableRow; row++) {
                String machine = tableTarget.getValueAt(row, 1).toString();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("INSERT INTO target\n"
                        + "VALUES (?,?,?,?)")) {
                    int col = 4;//column start at 4 for the production values
                    double targetValue = 0d;
                    for (ProductionType type : ProductionType.values()) {
                        String query = "SELECT DISTINCT c.ConfigNo\n"
                                + "FROM configuration c, hardware h\n"
                                + "WHERE h.HwNo = c.HwNo\n"
                                + "AND c.AvMinMax = '" + type.toString() + "'\n"
                                + "AND h.Machine = '" + tableTarget.getValueAt(row, 1) + "'"
                                + "AND c.Active = 1 ORDER BY h.HwNo ASC";
                        int configNo = -1;
                        try (PreparedStatement ps1 = ConnectDB.con.prepareStatement(query)) {
                            ConnectDB.res = ps1.executeQuery();
                            while (ConnectDB.res.next()) {
                                configNo = ConnectDB.res.getInt(1);
                            }
                        }
                        if (!checkTargetExist(machine, configNo)) {
                            ps.setObject(1, null);
                            ps.setString(2, machine);
                            ps.setInt(3, configNo);
                            if (!tableTarget.getValueAt(row, col).toString().isEmpty()) {
                                targetValue = Double.valueOf(tableTarget.getValueAt(row, col).toString());
                            }
                            ps.setDouble(4, targetValue);
                            res = ps.executeUpdate();
                            col++;
                        } else {//update
                            try (PreparedStatement psUpdate = ConnectDB.con.prepareStatement("UPDATE target\n"
                                    + "SET TargetValue =?\n"
                                    + "WHERE TargetNo =?")) {
                                if (!tableTarget.getValueAt(row, col).toString().isEmpty()) {
                                    targetValue = Double.valueOf(tableTarget.getValueAt(row, col).toString());
                                }
                                psUpdate.setDouble(1, targetValue);
                                psUpdate.setInt(2, targetNo);
                                res = psUpdate.executeUpdate();
                                col++;
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
    }//GEN-LAST:event_btnCloseActionPerformed

    private void radSecondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radSecondActionPerformed
        this.setTitle("Machines Target Value (second)");
        targetUnit = "second";
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, targetUnit);
//        changeTableTargetValue("second");
    }//GEN-LAST:event_radSecondActionPerformed

    private void radMinuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radMinuteActionPerformed
        this.setTitle("Machines Target Value (minute)");
        targetUnit = "minute";
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, targetUnit);
//        changeTableTargetValue("minute");
    }//GEN-LAST:event_radMinuteActionPerformed

    private void radHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radHourActionPerformed
        this.setTitle("Machines Target Value (hour)");
        targetUnit = "hour";
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, targetUnit);
//        changeTableTargetValue("hour");
    }//GEN-LAST:event_radHourActionPerformed

    private void loadTargetTable() throws SQLException {
        clearTable(tableTarget);
        tableRow = tableTarget.getModel().getRowCount();
        while (tableRow > 0) {
            refModel.removeNewRow(--tableRow);
        }
        nbLine = 1;
        n = 0;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Machine\n"
                + "FROM hardware WHERE HwNo > ? ORDER BY HwNo ASC")) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (nbLine > tableTarget.getModel().getRowCount()) {
                    refModel.addNewRow();
                }
                tableTarget.setValueAt(nbLine, n, 0);
                tableTarget.setValueAt(ConnectDB.res.getString(1), n, 1);
                n++;
                nbLine++;
            }
        }
        if (tableTarget.getRowCount() > 0) {
            tableRow = tableTarget.getModel().getRowCount();
            for (int i = 0; i < tableRow; i++) {
                String machineName = tableTarget.getValueAt(i, 1).toString();
                for (ProductionType type : ProductionType.values()) {
                    String _type = type.toString();
                    String query = "SELECT DISTINCT c.ConfigNo\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND c.AvMinMax = '" + _type + "'\n"
                            + "AND h.Machine = '" + machineName + "'"
                            + "AND c.Active = 1 ORDER BY h.HwNo ASC";
                    int configNo = -1;
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                        ConnectDB.res = ps.executeQuery();
                        while (ConnectDB.res.next()) {
                            configNo = ConnectDB.res.getInt(1);
                        }
                    }
                    query = "SELECT TargetValue FROM target WHERE ConfigNo =?";
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                        ps.setInt(1, configNo);
                        ConnectDB.res = ps.executeQuery();
                        while (ConnectDB.res.next()) {
                            if (_type.equalsIgnoreCase("rate")) {
                                tableTarget.setValueAt(ConnectDB.res.getDouble(1), i, 4);
                            } else {
                                tableTarget.setValueAt(ConnectDB.res.getDouble(1), i, 5);
                            }
                        }
                    }
                    query = "SELECT StartTime, EndTime FROM startendtime\n"
                            + "WHERE HwNo =?";
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                        ps.setInt(1, ConnectDB.getIDMachine(machineName));
                        ConnectDB.res = ps.executeQuery();
                        while (ConnectDB.res.next()) {
                            tableTarget.setValueAt(ConnectDB.res.getString(1).substring(0, 5), i, 2);
                            tableTarget.setValueAt(ConnectDB.res.getString(2).substring(0, 5), i, 3);
                        }
                    }
                }
            }
        }
    }

    private void fillArrayTarget() {
        int targetRow = tableTarget.getRowCount();
        cumulTargetValues = new double[targetRow];
        rateTargetValues = new double[targetRow];
        for (int i = 0; i < targetRow; i++) {
            cumulTargetValues[i] = (double) tableTarget.getValueAt(i, 5);
            rateTargetValues[i] = (double) tableTarget.getValueAt(i, 4);
        }
    }

    private void setTargetOptionsProductionTime() throws ParseException {
        String startTime = tableTarget.getValueAt(tableTarget.getSelectedRow(), 2).toString();
        String endTime = tableTarget.getValueAt(tableTarget.getSelectedRow(), 3).toString();
        if (startTime.isEmpty()) {
            targetOptions.getDsStartTime().setValue(sdf.parse(sdf.format(ConnectDB.CALENDAR.getTime())));
        } else {
            targetOptions.getDsStartTime().setValue(sdf.parse(startTime));
        }
        if (endTime.isEmpty()) {
            targetOptions.getDsEndTime().setValue(sdf.parse(sdf.format(ConnectDB.CALENDAR.getTime())));
        } else {
            targetOptions.getDsEndTime().setValue(sdf.parse(endTime));
        }
    }

    private boolean checkMachineStartEndTimeExist(int machineID) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT HwNo FROM startendtime")) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (machineID == ConnectDB.res.getInt(1)) {
                    return true;
                }
            }
        }
        return false;
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

    private void changeTableTargetValue(String timeOptions) {
        fillArrayTarget();
        for (int i = 0; i < tableTarget.getRowCount(); i++) {
            int j = 4;
            while (j <= 5) {
                double value = (double) tableTarget.getValueAt(i, j);
                switch (timeOptions) {
                    case "second":
                        if ("minute".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 60, i, j);
                        } else if ("hour".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 3600, i, j);
                        } else {
                            tableTarget.setValueAt(value / 3600, i, j);
                        }
                        break;
                    case "minute":
                        if ("second".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 60, i, j);
                        } else if ("hour".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 3600, i, j);
                        } else {
                            tableTarget.setValueAt(value / 60, i, j);
                        }
                        break;
                    case "hour":
                        if ("minute".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 60, i, j);
                        } else if ("second".equals(targetUnit)) {
                            tableTarget.setValueAt(value * 3600, i, j);
                        } else {
                            tableTarget.setValueAt(value * 60, i, j);
                        }
                        break;
                }
                j++;
            }
        }
    }

    private void clearTable(SortableTable st) {
        nbLine = 1;
        for (int i = 0; i < st.getRowCount(); i++) {
            for (int j = 0; j < st.getColumnCount(); j++) {
                st.setValueAt("", i, j);
            }
        }
    }

    public boolean checkEmptyTable() {
        if (_tableTargetOption.isEditing()) {
            _tableTargetOption.getCellEditor().stopCellEditing();
        }
        if (_tableTargetOption.getModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "The break time table is empty.", "Target",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            for (int i = 0; i < _tableTargetOption.getRowCount(); i++) {
                if (_tableTargetOption.getValueAt(i, 1).toString().isEmpty()
                        || _tableTargetOption.getValueAt(i, 2).toString().isEmpty()
                        || _tableTargetOption.getValueAt(i, 3).toString().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Table cells are empty!", "Target",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    private void setTableTarget() {
        refModel = new TableModelTarget();
        tableTarget = new SortableTable(refModel);
        tableTarget.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        tableTarget.getTableHeader().setReorderingAllowed(false);
        tableTarget.setFocusable(true);
        tableTarget.setSortingEnabled(false);
        tableTarget.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTarget.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        tableTarget.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        tableTarget.getTableHeader().setBackground(Color.BLUE);
        tableTarget.setRowHeight(16);
        tableTarget.setShowGrid(true);
        tableTarget.setFillsViewportHeight(true);
        tableTarget.setIntercellSpacing(new Dimension(0, 0));
        RolloverTableUtils.install(tableTarget);
        setTableColumn();
        TableUtils.autoResizeAllColumns(tableTarget);
        tableTarget.repaint();
        jScrollPane1.setViewportView(tableTarget);
    }

    private void setTableColumn() {
        TableColumn col = tableTarget.getColumnModel().getColumn(0);
        col.setMinWidth(30);
        col.setMaxWidth(30);
        col.setResizable(false);
    }

    private int getBreaksNameNo(String breaksName) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT BreaksNo FROM breaks\n"
                + "WHERE BreaksName =?")) {
            ps.setString(1, breaksName);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                return ConnectDB.res.getInt(1);
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Target dialog = null;
                try {
                    dialog = new Target(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnClose;
    private javax.swing.JButton btnLoadSetting;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radHour;
    private javax.swing.JRadioButton radMinute;
    private javax.swing.JRadioButton radSecond;
    private javax.swing.JScrollPane scrlTargetOptions;
    // End of variables declaration//GEN-END:variables
    private int tableRow = 0, targetNo = -1, nbLine = 1, n = 0;
    private SortableTable tableTarget, _tableTargetOption;
    private TableModelTarget refModel;
    private TargetOptions targetOptions;
    private String _machine;
    private String targetUnit = "hour";
    private double[] cumulTargetValues, rateTargetValues;
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    public static boolean anyChange, targetFound = true;
}
