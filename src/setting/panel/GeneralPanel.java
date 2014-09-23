package setting.panel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class GeneralPanel extends javax.swing.JPanel {

    public static String getYearStart() {
        return yearStart;
    }

    public GeneralPanel(AbstractDialogPage page) {
        this.page = page;
        initComponents();
        ConnectDB.collapsiblePaneProperties(collapsiblePane3);
        ConnectDB.collapsiblePaneProperties(collapsiblePane4);
        ConnectDB.collapsiblePaneProperties(collapsiblePane5);
        ConnectDB.collapsiblePaneProperties(collapsiblePane6);
        try {
            initValues();
        } catch (ParseException ex) {
            Logger.getLogger(GeneralPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane3 = new com.jidesoft.pane.CollapsiblePane();
        chkCenterTableHead = new javax.swing.JCheckBox();
        chkBoldTableHead = new javax.swing.JCheckBox();
        collapsiblePane4 = new com.jidesoft.pane.CollapsiblePane();
        jLabel12 = new javax.swing.JLabel();
        chkDateFormat = new javax.swing.JComboBox();
        collapsiblePane5 = new com.jidesoft.pane.CollapsiblePane();
        chkCloseSelectedOnly = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        cmbTabPlacement = new javax.swing.JComboBox();
        collapsiblePane6 = new com.jidesoft.pane.CollapsiblePane();
        chkConfirmCloseMainFrame = new javax.swing.JCheckBox();
        chkConfirmCloseTab = new javax.swing.JCheckBox();
        chkProdQuickView = new javax.swing.JCheckBox();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        jLabel1 = new javax.swing.JLabel();
        cmbYear = new com.jidesoft.combobox.DateExComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbDayOfWeek = new com.alee.laf.combobox.WebComboBox();

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        try {
            collapsiblePane3.setCollapsed(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        collapsiblePane3.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane3.setTitle("Tables properties");
        collapsiblePane3.setFocusable(false);

        chkCenterTableHead.setSelected(true);
        chkCenterTableHead.setText("Center table head");
        chkCenterTableHead.setFocusable(false);
        chkCenterTableHead.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCenterTableHeadItemStateChanged(evt);
            }
        });

        chkBoldTableHead.setSelected(true);
        chkBoldTableHead.setText("Bold table head");
        chkBoldTableHead.setFocusable(false);
        chkBoldTableHead.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBoldTableHeadItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane3Layout = new javax.swing.GroupLayout(collapsiblePane3.getContentPane());
        collapsiblePane3.getContentPane().setLayout(collapsiblePane3Layout);
        collapsiblePane3Layout.setHorizontalGroup(
            collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(chkCenterTableHead)
                .addGap(74, 74, 74)
                .addComponent(chkBoldTableHead)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane3Layout.setVerticalGroup(
            collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane3Layout.createSequentialGroup()
                .addGroup(collapsiblePane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCenterTableHead)
                    .addComponent(chkBoldTableHead))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        try {
            collapsiblePane4.setCollapsed(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        collapsiblePane4.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane4.setTitle("Date format");
        collapsiblePane4.setFocusable(false);

        jLabel12.setText("Date format");

        chkDateFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MMMM dd, yyyy", "dd - MM - yyyy", "MM / dd / yyyy", "yyyy - MM - dd", "dd MMMM yyyy" }));
        chkDateFormat.setFocusable(false);
        chkDateFormat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkDateFormatItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane4Layout = new javax.swing.GroupLayout(collapsiblePane4.getContentPane());
        collapsiblePane4.getContentPane().setLayout(collapsiblePane4Layout);
        collapsiblePane4Layout.setHorizontalGroup(
            collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkDateFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane4Layout.setVerticalGroup(
            collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane4Layout.createSequentialGroup()
                .addGroup(collapsiblePane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkDateFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        collapsiblePane5.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane5.setTitle("Tabs properties");
        collapsiblePane5.setFocusable(false);

        chkCloseSelectedOnly.setSelected(true);
        chkCloseSelectedOnly.setText("Show close button on selected tab only");
        chkCloseSelectedOnly.setFocusable(false);
        chkCloseSelectedOnly.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCloseSelectedOnlyItemStateChanged(evt);
            }
        });

        jLabel11.setText("Tab Placement");

        cmbTabPlacement.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TOP", "LEFT", "BOTTOM", "RIGHT" }));
        cmbTabPlacement.setFocusable(false);
        cmbTabPlacement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTabPlacementItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane5Layout = new javax.swing.GroupLayout(collapsiblePane5.getContentPane());
        collapsiblePane5.getContentPane().setLayout(collapsiblePane5Layout);
        collapsiblePane5Layout.setHorizontalGroup(
            collapsiblePane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(chkCloseSelectedOnly)
                .addGap(11, 11, 11)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbTabPlacement, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        collapsiblePane5Layout.setVerticalGroup(
            collapsiblePane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane5Layout.createSequentialGroup()
                .addGroup(collapsiblePane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCloseSelectedOnly)
                    .addComponent(cmbTabPlacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        collapsiblePane6.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane6.setTitle("Other properties");
        collapsiblePane6.setFocusable(false);

        chkConfirmCloseMainFrame.setText("Show confirm dialog before closing Main frame");
        chkConfirmCloseMainFrame.setFocusable(false);
        chkConfirmCloseMainFrame.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkConfirmCloseMainFrameItemStateChanged(evt);
            }
        });

        chkConfirmCloseTab.setText("Show confirm dialog before closing Tabs");
        chkConfirmCloseTab.setFocusable(false);
        chkConfirmCloseTab.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkConfirmCloseTabItemStateChanged(evt);
            }
        });

        chkProdQuickView.setText("Show Production Quick View on startup");
        chkProdQuickView.setFocusable(false);
        chkProdQuickView.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkProdQuickViewItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane6Layout = new javax.swing.GroupLayout(collapsiblePane6.getContentPane());
        collapsiblePane6.getContentPane().setLayout(collapsiblePane6Layout);
        collapsiblePane6Layout.setHorizontalGroup(
            collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkProdQuickView)
                    .addComponent(chkConfirmCloseTab)
                    .addComponent(chkConfirmCloseMainFrame))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane6Layout.setVerticalGroup(
            collapsiblePane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane6Layout.createSequentialGroup()
                .addComponent(chkProdQuickView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConfirmCloseMainFrame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConfirmCloseTab)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        collapsiblePane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane1.setTitle("Production Working Calendar");
        collapsiblePane1.setFocusable(false);

        jLabel1.setText("Year starts at:");

        cmbYear.setShowNoneButton(false);
        cmbYear.setShowTodayButton(false);
        cmbYear.setShowWeekNumbers(false);
        cmbYear.setFocusable(false);
        cmbYear.setFormat(new SimpleDateFormat("dd MMMM"));
        cmbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbYearItemStateChanged(evt);
            }
        });

        jLabel2.setText("Day of Week:");

        cmbDayOfWeek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        cmbDayOfWeek.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDayOfWeekItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDayOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(cmbDayOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collapsiblePane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsiblePane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 248, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkConfirmCloseMainFrameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkConfirmCloseMainFrameItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkConfirmCloseMainFrameItemStateChanged

    private void chkConfirmCloseTabItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkConfirmCloseTabItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkConfirmCloseTabItemStateChanged

    private void chkCloseSelectedOnlyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCloseSelectedOnlyItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkCloseSelectedOnlyItemStateChanged

    private void cmbTabPlacementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTabPlacementItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_cmbTabPlacementItemStateChanged

    private void chkDateFormatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkDateFormatItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkDateFormatItemStateChanged

    private void chkCenterTableHeadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCenterTableHeadItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkCenterTableHeadItemStateChanged

    private void chkBoldTableHeadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBoldTableHeadItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkBoldTableHeadItemStateChanged

    private void chkProdQuickViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkProdQuickViewItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_chkProdQuickViewItemStateChanged

    private void cmbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbYearItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
//        if (evt.getStateChange() == ItemEvent.SELECTED) {
//            try {
//                System.out.println(ObjectConverterManager.toString(evt.getItem()));
//                System.out.println(dateFormat.parse(ObjectConverterManager.toString(evt.getItem())));
//                yearStart = dateFormat.format(dateFormat.parse(ObjectConverterManager.toString(evt.getItem())));
//                System.out.println("Date selected: " + yearStart);
//            } catch (ParseException ex) {
//                Logger.getLogger(GeneralPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_cmbYearItemStateChanged

    private void cmbDayOfWeekItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDayOfWeekItemStateChanged
        page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
    }//GEN-LAST:event_cmbDayOfWeekItemStateChanged

    private void initValues() throws ParseException {
//        System.out.println(ConnectDB.pref.get(SettingKeyFactory.General.YEAR_START, ""));
        chkCenterTableHead.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.General.CENTERTABLEHEAD, true));
        chkBoldTableHead.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.General.BOLDTABLEHEAD, true));
        chkDateFormat.setSelectedItem(ConnectDB.pref.get(SettingKeyFactory.General.DATEFORMAT, "MMMM dd, yyyy"));
        chkCloseSelectedOnly.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.General.TABCLOSESELECTEDONLY, true));
        cmbTabPlacement.setSelectedIndex(ConnectDB.pref.getInt(SettingKeyFactory.General.TABPLACEMENT, JTabbedPane.TOP - 1));
        chkConfirmCloseMainFrame.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.General.CONFIRMCLOSEMAINFRAME, false));
        chkConfirmCloseTab.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.General.CONFIRMCLOSETAB, false));
        chkProdQuickView.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.DefaultProperties.SHOWPRODUCTIONQVIEW, false));
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(dateFormat.parse(ConnectDB.pref.get(SettingKeyFactory.General.YEAR_START, yearStart)));
//        cmbYear.setCalendar(cal);
        cmbDayOfWeek.setSelectedItem(ConnectDB.pref.get(SettingKeyFactory.General.DAY_OF_WEEK, "Monday"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    public static javax.swing.JCheckBox chkBoldTableHead;
    public static javax.swing.JCheckBox chkCenterTableHead;
    public static javax.swing.JCheckBox chkCloseSelectedOnly;
    public static javax.swing.JCheckBox chkConfirmCloseMainFrame;
    public static javax.swing.JCheckBox chkConfirmCloseTab;
    public static javax.swing.JComboBox chkDateFormat;
    public static javax.swing.JCheckBox chkProdQuickView;
    public static com.alee.laf.combobox.WebComboBox cmbDayOfWeek;
    public static javax.swing.JComboBox cmbTabPlacement;
    private com.jidesoft.combobox.DateExComboBox cmbYear;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane3;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane4;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane5;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private final AbstractDialogPage page;
    private static String yearStart;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
}
