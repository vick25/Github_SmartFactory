package eventsPanel;

import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.NestedTableHeader;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
import com.jidesoft.grid.TableColumnGroup;
import com.jidesoft.grid.TableHeaderPopupMenuInstaller;
import com.jidesoft.grid.TableUtils;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.SimpleScrollPane;
import com.jidesoft.tooltip.ExpandedTipUtils;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelEventData;

/**
 *
 * @author Victor Kadiata
 */
public class EventsDataPanel extends javax.swing.JPanel {

    public static boolean isDataGrouped() {
        return dataGrouped;
    }

    public EventsDataPanel(String customCodeValue, String query, String machineTitle) throws SQLException {
//        System.out.println(query);
        this._query = query;
        this._machineTitle = machineTitle;
        TableModelEventData.customCodeValue = customCodeValue;

        model = new TableModelEventData();
        table = new SortableTable(model);
        table.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setNestedTableHeader(true);
        table.setFillsGrids(false);
        table.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        TableColumnGroup period = new TableColumnGroup("Time");
        period.add(table.getColumnModel().getColumn(1));
        period.add(table.getColumnModel().getColumn(2));
        if (table.getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) table.getTableHeader();
            header.addColumnGroup(period);
        }
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(table);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        initComponents();
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popMenu.show(table, evt.getX(), evt.getY());
                }
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrlPanTable.getViewport().setBackground(Color.WHITE);
        scrlPanTable.setViewportView(table);
        table.requestFocus();
        setScrollPaneBorder();
        scrlPanTable.setHorizontalScrollBarPolicy(SimpleScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrlPanTable.setVerticalScrollBarPolicy(SimpleScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        if (runThread != null) {
            while (runThread.isAlive()) {
                try {
                    runThread.interrupt();
                    runThread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        runThread = new Thread(runnable);
        runThread.start();
        ExpandedTipUtils.install(table);
        if (dataGrouped) {
            chkGroupData.setSelected(true);
        } else {
            chkGroupData.setSelected(false);
        }
        Timer time = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getModel().getRowCount() <= 0) {
                    btnExport.setEnabled(false);
                } else {
                    btnExport.setEnabled(true);
                }
            }
        });
        time.start();
//        this.add(this.createLoadingTextArea());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu = new javax.swing.JPopupMenu();
        mniRemoveRow = new javax.swing.JMenuItem();
        mniClearTable = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniExcelExport = new javax.swing.JMenuItem();
        btnCleanTable = new com.jidesoft.swing.JideButton();
        btnExport = new com.jidesoft.swing.JideButton();
        scrlPanTable = new javax.swing.JScrollPane();
        chkGroupData = new javax.swing.JCheckBox();

        mniRemoveRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit16x16.png"))); // NOI18N
        mniRemoveRow.setText("Remove row");
        mniRemoveRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniRemoveRowActionPerformed(evt);
            }
        });
        popMenu.add(mniRemoveRow);

        mniClearTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/edit_clear.png"))); // NOI18N
        mniClearTable.setText("Clean table");
        mniClearTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniClearTableActionPerformed(evt);
            }
        });
        popMenu.add(mniClearTable);
        popMenu.add(jSeparator1);

        mniExcelExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/excel_csv_1.png"))); // NOI18N
        mniExcelExport.setText("Export Excel / CSV");
        mniExcelExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExcelExportActionPerformed(evt);
            }
        });
        popMenu.add(mniExcelExport);

        setBackground(new java.awt.Color(255, 255, 255));

        btnCleanTable.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnCleanTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/edit_clear.png"))); // NOI18N
        btnCleanTable.setText("Clean Table");
        btnCleanTable.setFocusable(false);
        btnCleanTable.setOpaque(true);
        btnCleanTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanTableActionPerformed(evt);
            }
        });

        btnExport.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/excel_csv_2.png"))); // NOI18N
        btnExport.setText("Excel / CSV");
        btnExport.setFocusable(false);
        btnExport.setOpaque(true);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        chkGroupData.setBackground(new java.awt.Color(255, 255, 255));
        chkGroupData.setText("Group data");
        chkGroupData.setFocusable(false);
        chkGroupData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGroupDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrlPanTable)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCleanTable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkGroupData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrlPanTable, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCleanTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chkGroupData))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCleanTable, btnExport});

    }// </editor-fold>//GEN-END:initComponents

    private void btnCleanTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanTableActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Would you like to remove all the lines in the table?",
                "Data Events", 0) == 0) {
            cleanTable();
            tableRow = 0;
            setScrollPaneBorder();
            EventsStatistic.getTree().setSelectionRow(-1);
        }
    }//GEN-LAST:event_btnCleanTableActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        new Thread(new Runnable() {

            @Override
            public void run() {
                File fichier;
                JFileChooser jfc = new JFileChooser(ConnectDB.fsv.getRoots()[0]);
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Excel Documents (*.xls)", "xls"));
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Csv Documents (*.csv)", "csv"));
                try {
                    fichier = new File(ConnectDB.fsv.getRoots()[0] + new StringBuilder(File.separator).
                            append("data_").append(ConnectDB.correctBarreFileName(ConnectDB.SDATE_FORMAT_HOUR.
                                            format(Calendar.getInstance().getTime()))).toString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(EventsDataPanel.this, new StringBuilder(jfc.getSelectedFile().getName()).
                            append("\n The file name is not valid.").toString(), "Export", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                short fileType = 0;
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.setSelectedFile(fichier);
                int result = jfc.showSaveDialog(EventsDataPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (!jfc.getSelectedFile().exists()) {
                        if (jfc.getFileFilter().getDescription().equalsIgnoreCase("Excel Documents (*.xls)")) {
                            table.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT,
                                    HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
                            ConnectDB.outputToExcel(table, jfc.getSelectedFile());
                            fileType = 1;
                        } else {//Output to Csv
                            ConnectDB.outputToCsv(table, jfc.getSelectedFile());
                        }
                        if (JOptionPane.showConfirmDialog(EventsDataPanel.this, "The file was saved sucessfully. "
                                + "Do you want to open the file?", "Export",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == 0) {
                            File f;
                            if (fileType == 1) {
                                f = new File(jfc.getSelectedFile().getAbsolutePath() + ".xls");
                            } else {
                                f = new File(jfc.getSelectedFile().getAbsolutePath() + ".csv");
                            }
                            if (Desktop.isDesktopSupported()) {
                                try {
                                    Desktop.getDesktop().open(f);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(EventsDataPanel.this, "The file could not be opened. "
                                        + "Check if damaged or retry.", "Export", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(EventsDataPanel.this, jfc.getSelectedFile().getName()
                                + " already exists...", "Export", JOptionPane.WARNING_MESSAGE);
//                        return;
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btnExportActionPerformed

    private void mniRemoveRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniRemoveRowActionPerformed
        if (table.getSelectedRow() > -1) {
            model.removeNewRow(table.getSelectedRow());
            table.scrollRowToVisible(table.getRowCount() - 1);
        }
    }//GEN-LAST:event_mniRemoveRowActionPerformed

    private void mniExcelExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExcelExportActionPerformed
        btnExportActionPerformed(evt);
    }//GEN-LAST:event_mniExcelExportActionPerformed

    private void mniClearTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniClearTableActionPerformed
        btnCleanTableActionPerformed(evt);
    }//GEN-LAST:event_mniClearTableActionPerformed

    private void chkGroupDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGroupDataActionPerformed
        dataGrouped = chkGroupData.isSelected();
    }//GEN-LAST:event_chkGroupDataActionPerformed

    private void setScrollPaneBorder() {
        TableUtils.autoResizeAllColumns(table);
        table.getColumnModel().getColumn(0).setMaxWidth(360);
        table.getColumnModel().getColumn(0).setResizable(false);
        scrlPanTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                new StringBuilder("Events of data [").append(tableRow).append("]").toString(),
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                fillTable();
                tableRow = table.getModel().getRowCount();
                setScrollPaneBorder();
//                runOverlayable = false;
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
    };

    synchronized private void fillTable() throws SQLException {
        int nbRow = 1;
        if (_query.isEmpty()) {
            return;
        }
        try (Statement stat = ConnectDB.con.createStatement()) {
            ConnectDB.res = stat.executeQuery(_query);
            while (ConnectDB.res.next()) {
                /** Get the data from the runDataLogQuery method for the total production from the datalog
                 table with the parameters as starttime and endtime
                 */
                runDataLogQuery(ConnectDB.res.getString(1), ConnectDB.res.getString(3));
//                runOverlayable = false;
                if (nbRow > table.getModel().getRowCount()) {
                    model.addNewRow();
                }
                table.setValueAt(nbRow, nbRow - 1, 0);
                table.setValueAt(ConnectDB.res.getString(1), nbRow - 1, 1);
                table.setValueAt(ConnectDB.res.getString(3), nbRow - 1, 2);
                table.setValueAt(ConnectDB.res.getString(2), nbRow - 1, 3);
                table.setValueAt(totalSum, nbRow - 1, 4);
                nbRow++;
            }
        }
    }

    /**This method run the query in the datalog table with the given time frame as parameters and
     returns the total sum of the subtracted values in the totalSum variable
     @param startTime
     @param endTime
     */
    synchronized private void runDataLogQuery(String startTime, String endTime) throws SQLException {
        ArrayList<Double> alValue = new ArrayList<>();
        totalSum = 0d;
        String query = "SELECT d.LogData FROM datalog d\n"
                + "WHERE d.ConfigNo = (SELECT DISTINCT c.ConfigNo\n"
                + "FROM configuration c, hardware h\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND c.AvMinMax = 'Cumulative'\n"
                + "AND h.Machine =? AND c.Active = 1 ORDER BY h.HwNo ASC)\n"
                + "AND d.LogTime >=? AND d.LogTime <=?\n"
                + "ORDER BY d.LogTime ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ps.setString(1, this._machineTitle);
            ps.setString(2, startTime);
            ps.setString(3, endTime);
//            System.out.println(ps.toString());
            this.res = ps.executeQuery();
            while (this.res.next()) {
                alValue.add(res.getDouble(1));
            }
        }
        totalSum = getSubtractedValues(alValue);
    }

    synchronized private double getSubtractedValues(ArrayList alValue) {
        double addTotalSum = 0d;
        for (int i = 0; i < alValue.size(); i++) {
            double xDiff;
            if (i == 0) {
                xDiff = Double.valueOf(alValue.get(i).toString()) - Double.valueOf(alValue.get(i).toString());
                addTotalSum += xDiff;
                continue;
            }
            xDiff = Double.valueOf(alValue.get(i).toString()) - Double.valueOf(alValue.get(i - 1).toString());
            addTotalSum += xDiff;
        }
        return addTotalSum;
    }

    public static void cleanTable() {
        TableModelEventData.customCodeValue = "Event Description";
        model = new TableModelEventData();
        table.setModel(model);
    }

//    private JPanel createLoadingTextArea() {
////        final JPanel pane = new JPanel(new BorderLayout());
////        pane.setBackground(new Color(255, 255, 255));
////        pane.setOpaque(true);
//
//        final DefaultOverlayable overlayTextArea = new DefaultOverlayable(scrlPanTable);
//
//        final InfiniteProgressPanel progressPanel = new InfiniteProgressPanel() {
//            @Override
//            public Dimension getPreferredSize() {
//                return new Dimension(20, 20);
//            }
//        };
//        overlayTextArea.addOverlayComponent(progressPanel);
//        progressPanel.stop();
//        overlayTextArea.setOverlayVisible(false);
//
//        if (runOverlayable) {
//            if (_thread == null || !_thread.isAlive()) {
//                _thread = createThread(progressPanel, scrlPanTable);
//                _thread.start();
//                progressPanel.start();
//            }
//        } else {
//            if (_thread != null) {
//                _thread.interrupt();
//                _thread = null;
//                progressPanel.stop();
//            }
//        }
////        JPanel panel = new JPanel(new BorderLayout());
////        panel.setBackground(new Color(0, 0, 0, 65));
////        panel.add(overlayTextArea);
//        return overlayTextArea;
//    }
//    private Thread createThread(final InfiniteProgressPanel progressPanel, final JComponent pane) {
//        return new Thread() {
//            @Override
//            public void run() {
//                Overlayable overlayable = OverlayableUtils.getOverlayable(table);
//                if (overlayable != null) {
//                    overlayable.setOverlayVisible(true);
//                }
//                while (true) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        break;
//                    }
//                }
//                if (overlayable != null) {
//                    overlayable.setOverlayVisible(false);
//                }
//            }
//        };
//    }
//    public static void main(String[] agrs) throws SQLException {
//        LookAndFeelFactory.installDefaultLookAndFeel();
//        final JFrame frame = new JFrame("Smartfactory Events & Downtime Statistics Report 1.0");
//        frame.setSize(700, 500);
//        frame.setContentPane(new EventsDataPanel("Parts Num", "", ""));
//        frame.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            }
//        });
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnCleanTable;
    private com.jidesoft.swing.JideButton btnExport;
    private javax.swing.JCheckBox chkGroupData;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mniClearTable;
    private javax.swing.JMenuItem mniExcelExport;
    private javax.swing.JMenuItem mniRemoveRow;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JScrollPane scrlPanTable;
    // End of variables declaration//GEN-END:variables
    private static int tableRow = 0;
    private String _query = null, _machineTitle = null;
    private ResultSet res = null;
    private Double totalSum = 0d;
    private Thread runThread = null;
    private static SortableTable table = null;
    private static TableModelEventData model = null;
    private static boolean dataGrouped = false;
//    RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.FRACTION_FIELD);
}
