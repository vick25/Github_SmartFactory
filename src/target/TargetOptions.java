package target;

import com.jidesoft.grid.AbstractTableCellEditorRenderer;
import com.jidesoft.grid.CellRolloverSupport;
import com.jidesoft.grid.RolloverTableUtils;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.spinner.DateSpinner;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXComboBox;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelBreaksTime;

public class TargetOptions extends javax.swing.JPanel {

    public SortableTable getSortableTable() {
        return sortableTable;
    }

    public TableModelBreaksTime getTableModelBreaksTime() {
        return tableModelBreaksTime;
    }

    public DateSpinner getDsEndTime() {
        return dsEndTime;
    }

//    public void setDsEndTime(DateSpinner dsEndTime) {
//        this.dsEndTime = dsEndTime;
//        this.dsEndTime.repaint();
//    }
    public DateSpinner getDsStartTime() {
        return dsStartTime;
    }

//    public void setDsStartTime(DateSpinner dsStartTime) {
//        this.dsStartTime = dsStartTime;
//        this.dsStartTime.repaint();
//    }
    public TargetOptions(String machine) throws SQLException, ParseException {
        _machine = machine;
        initComponents();
        scrlBreakTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                _machine + " Break Times", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        this.setTable();
        this.loadMachineBreakTime(_machine);
//        getDsStartTime().setValue(new SimpleDateFormat("HH:mm").parse("20:30"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        scrlBreakTime = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dsStartTime = new com.jidesoft.spinner.DateSpinner("HH:mm", Calendar.getInstance().getTime());
        jLabel3 = new javax.swing.JLabel();
        dsEndTime = new com.jidesoft.spinner.DateSpinner("HH:mm", Calendar.getInstance().getTime());
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        spBreaksNum = new javax.swing.JSpinner();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Break Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Use this time settings as universal for all machines");
        jButton1.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlBreakTime)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scrlBreakTime, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(67, 67, 255));
        jLabel2.setText("Production Start Time:");

        dsStartTime.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(67, 67, 255));
        jLabel3.setText("Production End Time:");

        dsEndTime.setBackground(new java.awt.Color(255, 255, 255));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Machine operates during break periods?");
        jCheckBox1.setFocusable(false);

        jLabel1.setText("Add breaks:");

        spBreaksNum.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        spBreaksNum.setFocusable(false);
        spBreaksNum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spBreaksNumStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dsEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spBreaksNum, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dsStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jCheckBox1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jCheckBox1)
                    .addComponent(dsStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(dsEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(spBreaksNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spBreaksNumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spBreaksNumStateChanged
        if (catLoad) {
            if ((int) spBreaksNum.getValue() > sortableTable.getModel().getRowCount()) {
                while ((int) spBreaksNum.getValue() > sortableTable.getRowCount()) {
                    tableModelBreaksTime.addNewRow();
                    tableModelBreaksTime.setValueAt(spBreaksNum.getValue(), (int) spBreaksNum.getValue() - 1, 0);
                }
            } else if (sortableTable.getRowCount() > (int) spBreaksNum.getValue()) {
                int nbTable = sortableTable.getRowCount();
                while (nbTable > (int) spBreaksNum.getValue()) {
                    tableModelBreaksTime.removeNewRow();
                    --nbTable;
                }
            }
        }
    }//GEN-LAST:event_spBreaksNumStateChanged

    private void setTable() throws SQLException, ParseException {
        tableModelBreaksTime = new TableModelBreaksTime();
        sortableTable = new SortableTable(tableModelBreaksTime);
        setEditorTable(getBreakName(), sortableTable, 3);//set the cell editor for the Break Name column
        sortableTable.getTableHeader().setReorderingAllowed(false);
        sortableTable.setFocusable(true);
        sortableTable.setSortingEnabled(false);
        sortableTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sortableTable.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        sortableTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 11));
//        sortableTable.getTableHeader().setBackground(Color.BLUE);
        sortableTable.setRowHeight(16);
        sortableTable.setShowGrid(true);
        sortableTable.setFillsViewportHeight(true);
        sortableTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        sortableTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        sortableTable.setIntercellSpacing(new Dimension(0, 0));
        RolloverTableUtils.install(sortableTable);
        setTableColumns();
        scrlBreakTime.setViewportView(sortableTable);
    }

    private void setEditorTable(String[] tab, JTable table, int column) {
        final JXComboBox comboBox = new JXComboBox(tab);
        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().toString().equals("Add ...") && comboBox.getSelectedIndex() == comboBox.getItemCount() - 1) {
                    comboBox.setSelectedIndex(-1);
                    try {
                        AddBreakName abn = new AddBreakName(null, true, TargetOptions.this);
                        abn.setVisible(true);
                        setEditorTable(getBreakName(), sortableTable, 3);
//                        comboBox.addItem(e);
//                        comboBox.setSelectedIndex(getBreakNameIndex(comboBox, abn.getBreakName()));
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
                }
            }
        });
        table.getColumnModel().getColumn(column).setCellEditor(new DefaultCellEditor(comboBox));
    }

    private String[] getBreakName() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT DISTINCT BreaksName FROM breaks\n"
                + "WHERE BreaksName <> '' ORDER BY BreaksName")) {
            ConnectDB.res = ps.executeQuery();
            list.add(" ");
            while (ConnectDB.res.next()) {
                list.add(ConnectDB.res.getString(1));
            }
            list.add(list.size(), "Add ...");
        }
        return list.toArray(new String[list.size()]);
    }

    private void setTableColumns() throws ParseException {
        TableColumn col = sortableTable.getColumnModel().getColumn(0);
        col.setMinWidth(45);
        col.setMaxWidth(45);
        col.setResizable(false);
        col = sortableTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(150);
        col = sortableTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(150);
        col = sortableTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(140);
        col = sortableTable.getColumnModel().getColumn(4);
        col.setCellEditor(new ButtonsCellEditorRenderer());
        col.setCellRenderer(new ButtonsCellEditorRenderer());
        col.setPreferredWidth(28);
        col.setMaxWidth(28);
        col.setMinWidth(28);
        col = sortableTable.getColumnModel().getColumn(1);
        col.setCellRenderer(new MyTimeCellRenderer());
        col.setCellEditor(new MyTimeCellEditor());
        col = sortableTable.getColumnModel().getColumn(2);
        col.setCellRenderer(new MyTimeCellRenderer());
        col.setCellEditor(new MyTimeCellEditor());
    }

    private void loadMachineBreakTime(String machine) throws SQLException {
        nbLine = 1;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT StartTime, EndTime, BreaksName\n"
                + "FROM breaks, timebreaks\n"
                + "WHERE breaks.BreaksNo = timebreaks.BreaksNo\n"
                + "AND HwNo=? ORDER BY TimeBreaksNo ASC")) {
            ps.setInt(1, ConnectDB.getIDMachine(machine));
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (nbLine > sortableTable.getModel().getRowCount()) {
                    tableModelBreaksTime.addNewRow();
                }
                sortableTable.setValueAt(n + 1, n, 0);
                sortableTable.setValueAt(ConnectDB.res.getTime(1), n, 1);
                sortableTable.setValueAt(ConnectDB.res.getTime(2), n, 2);
                sortableTable.setValueAt(ConnectDB.res.getString(3), n, 3);
                nbLine++;
                n++;
            }
            catLoad = false;
            spBreaksNum.setValue(n);
            catLoad = true;
        }
    }

    private JButton createButton(Icon icon, Icon rolloverIcon) {
        JButton button = new JideButton(icon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(9, 9));
        button.setMaximumSize(new Dimension(9, 9));
        button.setMinimumSize(new Dimension(9, 9));
        button.setContentAreaFilled(false);
        button.setRolloverIcon(rolloverIcon);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setRequestFocusEnabled(false);
        return button;
    }

    private class ButtonsCellEditorRenderer extends AbstractTableCellEditorRenderer implements CellRolloverSupport {

        @Override
        public Component createTableCellEditorRendererComponent(JTable table, int row, int column) {
            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.setOpaque(true);
            panel.add(createButton(removeIcon, removeRolloverIcon));
            return panel;
        }

        @Override
        public void configureTableCellEditorRendererComponent(final JTable table, Component editorRendererComponent,
                boolean forRenderer, Object value, boolean isSelected, boolean hasFocus, final int row, final int column) {
            if (!forRenderer) {
                JButton removeButton = (JButton) (((Container) editorRendererComponent).getComponent(0));
                removeButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
//                            int count = 0;
//                            int columnToRemoveID = Integer.parseInt(tableModelBreaksTime.getValueAt(row, 0).toString());
//                            if (sortableTable.isEditing()) {
//                                sortableTable.getCellEditor().stopCellEditing();
//                            }
//                            for (int j = 0; j < tableModelBreaksTime.getRowCount(); j++) {
//                                if (Integer.parseInt(tableModelBreaksTime.getValueAt(j, 0).toString()) == columnToRemoveID) {
//                                    count++;
//                                }
//                            }
                            catLoad = false;
                            tableModelBreaksTime.removeNewRow(row);
                            spBreaksNum.setValue(row);
                            catLoad = true;
//                            if (count == 1) {
//                                for (int i = 0; i < tableModelBreaksTime.getRowCount(); i++) {
//                                    if (Integer.parseInt(tableModelBreaksTime.getValueAt(i, 1).toString()) == columnToRemoveID) {
//                                        tableModelBreaksTime.setValueAt(false, i, 0);
//                                        countTotalPart = 1;
//                                    }
//                                }
//                            }
                        } catch (Exception ex) {
                            table.revalidate();
                        }
                    }
                });
            }
            editorRendererComponent.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            editorRendererComponent.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean isRollover(JTable table, MouseEvent e, int row, int column) {
            return true;
        }
    }

    @SuppressWarnings("serial")
    class MyTimeCellRenderer extends DefaultTableCellRenderer {

        DateFormat formatter;

        MyTimeCellRenderer() {
            this.formatter = new SimpleDateFormat("HH:mm");
        }

        @Override
        public void setValue(Object value) {
            setText((value == null) ? "" : formatter.format(value));
        }
    }

    @SuppressWarnings("serial")
    class MyTimeCellEditor extends AbstractCellEditor implements TableCellEditor {

        private final DateSpinner dateSpinner;

        MyTimeCellEditor() {
            dateSpinner = new DateSpinner("HH:mm");
        }

        @Override
        public Object getCellEditorValue() {
            return dateSpinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, 
                int column) {
            dateSpinner.setValue(value);
            return dateSpinner;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.spinner.DateSpinner dsEndTime;
    private com.jidesoft.spinner.DateSpinner dsStartTime;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scrlBreakTime;
    private javax.swing.JSpinner spBreaksNum;
    // End of variables declaration//GEN-END:variables
    private boolean catLoad = true;
    private short nbLine = 1, n = 0;
    private final String _machine;
    private SortableTable sortableTable = null;
    private TableModelBreaksTime tableModelBreaksTime = null;
    private final ImageIcon removeIcon = IconsFactory.getImageIcon(TargetOptions.class, "/images/icons/office/remove_package.png");
    private final ImageIcon removeRolloverIcon = IconsFactory.getImageIcon(TargetOptions.class, "/images/icons/office/remove_package_rollover.png");
}
