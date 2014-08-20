package dashboard;

import com.jidesoft.chart.Chart;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JComponent;

/*
 * @author Victor Kadiata
 *
 */
public class GadgetFactory {

    public static Chart getChartTotalProd() {
        return chartTotalProd;
    }

    public static Chart getChartRateProd() {
        return chartRateProd;
    }

    public static JComponent createChart(int[] configNo, String machineTitle, Date startDate)
            throws SQLException {
        _machineTitle = machineTitle;
        _configNo = configNo;
        _startDate = startDate;
        if (DashBoard.isShowTotalProd()) {
            chartTotalProd = new Charts(_machineTitle, _configNo[0], _startDate).getChartTotal();
        } else {
            chartTotalProd = new Chart();
        }
        if (DashBoard.isShowRateProd()) {
            chartRateProd = new Charts(_configNo[1], _startDate).getChartRate();
        } else {
            chartRateProd = new Chart();
        }

        return new VerticalMultiChartPanel(chartTotalProd, chartRateProd);
    }

    private static int[] _configNo;
    private static String _machineTitle;
    private static Date _startDate;
    private static Chart chartTotalProd, chartRateProd;
}
