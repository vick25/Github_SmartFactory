package dashboard;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import mainFrame.ShortCutPanel;
import resources.Constants;
import resources.ReadPropertiesFile;
import collapsibleDashboard.CollapsiblePaneDashboard;
import smartfactoryV2.ConnectDB;

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

    public DashBoard(JFrame parent, Date dt) throws Exception {
        ConnectDB.getConnectionInstance();
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        initComponents();
        /* Method to call the scheduler to refresh the chart in the dashboard */
        callScheduler();
        //*

        JideButton btnSettings = new JideButton("<html><font color=darkblue><strong>DashBoard Settings...</strong></font>");
        btnSettings.setHorizontalAlignment(SwingConstants.CENTER);
        btnSettings.setFocusable(false);
        btnSettings.setOpaque(true);
        btnSettings.setButtonStyle(JideButton.TOOLBOX_STYLE);
        btnSettings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dashBoardSettings == null) {
                    dashBoardSettings = new DashBoardSettings(ShortCutPanel._dashBoardFrame, true,
                            _colDashBoard._vertical, _colDashBoard._tabbedPane);
                    dashBoardSettings.setVisible(true);
                } else {
                    dashBoardSettings.setVisible(true);
                }
            }
        });
        panSettings.add(btnSettings, FlowLayout.LEFT);

        dtSpinner.setFormat(ConnectDB.SDATEFORMATHOUR);
        if (dt == null) {
            Calendar working = ((Calendar) ConnectDB.CALENDAR.clone());
            int hour = ((Calendar) ConnectDB.CALENDAR).get(Calendar.HOUR_OF_DAY),
                    minute = ((Calendar) ConnectDB.CALENDAR).get(Calendar.MINUTE),
                    second = ((Calendar) ConnectDB.CALENDAR).get(Calendar.SECOND);
            working.add(Calendar.HOUR_OF_DAY, -(00 + hour));
            working.add(Calendar.MINUTE, -minute);
            working.add(Calendar.SECOND, -second);
            dtSpinner.setDate(working.getTime());
            dtSpinner.getEditor().getEditorComponent().setFocusable(false);
        } else {
            dtSpinner.setDate(dt);
            dtSpinner.getEditor().getEditorComponent().setFocusable(false);
        }

        bslTime.setHorizontalAlignment(SwingConstants.LEADING);
        bslTime.setForeground(Color.GRAY);
        bslTime.setFont(new Font("Tahoma", Font.PLAIN, 10));
        bslTime.setHorizontalTextPosition(SwingConstants.RIGHT);
        bslTime.setVerticalTextPosition(SwingConstants.CENTER);
        bslTime.setBusy(true);
        bslTime.setVisible(true);
        date = false;
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

        setBackground(new java.awt.Color(255, 255, 255));

        panDashBoard.setBackground(new java.awt.Color(255, 255, 255));
        panDashBoard.setLayout(new java.awt.BorderLayout());

        panStatus.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Starting Day Time:");
        panStatus.add(jLabel1);

        dtSpinner.setShowNoneButton(false);
        dtSpinner.setShowOKButton(true);
        dtSpinner.setShowWeekNumbers(false);
        dtSpinner.setDate(Calendar.getInstance().getTime());
        dtSpinner.setFocusable(false);
        dtSpinner.setMinimumSize(new java.awt.Dimension(150, 16));
        dtSpinner.setPreferredSize(new java.awt.Dimension(150, 23));
        dtSpinner.setRequestFocusEnabled(false);
        dtSpinner.setTimeDisplayed(true);
        dtSpinner.setTimeFormat("HH:mm:ss");
        dtSpinner.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dtSpinnerItemStateChanged(evt);
            }
        });
        panStatus.add(dtSpinner);

        bslTime.setBusy(true);
        bslTime.setFocusable(false);
        bslTime.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        panStatus.add(bslTime);

        panSettings.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dtSpinnerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dtSpinnerItemStateChanged
        if (date) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
//                System.out.println("hjkjjhksd");
            }
        }
        date = true;
    }//GEN-LAST:event_dtSpinnerItemStateChanged

    public final void callScheduler() throws Exception {
        ReadPropertiesFile.readConfig();//read the property file to get the time and delay for the schedule
        _colDashBoard = new CollapsiblePaneDashboard(getMachineDummy(), dtSpinner.getDate());
        timer = new Timer();
        timer.scheduleAtFixedRate(_colDashBoard, getTimePrecision(Constants.delay),
                getTimePrecision(Constants.timetoquery));
        bslTime.setText("Scheduler of " + Constants.timetoquery + " is running to refresh the chart(s) if any "
                + "is shown...");
        this.panDashBoard.removeAll();
        this.panDashBoard.add(_colDashBoard.getDemoPanel());
    }

    private ArrayList<Machine> getMachineDummy() throws SQLException {
        ArrayList<Machine> machines = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT HwNo, Machine FROM hardware\n"
                + "WHERE HwNo > ?")) {
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
            _dashBoardFrame = new JFrame("Smartfactory Machines DashBoard");
            _dashBoardFrame.setSize(1025, 700);
            _dashBoardFrame.getContentPane().add(new DashBoard(null, null));
            _dashBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _dashBoardFrame.setLocationRelativeTo(null);
            _dashBoardFrame.setVisible(true);
        } catch (SQLException ex) {
            ConnectDB.catchSQLException(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static org.jdesktop.swingx.JXBusyLabel bslTime;
    public static com.jidesoft.combobox.DateSpinnerComboBox dtSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panDashBoard;
    private javax.swing.JPanel panSettings;
    private javax.swing.JPanel panStatus;
    // End of variables declaration//GEN-END:variables
    private DashBoardSettings dashBoardSettings;
    private boolean date = false;
    private static boolean _showTotalProd = true, _showRateProd = false;
    public static JFrame _dashBoardFrame;
    public static CollapsiblePaneDashboard _colDashBoard;
    public static Timer timer = null;
}
