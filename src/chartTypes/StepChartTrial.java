package chartTypes;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.DefaultAutoRanger;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.StepLineRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.icons.JideIconsFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Victor Kadiata
 */
public class StepChartTrial extends JPanel {

    private Object[][] data1 = new Object[][]{
        {"08:00", 0}, {"08:01", 2}, {"09:05", 4}, {"10:06", 4}, {"11:06", 5},
        {"12:06", 3}, {"13:06", 6}, {"14:06", 6}, {"15:30", 2}, {"16:07", 0}
    };

    private Object[][] data2 = new Object[][]{
        {"08:40", 0}, {"08:45", 1}, {"09:00", 6}, {"10:06", 2}, {"10:45", 4},
        {"12:00", 7}, {"13:00", 5}, {"14:06", 4}, {"15:15", 4}, {"16:00", 0}
    };

    private DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

    public StepChartTrial() {
        super(new BorderLayout());
        Chart chart = new Chart();
        Font titleFont = new Font("Arial", Font.BOLD, 18);
        chart.setTitle(new AutoPositionedLabel("LCACs in use at given time", Color.black, titleFont));
        chart.setBorder(new EmptyBorder(0, 20, 20, 20));
        chart.setChartBorder(new LineBorder(Color.gray));
        TimeAxis xAxis = new TimeAxis("Time");
        chart.setXAxis(xAxis);
        chart.getYAxis().setLabel("Number of Transports");
        chart.setAutoRanging(true);
        chart.setAutoRanger(new DefaultAutoRanger(null, 0.0, null, null));
        add(chart, BorderLayout.CENTER);
        chart.addModel(createModel("Plan 2", "19-Feb-2002", data2), createStyle(Color.blue, 2));
        chart.addModel(createModel("Plan 1", "19-Feb-2002", data1), createStyle(Color.red, 2));
        chart.setLineRenderer(new StepLineRenderer(chart));
    }

    private ChartStyle createStyle(Color lineColor, int lineWidth) {
        ChartStyle style = new ChartStyle(lineColor).withLines();
        style.setLineWidth(lineWidth);
        return style;
    }

    private ChartModel createModel(String modelName, String date, Object[][] data) {
        DefaultChartModel model = new DefaultChartModel(modelName);
        try {
            for (int i = 0; i < data.length; i++) {
                Object[] pair = data[i];
                String timeOfDay = (String) pair[0];
                Integer value = (Integer) pair[1];
                Date d = dateFormat.parse(date + " " + timeOfDay);
                model.addPoint(d.getTime(), value.intValue());
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Plan Comparison");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setContentPane(new StepChartTrial());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(150, 150, 700, 450);
                frame.setVisible(true);
            }
        });
    }
}
