package mainFrame;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.swing.JideSwingUtilities;
import dashboard.DashBoard;
import eventsPanel.EventsStatistic;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static login.Identification.quickViewFrame;
import static mainFrame.MainFrame._documentPane;
import static mainFrame.MainFrame._frame;
import productionPanel.ProductionPane;
import productionQuickView.ProductionQuickView;
import smartfactoryV2.ConnectDB;
import viewData.ViewHistory;

public class ShortCutPanel extends JPanel {

    public ShortCutPanel(JFrame parent) {
        _parent = parent;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cpMainContainer = new com.jidesoft.pane.CollapsiblePanes();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        jPanel2 = new javax.swing.JPanel();
        btnProduction = new com.jidesoft.swing.JideButton();
        btnEventsStattistic = new com.jidesoft.swing.JideButton();
        btnProductionQuickView = new com.jidesoft.swing.JideButton();
        btnDashBoard = new com.jidesoft.swing.JideButton();
        collapsiblePane2 = new com.jidesoft.pane.CollapsiblePane();
        jPanel5 = new javax.swing.JPanel();
        btnViewHistory = new com.jidesoft.swing.JideButton();
        btnLogOut = new com.jidesoft.swing.JideButton();
        btnCreateUser = new com.jidesoft.swing.JideButton();

        collapsiblePane1.setTitle("Production Management");
        collapsiblePane1.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/session_manager.png"))); // NOI18N
        collapsiblePane1.setContentPane(JideSwingUtilities.createTopPanel(jPanel2));
        collapsiblePane1.setEmphasized(true);
        collapsiblePane1.setFocusable(false);

        jPanel2.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        jPanel2.setOpaque(false);

        btnProduction.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/first_year_production(24).png"))); // NOI18N
        btnProduction.setText("Production");
        btnProduction.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnProduction.setHorizontalAlignment(SwingConstants.LEADING);
        btnProduction.setRequestFocusEnabled(true);
        btnProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductionActionPerformed(evt);
            }
        });

        btnEventsStattistic.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnEventsStattistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/event_time.png"))); // NOI18N
        btnEventsStattistic.setText("Events (HMI)");
        btnEventsStattistic.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnEventsStattistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventsStattisticActionPerformed(evt);
            }
        });

        btnProductionQuickView.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnProductionQuickView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/stock_data_queries.png"))); // NOI18N
        btnProductionQuickView.setText("Production Quick View");
        btnProductionQuickView.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnProductionQuickView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductionQuickViewActionPerformed(evt);
            }
        });

        btnDashBoard.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnDashBoard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dashboard16x16.png"))); // NOI18N
        btnDashBoard.setText("DashBoard");
        btnDashBoard.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnDashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashBoardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEventsStattistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductionQuickView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnProduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEventsStattistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnProductionQuickView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        collapsiblePane2.setTitle("Options");
        collapsiblePane2.setContentPane(JideSwingUtilities.createTopPanel(jPanel5));
        collapsiblePane2.setEmphasized(true);
        collapsiblePane2.setFocusable(false);

        jPanel5.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        jPanel5.setOpaque(false);

        btnViewHistory.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnViewHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/time16x16_2.png"))); // NOI18N
        btnViewHistory.setText("View History");
        btnViewHistory.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnViewHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewHistoryActionPerformed(evt);
            }
        });

        btnLogOut.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/logout16x16.png"))); // NOI18N
        btnLogOut.setText("Log Out");
        btnLogOut.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnCreateUser.setButtonStyle(com.jidesoft.swing.JideButton.HYPERLINK_STYLE);
        btnCreateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/add_user16x16_1.png"))); // NOI18N
        btnCreateUser.setText("Create User");
        btnCreateUser.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 113, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnViewHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout collapsiblePane2Layout = new javax.swing.GroupLayout(collapsiblePane2.getContentPane());
        collapsiblePane2.getContentPane().setLayout(collapsiblePane2Layout);
        collapsiblePane2Layout.setHorizontalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        collapsiblePane2Layout.setVerticalGroup(
            collapsiblePane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout cpMainContainerLayout = new javax.swing.GroupLayout(cpMainContainer);
        cpMainContainer.setLayout(cpMainContainerLayout);
        cpMainContainerLayout.setHorizontalGroup(
            cpMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cpMainContainerLayout.createSequentialGroup()
                .addGroup(cpMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(collapsiblePane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(collapsiblePane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        cpMainContainerLayout.setVerticalGroup(
            cpMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cpMainContainerLayout.createSequentialGroup()
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 146, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cpMainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cpMainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductionActionPerformed
        if (!_documentPane.isDocumentOpened("Production")) {
            try {
                MainFrame.productionPane = new ProductionPane(_frame);
                final DocumentComponent document = new DocumentComponent(MainFrame.productionPane, "Production",
                        "Production", new ImageIcon(this.getClass().getResource("/images/icons/kchart12.png")));
                _documentPane.openDocument(document);
                MainFrame.confirmCloseTab(document);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
        _documentPane.setActiveDocument("Production");
        this.repaint();
    }//GEN-LAST:event_btnProductionActionPerformed

    private void btnEventsStattisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventsStattisticActionPerformed
        if (!_documentPane.isDocumentOpened("Events")) {
            try {
                final DocumentComponent document = new DocumentComponent(new EventsStatistic(_frame), "Events",
                        "Events", new ImageIcon(this.getClass().getResource("/images/icons/kchart12.png")));
                _documentPane.openDocument(document);
                MainFrame.confirmCloseTab(document);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
        _documentPane.setActiveDocument("Events");
        this.repaint();
    }//GEN-LAST:event_btnEventsStattisticActionPerformed

    private void btnProductionQuickViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductionQuickViewActionPerformed
        if (quickViewFrame == null) {
            try {
                quickViewFrame = new JFrame("Production Quick View");
                quickViewFrame.setSize(915, 570);
                quickViewFrame.setContentPane(new ProductionQuickView(quickViewFrame));
                quickViewFrame.setLocationRelativeTo(_frame);
                quickViewFrame.setIconImage(new ImageIcon(_frame.getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
                quickViewFrame.setVisible(true);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        } else {
            quickViewFrame.setVisible(true);
        }
        quickViewFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                quickViewFrame = null;
            }
        });
    }//GEN-LAST:event_btnProductionQuickViewActionPerformed

    private void btnViewHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewHistoryActionPerformed
        if (_documentPane.isDocumentOpened("Production")) {
            machineTitle = ProductionPane.machineTitle;
            date = ProductionPane.cmbPFrom.getDate();
        }
        if (!_documentPane.isDocumentOpened("History")) {
            try {
                final DocumentComponent document = new DocumentComponent(new ViewHistory(machineTitle, date), "History",
                        "History", new ImageIcon(this.getClass().getResource("/images/icons/kchart12.png")));
                _documentPane.openDocument(document);
                MainFrame.confirmCloseTab(document);
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        }
        _documentPane.setActiveDocument("History");
        this.repaint();
    }//GEN-LAST:event_btnViewHistoryActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        MainFrame.actionLogOut();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        MainFrame.actionCreateUser();
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void btnDashBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashBoardActionPerformed
        MainFrame.dashBoardDate = null;
        showDashBoard();
    }//GEN-LAST:event_btnDashBoardActionPerformed

    public static void showDashBoard() {
        try {
            _parent.setVisible(false);
            _dashBoardFrame = new JFrame("Smartfactory Machines DashBoard");
            _dashBoardFrame.setSize(1125, 700);
            _dashBoardFrame.setIconImage(_parent.getIconImage());
            _dashBoard = new DashBoard(_parent, MainFrame.dashBoardDate);
            _dashBoardFrame.getContentPane().add(_dashBoard);
            _dashBoardFrame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    ShortCutPanel._parent.setVisible(true);
                }
            });
            _dashBoardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            _dashBoardFrame.setLocationRelativeTo(null);
            _dashBoardFrame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.swing.JideButton btnCreateUser;
    private com.jidesoft.swing.JideButton btnDashBoard;
    private com.jidesoft.swing.JideButton btnEventsStattistic;
    private com.jidesoft.swing.JideButton btnLogOut;
    private com.jidesoft.swing.JideButton btnProduction;
    private com.jidesoft.swing.JideButton btnProductionQuickView;
    private com.jidesoft.swing.JideButton btnViewHistory;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane2;
    private com.jidesoft.pane.CollapsiblePanes cpMainContainer;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
    private String machineTitle = "";
    private Date date;
    private static JFrame _parent;
    public static JFrame _dashBoardFrame;
    public static DashBoard _dashBoard;
}
