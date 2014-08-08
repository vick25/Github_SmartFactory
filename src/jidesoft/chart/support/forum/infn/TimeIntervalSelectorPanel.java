package jidesoft.chart.support.forum.infn;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.RectangularRegionMarker;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartModelListener;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;
import com.jidesoft.range.TimeRange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimeIntervalSelectorPanel extends JPanel {

    private WeakReference<Chart> chartRef;
    private Timer timer;
    private Image image;
    private final Chart overviewChart = new Chart();
    private TimeAxis xAxis;
    private NumericAxis yAxis;
    private final ChartModelListener chartModelListener = new IntervalSelectorChartModelListener();
    private DefaultChartModel model;
    private final RectangularRegionMarker marker = new RectangularRegionMarker(overviewChart);

    public TimeIntervalSelectorPanel(Chart chart) {
        super(new BorderLayout());
        chartRef = new WeakReference<>(chart);
        add(overviewChart, BorderLayout.CENTER);

        overviewChart.setHorizontalGridLinesVisible(false);
        overviewChart.setVerticalGridLinesVisible(false);
        xAxis = new TimeAxis();
        yAxis = new NumericAxis(0, 100);
        overviewChart.setXAxis(xAxis);
        overviewChart.setYAxis(yAxis);
        overviewChart.setLazyRenderingThreshold(0);
        overviewChart.setAnimateOnShow(false);
        overviewChart.addDrawable(marker);
        setPreferredSize(new Dimension(400, 100));

        chart.getXAxis().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateMarker();
            }
        });

        overviewChart.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                Point2D userPoint = overviewChart.calculateUserPoint(p);
                NumericRange xRange = marker.getXInterval();
                NumericRange yRange = marker.getYInterval();
                if (withinRange(userPoint, xRange, yRange)) {
                    double width = xRange.size();
                    double height = yRange.size();
                    marker.setXInterval(userPoint.getX() - width / 2, userPoint.getX() + width / 2);
                    marker.setYInterval(userPoint.getY() - height / 2, userPoint.getY() + height / 2);
                    Chart chart = chartRef.get();
                    if (chart != null) {
                        Axis xAxis = chart.getXAxis();
                        Axis yAxis = chart.getYAxis();
                        xAxis.setRange(userPoint.getX() - width / 2, userPoint.getX() + width / 2);
                        yAxis.setRange(userPoint.getY() - height / 2, userPoint.getY() + height / 2);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point2D userPoint = overviewChart.calculateUserPoint(e.getPoint());
                if (withinRange(userPoint, marker.getXInterval(), marker.getYInterval())) {
                    overviewChart.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                } else {
                    overviewChart.setCursor(Cursor.getDefaultCursor());
                }
            }

            private boolean withinRange(Point2D userPoint, NumericRange xRange, NumericRange yRange) {
                return (userPoint.getX() < xRange.getMax() && userPoint.getX() > xRange.getMin()
                        && userPoint.getY() < yRange.getMax() && userPoint.getY() > yRange.getMin());
            }
        });
    }

    public void setModel(ChartModel m, ChartStyle style) {
        if (m instanceof DefaultChartModel) {
            model = (DefaultChartModel) m;
            if (model != null) {
                model.removeChartModelListener(chartModelListener);
            }
            model.addChartModelListener(chartModelListener);
        }
        overviewChart.addModel(model, style);
        updateOverviewAxes(model);
        updateMarker();
    }

    private void updateOverviewAxes(ChartModel model) {
        Chart c = chartRef.get();
        if (c != null) {
            Range<?> xRange = ((DefaultChartModel) model).getXRange();
            TimeRange timeRange = new TimeRange((long) xRange.minimum(), (long) xRange.maximum());
            xAxis.setRange(timeRange);
        }
    }

    private void updateMarker() {
        Chart c = chartRef.get();
        if (c != null) {
            Axis otherXAxis = c.getXAxis();
            Axis otherYAxis = c.getYAxis();
            Range<?> xRange = otherXAxis.getRange();
            Range<?> yRange = otherYAxis.getRange();
            marker.setColor(new Color(200, 200, 200));
            marker.setXInterval(xRange.minimum(), xRange.maximum());
            marker.setYInterval(yRange.minimum(), yRange.maximum());
        }

    }

    class IntervalSelectorChartModelListener implements ChartModelListener {

        @Override
        public void chartModelChanged() {
            //updateOverviewAxes();
        }
    }

}
