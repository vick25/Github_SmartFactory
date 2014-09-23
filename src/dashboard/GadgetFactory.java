package dashboard;

import com.jidesoft.chart.Chart;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JComponent;

/*
 *
 * @author Victor Kadiata
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

    public static JComponent createChart(int[] myConfigNo, String myMachineTitle, Date myDashBoardStartDate)
            throws SQLException, ParseException {
        if (DashBoard.isShowTotalProd()) {
            //create the cumulative bar chart
            chartTotalProd = new Charts(myMachineTitle, myConfigNo[0], myDashBoardStartDate).getChartTotal();
        } else {
            //create an empty chart
            chartTotalProd = new Chart();
        }
        if (DashBoard.isShowRateProd()) {
            //create the rate chart
            chartRateProd = new Charts(myConfigNo[1], myDashBoardStartDate).getChartRate();
        } else {
            //create an empty chart
            chartRateProd = new Chart();
        }

        return new VerticalMultiChartPanel(chartTotalProd, chartRateProd);//panel returned with the chart
    }

    private static Chart chartTotalProd, chartRateProd;
}
