package eventsPanel;

import com.jidesoft.grid.AutoFilterTableHeader;
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
public class EventsHierarchicalTable extends JFrame {

    public EventsHierarchicalTable() {
        super("Events Statistics Data");
        this.setSize(720, 400);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
        this.getContentPane().setBackground(BG4);
        this.getContentPane().add(getDemoPanel());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Component getDemoPanel() {
        _table = createTable();
        JScrollPane scrollPane = new JScrollPane(_table);
        scrollPane.getViewport().putClientProperty("HierarchicalTable.mainViewport", Boolean.TRUE);
        return scrollPane;
    }

    // create property table
    private HierarchicalTable createTable() {
        DefaultTableModel _eventsTableModel = new EventsTableModel();
        final HierarchicalTable table = new HierarchicalTable();
        table.setAutoRefreshOnRowUpdate(false);
        table.setModel(_eventsTableModel);
        table.setBackground(BG4);
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
                            EventsHierarchicalTable.scrollRectToVisible(this, aRect);
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
                                        EventsHierarchicalTable.scrollRectToVisible(this, aRect);
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
                        EventsHierarchicalTable.scrollRectToVisible(this, aRect);
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
        int i = EventsStatistic.getDescriptionSet().size(), y = 0;
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
        String[][] tab = null;
        int row = 0;
        if (!rowNameDesc.isEmpty()) {
            String eventQuery = "SELECT e.`EventNo`, e.`EventTime`, e.`UntilTime`, e.`Value`, c.`Description`\n"
                    + "FROM eventlog e, customlist c\n"
                    + "WHERE e.HwNo =?  AND e.CustomCode = c.Code \n"
                    + "AND e.Value <> '(null)'\n"
                    + "AND (e.EventTime BETWEEN ? AND ?)\n"
                    + "AND c.`Description` IN (?)\n"
                    + "ORDER BY c.Description ASC, e.`Value` ASC, e.`EventTime` ASC";
            PreparedStatement ps;
            try {
                ps = ConnectDB.con.prepareStatement(eventQuery);
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
                            ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(eventTime)),
                            ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(untilTime)));
                    tab[row][0] = ConnectDB.res.getString(1);//EventNo
                    tab[row][1] = eventTime;
                    tab[row][2] = untilTime;
                    tab[row][3] = ConnectDB.res.getString(4);//Value
                    tab[row][4] = ConnectDB.DECIMALFORMAT.format(diffs[1]) + "h";//Hour
                    tab[row][5] = diffs[2] + "m";//Minutes
                    tab[row][6] = diffs[3] + "s";//Secondes
                    row++;
                }
                ps.close();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return tab;
    }

    static class EventsTableModel extends DefaultTableModel implements HierarchicalTableModel {

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
    }

    static class FitScrollPane extends JScrollPane implements ComponentListener {

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
                parent != null && (!(parent instanceof JViewport) || (((JViewport) parent).getClientProperty("HierarchicalTable.mainViewport") == null));
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

    private static String[] DESCRIPTION_COLUMNS = new String[]{"Description Name", "Information"};
    private static String[] DETAIL_COLUMNS = new String[]{"EventNo", "EventTime", "UntilTime", "Value", "Hours", 
        "Minutes", "Seconds"};
//    protected static final Color BG1 = new Color(232, 237, 230);
    protected static final Color BG2 = new Color(243, 234, 217);
    protected static final Color BG3 = new Color(214, 231, 247);
    protected static final Color BG4 = new Color(255, 255, 255);

    private HierarchicalTable _table = null;
    private int _destroyedCount = 0;
    private ListSelectionModelGroup _group = new ListSelectionModelGroup();
}
