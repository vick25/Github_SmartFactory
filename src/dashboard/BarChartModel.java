package dashboard;

import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class BarChartModel extends DefaultChartModel {

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

    public BarChartModel(final int configNo, final String query, final boolean withShifts,
            final String machineTitle, Date start, Date end, SortableTable tableTime) throws SQLException {
        super();
        this._configNo = configNo;
        this._query = query;
        this._withShifts = withShifts;
        this._startD = start;
        this._endD = end;
        this._tableTime = tableTime;

        categoryRange = new CategoryRange<>();
//        findMaxValue.clear();
        logDateHourList.clear();
        datalogValuesList.clear();
        _loopQueryFound = false;
        subtractedDatalogValues = new ArrayList<>();
        PreparedStatement ps = ConnectDB.con.prepareStatement(this._query);
        ps.setInt(1, this._configNo);
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
                final List LOGTIMEWITHSHIFTS = new ArrayList(new TreeSet<>(dateData));//list to store each sorted date retrieved form the values arrays
                for (short q = 0; q < LOGTIMEWITHSHIFTS.size(); ++q) {
                    Category cShifts = new ChartCategory(LOGTIMEWITHSHIFTS.get(q), categoryRange);
                    categoryRange.add(cShifts);
                }
                String queryShift;
                //case with shifts
                sumHourValues = new String[3][LOGTIMEWITHSHIFTS.size()];
                for (short k = 0; k < LOGTIMEWITHSHIFTS.size(); k++) {//dates
                    String fVal = "", sVal = "";
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
                                        fVal = LOGTIMEWITHSHIFTS.get(k).toString() + " "
                                                + this._tableTime.getValueAt(j, 1).toString() + ":00";
                                        sVal = LOGTIMEWITHSHIFTS.get(k).toString() + " " + "23:59:59";
                                    } else {//(00:00:00 to 06:00:00) of the next day
                                        fVal = addDate(LOGTIMEWITHSHIFTS.get(k).toString()) + " " + "00:00:00";
                                        sVal = addDate(LOGTIMEWITHSHIFTS.get(k).toString()) + " "
                                                + this._tableTime.getValueAt(j, 2).toString() + ":00";
                                    }
                                }
                                queryShift = this._query.substring(0, this._query.indexOf("ORDER")).trim()
                                        + "\nAND (dl0.LogTime BETWEEN '" + fVal + "' AND '" + sVal + "')\n"
                                        + "ORDER BY 'Time' ASC";
                                datalogValuesList.clear();
                                runQueryShift(x, queryShift, this._configNo);
                                getSubtractedValues(x, datalogValuesList);
                                x++;
                            }
                        } else {//First and Second shift
                            fVal = LOGTIMEWITHSHIFTS.get(k).toString() + " "
                                    + this._tableTime.getValueAt(j, 1).toString() + ":00";
                            sVal = LOGTIMEWITHSHIFTS.get(k).toString() + " "
                                    + this._tableTime.getValueAt(j, 2).toString() + ":00";
                            queryShift = this._query.substring(0, this._query.indexOf("ORDER")).trim()
                                    + "\nAND (dl0.LogTime BETWEEN '" + fVal + "' AND '" + sVal + "')\n"
                                    + "ORDER BY 'Time' ASC";
                            runQueryShift((byte) -1, queryShift, this._configNo);
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
                    row.add(0, "Shift " + ++shName);
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
            }
        } else {
            /**
             * ************** NO TIME SHIFTS **************
             */
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                logDateHourList.add(ConnectDB.res.getString(1).substring(0, 13));//LogTime only Date and Hour
                datalogValuesList.add(ConnectDB.res.getString(2));//LogData
            }
            ps.close();
            if (_loopQueryFound) {
                subtractedDatalogValues.clear();
                //Method used to send the datalog values to get the difference in row for each hour
                getSubtractedValues((byte) -1, datalogValuesList);
                //
                eachDateHour = new ArrayList(new TreeSet<>(logDateHourList));//sort the Time data and remove duplicates
                List<Category> dateCategoryChart = new ArrayList<>();//List of chart category
                for (String eachDateH : eachDateHour) {
//                    Date time = TimeUtils.createTime(eachDateH1.substring(11));
                    Category c = new ChartCategory((Object) (eachDateH + "h:00").substring(11), categoryRange);
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
            }
        }

        if (_loopQueryFound) {
            subtractedDatalogValues.clear();
        } else {
            subtractedDatalogValues.clear();
        }
    }

    private void getSubtractedValues(byte x, ArrayList alValues) {
        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                if (x == 1) {//Third shift and next day
                    xDiff = Integer.parseInt(alValues.get(i).toString()) - lastValue;
                    subtractedDatalogValues.add(logDateHourList.get(++countAlValues) + ";" + xDiff);
                } else {//Same day
                    xDiff = Integer.parseInt(alValues.get(i).toString()) - Integer.parseInt(alValues.get(i).toString());
                    subtractedDatalogValues.add(logDateHourList.get(i) + ";" + xDiff);
                }
                continue;
            }
            xDiff = Integer.parseInt(alValues.get(i).toString()) - Integer.parseInt(alValues.get(i - 1).toString());
            if (x == 1) {//Third shift and next day
                subtractedDatalogValues.add(logDateHourList.get(++countAlValues) + ";" + xDiff);
            } else {//Same day
                subtractedDatalogValues.add(logDateHourList.get(i) + ";" + xDiff);
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

    private class Interval {

        private final double min, max;

        Interval(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return String.format("[%.1f, %.1f]", min, max);
        }
    }

    private String lastHourValue;
    private volatile boolean _loopQueryFound = false, _withShifts;
    private final ArrayList logDateHourList = new ArrayList(),
            datalogValuesList = new ArrayList();
    private static CategoryRange categoryRange;
    private int countAlValues, lastValue, maxSumValue = 0;
    private final int _configNo;
    private final String _query;
    private final Date _startD, _endD;
    private final SortableTable _tableTime;
    private DefaultChartModel modelPoints = new DefaultChartModel();
    private static String[][] sumHourValues;
    private static List<String> eachDateHour;
    private ArrayList<String> subtractedDatalogValues = null;
}
