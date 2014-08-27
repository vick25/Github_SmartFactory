package dashboard;

import collapsibleDashboard.CollapsiblePaneGadget;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import mainFrame.MainMenuPanel;
import productionPanel.ProdStatKeyFactory;
import productionPanel.SetTableTime;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import tableModel.TableModelShiftTime;

/**
 *
 * @author Victor Kadiata
 */
public class GadgetSettings extends javax.swing.JPanel {

    public GadgetSettings(int[] configNo, String keyMachine, Component gadget) {
        initComponents();
        this._gadget = (CollapsiblePaneGadget) gadget;
        this._configNo = configNo;
        this._keyMachine = keyMachine;
        panShiftTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                "Hours", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        setTableTime(3);
        setPropertiesTimeSaved(ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TIMESHIFT, getPropertiesToSave()));
        if (DashBoard.isShowTotalProd()) {
            chkTotalProduction.setSelected(true);
        } else {
            chkTotalProduction.setSelected(false);
        }
        if (DashBoard.isShowRateProd()) {
            chkRateProduction.setSelected(true);
        } else {
            chkRateProduction.setSelected(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chkTotalProduction = new javax.swing.JCheckBox();
        chkRateProduction = new javax.swing.JCheckBox();
        chkPerShift = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        panShiftTime = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnShiftTable = new com.jidesoft.swing.JideButton();
        radPerMin = new javax.swing.JRadioButton();
        radPerHour = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXComboBox1 = new org.jdesktop.swingx.JXComboBox();
        btnRegenerateChart = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Charts", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 255))); // NOI18N

        chkTotalProduction.setBackground(new java.awt.Color(255, 255, 255));
        chkTotalProduction.setSelected(true);
        chkTotalProduction.setText("Total production");
        chkTotalProduction.setFocusable(false);
        chkTotalProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTotalProductionActionPerformed(evt);
            }
        });

        chkRateProduction.setBackground(new java.awt.Color(255, 255, 255));
        chkRateProduction.setText("Rate production");
        chkRateProduction.setFocusable(false);
        chkRateProduction.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkRateProductionItemStateChanged(evt);
            }
        });

        chkPerShift.setBackground(new java.awt.Color(255, 255, 255));
        chkPerShift.setSelected(true);
        chkPerShift.setText("Shifts");
        chkPerShift.setFocusable(false);
        chkPerShift.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPerShiftItemStateChanged(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        panShiftTime.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panShiftTimeLayout = new javax.swing.GroupLayout(panShiftTime);
        panShiftTime.setLayout(panShiftTimeLayout);
        panShiftTimeLayout.setHorizontalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panShiftTimeLayout.setVerticalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
            .addGroup(panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panShiftTimeLayout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );

        btnShiftTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/refresh16x16.png"))); // NOI18N
        btnShiftTable.setToolTipText("Reset to defaults value or edit Time-Shift table by double-cliking the button");
        btnShiftTable.setFocusable(false);
        btnShiftTable.setOpaque(true);
        btnShiftTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShiftTableMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panShiftTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        radPerMin.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radPerMin);
        radPerMin.setText("p/min");
        radPerMin.setEnabled(false);
        radPerMin.setFocusable(false);
        radPerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPerMinActionPerformed(evt);
            }
        });

        radPerHour.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radPerHour);
        radPerHour.setSelected(true);
        radPerHour.setText("p/hr");
        radPerHour.setEnabled(false);
        radPerHour.setFocusable(false);
        radPerHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPerHourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chkRateProduction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radPerMin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radPerHour)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chkTotalProduction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addComponent(chkPerShift)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTotalProduction)
                    .addComponent(chkPerShift))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRateProduction)
                    .addComponent(radPerMin)
                    .addComponent(radPerHour))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel1.setText("Time calibration");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jXComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        btnRegenerateChart.setBackground(new java.awt.Color(255, 255, 255));
        btnRegenerateChart.setText("Regenerate");
        btnRegenerateChart.setFocusable(false);
        btnRegenerateChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegenerateChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegenerateChart))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegenerateChart)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnShiftTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShiftTableMouseClicked
        if (evt.getClickCount() == 2) {
            final Point pos = this.btnShiftTable.getLocationOnScreen();
            new SetTableTime(null, true, pos).setVisible(true);
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
        saveChanges();
    }//GEN-LAST:event_btnShiftTableMouseClicked

    private void chkTotalProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTotalProductionActionPerformed
        if (chkTotalProduction.isSelected()) {
            DashBoard.setShowTotalProd(true);
            chkPerShift.setEnabled(true);
            chkPerShift.setSelected(true);
        } else {
            DashBoard.setShowTotalProd(false);
            chkPerShift.setEnabled(false);
            chkPerShift.setSelected(false);
        }
    }//GEN-LAST:event_chkTotalProductionActionPerformed

    private void chkPerShiftItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPerShiftItemStateChanged
        if (chkPerShift.isSelected()) {
            btnShiftTable.setEnabled(true);
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, chkPerShift.isSelected());
            jScrollPane3.getHorizontalScrollBar().setEnabled(true);
            jScrollPane3.getVerticalScrollBar().setEnabled(true);
            jScrollPane3.getViewport().getView().setEnabled(true);
            saveChanges();
        } else {
            btnShiftTable.setEnabled(false);
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, chkPerShift.isSelected());
            jScrollPane3.getHorizontalScrollBar().setEnabled(false);
            jScrollPane3.getVerticalScrollBar().setEnabled(false);
            jScrollPane3.getViewport().getView().setEnabled(false);
        }
    }//GEN-LAST:event_chkPerShiftItemStateChanged

    private void radPerHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPerHourActionPerformed
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, true);
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, false);
    }//GEN-LAST:event_radPerHourActionPerformed

    private void radPerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPerMinActionPerformed
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true);
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false);
    }//GEN-LAST:event_radPerMinActionPerformed

    private void chkRateProductionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkRateProductionItemStateChanged
        if (chkRateProduction.isSelected()) {
            DashBoard.setShowRateProd(true);
            radPerHour.setEnabled(true);
            radPerMin.setEnabled(true);
        } else {
            DashBoard.setShowRateProd(false);
            radPerHour.setEnabled(false);
            radPerMin.setEnabled(false);
        }
    }//GEN-LAST:event_chkRateProductionItemStateChanged

    private void btnRegenerateChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegenerateChartActionPerformed
        try {
            dialog.dispose();
            this._gadget.getContentPane().removeAll();
            try {
                DashBoard.bslTime.setText("Regenerating " + this._keyMachine + " in a while.");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            DashBoard.getColDashBoard().setGadgetComponentPane(this._configNo, this._keyMachine, this._gadget);
//            DashBoard.bslTime.setText("Regenerating " + this._keyMachine + " in a while.");
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnRegenerateChartActionPerformed

    private void saveChanges() {
        if (saved) {
            if (tableTime.isEditing()) {
                tableTime.getCellEditor().stopCellEditing();
            }
            propertiesToSave = getPropertiesToSave();
        }
        ConnectDB.pref.put(SettingKeyFactory.DefaultProperties.TIMESHIFT, propertiesToSave);
    }

    private String getPropertiesToSave() {
        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
        String properties = "";
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
            int k = 0;
            while (k < rowValues.length) {
                String[] columnTrainee = rowValues[k].split("\t");
                for (int j = 0; j < columnTrainee.length; j++) {
                    tableTime.setValueAt(columnTrainee[j], k, j + 1);
                }
                k++;
            }
            saved = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            error = true;
            saved = true;
            ConnectDB.pref.remove(SettingKeyFactory.DefaultProperties.TIMESHIFT);
//            return;
        } finally {
            saved = true;
            if (error) {
                setTableTime(3);
            }
            setSettings();
        }
    }

    public void setSettings() {
        chkPerShift.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false));
        radPerMin.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, true));
        radPerHour.setSelected(ConnectDB.pref.getBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, false));
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
        tableTime.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{BACKGROUND1, BACKGROUND2}));
        tableTime.getColumnModel().getColumn(0).setMinWidth(35);
        tableTime.getColumnModel().getColumn(0).setMaxWidth(35);
        tableTime.getColumnModel().getColumn(0).setResizable(false);
        tableTime.setRowSelectionAllowed(true);
        tableTime.setColumnSelectionAllowed(true);
        tableTime.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableTime.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(tableTime);
    }

    public static void showSettingsDialog(int[] configNo, String keyMachine, Component gadget) {
        dialog = new JDialog(MainMenuPanel.getDashBoardFrame(), keyMachine + " Gadget Settings");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(new GadgetSettings(configNo, keyMachine, gadget));
        dialog.setPreferredSize(new Dimension(370, 340));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(gadget);
        dialog.setVisible(true);
        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        showSettingsDialog(null, "", null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegenerateChart;
    private com.jidesoft.swing.JideButton btnShiftTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkPerShift;
    private javax.swing.JCheckBox chkRateProduction;
    private javax.swing.JCheckBox chkTotalProduction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXComboBox jXComboBox1;
    private javax.swing.JPanel panShiftTime;
    private javax.swing.JRadioButton radPerHour;
    private javax.swing.JRadioButton radPerMin;
    // End of variables declaration//GEN-END:variables
    private TableModelShiftTime modelTime;
    private static SortableTable tableTime;
    protected final Color BACKGROUND1 = new Color(253, 253, 220), BACKGROUND2 = new Color(255, 255, 255);
    static boolean saved = true;
    static String propertiesToSave = "";
    private static JDialog dialog;
    private final CollapsiblePaneGadget _gadget;
    private final int[] _configNo;
    private final String _keyMachine;
}
