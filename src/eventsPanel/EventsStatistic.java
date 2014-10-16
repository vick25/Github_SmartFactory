package eventsPanel;

import balloonTip.BalloonTipDemo;
import chartTypes.RandomColor;
import com.jidesoft.chart.BarResizePolicy;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.ChartType;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.event.MouseDragPanner;
import com.jidesoft.chart.event.PanIndicator;
import com.jidesoft.chart.event.PointSelection;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.render.AbstractPieLabelRenderer;
import com.jidesoft.chart.render.AbstractPieSegmentRenderer;
import com.jidesoft.chart.render.Axis3DRenderer;
import com.jidesoft.chart.render.CylinderBarRenderer;
import com.jidesoft.chart.render.DefaultBarRenderer;
import com.jidesoft.chart.render.DefaultPieSegmentRenderer;
import com.jidesoft.chart.render.LinePieLabelRenderer;
import com.jidesoft.chart.render.NoAxisRenderer;
import com.jidesoft.chart.render.Pie3DRenderer;
import com.jidesoft.chart.render.PieLabelRenderer;
import com.jidesoft.chart.render.PointLabeler;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.render.RaisedPieSegmentRenderer;
import com.jidesoft.chart.render.SimplePieLabelRenderer;
import com.jidesoft.chart.render.SphericalPointRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Positionable;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.InfiniteProgressPanel;
import com.jidesoft.swing.Overlayable;
import com.jidesoft.swing.OverlayableUtils;
import com.jidesoft.swing.SearchableUtils;
import com.jidesoft.swing.TreeSearchable;
import com.jidesoft.tree.TreeUtils;
import irepport.view.Print;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import reportSettings.ReportOptions;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

public class EventsStatistic extends javax.swing.JPanel {

    public static int getMachineID() {
        return machineID;
    }

    public static ChartStyle getStylePieChart() {
        return stylePieChart;
    }

    public static Chart getChart() {
        return chart;
    }

    public static String getMinLogTime() {
        return minLogTime;
    }

    public static String getMaxLogTime() {
        return maxLogTime;
    }

    public static JTree getTree() {
        return _tree;
    }

    public static Set<String> getDescriptionSet() {
        return Collections.unmodifiableSet(descriptionSet);
    }

    public EventsStatistic(JFrame parent) throws SQLException {
        initComponents();
        this._parent = parent;//MainFrame
        randomColor = new RandomColor();
        ConnectDB.getConnectionInstance();
        loadComboBox();//load the combobox with data from the database
        AutoCompleteDecorator.decorate(cmbMachineTitle);
        AutoCompleteDecorator.decorate(cmbProductionType);
        jPanel5.setFocusable(true);
        jPanel5.requestFocus();
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableResetButton();
                if (btnTimeEvent.isSelected()) {
                    lblTo.setEnabled(true);
//                    lblEventTo.setEnabled(true);
                    lblFrom.setEnabled(true);
//                    lblProductionRate.setEnabled(true);
//                    jLabel4.setEnabled(true);
//                    cmbProductionType.setEnabled(true);
//                    spProductionRate.setEnabled(true);
                    if (panChart.getComponentCount() > 0) {
                        btnClipboard.setEnabled(true);
                        btnPrintChart.setEnabled(true);
                    } else {
                        btnClipboard.setEnabled(false);
                        btnPrintChart.setEnabled(false);
                    }
                    if (_tree != null) {
                        if (panChart.getComponentCount() > 0 || !_tree.isSelectionEmpty()
                                || _tree.isShowing()) {
                            btnViewData.setEnabled(true);
                            btnRefresh.setEnabled(true);
                        } else {
                            btnRefresh.setEnabled(false);
                            btnViewData.setEnabled(false);
                        }
                    }
                    if (catMachine) {
                        if (cmbMachineTitle.getSelectedItem() != null && cmbMachineTitle.getSelectedIndex() > 0) {
                            radHour.setEnabled(true);
                            radMinute.setEnabled(true);
                            radSecond.setEnabled(true);
                        } else {
                            radHour.setEnabled(false);
                            radMinute.setEnabled(false);
                            radSecond.setEnabled(false);
                        }
                    }
                } else {
                    lblEventTo.setEnabled(false);
                    lblFrom.setEnabled(false);
                    lblTo.setEnabled(false);
                    lblProductionRate.setEnabled(false);
                    jLabel4.setEnabled(false);
                    cmbProductionType.setEnabled(false);
                    spProductionRate.setEnabled(false);
                    radHour.setEnabled(false);
                    radMinute.setEnabled(false);
                    radSecond.setEnabled(false);
                    btnViewData.setEnabled(false);
                }
            }
        });
        timer.start();
        if (ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.CMBTIME, 0) == 0) {
            radHour.setSelected(true);
        } else if (ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.CMBTIME, 0) == 1) {
            radMinute.setSelected(true);
        } else {
            radSecond.setSelected(true);
        }
        setPropertiesSaved();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkFrom = new javax.swing.JCheckBox();
        chkTo = new javax.swing.JCheckBox();
        cmbETo = new com.jidesoft.combobox.DateComboBox();
        cmbEFrom = new com.jidesoft.combobox.DateComboBox();
        lblProductionRate = new javax.swing.JLabel();
        cmbProductionType = new org.jdesktop.swingx.JXComboBox();
        spProductionRate = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblFrom = new javax.swing.JLabel();
        lblEventTo = new javax.swing.JLabel();
        lblTo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbValue = new com.jidesoft.combobox.CheckBoxListComboBox();
        cmbDescription = new com.jidesoft.combobox.CheckBoxListComboBox();
        cmbMachineTitle = new org.jdesktop.swingx.JXComboBox();
        jPanel7 = new javax.swing.JPanel();
        btnDataEvent = new javax.swing.JToggleButton();
        btnTimeEvent = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        radHour = new javax.swing.JRadioButton();
        radMinute = new javax.swing.JRadioButton();
        radSecond = new javax.swing.JRadioButton();
        jToolBar2 = new javax.swing.JToolBar();
        jToolBar7 = new javax.swing.JToolBar();
        btnPrintChart = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jToolBar8 = new javax.swing.JToolBar();
        btnPieChart = new javax.swing.JButton();
        btnBarChart = new javax.swing.JButton();
        btnLineChart = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnClipboard = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSetting = new javax.swing.JButton();
        jToolBar9 = new javax.swing.JToolBar();
        btnViewData = new javax.swing.JButton();
        btnOEECalculation = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        _field = new com.jidesoft.tree.QuickTreeFilterField(){
            @Override
            public void applyFilter() {
                super.applyFilter();
                TreeUtils.expandAll(_tree);
            }
        };
        scrlPaneTree = new javax.swing.JScrollPane();
        lblTimeSum = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jSplitPane1.setDividerLocation(185);
        jSplitPane1.setDividerSize(9);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);

        panChart.setBackground(new java.awt.Color(255, 255, 255));
        panChart.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(panChart);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Production Period", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 102, 255))); // NOI18N

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

        cmbETo.setShowWeekNumbers(false);
        cmbETo.setDate(Calendar.getInstance().getTime());
        cmbETo.setFocusable(false);
        cmbETo.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        cmbETo.setTimeDisplayed(true);
        cmbETo.setTimeFormat("HH:mm:ss");
        cmbETo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEToActionPerformed(evt);
            }
        });

        cmbEFrom.setShowWeekNumbers(false);
        cmbEFrom.setDate(Calendar.getInstance().getTime());
        cmbEFrom.setFocusable(false);
        cmbEFrom.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        cmbEFrom.setTimeDisplayed(true);
        cmbEFrom.setTimeFormat("HH:mm:ss");
        cmbEFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEFromActionPerformed(evt);
            }
        });

        lblProductionRate.setBackground(new java.awt.Color(204, 204, 204));
        lblProductionRate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductionRate.setForeground(new java.awt.Color(204, 0, 51));
        lblProductionRate.setText("Production Rate");
        lblProductionRate.setEnabled(false);

        cmbProductionType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">", "=", "<=", ">=" }));
        cmbProductionType.setSelectedIndex(2);
        cmbProductionType.setEnabled(false);

        spProductionRate.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        spProductionRate.setEnabled(false);
        spProductionRate.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Events Time:");
        jLabel3.setEnabled(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lblEventTo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEventTo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEventTo.setText("To");

        lblTo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEventTo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTo, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(lblFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEventTo)
                .addComponent(lblTo))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblFrom, lblTo});

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("(Prod)");
        jLabel4.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkFrom)
                            .addComponent(chkTo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbETo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbEFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblProductionRate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProductionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spProductionRate, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkFrom)
                    .addComponent(cmbEFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkTo)
                    .addComponent(cmbETo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(spProductionRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbProductionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductionRate)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbProductionType, lblProductionRate, spProductionRate});

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Events Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 102, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Category");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Machine");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Value");

        cmbValue.setEnabled(false);

        cmbDescription.setEnabled(false);
        cmbDescription.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDescriptionItemStateChanged(evt);
            }
        });

        cmbMachineTitle.setFocusable(false);
        cmbMachineTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMachineTitleItemStateChanged(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Events of", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        btnDataEvent.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(btnDataEvent);
        btnDataEvent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnDataEvent.setForeground(new java.awt.Color(204, 0, 0));
        btnDataEvent.setSelected(true);
        btnDataEvent.setText("Data");
        btnDataEvent.setFocusable(false);
        btnDataEvent.setOpaque(true);
        btnDataEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataEventActionPerformed(evt);
            }
        });

        btnTimeEvent.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(btnTimeEvent);
        btnTimeEvent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTimeEvent.setForeground(new java.awt.Color(204, 0, 0));
        btnTimeEvent.setText("Time");
        btnTimeEvent.setFocusable(false);
        btnTimeEvent.setOpaque(true);
        btnTimeEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeEventActionPerformed(evt);
            }
        });

        radHour.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radHour);
        radHour.setSelected(true);
        radHour.setText("Hour");
        radHour.setFocusable(false);
        radHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radHourActionPerformed(evt);
            }
        });

        radMinute.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radMinute);
        radMinute.setText("Minutes");
        radMinute.setFocusable(false);
        radMinute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radMinuteActionPerformed(evt);
            }
        });

        radSecond.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radSecond);
        radSecond.setText("Seconds");
        radSecond.setFocusable(false);
        radSecond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radSecondActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radHour, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radSecond, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(radHour)
                    .addComponent(radMinute)
                    .addComponent(radSecond))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnDataEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimeEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(btnDataEvent)
                .addComponent(btnTimeEvent)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel2)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbMachineTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(6, 6, 6))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmbMachineTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(cmbDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(cmbValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 758, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, Short.MAX_VALUE))
                    .addGap(1, 1, 1)))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jToolBar7.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(0, 102, 255))); // NOI18N
        jToolBar7.setRollover(true);

        btnPrintChart.setBackground(new java.awt.Color(255, 255, 255));
        btnPrintChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/printer_1.png"))); // NOI18N
        btnPrintChart.setText("Print report");
        btnPrintChart.setEnabled(false);
        btnPrintChart.setFocusable(false);
        btnPrintChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrintChart.setOpaque(false);
        btnPrintChart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrintChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintChartActionPerformed(evt);
            }
        });
        jToolBar7.add(btnPrintChart);

        btnReset.setBackground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/clear.png"))); // NOI18N
        btnReset.setText("Clean");
        btnReset.setEnabled(false);
        btnReset.setFocusable(false);
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.setOpaque(false);
        btnReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jToolBar7.add(btnReset);

        jToolBar2.add(jToolBar7);

        jToolBar8.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Charts", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(0, 102, 255))); // NOI18N
        jToolBar8.setRollover(true);

        btnPieChart.setBackground(new java.awt.Color(255, 255, 255));
        btnPieChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/pie2d.png"))); // NOI18N
        btnPieChart.setText("Pie chart");
        btnPieChart.setEnabled(false);
        btnPieChart.setFocusable(false);
        btnPieChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPieChart.setOpaque(false);
        btnPieChart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPieChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPieChartActionPerformed(evt);
            }
        });
        jToolBar8.add(btnPieChart);

        btnBarChart.setBackground(new java.awt.Color(255, 255, 255));
        btnBarChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/columns2d.png"))); // NOI18N
        btnBarChart.setText("Bar chart");
        btnBarChart.setFocusable(false);
        btnBarChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBarChart.setOpaque(false);
        btnBarChart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBarChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarChartActionPerformed(evt);
            }
        });
        jToolBar8.add(btnBarChart);

        btnLineChart.setBackground(new java.awt.Color(255, 255, 255));
        btnLineChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/stackdirect2d.png"))); // NOI18N
        btnLineChart.setText("Line chart");
        btnLineChart.setFocusable(false);
        btnLineChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLineChart.setOpaque(false);
        btnLineChart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLineChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLineChartActionPerformed(evt);
            }
        });
        jToolBar8.add(btnLineChart);
        jToolBar8.add(jSeparator2);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/refresh2_1.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setOpaque(false);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar8.add(btnRefresh);
        jToolBar8.add(jSeparator3);

        btnClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/1_folder2.png"))); // NOI18N
        btnClipboard.setText("Copy to clipboard");
        btnClipboard.setEnabled(false);
        btnClipboard.setFocusable(false);
        btnClipboard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClipboard.setOpaque(false);
        btnClipboard.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClipboardActionPerformed(evt);
            }
        });
        jToolBar8.add(btnClipboard);
        jToolBar8.add(jSeparator1);

        btnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/advancedsettings(4).png"))); // NOI18N
        btnSetting.setText("Settings");
        btnSetting.setFocusable(false);
        btnSetting.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetting.setOpaque(false);
        btnSetting.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });
        jToolBar8.add(btnSetting);

        jToolBar2.add(jToolBar8);

        jToolBar9.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Events Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(0, 102, 255))); // NOI18N
        jToolBar9.setRollover(true);

        btnViewData.setBackground(new java.awt.Color(255, 255, 255));
        btnViewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/view_icon.png"))); // NOI18N
        btnViewData.setText("View Data");
        btnViewData.setEnabled(false);
        btnViewData.setFocusable(false);
        btnViewData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewData.setOpaque(false);
        btnViewData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDataActionPerformed(evt);
            }
        });
        jToolBar9.add(btnViewData);

        btnOEECalculation.setBackground(new java.awt.Color(255, 255, 255));
        btnOEECalculation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/oee_calculator.png"))); // NOI18N
        btnOEECalculation.setText("OEE Calculation");
        btnOEECalculation.setEnabled(false);
        btnOEECalculation.setFocusable(false);
        btnOEECalculation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOEECalculation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar9.add(btnOEECalculation);

        jToolBar2.add(jToolBar9);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSplitPane1))
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        _field.setHintText("Filter");
        _field.setShowMismatchColor(true);
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("***NO EVENT RETREIVED***");
        _field.setTreeModel(new javax.swing.tree.DefaultTreeModel(treeNode1));

        scrlPaneTree.setForeground(new java.awt.Color(51, 51, 255));

        lblTimeSum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_field, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
            .addComponent(scrlPaneTree)
            .addComponent(lblTimeSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrlPaneTree)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTimeSum, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("Events are shown as a tree view on the right side whenever a machine and correct period range are selected.");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6))
        );
    }// </editor-fold>//GEN-END:initComponents

private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
    try {
        catMachine = false;
        cmbDescription.setSelectedIndex(-1);
        cmbMachineTitle.setSelectedIndex(-1);
        cmbValue.setSelectedIndex(-1);
        totalSum = 0d;
        max = 0;
        chart = null;
        model = null;
        _thread1 = null;
        descriptionSet.clear();
//        setDescOrValue.clear();
        runThread = null;
        panChart.removeAll();
        panChart.repaint();
        _tree = null;
        scrlPaneTree.setViewportView(null);
        _field.setText(null);
        lblTimeSum.setText("");
        btnPieChartActionPerformed(evt);
        catMachine = true;
        colorsCategoryRange.reset();
        HL.clear();
        System.gc();
    } catch (NullPointerException e) {
        scrlPaneTree.setViewportView(null);
    }
}//GEN-LAST:event_btnResetActionPerformed

    private void btnLineChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineChartActionPerformed
        btnBarChart.setEnabled(true);
        btnPieChart.setEnabled(true);
        btnLineChart.setEnabled(false);
        if (_tree != null && _tree.getSelectionPath() != null) {
            createLineChart();
        }
    }//GEN-LAST:event_btnLineChartActionPerformed

    private void btnBarChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarChartActionPerformed
        btnBarChart.setEnabled(false);
        btnPieChart.setEnabled(true);
        btnLineChart.setEnabled(true);
        if (_tree != null && _tree.getSelectionPath() != null) {
            createBarChart();
        }
    }//GEN-LAST:event_btnBarChartActionPerformed

    private void btnPieChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPieChartActionPerformed
        btnBarChart.setEnabled(true);
        btnPieChart.setEnabled(false);
        btnLineChart.setEnabled(true);
        if (_tree != null && _tree.getSelectionPath() != null) {
            createPieChart();
        }
    }//GEN-LAST:event_btnPieChartActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        StatSetting.showOptionsDialog();
        if (ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.CMBTIME, 0) == 0) {
            radHour.setSelected(true);
        } else if (ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.CMBTIME, 0) == 1) {
            radMinute.setSelected(true);
        } else {
            radSecond.setSelected(true);
        }
    }//GEN-LAST:event_btnSettingActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        catMachine = true;
        _thread1 = null;
        try {
            int[] rows = _tree.getSelectionRows();
            TreePath path = _tree.getSelectionPath();
            cmbMachineTitleItemStateChanged(null);
            _tree.setSelectionPath(path);
            _tree.setSelectionRow(rows[0]);
            _tree.repaint();
        } catch (java.util.NoSuchElementException | NullPointerException | ArrayIndexOutOfBoundsException e) {
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClipboardActionPerformed
        ChartUtils.copyImageToClipboard(panChart);
    }//GEN-LAST:event_btnClipboardActionPerformed

    private void btnViewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDataActionPerformed
        if (eht != null) {
            eht.dispose();
        }
        eht = new EventsHierarchicalTable();
        eht.setVisible(true);
    }//GEN-LAST:event_btnViewDataActionPerformed

    private void btnPrintChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintChartActionPerformed
        final ReportOptions reportOptions = new ReportOptions(_parent, true);
        reportOptions.chkPrintChart.setSelected(true);
        reportOptions.chkPrintTable.setSelected(false);
        reportOptions.chkPrintTable.setEnabled(false);
        reportOptions.setReportTitle("Events plot for \"" + cmbMachineTitle.getSelectedItem().toString()
                + "\" (" + timeFormat + ")");
        ReportOptions.txtReportTitle.setText(reportOptions.getReportTitle());
        ReportOptions.txtReportTitle.setForeground(Color.BLACK);
        ReportOptions.txtReportTitle.setHorizontalAlignment(javax.swing.JTextField.LEADING);
        ReportOptions.txtReportTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        Thread threadPrint = new Thread() {

            @Override
            public void run() {
                try {
                    HashMap hashMap = new HashMap();
                    chart.setTitle("");
                    ConnectDB.setMainDir(new File(new StringBuilder(ConnectDB.DEFAULT_DIRECTORY).
                            append(File.separator).append("SmartFactory Data").toString()));
                    if (!ConnectDB.getMainDir().exists()) {
                        ConnectDB.getMainDir().mkdirs();
                    }
                    String dirIcon = new StringBuilder(ConnectDB.getMainDir().getAbsolutePath()).
                            append(File.separator).append("chart.png").toString();
                    ChartUtils.writePngToFile(chart, new File(dirIcon));
                    hashMap.put("logo", reportOptions.getPhoto());
                    hashMap.put("logo2", getClass().getResourceAsStream("/jasper/smartfactory.png"));
                    hashMap.put("date1", ConnectDB.SDATE_FORMAT_HOUR.format(eventFrom));
                    hashMap.put("date2", ConnectDB.SDATE_FORMAT_HOUR.format(eventTo));
                    if (reportOptions.isAddChart()) {
                        hashMap.put("photo", new FileInputStream(new File(dirIcon)));
                    }
                    hashMap.put("title", reportOptions.getReportTitle());
                    JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream(""
                            + "/jasper/chart.jasper"), hashMap, ConnectDB.con);
                    Print.viewReport(_parent, jp, false, ConnectDB.LOCALE);
                    chart.setTitle(new AutoPositionedLabel(reportTitle, Color.BLACK, ConnectDB.TITLEFONT));
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

    private void chkFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFromItemStateChanged
        if (chkFrom.isSelected()) {
            cmbEFrom.setEnabled(true);
            Calendar working = ((Calendar) Calendar.getInstance().clone());
            working.add(Calendar.DAY_OF_YEAR, -1);
            cmbEFrom.setDate(working.getTime());
        } else {
            cmbEFrom.setEnabled(false);
        }
    }//GEN-LAST:event_chkFromItemStateChanged

    private void chkToItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkToItemStateChanged
        if (chkTo.isSelected()) {
            cmbETo.setEnabled(true);
            cmbETo.setDate(Calendar.getInstance().getTime());
        } else {
            cmbETo.setEnabled(false);
        }
    }//GEN-LAST:event_chkToItemStateChanged

    private void cmbEToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEToActionPerformed
        if (cmbEFrom.getDate() != null && cmbETo.getDate() != null) {
            if (cmbEFrom.getDate().getTime() > cmbETo.getDate().getTime()) {
                skipFirstMessage = true;
                if (skipFirstMessage) {
                    JOptionPane.showMessageDialog(this, "End date of production can not come "
                            + "before the start date...", "Dates", JOptionPane.WARNING_MESSAGE);
                    skipFirstMessage = false;
                }
            }
        }
        catMachine = true;
        cmbMachineTitleItemStateChanged(null);
    }//GEN-LAST:event_cmbEToActionPerformed

    private void cmbEFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEFromActionPerformed
        cmbEToActionPerformed(evt);
    }//GEN-LAST:event_cmbEFromActionPerformed

    private void cmbMachineTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMachineTitleItemStateChanged
        cmbMachineTitle.setPopupVisible(false);
        if (catMachine) {
            catDescription = false;
            if (cmbMachineTitle.getSelectedIndex() < 0) {
                new BalloonTipDemo(cmbMachineTitle, "Please select a machine in the dropdown "
                        + "list component.").toggleToolTip();
                return;
            }
            if (!"".equals(cmbMachineTitle.getSelectedItem()) && cmbMachineTitle.getSelectedIndex() > 0) {
                machineTitle = cmbMachineTitle.getSelectedItem().toString();
                try {
                    machineID = ConnectDB.getIDMachine(machineTitle);
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                panChart.removeAll();
                panChart.repaint();
                scrlPaneTree.setViewportView(null);//clearing the scrollpanel of the jtree
                boolean find = false;
                ArrayList<String> data = new ArrayList<>();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT DISTINCT(c.Description) "
                        + "FROM eventlog e, customlist c \n"
                        + "WHERE e.Customcode = c.Code AND e.HwNo =? \n"
                        + "ORDER BY Description ASC")) {
                    ps.setInt(1, machineID);
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        find = true;
                        data.add(ConnectDB.res.getString(1));//adding the description
                    }
                    cmbDescription.setModel(new DefaultComboBoxModel(data.toArray()));
                    data.clear();
                    cmbDescription.setSelectedIndex(-1);
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                if (find) {
                    try {
                        createTreeDisplay();
                        initFieldTree();
                    } catch (java.lang.NullPointerException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
//                    catMachine = false;
                } else {
                    catMachine = false;
                    JOptionPane.showMessageDialog(this, new StringBuilder("No description/category exists for the \"").
                            append(machineTitle).append("\" machine").toString(), "Events", JOptionPane.WARNING_MESSAGE);
                    lblFrom.setText("");
                    lblTo.setText("");
                    _field.setTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode(EMPTYEVENTS)));
                    scrlPaneTree.setViewportView(null);
                }
            } else {
                panChart.removeAll();
                panChart.repaint();
                scrlPaneTree.setViewportView(null);
                cmbDescription.setModel(new DefaultComboBoxModel());
                cmbDescription.setSelectedIndex(-1);
                cmbValue.setModel(new DefaultComboBoxModel());
                cmbValue.setSelectedIndex(-1);
                lblTimeSum.setText("");
                lblFrom.setText("");
                lblTo.setText("");
            }
            catDescription = true;
        } else {
            catMachine = true;
        }
    }//GEN-LAST:event_cmbMachineTitleItemStateChanged

    private void cmbDescriptionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDescriptionItemStateChanged
        if (catDescription) {
            if (cmbDescription.getSelectedObjects().length != 0) {
                ArrayList<String> dataValue = new ArrayList<>();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(new StringBuilder("SELECT DISTINCT(e.Value) ").
                        append("FROM eventlog e, customlist c \nWHERE e.HwNo =? AND e.Customcode = c.Code AND \n"
                                + "c.Description IN (").append(ConnectDB.retrieveCateria(cmbDescription.getSelectedObjects())).
                        append(") \nAND e.Value <> '(null)' \nORDER BY VALUE ASC").toString())) {
                    ps.setInt(1, machineID);
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        dataValue.add(ConnectDB.res.getString(1));
                    }
                    cmbValue.setModel(new DefaultComboBoxModel(dataValue.toArray()));
                    dataValue.clear();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                cmbValue.setSelectedIndex(-1);
            } else {
                cmbValue.setModel(new DefaultComboBoxModel());
                cmbValue.setSelectedIndex(-1);
            }
        }
    }//GEN-LAST:event_cmbDescriptionItemStateChanged

    private void btnDataEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataEventActionPerformed
//        panChart.removeAll();
//        panChart.repaint();
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_btnDataEventActionPerformed

    private void btnTimeEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeEventActionPerformed
        btnDataEventActionPerformed(evt);
    }//GEN-LAST:event_btnTimeEventActionPerformed

    private void radHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radHourActionPerformed
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_radHourActionPerformed

    private void radMinuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radMinuteActionPerformed
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_radMinuteActionPerformed

    private void radSecondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radSecondActionPerformed
        btnRefreshActionPerformed(evt);
    }//GEN-LAST:event_radSecondActionPerformed

    private void getComponentDates() throws SQLException {
        if (cmbEFrom.getDate() != null && cmbEFrom.isEnabled()) {
            eventFrom = cmbEFrom.getDate();
        } else {
            try (Statement stmt = ConnectDB.con.createStatement()) {
                ConnectDB.res = stmt.executeQuery("SELECT LogTime FROM datalog "
                        + "ORDER BY LogTime LIMIT 1");
                while (ConnectDB.res.next()) {
                    eventFrom = ConnectDB.res.getDate("LogTime");
                }
            }
        }
        if (cmbETo.getDate() != null && cmbETo.isEnabled()) {
            eventTo = cmbETo.getDate();
        } else {
            try (Statement stmt = ConnectDB.con.createStatement()) {
                ConnectDB.res = stmt.executeQuery("SELECT LogTime FROM datalog "
                        + "ORDER BY LogTime LIMIT 1");
                while (ConnectDB.res.next()) {
                    eventTo = ConnectDB.res.getDate("LogTime");
                }
            }
        }
    }

    private void createTreeDisplay() throws SQLException {
        //
        this.getComponentDates();
        //
        Set<String> setDescriptionValue = new TreeSet<>();
        PreparedStatement ps;
        descriptionSet.clear();
        String time = "", query;
        boolean loopQueryFound = false;

//        if (btnDataEvent.isSelected()) {
//            time = "";
//        } else {
//            //Get the minimum and maximum logtime of a specified production rate value (ie. 0)
//            query = new StringBuilder("SELECT LogData, \n").append("CONCAT(MIN(LogTime), ', ', MAX(LogTime)) AS Time \n"
//                    + "FROM datalog d, configuration co, hardware h \nWHERE co.HwNo = h.HwNo \n"
//                    + "AND co.ConfigNo = d.ConfigNo \nAND ((d.LogData * 60) ").
//                    append(cmbProductionType.getSelectedItem()).append(" ?) \n"
//                            + "AND d.ConfigNo =? \nAND co.HwNo =? \n"
//                            + "AND (d.LogTime BETWEEN ? AND ?) \n"
//                            + "GROUP BY LogData").toString();
//            ps = ConnectDB.con.prepareStatement(query);
//            ps.setInt(1, (int) spProductionRate.getValue());
//            ps.setInt(2, getConfigNo());
//            ps.setInt(3, machineID);
//            ps.setString(4, ConnectDB.SDATE_FORMAT_HOUR.format(dt_startE));
//            ps.setString(5, ConnectDB.SDATE_FORMAT_HOUR.format(dt_stopE));
//            System.out.println(ps.toString());
//            ConnectDB.res = ps.executeQuery();
//            while (ConnectDB.res.next()) {
//                loopQueryFound = true;
//                time = ConnectDB.res.getString(2);
//            }
//            ps.close();
//        }
        //case there is no events dates found where the production rate is specified
        if (time != null) {
//            if (!"".equals(time)) {//case for the time event
////                StringTokenizer st = new StringTokenizer(time, ",");
////                minLogTime = st.nextToken();//the eventtime minimum value
////                maxLogTime = st.nextToken();//the eventtime maximum value
//                lblFrom.setText("");
//                lblTo.setText("");
//            } else {//case for the data event
//            }
            minLogTime = ConnectDB.SDATE_FORMAT_HOUR.format(eventFrom);//the eventtime minimum value
            maxLogTime = ConnectDB.SDATE_FORMAT_HOUR.format(eventTo);//the eventtime maximum value  
            lblFrom.setText("");
            lblTo.setText("");
            query = "SELECT e.EventNo, CONCAT(e.EventTime, ',', e.UntilTime) AS 'Time', e.Value, c.Description \n"
                    + "FROM eventlog e, customlist c \n"
                    + "WHERE e.CustomCode = c.Code \n"
                    + "AND e.HwNo =? \n"
                    + "AND e.Value <> '(null)' \n"
                    + "AND (e.EventTime BETWEEN ? AND ?) \n"
                    + "ORDER BY c.Description ASC, e.Value ASC";
            ps = ConnectDB.con.prepareStatement(query);
            ps.setInt(1, machineID);
            ps.setString(2, minLogTime);
            ps.setString(3, maxLogTime);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                descriptionSet.add(ConnectDB.res.getString(4).toLowerCase());//get only the description
                setDescriptionValue.add(new StringBuilder(ConnectDB.res.getString(4)).append(";").
                        append(ConnectDB.res.getString(3)).toString().toLowerCase());//get the description and Value
            }
            ps.close();
            if (loopQueryFound) {//boolean value for description and value
                DefaultMutableTreeNode m_rootNode = new DefaultMutableTreeNode(machineTitle);
                Vector data = getDummyData(setDescriptionValue);//method to create the tree of description and value
                for (String sDesc : descriptionSet) {
                    DefaultMutableTreeNode m_descNode = new DefaultMutableTreeNode(ConnectDB.firstLetterCapital(sDesc));
                    for (Enumeration enumData = data.elements(); enumData.hasMoreElements();) {
                        Machine machData = (Machine) enumData.nextElement();//machData == Value
                        if (machData.getMachDomain().equals(sDesc)) {//check for a value description
                            m_descNode.add(new DefaultMutableTreeNode(ConnectDB.firstLetterCapital(machData.toString())));//add value to a description node
                        }
                    }
                    m_rootNode.add(m_descNode);//add a description to the machine main node
                }
                _field.setTreeModel(new DefaultTreeModel(m_rootNode));//put the node in the field tree
            } else {
                _field.setTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode(EMPTYEVENTS)));
                JOptionPane.showMessageDialog(_parent, "No data found and retrieved. Please check "
                        + "the period provided", "Events", JOptionPane.WARNING_MESSAGE);
                scrlPaneTree.setViewportView(null);
                lblTimeSum.setText("");
                catMachine = false;
            }
        } else {
            _field.setTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode(EMPTYEVENTS)));
            JOptionPane.showMessageDialog(this, new StringBuilder("No events registered during the periods "
                    + "specified for the \"").append(machineTitle).append("\"."), "Events", JOptionPane.WARNING_MESSAGE);
            scrlPaneTree.setViewportView(null);
            lblFrom.setText("");
            lblTo.setText("");
            lblTimeSum.setText("");
        }
    }

    public static Vector getDummyData(Set<String> set) {
        Vector dummyPartData = new Vector();
        for (Object object : set) {
            StringTokenizer tokenizer = new StringTokenizer(object.toString(), ";");
            String treeDescription = tokenizer.nextToken(),
                    treeValue = tokenizer.nextToken();
            dummyPartData.addElement(new Machine(treeValue, treeDescription));
        }
        return dummyPartData;
    }

    private void initFieldTree() {
        try {
            _field.setMatchesLeafNodeOnly(true);
            _field.setHideEmptyParentNode(true);
            _field.setWildcardEnabled(true);
            _tree = new JTree(_field.getDisplayTreeModel());
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) _tree.getCellRenderer();
            renderer.setLeafIcon(new ImageIcon(getClass().getResource("/images/icons/new12.gif")));
            renderer.setClosedIcon(new ImageIcon(getClass().getResource("/images/icons/background(6).png")));
            renderer.setOpenIcon(new ImageIcon(getClass().getResource("/images/icons/background2.png")));
            renderer.setTextSelectionColor(Color.WHITE);
            renderer.setBackgroundSelectionColor(Color.BLUE);
            renderer.setBorderSelectionColor(Color.BLACK);
            _field.setTree(_tree);
            TreeSearchable searchable = SearchableUtils.installSearchable(_tree);
            searchable.setFromStart(false);
            _tree.addTreeSelectionListener(new TreeSelectionListener() {

                @Override
                public void valueChanged(TreeSelectionEvent e) {
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
                    runThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            drawPanel();//draw the chart when clicking on a tree leaf
                        }
                    });
                    runThread.start();
                }
            });
            TreeUtils.expandAll(_tree, true);
            _tree.setRootVisible(true);
            _tree.setShowsRootHandles(true);
            scrlPaneTree.setViewportView(_tree);//adding the tree to the scrollpanel
        } catch (java.lang.NullPointerException e) {
        }
    }

    private void drawPanel() {
        try {
            TreePath path = _tree.getSelectionPath();
            if (path != null) {
                String valTitle = getChartTitleFromTree();//method to get the title for the chart
                if (!_tree.getModel().isLeaf(path.getLastPathComponent())) {
                    drawChart(clickTreeSQLQuery(valTitle));//Query method
                } else {//Case is a leaf
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path.getLastPathComponent();
                    String pathParent = String.valueOf(parent.getParent()),
                            pathChild = String.valueOf(path.getLastPathComponent());
                    panChart.removeAll();
                    if (btnTimeEvent.isSelected()) {
                        panChart.add(new EventsLeafTime(pathChild, pathParent));
                        panChart.revalidate();
                        lblTimeSum.setText("");
                    } else {
                        customCodeValue = pathParent;
                        drawChart(new StringBuilder("SELECT e.`EventTime`, e.`Value`, e.`UntilTime` \n").
                                append("FROM eventlog e, customlist c \nWHERE e.HwNo = '").append(machineID).
                                append("' AND e.CustomCode = c.Code \nAND e.Value <> '(null)' \n"
                                        + "AND (e.EventTime BETWEEN '").append(minLogTime).append("' AND '").
                                append(maxLogTime).append("') \n" + "AND c.`Description` = '").
                                append(ConnectDB.firstLetterCapital(pathParent.toLowerCase())).
                                append("' AND e.Value = '").append(pathChild).
                                append("' \nORDER BY e.CustomCode ASC, e.`EventTime` ASC, e.`Value` ASC").toString());//Query method
                    }
                }
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }

    private static String getChartTitleFromTree() {
        StringBuilder title = new StringBuilder();
        TreePath treePath = _tree.getSelectionPath();
        if (treePath != null) {
            if (!_tree.getModel().isLeaf(treePath.getLastPathComponent())) {
                title.append(treePath.getPath()[0]);//Root node title
                if (!_tree.getModel().isLeaf(treePath.getLastPathComponent())
                        && (!treePath.getLastPathComponent().toString().equals(treePath.getPath()[0].toString()))) {
                    for (Object path : treePath.getPath()) {
                        title = new StringBuilder();
                        if (!path.toString().equalsIgnoreCase(machineTitle)) {
                            title.append(path).append(" ");
                        }
                    }
                    return title.substring(0, title.length() - 1).toLowerCase();
                }
            }
        }
        return title.toString();
    }

    private String clickTreeSQLQuery(String leafTitle) {
        if (leafTitle.equalsIgnoreCase(machineTitle)) {//Main node
            //query to retrieve only the description for the entire machine
            if (btnDataEvent.isSelected()) {//case for data events
                customCodeValue = machineTitle;
                if (!EventsDataPanel.isDataGrouped()) {
                    return new StringBuilder("SELECT e.`EventTime`, e.`Value`, e.`UntilTime` \n").
                            append("FROM eventlog e, customlist c\n" + "WHERE e.HwNo = '").
                            append(machineID).append("' AND e.CustomCode = c.Code \nAND e.Value <> '(null)' \n"
                                    + "AND (e.EventTime BETWEEN '").append(minLogTime).append("' AND '").
                            append(maxLogTime).append("')\n" + "AND c.`Description` IN (").
                            append(ConnectDB.retrieveCateria(descriptionSet.toArray())).append(") \n"
                                    + "ORDER BY e.CustomCode ASC, e.`EventTime` ASC, e.`Value` ASC").toString();
                } else {
                    return new StringBuilder("SELECT main.`EventTime`, main.`Value`, \n").
                            append("(SELECT MAX(e.UntilTime) FROM eventlog e \nWHERE e.Value = main.Value AND "
                                    + "e.UntilTime > main.UntilTime) AS NextTime \nFROM eventlog main, customlist c \n"
                                    + "WHERE main.HwNo = '").append(machineID).append("' \nAND main.CustomCode = c.Code \n"
                                    + "AND main.Value <> '(null)' \nAND (main.EventTime BETWEEN '").append(minLogTime).
                            append("' AND '").append(maxLogTime).append("') \nAND c.`Description` IN (").
                            append(ConnectDB.retrieveCateria(descriptionSet.toArray())).append(") \n"
                                    + "GROUP BY main.Value \nORDER BY c.Description ASC, main.Value ASC, main.`EventTime` ASC").toString();
                }
            } else {//case for time events
                return new StringBuilder("SELECT e.`EventTime`, e.`UntilTime`, c.`Description` \n").
                        append("FROM eventlog e, customlist c \nWHERE e.HwNo = '").append(machineID).
                        append("' AND e.CustomCode = c.Code \nAND e.Value <> '(null)'\n"
                                + "AND (e.EventTime BETWEEN '").append(minLogTime).append("' AND '").
                        append(maxLogTime).append("') \n" + "AND c.`Description` IN (").
                        append(ConnectDB.retrieveCateria(descriptionSet.toArray())).append(") \n"
                                + "ORDER BY c.Description ASC, e.`EventTime` ASC").toString();
            }
        } else if (descriptionSet.contains(leafTitle.toLowerCase())) {//Node
            //query to retrieve the specific values bound to a description of a machine
            if (btnDataEvent.isSelected()) {//case for data events
                customCodeValue = ConnectDB.firstLetterCapital(leafTitle.toLowerCase());
                if (!EventsDataPanel.isDataGrouped()) {
                    return new StringBuilder("SELECT e.`EventTime`, e.`Value`, e.`UntilTime` \n").
                            append("FROM eventlog e, customlist c \nWHERE e.HwNo = '").append(machineID).
                            append("' AND e.CustomCode = c.Code \nAND e.Value <> '(null)' \n"
                                    + "AND (e.EventTime BETWEEN '").append(minLogTime).append("' AND '").
                            append(maxLogTime).append("') \n" + "AND c.`Description` = '").
                            append(ConnectDB.firstLetterCapital(leafTitle.toLowerCase())).
                            append("' \nORDER BY e.CustomCode ASC, e.`EventTime` ASC, e.`Value` ASC").toString();
                } else {
                    return new StringBuilder("SELECT main.`EventTime`, main.`Value`, \n").
                            append("(SELECT MAX(e.UntilTime) FROM eventlog e \nWHERE e.Value = main.Value AND "
                                    + "e.UntilTime > main.UntilTime) AS NextTime FROM eventlog main, customlist c \n"
                                    + "WHERE main.HwNo = '").append(machineID).append("' \nAND main.CustomCode = c.Code "
                                    + "\nAND main.Value <> '(null)' \nAND (main.EventTime BETWEEN '").
                            append(minLogTime).append("' AND '").append(maxLogTime).append("') \nAND c.`Description` = '").
                            append(ConnectDB.firstLetterCapital(leafTitle.toLowerCase())).
                            append("' \nGROUP BY main.Value \n"
                                    + "ORDER BY c.Description ASC, main.Value ASC, main.`EventTime` ASC").toString();
                }
            } else {//case for time events
                return new StringBuilder("SELECT e.`EventTime`, e.`UntilTime`, e.`Value` \n").
                        append("FROM eventlog e, customlist c \n" + "WHERE e.HwNo = '").append(machineID).
                        append("' AND e.CustomCode = c.Code \n" + "AND e.Value <> '(null)' \n"
                                + "AND (e.EventTime BETWEEN '").append(minLogTime).append("' AND '").
                        append(maxLogTime).append("') \n" + "AND c.`Description` = '").
                        append(ConnectDB.firstLetterCapital(leafTitle.toLowerCase())).
                        append("' \nORDER BY e.`Value` ASC, e.`EventTime` ASC").toString();
            }
        } else {//Leaf
            return "";
        }
    }

    private void drawChart(String query) throws SQLException {
        lblTimeSum.setText("");
        model = new DefaultChartModel();
        colorsCategoryRange = new CategoryRange<>();
        HL = new ArrayList();
        if (!query.isEmpty()) {
            panChart.removeAll();
            if (btnDataEvent.isSelected()) {
                _thread1 = null;
                JPanel panelShow = new JPanel(new BorderLayout());
                panelShow.setBackground(new Color(255, 255, 255));
                panelShow.setOpaque(true);
                final DefaultOverlayable overlayPanelArea = new DefaultOverlayable(panelShow);

                final InfiniteProgressPanel progressPanel = new InfiniteProgressPanel() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(90, 90);
                    }
                };
                overlayPanelArea.addOverlayComponent(progressPanel);
                progressPanel.stop();
                overlayPanelArea.setOverlayVisible(false);

                if (_thread1 == null || !_thread1.isAlive()) {
                    _thread1 = createThread(progressPanel, panelShow, query);
                    _thread1.start();
                    progressPanel.start();
                }
                if (EventsDataPanel.isLoadDataFinish()) {
                    if (_thread1 != null) {
                        _thread1.interrupt();
                        _thread1 = null;
                        progressPanel.stop();
                    }
                }

                JPanel panel = new JPanel(new BorderLayout());
                panel.add(overlayPanelArea);

                panChart.add(panel);
                panChart.revalidate();
                EventsDataPanel.setLoadDataFinish(false);
            } else {
                ArrayList<String> allDatas = new ArrayList<>();//listarray to get only the date from the database                
                Set<String> setDescOrValue = new TreeSet<>();
                Statement stat = ConnectDB.con.createStatement();
                ConnectDB.res = stat.executeQuery(query);
                double sum;
                max = 0;
                timeFormat = "fictitious value";
                while (ConnectDB.res.next()) {
                    String eventTime = ConnectDB.res.getString(1),//EventTime
                            untilTime = ConnectDB.res.getString(2);//UntilTime
                    setDescOrValue.add(ConnectDB.res.getString(3).toLowerCase());//Description or Value
                    try {
                        double[] diffs = ConnectDB.getTimeDifference(
                                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(eventTime)),
                                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(untilTime)));
                        //for each row in the result set, get the difference in hours
                        //and the Description or Value
                        if (radHour.isSelected()) {
                            timeFormat = "hour";//Time + The Description or Value
                            allDatas.add(new StringBuilder().append(diffs[1]).append(";").
                                    append(ConnectDB.res.getString(3)).toString().toLowerCase());
                        } else if (radMinute.isSelected()) {
                            timeFormat = "min";
                            allDatas.add(new StringBuilder().append(diffs[2]).append(";").
                                    append(ConnectDB.res.getString(3)).toString().toLowerCase());
                        } else {
                            timeFormat = "sec";
                            allDatas.add(new StringBuilder().append(diffs[3]).append(";").
                                    append(ConnectDB.res.getString(3)).toString().toLowerCase());
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                Set<String> setData = null;//define the set depending on the tree path selected
                if (getChartTitleFromTree().equalsIgnoreCase(machineTitle)) {
                    setData = descriptionSet; //set for only the description
                } else if (descriptionSet.contains(getChartTitleFromTree())) {
                    setData = setDescOrValue; //set for only the value
                }
                totalSum = 0d;
                short i = 0;
                for (String setLine : setData) {
                    sum = 0d;
                    for (int j = 0; j < allDatas.size(); j++) {
                        StringTokenizer st = new StringTokenizer(allDatas.get(j), ";");
                        final String time = st.nextToken();//0==
                        final String val = st.nextToken();//1==
//                            String[] ses = allDatas.get(j).split(",");
                        if (setLine.equalsIgnoreCase(val)) {
                            sum += Double.parseDouble(time);//get the sum of time
                        }
                    }
                    HL.add(new ChartCategory((Object) ConnectDB.firstLetterCapital(setLine),
                            new Highlight(new StringBuilder("hl").append(i).toString())));
                    colorsCategoryRange.add((Category<ChartCategory>) HL.get(i));
                    model.addPoint(new ChartPoint((Positionable) HL.get(i), sum));
                    i++;
                    if (max < sum) {
                        max = (int) sum;
                    }
                    totalSum += sum;
                }
                //Create the charts
                lblTimeSum.setText(new StringBuilder("<html>Total parts ").append(timeFormat).
                        append(": <font color=red>").append(ConnectDB.DECIMALFORMAT.format(totalSum)).
                        append("</font>").toString());
                if (!btnBarChart.isEnabled()) {
                    createBarChart();
                } else if (!btnPieChart.isEnabled()) {
                    createPieChart();
                } else if (!btnLineChart.isEnabled()) {
                    createLineChart();
                }
            }
            panChart.repaint();
        } else {
            if (btnDataEvent.isSelected()) {//Events of data
                panChart.removeAll();
                panChart.add(new EventsDataPanel(customCodeValue, query, machineTitle));
                panChart.revalidate();
            }
        }
    }

    private Thread createThread(final InfiniteProgressPanel progressPanel, final JComponent panelShow, final String query) {
        return new Thread() {
            @Override
            public void run() {
                Overlayable overlayable = OverlayableUtils.getOverlayable(panelShow);
                if (overlayable != null) {
                    overlayable.setOverlayVisible(true);
                }
                try {
                    EventsDataPanel edp = new EventsDataPanel(customCodeValue, query, machineTitle);
                    panelShow.add(edp);
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
                if (overlayable != null) {
                    overlayable.setOverlayVisible(false);
                }
            }
        };
    }

    private static Axis chartTitleAxis() {
        Axis yAxis;
        switch (timeFormat) {
            case "hour":
                yAxis = chartTitleAxis(10, "Hr", "Hours");
                break;
            case "min":
                yAxis = chartTitleAxis(500, "Min", "Minutes");
                break;
            case "fictuous value":
                yAxis = chartTitleAxis(300, "Fictitious", "Fictitious value");
                break;
            default:
                yAxis = chartTitleAxis(6000, "Sec", "Seconds");
                break;
        }
        return yAxis;
    }

    private static Axis chartTitleAxis(int num, String smallName, String bigName) {
        Axis yAxis;
        yAxis = new Axis(new NumericRange(0, max + num));
        yAxis.setLabel(new AutoPositionedLabel(new StringBuilder(smallName).append("(s)").toString(), Color.BLACK));
        String title = getChartTitleFromTree().toUpperCase();
//        chartTitle = new StringBuilder(title).append(" (").append(bigName).append(")").toString();
        reportTitle = new StringBuilder("<html>").append(title).append(" <font color=Red><strong>(").
                append(bigName).append(")</strong></font><html>").toString();
        chart.setTitle(new AutoPositionedLabel(reportTitle, Color.BLACK, ConnectDB.TITLEFONT));
        return yAxis;
    }

    public static void createBarChart() {
        panChart.removeAll();
        chart = new Chart(model);
        Axis xAxis = new CategoryAxis(colorsCategoryRange);
        Axis yAxis = chartTitleAxis();
        chart.setBackground(Color.WHITE);
        ChartStyle style = new ChartStyle();
        style.setBarsVisible(true);
        style.setLinesVisible(false);
        style.setPointsVisible(false);
        chart.setStyle(model, style);
        chart.setGridColor(new Color(150, 150, 150));
        chart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f, Color.LIGHT_GRAY));
        chart.setLayout(new BorderLayout());
        chart.setBorder(new EmptyBorder(5, 5, 10, 15));
        chart.setXAxis(xAxis);
        chart.setYAxis(yAxis);
        chart.setHorizontalGridLinesVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.BarChBHLine, true));
        chart.setVerticalGridLinesVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.BarChBVLine, false));
        chart.setRolloverEnabled(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBRollover, false));
//        chart.setShadowVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBShadow, true));
        chart.setSelectionEnabled(true);
        chart.setSelectionShowsOutline(false);
        for (Object HL1 : HL) {
            if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RandomColor, true)) {
                chart.setHighlightStyle(((ChartCategory) HL1).getHighlight(), new ChartStyle(randomColor.randomColor()).withBars());
            } else {
                chart.setHighlightStyle(((ChartCategory) HL1).getHighlight(), new ChartStyle(
                        ConnectDB.getColorFromKey(ConnectDB.pref.get(StatKeyFactory.ChartFeatures.CBColor2, "0, 204, 0"))).withBars());
            }
        }
        chart.addMousePanner().addMouseZoomer();
        if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RB3D, false)) {
            CylinderBarRenderer barR = new CylinderBarRenderer();
            barR.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            barR.setZeroHeightBarsVisible(true);
            barR.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
            chart.setBarRenderer(barR);
            chart.getXAxis().setAxisRenderer(new Axis3DRenderer());
        } else if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBRaised, false)) {
            RaisedBarRenderer barRenderer = new RaisedBarRenderer();
            barRenderer.setZeroHeightBarsVisible(true);
            barRenderer.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
            barRenderer.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            chart.setBarRenderer(barRenderer);
            chart.getXAxis().setAxisRenderer(new NoAxisRenderer());
        } else {
            DefaultBarRenderer barRenderer = new DefaultBarRenderer();
            barRenderer.setZeroHeightBarsVisible(true);
            barRenderer.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
            barRenderer.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            chart.setBarRenderer(barRenderer);
            chart.getXAxis().setAxisRenderer(new NoAxisRenderer());
        }
        chart.getXAxis().setTicksVisible(true);
        chart.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
        chart.setBarGap(10);
        panner = new MouseDragPanner(chart, true, false);
        panner.setContinuous(true);
        chart.addMouseListener(panner);
        chart.addMouseMotionListener(panner);
        chart.addDrawable(new PanIndicator(chart, PanIndicator.Placement.LEFT));
        chart.addDrawable(new PanIndicator(chart, PanIndicator.Placement.RIGHT));
        panChart.add(chart);
        panChart.revalidate();
    }

    public static void createPieChart() {
        panChart.removeAll();
        JPanel legendPanel = new JPanel();
        JToggleButton btnPercentage = new JToggleButton("<html><font color=black><strong>(%) Percentage</strong></font>");
        btnPercentage.setFocusable(false);
        btnPercentage.setToolTipText(new StringBuilder("Percentage calculated with proportion of ").
                append(ConnectDB.DECIMALFORMAT.format(totalSum)).append(" as the total sum of values.").toString());
        stylePieChart = new ChartStyle();
        chart = new Chart(model);
        chart.setBarGap(10);
        chartTitleAxis();//create only the title
        chart.setBackground(Color.WHITE);
        chart.setBorder(new EmptyBorder(5, 5, 10, 15));
        if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RB3D, false)) {
            chart.setPieSegmentRenderer(new Pie3DRenderer());
            Pie3DRenderer pSeg = (Pie3DRenderer) chart.getPieSegmentRenderer();
            pSeg.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            pSeg.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
        } else if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBRaised, false)) {
            chart.setPieSegmentRenderer(new RaisedPieSegmentRenderer());
            RaisedPieSegmentRenderer pSeg = (RaisedPieSegmentRenderer) chart.getPieSegmentRenderer();
            pSeg.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            pSeg.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
        } else {
            chart.setPieSegmentRenderer(new DefaultPieSegmentRenderer());
            DefaultPieSegmentRenderer pSeg = (DefaultPieSegmentRenderer) chart.getPieSegmentRenderer();
            pSeg.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
            pSeg.setOutlineWidth(ConnectDB.OUTLINEWIDTH);
            LinePieLabelRenderer segmentLabeler = new LinePieLabelRenderer();
            segmentLabeler.setLabelFont(new Font("Cooper Black", Font.PLAIN, 12));
            pSeg.setPieLabelRenderer(segmentLabeler);
        }
        stylePieChart.setPieOffsetAngle(ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.SLAngle, 0));
        chart.setChartType(ChartType.PIE);
        chart.setStyle(model, stylePieChart);
        chart.setSelectionEnabled(true);
        chart.setRolloverEnabled(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBRollover, false));
        chart.setSelectionShowsExplodedSegments(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBExplodedSegment, false));

        for (Object HL1 : HL) {
            chart.setHighlightStyle(((ChartCategory) HL1).getHighlight(), new ChartStyle(randomColor.randomColor()).withBars());
        }
        btnPercentage.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    new Thread() {

                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    chart.getPieSegmentRenderer().setPointLabeler(new PointLabeler() {

                                        @Override
                                        public String getDisplayText(Chartable p) {
                                            Category text = (Category) p.getX();
                                            double value = p.getY().position();
                                            return String.format("%s (%.2f %s)", text.getName(),
                                                    (value / totalSum) * 100, "%");
                                        }
                                    });
                                }
                            });
                        }
                    }.start();
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    new Thread() {

                        @Override
                        public void run() {
                            AbstractPieSegmentRenderer renderer = (AbstractPieSegmentRenderer) chart.getPieSegmentRenderer();
                            PieLabelRenderer labelRenderer;
                            if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBLineLabel, true)) {
                                labelRenderer = new LinePieLabelRenderer();
                                ((AbstractPieLabelRenderer) labelRenderer).setLabelFont(new Font("Cooper Black", Font.PLAIN, 12));
                            } else if (ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.RBSimpleLabel, false)) {
                                labelRenderer = new SimplePieLabelRenderer();
                            } else {
                                labelRenderer = null;
                            }
                            renderer.setPieLabelRenderer(labelRenderer);
                        }
                    }.start();
                }
            }
        });
//        chart.setShadowVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBShadow, true));
        chart.setSelectionShowsOutline(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBSelectionOutline, false));
        legendPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        legendPanel.setOpaque(true);
        legendPanel.setBackground(Color.WHITE);
        legendPanel.add(btnPercentage);
        panChart.add(legendPanel, BorderLayout.PAGE_START);
        panChart.add(chart);
        panChart.revalidate();
    }

    public static void createLineChart() {
        panChart.removeAll();
        chart = new Chart();
        Axis xAxis = new CategoryAxis(colorsCategoryRange);
        Axis yAxis = chartTitleAxis();
        ChartStyle style = new ChartStyle(ConnectDB.getColorFromKey(ConnectDB.pref.get(StatKeyFactory.ChartFeatures.CBColor, "255, 0, 0")), true, true);
        style.setLineWidth(ConnectDB.pref.getInt(StatKeyFactory.ChartFeatures.SPLineWidth, 2));
        chart.setBackground(Color.WHITE);
        chart.setBorder(new EmptyBorder(5, 5, 10, 15));
        chart.setGridColor(new Color(150, 150, 150));
        style.setPointSize(12);
        chart.addModel(model, style).setPointRenderer(new SphericalPointRenderer());
        chart.addMousePanner().addMouseZoomer();
        chart.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    rollover(e);
                } catch (Exception ex) {
                }
            }

            private void rollover(MouseEvent e) {
                Point p = e.getPoint();
                PointSelection selection = chart.nearestPoint(p, model);
                Chartable selected = selection.getSelected();
                Point2D selectedCoords = new Point2D.Double(selected.getX().position(), selected.getY().position());
                Point dp = chart.calculatePixelPoint(selectedCoords);
                if (p.distance(dp) < 20) {
                    ChartCategory<?> x = (ChartCategory<?>) selected.getX();
                    chart.setToolTipText(String.format("%s : %.0f", x.getName(), selected.getY().position()));
                } else {
                    chart.setToolTipText(null);
                }
                chart.repaint();
            }
        });
        chart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f, Color.LIGHT_GRAY));
        chart.setXAxis(xAxis);
        chart.setYAxis(yAxis);
        chart.setHorizontalGridLinesVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.LineChBHLine, true));
        chart.setVerticalGridLinesVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.LineChBVLine, true));
        chart.setSelectionEnabled(true);
        chart.setRolloverEnabled(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBRollover, false));
//        chart.setShadowVisible(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.ChBShadow, true));
        chart.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
        chart.setBarGap(10);
        panner = new MouseDragPanner(chart, true, false);
        panner.setContinuous(true);
        chart.addMouseListener(panner);
        chart.addMouseMotionListener(panner);
        chart.addDrawable(new PanIndicator(chart, PanIndicator.Placement.LEFT));
        chart.addDrawable(new PanIndicator(chart, PanIndicator.Placement.RIGHT));
        panChart.add(chart);
        panChart.revalidate();
    }

    private void enableResetButton() {
        if (cmbMachineTitle.getSelectedIndex() > 0 || cmbValue.getSelectedObjects().length != 0
                || cmbDescription.getSelectedObjects().length != 0
                || panChart.getComponentCount() > 0) {
            btnReset.setEnabled(true);
        } else {
            btnReset.setEnabled(false);
        }
    }

    private void setPropertiesSaved() {
        Calendar working = ((Calendar) ConnectDB.CALENDAR.clone());
        working.add(Calendar.DAY_OF_YEAR, -1);
        cmbEFrom.setDate(working.getTime());
        cmbETo.setDate(ConnectDB.CALENDAR.getTime());
    }

    private void loadComboBox() throws SQLException {
        catMachine = false;
        cmbMachineTitle.removeAllItems();
        cmbMachineTitle.addItem(" ");
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_HARDWARE)) {
            ps.setInt(1, 0);//not selecting the SYSTEM as hardware
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                cmbMachineTitle.addItem(ConnectDB.res.getString("Machine"));
            }
        }
        catMachine = true;
    }

//    private int getConfigNo() throws SQLException {
//        int configNo = -1;
//        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_CONFIGNO)) {
//            ps.setString(1, "Rate");
//            ps.setString(2, machineTitle);
//            ConnectDB.res = ps.executeQuery();
//            while (ConnectDB.res.next()) {
//                configNo = ConnectDB.res.getInt(1);
//            }
//        }
//        return configNo;
//    }
    public static class Machine {

        private String m_machName;
        private final String m_machDomain;

        Machine(String machName, String machDomain) {
            m_machName = machName;
            m_machDomain = machDomain;
        }

        public String getMachDomain() {
            return m_machDomain;
        }

        public void setMacName(String machName) {
            m_machName = machName;
        }

        @Override
        public String toString() {
            return m_machName;
        }
    }

    public static void main(String[] agrs) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        try {
            final JFrame frame = new JFrame("Smartfactory Events & Downtime Statistics Report 1.0");
            frame.setSize(1000, 700);
            frame.setContentPane(new EventsStatistic(null));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.tree.QuickTreeFilterField _field;
    public static javax.swing.JButton btnBarChart;
    private javax.swing.JButton btnClipboard;
    private javax.swing.JToggleButton btnDataEvent;
    public static javax.swing.JButton btnLineChart;
    private javax.swing.JButton btnOEECalculation;
    public static javax.swing.JButton btnPieChart;
    private javax.swing.JButton btnPrintChart;
    public static javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSetting;
    private javax.swing.JToggleButton btnTimeEvent;
    private javax.swing.JButton btnViewData;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JCheckBox chkFrom;
    public static javax.swing.JCheckBox chkTo;
    private com.jidesoft.combobox.CheckBoxListComboBox cmbDescription;
    public static com.jidesoft.combobox.DateComboBox cmbEFrom;
    public static com.jidesoft.combobox.DateComboBox cmbETo;
    public static org.jdesktop.swingx.JXComboBox cmbMachineTitle;
    private org.jdesktop.swingx.JXComboBox cmbProductionType;
    private com.jidesoft.combobox.CheckBoxListComboBox cmbValue;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar7;
    private javax.swing.JToolBar jToolBar8;
    private javax.swing.JToolBar jToolBar9;
    private javax.swing.JLabel lblEventTo;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblProductionRate;
    private javax.swing.JLabel lblTimeSum;
    private javax.swing.JLabel lblTo;
    private static javax.swing.JPanel panChart;
    private javax.swing.JRadioButton radHour;
    private javax.swing.JRadioButton radMinute;
    private javax.swing.JRadioButton radSecond;
    private javax.swing.JScrollPane scrlPaneTree;
    private javax.swing.JSpinner spProductionRate;
    // End of variables declaration//GEN-END:variables
    private static int machineID = -1;
    private static double totalSum = 0d;
    private Date eventFrom, eventTo;
    private boolean skipFirstMessage = true,//variables for the dates;
            catMachine, catDescription;//check if all the machineTitle title are loaded
    private static int max = 0;
    private static RandomColor randomColor = null;
    private static ChartStyle stylePieChart = null;
    private static ArrayList HL = null;
    private static DefaultChartModel model = null;
    private static Chart chart;
    private static CategoryRange<ChartCategory> colorsCategoryRange = null;
    private static String minLogTime = null, maxLogTime = null,//also used in the EventsHierarchicalTable
            timeFormat = null, reportTitle = "", machineTitle = null, customCodeValue = "";
    private static MouseDragPanner panner;
    private static JTree _tree;
    private static final Set<String> descriptionSet = new TreeSet<>();
    private EventsHierarchicalTable eht = null;
    private final JFrame _parent;
    private Thread runThread, _thread1;
    private final String EMPTYEVENTS = "***NO EVENT RETRIEVED***";
}
