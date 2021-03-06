package productionChartsPanel;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.LabelPlacement;
import com.jidesoft.chart.LineMarker;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.RectangularRegionMarker;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.event.ChartSelectionEvent;
import com.jidesoft.chart.event.PointSelectionEvent;
import com.jidesoft.chart.event.RectangleSelectionEvent;
import com.jidesoft.chart.event.RubberBandZoomer;
import com.jidesoft.chart.event.ZoomFrame;
import com.jidesoft.chart.event.ZoomListener;
import com.jidesoft.chart.event.ZoomOrientation;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import mainFrame.MainFrame;
import productionPanel.ProductionPane;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class MachineRun extends javax.swing.JPanel {

    public Chart getChart() {
        return chart;
    }

    public static ArrayList<String> getLogTimeList() {
        return logTimeList;
    }

    public static ArrayList<Integer> getLogDataList() {
        return logDataList;
    }

    public MachineRun(int myConfigNo, String myQuery, String myMachineTitle, Date myStart, Date myEnd) throws SQLException {
        ConnectDB.getConnectionInstance();
        initComponents();
        _startD = myStart;
        _endD = myEnd;
        _machineTitle = myMachineTitle;

        this.setLayout(new BorderLayout());
        chart = new StepChart(myConfigNo, myQuery).getInnerChart();
        if (chart != null) {
            this.add(chart, BorderLayout.CENTER);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private static class StepChart extends Chart {

        private static Chart innerChart;
        private static CategoryRange<?> categoryRange;
        private static CategoryRange<BinaryTrace> yRange;
        private static final double[] DISTANCES = {1.3, 0.7};
        private static final String[] NAMES = {"ON", "OFF"};
        private boolean loopQueryFound = false;
        private final int _IDChannel;
        private final String _query;

        public Chart getInnerChart() {
            return innerChart;
        }

        StepChart(int myConfigNo, String myQuery) throws SQLException {
            super();
            this._IDChannel = myConfigNo;
            this._query = myQuery;
            innerChart = new Chart("Step Chart");
            categoryRange = new CategoryRange<>();
            yRange = new CategoryRange<>();

            try (PreparedStatement ps = ConnectDB.con.prepareStatement(this._query)) {
                ps.setInt(1, this._IDChannel);
                ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(_startD));
                ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(_endD));
                ConnectDB.res = ps.executeQuery();
//                System.out.println(ps.toString());
                logTimeList = new ArrayList<>();
                logDataList = new ArrayList<>();
                while (ConnectDB.res.next()) {
                    loopQueryFound = true;
                    logTimeList.add(ConnectDB.res.getString(1)); //Time
                    logDataList.add(ConnectDB.res.getInt(2)); //Values
                }
            }
            if (loopQueryFound) {
                xEnd = (double) logDataList.size() + 1;
                innerChart.setPreferredSize(new Dimension(600, 300));
                innerChart.setTitle(new AutoPositionedLabel(new StringBuilder().append("Step Charts (ON/OFF) for \"").
                        append(_machineTitle).append("\"").toString(), Color.BLACK, ConnectDB.TITLEFONT));
                ProductionPane.setChartTitle(innerChart.getTitle().toString());
                innerChart.setAnimateOnShow(true);
                innerChart.setAntiAliasing(false);

                double[] vTransition = new double[logDataList.size()];
                int i = 0;
                for (String logTime : logTimeList) {
                    Category c = new ChartCategory((Object) logTime.substring(0, 19), categoryRange);
                    vTransition[i++] = i + 1;
                    categoryRange.add(c);
                }
                boolean firstValueState = false;
                if (logDataList.get(0) == 1) {
                    firstValueState = true;
                }
                BinaryTrace bt = new BinaryTrace(firstValueState, vTransition);
                Category<BinaryTrace> cat = new Category<>("Machine", bt);
                yRange.add(cat);
                CategoryAxis<BinaryTrace> yCatAxis = new CategoryAxis<>(yRange);
                yCatAxis.setTicksVisible(true);
//                yAxis.setTickLength(0);
                // Paint some 'tram lines' for each of the categories
                for (Category<BinaryTrace> category : yRange.getCategoryValues()) {
                    double pos = category.position();
                    RectangularRegionMarker marker = new RectangularRegionMarker(innerChart, xStart, xEnd,
                            pos - binaryOffset, pos + binaryOffset, new Color(200, 200, 255, 75));
                    marker.setOutlineColor(new Color(140, 140, 140, 75));
                    innerChart.addDrawable(marker);
                }
                for (int j = 0; j < DISTANCES.length; j++) {
                    LineMarker marker = new LineMarker(innerChart, Orientation.horizontal, DISTANCES[j], Color.WHITE);
                    marker.setLabel(NAMES[j]);
                    marker.setLabelFont(ConnectDB.TITLEFONT);
                    marker.setLabelPlacement(LabelPlacement.NORTH_WEST);
                    innerChart.addDrawable(marker);
                }

//                Range<?> xRange = new NumericRange(xStart, xEnd);
                final CategoryAxis xAxis = new CategoryAxis(categoryRange, "Time Range");
                xAxis.setTickLabelRotation(Math.PI / 16);
//                 Axis xAxis = new Axis(xRange, "Time");
                final Axis yAxis = new Axis(yCatAxis.getRange());
                xAxis.setTicksVisible(true);
                xAxis.setMinorTickColor(Color.RED);

                innerChart.setXAxis(xAxis);
                innerChart.setYAxis(yCatAxis);
                innerChart.setHorizontalGridLinesVisible(false);
                innerChart.setVerticalGridLinesVisible(true);
//                innerChart.setGridColor(new Color(150, 150, 150));
//                innerChart.setGridColor(new Color(220, 220, 220));
                innerChart.setBorder(new EmptyBorder(5, 5, 10, 15));
                for (Category<BinaryTrace> c : yRange.getCategoryValues()) {
                    BinaryTrace b = c.getValue();
                    ChartModel model = b.getModel(c);
                    ChartStyle style = new ChartStyle(Color.RED).withLines();
                    style.setLineWidth(2);
                    innerChart.addModel(model, style);
                }
                ChartStyle continuityStyle = continuousStyle;
//                innerChart.addMouseZoomer().addMousePanner();
                innerChart.drawInBackground();
                innerChart.setLazyRenderingThreshold(10000);//for swing drawing response
                innerChart.setHighlightStyle(discontinuity, continuityStyle);
//                innerChart.setPanelBackground(new Color(153, 153, 153));
                innerChart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f,
                        Color.LIGHT_GRAY));
                innerChart.setLabelColor(Color.BLACK);

//                innerChart.addMouseWheelListener(new MouseWheelZoomer(innerChart));
//                MouseDragPanner panner = new MouseDragPanner(innerChart);
//                innerChart.addMouseListener(panner);
//                innerChart.addMouseMotionListener(panner);
                 zoomStack = new Stack<>();
                RubberBandZoomer rubberBand = new RubberBandZoomer(innerChart);
                rubberBand.setOutlineColor(Color.GREEN);
                rubberBand.setOutlineStroke(new BasicStroke(2f));
                rubberBand.setZoomOrientation(ZoomOrientation.BOTH);
                rubberBand.setFill(new Color(128, 128, 128, 50));
                rubberBand.setKeepWidthHeightRatio(true);
                innerChart.addDrawable(rubberBand);
                innerChart.addMouseListener(rubberBand);
                innerChart.addMouseMotionListener(rubberBand);

                rubberBand.addZoomListener(new ZoomListener() {
                    @Override
                    public void zoomChanged(ChartSelectionEvent event) {
                        if (event instanceof RectangleSelectionEvent) {
                            Range<?> currentXRange = innerChart.getXAxis().getOutputRange();
                            Range<?> currentYRange = innerChart.getYAxis().getOutputRange();
                            ZoomFrame frame = new ZoomFrame(currentXRange, currentYRange);
                            zoomStack.push(frame);
                            Rectangle selection = (Rectangle) event.getLocation();
                            Point topLeft = selection.getLocation();
                            Point bottomRight = new Point(topLeft.x + selection.width, topLeft.y + selection.height);
                            assert bottomRight.x >= topLeft.x;
                            Point2D rp1 = innerChart.calculateUserPoint(topLeft);
                            Point2D rp2 = innerChart.calculateUserPoint(bottomRight);
                            if (rp1 != null && rp2 != null) {
                                // Catch the problem case when division has led to NaN
                                if (Double.isNaN(rp1.getX()) || Double.isNaN(rp2.getX())
                                        || Double.isNaN(rp1.getY()) || Double.isNaN(rp2.getY())) {
                                    logger.warning("Cannot zoom as zoomed position is out of range");
                                    return;
                                }
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
            } else {
                innerChart = null;
                ConnectDB.showChartMessageDialog(MainFrame.getFrame());
            }
        }
    }

    static private class BinaryTrace {

        private final double[] transitions;
        private final boolean initialState;

        BinaryTrace(boolean initialState, double... transitions) {
            this.initialState = initialState;
            this.transitions = transitions;
        }

        public ChartModel getModel(Category<?> category) {
            DefaultChartModel model = new DefaultChartModel();
            boolean state = initialState;
            model.addPoint(xStart, getY(category, state));
            for (int i = 0; i < transitions.length; i++) {
                double transition = transitions[i];
                ChartPoint p1 = new ChartPoint(transition, getY(category, state));
                p1.setHighlight(discontinuity);
                model.addPoint(p1);
                state = logDataList.get(i) == 1;
                ChartPoint p2 = new ChartPoint(transition, getY(category, state));
                model.addPoint(p2);
            }
            model.addPoint(xEnd, getY(category, state));
            return model;
        }

        private double getY(Category<?> category, boolean state) {
            return state ? category.position() + binaryOffset : category.position() - binaryOffset;
        }
    }

    public static void main(String[] agrs) throws SQLException, ParseException {
        try {
            final JFrame frame = new JFrame("Machine Run");
            String query = "SELECT dl0.LogTime AS 'Time', dl0.LogData AS MachineRun "
                    + "FROM datalog dl0 "
                    + "WHERE dl0.ConfigNo =? "
                    + "AND dl0.LogTime >=? AND dl0.LogTime <=? "
                    + "ORDER BY 'Time' ASC";
            frame.setSize(800, 500);
            frame.setContentPane(new MachineRun(22, query, "", ConnectDB.SDATE_FORMAT_HOUR.parse("2014/09/02 09:37:46"),
                    ConnectDB.SDATE_FORMAT_HOUR.parse("2014/09/09 09:37:46")));
            frame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            });
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (HeadlessException e) {
//            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    // Defines the width of the gap between the true and false values for a bit trace
    // This value must be between 0 and 0.5
    private Chart chart = null;
    private static final double binaryOffset = 0.30;
    private static final ChartStyle continuousStyle = new ChartStyle(Color.BLACK).withLines();
    private static final Highlight discontinuity = new Highlight("discontinuity");
    private static final double xStart = 0.0;
    private static double xEnd = 8;
    private static Date _startD, _endD;
    private static String _machineTitle;
    private static ArrayList<String> logTimeList;
//    private static MouseDragPanner panner;
    private static ArrayList<Integer> logDataList;
    private static Stack<ZoomFrame> zoomStack;
    private static final Logger logger = Logger.getLogger(MachineRun.class.getName());
}
