package chartTypes;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.LineMarker;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.MouseWheelZoomer;
import com.jidesoft.chart.event.ZoomLocation;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.DefaultPointRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import mainFrame.MainFrame;
import productionPanel.ProductionPane;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class LineChart extends Chart {

    public Chart getChart() {
        return chart;
    }

    public DefaultChartModel getChartModel() {
        return modelChart;
    }

    public ArrayList<String> getLogTimeList() {
        return logTimeList;
    }

    public ArrayList<String> getLogDataList() {
        return logDataList;
    }

    public LineChart(final int myConfigNo, final String myQuery, String unit, String myMachineTitle,
            Date myStartDate, Date myEndDate) throws SQLException {
        super();
        this.chart = new Chart("Line");
        boolean loopQueryFound = false;
        CategoryRange range = new CategoryRange<>();

        try (PreparedStatement ps = ConnectDB.con.prepareStatement(myQuery)) {
            ps.setInt(1, myConfigNo);
            ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(myStartDate));
            ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(myEndDate));
            ConnectDB.res = ps.executeQuery();
            logTimeList = new ArrayList<>();
            logDataList = new ArrayList<>();
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                logTimeList.add(ConnectDB.res.getString(1)); //Time
                logDataList.add(ConnectDB.res.getString(2)); //Values
            }
        }
        if (modelChart != null) {
            modelChart.clearPoints();
        }
        if (loopQueryFound) {
            chart.setPreferredSize(new Dimension(600, 300));
            chart.setBorder(new EmptyBorder(5, 5, 10, 15));
            chart.setTitle(new AutoPositionedLabel(new StringBuilder("Production Rate for \"").
                    append(myMachineTitle).append("\"").toString(), Color.BLACK, ConnectDB.TITLEFONT));
            ProductionPane.setChartTitle(chart.getTitle().toString());
            dateCategoryChart = new ArrayList<>();
            for (String alTime : logTimeList) {
                Category c = new ChartCategory((Object) alTime.substring(0, 19), range);
                dateCategoryChart.add(c);
                range.add(c);
            }
            CategoryAxis xAxis = new CategoryAxis(range);
            xAxis.setLabel(new AutoPositionedLabel("Time", Color.BLACK));
            xAxis.setMinorTickColor(Color.RED);
            xAxis.setTickLabelRotation(Math.PI / 8);
            chart.setXAxis(xAxis);
            chart.getXAxis().setTicksVisible(true);

            int maxNumber = ConnectDB.maxNumber(logDataList);
            if (maxNumber <= 0) {
                JOptionPane.showMessageDialog(chart, "The maximum value on the y-Axis is less than or egal to zero!!!\n"
                        + "Please adjust the time period selections.", "Line chart", JOptionPane.ERROR_MESSAGE);
                return;
            }
            NumericAxis yAxis = new NumericAxis(0, maxNumber + (int) ((maxNumber < 100 ? 0.35 : 0.08) * maxNumber));
            yAxis.setLabel(new AutoPositionedLabel(unit, Color.BLACK));
            chart.setYAxis(yAxis);
            chart.autoRange();

            ChartStyle style = new ChartStyle(new Color(0, 75, 190), true, true);
            style.setLineFill(new Color(0, 75, 190, 75));
            style.setLineWidth(3);
            style.setPointSize(5);
            style.setPointColor(Color.RED);
            style.setPointShape(PointShape.DIAMOND);

            ChartStyle selectionStyle = new ChartStyle(style);
            selectionStyle.setPointSize(15);
            chart.setHighlightStyle(ConnectDB.SELECTION_HIGHLIGHT, selectionStyle);
            chart.getYAxis().setTicksVisible(true);
//            chart.getYAxis().setVisible(true);
//            chart.addMousePanner();
            MouseWheelZoomer zoomer = new MouseWheelZoomer(chart, true, false);
            zoomer.setZoomLocation(ZoomLocation.MOUSE_CURSOR);
            chart.addMouseWheelListener(zoomer);

            /*Creating the chart model*/
            chart.addModel(createModel(), style);
//            chart.setPanelBackground(new Color(153, 153, 153));
            chart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f,
                    Color.LIGHT_GRAY));
            Color gridColor = new Color(150, 150, 150);
            chart.setGridColor(gridColor);
            chart.setVerticalGridLinesVisible(false);
            chart.setHorizontalGridLinesVisible(false);
            chart.setLabelColor(Color.BLACK);
            chart.drawInBackground();
            chart.setLazyRenderingThreshold(10000);//for swing drawing response
            DefaultPointRenderer pointRenderer = new DefaultPointRenderer();
            pointRenderer.setAlwaysShowOutlines(true);
            pointRenderer.setOutlineColor(Color.GREEN);
            pointRenderer.setOutlineWidth(1f);
            chart.setPointRenderer(pointRenderer);
            // Generate some custom grid lines to match the original chart
            for (int i = 0; i <= ConnectDB.maxNumber(logDataList) + 10; i += 10) {//line marker for y axis
                LineMarker marker = new LineMarker(chart, Orientation.horizontal, i, gridColor);
                chart.addDrawable(marker);
            }
            chart.repaint();
        } else {
            chart = null;
            ConnectDB.showChartMessageDialog(MainFrame.getFrame());
        }
    }

    private ChartModel createModel() {
        modelChart = new DefaultChartModel();
        for (int i = 0; i < logDataList.size(); i++) {
            modelChart.addPoint(dateCategoryChart.get(i), Double.valueOf(logDataList.get(i)));
        }
        return modelChart;
    }

    private DefaultChartModel modelChart = null;
    private Chart chart;
    private List<Category> dateCategoryChart;
    private final ArrayList<String> logTimeList, logDataList;
}
