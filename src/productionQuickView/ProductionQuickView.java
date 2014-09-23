package productionQuickView;

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
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.SearchableUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;
import tableModel.TableModelRate;
import tableModel.TableModelTotal;
import target.TargetInsert;

/**
 *
 * @author Victor Kadiata
 */
public class ProductionQuickView extends javax.swing.JPanel {

    public static boolean isTotProdSelected() {
        return totProdSelected;
    }

    public static void setTotProdSelected(boolean totProdSelected) {
        ProductionQuickView.totProdSelected = totProdSelected;
    }

    public ProductionQuickView(JFrame parent) throws SQLException {
        this._parent = parent;
        ball = new Ball(this);
        initComponents();
        this.autoFill("cumulative");//Fill the checkboxlist with machines
        this.setDateComponent();
        bslQuickView.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bslQuickView.setForeground(Color.GRAY);
        bslQuickView.setFont(new Font("Tahoma", Font.PLAIN, 10));
        bslQuickView.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        bslQuickView.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        bslQuickView.setBusy(true);
        bslQuickView.setVisible(false);
        tbpPanDetails.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
                try {
                    if (catProdTab) {
                        lblProduction.setEnabled(true);
                        btnPerHour.setEnabled(true);
                        btnPerMin.setEnabled(true);
                        switch (tbpPanDetails.getTitleAt(index)) {
                            case "Production Rate":
                                showActualTotalValues = false;
                                btnProductionRate.setEnabled(false);
                                btnTotalProduction.setEnabled(true);
                                lblProduction.setText("Production Rate:");
                                btnPerHour.setText("p/hr");
                                btnPerMin.setText("p/min");
                                btnPerMin.setSelected(true);
//                                btnProductionRateActionPerformed(null);
                                break;
                            case "Total Production":
                                showActualTotalValues = true;
                                rateType = "";
                                btnProductionRate.setEnabled(true);
                                btnTotalProduction.setEnabled(false);
                                lblProduction.setText("Total Production:");
                                btnPerMin.setText("Average parts");
                                btnPerHour.setText("Actual parts");
                                btnPerHour.setSelected(true);
//                                btnTotalProductionActionPerformed(null);
                                totProdSelected = true;
                                break;
                        }
                        skipRunListSelection = false;
                        btnRefreshTableActionPerformed(null);
                    }
//                    System.out.println("Tab changed to: " + tbpPanDetails.getTitleAt(index));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                    btnProductionRate.setEnabled(true);
                    btnTotalProduction.setEnabled(true);
                    lblProduction.setEnabled(false);
                    btnPerHour.setEnabled(false);
                    btnPerMin.setEnabled(false);
                }
            }
        });
        cblMachine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int c = e.getClickCount();
                if (c == 2) {
                    firePopup(e);
                }
            }
        });
        cblMachine.getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(final ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Thread worker = new Thread() {
                        @Override
                        public void run() {
                            if (catCreateTab) {
                                SwingUtilities.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            runListSelection();
                                        } catch (SQLException ex) {
                                            ConnectDB.catchSQLException(ex);
                                        }
                                    }
                                });
                            }
                        }
                    };
                    worker.start();
                }
            }
        });
        Timer time = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selected = cblMachine.getCheckBoxListSelectedIndices();
                SortableTable _sortableTable = null;
                if (selected.length <= 0) {
                    btnExportExcelCsv.setEnabled(false);
                    bslQuickView.setVisible(false);
                    try {
                        if (!btnProductionRate.isEnabled()) {
                            _sortableTable = ProductionQuickView.tableRate;
                        } else if (!btnTotalProduction.isEnabled()) {
                            _sortableTable = ProductionQuickView.tableTotal;
                        }
                        cleanTable(_sortableTable);
                    } catch (Exception ex) {
                    }
                } else {
                    btnExportExcelCsv.setEnabled(true);
                }
            }
        });
        time.start();
        this.createTabbedPanel(new JPanel(), "Total Production");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        btnLoadMachines = new javax.swing.JButton();
        btnTarget = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnProductionRate = new javax.swing.JButton();
        btnTotalProduction = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnExportExcelCsv = new javax.swing.JButton();
        btnRefreshTable = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cblMachine = new com.jidesoft.swing.CheckBoxList();
        lblMachine = new javax.swing.JLabel();
        btnRefreshMachine = new com.jidesoft.swing.JideButton();
        panProductionsOptions = new javax.swing.JPanel();
        cmbDay = new com.jidesoft.combobox.DateSpinnerComboBox();
        lblProduction = new javax.swing.JLabel();
        chkDay = new javax.swing.JCheckBox();
        lblMessage = new javax.swing.JLabel();
        bslQuickView = new org.jdesktop.swingx.JXBusyLabel();
        btnPerMin = new javax.swing.JToggleButton();
        btnPerHour = new javax.swing.JToggleButton();
        tbpPanDetails = new com.jidesoft.swing.JideTabbedPane();
        panColor = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D) g;
                gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ball.paint(gd);
            }
        };
        panTarget = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        _listTarget = new com.jidesoft.navigation.NavigationList(listModelTarget);
        lblTargetTime = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9), new java.awt.Color(0, 0, 204))); // NOI18N
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnLoadMachines.setBackground(new java.awt.Color(255, 255, 255));
        btnLoadMachines.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLoadMachines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/machine16x16_1.png"))); // NOI18N
        btnLoadMachines.setText("Load Machine & Target");
        btnLoadMachines.setToolTipText("Show list of machine and their targets");
        btnLoadMachines.setFocusable(false);
        btnLoadMachines.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLoadMachines.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLoadMachines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadMachinesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLoadMachines);

        btnTarget.setBackground(new java.awt.Color(255, 255, 255));
        btnTarget.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/target_3.png"))); // NOI18N
        btnTarget.setText("Add Target");
        btnTarget.setToolTipText("Modify/Add target to a particular machine ");
        btnTarget.setEnabled(false);
        btnTarget.setFocusable(false);
        btnTarget.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTarget.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTargetActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTarget);
        jToolBar1.add(jSeparator1);

        btnProductionRate.setBackground(new java.awt.Color(255, 255, 255));
        btnProductionRate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductionRate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/rate_4.png"))); // NOI18N
        btnProductionRate.setText("Production Rate");
        btnProductionRate.setToolTipText("Show production rate data");
        btnProductionRate.setFocusable(false);
        btnProductionRate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductionRate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductionRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductionRateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProductionRate);

        btnTotalProduction.setBackground(new java.awt.Color(255, 255, 255));
        btnTotalProduction.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnTotalProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/rate_1.png"))); // NOI18N
        btnTotalProduction.setText("Total Production");
        btnTotalProduction.setToolTipText("Show cumulative production data");
        btnTotalProduction.setFocusable(false);
        btnTotalProduction.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTotalProduction.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTotalProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalProductionActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTotalProduction);
        jToolBar1.add(jSeparator2);

        btnExportExcelCsv.setBackground(new java.awt.Color(255, 255, 255));
        btnExportExcelCsv.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExportExcelCsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/excel_csv_1.png"))); // NOI18N
        btnExportExcelCsv.setText("Excel / CSV");
        btnExportExcelCsv.setToolTipText("Export to excel/csv file");
        btnExportExcelCsv.setFocusable(false);
        btnExportExcelCsv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelCsv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportExcelCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelCsvActionPerformed(evt);
            }
        });
        jToolBar1.add(btnExportExcelCsv);

        btnRefreshTable.setBackground(new java.awt.Color(255, 255, 255));
        btnRefreshTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRefreshTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/gnome_view_refresh16.png"))); // NOI18N
        btnRefreshTable.setText("Refresh");
        btnRefreshTable.setToolTipText("Refresh the table");
        btnRefreshTable.setFocusable(false);
        btnRefreshTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefreshTable.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefreshTable);

        cblMachine.setFocusable(false);
        cblMachine.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cblMachine.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                cblMachineValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(cblMachine);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblMachine.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMachine.setForeground(new java.awt.Color(0, 204, 0));
        lblMachine.setText("List of machines (0)");

        btnRefreshMachine.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnRefreshMachine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/refresh16.png"))); // NOI18N
        btnRefreshMachine.setToolTipText("Refresh the machine list and target");
        btnRefreshMachine.setFocusable(false);
        btnRefreshMachine.setOpaque(true);
        btnRefreshMachine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMachineActionPerformed(evt);
            }
        });

        panProductionsOptions.setBackground(new java.awt.Color(255, 255, 255));
        panProductionsOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Production Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15), new java.awt.Color(0, 0, 204))); // NOI18N

        cmbDay.setShowNoneButton(false);
        cmbDay.setShowOKButton(true);
        cmbDay.setDate(Calendar.getInstance().getTime());
        cmbDay.setFocusable(false);
        cmbDay.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbDay.setRequestFocusEnabled(false);
        cmbDay.setTimeDisplayed(true);
        cmbDay.setTimeFormat("HH:mm:ss");
        cmbDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDayActionPerformed(evt);
            }
        });

        lblProduction.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblProduction.setText("Production Rate:");

        chkDay.setBackground(new java.awt.Color(255, 255, 255));
        chkDay.setSelected(true);
        chkDay.setText("End Time:");
        chkDay.setFocusable(false);
        chkDay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkDayItemStateChanged(evt);
            }
        });

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(204, 0, 0));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        bslQuickView.setBusy(true);
        bslQuickView.setDirection(org.jdesktop.swingx.painter.BusyPainter.Direction.RIGHT);
        bslQuickView.setFocusable(false);
        bslQuickView.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        btnPerMin.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(btnPerMin);
        btnPerMin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPerMin.setSelected(true);
        btnPerMin.setText("p/min");
        btnPerMin.setFocusable(false);
        btnPerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerMinActionPerformed(evt);
            }
        });

        btnPerHour.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(btnPerHour);
        btnPerHour.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPerHour.setText("p/hr");
        btnPerHour.setFocusable(false);
        btnPerHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerHourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panProductionsOptionsLayout = new javax.swing.GroupLayout(panProductionsOptions);
        panProductionsOptions.setLayout(panProductionsOptionsLayout);
        panProductionsOptionsLayout.setHorizontalGroup(
            panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProductionsOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panProductionsOptionsLayout.createSequentialGroup()
                        .addComponent(chkDay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panProductionsOptionsLayout.createSequentialGroup()
                        .addComponent(lblProduction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPerMin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPerHour)))
                .addGroup(panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panProductionsOptionsLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panProductionsOptionsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bslQuickView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panProductionsOptionsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPerHour, btnPerMin});

        panProductionsOptionsLayout.setVerticalGroup(
            panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProductionsOptionsLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblProduction)
                    .addComponent(btnPerMin)
                    .addComponent(btnPerHour)
                    .addComponent(bslQuickView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblMessage)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkDay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panProductionsOptionsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbDay, lblMessage});

        tbpPanDetails.setBoldActiveTab(true);
        tbpPanDetails.setFocusable(false);
        tbpPanDetails.setShowCloseButtonOnMouseOver(true);
        tbpPanDetails.setShowCloseButtonOnSelectedTab(true);
        tbpPanDetails.setShowCloseButtonOnTab(true);

        panColor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panColorLayout = new javax.swing.GroupLayout(panColor);
        panColor.setLayout(panColorLayout);
        panColorLayout.setHorizontalGroup(
            panColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        panColorLayout.setVerticalGroup(
            panColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );

        panTarget.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setViewportView(_listTarget);

        javax.swing.GroupLayout panTargetLayout = new javax.swing.GroupLayout(panTarget);
        panTarget.setLayout(panTargetLayout);
        panTargetLayout.setHorizontalGroup(
            panTargetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panTargetLayout.setVerticalGroup(
            panTargetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        lblTargetTime.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lblTargetTime.setText("<html>Production<br> Target (/hr)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(panColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMachine, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTargetTime, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(panTarget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panProductionsOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panProductionsOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblMachine, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTargetTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panTarget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshMachineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMachineActionPerformed
        try {
            skipRunListSelection = true;
            refreshMachine_Targets();
            chkDayItemStateChanged(null);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnRefreshMachineActionPerformed

    private void chkDayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkDayItemStateChanged
        if (chkDay.isSelected()) {
            cmbDay.setEnabled(true);
            cmbDay.setDate(Calendar.getInstance().getTime());
        } else {
            cmbDay.setEnabled(false);
        }
    }//GEN-LAST:event_chkDayItemStateChanged

    private void btnProductionRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductionRateActionPerformed
        Thread rateThread = new Thread() {

            @Override
            public void run() {
                try {
                    skipRunListSelection = true;
                    btnProductionRate.setEnabled(false);
                    if (!skipRefresh) {
                        btnTotalProduction.setEnabled(true);
                        refreshMachine_Targets();
                    }
                    createTabbedPanel(new JPanel(), "Production Rate");
                    skipRefresh = false;
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        };
        rateThread.start();
    }//GEN-LAST:event_btnProductionRateActionPerformed

    private void btnTotalProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalProductionActionPerformed
        Thread totalThread = new Thread() {

            @Override
            public void run() {
                try {
                    skipRunListSelection = true;
                    btnTotalProduction.setEnabled(false);
                    if (!skipRefresh) {
                        btnProductionRate.setEnabled(true);
                        refreshMachine_Targets();
                    }
                    createTabbedPanel(new JPanel(), "Total Production");
                    skipRefresh = false;
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        };
        totalThread.start();
    }//GEN-LAST:event_btnTotalProductionActionPerformed

    private void btnTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTargetActionPerformed
        new TargetSingle(_parent, true).setVisible(true);
    }//GEN-LAST:event_btnTargetActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
            try {
                if (!skipRunListSelection) {
                    runListSelection();
                }
                skipRunListSelection = true;
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        } else {
            try {
                refreshMachine_Targets();
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    private void btnLoadMachinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadMachinesActionPerformed
        Thread threadLoadMachine = new Thread() {

            @Override
            public void run() {
                try {
                    new TargetInsert(_parent, true).setVisible(true);
                    String targetTime = ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour");
                    switch (targetTime) {
                        case "second":
                            lblTargetTime.setText("<html>Production<br> Target (/sec)");
                            break;
                        case "minute":
                            lblTargetTime.setText("<html>Production<br> Target (/min)");
                            break;
                        default:
                            lblTargetTime.setText("<html>Production<br> Target (/hr)");
                            break;
                    }
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
                if (TargetInsert.isAnyChangeOccured()) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                refreshMachine_Targets();
                            } catch (SQLException ex) {
                                ConnectDB.catchSQLException(ex);
                            }
                        }
                    });
                }
            }
        };
        threadLoadMachine.start();
    }//GEN-LAST:event_btnLoadMachinesActionPerformed

    private void cblMachineValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cblMachineValueChanged
        String s = String.valueOf(cblMachine.getSelectedValue());
        if (s.isEmpty() || "(All)".equals(s)) {
            btnTarget.setEnabled(false);
        } else {
            btnTarget.setEnabled(true);
        }
    }//GEN-LAST:event_cblMachineValueChanged

    private void btnExportExcelCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelCsvActionPerformed
        try {
            SortableTable _sortableTable = null;
            if (!btnProductionRate.isEnabled()) {
                _sortableTable = ProductionQuickView.tableRate;
            } else if (!btnTotalProduction.isEnabled()) {
                _sortableTable = ProductionQuickView.tableTotal;
            }
            _sortableTable.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT,
                    HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
            if (!HssfTableUtils.isHssfInstalled()) {
                JOptionPane.showMessageDialog((Component) evt.getSource(), "Export to Excel feature is disabled "
                        + "because POI-HSSF jar is missing in the classpath.");
                return;
            }
            //Method to write in excel file
            this.outputToExcel(evt, _sortableTable);
        } catch (HeadlessException e) {
        }
    }//GEN-LAST:event_btnExportExcelCsvActionPerformed

    private void btnPerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerMinActionPerformed
        skipRefresh = true;
        showActualTotalValues = false;
        if (evt.getActionCommand().equals("p/min")) {
            btnProductionRateActionPerformed(evt);
            tableRate.revalidate();
        } else {//Average parts
            btnTotalProductionActionPerformed(evt);
            tableTotal.revalidate();
        }
        if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
            try {
                runListSelection();
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
    }//GEN-LAST:event_btnPerMinActionPerformed

    private void btnPerHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerHourActionPerformed
        skipRefresh = true;
        if (evt.getActionCommand().equals("p/hr")) {
            btnProductionRateActionPerformed(evt);
            tableRate.revalidate();
        } else {//Total parts
            showActualTotalValues = true;
            btnTotalProductionActionPerformed(evt);
            tableTotal.revalidate();
        }
        if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
            try {
                runListSelection();
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
    }//GEN-LAST:event_btnPerHourActionPerformed

    private void cmbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDayActionPerformed
        if (catDate) {
            skipRunListSelection = false;
            cmbDay.setPopupVisible(false);
            btnRefreshTableActionPerformed(evt);
        }
    }//GEN-LAST:event_cmbDayActionPerformed

    synchronized private void refreshMachine_Targets() throws SQLException {
        catProdTab = false;
        int[] selected = cblMachine.getCheckBoxListSelectedIndices();
        if (!btnProductionRate.isEnabled()) {
            autoFill("Rate");
        } else if (!btnTotalProduction.isEnabled()) {
            autoFill("Cumulative");
        }
        cblMachine.setCheckBoxListSelectedIndices(selected);
        catProdTab = true;
    }

    private void runListSelection() throws SQLException {
        if (fillTableThread != null) {
            while (fillTableThread.isAlive()) {
                try {
                    bslQuickView.setVisible(true);
                    bslQuickView.setBusy(true);
                    bslQuickView.setText("Waiting for the current thread to finish");
                    fillTableThread.interrupt();
                    fillTableThread.join(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProductionQuickView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        panProductionsOptions.requestFocus();
        SortableTable _sortableTable = null;
        if (!btnProductionRate.isEnabled()) {
            showActualTotalValues = false;
            _sortableTable = ProductionQuickView.tableRate;
        } else if (!btnTotalProduction.isEnabled()) {
            if (showActualTotalValues) {
                showActualTotalValues = true;
            }
            _sortableTable = ProductionQuickView.tableTotal;
        }
        if (cblMachine.getCheckBoxListSelectedValues().length <= 0) {
            cleanTable(_sortableTable);
        } else {
            if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
                if (!btnProductionRate.isEnabled()) {
                    prodType = "rate";
                } else if (!btnTotalProduction.isEnabled()) {
                    prodType = "cumulative";
                }
                final ArrayList<String> listMachine_ConfigNo = new ArrayList();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(new StringBuilder("SELECT DISTINCT "
                        + "h.Machine, c.ConfigNo \n").append("FROM configuration c, hardware h \n"
                                + "WHERE h.HwNo = c.HwNo \n"
                                + "AND c.AvMinMax =? \n").append(criteriaSearch()).
                        append(" AND c.Active =1 ORDER BY h.HwNo ASC").toString())) {
                    ps.setString(1, prodType);
                    ResultSet result_Set = ps.executeQuery();
                    while (result_Set.next()) {
                        listMachine_ConfigNo.add(new StringBuilder(result_Set.getString(1)).append(";").
                                append(result_Set.getString(2)).toString());
                    }
                }
                fillTableThread = new Thread() {

                    @Override
                    public void run() {
                        try {
                            fillTable(listMachine_ConfigNo, prodType);
                        } catch (SQLException ex) {
                            ConnectDB.catchSQLException(ex);
                        }
                    }
                };
                fillTableThread.start();
            }
        }
    }

    private String criteriaSearch() {
        StringBuilder machines = new StringBuilder("");
        if (cblMachine.getCheckBoxListSelectedValues().length != 0) {
            machines = new StringBuilder(" AND (h.Machine IN (").
                    append(ConnectDB.retrieveCateria(cblMachine.getCheckBoxListSelectedValues())).append("))");
        }
        return machines.toString();
    }

    synchronized private void fillTable(final ArrayList<String> listMachines, String configurationType)
            throws SQLException {
        this.requestFocus();
        log_TimeData_List = new ArrayList<>();
        today = cmbDay.getDate();
        nbRow = 1;
        if (!btnProductionRate.isEnabled()) {
            cleanTable(ProductionQuickView.tableRate);
        } else if (!btnTotalProduction.isEnabled()) {
            cleanTable(ProductionQuickView.tableTotal);
        }
        try {
            for (String machine : listMachines) {
                String query = "SELECT d.LogTime AS 'Time', d.LogData \n"
                        + "FROM datalog d \n"
                        + "WHERE d.ConfigNo =? \n"
                        + "AND d.LogTime <=? \n"
                        + "ORDER BY 'Time' ASC";
                bslQuickView.setVisible(true);
                bslQuickView.setBusy(true);

                StringTokenizer stringTokenizer = new StringTokenizer(machine, ";");
                String machineTitle = stringTokenizer.nextToken();//The Machine name
                int configNo = Integer.parseInt(stringTokenizer.nextToken());//The ConfigNo

                bslQuickView.setText(new StringBuilder("Processing ").append(machineTitle).append(" ... ").toString());
                if (configurationType.equals("rate")) {
                    if (btnPerHour.isSelected()) {
                        query = "SELECT d.LogTime AS 'Time', (d.LogData * 60) \n"
                                + "FROM datalog d \n"
                                + "WHERE d.ConfigNo =? \n"
                                + "AND d.LogTime <=? \n"
                                + "ORDER BY 'Time' ASC";
                    }
                    //Rate query (0)
                    runQuery(query, tableRate, machineTitle, configNo, 0);
                } else {
                    //Cumulative query (1)
                    runQuery(query, tableTotal, machineTitle, configNo, 1);
                }
//                Thread.sleep(500);
            }
            bslQuickView.setVisible(false);
        } catch (NumberFormatException e) {
        }
    }

    synchronized private void runQuery(String query, SortableTable table, String machineName, int configNo, int pan)
            throws SQLException {
        logTimeList = new ArrayList<>();
        logDataList = new ArrayList<>();
        try {
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                ps.setInt(1, configNo);
                ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(today));
                resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    if (pan == 1) {//Pane case for the total production
                        logTimeList.add(resultSet.getString(1));
                        logDataList.add(resultSet.getInt(2));
                    } else {//Pane case for the rate production
                        log_TimeData_List.add(new StringBuilder(resultSet.getString(1)).append(";").
                                append(resultSet.getDouble(2)).toString());//LogTime and LogData
                    }
                }
            }
            if (pan == 1) {//Total production case
                log_TimeData_List = getSubtractedValues(logDataList, query, machineName);
            }
            if (table != null) {
                if (nbRow > table.getModel().getRowCount()) {
                    if (pan == 0) {
                        _modelRate.addNewRow();
                    } else {
                        _modelTotal.addNewRow();
                    }
                }
            }
            int listSize = log_TimeData_List.size();
            if (listSize > 0) {
                table.setValueAt(machineName, nbRow - 1, 0);
//                String current = alTimeValue.get(listSize - 1).split(";")[1];
                table.setValueAt(calculateLogData(log_TimeData_List, "current"), nbRow - 1, 1);
//                st.setValueAt(Double.valueOf(ConnectDB.DECIMALFORMAT.format(Double.parseDouble(current))), nbRow - 1, 1);
                table.setValueAt(calculateLogData(log_TimeData_List, "lasthour"), nbRow - 1, 2);
                table.setValueAt(calculateLogData(log_TimeData_List, "daily"), nbRow - 1, 3);
                table.setValueAt(calculateLogData(log_TimeData_List, "weekly"), nbRow - 1, 4);
                table.setValueAt(calculateLogData(log_TimeData_List, "mtd"), nbRow - 1, 5);
                table.setValueAt(calculateLogData(log_TimeData_List, "ytd"), nbRow - 1, 6);
                nbRow++;
            }
        } catch (NumberFormatException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    synchronized private double calculateLogData(ArrayList<String> list, String type) throws ParseException {
        Calendar now = Calendar.getInstance(), working;
        now.setTime(today);//set the calendar to the chosen date and time
        Double averageSum = 0d;
        switch (type) {
            case "current":
                int listSize = list.size();
//                System.out.println(listSize);
                String[] data = log_TimeData_List.get(listSize - 1).split(";");
                working = (Calendar) now.clone();
                double currentData = Double.parseDouble(data[1]);
                if (ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(lastDayDBData)).compareTo(working.getTime()) > 0
                        || ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(lastDayDBData)).compareTo(working.getTime()) == 0) {
                    averageSum = currentData;
                }
                break;
            case "lasthour":
                working = (Calendar) now.clone();
                working.add(Calendar.HOUR_OF_DAY, -1);
                averageSum = calculateAverage(list, working);
//                System.out.println(ConnectDB.SDATE_FORMAT_HOUR.format(working.getTime()));
                break;
            case "daily":
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, -1);
                averageSum = calculateAverage(list, working);
                break;
            case "weekly":
                working = (Calendar) now.clone();
                if (working.get(Calendar.DAY_OF_WEEK) > Calendar.MONDAY) {
                    working.add(Calendar.DAY_OF_WEEK, -working.get(Calendar.DAY_OF_WEEK) + 2);
                } else if (working.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    working.add(Calendar.DAY_OF_WEEK, 0);
                } else {
                    working.add(Calendar.DAY_OF_WEEK, -working.get(Calendar.DAY_OF_WEEK) + 1);
                }
//                working.add(Calendar.DAY_OF_YEAR, -working.get(Calendar.MONDAY));
//                System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(working.getTime()));
//                working.add(Calendar.DAY_OF_YEAR, -7);
                averageSum = calculateAverage(list, working);
                break;
            case "mtd":
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, -working.get(Calendar.DAY_OF_MONTH) + 1);
//                working.add(Calendar.DAY_OF_YEAR, -getMonthTypeValue((byte) working.get(Calendar.MONTH)));
                averageSum = calculateAverage(list, working);
                break;
            default:
                working = (Calendar) now.clone();
                if (working.get(Calendar.MONTH) > Calendar.JULY) {
                    working.add(Calendar.MONTH, -working.get(Calendar.MONTH) + 6);
                } else if (working.get(Calendar.MONTH) == Calendar.JULY) {
                    working.add(Calendar.MONTH, 0);
                } else {
                    working.add(Calendar.MONTH, -working.get(Calendar.MONTH) + 6);
                    working.add(Calendar.YEAR, -1);
                }
                //Set the working date to start at the 1 day of the month
                working.add(Calendar.DAY_OF_YEAR, -working.get(Calendar.DAY_OF_MONTH) + 1);
//                working.add(Calendar.DAY_OF_YEAR, -365);
//                System.out.println(working.get(Calendar.DAY_OF_MONTH));
                averageSum = calculateAverage(list, working);
                break;
        }
//        System.out.println(type + ": " + Calendar.DAY_OF_YEAR + "; " + ConnectDB.SDATE_FORMAT_HOUR.format(working.getTime()));
//        System.out.println(ConnectDB.isLeapYear(Calendar.YEAR));
        return Double.isNaN(averageSum) ? 0d : ConnectDB.DECIMALFORMAT.parse(ConnectDB.DECIMALFORMAT.format(averageSum)).doubleValue();
    }

    synchronized private byte getMonthTypeValue(byte month) {
        switch (month + 1) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return 31;
    }

    synchronized private Double calculateAverage(ArrayList<String> _list, Calendar calDaySet) throws ParseException {
        Double totalSum = 0d;
        Date date = calDaySet.getTime();//the time set in the form
        int count = 0;
        for (String list : _list) {
            StringTokenizer stringTokenizer = new StringTokenizer(list, ";");
            String logTime = stringTokenizer.nextToken();//LogTime
            double logData = Double.parseDouble(stringTokenizer.nextToken());
            if (Double.valueOf(logData) > 0d) {
                Date logTimeParsed = ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(logTime));
                if (logTimeParsed.compareTo(date) > 0
                        || logTimeParsed.compareTo(date) == 0) {
                    totalSum += logData;//LogData value
                    count++;
                }
            }
        }
        if (!showActualTotalValues) {
            return totalSum / count;
        } else {
            return totalSum;
        }
    }

    synchronized private ArrayList<String> getSubtractedValues(ArrayList<Integer> alValues, String query, String machineTitle)
            throws SQLException {
        ArrayList<String> subtractValues = new ArrayList<>(),
                prodRateArrayList = getProductionRate(query, machineTitle);
        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                xDiff = alValues.get(i) - alValues.get(i);
                subtractValues.add(new StringBuilder(logTimeList.get(i)).append(";").append(xDiff).toString());
                continue;
            }
            xDiff = alValues.get(i) - alValues.get(i - 1);
            if (xDiff < 0) {
                xDiff = 1000000 - alValues.get(i - 1) + alValues.get(i);
            }

            try {
                int totVal = alValues.get(i),
                        totValNext = alValues.get(i + 1),
                        rateVal = (int) Double.parseDouble(prodRateArrayList.get(i));
                //Case where the cumulative values are not consecutive by the addition of the production rate number
                if ((totVal + rateVal != totValNext) || (totVal + rateVal + 1 != totValNext)) {
                    // Not sequential
                    xDiff = rateVal;
                }
            } catch (IndexOutOfBoundsException e) {
                xDiff = (int) Double.parseDouble(prodRateArrayList.get(i - 1));
            }
            subtractValues.add(new StringBuilder(logTimeList.get(i)).append(";").append(xDiff).toString());
        }
        return subtractValues;
    }

    private ArrayList<String> getProductionRate(String query, String machineTitle) throws SQLException {
        int configNo = -1;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_CONFIGNO)) {
            ps.setString(1, "rate");
            ps.setString(2, machineTitle);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                configNo = ConnectDB.res.getInt(1);
            }
        }
        ArrayList<String> listProductionRate = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ps.setInt(1, configNo);
            ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(today));
            ResultSet result_Set = ps.executeQuery();
            while (result_Set.next()) {
                listProductionRate.add(result_Set.getString(2)); //Values
            }
        }
        return listProductionRate;
    }

    private void createTabbedPanel(JPanel jPanel, String panelName) {
        catCreateTab = false;
        short i = 0;
        if (tbpPanDetails.getTabCount() == 0) {
            i = 0;
            createTab(panelName, jPanel, i);
        } else {
            boolean find = false;
            for (; i < tbpPanDetails.getTabCount(); i++) {
                if (tbpPanDetails.getTitleAt(i).equals(panelName)) {
                    find = true;
                    if ("Production Rate".equals(panelName)) {
                        rateType = "(p/min)";
                        if (btnPerHour.isSelected()) {
                            rateType = "(p/hr)";
                        }
                    }
                    tbpPanDetails.setSelectedIndex(i);
                    tbpPanDetails.getSelectedComponent().validate();
                    break;
                }
            }
            if (!find) {
                createTab(panelName, jPanel, i);
                tbpPanDetails.setSelectedIndex(i);
            }
        }
        catCreateTab = true;
    }

    private void createTab(String tabName, JPanel jpanel, short i) {
        ImageIcon icon = null;
        jpanel.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        String titlePane = "";
        try {
            switch (tabName) {
                case "Production Rate"://Production rate
                    rateType = "(p/min)";
                    if (btnPerHour.isSelected()) {
                        rateType = "(p/hr)";
                    }
                    pane.setViewportView(createTable(tabName));//create and set the table in the viewport
                    icon = new ImageIcon(getClass().getResource("/images/icons/rate_4.png"));
                    titlePane = "Machine(s) average for the production rate";
                    break;
                default://Total Production
                    pane.setViewportView(createTable(tabName));//create and set the table in the viewport
                    icon = new ImageIcon(getClass().getResource("/images/icons/rate_1.png"));
                    titlePane = "Machine(s) quick view for the total of production";
                    break;
            }
            tbpPanDetails.insertTab(tabName, icon, jpanel, tabName, i);
            jpanel.add(pane);
        } catch (NullPointerException e) {
            tbpPanDetails.insertTab(tabName, icon, jpanel, tabName, i);
            jpanel.add(pane);
            jpanel.revalidate();
        }
        pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH), titlePane,
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", Font.PLAIN, 13)),
                BorderFactory.createEmptyBorder(6, 4, 4, 4)));
    }

    private SortableTable createTable(String prodType) {
        switch (prodType) {
            case "Production Rate":
                _modelRate = new TableModelRate();
                tableRate = new SortableTable(_modelRate);
                return setTableProperty(tableRate);
            default:
                _modelTotal = new TableModelTotal();
                tableTotal = new SortableTable(_modelTotal);
                return setTableProperty(tableTotal);
        }
    }

    private SortableTable setTableProperty(SortableTable table) {
        table.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setNestedTableHeader(true);
        table.setFillsGrids(false);
        table.getTableHeader();
        table.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        TableColumnGroup period = new TableColumnGroup("Evaluation Periods");
        period.add(table.getColumnModel().getColumn(1));
        period.add(table.getColumnModel().getColumn(2));
        period.add(table.getColumnModel().getColumn(3));
        period.add(table.getColumnModel().getColumn(4));
        period.add(table.getColumnModel().getColumn(5));
        period.add(table.getColumnModel().getColumn(6));
        if (table.getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) table.getTableHeader();
            header.addColumnGroup(period);
        }
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(table);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        TableUtils.autoResizeAllColumns(table);
        return table;
    }

    private void cleanTable(SortableTable sortableTable) {
        if (sortableTable != null) {
            byte nbTable = (byte) sortableTable.getModel().getRowCount();
            while (nbTable > 0) {
                if (!btnProductionRate.isEnabled()) {
                    _modelRate.removeNewRow(--nbTable);
                } else if (!btnTotalProduction.isEnabled()) {
                    _modelTotal.removeNewRow(--nbTable);
                }
            }
        }
    }

    private void firePopup(MouseEvent evt) {
        if (cblMachine.getSelectedIndex() <= 0) {
            return;
        }
        if (evt.getModifiers() == InputEvent.BUTTON1_MASK) {
            if (!evt.isPopupTrigger()) {
                createPopMenu();
                jpm.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private void createPopMenu() {
        jpm = new JPopupMenu();
        menu1 = new JMenuItem("Add/Edit Target");
        jpm.add(menu1);
        menu1.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/icons/target_3.png")));
        menu1.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = cblMachine.getSelectedValue().toString();
                if (s != null) {
                    final int getPos = cblMachine.getSelectedIndex();
                    if (getPos > -1) {
                        new TargetSingle(_parent, true).setVisible(true);
                    }
                }
            }
        });
    }

    private void outputToExcel(ActionEvent e, SortableTable table) {
        if (table.getModel().getRowCount() <= 0) {
            JOptionPane.showMessageDialog(_parent, "<html>The current table is blank, please select a machine"
                    + "<br> in the checkbox list on the left to fill the table.",
                    "Production Quick View", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser() {

            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setTitle("Export the content to an Excel file");
                return dialog;
            }
        };
        chooser.setCurrentDirectory(new File(ConnectDB.DEFAULT_DIRECTORY));
        chooser.setFileFilter(new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"));
        int result = chooser.showDialog(((JComponent) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                HssfTableUtils.export(table, chooser.getSelectedFile().getAbsolutePath() + ".xls", "SortableTable",
                        false, true, new HssfTableUtils.DefaultCellValueConverter() {

                            @Override
                            public int getDataFormat(JTable table, Object value, int rowIndex, int columnIndex) {
                                if (value instanceof Double) {
                                    return 2; // use 0.00 format
                                } else if (value instanceof Date) {
                                    return 0xe; // use "m/d/yy" format
                                }
                                return super.getDataFormat(table, value, rowIndex, columnIndex);
                            }
                        });
                if (JOptionPane.showConfirmDialog(_parent, "The file was saved sucessfully. "
                        + "Do you want to open the file ?", "Export",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == 0) {
                    File file = new File(chooser.getSelectedFile().getAbsolutePath() + ".xls");
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        JOptionPane.showMessageDialog(_parent, "The file could not be opened. Check if "
                                + "damaged or retry.", "Production Quick View", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(_parent, "Not very well implemented. Sorry !!!",
                        "Production Quick View", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }

    synchronized private void autoFill(String type) throws SQLException {
        short countMachine = 0;
        final DefaultListModel listModelMachine = new DefaultListModel();
        cblMachine.setModel(listModelMachine);
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT DISTINCT t.targetValue, t.Machine \n"
                + "FROM configuration c, hardware h, target t \n"
                + "WHERE h.HwNo = c.HwNo \n"
                + "AND h.Machine = t.Machine \n"
                + "AND c.ConfigNo = t.ConfigNo \n"
                + "AND c.AvMinMax =? \n"
                + "AND c.Active = 1 ORDER BY h.HwNo ASC")) {
            ps.setString(1, type);
            ConnectDB.res = ps.executeQuery();
            boolean find = false;
            listModelTarget.clear();
            listModelTarget.addElement(" ");
            while (ConnectDB.res.next()) {
                find = true;
                listModelMachine.addElement(ConnectDB.res.getString(2));
                listModelTarget.addElement(new StringBuilder("[").append(ConnectDB.res.getInt(1)).append("]").toString());
                countMachine++;
            }
            if (!find) {
                TargetInsert.setTargetFound(false);
//                new TargetInsert(_parent, true).setVisible(true);
                return;
            }
            if (find) {
                lblMachine.setText(new StringBuilder("List of machines (").append(countMachine).append(")").toString());
                listModelMachine.insertElementAt(CheckBoxList.ALL, 0);
                cblMachine.setModel(listModelMachine);
                cblMachine.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                SearchableUtils.installSearchable(cblMachine);
//            cblMachine.getCheckBoxListSelectionModel().addSelectionInterval(0, cblMachine.getModel().getSize() - 1);
                //Add the target in the list
                _listTarget.setFont(new Font("Tahoma", Font.PLAIN, 13));
                _listTarget.setFocusable(false);
                _listTarget.setExpandedTip(true);
                _listTarget.setModel(listModelTarget);
            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_LAST_DATA_TIME)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lastDayDBData = rs.getString(1).substring(0, 19);
                lblMessage.setText(new StringBuilder("Last recorded data: ").append(lastDayDBData).toString());
            }
        }
    }

    private void setDateComponent() {
        catDate = false;
        cmbDay.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        cmbDay.setDate(Calendar.getInstance().getTime());
        cmbDay.getEditor().getEditorComponent().setFocusable(false);
        catDate = true;
    }

    public static void main(String[] agrs) throws SQLException {
        ConnectDB.getConnectionInstance();
        final JFrame frame = new JFrame("Smartfactory Production Quick View 1.0");
        frame.setSize(950, 570);
        frame.setContentPane(new ProductionQuickView(null));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.navigation.NavigationList _listTarget;
    private org.jdesktop.swingx.JXBusyLabel bslQuickView;
    private javax.swing.JButton btnExportExcelCsv;
    private javax.swing.JButton btnLoadMachines;
    private javax.swing.JToggleButton btnPerHour;
    private javax.swing.JToggleButton btnPerMin;
    private javax.swing.JButton btnProductionRate;
    private com.jidesoft.swing.JideButton btnRefreshMachine;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JButton btnTarget;
    private javax.swing.JButton btnTotalProduction;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.jidesoft.swing.CheckBoxList cblMachine;
    private javax.swing.JCheckBox chkDay;
    private com.jidesoft.combobox.DateSpinnerComboBox cmbDay;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblMachine;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblProduction;
    private javax.swing.JLabel lblTargetTime;
    private javax.swing.JPanel panColor;
    private javax.swing.JPanel panProductionsOptions;
    private javax.swing.JPanel panTarget;
    private com.jidesoft.swing.JideTabbedPane tbpPanDetails;
    // End of variables declaration//GEN-END:variables
    private boolean catProdTab = true, showActualTotalValues = true, skipRefresh, skipRunListSelection = false,
            catDate;
    private volatile boolean catCreateTab = false;
    private int nbRow = 1;
    private static SortableTable tableRate, tableTotal;
    private static boolean totProdSelected = false;
    private String rateType = "(p/min)", lastDayDBData, prodType = null;
    private static Date today = null;
    private ArrayList<String> log_TimeData_List, logTimeList;
    private ArrayList<Integer> logDataList;
    private ResultSet resultSet = null;
    private TableModelRate _modelRate = null;
    private TableModelTotal _modelTotal = null;
    private final JFrame _parent;
    private final DefaultListModel listModelTarget = new DefaultListModel();
    private JPopupMenu jpm;
    private JMenuItem menu1;
    private Ball ball = null;
    private Thread fillTableThread = null;
    public Color ballColor = Color.RED;
}
