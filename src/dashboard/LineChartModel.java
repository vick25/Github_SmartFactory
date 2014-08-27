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

    public final void setModelPoints(DefaultChartModel model) {
        this.modelPoints = model;
    }

    public static double getMaxNum() {
        return _maxNum;
    }

    public LineChartModel(final int configNo, final String query, Date start, Date end, String unit)
            throws SQLException {
        super();
        this._configNo = configNo;
        this._query = query;
        this._startD = start;
        this._endD = end;
//        this._unit = unit;

        range = new CategoryRange<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(this._query)) {
            int i = 1;
            ps.setInt(i++, this._configNo);
            ps.setString(i++, ConnectDB.SDATE_FORMAT_HOUR.format(this._startD));
            ps.setString(i++, ConnectDB.SDATE_FORMAT_HOUR.format(this._endD));
            ConnectDB.res = ps.executeQuery();
            alTime.clear();
            alValues.clear();
            while (ConnectDB.res.next()) {
                _loopQueryFound = true;
                alTime.add(ConnectDB.res.getString(1)); //Time
                alValues.add(ConnectDB.res.getString(2)); //Values
            }
        }
        if (_loopQueryFound) {
            for (String alTime1 : alTime) {
                Category c = new ChartCategory((Object) alTime1.substring(0, 19), range);
                dateCategoryChart.add(c);
                range.add(c);
            }
            setModelPoints(createModel());
        } else {
        }
    }

    private DefaultChartModel createModel() {
        _maxNum = 0d;
        DefaultChartModel modelChart = new DefaultChartModel("Rate");
        for (int i = 0; i < alValues.size(); i++) {
            double value = Double.valueOf(alValues.get(i));
            modelChart.addPoint(dateCategoryChart.get(i), value);
            if (_maxNum < value) {
                _maxNum = value;
            }
        }
        return modelChart;
    }

    private final int _configNo;
    private boolean _loopQueryFound = false;
    private static double _maxNum = 0d;
    private final String _query;
    private final Date _startD, _endD;
    private DefaultChartModel modelPoints = new DefaultChartModel();
    private static final List<Category> dateCategoryChart = new ArrayList<>();
    private static final ArrayList<String> alTime = new ArrayList<>(), alValues = new ArrayList<>();
    public static CategoryRange range;
}
