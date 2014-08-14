package smartfactoryV2;

import com.jidesoft.grid.CsvTableUtils;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import login.Identification;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import setting.SettingKeyFactory;

/**
 *
 * @author Victor Kadiata
 */
public class ConnectDB {

    private static final String KEYCODE = "crypter";
    private static final String DBNAME = "smartfactory";
    private static final String DBUSERNAME = "root";
    private static final String DBPASSWORD = "wnnr123";
//    private static int count = 0;
    public static final int PORTDDOC = 6300;
    public static final int PORTMAINSERVER = 4763;
    public static int lookAndFeel = LookAndFeelFactory.OFFICE2003_STYLE;
    public static Connection con = null;
    public static ResultSet res = null;
    public static final Preferences pref = Preferences.userNodeForPackage(ConnectDB.class);
    public static File tempDir;
    public static String serverIP = "127.0.0.1";
    public static final String DEFAULTDIRECTORY = new File(new JFileChooser().getCurrentDirectory().getAbsolutePath()).getParent();
    public static final String WORKINGDIR = System.getProperty("user.dir");
    public static FileSystemView fsv = FileSystemView.getFileSystemView();
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(pref.get(SettingKeyFactory.General.DATEFORMAT,
            "MMMM dd, yyyy"), new DateFormatSymbols());
    public static final Locale LOCALE = Locale.US;
    public static final Calendar CALENDAR = Calendar.getInstance();
    public static final DecimalFormat DECIMALFORMAT = new DecimalFormat();
    public static final Font TITLEFONT = UIManager.getFont("Label.font").deriveFont(Font.BOLD, 13f);
    public static final String SMARTSERVERPATH = File.listRoots()[0].getAbsolutePath() + "CSIR"
            + File.separator + "SmartFactory" + File.separator + "SmartServer.ini";
//    public static final SimpleDateFormat SDATEFORMAT = new SimpleDateFormat("yyyy-MM-dd", new DateFormatSymbols());
    public static final SimpleDateFormat SDATEFORMATHOUR = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Method to create an instance of the ConnectDB class when the Connection
     * object is null
     *
     * @return con
     */
    public static Connection getConnectionInstance() {
        if (con == null) {
            new ConnectDB();
        }
        return con;
    }

    public static void catchSQLException(SQLException ex) {
        if (ex instanceof CommunicationsException
                || ex instanceof MySQLNonTransientConnectionException) {
            try {
                Identification.okID = false;
                con = null;
                if (SplashScreen.identification != null) {
                    SplashScreen.identification.setVisible(false);
                } else {
                    Identification.dialog.setVisible(false);
                }
                JXErrorPane.showDialog(null, new ErrorInfo("Fatal Error", "Database connection "
                        + "is impossible: Please verify that MySQL server is started,"
                        + " and retry connecting ..." + ex.getMessage(),
                        ex.getMessage(), "Error", ex, Level.SEVERE, null));
                SplashScreen.identification.setVisible(true);
            } catch (Exception e) {
            }
        } else {
            ex.printStackTrace();
        }
    }

    public static String correctApostrophe(String text) {
        String _text = correctBarre(text);
        if (text.contains("\'")) {
            _text = text.replace("\'", "\\'");
        }
        return _text;
    }

    public static String correctToBarreDate(String text) {
        String _text = correctBarre(text);
        if (text.contains("-")) {
            _text = text.replace("-", "/");
        }
        return _text;
    }

    public static String correctBarre(String text) {
        String _text = null;
        if (_text.contains("\\")) {
            _text = text.replace("\\", "\\\\");
        }
        return _text;
    }

    public static String correctBarreFileName(String text) {
        String _text = correctBarre(text);
        if (text.contains("/")) {
            _text = text.replace("/", "");
        }
        if (text.contains("\\")) {
            _text = text.replace("\\", "");
        }
        if (text.contains(":")) {
            _text = text.replace(":", "");
        }
        return _text.replace(" ", "").trim();
    }

    public static String firstLetterCapital(String s) {
       String _text = correctApostrophe(s);
        if (_text.isEmpty()) {
            return _text;
        } else {
            return _text.substring(0, 1).toUpperCase() + _text.substring(1);
        }
    }

    public static String capitalLetter(String s) {
        String _text = correctApostrophe(s);
        if (_text.isEmpty()) {
            return _text;
        } else {
            return _text.toUpperCase();
        }
    }

    public static String crypter(String s) {
        int code = (KEYCODE.hashCode() % 5 == 0 ? (KEYCODE.length() % 2 == 1 ? -1 : 1) * 3
                : (KEYCODE.length() % 2 == 1 ? -1 : 1) * KEYCODE.hashCode() % 5);
        char[] chars;
        chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] - code);
        }
        return new String(chars);
    }

    public static String decrypter(String s) {
        int code = (KEYCODE.hashCode() % 5 == 0 ? (KEYCODE.length() % 2 == 1 ? -1 : 1) * 3
                : (KEYCODE.length() % 2 == 1 ? -1 : 1) * KEYCODE.hashCode() % 5);
        char[] chars;
        chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + code);
        }
        return new String(chars);
    }

    public static String getDefaultDirectory() {
        return pref.get(SettingKeyFactory.DefaultProperties.DEFAULTDIRECTORY, DEFAULTDIRECTORY);
    }

    public static void setDefaultDirectory(String dir) {
        pref.put(SettingKeyFactory.DefaultProperties.DEFAULTDIRECTORY, dir);
    }

    public static Color[] colors3() {
        final Color BACKGROUND1 = new Color(253, 253, 244);
        final Color BACKGROUND2 = new Color(230, 230, 255);
        final Color BACKGROUND3 = new Color(210, 255, 210);
        Color[] cs = {BACKGROUND1, BACKGROUND2, BACKGROUND3};
        return cs;
    }

    public static Color getColorFromKey(String value) {
        String tab[] = value.split(", ");
        return new Color(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
    }

    public static boolean checkTableValidity(SortableTable table) {
        boolean checked = false;
        for (int i = 0; i < table.getRowCount(); i++) {
            checked = !table.getValueAt(i, 1).toString().equals("00:00")
                    && !table.getValueAt(i, 2).toString().equals("00:00")
                    && table.getValueAt(i, 1).toString().contains(":")
                    && table.getValueAt(i, 2).toString().contains(":");
        }
        return checked;
    }

    public static boolean checkEmailValidity(String emailText, Component comp) {
        if (!emailText.isEmpty()) {
            if (emailText.contains("@") && emailText.contains(".")) {
                return true;
            } else {
                JOptionPane.showMessageDialog(comp, "E-mail address is not valid !!!"
                        + " ==> \"@,.\" ", "Error", JOptionPane.ERROR_MESSAGE);
                comp.requestFocus();
                return false;
            }
        } else {
            return true;
        }
    }

    public static int maxNumber(List list) {
        if (list.size() > 0) {
            int[] numbers = new int[list.toArray().length];
            for (int i = 0; i < list.toArray().length; i++) {
                numbers[i] = (int) (Double.parseDouble(list.get(i).toString()));
            }
            int maxNum = numbers[0];
            for (int i = 1; i < numbers.length;) {
                int j = numbers[i];
                if (j > maxNum) {
                    maxNum = j;
                }
                i++;
            }
            return maxNum;
        } else {
            return 0;
        }
    }

    public static int getMachineID(String machine) {
        int IDMachine = -1;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT HwNo, Machine FROM hardware "
                + "WHERE Machine =?")) {
            ps.setString(1, ConnectDB.firstLetterCapital(machine));
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                if (ConnectDB.res.getString(2).equalsIgnoreCase(machine)) {
                    IDMachine = ConnectDB.res.getInt(1);
                }
            }
        } catch (Exception ex) {
        }
        return IDMachine;
    }

    public static int getConfigNo(String channelID, String machine) {
        int IDChannel = -1;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT ConfigNo\n"
                + "FROM `configuration` c, `hardware` h\n"
                + "WHERE c.HwNo = h.HwNo\n"
                + "AND h.HwNo =?\n"
                + "AND ChannelID =? ORDER BY ACTIVE")) {
            ps.setInt(1, getMachineID(machine));
            ps.setString(2, channelID);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                IDChannel = ConnectDB.res.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return IDChannel;
    }

    public static double getMachineTarget(String machineName, String configType) throws SQLException {
        double targetValue = 0d;
        int configNo = -1;
        String query = "SELECT DISTINCT c.ConfigNo FROM configuration c, hardware h\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND c.AvMinMax = '" + configType + "'\n"
                + "AND h.Machine = '" + machineName + "'\n"
                + "AND c.Active = 1 ORDER BY h.HwNo ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                configNo = ConnectDB.res.getInt(1);
            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT TargetValue FROM target\n"
                + "WHERE Machine =?\n"
                + "AND ConfigNo =?")) {
            ps.setString(1, machineName);
            ps.setInt(2, configNo);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                targetValue = ConnectDB.res.getDouble(1);
            }
        }
        return targetValue;
    }

    public static double[] getTimeDifference(Date d1, Date d2) {
        //        System.out.println(d1 + "   " + d2);
        double[] result = new double[4];
        CALENDAR.setTimeZone(TimeZone.getTimeZone("UTC"));
        CALENDAR.setTime(d1);

        long t1 = CALENDAR.getTimeInMillis();
        CALENDAR.setTime(d2);

        long diff = Math.abs(CALENDAR.getTimeInMillis() - t1);
//        final int ONE_DAY = 1000 * 60 * 60 * 24;
//        final int ONE_HOUR = ONE_DAY / 24;
//        final int ONE_MINUTE = ONE_HOUR / 60;
//        final int ONE_SECOND = ONE_MINUTE / 60;

//        long d = diff / ONE_DAY;
//        diff %= ONE_DAY;
//
//        long h = diff / ONE_HOUR + (d * 24);
//        diff %= ONE_HOUR;
//
//        long m = diff / ONE_MINUTE + (h * 60);
//        diff %= ONE_MINUTE;
//
//        long s = diff / ONE_SECOND + (h * 3600);
//        long ms = diff % ONE_SECOND;
        long s = diff / 1000;
        double m = Double.parseDouble(String.format("%.6f", s / 60d));
        double h = new BigDecimal(String.format("%.5f", s / (60d * 60))).doubleValue();
        long d = diff / (24 * 60 * 60 * 1000);
        result[0] = d;
        result[1] = h;
        result[2] = m;
        result[3] = s;
//        result[4] = ms;
        return result;
    }

    public static void printDiffs(long[] diffs) {
        //        System.out.printf("Days:         %3d\n", diffs[0]);
        System.out.printf("Hours:        %3d\n", diffs[1]);
        System.out.printf("Minutes:      %3d\n", diffs[2]);
        System.out.printf("Seconds:      %3d\n", diffs[3]);
//        System.out.printf("Milliseconds: %3d\n", diffs[4]);
    }

    public static String removeLineBreaks(String inputText) {
        try {
            Pattern p = Pattern.compile("\n");
            Matcher m = p.matcher(inputText);
            return m.replaceAll("").trim();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static void outputToExcel(SortableTable table, File file) {
        try {
            HssfTableUtils.export(table, file.getAbsolutePath() + ".xls", "SortableTable",
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void outputToCsv(SortableTable table, File file) {
        try {
            CsvTableUtils.export(table, file.getAbsolutePath() + ".csv", true,
                    new HssfTableUtils.DefaultCellValueConverter() {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public ConnectDB() {
        Locale.setDefault(ConnectDB.LOCALE);
        JComponent.setDefaultLocale(ConnectDB.LOCALE);
        ToolTipManager.sharedInstance().setDismissDelay(20000);
        ToolTipManager.sharedInstance().setInitialDelay(100);
        DECIMALFORMAT.setMaximumFractionDigits(3);
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            serverIP = pref.get(SettingKeyFactory.Connection.SERVERIPADDRESS, serverIP);
            /* Get the previous theme(lookAndFeel) used */
//            System.out.println(serverIP + " " + ++count);
            lookAndFeel = pref.getInt(SettingKeyFactory.Theme.LOOKANDFEEL, lookAndFeel);
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
            LookAndFeelFactory.installJideExtension(lookAndFeel);//Set the theme
            /* Server Connection */
            con = DriverManager.getConnection("jdbc:mysql://" + serverIP + ":3306/" + DBNAME
                    + "?dontTrackOpenResources=true&allowMultiQueries=true", DBUSERNAME, DBPASSWORD);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            catchSQLException(ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
