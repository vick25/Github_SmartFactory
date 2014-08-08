package chartTypes;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.TimePosition;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.utils.TimeUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Victor Kadiata
 */
public class SimpleTimeSeriesTrial extends JPanel {

    public SimpleTimeSeriesTrial() {
        super(new BorderLayout());
        Chart chart = new Chart();
        chart.setAutoRanging(true);
        chart.setXAxis(new TimeAxis());
        chart.setYAxis(new NumericAxis(0, 100));
        add(chart, BorderLayout.CENTER);
        ChartStyle style = new ChartStyle(Color.blue).withPointsAndLines();
        chart.addModel(createTimeSeries(), style);
    }

    private DefaultChartModel createTimeSeries() {
        DefaultChartModel model = new DefaultChartModel();
        try {
            Date t1 = TimeUtils.createTime("19-Jul-2011 11:55:00");
            Date t2 = TimeUtils.createTime("19-Jul-2011 12:00:00");
            Date t3 = TimeUtils.createTime("19-Jul-2011 12:05:00");
            model.addPoint(new TimePosition(t1.getTime()), 20);
            model.addPoint(new TimePosition(t2.getTime()), 40);
            model.addPoint(new TimePosition(t2.getTime()), 60);
            model.addPoint(new TimePosition(t3.getTime()), 80);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("SimpleTimeSeriesTrial");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setContentPane(new SimpleTimeSeriesTrial());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(150, 150, 400, 300);
                frame.setVisible(true);
            }
        });
    }
}
