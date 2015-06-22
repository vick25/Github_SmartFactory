package viewData;

import com.jidesoft.grid.AutoFilterTableHeader;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.HeaderStyleModel;
import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.grid.HierarchicalTableComponentFactory;
import com.jidesoft.grid.HierarchicalTableModel;
import com.jidesoft.grid.ListSelectionModelGroup;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TreeLikeHierarchicalPanel;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.InfiniteProgressPanel;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.Overlayable;
import com.jidesoft.swing.OverlayableUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;
import tableModel.FitScrollPane;

/**
 *
 * @author Victor Kadiata
 */
public class ViewHistory extends javax.swing.JPanel {

    public ViewHistory(String myMachineTitle, Date myDateFrom) throws SQLException {
        initComponents();
//        this._machineTitle = myMachineTitle;
        this.loadMachine();
        if (myDateFrom != null) {
            cmbHFrom.setDate(myDateFrom);
        } else {
            cmbHFrom.setDate(Calendar.getInstance().getTime());
        }
        bslHistory.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bslHistory.setForeground(Color.GRAY);
        bslHistory.setFont(new Font("Tahoma", Font.PLAIN, 10));
        bslHistory.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bslHistory.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        bslHistory.setBusy(true);
        bslHistory.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmbMachine = new com.jidesoft.combobox.CheckBoxListComboBox();
        jideLabel1 = new com.jidesoft.swing.JideLabel();
        chkFrom = new javax.swing.JCheckBox();
        cmbHFrom = new com.jidesoft.combobox.DateComboBox();
        btnRefresh = new javax.swing.JButton();
        _topPane = new javax.swing.JScrollPane();
        lblLastData = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bslHistory = new org.jdesktop.swingx.JXBusyLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cmbMachine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMachineItemStateChanged(evt);
            }
        });

        jideLabel1.setText("Machine:");

        chkFrom.setBackground(new java.awt.Color(255, 255, 255));
        chkFrom.setText("From:");
        chkFrom.setFocusable(false);
        chkFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFromItemStateChanged(evt);
            }
        });

        cmbHFrom.setShowWeekNumbers(false);
        cmbHFrom.setDate(Calendar.getInstance().getTime());
        cmbHFrom.setEnabled(false);
        cmbHFrom.setFocusable(false);
        cmbHFrom.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        cmbHFrom.setRequestFocusEnabled(false);
        cmbHFrom.setTimeDisplayed(true);
        cmbHFrom.setTimeFormat("HH:mm:ss");
        cmbHFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbHFromActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/repeat.png"))); // NOI18N
        btnRefresh.setToolTipText("Reset Date");
        btnRefresh.setEnabled(false);
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jideLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFrom))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbMachine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbHFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 403, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jideLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkFrom)
                    .addComponent(cmbHFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblLastData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLastData.setForeground(new java.awt.Color(204, 0, 0));
        lblLastData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        bslHistory.setBusy(true);
        bslHistory.setDirection(org.jdesktop.swingx.painter.BusyPainter.Direction.RIGHT);
        bslHistory.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_topPane)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bslHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLastData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_topPane, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblLastData, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bslHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMachineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMachineItemStateChanged
        if (cmbMachine.getSelectedObjects().length != 0) {
            _topPane.setViewportView(null);
            setMachine = new TreeSet<>();
            try (Statement stat = ConnectDB.con.createStatement()) {//for the channel
                ConnectDB.res = stat.executeQuery(new StringBuilder("SELECT h.Machine, c.ConfigNo, c.ChannelID \n").
                        append("FROM configuration c, hardware h \nWHERE h.HwNo = c.HwNo "
                                + "\nAND c.HwNo IN (SELECT HwNo FROM hardware WHERE Machine IN (").
                        append(ConnectDB.retrieveCriteria(cmbMachine.getSelectedObjects())).
                        append(")) \nORDER BY c.HwNo ASC, c.AvMinMax DESC").toString());
                while (ConnectDB.res.next()) {
                    setMachine.add(ConnectDB.res.getString(1));
                }
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
            getDemoPanel();//create everything
        } else {
            _topPane.setViewportView(null);
            bslHistory.setVisible(false);
        }
    }//GEN-LAST:event_cmbMachineItemStateChanged

    private void chkFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFromItemStateChanged
        if (chkFrom.isSelected()) {
            cmbHFromActionPerformed(null);
            cmbHFrom.setEnabled(true);
            btnRefresh.setEnabled(true);
        } else {
            cmbHFromActionPerformed(null);
            btnRefresh.setEnabled(false);
            cmbHFrom.setEnabled(false);
        }
    }//GEN-LAST:event_chkFromItemStateChanged

    private void cmbHFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbHFromActionPerformed
        cmbMachineItemStateChanged(null);
    }//GEN-LAST:event_cmbHFromActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        Calendar working = ((Calendar) Calendar.getInstance().clone());
        working.add(Calendar.DAY_OF_YEAR, -1);
        cmbHFrom.setDate(working.getTime());
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void getDemoPanel() {
        _table = createTable();
        _topPane.setViewportView(_table);
        _topPane.getViewport().putClientProperty("HierarchicalTable.mainViewport", Boolean.TRUE);
        _topPane.getViewport().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Point point = ((JViewport) e.getSource()).getViewPosition();
                int rowIndex = _table.rowAtPoint(point);
                try {
                    // according to the value of rowIndex and if the row is expanded, you could now choose to
                    //switch the column header view as you wish
                    _topPane.setColumnHeaderView(((JTable) _subTablesList.get(rowIndex)).getTableHeader());
                } catch (Exception ex) {
                    if (_subTablesList.size() > 0) {
                        _topPane.setColumnHeaderView(((JTable) _subTablesList.get(rowIndex - rowIndex)).getTableHeader());
                    }
                }
            }
        });
        bslHistory.setVisible(false);
    }

    // create property table
    private HierarchicalTable createTable() {
        _historyTableModel = new HistoryTableModel();
        final HierarchicalTable table = new HierarchicalTable();
        table.setAutoRefreshOnRowUpdate(false);
        table.setModel(_historyTableModel);
        table.setBackground(ConnectDB.BG4);
        table.setName("History Table");
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentFactory(new HierarchicalTableComponentFactory() {
            Map<Integer, TableModel> _tableModels = new HashMap<>();
            Map<Integer, JTable> _tables = new HashMap<>();

            @Override
            public Component createChildComponent(HierarchicalTable table, Object value, int row) {
                if (value == null) {
                    return new JPanel();
                }
                TableModel model = ((TableModel) value);
                if (model instanceof HierarchicalTableModel) {
                    HierarchicalTable childTable = new HierarchicalTable(model) {
                        @Override
                        public void scrollRectToVisible(Rectangle aRect) {
                            ConnectDB.scrollRectToVisible(this, aRect);
                        }
                    };
                    childTable.setBackground(ConnectDB.BG2);
                    childTable.setOpaque(true);
                    childTable.setName("Detail Table");
                    childTable.setComponentFactory(new HierarchicalTableComponentFactory() {
                        @Override
                        public Component createChildComponent(HierarchicalTable table, Object value, int row) {
                            if (value instanceof TableModel) {
                                TableModel model = ((TableModel) value);
                                SortableTable sortableTable = new SortableTable(model) {
                                    @Override
                                    public void scrollRectToVisible(Rectangle aRect) {
                                        ConnectDB.scrollRectToVisible(this, aRect);
                                    }
                                };
                                FitScrollPane pane = new FitScrollPane(sortableTable);
                                sortableTable.setBackground(ConnectDB.BG3);
                                _group.add(sortableTable.getSelectionModel());
                                TreeLikeHierarchicalPanel treeLikeHierarchicalPanel = new TreeLikeHierarchicalPanel(pane);
                                treeLikeHierarchicalPanel.setBackground(sortableTable.getMarginBackground());
                                return treeLikeHierarchicalPanel;
                            }
                            return null;
                        }

                        @Override
                        public void destroyChildComponent(HierarchicalTable table, Component component, int row) {
                            Component t = JideSwingUtilities.getFirstChildOf(JTable.class, component);
                            if (t instanceof JTable) {
                                _group.remove(((JTable) t).getSelectionModel());
                            }
                        }
                    });
                    _group.add(childTable.getSelectionModel());
                    TreeLikeHierarchicalPanel treeLikeHierarchicalPanel = new TreeLikeHierarchicalPanel(new FitScrollPane(childTable));
                    treeLikeHierarchicalPanel.setBackground(childTable.getMarginBackground());
                    return treeLikeHierarchicalPanel;
                } else {
                    return createPanel(model, row);
                }
            }

            private JComponent createPanel(final TableModel model, final int row) {
                TableModel emptyTableModel = new TableModel() {
                    @Override
                    public int getRowCount() {
                        return 0;
                    }

                    @Override
                    public int getColumnCount() {
                        return model.getColumnCount();
                    }

                    @Override
                    public String getColumnName(int columnIndex) {
                        return model.getColumnName(columnIndex);
                    }

                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return model.getColumnClass(columnIndex);
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return null;
                    }

                    @Override
                    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                    }

                    @Override
                    public void addTableModelListener(TableModelListener l) {

                    }

                    @Override
                    public void removeTableModelListener(TableModelListener l) {

                    }
                };
                SortableTable sortableTable = new SortableTable(emptyTableModel) {
                    @Override
                    public void scrollRectToVisible(Rectangle aRect) {
                        ConnectDB.scrollRectToVisible(this, aRect);
                    }
                };
                if (!_subTablesList.contains(sortableTable)) {
                    _subTablesList.add(sortableTable);//add the subtables to a list
                }
                sortableTable.setBackground(ConnectDB.BG2);
                _group.add(sortableTable.getSelectionModel());
                TreeLikeHierarchicalPanel treeLikeHierarchicalPanel = new TreeLikeHierarchicalPanel(new FitScrollPane(sortableTable));
                treeLikeHierarchicalPanel.setBackground(sortableTable.getMarginBackground());
                DefaultOverlayable overlayable = new DefaultOverlayable(treeLikeHierarchicalPanel);

                InfiniteProgressPanel progressPanel = new InfiniteProgressPanel() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(20, 20);
                    }
                };
                overlayable.addOverlayComponent(progressPanel);
                progressPanel.start();
                overlayable.setOverlayVisible(true);
                _tables.put(row, sortableTable);
                _tableModels.put(row, model);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(500); // you could use this thread to calculate your table model.
                        } catch (InterruptedException e) {
                        }
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                JTable internalTable = _tables.get(row);
                                Container parent1 = internalTable.getParent();
                                while (parent1 != null && !(parent1 instanceof TreeLikeHierarchicalPanel)) {
                                    parent1 = parent1.getParent();
                                }
                                if (parent1 != null) {
                                    Overlayable overlayable1 = OverlayableUtils.getOverlayable((JComponent) parent1);
                                    overlayable1.setOverlayVisible(false);
                                    JComponent[] overlayComponents = overlayable1.getOverlayComponents();
                                    for (JComponent comp : overlayComponents) {
                                        if (comp instanceof InfiniteProgressPanel) {
                                            ((InfiniteProgressPanel) comp).stop();
                                        }
                                    }
                                }
                                internalTable.setModel(_tableModels.get(row));
                                setTableColumn(internalTable);
                                table.doLayout();
                            }
                        });
                    }
                };
                thread.start();
                return overlayable;
            }

            @Override
            public void destroyChildComponent(HierarchicalTable table, Component component, int row) {
                Component t = JideSwingUtilities.getFirstChildOf(JTable.class, component);
                if (t instanceof JTable) {
                    _group.remove(((JTable) t).getSelectionModel());
                }
                _subTablesList.clear();
                _topPane.setColumnHeaderView(_table.getTableHeader());
                _destroyedCount++;
            }
        });
        _group.add(table.getSelectionModel());
        AutoFilterTableHeader header = new AutoFilterTableHeader(table);
        table.setTableHeader(header);
        header.setAutoFilterEnabled(true);
        header.setUseNativeHeaderRenderer(true);
        return table;
    }

    // for automated test case purpose
    public int getDestroyedCount() {
        return _destroyedCount;
    }

    private static class HistoryTableModel extends DefaultTableModel implements HierarchicalTableModel,
            HeaderStyleModel {

        private static final CellStyle PERIOD_STYLE = new CellStyle();

        static {
            PERIOD_STYLE.setFontStyle(Font.BOLD);
            PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        }

        HistoryTableModel() {
            super(getListOfMachine(), DESCRIPTION_COLUMNS);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public boolean hasChild(int row) {
            return true;
        }

        @Override
        public boolean isExpandable(int row) {
            return true;
        }

        @Override
        public boolean isHierarchical(int row) {
            return true;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getChildValueAt(final int row) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        String name = getValueAt(row, 0).toString(); //machine name
                        model = new DefaultTableModel(getDetails(name), getChannelID_DETAIL_COLUMNS(name)) {

                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
                }
            }).start();
            bslHistory.setVisible(false);
            return model;
        }

        @Override
        public CellStyle getHeaderStyleAt(int i, int i1) {
            return PERIOD_STYLE;
        }

        @Override
        public boolean isHeaderStyleOn() {
            return true;
        }
    }

    private void setTableColumn(JTable t) {
        try {
            TableColumn col = t.getColumnModel().getColumn(0);
            col.setMinWidth(160);
            col.setMaxWidth(160);
//        col.setResizable(false);
            col = t.getColumnModel().getColumn(1);
            col.setMinWidth(135);
            col.setMaxWidth(135);
            col = t.getColumnModel().getColumn(2);
            col.setMinWidth(135);
            col.setMaxWidth(135);
            col = t.getColumnModel().getColumn(3);
            col.setMinWidth(135);
            col.setMaxWidth(135);
        } catch (Exception e) {
        }
    }

    private static String[][] getListOfMachine() {
        short i = (short) setMachine.size(), y = 0;
        String[][] tabDescription = new String[i][2];
        for (String sMachine : setMachine) {
            if (y < i) {
                tabDescription[y][0] = sMachine;
                tabDescription[y][1] = new StringBuilder(sMachine).append(" channels").toString();
            }
            y++;
        }
        return tabDescription;
    }

    private static Object[] getChannelID_DETAIL_COLUMNS(String name) throws SQLException {
        ArrayList<String> channelID = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.HISTORY_MACHINE_CHANNELID)) {
            ps.setString(1, name);
            channelID.add("Time");
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                channelID.add(ConnectDB.res.getString(1));
            }
        }
        return channelID.toArray();
    }

    private static String[][] getDetails(String rowMachineName) throws SQLException {
        String[][] tab = null;
        Vector vectConfigNo = new Vector();
        if (!rowMachineName.isEmpty()) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    bslHistory.setVisible(true);
                    bslHistory.setBusy(true);
                    bslHistory.setText("Processing ... ");
                }
            });
            short col = 0;
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.HISTORY)) {
                ps.setString(1, rowMachineName);
                ConnectDB.res = ps.executeQuery();
                while (ConnectDB.res.next()) {
                    vectConfigNo.add(ConnectDB.res.getInt(1));
                }
                //Define the size of the tabFill array
                tabFill = new String[getRowNum((int) vectConfigNo.elementAt(0))][vectConfigNo.size() + 1];
                Iterator<Integer> it = vectConfigNo.iterator();
                while (it.hasNext()) {
                    int item = it.next();
                    ++col;
                    try {
                        tab = fillTable(col, item);
                    } catch (SQLException e) {
                        ConnectDB.catchSQLException(e);
                    }
                }
            }
        }
        bslHistory.setVisible(false);
        return tab;
    }

    private static String[][] fillTable(short col, int item) throws SQLException {
        int row = 0;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(addExtraQuery(Queries.HISTORY_LOGDATA))) {
            ps.setInt(1, item);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                tabFill[row][0] = ConnectDB.res.getString(1);//LogTime
                tabFill[row][col] = ConnectDB.res.getString(2);//LogData
                row++;
            }
        }
        return tabFill;
    }

    private static int getRowNum(int row) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(addExtraQuery(Queries.HISTORY_LOGDATA))) {
            ps.setInt(1, row);
            ConnectDB.res = ps.executeQuery();
            ConnectDB.res.last();
            return ConnectDB.res.getRow();
        }
    }

    private static String addExtraQuery(String channel) {
        StringBuilder channelQuery = new StringBuilder(channel);
        if (chkFrom.isSelected()) {
            channelQuery = new StringBuilder(channel.substring(0, channel.lastIndexOf("ORDER")).trim()).
                    append(" \nAND d.LogTime >= '").append(ConnectDB.SDATE_FORMAT_HOUR.format(cmbHFrom.getDate())).
                    append("' \nORDER BY d.LogTime ASC");
        }
        return channelQuery.toString();
    }

    private void loadMachine() throws SQLException {
        ArrayList<String> data = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Machine \n"
                + "FROM hardware WHERE HwNo > ?")) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            data.add(0, CheckBoxList.ALL);
            while (ConnectDB.res.next()) {
                data.add(ConnectDB.res.getString(1));
            }
            cmbMachine.setModel(new DefaultComboBoxModel(data.toArray()));
            data.clear();
//            if ("".equals(this._machineTitle)) {
            cmbMachine.setSelectedIndex(-1);
//            } 
//            else {
//                cmbMachine.setSelectedItem(this._machineTitle);
//            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_LAST_DATA_TIME)) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                lblLastData.setText(new StringBuilder("Last recorded data: ").
                        append(ConnectDB.res.getString(1).substring(0, 19)).toString());
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane _topPane;
    private static org.jdesktop.swingx.JXBusyLabel bslHistory;
    private javax.swing.JButton btnRefresh;
    public static javax.swing.JCheckBox chkFrom;
    public static com.jidesoft.combobox.DateComboBox cmbHFrom;
    private static com.jidesoft.combobox.CheckBoxListComboBox cmbMachine;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.jidesoft.swing.JideLabel jideLabel1;
    private javax.swing.JLabel lblLastData;
    // End of variables declaration//GEN-END:variables
//    private String _machineTitle = "";
    private static String[][] tabFill = null;
    private static Set<String> setMachine = null;
    private static final String[] DESCRIPTION_COLUMNS = new String[]{"Machine Name", "Machine History Information"};

    private HierarchicalTable _table;
    private DefaultTableModel _historyTableModel;

    private static TableModel model = null;
    private int _destroyedCount = 0;

    private final List _subTablesList = new ArrayList();
    private final ListSelectionModelGroup _group = new ListSelectionModelGroup();
}
