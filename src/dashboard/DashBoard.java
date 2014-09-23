package dashboard;

import collapsibleDashboard.CollapsiblePaneDashboard;
import com.jidesoft.dashboard.Gadget;
import com.jidesoft.dashboard.GadgetManager;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import mainFrame.MainMenuPanel;
import resources.Constants;
import resources.ReadPropertiesFile;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

/**
 *
 * @author Victor Kadiata
 */
public class DashBoard extends javax.swing.JPanel {

    public static Date getDate() {
        return dtSpinner.getDate();
    }

    public static boolean isShowTotalProd() {
        return _showTotalProd;
    }

    public static void setShowTotalProd(boolean _showTotalProd) {
        DashBoard._showTotalProd = _showTotalProd;
    }

    public static boolean isShowRateProd() {
        return _showRateProd;
    }

    public static void setShowRateProd(boolean _showRateProd) {
        DashBoard._showRateProd = _showRateProd;
    }

    public static CollapsiblePaneDashboard getColDashBoard() {
        return _colDashBoard;
    }

    public DashBoard(JFrame parent, Date dt) throws Exception {
        ConnectDB.getConnectionInstance();
        initComponents();
        /* Method to call the scheduler to refresh the chart in the dashboard */
        callScheduler();
        //*

        JideButton btnSettings = new JideButton("<html><font color=darkblue size=3><strong>DashBoard Settings "
                + "...</strong></font>");
        btnSettings.setHorizontalAlignment(SwingConstants.CENTER);
        btnSettings.setFocusable(false);
        btnSettings.setOpaque(true);
        btnSettings.setButtonStyle(JideButton.TOOLBOX_STYLE);
        btnSettings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DashBoardSettings dashBoardSettings = new DashBoardSettings(MainMenuPanel.getDashBoardFrame(),
                        true, _colDashBoard.isVertical(), _colDashBoard.getTabbedPane());
                dashBoardSettings.setVisible(true);
            }
        });
        panSettings.add(btnSettings, FlowLayout.LEFT);

        dtSpinner.setFormat(ConnectDB.SDATE_FORMAT_HOUR);
        if (dt == null) {
            Calendar working = ((Calendar) ConnectDB.CALENDAR.clone());
            int hour = ConnectDB.CALENDAR.get(Calendar.HOUR_OF_DAY),
                    minute = ConnectDB.CALENDAR.get(Calendar.MINUTE),
                    second = ConnectDB.CALENDAR.get(Calendar.SECOND);
            working.add(Calendar.HOUR_OF_DAY, -hour);
            working.add(Calendar.MINUTE, -minute);
            working.add(Calendar.SECOND, -second);
            dtSpinner.setDate(working.getTime());
        } else {
            dtSpinner.setDate(dt);
        }
        dtSpinner.getEditor().getEditorComponent().setFocusable(false);

        bslTime.setHorizontalAlignment(SwingConstants.LEADING);
        bslTime.setForeground(Color.GRAY);
        bslTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        bslTime.setHorizontalTextPosition(SwingConstants.RIGHT);
        bslTime.setVerticalTextPosition(SwingConstants.CENTER);
        bslTime.setBusy(true);
        bslTime.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panDashBoard = new javax.swing.JPanel();
        panStatus = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dtSpinner = new com.jidesoft.combobox.DateSpinnerComboBox();
        bslTime = new org.jdesktop.swingx.JXBusyLabel();
        panSettings = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panDashBoard.setBackground(new java.awt.Color(255, 255, 255));
        panDashBoard.setLayout(new java.awt.BorderLayout());

        panStatus.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Starting Time:");
        panStatus.add(jLabel1);

        dtSpinner.setShowNoneButton(false);
        dtSpinner.setShowOKButton(true);
        dtSpinner.setShowWeekNumbers(false);
        dtSpinner.setDate(Calendar.getInstance().getTime());
        dtSpinner.setFocusable(false);
        dtSpinner.setMinimumSize(new java.awt.Dimension(150, 16));
        dtSpinner.setPreferredSize(new java.awt.Dimension(170, 23));
        dtSpinner.setRequestFocusEnabled(false);
        dtSpinner.setTimeDisplayed(true);
        dtSpinner.setTimeFormat("HH:mm:ss");
        panStatus.add(dtSpinner);

        bslTime.setBusy(true);
        bslTime.setFocusable(false);
        bslTime.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panStatus.add(bslTime);

        panSettings.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html><font size=5><center></strong>This Dashboard only displays<br/> today's information. <br/>Use the production toolkit for historical data.</strong></center></font>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void callScheduler() throws Exception {
        ReadPropertiesFile.readConfig();//read the property file to get the time and delay for the schedule
        _colDashBoard = new CollapsiblePaneDashboard(getMachineDummy());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(_colDashBoard, getTimePrecision(Constants.delay),
                getTimePrecision(Constants.timetoquery));
        bslTime.setText(new StringBuilder("Scheduler of ").append(Constants.timetoquery).
                append(" is running to refresh the chart(s) if any is shown ...").toString());
        this.panDashBoard.removeAll();
        this.panDashBoard.add(_colDashBoard.getDemoPanel());
    }

    private String compareDates() throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_LAST_DATA_TIME)) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                return ConnectDB.res.getString(1);
            }
        }
        return null;
    }

    /* Method to check if data is avaialable for the current day and load the machines dynamically
     * on the dashboard
     */
    public void checkAndLoadDataOnCurrentDay() {
        try {
            String lastDBDate = compareDates();
            if (lastDBDate != null) {
                Date logTimeParsed = ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(lastDBDate));
                if (logTimeParsed.compareTo(getDate()) > 0 || logTimeParsed.compareTo(getDate()) == 0) {
                    GadgetManager gadgetManager = _colDashBoard.getTabbedPane().getGadgetManager();
                    byte col = 0;
                    for (Gadget gadgetObject : _colDashBoard.getGadgetList()) {
                        if (gadgetObject != null) {
                            if (col == 3) {
                                col = 0;
                            }
                            gadgetManager.showGadget(gadgetObject,
                                    gadgetManager.getDashboard(gadgetManager.getActiveDashboardKey()), col, 0);
                            col++;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panDashBoard, new StringBuilder("The last recorded data in the database is on the  ").
                            append(lastDBDate.substring(0, 19)).append(". \nPlease, adjust the Dashbaord starting time to the "
                                    + "day and wait for the refresh time.").toString(), "Dashboard", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Machine> getMachineDummy() throws SQLException {
        ArrayList<Machine> machines = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_HARDWARE)) {
            ps.setInt(1, 0);//not selecting the SYSTEM as hardware
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                machines.add(new Machine(resultSet.getInt(1), resultSet.getString(2)));
            }
        }
        return machines;
    }

    private long getTimePrecision(String value) {
        long l = 0;
        String val;
        try {
            if (value.endsWith("d") || value.endsWith("D")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 24 * 60 * 60 * 1000;
            } else if (value.endsWith("h") || value.endsWith("H")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 60 * 60 * 1000;
            } else if (value.endsWith("m") || value.endsWith("M")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 60 * 1000;
            } else if (value.endsWith("s") || value.endsWith("S")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 1000;
            } else {
                l = Long.parseLong(value);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    public static void main(String[] agrs) throws Exception {
        try {
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
            JFrame _dashBoardFrame = new JFrame("Smartfactory Machines DashBoard");
            _dashBoardFrame.setSize(1025, 700);
            DashBoard dashBoard = new DashBoard(null, null);
            _dashBoardFrame.getContentPane().add(dashBoard);
            _dashBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _dashBoardFrame.setLocationRelativeTo(null);
            _dashBoardFrame.setVisible(true);
            dashBoard.checkAndLoadDataOnCurrentDay();
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static org.jdesktop.swingx.JXBusyLabel bslTime;
    public static com.jidesoft.combobox.DateSpinnerComboBox dtSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panDashBoard;
    private javax.swing.JPanel panSettings;
    private javax.swing.JPanel panStatus;
    // End of variables declaration//GEN-END:variables
    private static boolean _showTotalProd = true, _showRateProd = false;
    private static CollapsiblePaneDashboard _colDashBoard;
}
