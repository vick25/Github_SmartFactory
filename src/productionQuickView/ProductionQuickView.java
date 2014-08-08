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
import com.jidesoft.navigation.NavigationList;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.JideTabbedPane;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelRate;
import tableModel.TableModelTotal;

/**
 *
 * @author Victor Kadiata
 */
public class ProductionQuickView extends javax.swing.JPanel {

    public ProductionQuickView(JFrame parent) throws SQLException {
        this._parent = parent;
        initComponents();
        panTarget.revalidate();
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
                int index = ((JideTabbedPane) e.getSource()).getSelectedIndex();
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
                                btnProductionRateActionPerformed(null);
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
                                btnTotalProductionActionPerformed(null);
                                totProd = true;
                                break;
                        }
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
        autoFill("Rate");//Fill the checkboxlist with machines
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
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
//                    terminate = true;
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
        cmbDay.setFormat(ConnectDB.SDATEFORMATHOUR);
        cmbDay.setDate(Calendar.getInstance().getTime());
        cmbDay.getEditor().getEditorComponent().setFocusable(false);
        createTabbedPanel(new JPanel(), "Production Rate");
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
        btnRefresh = new javax.swing.JButton();
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
        panTarget = new javax.swing.JPanel(new BorderLayout());
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9), new java.awt.Color(0, 0, 204))); // NOI18N
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnLoadMachines.setBackground(new java.awt.Color(255, 255, 255));
        btnLoadMachines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/machine16x16_1.png"))); // NOI18N
        btnLoadMachines.setText("Load Machines");
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
        btnTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/target_3.png"))); // NOI18N
        btnTarget.setText("Add Target");
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
        btnProductionRate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/rate_4.png"))); // NOI18N
        btnProductionRate.setText("Production Rate");
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
        btnTotalProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/rate_1.png"))); // NOI18N
        btnTotalProduction.setText("Total Production");
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
        btnExportExcelCsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/excel_csv_1.png"))); // NOI18N
        btnExportExcelCsv.setText("Excel/CSV");
        btnExportExcelCsv.setFocusable(false);
        btnExportExcelCsv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelCsv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportExcelCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelCsvActionPerformed(evt);
            }
        });
        jToolBar1.add(btnExportExcelCsv);

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/gnome_view_refresh16.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        cblMachine.setFocusable(false);
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblMachine.setForeground(new java.awt.Color(0, 204, 0));
        lblMachine.setText("List of machines (0)");

        btnRefreshMachine.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        btnRefreshMachine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/refresh16.png"))); // NOI18N
        btnRefreshMachine.setToolTipText("Refresh the machine list");
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
        cmbDay.setRequestFocusEnabled(false);
        cmbDay.setTimeDisplayed(true);
        cmbDay.setTimeFormat("HH:mm:ss");

        lblProduction.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblProduction.setText("Production Rate:");

        chkDay.setBackground(new java.awt.Color(255, 255, 255));
        chkDay.setSelected(true);
        chkDay.setText("Day:");
        chkDay.setFocusable(false);
        chkDay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkDayItemStateChanged(evt);
            }
        });

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(204, 0, 0));
        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        bslQuickView.setBusy(true);
        bslQuickView.setDirection(org.jdesktop.swingx.painter.BusyPainter.Direction.RIGHT);
        bslQuickView.setFocusable(false);
        bslQuickView.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        btnPerMin.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(btnPerMin);
        btnPerMin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        btnPerHour.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btnPerMin)
                    .addComponent(btnPerHour)
                    .addComponent(lblProduction)
                    .addComponent(bslQuickView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panProductionsOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkDay)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMessage))
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

        javax.swing.GroupLayout panTargetLayout = new javax.swing.GroupLayout(panTarget);
        panTarget.setLayout(panTargetLayout);
        panTargetLayout.setHorizontalGroup(
            panTargetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );
        panTargetLayout.setVerticalGroup(
            panTargetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel1.setText("<html>Production<br> Target (/hr)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(panColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMachine, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(panTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpPanDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panProductionsOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRefreshMachine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMachine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(panTarget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshMachineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMachineActionPerformed
        try {
            refresh();
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
        try {
            if (!skipRefresh) {
                refresh();
            }
            createTabbedPanel(new JPanel(), "Production Rate");
            if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
                runListSelection();
            }
            skipRefresh = false;
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnProductionRateActionPerformed

    private void btnTotalProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalProductionActionPerformed
        try {
            if (!skipRefresh) {
                refresh();
            }
            createTabbedPanel(new JPanel(), "Total Production");
            if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
                runListSelection();
            }
            skipRefresh = false;
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }//GEN-LAST:event_btnTotalProductionActionPerformed

    private void btnTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTargetActionPerformed
        new TargetSingle(_parent, true).setVisible(true);
    }//GEN-LAST:event_btnTargetActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
            try {
                runListSelection();
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLoadMachinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadMachinesActionPerformed
        try {
            new Target(_parent, true).setVisible(true);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
        if (Target.anyChange) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        refresh();
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
                }
            });
        }
    }//GEN-LAST:event_btnLoadMachinesActionPerformed

    private void cblMachineValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cblMachineValueChanged
        String s = String.valueOf(cblMachine.getSelectedValue());
        if ("null".equals(s) || "(All)".equals(s)) {
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
            outputToExcel(evt, _sortableTable);
        } catch (Exception e) {
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
    }//GEN-LAST:event_btnPerHourActionPerformed

    synchronized public void refresh() throws SQLException {
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
//        terminate = false;
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
        String query = "";
        SortableTable _sortableTable = null;
        if (!btnProductionRate.isEnabled()) {
            showActualTotalValues = false;
            _sortableTable = ProductionQuickView.tableRate;
        } else if (!btnTotalProduction.isEnabled()) {
            if (showActualTotalValues) {
                showActualTotalValues = true;
            } else {
                showActualTotalValues = false;
            }
            _sortableTable = ProductionQuickView.tableTotal;
        }
        if (cblMachine.getCheckBoxListSelectedValues().length <= 0) {
//            System.out.println("zero selected" + " " + cblMachine.getCheckBoxListSelectedValue());
            cleanTable(_sortableTable);
        } else {
            if (cblMachine.getCheckBoxListSelectedValues().length > 0) {
//                for (int i = 0; i < selected.length; i++) {
//                    int select = selected[i];
//                    Object obj = cblMachine.getCheckBoxListSelectionModel().getModel().getElementAt(select);
////                    System.out.print(select + " " + obj + " \n");
//                }
                if (!btnProductionRate.isEnabled()) {
                    prodType = "rate";
                    query = "SELECT DISTINCT h.Machine, c.ConfigNo\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND c.AvMinMax = 'Rate'\n"
                            + "" + criteriaSearch() + " AND c.Active = 1 ORDER BY h.HwNo ASC";
                } else if (!btnTotalProduction.isEnabled()) {
                    prodType = "total";
                    query = "SELECT DISTINCT h.Machine, c.ConfigNo\n"
                            + "FROM configuration c, hardware h\n"
                            + "WHERE h.HwNo = c.HwNo\n"
                            + "AND c.AvMinMax = 'Cumulative'\n"
                            + "" + criteriaSearch() + " AND c.Active = 1 ORDER BY h.HwNo ASC";
                }
                final ArrayList<String> list = new ArrayList();
                try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                    ConnectDB.res = ps.executeQuery();
                    while (ConnectDB.res.next()) {
                        list.add(ConnectDB.res.getString(1) + "," + ConnectDB.res.getString(2));
                    }
                }
                fillTableThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            fillTable(list, prodType);
                        } catch (Exception e) {
                        }
                    }
                });
//                try {
//                    fillTableThread.setPriority(Thread.currentThread().getPriority() - 1);
//                } catch (Exception e) {
//                }
                fillTableThread.start();
            }
        }
    }

    private String criteriaSearch() {
        String machines;
        if (cblMachine.getCheckBoxListSelectedValues().length != 0) {
            machines = " AND (h.Machine IN (" + manyCriteria(cblMachine.getCheckBoxListSelectedValues()) + "))";
        } else {
            machines = "";
        }
        return machines;
    }

    private String manyCriteria(Object[] list) {
        String values = "";
        for (Object list1 : list) {
            String value = list1.toString();
            values += "\'" + value + "\',";
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    synchronized private void fillTable(final ArrayList<String> listMachines, String configurationType)
            throws SQLException {
        this.requestFocus();
        alTimeValue.clear();
//        (AbstractComboBox) cmbDay.getEditor();
//        if (cmbDay.getEditor().getEditorComponent().hasFocus()) {
//            System.out.println("hasfocus");
//        }
        today = cmbDay.getDate();
        String query;
        nbRow = 1;
        if (!btnProductionRate.isEnabled()) {
            cleanTable(ProductionQuickView.tableRate);
        } else if (!btnTotalProduction.isEnabled()) {
            cleanTable(ProductionQuickView.tableTotal);
        }
        try {
            for (String machine : listMachines) {
                bslQuickView.setVisible(true);
                bslQuickView.setBusy(true);
                StringTokenizer stringTokenizer = new StringTokenizer(machine, ",");
                String oneMachine = stringTokenizer.nextToken();//The Machine name
                int configNo = Integer.parseInt(stringTokenizer.nextToken());//The ConfigNo

                bslQuickView.setText("Processing " + oneMachine + " ... ");
                if (configurationType.equals("rate")) {
                    if (btnPerHour.isSelected()) {
                        query = "SELECT d.LogTime AS 'Time', (d.LogData * 60)\n"
                                + "FROM datalog d\n"
                                + "WHERE d.ConfigNo =?\n"
                                + "AND d.LogTime <=?\n"
                                + "ORDER BY 'Time' ASC";
                    } else {
                        query = "SELECT d.LogTime AS 'Time', d.LogData\n"
                                + "FROM datalog d\n"
                                + "WHERE d.ConfigNo =?\n"
                                + "AND d.LogTime <=?\n"
                                + "ORDER BY 'Time' ASC";
                    }
                    runQuery(query, tableRate, oneMachine, configNo, 0);
                } else {
                    query = "SELECT d.LogTime AS 'Time', d.LogData\n"
                            + "FROM datalog d\n"
                            + "WHERE d.ConfigNo =?\n"
                            + "AND d.LogTime <=?\n"
                            + "ORDER BY 'Time' ASC";
                    runQuery(query, tableTotal, oneMachine, configNo, 1);
                }
//                Thread.sleep(500);
            }
            bslQuickView.setVisible(false);
        } catch (NumberFormatException e) {
        }
    }

    synchronized private void runQuery(String query, SortableTable st, String machineName, int configNo, int pan)
            throws SQLException {
        alTimeValue.clear();
        alTime.clear();
        alValue.clear();
        try {
            try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
                int i = 1;
                ps.setInt(i++, configNo);
                ps.setString(i++, ConnectDB.SDATEFORMATHOUR.format(today));
                ConnectDB.res = ps.executeQuery();
                while (ConnectDB.res.next()) {
                    if (pan == 1) {//Pane for the total production
                        alTime.add(ConnectDB.res.getString(1));
                        alValue.add(ConnectDB.res.getString(2));
                    } else {
                        alTimeValue.add(ConnectDB.res.getString(1) + ";" + ConnectDB.res.getString(2));//LogTime and LogData
                    }
                }
            }
            if (pan == 1) {
                alTimeValue = getSubtractedValues(alValue);
            }
            if (nbRow > st.getModel().getRowCount()) {
                if (pan == 0) {
                    _modelRate.addNewRow();
                } else {
                    _modelTotal.addNewRow();
                }
            }
            int listSize = alTimeValue.size();
            if (listSize > 0) {
                st.setValueAt(machineName, nbRow - 1, 0);
//                String current = alTimeValue.get(listSize - 1).split(";")[1];
                st.setValueAt(calculateLogData(alTimeValue, "current"), nbRow - 1, 1);
//                st.setValueAt(Double.valueOf(ConnectDB.DECIMALFORMAT.format(Double.parseDouble(current))), nbRow - 1, 1);
                st.setValueAt(calculateLogData(alTimeValue, "lasthour"), nbRow - 1, 2);
                st.setValueAt(calculateLogData(alTimeValue, "daily"), nbRow - 1, 3);
                st.setValueAt(calculateLogData(alTimeValue, "weekly"), nbRow - 1, 4);
                st.setValueAt(calculateLogData(alTimeValue, "mtd"), nbRow - 1, 5);
                st.setValueAt(calculateLogData(alTimeValue, "ytd"), nbRow - 1, 6);
                nbRow++;
            }
        } catch (NumberFormatException | ParseException ex) {
//            ex.printStackTrace();
        }
    }

    synchronized private double calculateLogData(ArrayList<String> list, String type) throws ParseException {
        Calendar now = Calendar.getInstance(), working;
        now.setTime(today);//set the calendar to the chosen date and time
        Double averageSum = 0d;
        switch (type) {
            case "current":
                int listSize = list.size();
                String[] data = alTimeValue.get(listSize - 1).split(";");
                working = (Calendar) now.clone();
                String currentData = data[1];
                if (ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(lastDayDBData)).compareTo(working.getTime()) > 0
                        || ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(lastDayDBData)).compareTo(working.getTime()) == 0) {
                    averageSum = Double.parseDouble(currentData);
                }
                break;
            case "lasthour":
                working = (Calendar) now.clone();
                working.add(Calendar.HOUR_OF_DAY, -1);
                averageSum = calculateAverage(list, working);
//                System.out.println(ConnectDB.SDATEFORMATHOUR.format(working.getTime()));
                break;
            case "daily":
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, -1);
                averageSum = calculateAverage(list, working);
                break;
            case "weekly":
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, -7);
                averageSum = calculateAverage(list, working);
                break;
            case "mtd":
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, -30);
                averageSum = calculateAverage(list, working);
                break;
            default:
                working = (Calendar) now.clone();
                working.add(Calendar.DAY_OF_YEAR, - 365);
                averageSum = calculateAverage(list, working);
                break;
        }
        return Double.isNaN(averageSum) ? 0d : ConnectDB.DECIMALFORMAT.parse(ConnectDB.DECIMALFORMAT.format(averageSum)).doubleValue();
    }

    synchronized private Double calculateAverage(ArrayList<String> _list, Calendar calDaySet) throws ParseException {
        Double averageSum = null;
        Date date = calDaySet.getTime();//the time set in the form
        int count = 0;
        for (String list : _list) {
            StringTokenizer stringTokenizer = new StringTokenizer(list, ";");
            String logTime = stringTokenizer.nextToken();//LogTime
            String logData = stringTokenizer.nextToken();
            if (Double.valueOf(logData) > 0d) {
                if (ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(logTime)).compareTo(date) > 0
                        || ConnectDB.SDATEFORMATHOUR.parse(ConnectDB.correctToBarreDate(logTime)).compareTo(date) == 0) {
                    averageSum += Double.parseDouble(logData);//LogData value
                    count++;
                }
            }
        }
        if (!showActualTotalValues) {
            return averageSum / count;
        } else {
            return averageSum;
        }
    }

    synchronized private ArrayList<String> getSubtractedValues(ArrayList alValue) {
        ArrayList<String> subtractValues = new ArrayList<>();
        for (int i = 0; i < alValue.size(); i++) {
            int xDiff;
            if (i == 0) {
                xDiff = Integer.parseInt(alValue.get(i).toString()) - Integer.parseInt(alValue.get(i).toString());
                subtractValues.add(alTime.get(i) + ";" + xDiff);
                continue;
            }
            xDiff = Integer.parseInt(alValue.get(i).toString()) - Integer.parseInt(alValue.get(i - 1).toString());
            subtractValues.add(alTime.get(i) + ";" + xDiff);
        }
        return subtractValues;
    }

    private void createTabbedPanel(JPanel jPanel, String panelName) {
        int i = 0;
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
//                        jPanel.removeAll();
//                        jPanel.invalidate();
//                        System.out.println(jPanel);
////                 frame.invalidate();
////                 frame.validate();
////                 frame.repaint();
////                        tbpPanDetails.getSelectedComponent().rem
//                       createTable(rateType);
////                       tableRate.getTableHeader().g
//                        tableRate.invalidate();
////                        createTab(panelName, jPanel, -1);
////                        jPanel.setLayout(new BorderLayout());
////                        JScrollPane pane = new JScrollPane();
////                        pane.setViewportView(createTable(rateType));
////                        jPanel.add(pane);
                    }
                    tbpPanDetails.setSelectedIndex(i);
                    tbpPanDetails.getSelectedComponent().validate();
                    break;
                }
//                continue;
            }
            if (!find) {
                createTab(panelName, jPanel, i);
                tbpPanDetails.setSelectedIndex(i);
            }
        }
    }

    private void createTab(String tabName, JPanel jpanel, int i) {
        ImageIcon icon = null;
        jpanel.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        try {
            switch (tabName) {
                case "Total Production"://Total Production
                    pane.setViewportView(createTable());//create and set the table in the viewport
                    icon = new ImageIcon(getClass().getResource("/images/icons/rate_1.png"));
                    pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                            new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                                UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                            "Machine(s) quick view for the total of production",
                            TitledBorder.CENTER, TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
                    break;
                case "Production Rate"://Production rate 
                    rateType = "(p/min)";
                    if (btnPerHour.isSelected()) {
                        rateType = "(p/hr)";
                    }
                    pane.setViewportView(createTable(rateType));//create and set the table in the viewport
                    icon = new ImageIcon(getClass().getResource("/images/icons/rate_4.png"));
                    pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                            new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                                UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                            "Machine(s) average for the production rate",
                            TitledBorder.CENTER, TitledBorder.ABOVE_TOP), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
                    break;
            }
            tbpPanDetails.insertTab(tabName, icon, jpanel, tabName, i);
            jpanel.add(pane);
        } catch (NullPointerException e) {
            tbpPanDetails.insertTab(tabName, icon, jpanel, tabName, i);
            jpanel.add(pane);
        }
    }

    private SortableTable createTable(String rateType) {
        _modelRate = new TableModelRate();
        tableRate = new SortableTable(_modelRate);
        tableRate.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        tableRate.setNestedTableHeader(true);
        tableRate.setFillsGrids(false);
        tableRate.getTableHeader();
        tableRate.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        TableColumnGroup period = new TableColumnGroup("Periods " + rateType);
        period.add(tableRate.getColumnModel().getColumn(1));
        period.add(tableRate.getColumnModel().getColumn(2));
        period.add(tableRate.getColumnModel().getColumn(3));
        period.add(tableRate.getColumnModel().getColumn(4));
        period.add(tableRate.getColumnModel().getColumn(5));
        period.add(tableRate.getColumnModel().getColumn(6));
        if (tableRate.getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) tableRate.getTableHeader();
            header.addColumnGroup(period);
        }
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(tableRate);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        TableUtils.autoResizeAllColumns(tableRate);
        return tableRate;
    }

    private SortableTable createTable() {
        _modelTotal = new TableModelTotal();
        tableTotal = new SortableTable(_modelTotal);
        tableTotal.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
        tableTotal.setNestedTableHeader(true);
        tableTotal.setFillsGrids(false);
        tableTotal.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{ConnectDB.getColorFromKey(
            ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR1, "253, 253, 244")),
            ConnectDB.getColorFromKey(ConnectDB.pref.get(SettingKeyFactory.FontColor.RSTRIPE21COLOR2, "230, 230, 255"))}));
        TableColumnGroup period = new TableColumnGroup("Periods");
        period.add(tableTotal.getColumnModel().getColumn(1));
        period.add(tableTotal.getColumnModel().getColumn(2));
        period.add(tableTotal.getColumnModel().getColumn(3));
        period.add(tableTotal.getColumnModel().getColumn(4));
        period.add(tableTotal.getColumnModel().getColumn(5));
        period.add(tableTotal.getColumnModel().getColumn(6));
        if (tableTotal.getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) tableTotal.getTableHeader();
            header.addColumnGroup(period);
        }
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(tableTotal);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
//        setTableColumn();
//        setSortableTable(_sortableTable);
        TableUtils.autoResizeAllColumns(tableTotal);
//        jScrollPane2.setViewportView(_sortableTable);
        return tableTotal;
    }

    private void cleanTable(SortableTable sortableTable) {
        int nbTable = sortableTable.getModel().getRowCount();
        while (nbTable > 0) {
            if (!btnProductionRate.isEnabled()) {
                _modelRate.removeNewRow(--nbTable);
            } else if (!btnTotalProduction.isEnabled()) {
                _modelTotal.removeNewRow(--nbTable);
            }
        }
    }

    private void firePopup(MouseEvent evt) {
        int rowIndex = cblMachine.getSelectedIndex();
        if (rowIndex <= 0) {
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
        menu1.setIcon(new ImageIcon(getClass().getResource("/images/icons/target_3.png")));
        menu1.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = cblMachine.getSelectedValue().toString();
                System.out.println("s" + s);
                if (s != null) {
                    final int getPos = cblMachine.getSelectedIndex();
                    System.out.println(getPos);
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
        chooser.setCurrentDirectory(new File(ConnectDB.DEFAULTDIRECTORY));
        int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
//                System.out.println("Exporting to " + _lastDirectory);
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
//                System.out.println("Exported");
                if (JOptionPane.showConfirmDialog(_parent, "The file was saved sucessfully. "
                        + "Do you want to open the file?", "Export",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == 0) {
                    File file = new File(chooser.getSelectedFile().getAbsolutePath() + ".xls");
//                    System.out.println(file.getAbsolutePath());
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        JOptionPane.showMessageDialog(_parent, "The file could not be opened. Check if "
                                + "damaged or retry.", "Production Quick View", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(_parent, "Not very well implemented. Sorry",
                        "Production Quick View", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }

    synchronized private void autoFill(String type) throws SQLException {
//        catMachine = false;
        countMachine = 0;
        final DefaultListModel listModelMachine = new DefaultListModel(), listModelTarget = new DefaultListModel();
        cblMachine.setModel(listModelMachine);
        _listTarget.setModel(listModelTarget);
//        SELECT Machine\nFROM hardware WHERE HwNo > ? ORDER BY HwNo ASC
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT DISTINCT t.targetValue, t.Machine\n"
                + "FROM configuration c, hardware h, target t\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND c.AvMinMax =?\n"
                + "AND h.Machine = t.Machine\n"
                + "AND c.ConfigNo = t.ConfigNo\n"
                + "AND c.Active = 1 ORDER BY h.HwNo ASC")) {
            ps.setString(1, type);
            ConnectDB.res = ps.executeQuery();
            boolean find = false;
            listModelTarget.addElement(" ");
            while (ConnectDB.res.next()) {
                find = true;
                listModelMachine.addElement(ConnectDB.res.getString(2));
                listModelTarget.addElement("[" + ConnectDB.res.getInt(1) + "]");
                countMachine++;
            }
            if (!find) {
                Target.targetFound = false;
//                new Target(_parent, true).setVisible(true);
                return;
            }
            if (find) {
                lblMachine.setText("List of machines (" + countMachine + ")");
                listModelMachine.insertElementAt(CheckBoxList.ALL, 0);
                cblMachine.setModel(listModelMachine);
                cblMachine.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                SearchableUtils.installSearchable(cblMachine);
//            cblMachine.getCheckBoxListSelectionModel().addSelectionInterval(0, cblMachine.getModel().getSize() - 1);
                //Add the target in the list
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        _listTarget.setModel(listModelTarget);
//            _listTarget = new NavigationList(listModelTarget);
                        _listTarget.setFont(new Font("Tahoma", Font.PLAIN, 11));
                        _listTarget.setFocusable(false);
                        _listTarget.setExpandedTip(true);
                        panTarget.setLayout(new BorderLayout());
                        panTarget.setBackground(new Color(255, 255, 255));
                        panTarget.add(new JScrollPane(_listTarget));
                        panTarget.revalidate();
                    }
                });
            }
        }
        try (PreparedStatement ps1 = ConnectDB.con.prepareStatement("SELECT LogTime FROM datalog\n"
                + "ORDER BY LogTime DESC LIMIT 1;")) {
            ConnectDB.res = ps1.executeQuery();
            while (ConnectDB.res.next()) {
                lastDayDBData = ConnectDB.res.getString(1).substring(0, 19);
                lblMessage.setText("Last recorded data: " + lastDayDBData);
            }
        }
//        catMachine = true;
    }

    public static void main(String[] agrs) throws SQLException {
        ConnectDB.getConnectionInstance();
        final JFrame frame = new JFrame("Smartfactory Production Quick View 1.0");
        frame.setSize(915, 570);
        frame.setContentPane(new ProductionQuickView(null));
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Please make sure to save any data before closing.\n"
                        + "Do you want to close ?",
                        "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXBusyLabel bslQuickView;
    private javax.swing.JButton btnExportExcelCsv;
    private javax.swing.JButton btnLoadMachines;
    private javax.swing.JToggleButton btnPerHour;
    private javax.swing.JToggleButton btnPerMin;
    private javax.swing.JButton btnProductionRate;
    private javax.swing.JButton btnRefresh;
    private com.jidesoft.swing.JideButton btnRefreshMachine;
    private javax.swing.JButton btnTarget;
    private javax.swing.JButton btnTotalProduction;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.jidesoft.swing.CheckBoxList cblMachine;
    private javax.swing.JCheckBox chkDay;
    private com.jidesoft.combobox.DateSpinnerComboBox cmbDay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblMachine;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblProduction;
    private javax.swing.JPanel panColor;
    private javax.swing.JPanel panProductionsOptions;
    private javax.swing.JPanel panTarget;
    private com.jidesoft.swing.JideTabbedPane tbpPanDetails;
    // End of variables declaration//GEN-END:variables
    private boolean catProdTab = true, showActualTotalValues = true, skipRefresh;
    private int countMachine = 0, nbRow = 1;
    static SortableTable tableRate, tableTotal;
    public static boolean totProd;
    private String rateType = "(p/min)", lastDayDBData;
    private static Date today = null;
    private static ArrayList<String> alTimeValue = new ArrayList<>();
    private static final ArrayList<String> alTime = new ArrayList<>(), alValue = new ArrayList<>();
    String _lastDirectory = ".";
    private String prodType = "";
    TableModelRate _modelRate;
    TableModelTotal _modelTotal;
    JFrame _parent;
    NavigationList _listTarget = new NavigationList();
//    Thread[] machinesThread;
    JPopupMenu jpm;
    JMenuItem menu1;
    Ball ball = new Ball(this);
    Thread fillTableThread;
    Color ballColor = Color.RED;
//    volatile boolean terminate = false;
}
