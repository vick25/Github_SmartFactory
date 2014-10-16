package eventsPanel;

import com.jidesoft.grid.AutoFilterTableHeader;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.HeaderStyleModel;
import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.grid.HierarchicalTableComponentFactory;
import com.jidesoft.grid.HierarchicalTableModel;
import com.jidesoft.grid.ListSelectionModelGroup;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TreeLikeHierarchicalPanel;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.InfiniteProgressPanel;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.Overlayable;
import com.jidesoft.swing.OverlayableUtils;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import mainFrame.MainFrame;
import smartfactoryV2.ConnectDB;
import tableModel.FitScrollPane;

/**
 *
 * @author Victor Kadiata
 */
public class EventsHierarchicalTable extends JFrame {

    public EventsHierarchicalTable() {
        super("Events Statistics Data");
        this.setSize(720, 400);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
        this.getContentPane().setBackground(ConnectDB.BG4);
        this.getContentPane().add(getDemoPanel());
        this.setLocationRelativeTo(MainFrame.getFrame());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Component getDemoPanel() {
        _table = createTable();
        _topPane = new JScrollPane(_table);
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
        return _topPane;
    }

    // create property table
    private HierarchicalTable createTable() {
        DefaultTableModel _eventsTableModel = new EventsTableModel();
        final HierarchicalTable table = new HierarchicalTable();
        table.setAutoRefreshOnRowUpdate(false);
        table.setModel(_eventsTableModel);
        table.setBackground(ConnectDB.BG4);
        table.setName("Events Table");
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

    private void setTableColumn(JTable t) {
        TableColumn col = t.getColumnModel().getColumn(0);
        col.setMinWidth(60);
        col.setMaxWidth(60);
//        col.setResizable(false);
        col = t.getColumnModel().getColumn(1);
        col.setMinWidth(130);
        col.setMaxWidth(130);
        col = t.getColumnModel().getColumn(2);
        col.setMinWidth(130);
        col.setMaxWidth(130);
        col = t.getColumnModel().getColumn(3);
        col.setMinWidth(130);
        col.setMaxWidth(130);
        col = t.getColumnModel().getColumn(4);
        col.setMinWidth(80);
        col.setMaxWidth(80);
        col = t.getColumnModel().getColumn(5);
        col.setMinWidth(80);
        col.setMaxWidth(80);
        col = t.getColumnModel().getColumn(6);
        col.setMinWidth(80);
        col.setMaxWidth(80);
    }

    private static String[][] getListOfDescription() {
        short i = (short) EventsStatistic.getDescriptionSet().size(), y = 0;
        String[][] tabDescription = new String[i][2];
        for (String descriptionSet : EventsStatistic.getDescriptionSet()) {
            if (y < i) {
                tabDescription[y][0] = ConnectDB.firstLetterCapital(descriptionSet);
                tabDescription[y][1] = ConnectDB.firstLetterCapital(descriptionSet) + " collection of value in details of events";
            }
            y++;
        }
        return tabDescription;
    }

    private static String[][] getDetails(String rowNameDesc) throws SQLException {
        System.out.println(rowNameDesc);
        String[][] tab = null;
        int row = 0;
        if (!rowNameDesc.isEmpty()) {
            String eventQuery = "SELECT e.`EventNo`, e.`EventTime`, e.`UntilTime`, e.`Value`, c.`Description` \n"
                    + "FROM eventlog e, customlist c \n"
                    + "WHERE e.HwNo =?  AND e.CustomCode = c.Code \n"
                    + "AND e.Value <> '(null)' \n"
                    + "AND (e.EventTime BETWEEN ? AND ?) \n"
                    + "AND c.`Description` IN (?) \n"
                    + "ORDER BY c.Description ASC, e.`Value` ASC, e.`EventTime` ASC";
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(eventQuery)) {
                ps.setInt(1, EventsStatistic.getMachineID());
                ps.setString(2, EventsStatistic.getMinLogTime());
                ps.setString(3, EventsStatistic.getMaxLogTime());
                ps.setString(4, rowNameDesc);
                ConnectDB.res = ps.executeQuery();
                ConnectDB.res.last();
                tab = new String[ConnectDB.res.getRow()][7];
                ConnectDB.res.beforeFirst();
                while (ConnectDB.res.next()) {
                    String eventTime = ConnectDB.res.getString(2),//EventTime
                            untilTime = ConnectDB.res.getString(3);//UntilTime
                    double[] diffs = ConnectDB.getTimeDifference(
                            ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(eventTime)),
                            ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(untilTime)));
                    tab[row][0] = ConnectDB.res.getString(1);//EventNo
                    tab[row][1] = eventTime;
                    tab[row][2] = untilTime;
                    tab[row][3] = ConnectDB.res.getString(4);//Value
                    tab[row][4] = ConnectDB.DECIMALFORMAT.format(diffs[1]) + "h";//Hour
                    tab[row][5] = diffs[2] + "m";//Minutes
                    tab[row][6] = diffs[3] + "s";//Secondes
                    row++;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return tab;
    }

    static private class EventsTableModel extends DefaultTableModel implements HierarchicalTableModel,
            HeaderStyleModel {

        private static final CellStyle PERIOD_STYLE = new CellStyle();

        static {
            PERIOD_STYLE.setFontStyle(Font.BOLD);
            PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        }

        EventsTableModel() {
            super(getListOfDescription(), DESCRIPTION_COLUMNS);
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
        public Object getChildValueAt(int row) {
            TableModel model = null;
            String name = getValueAt(row, 0).toString(); //description name
            try {
                model = new DefaultTableModel(getDetails(name), DETAIL_COLUMNS) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
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

    private int _destroyedCount = 0;
    private static JScrollPane _topPane;
    private static final String[] DESCRIPTION_COLUMNS = new String[]{"Description Name", "Information"},
            DETAIL_COLUMNS = new String[]{"EventNo", "EventTime", "UntilTime", "Value", "Hours",
                "Minutes", "Seconds"};

    private HierarchicalTable _table = null;
    private final List _subTablesList = new ArrayList();
    private final ListSelectionModelGroup _group = new ListSelectionModelGroup();
}
