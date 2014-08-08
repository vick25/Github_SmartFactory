package dashboard;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.range.CategoryRange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BinningExample extends JPanel {

    private static final Color blue = new Color(60, 60, 220);
    private CategoryRange<Interval> intervals = new CategoryRange<>();
    private CategoryAxis xAxis = new CategoryAxis(intervals);
    private NumericAxis yAxis = new NumericAxis(0, 3000);

    public BinningExample() {
        super(new BorderLayout());
        final Chart chart = new Chart();
        chart.setXAxis(xAxis);
        chart.setYAxis(yAxis);
        add(chart, BorderLayout.CENTER);
        chart.addMousePanner(true, false).addMouseZoomer(true, false);
        ChartStyle style = new ChartStyle(blue).withBars();
        style.setBarWidthProportion(0.8);
        chart.addModel(createModel(), style);
        // Optional - can set a different bar renderer
        chart.setBarRenderer(new RaisedBarRenderer());

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
                PointDescriptor shape = chart.containingShape(e.getPoint());
                if (shape == null) {
                    chart.setToolTipText(null);
                } else {
                    Chartable c = shape.getChartable();
                    ChartCategory<Interval> category = (ChartCategory<Interval>) c.getX();
                    Interval interval = category.getValue();
                    double value = c.getY().position();
                    String tooltip = String.format("%s : %.1f", interval.toString(), value);
                    chart.setToolTipText(tooltip);
                }
            }
        };
        chart.addMouseMotionListener(listener);
        chart.setVerticalGridLinesVisible(false);

        xAxis.setTickLabelRotation(Math.PI / 4);
        xAxis.setTickLabelOffset(6);
    }

    private DefaultChartModel createModel() {
        DefaultChartModel model = new DefaultChartModel();
        final int numIntervals = 18;
        double intervalSize = (120.0 - (-20.0)) / numIntervals;
        double intervalStart = -20;
        for (int i = 0; i < numIntervals; i++) {
            ChartCategory category = new ChartCategory(new Interval(intervalStart, intervalStart + intervalSize));
            intervals.add(category);
            double value = createValue();
            Chartable p = new ChartPoint(category, value);
            model.addPoint(p);
            intervalStart += intervalSize;
        }
        return model;
    }

    private double createValue() {
        return 500 + Math.random() * 2500;
    }

    /**
     * A custom class to hold the interval for a bar
     */
    private class Interval {

        private double min, max;

        public Interval(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return String.format("[%.1f, %.1f]", min, max);
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Bar Chart Example with Binning");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setContentPane(new BinningExample());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(150, 150, 800, 600);
                frame.setVisible(true);
            }
        });
    }
}
