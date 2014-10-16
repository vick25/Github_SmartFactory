package eventsPanel;

import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
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
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.ExtensionFilter;

/**
 *
 * @author Victor Kadiata
 */
public class EventsLeafTime extends javax.swing.JPanel {

    public EventsLeafTime(String leafName, String myDescription) throws SQLException {
        if (leafName.isEmpty()) {
            return;
        }
//        TableModelEventData.customCodeValue = customCodeValue;

        model = new DefaultTableModel(getDetails(leafName, myDescription), DETAIL_COLUMNS) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new SortableTable(model);
        table.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setNestedTableHeader(true);
        table.setFillsGrids(false);
        table.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(table);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        initComponents();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrlPanTable.getViewport().setBackground(Color.WHITE);
        scrlPanTable.setViewportView(table);
        table.requestFocus();
        setScrollPaneBorder();
        scrlPanTable.setHorizontalScrollBarPolicy(SimpleScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrlPanTable.setVerticalScrollBarPolicy(SimpleScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ExpandedTipUtils.install(table);
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
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExport = new com.jidesoft.swing.JideButton();
        scrlPanTable = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));

        btnExport.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/excel_csv_2.png"))); // NOI18N
        btnExport.setText("Excel / CSV ...");
        btnExport.setFocusable(false);
        btnExport.setOpaque(true);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
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
                        .addGap(0, 524, Short.MAX_VALUE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrlPanTable, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        new Thread(new Runnable() {

            @Override
            public void run() {
                JFileChooser jfc = new JFileChooser(ConnectDB.getDefaultDirectory());
                FileFilter typeExcel = new ExtensionFilter("Excel files", ".xls"),
                        typeCsv = new ExtensionFilter("CSV files", ".csv");
                jfc.addChoosableFileFilter(typeExcel);
                jfc.addChoosableFileFilter(typeCsv);
                jfc.setFileFilter(typeExcel); //Initial filter setting
                File fichier;
                try {
                    fichier = new File(ConnectDB.getDefaultDirectory() + new StringBuilder(File.separator).append("data_").
                            append(new SimpleDateFormat("yyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(EventsLeafTime.this, new StringBuilder(jfc.getSelectedFile().getName()).
                            append("\n The file name is not valid.").toString(), "Export", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                byte fileType = 0;
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.setSelectedFile(fichier);
                int result = jfc.showSaveDialog(EventsLeafTime.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (!jfc.getSelectedFile().exists()) {
                        if (jfc.getFileFilter().getDescription().equalsIgnoreCase("Excel files")) {
                            table.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT,
                                    HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
                            ConnectDB.outputToExcel(table, jfc.getSelectedFile());
                            fileType = 1;
                        } else {//Output to Csv
                            ConnectDB.outputToCsv(table, jfc.getSelectedFile());
                        }
                        if (JOptionPane.showConfirmDialog(EventsLeafTime.this, "The file was saved sucessfully. "
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
                                JOptionPane.showMessageDialog(EventsLeafTime.this, "The file could not be opened. "
                                        + "Check if damaged or retry.", "Export", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(EventsLeafTime.this, jfc.getSelectedFile().getName()
                                + " already exists...", "Export", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btnExportActionPerformed

    private static void setScrollPaneBorder() {
        tableRow = table.getModel().getRowCount();
        TableUtils.autoResizeAllColumns(table);
        table.getColumnModel().getColumn(0).setMaxWidth(360);
        table.getColumnModel().getColumn(0).setResizable(false);
        scrlPanTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                new StringBuilder("Events of data table [").append(tableRow).append(" row(s)]").toString(),
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
    }

    private static String[][] getDetails(String leafString, String rowNameDesc) throws SQLException {
        String[][] tab = null;
        int row = 0;
        if (!rowNameDesc.isEmpty()) {
            String eventQuery = "SELECT e.`EventNo`, e.`EventTime`, e.`UntilTime`, e.`Value`, c.`Description` \n"
                    + "FROM eventlog e, customlist c \n"
                    + "WHERE e.HwNo =?  AND e.CustomCode = c.Code \n"
                    + "AND e.Value <> '(null)' \n"
                    + "AND (e.EventTime BETWEEN ? AND ?) \n"
                    + "AND e.Value =? AND c.`Description` IN (?) \n"
                    + "ORDER BY c.Description ASC, e.`Value` ASC, e.`EventTime` ASC";
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(eventQuery)) {
                ps.setInt(1, EventsStatistic.getMachineID());
                ps.setString(2, EventsStatistic.getMinLogTime());
                ps.setString(3, EventsStatistic.getMaxLogTime());
                ps.setString(4, leafString);
                ps.setString(5, rowNameDesc);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnExport;
    private static javax.swing.JScrollPane scrlPanTable;
    // End of variables declaration//GEN-END:variables
    private static int tableRow = 0;
    private static SortableTable table = null;
    private static TableModel model = null;
    private static final String[] DETAIL_COLUMNS = new String[]{"EventNo", "EventTime", "UntilTime", "Value", "Hours",
        "Minutes", "Seconds"};
//    RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.FRACTION_FIELD);
}
