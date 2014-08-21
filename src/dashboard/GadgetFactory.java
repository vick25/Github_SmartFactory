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

    private GadgetFactory() {
    }

    public static JComponent createChart(int[] configNo, String machineTitle, Date dashBoardStartDate)
            throws SQLException {
        _machineTitle = machineTitle;
        _configNo = configNo;
        _startDate = dashBoardStartDate;
        if (DashBoard.isShowTotalProd()) {
            //create the cumulative bar chart
            chartTotalProd = new Charts(_machineTitle, _configNo[0], _startDate).getChartTotal();
        } else {
            //create an empty chart
            chartTotalProd = new Chart();
        }
        if (DashBoard.isShowRateProd()) {
            //create the rate chart
            chartRateProd = new Charts(_configNo[1], _startDate).getChartRate();
        } else {
            //create an empty chart
            chartRateProd = new Chart();
        }

        return new VerticalMultiChartPanel(chartTotalProd, chartRateProd);//panel returned with the chart
    }

    private static int[] _configNo;
    private static String _machineTitle;
    private static Date _startDate;
    private static Chart chartTotalProd, chartRateProd;
}
