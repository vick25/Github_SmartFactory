package dashboard;

import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class LineChartModel extends DefaultChartModel {

    public DefaultChartModel getModelPoints() {
        return modelPoints;
    }

    public static double getMaxNum() {
        return _maxNum;
    }

    public LineChartModel(final int myConfigNo, final String myQuery, Date myStartDate, Date myEndDate, String unit)
            throws SQLException {
        super();
        boolean _loopQueryFound = false;

        CategoryRange range = new CategoryRange<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(myQuery)) {
            ps.setInt(1, myConfigNo);
            ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(myStartDate));
            ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(myEndDate));
            ConnectDB.res = ps.executeQuery();
            alTime = new ArrayList<>();
            alValues = new ArrayList<>();
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                alTime.add(ConnectDB.res.getString(1)); //Time
                alValues.add(ConnectDB.res.getString(2)); //Values
            }
        }
        if (_loopQueryFound) {
            dateCategoryChart = new ArrayList<>();
            for (String alTime1 : alTime) {
                Category c = new ChartCategory((Object) alTime1.substring(0, 19), range);
                dateCategoryChart.add(c);
                range.add(c);
            }
            this.createModel();
        } else {
        }
    }

    private DefaultChartModel createModel() {
        _maxNum = 0d;
        modelPoints = new DefaultChartModel("Rate");
        for (int i = 0; i < alValues.size(); i++) {
            double value = Double.valueOf(alValues.get(i));
            modelPoints.addPoint(dateCategoryChart.get(i), value);
            if (_maxNum < value) {
                _maxNum = value;
            }
        }
        return modelPoints;
    }

    private static double _maxNum = 0d;
    private DefaultChartModel modelPoints;
    private List<Category> dateCategoryChart = null;
    private final ArrayList<String> alTime, alValues;
}
