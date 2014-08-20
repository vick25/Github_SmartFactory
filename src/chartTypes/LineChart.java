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

    public static ChartModel getChartModel() {
        return chartModel;
    }

    public LineChart(final int ConfigNo, final String query, String unit, String machineTitle, String chanTitle,
            Date start, Date end) throws SQLException {
        super();
        this._startD = start;
        this._endD = end;
        this._machineTitle = machineTitle;
        this._chanTitle = chanTitle;

        boolean loopQueryFound = false;
        chart = new Chart("Line");
        CategoryRange range = new CategoryRange<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, this._chanTitle);
            ps.setInt(i++, ConfigNo);
            ps.setString(i++, ConnectDB.SDATEFORMATHOUR.format(this._startD));
            ps.setString(i++, ConnectDB.SDATEFORMATHOUR.format(this._endD));
            ConnectDB.res = ps.executeQuery();
            timeList.clear();
            alValues.clear();
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                timeList.add(ConnectDB.res.getString(1)); //Time
                alValues.add(ConnectDB.res.getString(2)); //Values
            }
        }
        if (loopQueryFound) {
            chart.setPreferredSize(new Dimension(600, 300));
            chart.setBorder(new EmptyBorder(5, 5, 10, 15));
            chart.setTitle(new AutoPositionedLabel("Production Rate for \"" + this._machineTitle + "\"",
                    Color.BLACK, ConnectDB.TITLEFONT));
            ProductionPane.setChartTitle(chart.getTitle().toString());

            for (String alTime : timeList) {
                Category c = new ChartCategory((Object) alTime.substring(0, 19), range);
                dateCategoryChart.add(c);
                range.add(c);
            }
            CategoryAxis xAxis = new CategoryAxis(range);
            xAxis.setLabel(new AutoPositionedLabel("Time", Color.BLACK));
            xAxis.setMinorTickColor(Color.RED);
            chart.setXAxis(xAxis);
            chart.getXAxis().setTicksVisible(true);
            NumericAxis yAxis = new NumericAxis(0, ConnectDB.maxNumber(alValues) + 25D);
            yAxis.setLabel(new AutoPositionedLabel(unit, Color.BLACK));
            chart.setYAxis(yAxis);
            chart.autoRange();

            ChartStyle style = new ChartStyle(new Color(0, 75, 190), true, true);
            style.setLineFill(new Color(0, 75, 190, 75));
            style.setLineWidth(3);
            style.setPointSize(5);
            style.setPointColor(Color.RED);
            style.setPointShape(PointShape.BOX);

            ChartStyle selectionStyle = new ChartStyle(style);
            selectionStyle.setPointSize(15);
            chart.setHighlightStyle(ConnectDB.SELECTION_HIGHLIGHT, selectionStyle);
            chart.getYAxis().setTicksVisible(true);
//            chart.getYAxis().setVisible(true);
            chart.drawInBackground();
            chart.setLazyRenderingThreshold(10000);//for swing drawing response
//            chart.addMousePanner();
            MouseWheelZoomer zoomer = new MouseWheelZoomer(chart, true, false);
            zoomer.setZoomLocation(ZoomLocation.MOUSE_CURSOR);
            chart.addMouseWheelListener(zoomer);
            chart.addModel(createModel(), style);
            chart.setPanelBackground(new Color(153, 153, 153));
            chart.setChartBackground(new GradientPaint(0f, 0f, Color.lightGray.brighter(), 300f, 300f,
                    Color.lightGray));
            Color gridColor = new Color(150, 150, 150);
            chart.setGridColor(gridColor);
            chart.setVerticalGridLinesVisible(false);
            chart.setHorizontalGridLinesVisible(false);
            chart.setLabelColor(Color.BLACK);
            DefaultPointRenderer pointRenderer = new DefaultPointRenderer();
            pointRenderer.setAlwaysShowOutlines(true);
            pointRenderer.setOutlineColor(Color.WHITE);
            pointRenderer.setOutlineWidth(1);
            chart.setPointRenderer(pointRenderer);
            // Generate some custom grid lines to match the original chart
            for (int i = 0; i <= ConnectDB.maxNumber(alValues) + 10; i += 10) {//line marker for y axis
                LineMarker marker = new LineMarker(chart, Orientation.horizontal, i, gridColor);
                chart.addDrawable(marker);
            }
            chart.repaint();
        } else {
            chart = null;
            JOptionPane.showMessageDialog(null, "No data retrieved. Please check "
                    + "the dates and time provided", "Chart", JOptionPane.WARNING_MESSAGE);
        }
    }

    private ChartModel createModel() {
        DefaultChartModel modelChart = new DefaultChartModel();
        for (int i = 0; i < alValues.size(); i++) {
            modelChart.addPoint(dateCategoryChart.get(i), Double.valueOf(alValues.get(i)));
        }
        chartModel = modelChart;
        return chartModel;
    }

    private static ChartModel chartModel;
    private Chart chart;
    private final Date _startD, _endD;
    private final String _machineTitle, _chanTitle;
    private static final List<Category> dateCategoryChart = new ArrayList<>();
    public static ArrayList<String> timeList = new ArrayList<>(), alValues = new ArrayList<>();
}
