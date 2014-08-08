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
import com.jidesoft.chart.model.Highlight;
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

    public LineChart(final int IDChannel, final String query, String unit) throws SQLException {
        super();
        if (chart == null) {
            chart = new Chart("Line");
        }
        chart.removeAll();
        range = new CategoryRange<>();
        ProductionPane.getComponentDates();//get the dates from the production class      
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, ProductionPane.cmbChannel.getSelectedItem().toString());
            ps.setInt(i++, IDChannel);
            ps.setString(i++, ConnectDB.SDATEFORMATHOUR.format(ProductionPane.dt_startP));
            ps.setString(i++, ConnectDB.SDATEFORMATHOUR.format(ProductionPane.dt_endP));
            ConnectDB.res = ps.executeQuery();
            alTime.clear();
            alValues.clear();
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                alTime.add(ConnectDB.res.getString(1)); //Time
                alValues.add(ConnectDB.res.getString(2)); //Values
            }
        }
        if (loopQueryFound) {
            chart.setPreferredSize(new Dimension(600, 300));
            chart.setTitle(new AutoPositionedLabel("Production Rate for \""
                    + ProductionPane.cmbMachineTitle.getSelectedItem().toString() + "\"", Color.BLACK,
                    ConnectDB.TITLEFONT));
            ProductionPane.chartTitle = chart.getTitle().toString();

            for (String alTime1 : alTime) {
                Category c = new ChartCategory((Object) alTime1.substring(0, 19), range);
                dateCategoryChart.add(c);
                range.add(c);
            }
            CategoryAxis xAxis = new CategoryAxis(range);
            xAxis.setLabel(new AutoPositionedLabel("Time", Color.BLACK));
            xAxis.setMinorTickColor(Color.RED);
            chart.setXAxis(xAxis);
            chart.getXAxis().setTicksVisible(true);
            NumericAxis yAxis = new NumericAxis(0, (double) ConnectDB.maxNumber(alValues) + 15D);
            yAxis.setLabel(new AutoPositionedLabel(unit, Color.BLACK));
            chart.setYAxis(yAxis);
            chart.setAutoRanging(true);
            ChartStyle style = new ChartStyle(new Color(0, 75, 190), true, true);
            style.setLineFill(new Color(0, 75, 190, 75));
            style.setLineWidth(3);
            style.setPointSize(5);
            style.setPointColor(Color.BLACK);
            style.setPointShape(PointShape.BOX);
            ChartStyle selectionStyle = new ChartStyle(style);
            selectionStyle.setPointSize(15);
            chart.setHighlightStyle(selectionHighlight, selectionStyle);
            chart.getYAxis().setTicksVisible(true);
            chart.getYAxis().setVisible(true);
            chart.drawInBackground();
            chart.setLazyRenderingThreshold(10000);//for swing drawing response
            chart.setBorder(new EmptyBorder(5, 5, 10, 15));
//            chart.addMousePanner();
            MouseWheelZoomer zoomer = new MouseWheelZoomer(chart, true, false);
            zoomer.setZoomLocation(ZoomLocation.MOUSE_CURSOR);
            chart.addMouseWheelListener(zoomer);
            model = createModel();
            chart.addModel(model, style);
//            chart.setPanelBackground(Color.BLACK);
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
//            SmoothLineRenderer lineRenderer = new SmoothLineRenderer(chart);
//            lineRenderer.setSmoothness(0.7);
//            chart.setLineRenderer(lineRenderer);
            // Generate some custom grid lines to match the original chart
            int i;
            for (i = 0; i <= ConnectDB.maxNumber(alValues) + 10; i += 10) {//line marker for y axis
                LineMarker marker = new LineMarker(chart, Orientation.horizontal, i, gridColor);
                chart.addDrawable(marker);
            }
        } else {
            chart = null;
            JOptionPane.showMessageDialog(null, "No data retrieved. Please check "
                    + "the dates and time provided", "Chart", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static ChartModel createModel() {
        DefaultChartModel modelChart = new DefaultChartModel();
        for (int i = 0; i < alValues.size(); i++) {
            modelChart.addPoint(dateCategoryChart.get(i), Double.valueOf(alValues.get(i)));
        }
        return modelChart;
    }

    boolean loopQueryFound = false;
    public Chart chart;
    private static final List<Category> dateCategoryChart = new ArrayList<>();
    public static ChartModel model;
    public static ArrayList<String> alTime = new ArrayList<>(), alValues = new ArrayList<>();
    private static final Highlight selectionHighlight = new Highlight("selection");
    CategoryRange range;
}
