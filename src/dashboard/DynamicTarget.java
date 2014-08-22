package dashboard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class DynamicTarget {

    public String getProdStartTime() {
        return prodStartTime;
    }

    public String getProdEndTime() {
        return prodEndTime;
    }

    public DynamicTarget(String machineName) throws SQLException, ParseException {
        _machineName = machineName;
        System.out.println("Machine: " + _machineName);
        int machineID = ConnectDB.getIDMachine(_machineName);

        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT StartTime, EndTime FROM startendtime\n"
                + "WHERE HwNo =?")) {
            ps.setInt(1, machineID);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                prodStartTime = ConnectDB.res.getString(1).substring(0, 5);
                prodEndTime = ConnectDB.res.getString(2).substring(0, 5);
            }
        }
        System.out.println("");
        System.out.println("Start & End Time: " + prodStartTime + "----" + prodEndTime);
        showTime();
    }

    private void showTime() throws ParseException, SQLException {
        long startTime = dateFormat.parse(getProdStartTime()).getTime();
        long earlierStartTime = dateFormat.parse(getProdStartTime().substring(0, 3) + "00").getTime();

        long endTime = dateFormat.parse(getProdEndTime()).getTime();
        long earlierEndtime = dateFormat.parse(getProdEndTime().substring(0, 3) + "00").getTime();

        long s = (startTime - earlierStartTime) / 1000; //in seconds
        long e = (endTime - earlierEndtime) / 1000; //in seconds

        double fraction = 0;
        String targetTimeUnit = ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGETTIMEUNIT, "hour");
        if (null != targetTimeUnit) {
            switch (targetTimeUnit) {
                case "hour":
                    fraction = Double.parseDouble(String.format("%.6f", s / (60d * 60))) / 60;
                    break;
                case "minute":
                    fraction = Double.parseDouble(String.format("%.6f", s / 60d)) / 60;
                    break;
                default:
                    fraction = Double.parseDouble(String.format("%.6f", s));
                    break;
            }
        }

        System.out.println("Start & Earlier Time in second: " + startTime + "----" + earlierStartTime);
        System.out.println("Target Unit: " + targetTimeUnit);
        System.out.println("Time fraction: " + fraction);
        System.out.println("PTF: " + calculateProdTargetFraction(fraction, targetTimeUnit));
    }

    private double calculateProdTargetFraction(double fraction, String targetTimeUnit) throws SQLException {
        double prodTargetFraction = 0;
        targetValue = ConnectDB.getMachineTarget(_machineName, "Cumulative");
        if (null != targetTimeUnit) {
            switch (targetTimeUnit) {
                case "hour":
                    prodTargetFraction = (1 - fraction) * (60 * targetValue);
                    break;
                case "minute":
                    prodTargetFraction = (1 - fraction) * targetValue;
                    break;
                default:
                    prodTargetFraction = (1 - fraction) * (3600 * targetValue);
                    break;
            }
        }
        System.out.println("Total prod target: " + targetValue);
        return prodTargetFraction;
    }

    public static void main(String[] args) throws SQLException, ParseException {
        ConnectDB.getConnectionInstance();
        new DynamicTarget("Balconi");
    }

    private final String _machineName;
    private double targetValue;
    private String prodStartTime;
    private String prodEndTime;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
}
