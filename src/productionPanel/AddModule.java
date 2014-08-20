package productionPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class AddModule extends javax.swing.JDialog {

    public String getSavedProperties() {
        return savedProperties;
    }

    public AddModule(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        ConnectDB.getConnectionInstance();
        _parent = parent;
        table = new JXTable(new TableModelAddModule("Mode"));
        refModel = (TableModelAddModule) table.getModel();
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        table.getTableHeader().setBackground(Color.BLUE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setHorizontalScrollEnabled(true);
        table.setHighlighters(HighlighterFactory.createAlternateStriping(new Color(253, 253, 244),
                new Color(230, 230, 255)));
        table.setSortable(false);
        table.setSelectionMode(0);
        table.setShowGrid(true);
        initComponents();
        setPropertiesSaved(ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.MODULES, getPropertiesToSave()));
        fillModules();
        Scrll.setViewportView(table);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                saveChanges();
                fillProductionModule();
                dispose();
            }
        });
        time = new Timer(150, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbMode.getSelectedItem().toString().isEmpty()) {
                    txtName.setText("");
                } else {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (table.getValueAt(i, 1).toString().equals(cmbMode.getSelectedItem().toString())) {
                            clearTextName = true;
                            txtName.setText("This mode already exists");
                            txtName.setEditable(false);
                            break;
                        } else {
                            if (clearTextName) {
                                txtName.setText("");
                                switch (cmbMode.getSelectedItem().toString()) {
                                    case "Rate":
                                        txtName.setText("Production Rate");
                                        break;
                                    case "Cumulative":
                                        txtName.setText("Total Production");
                                        break;
                                    case "Average":
                                        txtName.setText("Machine Running (ON/OFF)");
                                        break;
                                    default:
                                        txtName.setText("");
                                        break;
                                }
                            }
                            txtName.setEditable(true);
                            clearTextName = false;
//                            continue;
                        }
                    }
                }
                if (!txtName.getText().isEmpty() && !txtName.getText().equals("This mode already exists")
                        && cmbMode.getSelectedIndex() > 0) {
                    btnAdd.setEnabled(true);
                } else {
                    btnAdd.setEnabled(false);
                }
            }
        });
        txtName.requestFocus();
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                firePopupTable(e);
            }

        });
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icons/fyre(2).png")).getImage());
        this.setLocationRelativeTo(_parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jXTitledSeparator1 = new org.jdesktop.swingx.JXTitledSeparator();
        Scrll = new javax.swing.JScrollPane();
        cmbMode = new org.jdesktop.swingx.JXComboBox();
        btnRefresh = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Production Module Options");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Name");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/valid.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setFocusable(false);
        btnAdd.setOpaque(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setText("Mode");

        jXTitledSeparator1.setTitle("Option modes");

        Scrll.setBackground(new java.awt.Color(255, 255, 255));
        Scrll.setOpaque(false);

        cmbMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbModeItemStateChanged(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/repeat.png"))); // NOI18N
        btnRefresh.setToolTipText("Refresh the channel modes");
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnClean.setBackground(new java.awt.Color(255, 255, 255));
        btnClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/edit_clear.png"))); // NOI18N
        btnClean.setText("Clean");
        btnClean.setFocusable(false);
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbMode, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jXTitledSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Scrll)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnClean)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdd, btnClean});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(cmbMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTitledSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scrll, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnClean))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, btnRefresh, cmbMode});

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String textValue = txtName.getText();
        if (uniqueness(textValue)) {
            int nRow;
            for (int i = 0; i < table.getRowCount(); i++) {
                EDIT = true;
                if (!table.getValueAt(i, 0).toString().isEmpty()
                        && !table.getValueAt(i, 1).toString().isEmpty()) {
                    nRow = table.getRowCount() + 1;
                    if (nRow > table.getRowCount()) {
                        refModel.addNewRow();
                    }
                    table.setValueAt(textValue, nRow - 1, 0);
                    table.setValueAt(cmbMode.getSelectedItem().toString(), nRow - 1, 1);
                    nRow++;
                } else {
                    nRow = 1;
                    table.setValueAt(textValue, nRow - 1, 0);
                    table.setValueAt(cmbMode.getSelectedItem().toString(), nRow - 1, 1);
                }
                EDIT = false;
                break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "The module name \"" + txtName.getText()
                    + "\" already exists in the table", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        try {
            fillModules();
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        this.btnAddActionPerformed(evt);
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        EDIT = true;
        cleantable();
        ConnectDB.pref.remove(SettingKeyFactory.DefaultProperties.MODULES);
        EDIT = false;
    }//GEN-LAST:event_btnCleanActionPerformed

    private void cmbModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbModeItemStateChanged
        if (catModules) {
            txtName.requestFocus();
            clearTextName = true;
            time.start();
        }
        if (cmbMode.getSelectedIndex() < 0) {
            txtName.setText("");
        }
    }//GEN-LAST:event_cmbModeItemStateChanged

    private void saveChanges() {
        if (saved) {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            savedProperties = getPropertiesToSave();
        }
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.MODULES, savedProperties);
    }

    private String getPropertiesToSave() {
        String properties = "";
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                properties += table.getValueAt(i, j) + "\t";
            }
            properties += "\r\n";
        }
        return properties;
    }

    private void setPropertiesSaved(String values) {
        try {
            savedProperties = values;
            saved = false;
            EDIT = true;
            cleantable();
            String[] rowValues = values.split("\r\n");

            int k = rowValues.length;
            while (k >= rowValues.length) {
                String[] columnTrainee = rowValues[k - rowValues.length].split("\t");
                for (int j = 0; j < columnTrainee.length; j++) {
                    table.setValueAt(columnTrainee[j], k - rowValues.length, j);
                }
                k++;
                if ((k - rowValues.length) == rowValues.length) {
                    break;
                }
                refModel.addNewRow();
            }
            saved = true;
            EDIT = false;
        } catch (Exception e) {
            ConnectDB.pref.remove(SettingKeyFactory.DefaultProperties.MODULES);
            JOptionPane.showMessageDialog(_parent, "Error encountered! Aborting. Please restart "
                    + "the application.", "Exit", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void fillModules() throws SQLException {
        catModules = false;
        cmbMode.removeAllItems();
        cmbMode.addItem(" ");
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT DISTINCT(AvMinMax)\n"
                + "FROM `configuration`")) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                cmbMode.addItem(ConnectDB.res.getString(1));
            }
        }
        catModules = true;
    }

    private void fillProductionModule() {
        try {
            ProductionPane.setCatOptions(false);
            String[] rowValues = savedProperties.split("\r\n");
            ProductionPane.cmbOptions.removeAllItems();
            ProductionPane.cmbOptions.addItem(" ");
            for (String string : rowValues) {
                if (!string.equals("\t\t")) {
                    ProductionPane.cmbOptions.addItem(string.split("\t")[0]);
                }
            }
            ProductionPane.setCatOptions(true);
        } catch (Exception e) {
            this.dispose();
        }
    }

    private void cleantable() {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt("", i, j);
            }
        }
        for (int i = 1; i < table.getRowCount(); i++) {
            refModel.removeNewRow(i);
        }
    }

    private boolean uniqueness(String value) {
        boolean unique = true;
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).toString().equals(value)) {
                return false;
            }
        }
        return unique;
    }

    private void firePopupTable(MouseEvent evt) {
        int r = table.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < table.getRowCount()) {
            table.setRowSelectionInterval(r, r);
        } else {
            table.clearSelection();
        }
        int rowIndex = table.getSelectedRow();
        if (rowIndex < 0) {
            return;
        }
        if (evt.isPopupTrigger() && evt.getComponent() instanceof JXTable) {
            createPopMenuTable();
            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    private void createPopMenuTable() {
        popupMenu = new JPopupMenu();
        JMenuItem menu = new JMenuItem("Delete");
        menu.setIcon(new ImageIcon(getClass().getResource("/images/icons/deletered.png")));
        popupMenu.add(menu);
        menu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (int i = 0; i < table.getModel().getRowCount(); i++) {
                        if (table.isRowSelected(i)) {
                            clearTextName = true;
                            refModel.removeNewRow(i);
                            cmbMode.setSelectedIndex(-1);
                            time.stop();
                        }
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private class TableModelAddModule extends AbstractTableModel {

        private final String _titre;
        private final String[] columnNames = new String[2];
        private final ArrayList[] data;

        TableModelAddModule(String titre) {
            _titre = titre;
            columnNames[0] = "Name";
            columnNames[1] = _titre;
            data = new ArrayList[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                data[i] = new ArrayList();
            }
            for (int i = 0; i < columnNames.length; i++) {
                data[i].add("");
            }
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data[0].size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[col].get(row);
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (EDIT) {
                return (true);
            } else {
                if (col == 0) {
                    return (true);
                } else {
                    return (false);
                }
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[col].set(row, value);
            fireTableCellUpdated(row, col);
        }

        public void addNewRow() {
            for (int i = 0; i < columnNames.length; i++) {
                data[i].add("");
            }
            this.fireTableRowsInserted(0, data[0].size() - 1);
        }

        public void removeNewRow() {
            for (int i = 0; i < columnNames.length; i++) {
                data[i].remove(data[i].size() - 1);
            }
            this.fireTableRowsDeleted(0, data[0].size() - 1);
        }

        public void removeNewRow(int index) {
            for (int i = 0; i < columnNames.length; i++) {
                data[i].remove(index);
            }
            this.fireTableRowsDeleted(0, data[0].size() - 1);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scrll;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnRefresh;
    private org.jdesktop.swingx.JXComboBox cmbMode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
    private final Timer time;
    private JXTable table;
    private JPopupMenu popupMenu;
    private final java.awt.Frame _parent;
    private final TableModelAddModule refModel;
    private String savedProperties = null;
    private boolean EDIT = false, saved = true, clearTextName = false, catModules;
}
