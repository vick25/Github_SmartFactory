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
        alDateHour.clear();
        alValues.clear();
        _loopQueryFound = false;
        subtractValues = new ArrayList<>();
        PreparedStatement ps = ConnectDB.con.prepareStatement(this._query);
        ps.setInt(1, this._configNo);
        ps.setString(2, ConnectDB.SDATEFORMATHOUR.format(this._startD));
        ps.setString(3, ConnectDB.SDATEFORMATHOUR.format(this._endD));
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
                        alValues.clear();
                        alDateHour.clear();
                        subtractValues.clear();
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
                                alValues.clear();
                                runQueryShift(x, queryShift, this._configNo);
                                getSubtractedValues(x, alValues);
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
                                getSubtractedValues((byte) -1, alValues);//send the values to get the difference in each hour
                                for (String subtractValue : subtractValues) {
                                    if (subtractValue.contains(LOGTIMEWITHSHIFTS.get(k).toString())) {
                                        String[] val = subtractValue.split(";");
                                        sum += Integer.parseInt(val[1]);
                                    }
                                }
                            } else {//Third shift
                                int v = k;
                                while (v < LOGTIMEWITHSHIFTS.size()) {
                                    for (String subtractValue : subtractValues) {
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
                alDateHour.add(ConnectDB.res.getString(1).substring(0, 13));//only the Date and Hour
                alValues.add(ConnectDB.res.getString(2));
            }
            ps.close();
            if (_loopQueryFound) {
                subtractValues.clear();
                getSubtractedValues((byte) -1, alValues);//send the values to get the difference in each hour
                eachDateH = new ArrayList(new TreeSet<>(alDateHour));//sort the Time data and remove duplicates
                List<Category> dateCategoryChart = new ArrayList<>();
                for (String eachDateH1 : eachDateH) {
//                    Date time = TimeUtils.createTime(eachDateH.get(i).substring(11));
                    Category c = new ChartCategory((Object) (eachDateH1 + "h:00").substring(11), categoryRange);
//                    Category c = new ChartCategory(new TimePosition(time.getTime()), range);
                    dateCategoryChart.add(c);
                    categoryRange.add(c);
                }
                DefaultChartModel modelShift = new DefaultChartModel("Hourly Total parts");
                for (int j = 0; j < dateCategoryChart.size(); ++j) {
                    int sum = 0;
                    for (String subtractValue : subtractValues) {
                        if (subtractValue.contains(eachDateH.get(j))) {
                            String[] val = subtractValue.split(";");
                            sum += Integer.parseInt(val[1]);
                        }
                    }
                    if (sum > maxSumValue) {
                        maxSumValue = sum;
                    }
                    if (j == dateCategoryChart.size() - 1) {
                        VerticalMultiChartPanel.setActualBarValue(sum);
                    }
                    modelShift.addPoint(dateCategoryChart.get(j), sum);
                }
                this.setModelPoints(modelShift);
            }
        }

        if (_loopQueryFound) {
            subtractValues.clear();
        } else {
            subtractValues.clear();
        }
    }

    private void getSubtractedValues(byte x, ArrayList alValues) {
        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                if (x == 1) {//Third shift and next day
                    xDiff = Integer.parseInt(alValues.get(i).toString()) - lastValue;
                    subtractValues.add(alDateHour.get(++countAlValues) + ";" + xDiff);
                } else {//Same day
                    xDiff = Integer.parseInt(alValues.get(i).toString()) - Integer.parseInt(alValues.get(i).toString());
                    subtractValues.add(alDateHour.get(i) + ";" + xDiff);
                }
                continue;
            }
            xDiff = Integer.parseInt(alValues.get(i).toString()) - Integer.parseInt(alValues.get(i - 1).toString());
            if (x == 1) {//Third shift and next day
                subtractValues.add(alDateHour.get(++countAlValues) + ";" + xDiff);
            } else {//Same day
                subtractValues.add(alDateHour.get(i) + ";" + xDiff);
            }
        }
    }

    private void runQueryShift(byte x, String query, int configNo) throws SQLException {
        try (PreparedStatement psShift = ConnectDB.con.prepareStatement(query)) {
            psShift.setInt(1, configNo);
            psShift.setString(2, ConnectDB.SDATEFORMATHOUR.format(this._startD));
            psShift.setString(3, ConnectDB.SDATEFORMATHOUR.format(this._endD));
            ConnectDB.res = psShift.executeQuery();
//            System.out.println(psShift.toString());
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                alDateHour.add(ConnectDB.res.getString(1).substring(0, 13));//Date and Hour
                alValues.add(ConnectDB.res.getString(2));
            }
            if (x == 0) {
                countAlValues = alValues.size() - 1;
                if (countAlValues != -1) {
                    lastValue = Integer.parseInt(alValues.get(countAlValues).toString());
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

    private volatile boolean _loopQueryFound = false, _withShifts;
    private final ArrayList alDateHour = new ArrayList(), alValues = new ArrayList();
    private static CategoryRange categoryRange;
    private int countAlValues, lastValue, maxSumValue = 0;
    private final int _configNo;
    private final String _query;
    private final Date _startD, _endD;
    private final SortableTable _tableTime;
    private DefaultChartModel modelPoints = new DefaultChartModel();
    private static String[][] sumHourValues;
    private static List<String> eachDateH;
    private static ArrayList<String> subtractValues = null;
}
