package chartTypes;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.icons.JideIconsFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class PointsOnTop extends JPanel {

    public PointsOnTop() {
        super(new BorderLayout());
        Chart chart = new Chart();
        chart.setXAxis(new NumericAxis(0, 100));
        chart.setYAxis(new NumericAxis(0, 100));
        ChartModel model1 = createSinglePointModel(50, 50);
        ChartModel model2 = createSinglePointModel(50, 50);
        chart.addModel(model1, createStyle(Color.red, PointShape.DISC));
        chart.addModel(model2, createStyle(Color.blue, PointShape.BOX));
        chart.setZOrder(model1, 200);
        chart.setZOrder(model2, 100);
        add(chart, BorderLayout.CENTER);
    }

    private ChartModel createSinglePointModel(double x, double y) {
        DefaultChartModel model = new DefaultChartModel();
        model.addPoint(x, y);
        return model;
    }

    private ChartStyle createStyle(Color color, PointShape shape) {
        ChartStyle style = new ChartStyle();
        style.setPointsVisible(true);
        style.setPointColor(color);
        style.setPointShape(shape);
        style.setPointSize(50);
        return style;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("PointsOnTop");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setContentPane(new PointsOnTop());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setBounds(150, 150, 600, 400);
                frame.setVisible(true);
            }
        });
    }
}
