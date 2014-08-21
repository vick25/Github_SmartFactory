package viewData;

import com.jidesoft.grid.AutoFilterTableHeader;
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
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class ViewHistory extends javax.swing.JPanel {

    public ViewHistory(String machineTitle, Date from) throws SQLException {
        initComponents();
        this._machineTitle = machineTitle;
        loadMachine();
        if (from != null) {
            cmbHFrom.setDate(from);
        } else {
            cmbHFrom.setDate(Calendar.getInstance().getTime());
        }
        bslHistory.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bslHistory.setForeground(Color.gray);
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
        jScrollPane1 = new javax.swing.JScrollPane();
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
        cmbHFrom.setFormat(ConnectDB.SDATEFORMATHOUR);
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

        lblLastData.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bslHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLastData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
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
            jScrollPane1.setViewportView(null);
            setMachine.clear();
            String query = "SELECT h.Machine, c.ConfigNo, c.ChannelID\n"
                    + "FROM configuration c, hardware h\n"
                    + "WHERE h.HwNo = c.HwNo\n"
                    + "AND c.HwNo IN (SELECT HwNo FROM hardware WHERE Machine IN ("
                    + substringMachine(cmbMachine.getSelectedObjects()) + "))\n"
                    + "ORDER BY c.HwNo ASC, c.AvMinMax DESC";
            try (Statement stat = ConnectDB.con.createStatement()) {//for the channel
                ConnectDB.res = stat.executeQuery(query);
                while (ConnectDB.res.next()) {
                    setMachine.add(ConnectDB.res.getString(1));
                }
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
            getDemoPanel();//create everything
        } else {
            jScrollPane1.setViewportView(null);
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
        jScrollPane1.setViewportView(_table);
        jScrollPane1.getViewport().putClientProperty("HierarchicalTable.mainViewport", Boolean.TRUE);
        bslHistory.setVisible(false);
    }

    // create property table
    private HierarchicalTable createTable() {
        _historyTableModel = new HistoryTableModel();
        final HierarchicalTable table = new HierarchicalTable();
        table.setAutoRefreshOnRowUpdate(false);
        table.setModel(_historyTableModel);
        table.setBackground(BG4);
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
                            ViewHistory.scrollRectToVisible(this, aRect);
                        }
                    };
                    childTable.setBackground(BG2);
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
                                        ViewHistory.scrollRectToVisible(this, aRect);
                                    }
                                };
                                FitScrollPane pane = new FitScrollPane(sortableTable);
                                sortableTable.setBackground(BG3);
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
                        ViewHistory.scrollRectToVisible(this, aRect);
                    }
                };
                sortableTable.setBackground(BG2);
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
                            Thread.sleep(1000); // you could use this thread to calculate your table model.
                        } catch (InterruptedException e) {
                            // null
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

    private static class HistoryTableModel extends DefaultTableModel implements HierarchicalTableModel {

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
    }

    private static class FitScrollPane extends JScrollPane implements ComponentListener {

        FitScrollPane() {
            initScrollPane();
        }

        FitScrollPane(Component view) {
            super(view);
            initScrollPane();
        }

        FitScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
            super(view, vsbPolicy, hsbPolicy);
            initScrollPane();
        }

        FitScrollPane(int vsbPolicy, int hsbPolicy) {
            super(vsbPolicy, hsbPolicy);
            initScrollPane();
        }

        private void initScrollPane() {
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            getViewport().getView().addComponentListener(this);
            removeMouseWheelListeners();
        }

        // remove MouseWheelListener as there is no need for it in FitScrollPane.
        private void removeMouseWheelListeners() {
            MouseWheelListener[] listeners = getMouseWheelListeners();
            for (MouseWheelListener listener : listeners) {
                removeMouseWheelListener(listener);
            }
        }

        @Override
        public void updateUI() {
            super.updateUI();
            removeMouseWheelListeners();
        }

        @Override
        public void componentResized(ComponentEvent e) {
            setSize(getSize().width, getPreferredSize().height);
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }

        @Override
        public Dimension getPreferredSize() {
            getViewport().setPreferredSize(getViewport().getView().getPreferredSize());
            return super.getPreferredSize();
        }
    }

    public static void scrollRectToVisible(Component component, Rectangle aRect) {
        Container parent;
        int dx = component.getX(), dy = component.getY();

        for (parent = component.getParent();
                parent != null && (!(parent instanceof JViewport)
                || (((JViewport) parent).getClientProperty("HierarchicalTable.mainViewport") == null));
                parent = parent.getParent()) {
            Rectangle bounds = parent.getBounds();
            dx += bounds.x;
            dy += bounds.y;
        }
        if (parent != null) {
            aRect.x += dx;
            aRect.y += dy;

            ((JComponent) parent).scrollRectToVisible(aRect);
            aRect.x -= dx;
            aRect.y -= dy;
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
                tabDescription[y][1] = sMachine + " channels";
            }
            y++;
        }
        return tabDescription;
    }

    private static Object[] getChannelID_DETAIL_COLUMNS(String name) throws SQLException {
        ArrayList<String> channelID = new ArrayList<>();
        String query = "SELECT c.ChannelID\n"
                + "FROM configuration c, hardware h\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND h.Machine =?\n"
                + "ORDER BY c.HwNo ASC, c.AvMinMax DESC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ps.setString(1, name);
            ConnectDB.res = ps.executeQuery();
            channelID.add("Time");
            while (ConnectDB.res.next()) {
                channelID.add(ConnectDB.res.getString(1));
            }
        }
        return channelID.toArray();
    }

    private static String[][] getDetails(String rowMachineName) throws SQLException {
        String[][] tab = null;
        vectConfigNo.removeAllElements();
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
            String historyQuery = "SELECT c.ConfigNo\n"
                    + "FROM configuration c, hardware h\n"
                    + "WHERE h.HwNo = c.HwNo\n"
                    + "AND h.Machine =?\n"
                    + "ORDER BY c.HwNo ASC, c.AvMinMax DESC";
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(historyQuery)) {
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
        String channelQuery = "SELECT d.LogTime, d.LogData\n"
                + "FROM configuration c, datalog d\n"
                + "WHERE c.ConfigNo = d.ConfigNo\n"
                + "AND d.ConfigNo =?\n"
                + "ORDER BY d.LogTime ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(runQuery(channelQuery))) {
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
        String channelQuery = "SELECT d.LogTime, d.LogData\n"
                + "FROM configuration c, datalog d\n"
                + "WHERE c.ConfigNo = d.ConfigNo\n"
                + "AND d.ConfigNo =?\n"
                + "ORDER BY d.LogTime ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(runQuery(channelQuery))) {
            ps.setInt(1, row);
            ConnectDB.res = ps.executeQuery();
            ConnectDB.res.last();
            return ConnectDB.res.getRow();
        }
    }

    private static String runQuery(String channel) {
        String channelQuery = channel;
        if (chkFrom.isSelected()) {
            channelQuery = channel.substring(0, channel.lastIndexOf("ORDER")).trim() + "\n"
                    + "AND d.LogTime >= '" + ConnectDB.SDATEFORMATHOUR.format(cmbHFrom.getDate()) + "'\n"
                    + "ORDER BY d.LogTime ASC";
        }
        return channelQuery;
    }

    private static String substringMachine(Object[] list) {
        String values = "";
        for (Object list1 : list) {
            values += "\'" + ConnectDB.firstLetterCapital(list1.toString()) + "\',";
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private void loadMachine() throws SQLException {
        ArrayList<String> data = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Machine\n"
                + "FROM hardware WHERE HwNo > ?")) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            data.add(0, CheckBoxList.ALL);
            while (ConnectDB.res.next()) {
                data.add(ConnectDB.res.getString(1));
            }
            cmbMachine.setModel(new DefaultComboBoxModel(data.toArray()));
            data.clear();
            if ("".equals(this._machineTitle)) {
                cmbMachine.setSelectedIndex(-1);
            } else {
                cmbMachine.setSelectedItem(this._machineTitle);
            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT LogTime FROM datalog\n"
                + "ORDER BY LogTime DESC LIMIT 1;")) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                lblLastData.setText("Last recorded data: " + ConnectDB.res.getString(1).substring(0, 19));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static org.jdesktop.swingx.JXBusyLabel bslHistory;
    private javax.swing.JButton btnRefresh;
    public static javax.swing.JCheckBox chkFrom;
    public static com.jidesoft.combobox.DateComboBox cmbHFrom;
    private static com.jidesoft.combobox.CheckBoxListComboBox cmbMachine;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.jidesoft.swing.JideLabel jideLabel1;
    private javax.swing.JLabel lblLastData;
    // End of variables declaration//GEN-END:variables
    private String _machineTitle = "";
    private static String[][] tabFill = null;
    private static Set<String> setMachine = new TreeSet<>();
    private static String[] DESCRIPTION_COLUMNS = new String[]{"Machine Name", "Machine History Information"};
    private static Vector vectConfigNo = new Vector();
//    protected static final Color BG1 = new Color(232, 237, 230);
    protected static final Color BG2 = new Color(243, 234, 217);
    protected static final Color BG3 = new Color(214, 231, 247);
    protected static final Color BG4 = new Color(255, 255, 255);

    private HierarchicalTable _table;
    private DefaultTableModel _historyTableModel;

    private static TableModel model = null;
    private int _destroyedCount = 0;

    private ListSelectionModelGroup _group = new ListSelectionModelGroup();
}
