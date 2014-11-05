package dashboard;

import chartTypes.CumulativeSubractedValues;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import mainFrame.MainFrame;
import mainFrame.MainMenuPanel;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

/**
 *
 * @author Victor Kadiata
 */
public class BarChartModel extends DefaultChartModel implements CumulativeSubractedValues {

    public DefaultChartModel getModelPoints() {
        return modelPoints;
    }

    private void setModelPoints(DefaultChartModel modelPoints) {
        this.modelPoints = modelPoints;
    }

    public static CategoryRange getCategoryRange() {
        return categoryRange;
    }

    public int getMaxSumValue() {
        return maxSumValue;
    }

    public String getLastHourValue() {
        return lastHourValue;
    }

    public BarChartModel(final int myConfigNo, final String myQuery, final boolean withShifts,
            final String myMachineTitle, Date start, Date end, SortableTable tableTime) throws SQLException {
        super();
        modelPoints = new DefaultChartModel();
        this._withShifts = withShifts;
        this._startD = start;
        this._endD = end;
        this._tableTime = tableTime;
        this.machineTitle = myMachineTitle;

        categoryRange = new CategoryRange<>();
//        findMaxValue.clear();
        logDateHourList.clear();
        datalogValuesList.clear();
        _loopQueryFound = false;
        subtractedDatalogValues = new ArrayList<>();
        PreparedStatement ps = ConnectDB.con.prepareStatement(myQuery);
        ps.setInt(1, myConfigNo);
        ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(this._startD));
        ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(this._endD));
//        System.out.println(ps.toString());
        ConnectDB.res = ps.executeQuery();
        //End SQL query

        if (this._withShifts) {
            /**
             * ************ CASE WITH SHIFTS ************
             */
            ArrayList<String> dateData = new ArrayList<>();//get only the date from the database
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                String logTime = ConnectDB.res.getString(1);
                dateData.add(logTime.substring(0, 10));//list to store each sorted date retrieved form the values arrays
            }
            ps.close();
            if (_loopQueryFound) {
                MainFrame.setDashBoardDate(DashBoard.getDate());
                final List LOGTIMEWITHSHIFTS = new ArrayList(new TreeSet<>(dateData));//list to store each sorted date retrieved form the values arrays
                for (short q = 0; q < LOGTIMEWITHSHIFTS.size(); ++q) {
                    Category cShifts = new ChartCategory(LOGTIMEWITHSHIFTS.get(q), categoryRange);
                    categoryRange.add(cShifts);
                }
                StringBuilder queryShift;
                //case with shifts
                sumHourValues = new String[3][LOGTIMEWITHSHIFTS.size()];
                for (short k = 0; k < LOGTIMEWITHSHIFTS.size(); k++) {//dates
                    StringBuilder fVal = null, sVal = null;
                    for (short j = 0; j < 3; j++) {//shifts as row.
                        datalogValuesList.clear();
                        logDateHourList.clear();
                        subtractedDatalogValues.clear();
                        if (j == 2) {//if line is 3rd shift
                            byte x = 0;//shift splited in 2 periods
                            while (x <= 1) {
                                if (this._tableTime.getValueAt(j, 1).toString().substring(0, 2).equals("22")
                                        || (Integer.parseInt(this._tableTime.getValueAt(j, 1).toString().
                                                substring(0, 2)) < 24)) {
                                    if (x == 0) {//first split
                                        fVal = new StringBuilder().append(LOGTIMEWITHSHIFTS.get(k).toString()).
                                                append(" ").append(this._tableTime.getValueAt(j, 1).toString()).append(":00");
                                        sVal = new StringBuilder().append(LOGTIMEWITHSHIFTS.get(k).toString()).append(" 23:59:59");
                                    } else {//(00:00:00 to 06:00:00) of the next day
                                        fVal = new StringBuilder().append(addDate(LOGTIMEWITHSHIFTS.get(k).toString()))
                                                .append(" 00:00:00");
                                        sVal = new StringBuilder().append(addDate(LOGTIMEWITHSHIFTS.get(k).toString()))
                                                .append(" ").append(this._tableTime.getValueAt(j, 2).toString()).append(":00");
                                    }
                                }
                                queryShift = new StringBuilder().append(myQuery.substring(0, myQuery.
                                        indexOf("ORDER")).trim()).append("\nAND (dl0.LogTime BETWEEN '").
                                        append(fVal.toString()).append("' AND '").append(sVal.toString()).append("')\nORDER BY 'Time' ASC");
                                datalogValuesList.clear();
                                runQueryShift(x, queryShift.toString(), myConfigNo);
                                getSubtractedValues(x, datalogValuesList);
                                x++;
                            }
                        } else {//First and Second shift
                            fVal = new StringBuilder().append(LOGTIMEWITHSHIFTS.get(k).toString()).append(" ").
                                    append(this._tableTime.getValueAt(j, 1).toString()).append(":00");
                            sVal = new StringBuilder().append(LOGTIMEWITHSHIFTS.get(k).toString()).append(" ").
                                    append(this._tableTime.getValueAt(j, 2).toString()).append(":00");
                            queryShift = new StringBuilder().append(myQuery.substring(0, myQuery.
                                    indexOf("ORDER")).trim()).append("\nAND (dl0.LogTime BETWEEN '").
                                    append(fVal.toString()).append("' AND '").append(sVal.toString()).
                                    append("')\nORDER BY 'Time' ASC");
                            runQueryShift((byte) -1, queryShift.toString(), myConfigNo);
                        }
                        int sum = 0;
                        if (_loopQueryFound) {
                            if (j != 2) {//First and Second shift
                                getSubtractedValues((byte) -1, datalogValuesList);//send the values to get the difference in each hour
                                for (String subtractValue : subtractedDatalogValues) {
                                    if (subtractValue.contains(LOGTIMEWITHSHIFTS.get(k).toString())) {
                                        String[] val = subtractValue.split(";");
                                        sum += Integer.parseInt(val[1]);
                                    }
                                }
                            } else {//Third shift
                                int v = k;
                                while (v < LOGTIMEWITHSHIFTS.size()) {
                                    for (String subtractValue : subtractedDatalogValues) {
                                        if (subtractValue.contains(LOGTIMEWITHSHIFTS.get(v).toString())) {
                                            String[] val = subtractValue.split(";");
                                            sum += Integer.parseInt(val[1]);
                                        }
                                    }
                                    v++;
                                }
                            }
                            int w = k;//copy the index of each date as k to w
                            sumHourValues[j][w] = String.valueOf(sum);
                            if (sum > maxSumValue) {
                                maxSumValue = sum;
                            }
                        }
                    }//end of each shift in the tableTime
                }//end of each date

                List<List<String>> list = new ArrayList<>();
                for (String[] row : sumHourValues) {
                    list.add(new ArrayList<>(Arrays.asList(row)));//Treat each row of timeDifference as an array
                }

                byte shName = 0;
                DefaultChartModel modelShift = null;
                for (List<String> row : list) {
                    row.add(0, new StringBuilder("Shift ").append(++shName).toString());
                    String modelName = row.remove(0);//get each line of data
                    modelShift = new DefaultChartModel(modelName);
                    int column = 1;
                    for (String rowValue : row) {
                        Double value = (rowValue == null) ? Double.valueOf("0") : Double.parseDouble(rowValue);
                        modelShift.addPoint(categoryRange.getCategory(column), value);
                        column++;
                    }
                }
                this.setModelPoints(modelShift);
                list.clear();
            } else {
            }
        } else {
            /**
             * ************** NO TIME SHIFTS **************
             */
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                String logDateHour = ConnectDB.res.getString(1);
                logDateHourList.add(logDateHour.substring(0, 13));//LogTime only Date and Hour
                datalogValuesList.add(ConnectDB.res.getString(2));//LogData
            }
            ps.close();
            if (_loopQueryFound) {
                MainFrame.setDashBoardDate(DashBoard.getDate());
                subtractedDatalogValues.clear();
                //Method used to send the datalog values to get the difference in row for each hour
                getSubtractedValues((byte) -1, datalogValuesList);
                //
                eachDateHour = new ArrayList(new TreeSet<>(logDateHourList));//sort the Time data and remove duplicates
                List<Category> dateCategoryChart = new ArrayList<>();//List of chart category
                for (String eachDateH : eachDateHour) {
//                    Date time = TimeUtils.createTime(eachDateH1.substring(11));
                    Category c = new ChartCategory((Object) new StringBuilder().append(eachDateH).append("h:00")
                            .substring(11), categoryRange);
//                    Category d = new ChartCategory(new TimePosition(time.getTime()), categoryRange);
                    dateCategoryChart.add(c);
                    categoryRange.add(c);
                }
                DefaultChartModel modelShift = new DefaultChartModel("Hourly Total parts");
                int categoryListLength = dateCategoryChart.size();
                for (int j = 0; j < categoryListLength; ++j) {
                    int sum = 0;
                    for (String subtractValue : subtractedDatalogValues) {
                        if (subtractValue.contains(eachDateHour.get(j))) {
                            String[] value = subtractValue.split(";");
                            sum += Integer.parseInt(value[1]);
                        }
                    }
                    //Get the maximum sum of values
                    if (sum > maxSumValue) {
                        maxSumValue = sum;
                    }
                    //Get the sum of the last hour value as the chart actual bar value
                    if (j == (categoryListLength - 1)) {
                        this.lastHourValue = eachDateHour.get(j);
                        VerticalMultiChartPanel.setActualBarValue(sum);
                    }
                    modelShift.addPoint(dateCategoryChart.get(j), sum);
                }
                this.setModelPoints(modelShift);
                //change dashboard message
            } else {
                DashBoard.getBslTime().setText("Chart's loading failed.");
                if (DashBoardTimer.isShowChartDataMessage()) {
                    JOptionPane.showMessageDialog(MainMenuPanel.getDashBoardFrame(), new StringBuilder("<html>Please, "
                            + "adjust the Dashbaord starting time to this date: <font color=red><b>").
                            append(DashBoard.getLastDBDate().substring(0, 10)).append("</b></font>,<br>and retry.").toString(),
                            "Dashboard", JOptionPane.ERROR_MESSAGE);
                }
                modelPoints = null;
            }
        }
        subtractedDatalogValues.clear();
    }

    @Override
    public void getSubtractedValues(byte x, ArrayList<String> alValues) throws SQLException {
        ArrayList<String> prodRateArrayList = getProductionRate();

        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                if (x == 1) {//Third shift and next day
                    xDiff = Integer.parseInt(alValues.get(i)) - lastValue;
                    if (xDiff < 0) {//Case the difference is a negative value
                        xDiff = 1000000 - lastValue + Integer.parseInt(alValues.get(i));
                    }
                    subtractedDatalogValues.add(new StringBuilder().append(logDateHourList.get(++countAlValues)).append(";").append(xDiff).toString());
                } else {//Same day
                    xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i));
                    subtractedDatalogValues.add(new StringBuilder().append(logDateHourList.get(i)).append(";").append(xDiff).toString());
                }
                continue;
            }
            int sum = 0;
            xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i - 1));
            if (xDiff < 0) {//Case where the value is negative (rollover)
                xDiff = 1000000 - Integer.parseInt(alValues.get(i - 1)) + Integer.parseInt(alValues.get(i));
            } else if (xDiff > 500) {
                byte rollback = 5;
                int yr = i;
                while (rollback > 5) {
                    System.out.println("jump over value recorded");
                    try {
                        sum += (int) Double.parseDouble(prodRateArrayList.get(yr));
                        yr--;
                        rollback--;
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                xDiff = sum / rollback;
            }

//            try {
//                int cumulVal = Integer.parseInt(alValues.get(i - 1)),
//                        nextCumulVal = Integer.parseInt(alValues.get(i)),
//                        rateVal = (int) Double.parseDouble(prodRateArrayList.get(i + 1));
//                //Case where the cumulative values are not sequential by adding the production rate number
//                if ((cumulVal + rateVal + 1 == nextCumulVal) || (cumulVal + rateVal == nextCumulVal)
//                        || (cumulVal + rateVal + 2 == nextCumulVal)) {
//                    xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i - 1));
//                    if (xDiff < 0) {//Case where the value is negative
//                        xDiff = 1000000 - Integer.parseInt(alValues.get(i - 1)) + Integer.parseInt(alValues.get(i));
//                    }
//                } else {// Not sequential
//                    xDiff = rateVal;
//                }
////                System.out.println(cumulVal + " -- " + nextCumulVal + " -- " + rateVal + " --- " + xDiff);
//            } catch (IndexOutOfBoundsException e) {
//                xDiff = (int) Double.parseDouble(prodRateArrayList.get(i - 1));
//            }
            if (x == 1) {//Third shift and next day
                subtractedDatalogValues.add(new StringBuilder().append(logDateHourList.get(++countAlValues)).append(";").append(xDiff).toString());
            } else {//Same day
                subtractedDatalogValues.add(new StringBuilder().append(logDateHourList.get(i)).append(";").append(xDiff).toString());
            }
        }
    }

    private void runQueryShift(byte x, String query, int configNo) throws SQLException {
        try (PreparedStatement psShift = ConnectDB.con.prepareStatement(query)) {
            psShift.setInt(1, configNo);
            psShift.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(this._startD));
            psShift.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(this._endD));
            ConnectDB.res = psShift.executeQuery();
//            System.out.println(psShift.toString());
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                logDateHourList.add(ConnectDB.res.getString(1).substring(0, 13));//Date and Hour
                datalogValuesList.add(ConnectDB.res.getString(2));
            }
            if (x == 0) {
                countAlValues = datalogValuesList.size() - 1;
                if (countAlValues != -1) {
                    lastValue = Integer.parseInt(datalogValuesList.get(countAlValues).toString());
                }
            }
        }
    }

    private String addDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date date = formatter.parse(dateInString);
            c.setTime(date);
            c.add(Calendar.DATE, 1);//add one day
        } catch (ParseException e) {
        }
        return formatter.format(c.getTime());
    }

    private ArrayList<String> getProductionRate() throws SQLException {
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
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.DATALOG_PRODUCTION)) {
            ps.setInt(1, configNo);
            ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(_startD));
            ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(_endD));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                listProductionRate.add(resultSet.getString(2)); //Values
            }
        }
        return listProductionRate;
    }

//    private class Interval {
//
//        private final double min, max;
//
//        Interval(double min, double max) {
//            this.min = min;
//            this.max = max;
//        }
//
//        @Override
//        public String toString() {
//            return String.format("[%.1f, %.1f]", min, max);
//        }
//    }
    private String lastHourValue;
    private final String machineTitle;
    private volatile boolean _loopQueryFound = false, _withShifts;
    private final ArrayList logDateHourList = new ArrayList(),
            datalogValuesList = new ArrayList();
    private static CategoryRange categoryRange;
    private int countAlValues, lastValue, maxSumValue = 0;
    private final Date _startD, _endD;
    private final SortableTable _tableTime;
    private DefaultChartModel modelPoints;
    private static String[][] sumHourValues;
    private static List<String> eachDateHour;
    private ArrayList<String> subtractedDatalogValues = null;
}
