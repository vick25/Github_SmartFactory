package productionPanel;

import balloonTip.BalloonTipDemo;
import chartTypes.BarChart;
import chartTypes.LineChart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.tree.TreeUtils;
import eventsPanel.EventsStatistic;
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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import mainFrame.MainFrame;
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
import smartfactoryV2.ExtensionFilter;
import smartfactoryV2.Queries;
import tableModel.TableModelProductionData;
import tableModel.TableModelShiftTime;
import target.TargetInsert;
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

    public static SortableTable getTableOfTime() {
        return tableOfTime;
    }

    public ProductionPane(JFrame parent) throws SQLException {
        //        sclient = new Socket(ConnectDB.serverIP, ConnectDB.PORTMAINSERVER);
        ConnectDB.getConnectionInstance();
        initComponents();
        _parent = parent;
        addModule = new AddModule(_parent, true);
        panProductionRate.setVisible(false);
        panShiftTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH), "Hours", TitledBorder.CENTER,
                TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        this.createTableValues();//table for the chartPanel datas
        this.setTableNameOfValues(viewDateTableName);

//        _listener = new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                if (e.getSource() instanceof TableModel) {
//                    int count = ((TableModel) e.getSource()).getRowCount();
//                    System.out.println(count + " out of " + modelData.getRowCount() + " songs");
//                }
//            }
//        };
//        tableLogData.getModel().addTableModelListener(_listener);
        AutoCompleteDecorator.decorate(cmbMachineTitle);
        this.autoFill();
        this.addModule.fillProductionModule();
        Timer time = new Timer(150, new ActionListener() {

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
                if (tableLogData.getRowCount() <= 0) {
                    btnExportExcelCsv.setEnabled(false);
                } else {
                    btnExportExcelCsv.setEnabled(true);
                }
                if (!cmbMachineTitle.getSelectedItem().toString().isEmpty() && cmbMachineTitle.getSelectedIndex() > 0) {
                    btnViewEvents.setEnabled(true);
                } else {
                    btnViewEvents.setEnabled(false);
                }
            }
        });
//        t.setRepeats(true);
//        t.setCoalesce(true);
//        t.setInitialDelay(0);
        time.start();
        tbpPanDetails.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                byte index = (byte) ((JTabbedPane) e.getSource()).getSelectedIndex();
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
                    } catch (ParseException ex) {
                        Logger.getLogger(ProductionPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        tableLogData.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2 && tableLogData.getModel().getRowCount() > 0) {
                        if (viewData != null) {
                            viewData.dispose();
                        }
                        viewData = new ViewData(_parent, false, modelData,
                                new StringBuilder(viewDateTableName).append(" Table").toString());
                        viewData.setVisible(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(_parent, "No values to display...");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (tableLogData.getModel().getRowCount() > 0) {
                    tableLogData.setToolTipText("Double-click on the table values for a much broader viewing.");
                }
            }
        });
        jPanel2.requestFocus();
        lblActive.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setTableTime((int) spShiftTableRow.getValue());//tables for the hour shifts
        this.setPropertiesTimeSaved(ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.PRODPANESET,
                getPropertiesToSave()));
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
        btnCleanUI = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnViewEvents = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnPrintChart = new javax.swing.JButton();
        btnCopyToClipboard = new javax.swing.JButton();
        btnShowTargets = new javax.swing.JButton();
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
        shiftPane = new javax.swing.JScrollPane();
        chkFrom = new javax.swing.JCheckBox();
        chkTo = new javax.swing.JCheckBox();
        btnShiftTable = new com.jidesoft.swing.JideButton();
        spShiftTableRow = new com.alee.laf.spinner.WebSpinner();
        chkActiveChannel = new javax.swing.JCheckBox();
        lblSpace = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        tbpPanDetails = new com.jidesoft.swing.JideTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        panTable = new javax.swing.JPanel();
        logDataPane = new javax.swing.JScrollPane();
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
        btnShowEvents = new com.jidesoft.swing.JideButton();

        setBackground(new java.awt.Color(102, 102, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jToolBar3.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9), new java.awt.Color(0, 0, 204))); // NOI18N
        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        btnCleanUI.setBackground(new java.awt.Color(255, 255, 255));
        btnCleanUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/clear.png"))); // NOI18N
        btnCleanUI.setText("Clean");
        btnCleanUI.setToolTipText("Clean interface to orginal state");
        btnCleanUI.setFocusable(false);
        btnCleanUI.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCleanUI.setOpaque(false);
        btnCleanUI.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCleanUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanUIActionPerformed(evt);
            }
        });
        jToolBar3.add(btnCleanUI);

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

        btnViewEvents.setBackground(new java.awt.Color(255, 255, 255));
        btnViewEvents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/event_time_2.png"))); // NOI18N
        btnViewEvents.setText("Events");
        btnViewEvents.setToolTipText("Show the events interface of the current machine with the periods");
        btnViewEvents.setFocusable(false);
        btnViewEvents.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewEvents.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewEventsActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewEvents);

        btnSettings.setBackground(new java.awt.Color(255, 255, 255));
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/settings.png"))); // NOI18N
        btnSettings.setText("Settings");
        btnSettings.setToolTipText("Production settings");
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
        btnPrintChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/printer_1.png"))); // NOI18N
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
        btnCopyToClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/clipperboard.png"))); // NOI18N
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

        btnShowTargets.setBackground(new java.awt.Color(255, 255, 255));
        btnShowTargets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/target_1.png"))); // NOI18N
        btnShowTargets.setText("Targets");
        btnShowTargets.setFocusable(false);
        btnShowTargets.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowTargets.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowTargets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowTargetsActionPerformed(evt);
            }
        });
        jToolBar2.add(btnShowTargets);

        btnViewData.setBackground(new java.awt.Color(255, 255, 255));
        btnViewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/view_icon.png"))); // NOI18N
        btnViewData.setText("Chart data table");
        btnViewData.setToolTipText("View the current chart values in a table");
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
        btnViewHistory.setText("Database history");
        btnViewHistory.setToolTipText("Show raw database records");
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

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
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
        jSplitPane1.setDividerLocation(340);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setOneTouchExpandable(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Channel:");

        lblActive.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnPlotChart.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnPlotChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/frame_chart.png"))); // NOI18N
        btnPlotChart.setMnemonic(KeyEvent.VK_P);
        btnPlotChart.setText("Plot Chart");
        btnPlotChart.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPlotChart.setOpaque(true);
        btnPlotChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlotChartActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        radPerHour.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        radPerMin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                .addGap(0, 148, Short.MAX_VALUE))
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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActive, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(btnPlotChart, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panProductionRate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)))
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
        chkPerShift.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        cmbPFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPFrom.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
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
        cmbPTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPTo.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        cmbPTo.setRequestFocusEnabled(false);
        cmbPTo.setTimeDisplayed(true);
        cmbPTo.setTimeFormat("HH:mm:ss");
        cmbPTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPToActionPerformed(evt);
            }
        });

        panShiftTime.setBackground(new java.awt.Color(255, 255, 255));

        shiftPane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panShiftTimeLayout = new javax.swing.GroupLayout(panShiftTime);
        panShiftTime.setLayout(panShiftTimeLayout);
        panShiftTimeLayout.setHorizontalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(shiftPane, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panShiftTimeLayout.setVerticalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addComponent(shiftPane, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );

        chkFrom.setBackground(new java.awt.Color(255, 255, 255));
        chkFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkFrom.setSelected(true);
        chkFrom.setText("From:");
        chkFrom.setFocusable(false);
        chkFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFromItemStateChanged(evt);
            }
        });

        chkTo.setBackground(new java.awt.Color(255, 255, 255));
        chkTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        spShiftTableRow.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        spShiftTableRow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spShiftTableRowStateChanged(evt);
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
                                .addComponent(spShiftTableRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(spShiftTableRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPerShift))
                .addGap(6, 6, 6)
                .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnShiftTable, chkPerShift, spShiftTableRow});

        chkActiveChannel.setBackground(new java.awt.Color(255, 255, 255));
        chkActiveChannel.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        chkActiveChannel.setForeground(new java.awt.Color(255, 255, 0));
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSpace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMachineTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActiveChannel)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel4, jPanel6, jSeparator1, lblSpace});

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
        tbpPanDetails.setShowCloseButton(true);
        tbpPanDetails.setShowCloseButtonOnMouseOver(true);
        tbpPanDetails.setShowCloseButtonOnSelectedTab(true);
        tbpPanDetails.setShowCloseButtonOnTab(true);
        tbpPanDetails.setShowTabButtons(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
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
            .addComponent(logDataPane)
        );
        panTableLayout.setVerticalGroup(
            panTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logDataPane)
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
                .addGap(28, 100, Short.MAX_VALUE))
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

        btnShowEvents.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnShowEvents.setForeground(new java.awt.Color(51, 51, 255));
        btnShowEvents.setText("Events >>");
        btnShowEvents.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnShowEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowEventsActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
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
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnCleanTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExportExcelCsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
        );

        jSplitPane2.setRightComponent(jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
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

    private void btnCleanUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanUIActionPerformed
        try {
            machineTitle = "";
            catTabbedChange = false;
            tbpPanDetails.removeAll();
            lblTotalProductionSum.setText("");
            lblShiftSum1.setText("");
            lblShiftSum2.setText("");
            lblShiftSum3.setText("");
            btnMessage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/icons/light_on.png")));
            btnMessage.setToolTipText("0 flag(s) raised.");
            cmbMachineTitle.setSelectedIndex(0);
            cmbOptions.setSelectedIndex(0);
            btnCleanTableActionPerformed(evt);
            if (barChart != null) {
                if (barChart.getDateWithShiftsList() != null) {
                    barChart.getDateWithShiftsList().clear();
                    barChart.setDateWithShiftsList(null);
                }
            }
            BarChart.getMessageFlag().clear();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCleanUIActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        barChart.makeItBlink.stop();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPrintChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintChartActionPerformed
        final ReportOptions reportOptions = new ReportOptions(_parent, true);
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
                    ConnectDB.setMainDir(new File(ConnectDB.DEFAULT_DIRECTORY + File.separator + "SmartFactory Data"));
                    if (!ConnectDB.getMainDir().exists()) {
                        ConnectDB.getMainDir().mkdirs();
                    }

                    String dirIcon = new StringBuilder(ConnectDB.getMainDir().getAbsolutePath()).
                            append(File.separator).append("chart.png").toString();
                    JPanel panChart = (JPanel) tbpPanDetails.getSelectedComponent();
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
                    hm.put("date1", ConnectDB.SDATE_FORMAT_HOUR.format(dt_startP));
                    hm.put("date2", ConnectDB.SDATE_FORMAT_HOUR.format(dt_endP));
                    hm.put("valueTitle", tableType);//represents parts/hr or rate/hr
                    hm.put("SUBREPORT_DIR", getClass().getResourceAsStream("/jasper/tableExample.jasper"));
                    if (reportOptions.isAddTable()) {
                        if (tableLogData.getModel().getRowCount() > 0) {
                            hm.put("tableName", new StringBuilder(viewDateTableName).append(" Table").toString());
                            hm.put("MyTableModel", tableLogData.getModel());
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
            JPanel panChart = (JPanel) tbpPanDetails.getSelectedComponent();
            ChartUtils.copyImageToClipboard(panChart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCopyToClipboardActionPerformed

    private void btnViewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDataActionPerformed
        try {
            viewDataInTable();
        } catch (ParseException ex) {
            Logger.getLogger(ProductionPane.class.getName()).log(Level.SEVERE, null, ex);
        }
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
//            if (BarChart.getMessageFlag() != null) {
//                BarChart.getMessageFlag().clear();
//            }
            BarChart.setFlagDialog(new Flag(_parent, false, BarChart.getMessageFlag()));
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
            BarChart.getMessageFlag().clear();
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
            if (!cmbMachineTitle.getSelectedItem().toString().isEmpty() && cmbMachineTitle.getSelectedIndex() > 0) {
                StringBuilder strBuild;
                cmbChannel.removeAllItems();
                cmbChannel.addItem(" ");
                if (chkActiveChannel.isSelected()) {
                    strBuild = new StringBuilder().append("SELECT DISTINCT c.ChannelID \n"
                            + "FROM configuration c, hardware h \n"
                            + "WHERE h.HwNo = c.HwNo \nAND h.HwNo =? \nAND c.Active = 1");
                } else {
                    strBuild = new StringBuilder().append("SELECT DISTINCT c.ChannelID \n"
                            + "FROM configuration c, hardware h \n"
                            + "WHERE h.HwNo = c.HwNo \nAND h.HwNo =?");
                }
                if (!cmbOptions.getSelectedItem().toString().isEmpty() && cmbOptions.getSelectedIndex() > 0) {
                    String module = getModuleOption(cmbOptions.getSelectedItem().toString());
                    strBuild.append(" \nAND c.AvMinMax = '").append(module).append("'");
                }
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(strBuild.toString())) {
                    ps.setInt(1, machineID);
                    ConnectDB.res = ps.executeQuery();
                    boolean channelFound = false;
                    while (ConnectDB.res.next()) {
                        channelFound = true;
                        cmbChannel.addItem(ConnectDB.res.getString(1));
                    }
                    if (channelFound) {
                        if (!"".equals(cmbOptions.getSelectedItem()) && cmbOptions.getSelectedIndex() > 0) {
                            catChannel = true;
                            cmbChannel.setSelectedIndex(1);
                            catChannel = false;
                        }
                    } else {
                        lblMessage.setText("No channel found for the machine option");
                    }
                } catch (Exception ex) {
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
            if (!cmbChannel.getSelectedItem().toString().isEmpty() && cmbChannel.getSelectedIndex() > 0) {
                channelTitle = cmbChannel.getSelectedItem().toString();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT Active FROM `configuration` \n"
                        + "WHERE ChannelID =? ORDER BY ACTIVE")) {
                    ps.setString(1, channelTitle);
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        if (ConnectDB.res.getInt(1) == 1) {
                            lblActive.setIcon(new ImageIcon(getClass().getResource("/images/icons/tick.png")));
                            lblActive.setText("Active");
                        } else {
                            lblActive.setIcon(new ImageIcon(getClass().getResource("/images/icons/stop.png")));
                            lblActive.setText("Not Active");
                        }
                        lblActive.setToolTipText(new StringBuilder("\"").append(channelTitle).
                                append("\" channel recent activity").toString());
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT LogTime FROM datalog \n"
                        + "WHERE ConfigNo =? \n"
                        + "ORDER BY LogTime DESC \n"
                        + "LIMIT 1;")) {
                    ps.setInt(1, ConnectDB.getConfigNo(channelTitle, machineTitle));
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        lblMessage.setText(new StringBuilder().append("Last data recorded on: ").
                                append(ConnectDB.res.getString(1).substring(0, 19)).toString());
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
                        //Production rate (Rate)
                        catTabbedChange = false;
                        if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Rate")) {
                            if (catChannel) {
                                if (!"".equals(cmbChannel.getSelectedItem())) {
                                    ProductionRate productionRate = null;
                                    String query, unit;
                                    if (radPerHour.isSelected()) {
                                        unit = "parts/hr";
                                        query = Queries.DATALOG_PRODUCTION_HR;
                                    } else {
                                        unit = "parts/min";
                                        query = Queries.DATALOG_PRODUCTION;
                                    }
                                    lineChart = null;
                                    lineChart = new LineChart(ConnectDB.getConfigNo(channelTitle, machineTitle), query,
                                            ConnectDB.firstLetterCapital(unit), machineTitle, dt_startP, dt_endP);

                                    if (lineChart.getChart() != null) {
                                        productionRate = new ProductionRate(lineChart.getChart(), unit,
                                                lineChart.getChartModel(), lineChart);
                                    }
                                    if (!alProdRate.contains(productionRate) && productionRate != null) {
                                        alProdRate.add(productionRate);
                                    } else {
                                        alProdRate.clear();
                                    }
                                    if (lineChart.getChart() != null) {
                                        createTabbedPanel(alProdRate.get(0), "Production Rate");
                                        rateTab = new StringBuilder().append(cmbOptions.getSelectedItem()).
                                                append(";").append(channelTitle).toString();
                                        catTabbedChange = true;
                                    }
                                }
                            }
                        } else //Total production
                        if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Cumulative")) {
                            if (catChannel) {
                                if (!"".equals(cmbChannel.getSelectedItem())) {
                                    TotalProduction totalProduction = null;
                                    barChart = null;
                                    if (chkPerShift.isSelected()) {
                                        //With shifts
                                        if (ConnectDB.checkTableValidity(tableOfTime)) {
                                            barChart = new BarChart(ConnectDB.getConfigNo(channelTitle, machineTitle),
                                                    Queries.DATALOG_PRODUCTION, true, machineTitle, channelTitle, dt_startP, dt_endP);
                                            if (barChart.getChart() != null) {
                                                barChart.getChart().setBorder(new EmptyBorder(5, 5, 5, 20));
                                                JPanel panel = new JPanel();
                                                Legend legend = new Legend(barChart.getChart(), 0);
                                                panel.add(legend);
                                                totalProduction = new TotalProduction(barChart.getChart());
                                                totalProduction.add(panel, BorderLayout.SOUTH);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(_parent, "Please check the table shift "
                                                    + "for valid hours, not the \"00:00\"");
                                            return;
                                        }
                                    } else {
                                        //No time shifts
                                        barChart = new BarChart(ConnectDB.getConfigNo(channelTitle, machineTitle),
                                                Queries.DATALOG_PRODUCTION, false, machineTitle, channelTitle, dt_startP, dt_endP);
                                        if (barChart.getChart() != null) {
                                            totalProduction = new TotalProduction(barChart.getChart());
                                        }
                                    }
                                    if (!alTotalProd.contains(totalProduction) && totalProduction != null) {
                                        alTotalProd.add(totalProduction);
                                    } else {
                                        alTotalProd.clear();
                                    }
                                    if (barChart.getChart() != null) {
                                        createTabbedPanel(alTotalProd.get(0), "Total Production");
                                        totTab = new StringBuilder().append(cmbOptions.getSelectedItem()).
                                                append(";").append(channelTitle).toString();
                                        catTabbedChange = true;
                                    }
                                }

                            }
                        } else if (getModuleOption(cmbOptions.getSelectedItem().toString()).equalsIgnoreCase("Average")) {
                            if (catChannel) {
                                if (!"".equals(cmbChannel.getSelectedItem())) {
                                    machineRunPanel = new MachineRun(ConnectDB.getConfigNo(channelTitle, machineTitle),
                                            Queries.DATALOG_PRODUCTION, machineTitle, dt_startP, dt_endP);
                                    if (machineRunPanel.getChart() != null) {
                                        alMachineRun.add(machineRunPanel);
                                        createTabbedPanel(alMachineRun.get(0), "Machine Run (ON/OFF)");
                                    }
                                    runTab = new StringBuilder().append(cmbOptions.getSelectedItem()).
                                            append(";").append(channelTitle).toString();
                                    catTabbedChange = true;
                                }
                            }
                        }
                    } catch (HeadlessException e) {
                        catTabbedChange = false;
                    } catch (SQLException ex) {
                        catTabbedChange = false;
                        ConnectDB.catchSQLException(ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(ProductionPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!catDatesRunning) {
                        cmbPTo.setEnabled(true);
                    }
                }
            }
        });
        thread.start();
    }//GEN-LAST:event_btnPlotChartActionPerformed

    private void cmbOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOptionsItemStateChanged
        if (catOptions) {
            if (!"".equals(cmbOptions.getSelectedItem()) && cmbOptions.getSelectedIndex() > 0) {
                catTabbedChange = false;
                String module = getModuleOption(cmbOptions.getSelectedItem().toString());
                switch (module) {
                    case "Rate":
                        setTab("Production Rate");
                        panShiftTotals.setVisible(false);
                        panProductionRate.setVisible(true);
                        radPerHour.setEnabled(true);
                        radPerMin.setEnabled(true);
                        chkPerShift.setVisible(false);
                        spShiftTableRow.setVisible(false);
                        btnShiftTable.setVisible(false);
                        panShiftTime.setVisible(false);
                        break;
                    case "Average":
                        setTab("Machine Run (ON/OFF)");
                        panShiftTotals.setVisible(false);
                        chkPerShift.setVisible(false);
                        btnShiftTable.setVisible(false);
                        spShiftTableRow.setVisible(false);
                        panProductionRate.setVisible(false);
                        panShiftTime.setVisible(false);
                        break;
                    case "Cumulative":
                        setTab("Total Production");
                        panShiftTotals.setVisible(true);
                        panProductionRate.setVisible(false);
                        btnShiftTable.setVisible(true);
                        spShiftTableRow.setVisible(true);
                        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON,
                                false));
                        chkPerShift.setVisible(true);
                        panShiftTime.setVisible(true);
                        chkPerShiftItemStateChanged(evt);
                        break;
                }
                if (catOptions) {
                    StringBuilder strBuild;
                    catChannel = false;
                    cmbChannel.removeAllItems();
                    cmbChannel.addItem(" ");
                    if (chkActiveChannel.isSelected()) {
                        strBuild = new StringBuilder().append("SELECT DISTINCT c.ChannelID \n"
                                + "FROM configuration c, hardware h \n"
                                + "WHERE h.HwNo = c.HwNo \nAND c.AvMinMax =? "
                                + "\n AND h.HwNo =? \nAND c.Active = 1");
                    } else {
                        strBuild = new StringBuilder().append("SELECT DISTINCT c.ChannelID \n"
                                + "FROM configuration c, hardware h \n"
                                + "WHERE h.HwNo = c.HwNo \nAND c.AvMinMax =? \nAND h.HwNo =?");
                    }
                    try (PreparedStatement ps = ConnectDB.con.prepareStatement(strBuild.toString())) {
                        ps.setString(1, module);
                        ps.setInt(2, machineID);
                        ConnectDB.res = ps.executeQuery();
                        boolean channelFound = false;
                        while (ConnectDB.res.next()) {
                            channelFound = true;
                            cmbChannel.addItem(ConnectDB.res.getString(1));
                        }
                        if (channelFound) {
                            if (!"".equals(cmbMachineTitle.getSelectedItem()) && cmbMachineTitle.getSelectedIndex() > 0) {
                                catChannel = true;
                                cmbChannel.setSelectedIndex(1);
                                catChannel = false;
                            }
                        } else {
                            if (!"".equals(cmbMachineTitle.getSelectedItem()) && cmbMachineTitle.getSelectedIndex() > 0) {
                                lblMessage.setText("No channel found for the machine option");
                            }
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
                clearLabelActive();
                cmbMachineTitleItemStateChanged(evt);
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
        if (!"".equals(cmbOptions.getSelectedItem()) && cmbOptions.getSelectedIndex() > 0) {
            optionsIndex = cmbOptions.getSelectedIndex();
        }
        catOptions = false;
        addModule.setVisible(true);
        if (optionsIndex != -1) {
            cmbOptions.setSelectedIndex(optionsIndex);
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
            spShiftTableRow.setEnabled(true);
            shiftPane.getHorizontalScrollBar().setEnabled(true);
            shiftPane.getVerticalScrollBar().setEnabled(true);
            shiftPane.getViewport().getView().setEnabled(true);
            saveChanges();
        } else {
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, chkPerShift.isSelected());
            spShiftTableRow.setEnabled(false);
            shiftPane.getHorizontalScrollBar().setEnabled(false);
            shiftPane.getVerticalScrollBar().setEnabled(false);
            shiftPane.getViewport().getView().setEnabled(false);
        }
    }//GEN-LAST:event_chkPerShiftItemStateChanged

    private void cmbPFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPFromActionPerformed
        if (catDates) {
            catDatesRunning = true;
            btnRefreshActionPerformed(evt);
//            catDatesRunning = false;
        }
    }//GEN-LAST:event_cmbPFromActionPerformed

    private void cmbPToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPToActionPerformed
        if (catDates) {
            if (cmbPFrom.getDate() != null && cmbPTo.getDate() != null) {
                if (saved) {
                    if (cmbPFrom.getDate().getTime() > cmbPTo.getDate().getTime()) {
                        if (skipFirstMessage) {
                            JOptionPane.showMessageDialog(this, "\"To date\" of production cannot come "
                                    + "before \"from date\"...", "Dates", JOptionPane.ERROR_MESSAGE);
                            skipFirstMessage = false;
                        }
                        return;
                    }
                }
            }
        }
        saveChanges();
    }//GEN-LAST:event_cmbPToActionPerformed

    private void chkFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFromItemStateChanged
        catDates = false;
        if (chkFrom.isSelected()) {
            cmbPFrom.setEnabled(true);
            Calendar working = ((Calendar) Calendar.getInstance().clone());
            working.add(Calendar.DAY_OF_YEAR, -1);
            cmbPFrom.setDate(working.getTime());
        } else {
            cmbPFrom.setEnabled(false);
        }
        catDates = true;
    }//GEN-LAST:event_chkFromItemStateChanged

    private void chkToItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkToItemStateChanged
        catDates = false;
        if (chkTo.isSelected()) {
            cmbPTo.setEnabled(true);
            cmbPTo.setDate(Calendar.getInstance().getTime());
        } else {
            cmbPTo.setEnabled(false);
        }
        catDates = true;
    }//GEN-LAST:event_chkToItemStateChanged

    private void chkActiveChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActiveChannelActionPerformed
        clearTabbedPane = false;
        cmbMachineTitleItemStateChanged(null);
    }//GEN-LAST:event_chkActiveChannelActionPerformed

    private void btnExportExcelCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelCsvActionPerformed
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
                    JOptionPane.showMessageDialog(_parent, new StringBuilder(jfc.getSelectedFile().getName()).
                            append("\n The file name is not valid.").toString(), "Export", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                byte fileType = 0;//0==CSV and 1==Excel
                jfc.setSelectedFile(fichier);
                if (jfc.showSaveDialog(_parent) == JFileChooser.APPROVE_OPTION) {
                    if (!jfc.getSelectedFile().exists()) {
                        try {
                            if (jfc.getFileFilter().getDescription().equalsIgnoreCase("Excel files")) {
                                if (!jfc.getSelectedFile().getAbsolutePath().contains(".")) {
                                    createExcel(new File(new StringBuilder(jfc.getSelectedFile().getAbsolutePath()).
                                            append(".xls").toString()));
                                } else {
                                    createExcel(jfc.getSelectedFile());
                                }
                                fileType = 1;
                            } else if (jfc.getFileFilter().getDescription().equalsIgnoreCase("CSV files")) {//Output to Csv
                                ConnectDB.outputToCsv(tableLogData, jfc.getSelectedFile());
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(_parent, "The file could not be saved. Please choose a "
                                    + "file type (.xls or .csv).", "Export", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (JOptionPane.showConfirmDialog(_parent, "The file was saved sucessfully. "
                                + "Do you want to open the file?", "Export",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == 0) {
                            File f;
                            if (fileType == 1) {
                                f = new File(new StringBuilder(jfc.getSelectedFile().getAbsolutePath()).append(".xls").toString());
                            } else {
                                f = new File(new StringBuilder(jfc.getSelectedFile().getAbsolutePath()).append(".csv").toString());
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
                        JOptionPane.showMessageDialog(_parent, new StringBuilder(jfc.getSelectedFile().getName()).
                                append(" already exists...").toString(), "Export", JOptionPane.WARNING_MESSAGE);
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
        if ((int) spShiftTableRow.getValue() == 3) {
            final Point pos = this.btnShiftTable.getLocationOnScreen();
            final SetTableTime setTableTime = new SetTableTime(_parent, true, pos);
            if (evt.getClickCount() == 2) {
                setTableTime.setVisible(true);
                if (setTableTime.isWindowClosed()) {
                    String[] ses = setTableTime.getTimeString().split(";");
                    tableOfTime.setValueAt(ses[0], 0, 1);
                    tableOfTime.setValueAt(ses[1], 0, 2);
                    tableOfTime.setValueAt(ses[2], 1, 1);
                    tableOfTime.setValueAt(ses[3], 1, 2);
                    tableOfTime.setValueAt(ses[4], 2, 1);
                    tableOfTime.setValueAt(ses[5], 2, 2);
                }
            } else {
                tableOfTime.setValueAt(setTableTime.getTimes()[0], 0, 1);
                tableOfTime.setValueAt(setTableTime.getTimes()[1], 0, 2);
                tableOfTime.setValueAt(setTableTime.getTimes()[2], 1, 1);
                tableOfTime.setValueAt(setTableTime.getTimes()[3], 1, 2);
                tableOfTime.setValueAt(setTableTime.getTimes()[4], 2, 1);
                tableOfTime.setValueAt(setTableTime.getTimes()[5], 2, 2);
            }
        }
        saveChanges();
    }//GEN-LAST:event_btnShiftTableMouseClicked

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        ProdStatSetting.showOptionsDialog();
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnViewEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewEventsActionPerformed
        DocumentPane _documentPane = MainFrame.getDocumentPane();
        if (!_documentPane.isDocumentOpened("Events")) {
            try {
                final DocumentComponent document = new DocumentComponent(new EventsStatistic(_parent), "Events",
                        "Events", new ImageIcon(this.getClass().getResource("/images/icons/kchart12.png")));
                _documentPane.openDocument(document);
                MainFrame.confirmCloseTab(document);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
        _documentPane.setActiveDocument("Events");
        if (machineTitle != null && !machineTitle.isEmpty()) {
            EventsStatistic.cmbEFrom.setDate(cmbPFrom.getDate());
            EventsStatistic.cmbETo.setDate(cmbPTo.getDate());
            EventsStatistic.cmbMachineTitle.setSelectedItem(machineTitle);
        }
        this.repaint();
    }//GEN-LAST:event_btnViewEventsActionPerformed

    private void btnShowEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowEventsActionPerformed
        if (evt.getActionCommand().equals("<< Chart Data")) {
            btnShowEvents.setText("Events >>");
            logDataPane.setViewportView(tableLogData);
        } else {
            try {
                Set<String> setDescriptionValue = new TreeSet<>(),
                        descriptionSet = new TreeSet<>();
                String q = "SELECT c.Description, e.Value FROM eventlog e, customlist c \n"
                        + "WHERE e.`CustomCode` = c.`Code` \n"
                        + "AND e.`EventTime` >=? AND e.`UntilTime` <=? \n"
                        + "AND e.`HwNo` =? \n"
                        + "ORDER BY e.`EventNo` ASC";
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(q)) {
                    ps.setString(1, ConnectDB.SDATE_FORMAT_HOUR.format(dt_startP));
                    ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(dt_endP));
                    ps.setInt(3, ConnectDB.getIDMachine(machineTitle));
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        descriptionSet.add(resultSet.getString(1));
                        setDescriptionValue.add(new StringBuilder(resultSet.getString(1)).append(";").append(
                                resultSet.getString(2)).toString());
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                DefaultMutableTreeNode m_rootNode = new DefaultMutableTreeNode(machineTitle);
                Vector data = EventsStatistic.getDummyData(setDescriptionValue);//method to create the tree of description and value
                for (String sDesc : descriptionSet) {
                    DefaultMutableTreeNode m_descNode = new DefaultMutableTreeNode(ConnectDB.firstLetterCapital(sDesc));
                    for (Enumeration enumData = data.elements(); enumData.hasMoreElements();) {
                        EventsStatistic.Machine machData = (EventsStatistic.Machine) enumData.nextElement();//machData == Value
                        if (machData.getMachDomain().equals(sDesc)) {//check for a value description
                            m_descNode.add(new DefaultMutableTreeNode(ConnectDB.firstLetterCapital(machData.toString())));//add value to a description node
                        }
                    }
                    m_rootNode.add(m_descNode);//add a description to the machine main node
                }
                final JTree tree = new JTree(m_rootNode);
                TreeUtils.expandAll(tree, true);
                tree.setRootVisible(true);
                tree.setShowsRootHandles(true);
                logDataPane.setViewportView(tree);
                btnShowEvents.setText("<< Chart Data");
//                panTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
//                        new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
//                            UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
//                        new StringBuilder().append(" Events occurred within the period").toString(),
//                        TitledBorder.CENTER, TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
            } catch (NullPointerException e) {
                BalloonTipDemo balloonTip = new BalloonTipDemo(btnPlotChart, "Please click the plot chart button.");
                balloonTip.toggleToolTip();
            }
        }
    }//GEN-LAST:event_btnShowEventsActionPerformed

    private void spShiftTableRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spShiftTableRowStateChanged
        if (timeLoaded) {
            if ((int) spShiftTableRow.getValue() > tableOfTime.getModel().getRowCount()) {
                while ((int) spShiftTableRow.getValue() > tableOfTime.getRowCount()) {
                    modelTimeShift.addNewRow();
                    modelTimeShift.setValueAt(spShiftTableRow.getValue(), (int) spShiftTableRow.getValue() - 1, 0);
                }
            } else if (tableOfTime.getRowCount() > (int) spShiftTableRow.getValue()) {
                int nbTable = tableOfTime.getRowCount();
                while (nbTable > (int) spShiftTableRow.getValue()) {
                    modelTimeShift.removeNewRow();
                    --nbTable;
                }
            }
        }
        saveChanges();
    }//GEN-LAST:event_spShiftTableRowStateChanged

    private void btnShowTargetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowTargetsActionPerformed
        try {
            new TargetInsert(_parent, true).setVisible(true);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnShowTargetsActionPerformed

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

    private void viewDataInTable() throws ParseException {
        cleanTable();
        if (viewData != null) {
            viewData.dispose();
        }
        int nRow = 1, totalProductionSum = 0, t1Sum = 0, t2Sum = 0, t3Sum = 0;
        try {
            if (getModuleOption(String.valueOf(cmbOptions.getSelectedItem())).equalsIgnoreCase("Cumulative")
                    && "Total Production".equals(tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()))) {
                tableType = "parts/hr";
                if (barChart.isInShifts()) {
                    viewDateTableName = "Time Shifts Total Production";
                    ArrayList textLogTime = new ArrayList();//Get the dates and add it with shifts
                    for (Object tableDateWithShift : barChart.getDateWithShiftsList()) {
                        for (int j = 0; j < tableOfTime.getRowCount(); j++) {
                            textLogTime.add(new StringBuilder().append(tableDateWithShift).append(" ").
                                    append(tableOfTime.getValueAt(j, 1)).append("-").
                                    append(tableOfTime.getValueAt(j, 2)).toString());
                        }
                    }
                    for (int i = 0; i < textLogTime.size(); i++) {
                        if (nRow > tableLogData.getModel().getRowCount()) {
                            TableModelProductionData refTableValues = (TableModelProductionData) tableLogData.getModel();
                            refTableValues.addNewRow();
                        }
                        tableLogData.setValueAt(nRow, i, 0);//TableValue #
                        tableLogData.setValueAt(formatter.format(ConnectDB.SDATE_FORMAT_HOUR.
                                parse(ConnectDB.correctToBarreDate(textLogTime.get(i).toString().substring(0, 16)
                                                + ":00"))), i, 1);//Day
                        tableLogData.setValueAt(textLogTime.get(i), i, 2);//TableValue Time
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
                            tableLogData.setValueAt(Integer.parseInt(value), x, 3);//TableValue Data
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
                    for (int i = 0; i < barChart.getModelPoints().getPointCount(); i++) {
                        StringTokenizer timeValue = new StringTokenizer(BarChart.eachDateH.get(i), " ");
                        if (nRow > tableLogData.getModel().getRowCount()) {
                            TableModelProductionData refTableValues = (TableModelProductionData) tableLogData.getModel();
                            refTableValues.addNewRow();
                        }
                        tableLogData.setValueAt(nRow, i, 0);
                        String logTime = new StringBuilder(timeValue.nextToken()).append(" ").
                                append(Integer.parseInt(timeValue.nextToken()) + 1).append("h:00").toString();
                        tableLogData.setValueAt(formatter.format(ConnectDB.SDATE_FORMAT_HOUR.
                                parse(ConnectDB.correctToBarreDate(new StringBuilder(logTime.replaceAll("h", ""))
                                                .append(":00").toString()))), i, 1);//Day
                        tableLogData.setValueAt(logTime, i, 2);
                        tableLogData.setValueAt(BarChart.getMaxValue().get(i), i, 3);
                        totalProductionSum += BarChart.getMaxValue().get(i);
                        nRow++;
                    }
                }
                lblShiftSum1.setText(t1Sum + " parts");
                lblShiftSum2.setText(t2Sum + " parts");
                lblShiftSum3.setText(t3Sum + " parts");
                lblTotalProductionSum.setText(totalProductionSum + " parts");
            } else if (getModuleOption(String.valueOf(cmbOptions.getSelectedItem())).equalsIgnoreCase("Rate")
                    && "Production Rate".equals(tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()))) {
                clearLabelOfSums();
                viewDateTableName = "Production Rate";
                tableType = "rates/hr";
                for (int i = 0; i < lineChart.getLogTimeList().size(); i++) {
                    if (nRow > tableLogData.getModel().getRowCount()) {
                        TableModelProductionData refTableValues = (TableModelProductionData) tableLogData.getModel();
                        refTableValues.addNewRow();
                    }
                    tableLogData.setValueAt(nRow, i, 0);
                    String logTime = lineChart.getLogTimeList().get(i).
                            substring(0, lineChart.getLogTimeList().get(i).indexOf('.'));
                    tableLogData.setValueAt(formatter.format(ConnectDB.SDATE_FORMAT_HOUR.
                            parse(ConnectDB.correctToBarreDate(logTime))), i, 1);//Day
                    tableLogData.setValueAt(logTime, i, 2);
                    tableLogData.setValueAt(Math.round(Float.valueOf(lineChart.getLogDataList().get(i))), i, 3);
                    nRow++;
                }
            } else if (getModuleOption(String.valueOf(cmbOptions.getSelectedItem())).equalsIgnoreCase("Average")
                    && "Machine Run (ON/OFF)".equals(tbpPanDetails.getTitleAt(tbpPanDetails.getSelectedIndex()))) {
                clearLabelOfSums();
                viewDateTableName = "Machine Run";
                for (int i = 0; i < MachineRun.getLogTimeList().size(); i++) {
                    if (nRow > tableLogData.getModel().getRowCount()) {
                        TableModelProductionData refTableValues = (TableModelProductionData) tableLogData.getModel();
                        refTableValues.addNewRow();
                    }
                    tableLogData.setValueAt(nRow, i, 0);
                    String logTime = MachineRun.getLogTimeList().get(i).
                            substring(0, MachineRun.getLogTimeList().get(i).indexOf('.'));
                    tableLogData.setValueAt(formatter.format(ConnectDB.SDATE_FORMAT_HOUR.
                            parse(ConnectDB.correctToBarreDate(logTime))), i, 1);//Day
                    tableLogData.setValueAt(logTime, i, 2);
                    tableLogData.setValueAt(MachineRun.getLogDataList().get(i), i, 3);
                    nRow++;
                }
            } else {
                return;
            }
            this.setTableNameOfValues(new StringBuilder("<html><font color=blue><strong>").append(viewDateTableName).
                    append("</strong></font><html>").toString());
            tableLogData.getColumnModel().getColumn(0).setMinWidth(35);
            tableLogData.getColumnModel().getColumn(0).setMaxWidth(35);
            tableLogData.getColumnModel().getColumn(0).setResizable(false);
        } catch (NumberFormatException | HeadlessException ex) {
//            ex.printStackTrace();
        }
    }

    private void setTableNameOfValues(String name) {
        try {
            panTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                    new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                        UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                    new StringBuilder(name).append(" Table [").append(tableLogData.getModel().getRowCount()).
                    append(" row(s)]").toString(), TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                    BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        } catch (Exception e) {
            panTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                    new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                        UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                    new StringBuilder(name).append(" Table [0 row(s)]").toString(), TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                    BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        }
    }

    private void createTabbedPanel(JPanel optionPanel, String panelName) throws ParseException {
        try {
            byte i = 0;
            if (tbpPanDetails.getTabCount() == 0) {
                i = 0;
                tbpPanDetails.insertTab(panelName, null, optionPanel, new StringBuilder(channelTitle).
                        append(" ").append(panelName).toString(), i);
            } else {
                boolean find = false;
                for (; i < tbpPanDetails.getTabCount(); i++) {
                    if (panelName.equals(tbpPanDetails.getTitleAt(i))) {
                        find = true;
                        optionPanel.removeAll();
                        optionPanel.repaint();
                        optionPanel.setLayout(new BorderLayout());
                        switch (panelName) {
                            case "Total Production"://Total Production
                                if (barChart.getChart() != null) {
                                    JPanel panel = new JPanel();
                                    Legend legend = new Legend(barChart.getChart(), 0);
                                    panel.add(legend);
                                    alTotalProd.get(0).add(panel, BorderLayout.SOUTH);
                                    alTotalProd.get(0).add(barChart.getChart());
                                }
                                break;
                            case "Production Rate"://Production rate
                                if (lineChart.getChart() != null) {
                                    alProdRate.get(0).add(lineChart.getChart());
                                }
                                break;
                            default:
                                if (machineRunPanel.getChart() != null) {
                                    alMachineRun.get(0).add(machineRunPanel.getChart());
                                }
                                break;
                        }
                        tbpPanDetails.setSelectedIndex(i);
                        tbpPanDetails.getSelectedComponent().revalidate();
                        break;
                    }
                }
                if (!find) {
                    tbpPanDetails.insertTab(panelName, null, optionPanel, new StringBuilder(channelTitle).
                            append(" ").append(panelName).toString(), i);
                    tbpPanDetails.setSelectedIndex(i);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        viewDataInTable();
    }

    private void createExcel(File file) {
        try {
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook wbb = Workbook.createWorkbook(file);
            WritableSheet sheet = wbb.createSheet(viewDateTableName, 0);
            Label label;
            byte p;
            //Excel Files headings
            sheet.addCell(new Label(0, 0, new StringBuilder(machineTitle).append(" Analysis Results").toString()));
            sheet.addCell(new Label(0, 1, ""));
            //get the table headings
            for (byte gh = 0; gh < tableLogData.getColumnCount(); gh++) {
                p = gh;
                if (p < tableLogData.getColumnCount()) {
                    label = new Label(gh, 2, tableLogData.getColumnName(p));
                    if (p == tableLogData.getColumnCount() - 1) {//the value column
                        label = new Label(gh, 2, tableLogData.getColumnName(p));
                    }
                    sheet.addCell(label);
                    p++;
                }
            }
            //get the table datas
            int a = 0;
            for (int i = 0; i < tableLogData.getColumnCount(); i++) {
                int b = 0;
                for (int j = 0; j < tableLogData.getRowCount(); j++) {
                    if (a < tableLogData.getColumnCount()) {
                        label = new Label(i, j + 3, tableLogData.getValueAt(b, a).toString());
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

    private void getComponentDates() throws SQLException {
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
        if (catDatesRunning) {
            cmbPTo.setEnabled(false);
            catDatesRunning = false;
        }
    }

    private void autoFill() throws SQLException {
        catMachine = false;
        cmbMachineTitle.removeAllItems();
        cmbMachineTitle.addItem(" ");
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_HARDWARE)) {
            ps.setInt(1, 0);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                cmbMachineTitle.addItem(ConnectDB.res.getString(2));
            }
        }
        catMachine = true;
    }

    private void saveChanges() {
        if (saved) {
            if (tableOfTime.isEditing()) {
                tableOfTime.getCellEditor().stopCellEditing();
            }
            propertiesToSave = getPropertiesToSave();
        }
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.PRODPANESET, propertiesToSave);
    }

    private String getPropertiesToSave() {
        StringBuilder properties = new StringBuilder(1024);

        if (cmbPFrom.getDate() != null) {
            properties.append(cmbPFrom.getDate().getTime()).append("\r\n");
        } else {
            properties.append("\r\n");
        }
        if (cmbPTo.getDate() != null) {
            properties.append(cmbPTo.getDate().getTime()).append("\r\n");
        } else {
            properties.append("\r\n");
        }
        //save the table number of row
        properties.append(spShiftTableRow.getValue()).append("\r\n");
        //save the values of the time shift table
        for (byte i = 0; i < tableOfTime.getRowCount(); i++) {
            for (byte j = 1; j < 3; j++) {
                properties.append(tableOfTime.getValueAt(i, j)).append("\t");
            }
            properties.append("\r\n");
        }
        return properties.toString();
    }

    private void setPropertiesTimeSaved(String values) {
        catDates = false;
        boolean error = false;
        try {
            propertiesToSave = values;
            saved = false;
            String[] rowValues = values.split("\r\n");
            byte rValuesLenght = (byte) rowValues.length;

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
            timeLoaded = false;
            spShiftTableRow.setValue(Integer.parseInt(rowValues[2]));
            timeLoaded = true;
            int k = (int) spShiftTableRow.getValue();
            setTableTime(k);//tables for the hour shifts
            k = rValuesLenght - k;
            int row = k;
            while (k < rValuesLenght) {
                String[] columnTime = rowValues[k].split("\t");
                for (int j = 0; j < columnTime.length; j++) {
                    tableOfTime.setValueAt(columnTime[j], k - row, j + 1);
                }
                k++;
            }
            saved = true;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            error = true;
            saved = true;
            ConnectDB.pref.remove(SettingKeyFactory.DefaultProperties.PRODPANESET);
        } finally {
            saved = true;
            if (error) {
                timeLoaded = false;
                spShiftTableRow.setValue(1);
                timeLoaded = true;
                setTableTime((int) spShiftTableRow.getValue());
            }
            setSettings();
            catDates = true;
        }
    }

    public static void setSettings() {
        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
        radPerMin.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true));
        radPerHour.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false));
    }

    private void setTab(String tabName) {
        byte i = 0;
        for (; i < tbpPanDetails.getTabCount(); i++) {
            if (tabName.equals(tbpPanDetails.getTitleAt(i))) {
                tbpPanDetails.setSelectedIndex(i);
                tbpPanDetails.getSelectedComponent().revalidate();
                break;
            }
        }
    }

    private String getModuleOption(String options) {
        try {
            String[] rowValues = addModule.getSavedProperties().split("\r\n");
            for (String string : rowValues) {
                if (options.equals(string.split("\t")[0].trim())) {
                    return string.split("\t")[1];
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    private void clearLabelOfSums() {
        lblShiftSum1.setText("");
        lblShiftSum2.setText("");
        lblShiftSum3.setText("");
        lblTotalProductionSum.setText("");
    }

    private void clearLabelActive() {
        lblActive.setText("");
        lblActive.setIcon(null);
        lblActive.setToolTipText("");
        lblMessage.setText("");
    }

    private void setTableTime(int numRow) {
        modelTimeShift = new TableModelShiftTime(numRow);
        tableOfTime = new SortableTable(modelTimeShift);
        tableOfTime.getTableHeader().setReorderingAllowed(false);
        tableOfTime.setFocusable(false);
        tableOfTime.setSortable(false);
        tableOfTime.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        tableOfTime.getTableHeader().setBackground(Color.BLUE);
        tableOfTime.setColumnAutoResizable(true);
        tableOfTime.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        tableOfTime.getColumnModel().getColumn(0).setMinWidth(35);
        tableOfTime.getColumnModel().getColumn(0).setMaxWidth(35);
        tableOfTime.getColumnModel().getColumn(0).setResizable(false);
        tableOfTime.setRowSelectionAllowed(true);
        tableOfTime.setColumnSelectionAllowed(true);
        tableOfTime.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableOfTime.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        shiftPane.setViewportView(tableOfTime);
    }

    private void createTableValues() {
        tableLogData = null;
        modelData = new TableModelProductionData();
        tableLogData = new SortableTable(modelData);
        tableLogData.setTableStyleProvider(new RowStripeTableStyleProvider(ConnectDB.colors3()));
        tableLogData.getTableHeader().setReorderingAllowed(false);
        tableLogData.setFocusable(false);
        tableLogData.setSortable(false);
        tableLogData.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        tableLogData.getTableHeader().setBackground(Color.BLUE);
        tableLogData.setColumnAutoResizable(true);
        tableLogData.getColumnModel().getColumn(0).setMinWidth(35);
        tableLogData.getColumnModel().getColumn(0).setMaxWidth(35);
        tableLogData.getColumnModel().getColumn(0).setResizable(false);
        logDataPane.getViewport().setBackground(Color.WHITE);
        logDataPane.setViewportView(tableLogData);
    }

    private void cleanTable() {
        modelData = new TableModelProductionData();
        tableLogData.setModel(modelData);
        tableLogData.getColumnModel().getColumn(0).setMinWidth(35);
        tableLogData.getColumnModel().getColumn(0).setMaxWidth(35);
        tableLogData.getColumnModel().getColumn(0).setResizable(false);
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Production");
        frame.getContentPane().add(new ProductionPane(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnAddMode;
    private com.jidesoft.swing.JideButton btnCleanTable;
    private javax.swing.JButton btnCleanUI;
    private javax.swing.JButton btnCopyToClipboard;
    private com.jidesoft.swing.JideButton btnExportExcelCsv;
    public static javax.swing.JButton btnMessage;
    private com.jidesoft.swing.JideButton btnPlotChart;
    private javax.swing.JButton btnPrintChart;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSettings;
    private com.jidesoft.swing.JideButton btnShiftTable;
    private com.jidesoft.swing.JideButton btnShowEvents;
    private javax.swing.JButton btnShowTargets;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnViewData;
    private javax.swing.JButton btnViewEvents;
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
    private javax.swing.JScrollPane logDataPane;
    private javax.swing.JPanel panPerShiftTotals;
    private javax.swing.JPanel panProductionRate;
    private javax.swing.JPanel panShiftTime;
    private javax.swing.JPanel panShiftTotals;
    private javax.swing.JPanel panTable;
    private static javax.swing.JRadioButton radPerHour;
    private static javax.swing.JRadioButton radPerMin;
    private javax.swing.JScrollPane shiftPane;
    private com.alee.laf.spinner.WebSpinner spShiftTableRow;
    private com.jidesoft.swing.JideTabbedPane tbpPanDetails;
    // End of variables declaration//GEN-END:variables
    private int optionsIndex = -1, machineID;
    private boolean skipFirstMessage = true, //variables for the dates
            clearTabbedPane, catMachine, catChannel, catTabbedChange, catDates, catDatesRunning, timeLoaded;
    private static String channelTitle = "", tableType = "",
            machineTitle = "", chartTitle;
    private String viewDateTableName = "", propertiesToSave = "";
    private static boolean catOptions,//check if all the machineTitle title are loaded
            saved = true;
    private static SortableTable tableOfTime = null,
            tableLogData = null;
    private Date dt_startP, dt_endP;
    private final ArrayList<ProductionRate> alProdRate = new ArrayList<>();
    private final ArrayList<TotalProduction> alTotalProd = new ArrayList<>();
    private final ArrayList<MachineRun> alMachineRun = new ArrayList<>();
    private LineChart lineChart = null;
    private BarChart barChart = null;
    private MachineRun machineRunPanel = null;
    private final SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
    private ViewData viewData;
    private JFrame _parent;
    private String rateTab, runTab, totTab;
    private AddModule addModule = null;
    private ViewHistory viewHistory = null;
//    private TableModelListener _listener;
    private TableModelShiftTime modelTimeShift = null;
    private TableModelProductionData modelData = null;
}
