package dashboard;

import com.jidesoft.chart.BarResizePolicy;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.LabelPlacement;
import com.jidesoft.chart.LineMarker;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.ChartSelectionEvent;
import com.jidesoft.chart.event.MouseWheelZoomer;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.event.PointSelectionEvent;
import com.jidesoft.chart.event.RectangleSelectionEvent;
import com.jidesoft.chart.event.RubberBandZoomer;
import com.jidesoft.chart.event.ZoomFrame;
import com.jidesoft.chart.event.ZoomListener;
import com.jidesoft.chart.event.ZoomLocation;
import com.jidesoft.chart.event.ZoomOrientation;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.render.Axis3DRenderer;
import com.jidesoft.chart.render.DefaultPointRenderer;
import com.jidesoft.chart.render.PointLabeler;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.LabelStyle;
import com.jidesoft.chart.util.ColorFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Positionable;
import com.jidesoft.range.Range;
import eventsPanel.StatKeyFactory;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import javax.swing.border.EmptyBorder;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

public class Charts extends Chart {

    public Chart getChartTotal() {
        return chartTotal;
    }

    public Chart getChartRate() {
        return chartRate;
    }

    /*Constructor for the cumulative chart */
    public Charts(String machineName, int configNo, Date dashBoardStartDate) throws SQLException, ParseException {
        this._machineName = machineName;
        this._startDate = dashBoardStartDate;
        this._configNo = configNo;
        if (DashBoard.isShowTotalProd()) {
            updateModelsTotal();
//            xAxis.setMinorTickColor(Color.RED);
        }
//        xAxis.setTickLabelRotation(Math.PI / 8);
    }

    /*Constructor for the rate chart */
    public Charts(int _configNo, Date _startDate) throws SQLException {
        this._configNo = _configNo;
        this._startDate = _startDate;
        if (DashBoard.isShowRateProd()) {
            updateModelsRate();
        }
    }

    synchronized private void updateModelsTotal() throws SQLException, ParseException {
        chartTotal.removeModels();
        chartTotal.removeDrawables();

        BarChartModel bcm = new BarChartModel(this._configNo, Queries.DATALOG_PRODUCTION, this._withShifts,
                this._machineName, this._startDate, Calendar.getInstance().getTime(), null);//create object
        if (bcm.getModelPoints() != null) {
            ChartModel modelTotalProd = bcm.getModelPoints();//get the defaault chart model
            xAxis = new CategoryAxis<>(BarChartModel.getCategoryRange());

            chartTotal.setXAxis(xAxis);
            chartTotal.getXAxis().setTicksVisible(true);

            int maxNumber = bcm.getMaxSumValue();//get the maximum bar chart sum
            //Get the machine target
            double target = 0;
            try {
                target = new DynamicTarget(this._machineName).getReturnTargets();
                switch (ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour")) {
                    case "second":
                        target *= 3600;
                        break;
                    case "minute":
                        target *= 60;
                        break;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            //Calculate the current target and send it to the currentTarget method in the verticalMultiChartPanel
            VerticalMultiChartPanel.setCurrentTarget(calculateCurrentTarget(target, bcm.getLastHourValue()));
            //
            if (maxNumber < target) {
                maxNumber = (int) target;
            }
//        System.out.println(maxNumber);
//        System.out.println(maxNumber + (int) (Math.random() * maxNumber));
            NumericAxis yAxis = new NumericAxis(0, maxNumber + (int) (0.08 * maxNumber));

            yAxis.setLabel(new AutoPositionedLabel("Total Parts", Color.BLACK));
            chartTotal.setLayout(new BorderLayout());
            chartTotal.setBorder(new EmptyBorder(5, 5, 10, 15));
//        chartTotal.setShadowVisible(true);
            chartTotal.getYAxis().setTicksVisible(true);
            chartTotal.getYAxis().setVisible(true);
            chartTotal.setYAxis(yAxis);
            ChartStyle styleTotalProd;
            if (modelTotalProd != null) {
                RaisedBarRenderer renderer = new RaisedBarRenderer();
                renderer.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
                renderer.setZeroHeightBarsVisible(true);
                renderer.setSelectionColor(Color.BLACK);
                chartTotal.getXAxis().setAxisRenderer(new Axis3DRenderer());
                chartTotal.setAnimateOnShow(true);
                chartTotal.setRolloverEnabled(true);
                if (_withShifts) {
                    ColorFactory colorFactory = new ColorFactory(Color.RED, Color.ORANGE, Color.BLUE);
                    styleTotalProd = new ChartStyle(colorFactory.create()).withBars();
                    chartTotal.setBarsGrouped(true);
                    chartTotal.setBarGroupGapProportion(0.6);//changed from 0.5 for the size of the bar
                    renderer.setOutlineWidth(5f);
                    styleTotalProd.setBarWidth(50);
                    chartTotal.addModel(modelTotalProd, styleTotalProd);//Total Production Model
                    chartTotal.setGridColor(new Color(150, 150, 150));
                } else {
                    chartTotal.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
                    chartTotal.setBarGap(8);
                    chartTotal.setVerticalGridLinesVisible(false);
                    styleTotalProd = new ChartStyle(Color.BLUE, false, false, true);
                    styleTotalProd.setBarWidthProportion(0.8);
                    chartTotal.setGridColor(new Color(150, 150, 150));
                    chartTotal.addModel(modelTotalProd, styleTotalProd);//Total Production Model
                }

                /* Set the target line */
                if (target > 0d) {
                    LineMarker marker = new LineMarker(chartTotal, Orientation.horizontal, target, Color.RED);
                    marker.setLabel("Target");
                    marker.setLabelPlacement(LabelPlacement.NORTH_WEST);
                    chartTotal.addDrawable(marker);
                }
//            else {
//                System.out.println(target);
//            }
                //
                LabelStyle labelStyle = new LabelStyle();
                labelStyle.setColor(Color.RED);
                renderer.setLabelStyle(labelStyle);
                renderer.setLabelsVisible(true);// Add this to show labels for bars
                renderer.setOutlineColor(Color.WHITE);//the oultine color of a bar
                renderer.setOutlineWidth(1.6f);
                renderer.setAlwaysShowOutlines(true);
                // This is optional to format the display string
                renderer.setPointLabeler(new PointLabeler() {
                    @Override
                    public String getDisplayText(Chartable p) {
                        Positionable yPos;
                        try {
                            yPos = p.getY();
                            return yPos == null ? "" : String.format("%.0f", yPos.position());
                        } catch (Exception e) {
                            return "";
                        }
                    }
                });
                chartTotal.setBarRenderer(renderer);
                MouseMotionListener listener = new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        checkTooltip(e);
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        checkTooltip(e);
                    }

                    private void checkTooltip(MouseEvent e) {
                        PointDescriptor shape = chartTotal.containingShape(e.getPoint());
                        if (shape == null) {
                            chartTotal.setToolTipText(null);
                        } else {
                            Chartable chartable = shape.getChartable();
//                        int xPos = (int) chartable.getX().position() - 1;
                            StringBuilder builder = new StringBuilder("<html><table>");
                            builder.append(String.format("<tr><td><b>Time</b></td><td>%s</td></tr>",
                                    ((Category) chartable.getX()).getValue().toString()));
                            builder.append(String.format("<tr><td><b>Value</b></td><td>%.0f part(s)</td></tr>",
                                    chartable.getY().position()));
                            builder.append("</table></html>");
                            chartTotal.setToolTipText(builder.toString());
                        }
                    }
                };
                chartTotal.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        PointDescriptor shape = chartTotal.containingShape(e.getPoint());
                        if (shape != null) {
                            if (e.getClickCount() == 2) {
                                System.out.println(((Category) shape.getChartable().getX()).getValue().toString());
                            }
                        }
                    }
                });
                chartTotal.addMouseMotionListener(listener);
                setupRubberBandZoomer(this.chartTotal, this.chartTotal.getXAxis(), this.chartTotal.getYAxis());
            }
        }
    }

    private void updateModelsRate() throws SQLException {
        chartRate.removeModels();
        chartRate.removeDrawables();

        ChartModel modelRateProd = new LineChartModel(this._configNo, Queries.DATALOG_PRODUCTION_HR, _startDate,
                Calendar.getInstance().getTime(), "p/hrs").getModelPoints();
        chartRate.setLayout(new BorderLayout());
        chartRate.setBorder(new EmptyBorder(5, 5, 10, 15));

        if (modelRateProd != null) {
            ChartStyle styleRateProd = new ChartStyle(Color.BLUE, true, true);
            Color gridColor = new Color(150, 150, 150);
            chartRate.setGridColor(gridColor);
            chartRate.setVerticalGridLinesVisible(false);
            chartRate.setHorizontalGridLinesVisible(false);
            chartRate.setLabelColor(Color.BLACK);
            DefaultPointRenderer pointRenderer = new DefaultPointRenderer();
            pointRenderer.setAlwaysShowOutlines(true);
            pointRenderer.setOutlineColor(Color.WHITE);
            pointRenderer.setOutlineWidth(1);
            chartRate.setPointRenderer(pointRenderer);
            /*/Generate some custom grid lines to match the original chart*/
            for (int i = 0; i <= LineChartModel.getMaxNum() + 10; i += 10) {//line marker for y axis
                LineMarker marker = new LineMarker(chartRate, Orientation.horizontal, i, gridColor);
                chartRate.addDrawable(marker);
            }
            chartRate.setXAxis(xAxis);
            chartRate.getXAxis().setTicksVisible(true);
            NumericAxis yAxis = new NumericAxis(0, LineChartModel.getMaxNum() + 15D);
            yAxis.setLabel(new AutoPositionedLabel("Rate", Color.BLACK));
            chartRate.setYAxis(yAxis);
            styleRateProd.setLineFill(BLUEFILL);
            styleRateProd.setLineWidth(3);
            styleRateProd.setPointSize(5);
            styleRateProd.setPointColor(Color.BLACK);
            styleRateProd.setPointShape(PointShape.BOX);
            ChartStyle selectionStyle = new ChartStyle(styleRateProd);
            selectionStyle.setPointSize(15);
            chartRate.setHighlightStyle(ConnectDB.SELECTION_HIGHLIGHT, selectionStyle);
            chartRate.setLazyRenderingThreshold(10000);//for swing drawing response
            MouseWheelZoomer zoomer = new MouseWheelZoomer(chartRate, true, false);
            zoomer.setZoomLocation(ZoomLocation.MOUSE_CURSOR);
            chartRate.addMouseWheelListener(zoomer);
            chartRate.addModel(modelRateProd, styleRateProd);//adding the model point to the chart
        }
    }
    
    private void setupRubberBandZoomer(final Chart chartToZoom, final Axis xAxis, final Axis yAxis) {
        zoomStack = new Stack<>();
        RubberBandZoomer rubberBand = new RubberBandZoomer(chartToZoom);
        rubberBand.setZoomOrientation(ZoomOrientation.BOTH);
        rubberBand.setOutlineColor(Color.GREEN);
        rubberBand.setOutlineStroke(new BasicStroke(2f));
        rubberBand.setFill(new Color(100, 128, 100, 50));
        rubberBand.setKeepWidthHeightRatio(true);

        chartToZoom.addDrawable(rubberBand);
        chartToZoom.addMouseListener(rubberBand);
        chartToZoom.addMouseMotionListener(rubberBand);

        rubberBand.addZoomListener(new ZoomListener() {
            @Override
            public void zoomChanged(ChartSelectionEvent event) {
                if (event instanceof RectangleSelectionEvent) {
                    Range<?> currentXRange = chartToZoom.getXAxis().getOutputRange();
                    Range<?> currentYRange = chartToZoom.getYAxis().getOutputRange();
                    ZoomFrame frame = new ZoomFrame(currentXRange, currentYRange);
                    zoomStack.push(frame);
                    Rectangle selection = (Rectangle) event.getLocation();
                    Point topLeft = selection.getLocation();
                    //Point bottomRight = new Point(topLeft.x + selection.width, topLeft.y + selection.height);
                    // Always select down to the x axis
                    Point bottomRight = new Point(topLeft.x + selection.width, chartToZoom.getYStart());
                    assert bottomRight.x >= topLeft.x;
                    Point2D rp1 = chartToZoom.calculateUserPoint(topLeft);
                    Point2D rp2 = chartToZoom.calculateUserPoint(bottomRight);
                    if (rp1 != null && rp2 != null) {
                        assert rp2.getX() >= rp1.getX() : rp2.getX() + " must be greater than or equal to " + rp1.getX();
                        CategoryRange xRange = new CategoryRange((CategoryRange) currentXRange);
                        xRange.setMinimum(rp1.getX());
                        xRange.setMaximum(rp2.getX());
                        assert rp1.getY() >= rp2.getY() : rp1.getY() + " must be greater than or equal to " + rp2.getY();
                        Range<?> yRange = new NumericRange(rp2.getY(), rp1.getY());
                        xAxis.setRange(xRange);
                        yAxis.setRange(yRange);
                    }
                } else if (event instanceof PointSelectionEvent) {
                    if (zoomStack.size() > 0) {
                        ZoomFrame frame = zoomStack.pop();
                        Range<?> xRange = frame.getXRange();
                        Range<?> yRange = frame.getYRange();
                        xAxis.setRange(xRange);
                        yAxis.setRange(yRange);
                    }
                }
            }
        });
    }

    synchronized private double calculateCurrentTarget(double target, String lastHourValue) throws SQLException, ParseException {
        String PStart = "", Tcurrent = "";
        ResultSet resultSet;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(
                "(SELECT Logtime FROM datalog WHERE Logtime LIKE ? ORDER BY Logtime ASC LIMIT 1)\n"
                + "UNION \n"
                + "(SELECT Logtime FROM datalog WHERE Logtime LIKE ? ORDER BY Logtime DESC LIMIT 1)")) {
            ps.setString(1, lastHourValue + "%");
            ps.setString(2, lastHourValue + "%");
            resultSet = ps.executeQuery();
            boolean getFristValue = true;
            while (resultSet.next()) {
                if (getFristValue) {
                    PStart = resultSet.getString(1);
                    getFristValue = false;
                }
                Tcurrent = resultSet.getString(1);
            }
        }

        double[] diffs = ConnectDB.getTimeDifference(
                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(PStart)),
                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(Tcurrent)));
        double currentTarget = diffs[2] * (target / 60);
        return ConnectDB.DECIMALFORMAT.parse(ConnectDB.DECIMALFORMAT.format(currentTarget)).doubleValue();
    }

    private final int _configNo;
    private final Date _startDate;
    private String _machineName;
    private final boolean _withShifts = false;
    private static CategoryAxis xAxis;
    private static final Color BLUEFILL = new Color(0, 75, 190, 75);
    private final Chart chartTotal = new Chart("Bar"), chartRate = new Chart("Line");    
    private Stack<ZoomFrame> zoomStack = null;
}
