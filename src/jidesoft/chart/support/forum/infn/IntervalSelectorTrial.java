package jidesoft.chart.support.forum.infn;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.range.TimeRange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * A class to show how to select a sub-region along a time axis
 */
public class IntervalSelectorTrial extends JPanel {

    private static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
    private Chart chart;
    private ChartModel model;
    private TimeAxis xAxis;
    private NumericAxis yAxis;

    public IntervalSelectorTrial() throws InstantiationException {
        super(new BorderLayout());
        chart = new Chart();
        add(chart, BorderLayout.CENTER);

        Date from, to;
        try {
            from = dateFormat.parse("01-May-2010 00:00:00");
            to = dateFormat.parse("01-May-2010 23:59:59");
        } catch (ParseException pe) {
            throw new InstantiationException(pe.getMessage());
        }
        TimeRange timeRange = new TimeRange(from, to);
        xAxis = new TimeAxis(timeRange, "Time");
        yAxis = new NumericAxis(0, 105);
        chart.setXAxis(xAxis);
        chart.setYAxis(yAxis);
        chart.addMouseZoomer().addMousePanner();
        model = createModel(from, to);
        ChartStyle style = new ChartStyle(Color.blue, false, true);
        chart.addModel(model, style);
        TimeIntervalSelectorPanel selectorPanel = new TimeIntervalSelectorPanel(chart);
        add(selectorPanel, BorderLayout.SOUTH);
        selectorPanel.setModel(model, style);
    }

    /**
     * Creates a model as a random walk
     */
    private ChartModel createModel(Date from, Date to) {
        long fromL = from.getTime();
        long toL = to.getTime();
        int numPoints = 1000;
        DefaultChartModel model = new DefaultChartModel();
        long timePeriod = toL - fromL;
        double current = Math.random() * 100;
        for (int i = 0; i < numPoints; i++) {
            double diff = Math.random() * 20 - 10;
            current += diff;
            if (current < 0) {
                current = 0;
            }
            if (current > 100) {
                current = 100;
            }
            long timePoint = fromL + (long) ((((double) i) / numPoints) * timePeriod);

            model.addPoint(timePoint, current);
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        final IntervalSelectorTrial selectorPanel = new IntervalSelectorTrial();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Interval Selector Trial");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(300, 300, 600, 400);
                frame.setContentPane(selectorPanel);
                frame.setVisible(true);
            }
        });
    }
}
