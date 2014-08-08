package shiftReport;

import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import productionPanel.SetTableTime;
import tableModel.TableModelShiftTime;

/**
 *
 * @author Victor Kadiata
 */
public class ShiftReporter extends javax.swing.JPanel {

    public ShiftReporter() {
        initComponents();
        panShiftTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialGradientLineBorder(new Color[]{new Color(0, 0, 128),
                    UIDefaultsLookup.getColor("control")}, 2, PartialSide.NORTH),
                "Hours", TitledBorder.CENTER, TitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        setTableTime(3);//tables for the hour shifts
        jPanel3.setVisible(false);
        jXHyperlink1.setText("Show Shifts Time");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtReason = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstReasons = new org.jdesktop.swingx.JXList(listModel);
        jideButton2 = new com.jidesoft.swing.JideButton();
        jideButton1 = new com.jidesoft.swing.JideButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        checkBoxList1 = new com.jidesoft.swing.CheckBoxList();
        jPanel2 = new javax.swing.JPanel();
        dashboard1 = new com.jidesoft.dashboard.Dashboard();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();
        jPanel3 = new javax.swing.JPanel();
        panShiftTime = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnShiftTable = new com.jidesoft.swing.JideButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reasons", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel1.setText("Reason");

        lstReasons.setFocusable(false);
        jScrollPane2.setViewportView(lstReasons);

        jideButton2.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        jideButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/office/delete.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jideButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtReason)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jideButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jideButton1.setButtonStyle(com.jidesoft.swing.JideButton.TOOLBOX_STYLE);
        jideButton1.setText("Print");
        jideButton1.setOpaque(true);
        jideButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jideButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Machines", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        checkBoxList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        checkBoxList1.setFocusable(false);
        jScrollPane1.setViewportView(checkBoxList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jXHyperlink1.setText("Show Shifts Time");
        jXHyperlink1.setFocusable(false);
        jXHyperlink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXHyperlink1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panShiftTime.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panShiftTimeLayout = new javax.swing.GroupLayout(panShiftTime);
        panShiftTime.setLayout(panShiftTimeLayout);
        panShiftTimeLayout.setHorizontalGroup(
            panShiftTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(panShiftTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShiftTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Supervisor");

        jLabel3.setText("Team");

        jLabel4.setText("jLabel4");

        jTextField1.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jideButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jXHyperlink1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXHyperlink1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jideButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jXHyperlink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink1ActionPerformed
        if (jPanel3.isVisible()) {
            jPanel3.setVisible(false);
            jXHyperlink1.setText("Show Shifts Time");
        } else {
            jPanel3.setVisible(true);
            jXHyperlink1.setText("Hide Shifts Time");
        }
    }//GEN-LAST:event_jXHyperlink1ActionPerformed

    private void btnShiftTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShiftTableMouseClicked
        if (evt.getClickCount() == 2) {
            final Point pos = this.btnShiftTable.getLocationOnScreen();
            new SetTableTime(null, true, pos).setVisible(true);
            if (SetTableTime.closeWindow) {
                String[] ses = SetTableTime.timeString.split(";");
                tableTime.setValueAt(ses[0], 0, 1);
                tableTime.setValueAt(ses[1], 0, 2);
                tableTime.setValueAt(ses[2], 1, 1);
                tableTime.setValueAt(ses[3], 1, 2);
                tableTime.setValueAt(ses[4], 2, 1);
                tableTime.setValueAt(ses[5], 2, 2);
            }
        } else {
            tableTime.setValueAt(SetTableTime.times[0], 0, 1);
            tableTime.setValueAt(SetTableTime.times[1], 0, 2);
            tableTime.setValueAt(SetTableTime.times[2], 1, 1);
            tableTime.setValueAt(SetTableTime.times[3], 1, 2);
            tableTime.setValueAt(SetTableTime.times[4], 2, 1);
            tableTime.setValueAt(SetTableTime.times[5], 2, 2);
        }
    }//GEN-LAST:event_btnShiftTableMouseClicked

    private void jideButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jideButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jideButton1ActionPerformed

    private void setTableTime(int numRow) {
        modelTime = new TableModelShiftTime(numRow);
        tableTime = new SortableTable(modelTime);
        tableTime.getTableHeader().setReorderingAllowed(false);
        tableTime.setFocusable(false);
        tableTime.setSortable(false);
//        tableTime.getTableHeader().setDefaultRenderer(new ConnectDB.HeaderRenderer(tableTime));
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

    public static void main(String[] agrs) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        final JFrame frame = new JFrame("Smartfactory Production Quick View 1.0");
        frame.setSize(915, 615);
        frame.setContentPane(new ShiftReporter());
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
    private com.jidesoft.swing.JideButton btnShiftTable;
    private com.jidesoft.swing.CheckBoxList checkBoxList1;
    private com.jidesoft.dashboard.Dashboard dashboard1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    private com.jidesoft.swing.JideButton jideButton1;
    private com.jidesoft.swing.JideButton jideButton2;
    private org.jdesktop.swingx.JXList lstReasons;
    private javax.swing.JPanel panShiftTime;
    private javax.swing.JTextField txtReason;
    // End of variables declaration//GEN-END:variables
    DefaultListModel listModel = new DefaultListModel();
    private TableModelShiftTime modelTime;
    public static SortableTable tableTime;
    protected final Color BACKGROUND1 = new Color(253, 253, 220), BACKGROUND2 = new Color(255, 255, 255);
}
