package chartTypes;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.fit.Polynomial;
import com.jidesoft.chart.fit.PolynomialFitter;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.TimePosition;
import com.jidesoft.chart.model.Transform;
import com.jidesoft.chart.model.TransformingChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.range.Range;
import com.jidesoft.range.TimeRange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class CubicRegressionExample extends JPanel {

    private static final Logger logger = Logger.getLogger(CubicRegressionExample.class.getName());

    double[] data = {1394085600000.00, 188.12,
        1394172000000.00, 181.65,
        1394258400000.00, 179.99,
        1394344800000.00, 181.78,
        1394427600000.00, 184.43,
        1394514000000.00, 181.67,
        1394600400000.00, 180.16,
        1394686800000.00, 181.79,
        1394773200000.00, 183.9,
        1394859600000.00, 178.02,
        1394946000000.00, 179.59,
        1395032400000.00, 185.09,
        1395118800000.00, 190.35,
        1395205200000.00, 178.55,
        1395291600000.00, 185.3,
        1395378000000.00, 186.09,
        1395464400000.00, 181.23,
        1395550800000.00, 181.07,
        1395637200000.00, 181.25,
        1395723600000.00, 184.28,
        1395810000000.00, 183.25,
        1395896400000.00, 190.02,
        1395982800000.00, 182.49,
        1396069200000.00, 178.72,
        1396155600000.00, 176.04,
        1396242000000.00, 184.67,
        1396328400000.00, 185.6,
        1396414800000.00, 180.74,
        1396501200000.00, 177.35};

    public CubicRegressionExample() {
        super(new BorderLayout());
        Chart chart = new Chart();
        chart.setBorder(new EmptyBorder(10, 0, 10, 20));
        TimeAxis xAxis = new TimeAxis();
        chart.setXAxis(xAxis);
        add(chart, BorderLayout.CENTER);
        chart.setAutoRanging(true);

        // Create the random model
        DefaultChartModel model = createModel();

        // Add the original model - adding it last means it will be painted on top
        ChartStyle modelStyle = new ChartStyle(Color.BLUE).withPointsAndLines();
        modelStyle.setPointSize(8);
        modelStyle.setLineWidth(2);
        chart.addModel(model, modelStyle);

        chart.autoRange();
        long startOfRange = (long) xAxis.getRange().minimum();
        logger.info("Start of range at " + (new Date(startOfRange)));
        PolynomialFitter fitter = new PolynomialFitter(3);
        TransformingChartModel leftShiftModel = new TransformingChartModel(model);
        leftShiftModel.setTransform(new DateShift(startOfRange));
        Polynomial p = fitter.performRegression(leftShiftModel);
        logger.info("Polynomial is " + p);
        Range xAxisRange = xAxis.getRange();
        Range transformedRange = new TimeRange((long) (xAxisRange.minimum() - startOfRange), (long) (xAxisRange.maximum() - startOfRange));
        DefaultChartModel fittedModel = (DefaultChartModel) fitter.createModel(p, transformedRange, leftShiftModel.getPointCount());
        TransformingChartModel rightShiftModel = new TransformingChartModel(fittedModel);
        rightShiftModel.setTransform(new DateShift(-startOfRange));

        ChartStyle fittedStyle = new ChartStyle(Color.black).withLines();
        fittedStyle.setLineWidth(2);
        chart.addModel(rightShiftModel, fittedStyle);

    }

    static class DateShift implements Transform<Chartable> {

        private long dateShift;

        public DateShift(long timeShift) {
            this.dateShift = timeShift;
        }

        @Override
        public Chartable transform(Chartable chartable) {
            return new ChartPoint(chartable.getX().position() - dateShift, chartable.getY());
        }
    }

    private DefaultChartModel createModel() {
        DefaultChartModel model = new DefaultChartModel();
        for (int i = 0; i < data.length / 2; i++) {
            double y = data[2 * i + 1];
            TimePosition x = new TimePosition((long) data[2 * i]);
            model.addPoint(x, y);
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Curve Fitting Example");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setContentPane(new CubicRegressionExample());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setBounds(150, 150, 600, 400);
                frame.setVisible(true);
            }
        });
    }
}
