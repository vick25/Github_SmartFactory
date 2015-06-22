package productionChartsPanel;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.ChartSelectionEvent;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.event.PointSelectionEvent;
import com.jidesoft.chart.event.RectangleSelectionEvent;
import com.jidesoft.chart.event.RubberBandZoomer;
import com.jidesoft.chart.event.ZoomFrame;
import com.jidesoft.chart.event.ZoomListener;
import com.jidesoft.chart.event.ZoomOrientation;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.util.ColorFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BarChartTestZoom extends JApplet implements ActionListener {

    private static final String NAME = "name";
    private static final String VALUE = "value";
    private JPanel pnSouth = null;
    private JButton btAddChart = null;
    protected Chart chart = null;
    private Stack<ZoomFrame> zoomStack = null;
    private final ColorFactory colorFactory = new ColorFactory();

    /**
     * Create the panel.
     */
    public BarChartTestZoom() {
        setLayout(new BorderLayout());
        add(getPnSouth(), BorderLayout.SOUTH);
        getBtAddChart().doClick();
    }

    private JPanel getPnSouth() {
        if (pnSouth == null) {
            pnSouth = new JPanel();
            pnSouth.setLayout(new BorderLayout());
            pnSouth.add(getBtAddChart(), BorderLayout.SOUTH);
        }
        setLookAndFeel(this);
        return pnSouth;
    }

    private JButton getBtAddChart() {
        if (btAddChart == null) {
            btAddChart = new JButton("Add Bar Chart");
            btAddChart.addActionListener(this);
        }
        return btAddChart;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            HashMap map = new HashMap();
            map.put(NAME, "File_" + i);
            map.put(VALUE, new Long(225 * (i + 1)));
            list.add(map);
        }
        rebuildChart(list);
    }

    private void rebuildChart(ArrayList allRecords) {
        clearChart(); // wax old chart data first.
        Chart newChart = new Chart();
        int tlItems = allRecords.size();
        CategoryRange<String> categoryRange = new CategoryRange<>();
        HashMap<String, ChartCategory<String>> chartCategoryMap = new HashMap<>();
        String labelForXaxis = "Names";
        for (int i = 0; i < tlItems; i++) {
            AbstractMap map = (AbstractMap) allRecords.get(i);
            String label = (String) map.get(NAME);
            ChartCategory<String> chartCategory = new ChartCategory<>(label/*,
             label+i*/);
            chartCategoryMap.put(label/*+i*/, chartCategory);
        }

        Iterator myItr = chartCategoryMap.keySet().iterator();
        while (myItr.hasNext()) {
            categoryRange.add(chartCategoryMap.get(myItr.next()));
        }
//        System.out.println("A. categoryRange=" + categoryRange.getPossibleValues());
//        System.out.println("labelForXaxis=" + labelForXaxis);
        Axis xAxis = new CategoryAxis<>(categoryRange, labelForXaxis);
        newChart.setXAxis(xAxis);
//        System.out.println("newChart.getChartType()=" + newChart.getChartType());
//        System.out.println("categoryRange=" + categoryRange);
        long maxValue = 0;
        DefaultChartModel model = new DefaultChartModel();
        model.setName("Files");
//        long totalForAllRecords = 0;
        for (int i = 0; i < tlItems; i++) {
            AbstractMap map = (AbstractMap) allRecords.get(i);
            String label = (String) map.get(NAME);
            Long value = (Long) map.get(VALUE);
            long longValue = value;
//            totalForAllRecords += longValue;
            Highlight highlight = new Highlight(label);
            ChartCategory<String> chartCategory = chartCategoryMap.get(label/*+i*/);
//            System.out.println("B. chartCategoryPoint=" + chartCategory);
            ChartPoint chartPoint = new ChartPoint(chartCategory, longValue, highlight);
            chartPoint.setHighlight(highlight);
            model.addPoint(chartPoint);
            ChartStyle chartStyle = new ChartStyle(colorFactory.create()).withBars();
            newChart.setHighlightStyle(highlight, chartStyle);
            if (longValue > maxValue) {
                maxValue = longValue;
            }
        }
//        System.out.println("defaultChartModel.getName()=" + model.getName()
//                + ", totalForAllRecords=" + totalForAllRecords
//                + ", defaultChartModel.getPointCount()=" + model.getPointCount());
        newChart.addModel(model);
        newChart.setBarsGrouped(true);
//        System.out.println("categoryRange.getPossibleValues()="
//                + categoryRange.getPossibleValues());
//        System.out.println("max=" + maxValue);
        String yLabelText = "Values";
//        System.out.println("yLabelText=" + yLabelText);
        maxValue = (long) (maxValue + (maxValue * .05)); // no bars on tippy-top
        Axis yAxis = new NumericAxis(new NumericRange(0, maxValue), yLabelText);
        newChart.setYAxis(yAxis);

        ChartStyle chartStyle = new ChartStyle(Color.cyan, false, false);
        chartStyle.setBarsVisible(true);
        chartStyle.setPointsVisible(false);
        chartStyle.setBarWidth(20);
        newChart.setGridColor(new Color(150, 150, 150));
        newChart.setChartBackground(new GradientPaint(0f, 0f, Color.lightGray, 0f,
                300f, Color.gray));
        newChart.setSelectionShowsOutline(false);
        RaisedBarRenderer barRenderer = new RaisedBarRenderer();
        barRenderer.setAlwaysShowOutlines(false);
        barRenderer.setOutlineWidth(3f);
        newChart.setBarRenderer(barRenderer);
        newChart.setBarGap(5);
        newChart.setVerticalGridLinesVisible(false);
        newChart.setStyle(model, chartStyle);
        if (chart != null) {
            remove(chart);
        }
        chart = newChart;
        Legend legend = new Legend(chart, 0) {
            @Override
            protected JLabel createLegendLabel(String labelString) {
                JLabel legendLabel = new JLabel(labelString);
                legendLabel.setToolTipText(labelString); // in case truncated on display
                //            updateLegend();
                return legendLabel;
            }
        };
        legend.setGenerateFromPoints(true);
        chart.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                checkEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                checkEvent(e);
            }

            public void checkEvent(MouseEvent e) {
                final int PROXIMITY_TO_BASE = 25;
                Point p = e.getPoint();
                String tooltip = null;
                DefaultChartModel dcm = (DefaultChartModel) chart.getModel();
                for (Chartable c : dcm) {
                    Shape shape = chart.renderedAs(c);
                    if (shape.contains(p)) {
                        // The cursor is over a bar
                        Point screenLocation = MouseInfo.getPointerInfo().getLocation();
                        Point pt = new Point(screenLocation);
                        SwingUtilities.convertPointFromScreen(pt, chart);
                        PointDescriptor descriptor = chart.containingBar(pt);
                        if (descriptor != null) {
                            Chartable chartable = descriptor.getChartable();
                            ChartModel model = descriptor.getModel();
                            String valueData = getNameFromString(chartable.getX().toString());
                            if (valueData != null) {
                                String valueStr = chartable.getY().position() + "";
                                chart.setToolTipText("<html>"
                                        + "&nbsp; " + model.getName() + " &nbsp;<br>"
                                        + "&nbsp; Field: &nbsp;&nbsp;" + valueData + " &nbsp;<br>"
                                        + "&nbsp; Value: " + valueStr
                                        + "&nbsp;</html>");
                                return;
                            }
                        }
                        break;
                    } else {
                        // Check whether the cursor is close to the base of the bar
                        Rectangle rect = shape == null ? null : shape.getBounds();
                        if (rect != null && p.x <= rect.getMaxX() && p.x >= rect.getMinX()
                                && Math.abs(p.y - rect.getMaxY()) < PROXIMITY_TO_BASE) {
                            ChartCategory<String> cat = (ChartCategory<String>) (c.getX());
                            tooltip = cat.getName();
                            break;
                        }
                    }
                }
                if (tooltip == null || !tooltip.equals(chart.getToolTipText())) {
                    chart.setToolTipText(tooltip);
                }
            }
        });

        add(legend, BorderLayout.NORTH);
        add(chart, BorderLayout.CENTER);
        setupRubberBandZoomer(chart, xAxis, yAxis, categoryRange);
        chart.update();
    }

    private void setupRubberBandZoomer(final Chart newChart, final Axis xAxis, final Axis yAxis,
            final CategoryRange<String> categoryRange) {
        zoomStack = new Stack<>();
        RubberBandZoomer rubberBand = new RubberBandZoomer(chart);
        rubberBand.setZoomOrientation(ZoomOrientation.BOTH);
        rubberBand.setOutlineColor(Color.green);
        rubberBand.setOutlineStroke(new BasicStroke(2f));
        rubberBand.setFill(new Color(100, 128, 100, 50));
        rubberBand.setKeepWidthHeightRatio(true);

        chart.addDrawable(rubberBand);
        chart.addMouseListener(rubberBand);
        chart.addMouseMotionListener(rubberBand);

        rubberBand.addZoomListener(new ZoomListener() {
            @Override
            public void zoomChanged(ChartSelectionEvent event) {
                if (event instanceof RectangleSelectionEvent) {
                    Range<?> currentXRange = chart.getXAxis().getOutputRange();
                    Range<?> currentYRange = chart.getYAxis().getOutputRange();
                    ZoomFrame frame = new ZoomFrame(currentXRange, currentYRange);
                    zoomStack.push(frame);
                    Rectangle selection = (Rectangle) event.getLocation();
                    Point topLeft = selection.getLocation();
                    //Point bottomRight = new Point(topLeft.x + selection.width, topLeft.y + selection.height);
                    // Always select down to the x axis
                    Point bottomRight = new Point(topLeft.x + selection.width, chart.getYStart());
                    assert bottomRight.x >= topLeft.x;
                    Point2D rp1 = chart.calculateUserPoint(topLeft);
                    Point2D rp2 = chart.calculateUserPoint(bottomRight);
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

        //      zoomStack = new Stack<ZoomFrame>();
        //         RubberBandZoomer zoomer = new RubberBandZoomer(newChart);
        //         zoomer.setOutlineColor(Color.green);
        //         zoomer.setOutlineStroke(new BasicStroke(2f));
        //         zoomer.setFill(new Color(100, 128, 100, 50));
        //         zoomer.setKeepWidthHeightRatio(true);
        //         zoomer.setZoomOrientation(ZoomOrientation.BOTH);
        //        newChart.addDrawable(zoomer);
        //        newChart.addMouseListener(zoomer);
        //        newChart.addMouseMotionListener(zoomer);
        //        zoomer.addZoomListener(new ZoomListener() {
        //            public void zoomChanged(ChartSelectionEvent event) {
        //                if (event instanceof RectangleSelectionEvent) {
        //                    Range<?> currentXRange = chart.getXAxis().getRange();
        //                    Range<?> currentYRange = chart.getYAxis().getRange();
        //                    Range<?> xr = new NumericRange(currentXRange.minimum(), currentXRange.maximum());
        //                    ZoomFrame frame = new ZoomFrame(xr, currentYRange);
        //                    zoomStack.push(frame);
        //                    Rectangle selection = (Rectangle) event.getLocation();
        //                    Point topLeft = selection.getLocation();
        //                    Point bottomRight = new Point(topLeft.x + selection.width, topLeft.y + selection.height);
        //                    assert bottomRight.x >= topLeft.x;
        //                    Point2D rp1 = chart.calculateUserPoint(topLeft);
        //                    Point2D rp2 = chart.calculateUserPoint(bottomRight);
        //                    if(rp1 != null && rp2 != null) {
        //                       categoryRange.setMinimum(rp1.getX());
        //                       categoryRange.setMaximum(rp2.getX());
        //                        yAxis.setRange(new NumericRange(rp2.getY(), rp1.getY()));
        //                    }
        //                } else if (event instanceof PointSelectionEvent) {
        //                    if(zoomStack.size() > 0) {
        //                        ZoomFrame frame = zoomStack.pop();
        //                        Range<?> frameXRange = frame.getXRange();
        //                        Range<?> frameYRange = frame.getYRange();
        //                        categoryRange.setMinimum(frameXRange.minimum());
        //                        categoryRange.setMaximum(frameXRange.maximum());
        //                        yAxis.setRange(frameYRange);
        //                    }
        //                }
        //            }
        //        });
    }

    public void clearChart() {
        if (chart == null) {
            return;
        }
        getChartModel().clearPoints();
    }

    public DefaultChartModel getChartModel() {
        return (DefaultChartModel) getChart().getModel();
    }

    public Chart getChart() {
        return chart;
    }

    private void setLookAndFeel(Component comp) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if (comp != null) {
                SwingUtilities.updateComponentTreeUI(comp);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    protected String getNameFromString(String theString) {
        if (theString == null) {
            return null;
        }
        String returnStr = theString;
        int whereNameEqual = returnStr.indexOf("name='");
        if (whereNameEqual != -1) { // if found...
            returnStr = theString.substring(whereNameEqual);
            int whereFirstHyhpen = returnStr.indexOf('\'');
            if (whereFirstHyhpen != -1) { // if found...
                returnStr = returnStr.substring(whereFirstHyhpen + 1);
                whereFirstHyhpen = returnStr.indexOf('\'');
                if (whereFirstHyhpen != -1) { // if found...
                    returnStr = returnStr.substring(0, whereFirstHyhpen).trim();
                } else {
                    returnStr = null;
                }
            } else {
                returnStr = null;
            }
        } else {
            returnStr = null;
        }
        return returnStr;
    }

    public static void main(String[] args) {
        JApplet theApplet = new BarChartTestZoom();
        JFrame window = new JFrame("BarChartTest");
        window.setSize(900, 600);
        theApplet.setPreferredSize(new Dimension(900, 600));
        window.setContentPane(theApplet);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack(); // Arrange the components.
        window.setLocationRelativeTo(null);
//        System.out.println(theApplet.getSize());
        window.setVisible(true); // Make the window visible.
    }
}
