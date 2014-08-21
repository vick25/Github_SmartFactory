package productionPanel;

import balloonTip.BalloonTipDemo;
import chartTypes.BarChart;
import chartTypes.LineChart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import irepport.view.Print;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import productionPanelChartsPanel.MachineRun;
import productionPanelChartsPanel.ProductionRate;
import productionPanelChartsPanel.TotalProduction;
import reportSettings.ReportOptions;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelProductionData;
import tableModel.TableModelShiftTime;
import viewData.ViewData;

/**
 *
 * @author Victor Kadiata
 */
public class ProductionPane extends javax.swing.JPanel {

    public static void setCatOptions(boolean catOptions) {
        ProductionPane.catOptions = catOptions;
    }

    public static void setChartTitle(String chartTitle) {
        ProductionPane.chartTitle = chartTitle;
    }

    public static String getMachineTitle() {
        return machineTitle;
    }

    public static void setMachineTitle(String machineTitle) {
        ProductionPane.machineTitle = machineTitle;
    }

    public ProductionPane(JFrame parent) throws SQLException {
        //        sclient = new Socket(ConnectDB.serverIP, ConnectDB.PORTMAINSERVER);
        ConnectDB.getConnectionInstance();
        initComponents();
        _parent = parent;
        autoFill();
        fillProductionModuleOptions();
        panProductionRate.setVisible(false);
        panShiftTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                "Hours", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        setTableNameOfValues(viewDateTableName);
        setTableTime(3);//tables for the hour shifts
        setTableValues();//table for the chartPanel datas
        AutoCompleteDecorator.decorate(cmbMachineTitle);
//        addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                requestFocus();
//            }
//        });
        Timer t = new Timer(150, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tbpPanDetails.getTabCount() > 0) {
                    btnCopyToClipboard.setEnabled(true);
                    btnViewData.setEnabled(true);
                    btnPrintChart.setEnabled(true);
                    btnRefresh.setEnabled(true);
                } else {
                    btnCopyToClipboard.setEnabled(false);
                    btnViewData.setEnabled(false);
                    btnPrintChart.setEnabled(false);
                    btnRefresh.setEnabled(false);
                }
                if (tableValues.getRowCount() <= 0) {
                    btnExportExcelCsv.setEnabled(false);
                } else {
                    btnExportExcelCsv.setEnabled(true);
                }
            }
        });
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setInitialDelay(0);
        t.start();
        tbpPanDetails.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
                if (catTabbedChange) {
                    String channel;
                    StringTokenizer stTabOption;
                    try {
                        switch (tbpPanDetails.getTitleAt(index)) {
                            case "Production Rate":
                                stTabOption = new StringTokenizer(rateTab, ";");
                                cmbOptions.setSelectedItem(stTabOption.nextToken());
                                channel = stTabOption.nextToken();
                                cmbChannel.setSelectedItem(channel);
                                break;
                            case "Total Production":
                                stTabOption = new StringTokenizer(totTab, ";");
                                cmbOptions.setSelectedItem(stTabOption.nextToken());
                                channel = stTabOption.nextToken();
                                cmbChannel.setSelectedItem(channel);
                                break;
                            default://Machine Run
                                stTabOption = new StringTokenizer(runTab, ";");
                                cmbOptions.setSelectedItem(stTabOption.nextToken());
                                channel = stTabOption.nextToken();
                                cmbChannel.setSelectedItem(channel);
                                break;
                        }
                        viewDataInTable();
                    } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                        catOptions = true;
                        cmbOptionsItemStateChanged(null);
                        cmbMachineTitleItemStateChanged(null);
                    }
                }
            }
        });
        tableValues.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2 && tableValues.getModel().getRowCount() > 0) {
                        if (viewData != null) {
                            viewData.dispose();
                        }
                        viewData = new ViewData(_parent, false, modelData);
                        viewData.setTitle(viewDateTableName + " Table");
                        viewData.setVisible(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(_parent, "No values to display...");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (tableValues.getModel().getRowCount() > 0) {
                    tableValues.setToolTipText("Double-click the table for a better viewing of values");
                }
            }
        });
        jPanel2.requestFocus();
        lblActive.setHorizontalAlignment(SwingConstants.RIGHT);
        setPropertiesTimeSaved(ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.PRODPANESET, getPropertiesToSave()));
//        System.out.println(jSplitPane2.getPreferredSize());
//        System.out.println(jPanel8.getPreferredSize());
//        jSplitPane2.setDividerLocation(jPanel8.getPreferredSize().height);
        //        TAMPON = new byte[BUFFER];
//        Thread th = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    in = new DataInputStream(sclient.getInputStream());
//                    out = new DataOutputStream(sclient.getOutputStream());
//                    int nbbit;
//                    String Lut = "";
//                    while ((nbbit = in.read(TAMPON)) != -1) {
//                        Lut = new String(TAMPON, 0, nbbit);
//                        String nomFichier = "Settings.ini";
//                        if (Lut.equals("false")) {
//                            String wrongMessage = "The file \"" + nomFichier + "\""
//                                    + " doesn't exist in the server !!!";
//                            MainFrame.progress.setProgress(0);
//                            MainFrame.progress.setProgressStatus(wrongMessage);
//                            JOptionPane.showMessageDialog(parent, wrongMessage, "Fatal error", JOptionPane.ERROR_MESSAGE);
//                            MainFrame.progress.setIndeterminate(false);
//                            MainFrame.progress.setProgress(100);
//                        } else {
//                            fileSize = Long.parseLong(Lut);
//                            Thread.sleep(300);
//                            File dest = new File(dir + File.separator + nomFichier);
//                            try (FileOutputStream fout = new FileOutputStream(dest)) {
//                                int nbRead;
//                                MainFrame.progress.setIndeterminate(false);
//                                MainFrame.progress.setProgressStatus("Downloading ......");
//                                cumulFichier = 0;
//                                long endCopyFile = 0;
//                                while ((nbRead = in.read(TAMPON)) != -1) {
//                                    fout.write(TAMPON, 0, nbRead);
//                                    cumulFichier += nbRead;
//                                    endCopyFile += nbRead;
//                                    MainFrame.progress.setProgress((int) ((cumulFichier * 100) / ((int) fileSize)));
//                                    if (endCopyFile >= fileSize) {
//                                        break;
//                                    }
//                                }
//                            }
//                            MainFrame.progress.setProgress(100);
////                            if (JOptionPane.showConfirmDialog(parent, "Download completed successfully. "
////                                    + "Do you want to open the file \"" + nomFichier + "\"", "Confirmation", 0) == 0) {
////                                if (Desktop.isDesktopSupported()) {
////                                    Desktop desktop = Desktop.getDesktop();
////                                    desktop.open(dest);
////                                }
////                            }
//                        }
//                    }
//                } catch (IOException | HeadlessException | NumberFormatException | InterruptedException e) {
////                    System.out.println(e.getMessage());
//                }
//            }
//        };
//        th.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jToolBar3 = new javax.swing.JToolBar();
        btnReset = new javax.swing.JButton();
        btnEmail = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnSettings = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnPrintChart = new javax.swing.JButton();
        btnCopyToClipboard = new javax.swing.JButton();
        btnViewData = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        btnViewHistory = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        jToolBar4 = new javax.swing.JToolBar();
        btnMessage = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbMachineTitle = new org.jdesktop.swingx.JXComboBox();
        jPanel4 = new javax.swing.JPanel();
        cmbChannel = new org.jdesktop.swingx.JXComboBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblActive = new javax.swing.JLabel();
        btnPlotChart = new com.jidesoft.swing.JideButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        cmbOptions = new org.jdesktop.swingx.JXComboBox();
        btnAddMode = new com.jidesoft.swing.JideButton();
        panProductionRate = new javax.swing.JPanel();
        radPerHour = new javax.swing.JRadioButton();
        radPerMin = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        chkPerShift = new javax.swing.JCheckBox();
        cmbPFrom = new com.jidesoft.combobox.DateComboBox();
        cmbPTo = new com.jidesoft.combobox.DateComboBox();
        panShiftTime = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chkFrom = new javax.swing.JCheckBox();
        chkTo = new javax.swing.JCheckBox();
        btnShiftTable = new com.jidesoft.swing.JideButton();
        chkActiveChannel = new javax.swing.JCheckBox();
        lblSpace = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        tbpPanDetails = new com.jidesoft.swing.JideTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        panTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        btnExportExcelCsv = new com.jidesoft.swing.JideButton();
        panShiftTotals = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblTotalProductionSum = new javax.swing.JLabel();
        panPerShiftTotals = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblShiftSum1 = new javax.swing.JLabel();
        lblShiftSum3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblShiftSum2 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        btnCleanTable = new com.jidesoft.swing.JideButton();

        setBackground(new java.awt.Color(102, 102, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jToolBar3.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9), new java.awt.Color(0, 0, 204))); // NOI18N
        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        btnReset.setBackground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icon-48-clear.png"))); // NOI18N
        btnReset.setText("Clean");
        btnReset.setToolTipText("Clean interface to orginal state");
        btnReset.setFocusable(false);
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.setOpaque(false);
        btnReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jToolBar3.add(btnReset);

        btnEmail.setBackground(new java.awt.Color(255, 255, 255));
        btnEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/mail_message_new_22x22.png"))); // NOI18N
        btnEmail.setText("Email");
        btnEmail.setEnabled(false);
        btnEmail.setFocusable(false);
        btnEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(btnEmail);

        btnStop.setBackground(new java.awt.Color(255, 255, 255));
        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/close_box_red24.png"))); // NOI18N
        btnStop.setText("Stop");
        btnStop.setToolTipText("Stop current process");
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jToolBar3.add(btnStop);
        jToolBar3.add(jSeparator3);

        btnSettings.setBackground(new java.awt.Color(255, 255, 255));
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/advancedsettings(4).png"))); // NOI18N
        btnSettings.setText("Settings");
        btnSettings.setFocusable(false);
        btnSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });
        jToolBar3.add(btnSettings);

        jToolBar1.add(jToolBar3);

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chart", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9), new java.awt.Color(0, 0, 204))); // NOI18N
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnPrintChart.setBackground(new java.awt.Color(255, 255, 255));
        btnPrintChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileprint(23).png"))); // NOI18N
        btnPrintChart.setText("Print report");
        btnPrintChart.setToolTipText("View the chart in a printable format");
        btnPrintChart.setFocusable(false);
        btnPrintChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrintChart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrintChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintChartActionPerformed(evt);
            }
        });
        jToolBar2.add(btnPrintChart);

        btnCopyToClipboard.setBackground(new java.awt.Color(255, 255, 255));
        btnCopyToClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/1_folder2.png"))); // NOI18N
        btnCopyToClipboard.setText("Copy to clipboard");
        btnCopyToClipboard.setToolTipText("Copy the chart to clipboard");
        btnCopyToClipboard.setFocusable(false);
        btnCopyToClipboard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopyToClipboard.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopyToClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyToClipboardActionPerformed(evt);
            }
        });
        jToolBar2.add(btnCopyToClipboard);

        btnViewData.setBackground(new java.awt.Color(255, 255, 255));
        btnViewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/view_icon.png"))); // NOI18N
        btnViewData.setText("View Data");
        btnViewData.setToolTipText("View the generated charts data values in a table");
        btnViewData.setFocusable(false);
        btnViewData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDataActionPerformed(evt);
            }
        });
        jToolBar2.add(btnViewData);
        jToolBar2.add(jSeparator9);

        btnViewHistory.setBackground(new java.awt.Color(255, 255, 255));
        btnViewHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/time.png"))); // NOI18N
        btnViewHistory.setText("History");
        btnViewHistory.setFocusable(false);
        btnViewHistory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewHistory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewHistoryActionPerformed(evt);
            }
        });
        jToolBar2.add(btnViewHistory);
        jToolBar2.add(jSeparator10);

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/gtk_refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setToolTipText("Refresh the chart panel");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        jToolBar1.add(jToolBar2);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(204, 0, 0));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jToolBar1.add(jPanel12);

        jToolBar4.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        btnMessage.setBackground(new java.awt.Color(255, 255, 255));
        btnMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/light_on.png"))); // NOI18N
        btnMessage.setToolTipText("0 flag(s) raised.");
        btnMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMessage.setFocusable(false);
        btnMessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMessage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMessageActionPerformed(evt);
            }
        });
        jToolBar4.add(btnMessage);

        jToolBar1.add(jToolBar4);

        jSplitPane1.setBackground(new java.awt.Color(255, 255, 255));
        jSplitPane1.setDividerLocation(345);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setOneTouchExpandable(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("<html>Machine:<font color=red>*</font></html>");

        cmbMachineTitle.setFocusable(false);
        cmbMachineTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMachineTitleItemStateChanged(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Production", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        cmbChannel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbChannelItemStateChanged(evt);
            }
        });

        jLabel3.setText("Channel:");

        lblActive.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnPlotChart.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnPlotChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/frame_chart.png"))); // NOI18N
        btnPlotChart.setText("Plot Chart");
        btnPlotChart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPlotChart.setOpaque(true);
        btnPlotChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlotChartActionPerformed(evt);
            }
        });

        jLabel15.setText("Options:");

        cmbOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbOptionsItemStateChanged(evt);
            }
        });

        btnAddMode.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnAddMode.setText("Add...");
        btnAddMode.setToolTipText("Add options");
        btnAddMode.setOpaque(true);
        btnAddMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddModeActionPerformed(evt);
            }
        });

        panProductionRate.setBackground(new java.awt.Color(255, 255, 255));

        radPerHour.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radPerHour);
        radPerHour.setSelected(true);
        radPerHour.setText("per/Hr");
        radPerHour.setEnabled(false);
        radPerHour.setFocusable(false);
        radPerHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPerHourActionPerformed(evt);
            }
        });

        radPerMin.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radPerMin);
        radPerMin.setText("per/Min");
        radPerMin.setEnabled(false);
        radPerMin.setFocusable(false);
        radPerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPerMinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panProductionRateLayout = new javax.swing.GroupLayout(panProductionRate);
        panProductionRate.setLayout(panProductionRateLayout);
        panProductionRateLayout.setHorizontalGroup(
            panProductionRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProductionRateLayout.createSequentialGroup()
                .addComponent(radPerHour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radPerMin)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panProductionRateLayout.setVerticalGroup(
            panProductionRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProductionRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(radPerHour)
                .addComponent(radPerMin))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActive, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(panProductionRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(btnAddMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(cmbChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActive, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panProductionRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlotChart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Period", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        chkPerShift.setBackground(new java.awt.Color(255, 255, 255));
        chkPerShift.setSelected(true);
        chkPerShift.setText("Per Shift");
        chkPerShift.setFocusable(false);
        chkPerShift.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPerShiftItemStateChanged(evt);
            }
        });

        cmbPFrom.setShowWeekNumbers(false);
        cmbPFrom.setDate(Calendar.getInstance().getTime());
        cmbPFrom.setFocusable(false);
        cmbPFrom.setFormat(ConnectDB.SDATEFORMATHOUR);
        cmbPFrom.setRequestFocusEnabled(false);
        cmbPFrom.setTimeDisplayed(true);
        cmbPFrom.setTimeFormat("HH:mm:ss");
        cmbPFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPFromActionPerformed(evt);
            }
        });

        cmbPTo.setShowWeekNumbers(false);
        cmbPTo.setDate(Calendar.getInstance().getTime());
        cmbPTo.setFocusable(false);
        cmbPTo.setFormat(ConnectDB.SDATEFORMATHOUR);
        cmbPTo.setRequestFocusEnabled(false);
        cmbPTo.setTimeDisplayed(true);
        cmbPTo.setTimeFormat("HH:mm:ss");
        cmbPTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPToActionPerformed(evt);
            }
        });

        panShiftTime.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panShiftTimeLayout = new javax.swing.GroupLayout(panShiftTime);
        panShiftTime.setLayout(panShiftTimeLayout);
        panShiftTimeLayout.setHorizontalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panShiftTimeLayout.setVerticalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );

        chkFrom.setBackground(new java.awt.Color(255, 255, 255));
        chkFrom.setSelected(true);
        chkFrom.setText("From:");
        chkFrom.setFocusable(false);
        chkFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFromItemStateChanged(evt);
            }
        });

        chkTo.setBackground(new java.awt.Color(255, 255, 255));
        chkTo.setSelected(true);
        chkTo.setText("To:");
        chkTo.setFocusable(false);
        chkTo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkToItemStateChanged(evt);
            }
        });

        btnShiftTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/refresh16x16.png"))); // NOI18N
        btnShiftTable.setToolTipText("Reset to defaults value or edit Time-Shift table by double-cliking the button");
        btnShiftTable.setFocusable(false);
        btnShiftTable.setOpaque(true);
        btnShiftTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShiftTableMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkFrom)
                                    .addComponent(chkTo))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbPFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(cmbPTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(chkPerShift)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkFrom)
                    .addComponent(cmbPFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkTo)
                    .addComponent(cmbPTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkPerShift)
                    .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        chkActiveChannel.setBackground(new java.awt.Color(255, 255, 255));
        chkActiveChannel.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        chkActiveChannel.setSelected(true);
        chkActiveChannel.setText("Active Channels");
        chkActiveChannel.setToolTipText("Show only active channels for a specified machine");
        chkActiveChannel.setFocusable(false);
        chkActiveChannel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkActiveChannel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkActiveChannel.setOpaque(false);
        chkActiveChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActiveChannelActionPerformed(evt);
            }
        });

        lblSpace.setBackground(new java.awt.Color(255, 255, 255));
        lblSpace.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSpace.setForeground(new java.awt.Color(204, 0, 0));
        lblSpace.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMachineTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActiveChannel))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSpace, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel4, jPanel6});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkActiveChannel)
                    .addComponent(cmbMachineTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jSplitPane2.setBackground(new java.awt.Color(255, 255, 255));
        jSplitPane2.setDividerLocation(310);
        jSplitPane2.setDividerSize(8);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setOneTouchExpandable(true);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tbpPanDetails.setColorTheme(com.jidesoft.swing.JideTabbedPane.COLOR_THEME_WINXP);
        tbpPanDetails.setBoldActiveTab(true);
        tbpPanDetails.setFocusable(false);
        tbpPanDetails.setScrollSelectedTabOnWheel(true);
        tbpPanDetails.setShowCloseButton(true);
        tbpPanDetails.setShowCloseButtonOnMouseOver(true);
        tbpPanDetails.setShowCloseButtonOnSelectedTab(true);
        tbpPanDetails.setShowCloseButtonOnTab(true);
        tbpPanDetails.setShowTabButtons(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        panTable.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panTableLayout = new javax.swing.GroupLayout(panTable);
        panTable.setLayout(panTableLayout);
        panTableLayout.setHorizontalGroup(
            panTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        panTableLayout.setVerticalGroup(
            panTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        btnExportExcelCsv.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnExportExcelCsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/office/insert-excel.gif"))); // NOI18N
        btnExportExcelCsv.setText("Export to Excel/Csv...");
        btnExportExcelCsv.setOpaque(true);
        btnExportExcelCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelCsvActionPerformed(evt);
            }
        });

        panShiftTotals.setBackground(new java.awt.Color(255, 255, 255));
        panShiftTotals.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Total Parts for Period:");
        jLabel14.setToolTipText("");

        lblTotalProductionSum.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        panPerShiftTotals.setBackground(new java.awt.Color(255, 255, 255));
        panPerShiftTotals.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Shift Totals", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel8.setText("Shift1:");

        jLabel10.setText("Shift3:");

        jLabel9.setText("Shift2:");

        javax.swing.GroupLayout panPerShiftTotalsLayout = new javax.swing.GroupLayout(panPerShiftTotals);
        panPerShiftTotals.setLayout(panPerShiftTotalsLayout);
        panPerShiftTotalsLayout.setHorizontalGroup(
            panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPerShiftTotalsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblShiftSum1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShiftSum2)
                    .addComponent(lblShiftSum3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 86, Short.MAX_VALUE))
        );

        panPerShiftTotalsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblShiftSum1, lblShiftSum2, lblShiftSum3});

        panPerShiftTotalsLayout.setVerticalGroup(
            panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPerShiftTotalsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(lblShiftSum1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(lblShiftSum2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panPerShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(lblShiftSum3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panPerShiftTotalsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblShiftSum1, lblShiftSum2, lblShiftSum3});

        javax.swing.GroupLayout panShiftTotalsLayout = new javax.swing.GroupLayout(panShiftTotals);
        panShiftTotals.setLayout(panShiftTotalsLayout);
        panShiftTotalsLayout.setHorizontalGroup(
            panShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panShiftTotalsLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalProductionSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panPerShiftTotals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panShiftTotalsLayout.setVerticalGroup(
            panShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panShiftTotalsLayout.createSequentialGroup()
                .addComponent(panPerShiftTotals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panShiftTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTotalProductionSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnCleanTable.setButtonStyle(com.jidesoft.swing.JideButton.FLAT_STYLE);
        btnCleanTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/edit_clear.png"))); // NOI18N
        btnCleanTable.setText("Clean Table");
        btnCleanTable.setOpaque(true);
        btnCleanTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnCleanTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                        .addComponent(btnExportExcelCsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panShiftTotals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jSeparator7)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panShiftTotals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExportExcelCsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCleanTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))))
        );

        jSplitPane2.setRightComponent(jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        try {
            catTabbedChange = false;
            tbpPanDetails.removeAll();
            lblTotalProductionSum.setText("");
            lblShiftSum1.setText("");
            lblShiftSum2.setText("");
            lblShiftSum3.setText("");
            btnMessage.setIcon(new ImageIcon(getClass().getResource("/images/icons/light_on.png")));
            btnMessage.setToolTipText("0 flag(s) raised.");
            if (tableDateWithShifts != null) {
                tableDateWithShifts.clear();
            }
            cmbMachineTitle.setSelectedIndex(0);
            cmbOptions.setSelectedIndex(0);
            btnCleanTableActionPerformed(evt);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        barchart.makeItBlink.stop();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPrintChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintChartActionPerformed
        reportOptions = new ReportOptions(_parent, true);
        String reportTitle = chartTitle.substring(14, chartTitle.lastIndexOf('>'));
        if (!reportTitle.isEmpty()) {
            reportOptions.setReportTitle(reportTitle);
            ReportOptions.txtReportTitle.setText(reportOptions.getReportTitle());
            ReportOptions.txtReportTitle.setForeground(Color.black);
            ReportOptions.txtReportTitle.setHorizontalAlignment(javax.swing.JTextField.LEADING);
            ReportOptions.txtReportTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        }
        Thread threadPrint = new Thread() {

            @Override
            public void run() {
                try {
                    Map<String, Object> hm = new HashMap<>();
                    JasperPrint jpMaster;
                    ConnectDB.setMainDir(new File(ConnectDB.DEFAULTDIRECTORY + File.separator + "SmartFactory Data"));
                    if (!ConnectDB.getMainDir().exists()) {
                        ConnectDB.getMainDir().mkdirs();
                    }

                    String dirIcon = ConnectDB.getMainDir().getAbsolutePath() + File.separator + "chart.png";
                    panChart = (JPanel) tbpPanDetails.getSelectedComponent();
                    ChartUtils.writePngToFile(panChart, new File(dirIcon));

                    hm.put("logo", reportOptions.getPhoto());
                    hm.put("email", reportOptions.getEmail());
                    hm.put("website", reportOptions.getWebsite());
                    hm.put("address", ConnectDB.removeLineBreaks(reportOptions.getAddress()));
                    hm.put("logo2", getClass().getResourceAsStream("/jasper/smartfactory.png"));
                    if (reportOptions.isAddChart()) {
                        hm.put("photo", new FileInputStream(new File(dirIcon)));
                    }
                    hm.put("companyTitle", reportOptions.getCompanyTitle());
                    hm.put("title", reportOptions.getReportTitle());
                    hm.put("date1", ConnectDB.SDATEFORMATHOUR.format(dt_startP));
                    hm.put("date2", ConnectDB.SDATEFORMATHOUR.format(dt_endP));
                    hm.put("valueTitle", tableType);//represents parts/hr or rate/hr
                    hm.put("SUBREPORT_DIR", getClass().getResourceAsStream("/jasper/tableExample.jasper"));
                    if (reportOptions.isAddTable()) {
                        if (tableValues.getModel().getRowCount() > 0) {
                            hm.put("tableName", viewDateTableName + " Table");
                            hm.put("MyTableModel", tableValues.getModel());
                        } else {
                            hm.put("tableName", "");
                        }
                    }
                    jpMaster = JasperFillManager.fillReport(getClass().getResourceAsStream(""
                            + "/jasper/chartModified.jasper"), hm, ConnectDB.con);
                    Print.viewReport(_parent, jpMaster, false, ConnectDB.LOCALE);
                } catch (FileNotFoundException | JRException ex) {
                    ex.printStackTrace();
                }
            }
        };
        reportOptions.setVisible(true);
        if (reportOptions.isWindowClosed()) {
            threadPrint.start();
        }
    }//GEN-LAST:event_btnPrintChartActionPerformed

    private void btnCopyToClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyToClipboardActionPerformed
        try {
            panChart = (JPanel) tbpPanDetails.getSelectedComponent();
            ChartUtils.copyImageToClipboard(panChart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCopyToClipboardActionPerformed

    private void btnViewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDataActionPerformed
        viewDataInTable();
    }//GEN-LAST:event_btnViewDataActionPerformed

    private void btnViewHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewHistoryActionPerformed
        try {
            if (viewHistory != null) {
                viewHistory.dispose();
            }
            viewHistory = new ViewHistory(_parent, false, machineTitle, cmbPFrom.getDate());
            viewHistory.setVisible(true);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnViewHistoryActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        btnPlotChartActionPerformed(evt);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMessageActionPerformed
        if (BarChart.getFlagDialog() != null) {
            BarChart.getFlagDialog().dispose();
            BarChart.setFlagDialog(null);
        }
        if (BarChart.getFlagDialog() == null) {
            BarChart.setFlagDialog(new Flag(_parent, false, BarChart.messageFlag));
            BarChart.getFlagDialog().setVisible(true);
        }
    }//GEN-LAST:event_btnMessageActionPerformed

    private void cmbMachineTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMachineTitleItemStateChanged
        if (catMachine) {
            if (clearTabbedPane) { //boolean to check if a panel exists in the tabbedpane
                lblShiftSum1.setText("");
                lblShiftSum2.setText("");
                lblShiftSum3.setText("");
                lblTotalProductionSum.setText("");
                catTabbedChange = false;
                tbpPanDetails.removeAll();
            }
            btnCleanTableActionPerformed(null);
            clearTabbedPane = true;
            clearLabelActive();
            alProdRate.clear();
            alTotalProd.clear();
            catChannel = false;
            catOptions = false;
            machineTitle = cmbMachineTitle.getSelectedItem().toString();
            try {
                machineID = ConnectDB.getIDMachine(machineTitle);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
            if (!cmbMachineTitle.getSelectedItem().equals("") && cmbMachineTitle.getSelectedIndex() > 0) {
                String query;
                cmbChannel.removeAllItems();
                cmbChannel.addItem(" ");
                if (chkActiveChannel.isSelected()) {
                    query = "SELECT DISTINCT c.ChannelID\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND h.HwNo = '" + machineID + "'\n"
                            + "AND c.Active = 1";
                } else {
                    query = "SELECT DISTINCT c.ChannelID\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND h.HwNo = '" + machineID + "'";
                }
                if (!cmbOptions.getSelectedItem().equals("") && cmbOptions.getSelectedIndex() > 0) {
                    String module = getModuleOption(cmbOptions.getSelectedItem().toString());
                    query = query.substring(0) + " AND c.AvMinMax = '" + module + "'";
                }
                try (Statement stmt = ConnectDB.con.createStatement()) {
                    ConnectDB.res = stmt.executeQuery(query);
                    while (ConnectDB.res.next()) {
                        cmbChannel.addItem(ConnectDB.res.getString(1));
                    }
                } catch (Exception ex) {
//                    serverConnectionLabel = "<html><font color=red><strong>Lost Connection @ "
//                            + ConnectDB.pref.get("IPServerAddress", serverIP) + "</strong></font></html>";
//                    labelServer.setFont(f);
//                    labelServer.setText(serverConnectionLabel);
                }
            } else {
                cmbChannel.removeAllItems();
                cmbChannel.addItem(" ");
                clearLabelActive();
                cleanTable();
                lblTotalProductionSum.setText("");
            }
            catOptions = true;
            catChannel = true;
        }
    }//GEN-LAST:event_cmbMachineTitleItemStateChanged

    private void cmbChannelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbChannelItemStateChanged
        if (catChannel) {
            if (!cmbChannel.getSelectedItem().equals("") && cmbChannel.getSelectedIndex() > 0) {
                channelTitle = cmbChannel.getSelectedItem().toString();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Active FROM `configuration` "
                        + "WHERE ChannelID =? ORDER BY ACTIVE")) {
                    ps.setString(1, channelTitle);
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        if (ConnectDB.res.getInt(1) == 1) {
                            lblActive.setIcon(new ImageIcon(getClass().getResource("/images/icons/tick.png")));
                            lblActive.setToolTipText("\"" + channelTitle + "\" channel recent activity");
                            lblActive.setText("Active");
                        } else {
                            lblActive.setIcon(new ImageIcon(getClass().getResource("/images/icons/stop.png")));
                            lblActive.setToolTipText("\"" + channelTitle + "\" channel recent activity");
                            lblActive.setText("Not Active");
                        }
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                try (PreparedStatement ps1 = ConnectDB.con.prepareStatement("SELECT LogTime FROM datalog\n"
                        + "WHERE ConfigNo =?\n"
                        + "ORDER BY LogTime DESC\n"
                        + "LIMIT 1;")) {
                    ps1.setInt(1, ConnectDB.getConfigNo(channelTitle, machineTitle));
                    ConnectDB.res = ps1.executeQuery();
                    while (ConnectDB.res.next()) {
                        lblMessage.setText("Last data recorded on: " + ConnectDB.res.getString(1));
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            } else {
                clearLabelActive();
                lblTotalProductionSum.setText("");
                cleanTable();
            }
        } else {
            clearLabelActive();
        }
    }//GEN-LAST:event_cmbChannelItemStateChanged

    private void btnPlotChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlotChartActionPerformed
        cmbPToActionPerformed(evt);
        BarChart.messageFlag.clear();
        btnMessage.setIcon(new ImageIcon(getClass().getResource("/images/icons/light_on.png")));
        if (cmbPFrom.isFocusOwner() || cmbPTo.isFocusOwner()) {
            btnPlotChart.requestFocus();
        }
        if (viewData != null) {
            viewData.dispose();
        }
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                if (balloonTip()) {
                    try {
                        getComponentDates();
                        String query, unit;
                        //Production rate (Rate)
                        catTabbedChange = false;
                        if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Rate")) {
                            if (radPerHour.isSelected()) {
                                unit = "parts/hr";
                                query = "SELECT dl0.LogTime AS 'Time', (dl0.LogData * 60) AS ? "
                                        + "FROM datalog dl0 "
                                        + "WHERE dl0.ConfigNo =? "
                                        + "AND dl0.LogTime >=? AND dl0.LogTime <=? "
                                        + "ORDER BY 'Time' ASC";
                            } else {
                                unit = "parts/min";
                                query = "SELECT dl0.LogTime AS 'Time', dl0.LogData AS ? "
                                        + "FROM datalog dl0 "
                                        + "WHERE dl0.ConfigNo =? "
                                        + "AND dl0.LogTime >=? AND dl0.LogTime <=? "
                                        + "ORDER BY 'Time' ASC";
                            }
                            if (catChannel) {
                                if (!cmbChannel.getSelectedItem().equals("")) {
                                    lineChart = null;
                                    lineChart = new LineChart(ConnectDB.getConfigNo(channelTitle, machineTitle), query,
                                            ConnectDB.firstLetterCapital(unit), machineTitle, channelTitle, dt_startP, dt_endP);
                                    if (lineChart.getChart() != null) {
                                        productionRate = new ProductionRate(lineChart.getChart(), unit);
                                    }
                                    if (!alProdRate.contains(productionRate) && productionRate != null) {
                                        alProdRate.add(productionRate);
                                    } else {
                                        alProdRate.clear();
                                    }
                                    if (lineChart.getChart() != null) {
                                        createTabbedPanel(alProdRate.get(0), "Production Rate");
                                        rateTab = cmbOptions.getSelectedItem().toString() + ";" + channelTitle;
                                        catTabbedChange = true;
                                    }
                                }
                            }
                        } else //Total production
                        if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Cumulative")) {
                            query = "SELECT dl0.LogTime AS 'Time', dl0.LogData AS ? "
                                    + "FROM datalog dl0 "
                                    + "WHERE dl0.ConfigNo =? "
                                    + "AND dl0.LogTime >=? AND dl0.LogTime <=? "
                                    + "ORDER BY 'Time' ASC";
                            if (catChannel) {
                                if (!cmbChannel.getSelectedItem().equals("")) {
                                    barchart = null;
                                    if (chkPerShift.isSelected()) {
                                        //With shifts
                                        if (ConnectDB.checkTableValidity(tableTime)) {
                                            barchart = new BarChart(ConnectDB.getConfigNo(channelTitle, machineTitle),
                                                    query, true, machineTitle, channelTitle, dt_startP, dt_endP);
                                            if (barchart.getChart() != null) {
                                                barchart.getChart().setBorder(new EmptyBorder(5, 5, 5, 20));
                                                JPanel panel = new JPanel();
                                                Legend legend = new Legend(barchart.getChart(), 0);
                                                panel.add(legend);
                                                totalProduction = new TotalProduction(barchart.getChart());
                                                totalProduction.add(panel, BorderLayout.SOUTH);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(_parent, "Please check the table shift "
                                                    + "for valid hours, not the \"00:00\"");
                                            return;
                                        }
                                    } else {
                                        //No time shifts
                                        barchart = new BarChart(ConnectDB.getConfigNo(channelTitle, machineTitle), query,
                                                false, machineTitle, channelTitle, dt_startP, dt_endP);
                                        if (barchart.getChart() != null) {
                                            totalProduction = new TotalProduction(barchart.getChart());
                                        }
                                    }
                                    if (!alTotalProd.contains(totalProduction) && totalProduction != null) {
                                        alTotalProd.add(totalProduction);
                                    } else {
                                        alTotalProd.clear();
                                    }
                                    if (barchart.getChart() != null) {
                                        createTabbedPanel(alTotalProd.get(0), "Total Production");
                                        totTab = cmbOptions.getSelectedItem().toString() + ";" + channelTitle;
                                        catTabbedChange = true;
                                    }
                                }
                            }
                        } else if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Average")) {
                            query = "SELECT dl0.LogTime AS 'Time', dl0.LogData AS ? "
                                    + "FROM datalog dl0 "
                                    + "WHERE dl0.ConfigNo =? "
                                    + "AND dl0.LogTime >=? AND dl0.LogTime <=? "
                                    + "ORDER BY 'Time' ASC";
                            if (catChannel) {
                                if (!cmbChannel.getSelectedItem().equals("")) {
                                    machineRun = null;
                                    machineRun = new MachineRun(ConnectDB.getConfigNo(channelTitle, machineTitle), query);
                                    if (machineRun != null) {
                                        alMachineRun.add(machineRun);
                                    } else {
                                        alMachineRun.clear();
                                    }
                                    createTabbedPanel(alMachineRun.get(0), "Machine Run (ON/OFF)");
                                    runTab = cmbOptions.getSelectedItem().toString() + ";" + channelTitle;
                                    catTabbedChange = true;
                                }
                            }
                        }
                    } catch (HeadlessException e) {
                        e.printStackTrace();
                        catTabbedChange = false;
                    } catch (SQLException ex) {
                        catTabbedChange = false;
                        ConnectDB.catchSQLException(ex);
                    }
                }
            }
        });
        thread.start();
    }//GEN-LAST:event_btnPlotChartActionPerformed

    private void cmbOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOptionsItemStateChanged
        if (catOptions) {
            if (!cmbOptions.getSelectedItem().equals("") && cmbOptions.getSelectedIndex() > 0) {
                catTabbedChange = false;
                String query, module = getModuleOption(cmbOptions.getSelectedItem().toString());
                switch (module) {
                    case "Rate":
                        setTab("Production Rate");
                        panShiftTotals.setVisible(false);
                        panProductionRate.setVisible(true);
                        radPerHour.setEnabled(true);
                        radPerMin.setEnabled(true);
                        chkPerShift.setVisible(false);
                        btnShiftTable.setVisible(false);
                        panShiftTime.setVisible(false);
                        break;
                    case "Average":
                        setTab("Machine Run (ON/OFF)");
                        panShiftTotals.setVisible(false);
                        chkPerShift.setVisible(false);
                        btnShiftTable.setVisible(false);
                        panProductionRate.setVisible(false);
                        panShiftTime.setVisible(false);
                        break;
                    case "Cumulative":
                        setTab("Total Production");
                        panShiftTotals.setVisible(true);
                        panProductionRate.setVisible(false);
                        btnShiftTable.setVisible(true);
                        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
                        chkPerShift.setVisible(true);
                        panShiftTime.setVisible(true);
                        chkPerShiftItemStateChanged(evt);
                        break;
                }
                if (catOptions) {
                    catChannel = false;
                    cmbChannel.removeAllItems();
                    cmbChannel.addItem(" ");
                    if (chkActiveChannel.isSelected()) {
                        query = "SELECT DISTINCT c.ChannelID "
                                + "FROM configuration c, hardware h "
                                + "WHERE h.HwNo = c.HwNo "
                                + "AND c.AvMinMax = '" + module + "'\n"
                                + "AND h.HwNo = '" + machineID + "' AND c.Active = 1";
                    } else {
                        query = "SELECT DISTINCT c.ChannelID "
                                + "FROM configuration c, hardware h "
                                + "WHERE h.HwNo = c.HwNo "
                                + "AND c.AvMinMax = '" + module + "'\n"
                                + "AND h.HwNo = '" + machineID + "'";
                    }
                    try (Statement stmt = ConnectDB.con.createStatement()) {
                        ConnectDB.res = stmt.executeQuery(query);
                        while (ConnectDB.res.next()) {
                            cmbChannel.addItem(ConnectDB.res.getString(1));
                        }
                        if (!"".equals(channelTitle)) {
                            cmbChannel.setSelectedItem(channelTitle);
                        }
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
                    catChannel = true;
                }
            } else {
                catChannel = false;
                lblMessage.setText("");
                panProductionRate.setVisible(false);
                chkPerShift.setEnabled(true);
                chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
                cmbChannel.removeAllItems();
                cmbChannel.addItem(" ");
                clearLabelActive();
//                cmbMachineTitleItemStateChanged(evt);
                catChannel = true;
                if (tbpPanDetails.getTabCount() == 0) {
                    btnShiftTable.setVisible(true);
                    chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
                    chkPerShift.setVisible(true);
                    panShiftTotals.setVisible(true);
                    panShiftTime.setVisible(true);
                    chkPerShiftItemStateChanged(evt);
                }
            }
        }
        catTabbedChange = true;
    }//GEN-LAST:event_cmbOptionsItemStateChanged

    private void btnAddModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddModeActionPerformed
        try {
            if (!cmbOptions.getSelectedItem().equals("") && cmbOptions.getSelectedIndex() > 0) {
                optionsIndex = cmbOptions.getSelectedIndex();
            }
            catOptions = false;
            new AddModule(_parent, true).setVisible(true);
            if (optionsIndex != -1) {
                cmbOptions.setSelectedIndex(optionsIndex);
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnAddModeActionPerformed

    private void radPerHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPerHourActionPerformed
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, true);
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, false);
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_radPerHourActionPerformed

    private void radPerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPerMinActionPerformed
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true);
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false);
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_radPerMinActionPerformed

    private void chkPerShiftItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPerShiftItemStateChanged
        if (chkPerShift.isSelected()) {
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, chkPerShift.isSelected());
            jScrollPane1.getHorizontalScrollBar().setEnabled(true);
            jScrollPane1.getVerticalScrollBar().setEnabled(true);
            jScrollPane1.getViewport().getView().setEnabled(true);
            saveChanges();
        } else {
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, chkPerShift.isSelected());
            jScrollPane1.getHorizontalScrollBar().setEnabled(false);
            jScrollPane1.getVerticalScrollBar().setEnabled(false);
            jScrollPane1.getViewport().getView().setEnabled(false);
        }
    }//GEN-LAST:event_chkPerShiftItemStateChanged

    private void cmbPFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPFromActionPerformed
        cmbPToActionPerformed(evt);
    }//GEN-LAST:event_cmbPFromActionPerformed

    private void cmbPToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPToActionPerformed
        if (cmbPFrom.getDate() != null && cmbPTo.getDate() != null) {
            if (saved) {
                if (cmbPFrom.getDate().getTime() > cmbPTo.getDate().getTime()) {
                    if (skipFirstMessage) {
                        JOptionPane.showMessageDialog(this, "End date of production can't come "
                                + "before the begining date...", "Dates", JOptionPane.WARNING_MESSAGE);
                        skipFirstMessage = false;
                    }
                }
            }
        }
        saveChanges();
    }//GEN-LAST:event_cmbPToActionPerformed

    private void chkFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFromItemStateChanged
        if (chkFrom.isSelected()) {
            cmbPFrom.setEnabled(true);
            Calendar working = ((Calendar) Calendar.getInstance().clone());
            working.add(Calendar.DAY_OF_YEAR, -1);
            cmbPFrom.setDate(working.getTime());
        } else {
            cmbPFrom.setEnabled(false);
        }
    }//GEN-LAST:event_chkFromItemStateChanged

    private void chkToItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkToItemStateChanged
        if (chkTo.isSelected()) {
            cmbPTo.setEnabled(true);
            cmbPTo.setDate(Calendar.getInstance().getTime());
        } else {
            cmbPTo.setEnabled(false);
        }
    }//GEN-LAST:event_chkToItemStateChanged

    private void chkActiveChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActiveChannelActionPerformed
        clearTabbedPane = false;
        cmbMachineTitleItemStateChanged(null);
    }//GEN-LAST:event_chkActiveChannelActionPerformed

    private void btnExportExcelCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelCsvActionPerformed
        new Thread(new Runnable() {

            @Override
            public void run() {
                File fichier;
                JFileChooser jfc = new JFileChooser(ConnectDB.fsv.getRoots()[0]);
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Excel Documents (*.xls)", "xls"));
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Csv Documents (*.csv)", "csv"));
                try {
                    fichier = new File(ConnectDB.fsv.getRoots()[0] + File.separator + "data_"
                            + ConnectDB.correctBarreFileName(ConnectDB.SDATEFORMATHOUR.format(Calendar.getInstance().getTime())));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(_parent, jfc.getSelectedFile().getName()
                            + "\n The file name is not valid.", "Export", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                short fileType = 0;//0==CSV and 1==Excel
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.setSelectedFile(fichier);
                int result = jfc.showSaveDialog(_parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (!jfc.getSelectedFile().exists()) {
                        try {
                            if (jfc.getFileFilter().getDescription().equalsIgnoreCase("Excel Documents (*.xls)")) {
                                try {
                                    if (!jfc.getSelectedFile().getAbsolutePath().contains(".")) {
                                        createExcel(new File(jfc.getSelectedFile().getAbsolutePath() + ".xls"));
                                    } else {
                                        createExcel(jfc.getSelectedFile());
                                    }
                                    fileType = 1;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {//Output to Csv
                                ConnectDB.outputToCsv(tableValues, jfc.getSelectedFile());
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(_parent, "The file could not be saved. Choose the "
                                    + "file type (.xls or .csv).", "Export", JOptionPane.ERROR_MESSAGE);
                        }
                        if (JOptionPane.showConfirmDialog(_parent, "The file was saved sucessfully. "
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
                                JOptionPane.showMessageDialog(_parent, "The file could not be opened. "
                                        + "Check if damaged or retry.", "Export", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(_parent, jfc.getSelectedFile().getName() + " already exists...",
                                "Export", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btnExportExcelCsvActionPerformed

    private void btnCleanTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanTableActionPerformed
        if (viewData != null) {
            viewData.dispose();
        }
        cleanTable();
        setTableNameOfValues("");
    }//GEN-LAST:event_btnCleanTableActionPerformed

    private void btnShiftTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShiftTableMouseClicked
        if (evt.getClickCount() == 2) {
            final Point pos = this.btnShiftTable.getLocationOnScreen();
            new SetTableTime(_parent, true, pos).setVisible(true);
            if (SetTableTime.isWindowClosed()) {
                String[] ses = SetTableTime.getTimeString().split(";");
                tableTime.setValueAt(ses[0], 0, 1);
                tableTime.setValueAt(ses[1], 0, 2);
                tableTime.setValueAt(ses[2], 1, 1);
                tableTime.setValueAt(ses[3], 1, 2);
                tableTime.setValueAt(ses[4], 2, 1);
                tableTime.setValueAt(ses[5], 2, 2);
            }
        } else {
            tableTime.setValueAt(SetTableTime.getTimes()[0], 0, 1);
            tableTime.setValueAt(SetTableTime.getTimes()[1], 0, 2);
            tableTime.setValueAt(SetTableTime.getTimes()[2], 1, 1);
            tableTime.setValueAt(SetTableTime.getTimes()[3], 1, 2);
            tableTime.setValueAt(SetTableTime.getTimes()[4], 2, 1);
            tableTime.setValueAt(SetTableTime.getTimes()[5], 2, 2);
        }
    }//GEN-LAST:event_btnShiftTableMouseClicked

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        ProdStatSetting.showOptionsDialog();
    }//GEN-LAST:event_btnSettingsActionPerformed

    private boolean balloonTip() {
        BalloonTipDemo balloonTip;
        if (cmbMachineTitle.getSelectedIndex() > 0) {
            if (cmbOptions.getSelectedIndex() > 0) {
                if (cmbChannel.getSelectedIndex() > 0) {
                    return true;
                } else {
                    balloonTip = new BalloonTipDemo(cmbChannel, "Please select a channel in the dropdown list "
                            + "component of a machine already specified.");
                    balloonTip.toggleToolTip();
                    return false;
                }
            } else {
                balloonTip = new BalloonTipDemo(cmbOptions, "<html>Please select the plotting options for"
                        + "the machine channel.<br>Click the Add button to specify the options.");
                balloonTip.toggleToolTip();
                return false;
            }
        } else {
            balloonTip = new BalloonTipDemo(cmbMachineTitle, "Please select a machine in the dropdown "
                    + "list component.");
            balloonTip.toggleToolTip();
            return false;
        }
    }

    private void viewDataInTable() {
        cleanTable();
        if (viewData != null) {
            viewData.dispose();
        }
        int nRow = 1;
        int totalProductionSum = 0, t1Sum = 0, t2Sum = 0, t3Sum = 0;
        try {
            if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Cumulative")
                    && tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()).equals("Total Production")) {
                tableType = "parts/hr";
                if (BarChart.isInShifts()) {
                    viewDateTableName = "Time Shifts Total Production";
                    ArrayList text = new ArrayList();//Get the dates and add it with shifts
                    for (Object tableDateWithShift : tableDateWithShifts) {
                        for (int j = 0; j < tableTime.getRowCount(); j++) {
                            text.add(tableDateWithShift + " " + tableTime.getValueAt(j, 1).toString() + "-" + tableTime.getValueAt(j, 2).toString());
                        }
                    }
                    for (int i = 0; i < text.size(); i++) {
                        if (nRow > tableValues.getModel().getRowCount()) {
                            TableModelProductionData refTableValues = (TableModelProductionData) tableValues.getModel();
                            refTableValues.addNewRow();
                        }
                        tableValues.setValueAt(nRow, i, 0);//TableValue #
                        tableValues.setValueAt(text.get(i), i, 1);//TableValue Time
                        nRow++;
                    }
                    int x = 0, //ViewTable row count
                            y = 0; //dates as columns
                    for (int s = 0; s < BarChart.sumHourValues.length;) {
                        if (y < BarChart.sumHourValues[0].length) {
                            String value = BarChart.sumHourValues[s][y];
                            if (value == null) {
                                value = "0";
                            }
                            tableValues.setValueAt(Integer.parseInt(value), x, 2);
                            if (s == 0) {
                                t1Sum += Integer.parseInt(value);
                            } else if (s == 1) {
                                t2Sum += Integer.parseInt(value);
                            } else if (s == 2) {
                                t3Sum += Integer.parseInt(value);
                            }
                            totalProductionSum += Integer.parseInt(value);
                            if (s == BarChart.sumHourValues.length - 1) {
                                s = 0;
                                y++;
                            } else {
                                s++;
                            }
                        } else {
                            break;
                        }
                        x++;//increment viewtable row count
                    }
                } else {//No shifts
                    viewDateTableName = "Total Production";
                    for (int i = 0; i < barchart.getModelPoints().getPointCount(); i++) {
                        StringTokenizer timeValue = new StringTokenizer(BarChart.eachDateH.get(i), " ");
                        if (nRow > tableValues.getModel().getRowCount()) {
                            TableModelProductionData refTableValues = (TableModelProductionData) tableValues.getModel();
                            refTableValues.addNewRow();
                        }
                        tableValues.setValueAt(nRow, i, 0);
                        tableValues.setValueAt(timeValue.nextToken() + " "
                                + (Integer.parseInt(timeValue.nextToken()) + 1) + "h:00", i, 1);
                        tableValues.setValueAt(BarChart.getMaxValue().get(i), i, 2);
                        totalProductionSum += BarChart.getMaxValue().get(i);
                        nRow++;
                    }
                }
                setTableNameOfValues("<html><font color=blue><strong>" + viewDateTableName
                        + "</strong></font><html>");
                lblShiftSum1.setText(t1Sum + " parts");
                lblShiftSum2.setText(t2Sum + " parts");
                lblShiftSum3.setText(t3Sum + " parts");
                lblTotalProductionSum.setText(totalProductionSum + " parts");
            } else if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Rate")
                    && tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()).equals("Production Rate")) {
                viewDateTableName = "Production Rate";
                tableType = "rates/hr";
                for (int i = 0; i < LineChart.timeList.size(); i++) {
                    if (nRow > tableValues.getModel().getRowCount()) {
                        TableModelProductionData refTableValues = (TableModelProductionData) tableValues.getModel();
                        refTableValues.addNewRow();
                    }
                    tableValues.setValueAt(nRow, i, 0);
                    tableValues.setValueAt(LineChart.timeList.get(i).
                            substring(0, LineChart.timeList.get(i).indexOf('.')), i, 1);
                    tableValues.setValueAt(Math.round(Float.valueOf(LineChart.alValues.get(i))), i, 2);
                    nRow++;
                }
                setTableNameOfValues("<html><font color=blue><strong>" + viewDateTableName
                        + "</strong></font><html>");
                lblShiftSum1.setText("");
                lblShiftSum2.setText("");
                lblShiftSum3.setText("");
                lblTotalProductionSum.setText("");
            } else if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Average")
                    && tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()).equals("Machine Run (ON/OFF)")) {
                viewDateTableName = "Machine Run";
                for (int i = 0; i < MachineRun.alTime.size(); i++) {
                    if (nRow > tableValues.getModel().getRowCount()) {
                        TableModelProductionData refTableValues = (TableModelProductionData) tableValues.getModel();
                        refTableValues.addNewRow();
                    }
                    tableValues.setValueAt(nRow, i, 0);
                    tableValues.setValueAt(MachineRun.alTime.get(i).
                            substring(0, MachineRun.alTime.get(i).indexOf('.')), i, 1);
                    tableValues.setValueAt(MachineRun.alValues.get(i), i, 2);
                    nRow++;
                }
                setTableNameOfValues("<html><font color=blue><strong>" + viewDateTableName
                        + "</strong></font><html>");
                lblShiftSum1.setText("");
                lblShiftSum2.setText("");
                lblShiftSum3.setText("");
                lblTotalProductionSum.setText("");
            } else {
                JOptionPane.showMessageDialog(panChart, "<html>Please make sure the current option is "
                        + "selected and that its corresponding chart is shown.", "View Data",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            tableValues.getColumnModel().getColumn(0).setMinWidth(35);
            tableValues.getColumnModel().getColumn(0).setMaxWidth(35);
            tableValues.getColumnModel().getColumn(0).setResizable(false);
        } catch (NumberFormatException | HeadlessException ex) {
//            ex.printStackTrace();
        }
    }

    private void setTableNameOfValues(String name) {
        try {
            panTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                    new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                        UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                    name + " Table [" + tableValues.getModel().getRowCount() + "]", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                    BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        } catch (Exception e) {
            panTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                    new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                        UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                    name + " Table [0]", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                    BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        }
    }

    private void createTabbedPanel(JPanel jPanel, String panelName) {
        int i = 0;
        if (tbpPanDetails.getTabCount() == 0) {
            i = 0;
            tbpPanDetails.insertTab(panelName, null, jPanel, channelTitle + " " + panelName, i);
        } else {
            boolean find = false;
            for (; i < tbpPanDetails.getTabCount(); i++) {
                if (tbpPanDetails.getTitleAt(i).equals(panelName)) {
                    find = true;
                    jPanel.removeAll();
                    jPanel.repaint();
                    switch (panelName) {
                        case "Total Production"://Total Production
                            if (barchart.getChart() != null) {
                                JPanel panel = new JPanel();
                                Legend legend = new Legend(barchart.getChart(), 0);
                                panel.add(legend);
                                alTotalProd.get(0).add(panel, BorderLayout.SOUTH);
                                alTotalProd.get(0).add(barchart.getChart());
                            }
                            break;
                        case "Production Rate"://Production rate
                            if (lineChart.getChart() != null) {
                                alProdRate.get(0).add(lineChart.getChart());
                            }
                            break;
                        default:
                            break;
                    }
                    tbpPanDetails.setSelectedIndex(i);
                    tbpPanDetails.getSelectedComponent().revalidate();
                    break;
                }
//                continue;
            }
            if (!find) {
                tbpPanDetails.insertTab(panelName, null, jPanel, channelTitle + " " + panelName, i);
                tbpPanDetails.setSelectedIndex(i);
            }
        }
        viewDataInTable();
    }

    private void createExcel(File file) {
        try {
            String feuille = viewDateTableName;
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook wbb = Workbook.createWorkbook(file);
            WritableSheet sheet = wbb.createSheet(feuille, 0);
            Label label;
            int p;
            //get the table headings
            for (int gh = 0; gh < tableValues.getColumnCount(); gh++) {
                p = gh;
                if (p < tableValues.getColumnCount()) {
                    label = new Label(gh, 0, tableValues.getColumnName(p));
                    if (p == tableValues.getColumnCount() - 1) {//the value column
                        label = new Label(gh, 0, tableValues.getColumnName(p));
                    }
                    sheet.addCell(label);
                    p++;
                }
            }
            //get the table datas
            int a = 0;
            for (int i = 0; i < tableValues.getColumnCount(); i++) {
                int b = 0;
                for (int j = 0; j < tableValues.getRowCount(); j++) {
                    if (a < tableValues.getColumnCount()) {
                        label = new Label(i, j + 1, tableValues.getValueAt(b, a).toString());
                        sheet.addCell(label);
                        b++;
                    }
                }
                a++;
            }
            wbb.write();
            wbb.close();
        } catch (IOException | WriteException ex) {
            ex.printStackTrace();
        }
    }

    public static void getComponentDates() throws SQLException {
        if (cmbPFrom.getDate() != null && cmbPFrom.isEnabled()) {
            dt_startP = cmbPFrom.getDate();
        } else {
            try (Statement stmt = ConnectDB.con.createStatement()) {
                ConnectDB.res = stmt.executeQuery("SELECT LogTime FROM datalog "
                        + "ORDER BY LogTime LIMIT 1");
                while (ConnectDB.res.next()) {
                    dt_startP = ConnectDB.res.getDate("LogTime");
                }
            }
        }
        if (cmbPTo.getDate() != null && cmbPTo.isEnabled()) {
            dt_endP = cmbPTo.getDate();
        } else {
            try (Statement stmt = ConnectDB.con.createStatement()) {
                ConnectDB.res = stmt.executeQuery("SELECT LogTime FROM datalog "
                        + "ORDER BY LogTime LIMIT 1");
                while (ConnectDB.res.next()) {
                    dt_endP = ConnectDB.res.getDate("LogTime");
                }
            }
        }
    }

    private void autoFill() throws SQLException {
        catMachine = false;
        cmbMachineTitle.removeAllItems();
        cmbMachineTitle.addItem(" ");
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Machine "
                + "FROM hardware WHERE HwNo > ?")) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                cmbMachineTitle.addItem(ConnectDB.res.getString("Machine"));
            }
        }
        catMachine = true;
    }

    private void saveChanges() {
        if (saved) {
            if (tableTime.isEditing()) {
                tableTime.getCellEditor().stopCellEditing();
            }
            propertiesToSave = getPropertiesToSave();
        }
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.PRODPANESET, propertiesToSave);
    }

    private static String getPropertiesToSave() {
        String properties = "";

        if (cmbPFrom.getDate() != null) {
            properties += cmbPFrom.getDate().getTime() + "\r\n";
        } else {
            properties += null + "\r\n";
        }
        if (cmbPTo.getDate() != null) {
            properties += cmbPTo.getDate().getTime() + "\r\n";
        } else {
            properties += null + "\r\n";
        }

        for (int i = 0; i < tableTime.getRowCount(); i++) {
            for (int j = 1; j < tableTime.getColumnCount(); j++) {
                properties += tableTime.getValueAt(i, j) + "\t";
            }
            properties += "\r\n";
        }
        return properties;
    }

    private void setPropertiesTimeSaved(String values) {
        boolean error = false;
        try {
            propertiesToSave = values;
            saved = false;
            String[] rowValues = values.split("\r\n");

            if (!"null".equals(rowValues[0])) {
                Calendar working = ((Calendar) Calendar.getInstance().clone());
                working.add(Calendar.DAY_OF_YEAR, -1);
                cmbPFrom.setDate(working.getTime());
            } else {
                cmbPFrom.setDate(null);
            }
            if (!"null".equals(rowValues[1])) {
                cmbPTo.setDate(Calendar.getInstance().getTime());
            } else {
                cmbPTo.setDate(null);
            }
            int k = 2;
            while (k < rowValues.length) {
                String[] columnTrainee = rowValues[k].split("\t");
                for (int j = 0; j < columnTrainee.length; j++) {
                    tableTime.setValueAt(columnTrainee[j], k - 2, j + 1);
                }
                k++;
            }
            saved = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            error = true;
            saved = true;
            ConnectDB.pref.remove(SettingKeyFactory.DefaultProperties.PRODPANESET);
        } finally {
            saved = true;
            if (error) {
                setTableTime(3);
            }
            setSettings();
        }
    }

    public static void setSettings() {
        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
        radPerMin.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true));
        radPerHour.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false));
    }

    private void setTab(String tabName) {
        int i = 0;
        for (; i < tbpPanDetails.getTabCount(); i++) {
            if (tbpPanDetails.getTitleAt(i).equals(tabName)) {
                tbpPanDetails.setSelectedIndex(i);
                tbpPanDetails.getSelectedComponent().revalidate();
                break;
            }
        }
    }

    private void fillProductionModuleOptions() throws SQLException {
        catOptions = false;
        String[] rowValues = new AddModule(null, false).getSavedProperties().split("\r\n");
        cmbOptions.removeAllItems();
        cmbOptions.addItem(" ");
        for (String string : rowValues) {
            if (!string.equals("\t\t")) {
                cmbOptions.addItem(string.split("\t")[0]);
            }
        }
        catOptions = true;
    }

    private String getModuleOption(String options) {
        String opString = "";
        try {
            String[] rowValues = new AddModule(null, false).getSavedProperties().split("\r\n");
            for (String string : rowValues) {
                if (string.split("\t")[0].equals(options)) {
                    opString = string.split("\t")[1];
                    break;
                }
            }
        } catch (Exception e) {
        }
        return opString;
    }

    private void clearLabelActive() {
        lblActive.setText("");
        lblActive.setIcon(null);
        lblActive.setToolTipText("");
        lblMessage.setText("");
    }

    private void setTableTime(int numRow) {
        modelTime = new TableModelShiftTime(numRow);
        tableTime = new SortableTable(modelTime);
        tableTime.getTableHeader().setReorderingAllowed(false);
        tableTime.setFocusable(false);
        tableTime.setSortable(false);
        tableTime.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        tableTime.getTableHeader().setBackground(Color.BLUE);
        tableTime.setColumnAutoResizable(true);
        tableTime.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        tableTime.getColumnModel().getColumn(0).setMinWidth(35);
        tableTime.getColumnModel().getColumn(0).setMaxWidth(35);
        tableTime.getColumnModel().getColumn(0).setResizable(false);
        tableTime.setRowSelectionAllowed(true);
        tableTime.setColumnSelectionAllowed(true);
        tableTime.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableTime.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(tableTime);
    }

    private void setTableValues() {
        modelData = new TableModelProductionData();
        tableValues = new SortableTable(modelData);
        tableValues.setTableStyleProvider(new RowStripeTableStyleProvider(ConnectDB.colors3()));
        tableValues.getTableHeader().setReorderingAllowed(false);
        tableValues.setFocusable(false);
        tableValues.setSortable(false);
        tableValues.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        tableValues.getTableHeader().setBackground(Color.BLUE);
        tableValues.setColumnAutoResizable(true);
        tableValues.getColumnModel().getColumn(0).setMinWidth(35);
        tableValues.getColumnModel().getColumn(0).setMaxWidth(35);
        tableValues.getColumnModel().getColumn(0).setResizable(false);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane2.setViewportView(tableValues);
    }

    private void cleanTable() {
        modelData = new TableModelProductionData();
        tableValues.setModel(modelData);
        tableValues.getColumnModel().getColumn(0).setMinWidth(35);
        tableValues.getColumnModel().getColumn(0).setMaxWidth(35);
        tableValues.getColumnModel().getColumn(0).setResizable(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnAddMode;
    private com.jidesoft.swing.JideButton btnCleanTable;
    private javax.swing.JButton btnCopyToClipboard;
    private javax.swing.JButton btnEmail;
    private com.jidesoft.swing.JideButton btnExportExcelCsv;
    public static javax.swing.JButton btnMessage;
    private com.jidesoft.swing.JideButton btnPlotChart;
    private javax.swing.JButton btnPrintChart;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSettings;
    private com.jidesoft.swing.JideButton btnShiftTable;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnViewData;
    private javax.swing.JButton btnViewHistory;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkActiveChannel;
    public static javax.swing.JCheckBox chkFrom;
    public static javax.swing.JCheckBox chkPerShift;
    public static javax.swing.JCheckBox chkTo;
    public static org.jdesktop.swingx.JXComboBox cmbChannel;
    public static org.jdesktop.swingx.JXComboBox cmbMachineTitle;
    public static org.jdesktop.swingx.JXComboBox cmbOptions;
    public static com.jidesoft.combobox.DateComboBox cmbPFrom;
    public static com.jidesoft.combobox.DateComboBox cmbPTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JLabel lblActive;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblShiftSum1;
    private javax.swing.JLabel lblShiftSum2;
    private javax.swing.JLabel lblShiftSum3;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblTotalProductionSum;
    private javax.swing.JPanel panPerShiftTotals;
    private javax.swing.JPanel panProductionRate;
    private javax.swing.JPanel panShiftTime;
    private javax.swing.JPanel panShiftTotals;
    private javax.swing.JPanel panTable;
    private static javax.swing.JRadioButton radPerHour;
    private static javax.swing.JRadioButton radPerMin;
    private com.jidesoft.swing.JideTabbedPane tbpPanDetails;
    // End of variables declaration//GEN-END:variables
    private int optionsIndex = -1, machineID = -1;
    private boolean skipFirstMessage = true, //variables for the dates
            clearTabbedPane, catMachine, catChannel, catTabbedChange;
    private static String channelTitle, viewDateTableName = "", tableType = "";
    private static boolean catOptions,//check if all the machineTitle title are loaded
            saved = true;
    public static List tableDateWithShifts;
    public static SortableTable tableTime;
    public static Date dt_startP, dt_endP;
    private static String machineTitle = "", chartTitle, propertiesToSave = "";
    private TableModelProductionData modelData;
    private TableModelShiftTime modelTime;
    private SortableTable tableValues;
    private final ArrayList<ProductionRate> alProdRate = new ArrayList<>();
    private final ArrayList<TotalProduction> alTotalProd = new ArrayList<>();
    private final ArrayList<MachineRun> alMachineRun = new ArrayList<>();
    public ViewData viewData;
    JFrame _parent;
    String rateTab, runTab, totTab;
    JPanel panChart;
    private LineChart lineChart;
    private BarChart barchart;
//    File repTemp = null;
    MachineRun machineRun;
    ViewHistory viewHistory;
    ReportOptions reportOptions;
    ProductionRate productionRate;
    TotalProduction totalProduction;
}
