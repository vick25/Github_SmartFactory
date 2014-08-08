package chartTypes;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.render.Bar3DRenderer;
import com.jidesoft.chart.render.PointLabeler;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.LabelStyle;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Positionable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class BarsWithLabels extends JPanel {

    public BarsWithLabels() {
        super(new BorderLayout());
        ChartCategory<String> chocolate = new ChartCategory<>("Chocolate");
        ChartCategory<String> vanilla = new ChartCategory<>("Vanilla");
        ChartCategory<String> strawberry = new ChartCategory<>("Strawberry");
        CategoryRange<String> flavours = new CategoryRange<>();
        flavours.add(chocolate).add(vanilla).add(strawberry);

        Orientation orientation = Orientation.vertical;

        Chart chart = new Chart();
        chart.setVerticalGridLinesVisible(false);
        chart.setMinorTickLength(0);
        if (orientation == Orientation.horizontal) {
            chart.setXAxis(new Axis(new NumericRange(0, 600), "Sales"));
            chart.setYAxis(new CategoryAxis<>(flavours, "Flavours"));
        } else {
            chart.setXAxis(new CategoryAxis<>(flavours, "Flavours"));
            chart.setYAxis(new Axis(new NumericRange(0, 600), "Sales"));
        }
        ChartStyle style = new ChartStyle(Color.blue);
        style.setLinesVisible(false);
        style.setPointsVisible(false);
        style.setBarsVisible(true);
        style.setBarOrientation(orientation);
        style.setBarWidth(50);

        Highlight chocolateHighlight = new Highlight("Chocolate");
        Highlight vanillaHighlight = new Highlight("Vanilla");
        Highlight strawberryHighlight = new Highlight("Strawberry");

        DefaultChartModel salesModel = new DefaultChartModel("Sales");

        ChartPoint p1 = null, p2 = null, p3 = null;

        if (orientation == Orientation.horizontal) {
//            p1 = new ChartPoint(300, chocolate, chocolateHighlight);
//            p2 = new ChartPoint(500, vanilla, vanillaHighlight);
//            p3 = new ChartPoint(250, strawberry, strawberryHighlight);
        } else {
            p1 = new ChartPoint(chocolate, 300, chocolateHighlight);
            p2 = new ChartPoint(vanilla, 500, vanillaHighlight);
            p3 = new ChartPoint(strawberry, 250, strawberryHighlight);
        }

        salesModel.addPoint(p1);
        salesModel.addPoint(p2);
        salesModel.addPoint(p3);

        chart.setHighlightStyle(chocolateHighlight, barStyle(new Color(195, 105, 15)));
        chart.setHighlightStyle(vanillaHighlight, barStyle(new Color(249, 249, 159)));
        chart.setHighlightStyle(strawberryHighlight, barStyle(new Color(255, 85, 80)));

        chart.addModel(salesModel, style);

        //DefaultBarRenderer renderer = new DefaultBarRenderer();
        Bar3DRenderer renderer = new Bar3DRenderer();
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.setColor(Color.red);
        renderer.setLabelStyle(labelStyle);
        renderer.setLabelsVisible(true);      // Add this to show labels for bars
        renderer.setOutlineColor(Color.darkGray);
        renderer.setOutlineWidth(1.5f);
        renderer.setAlwaysShowOutlines(true);
        // This is optional to format the display string
        renderer.setPointLabeler(new PointLabeler() {
            @Override
            public String getDisplayText(Chartable p) {
                Positionable yPos = p.getY();
                return yPos == null ? "" : String.format("%.0f", yPos.position());
            }
        });
        chart.setBarRenderer(renderer);

        chart.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(chart, BorderLayout.CENTER);
    }

    private ChartStyle barStyle(Paint fill) {
        ChartStyle style = new ChartStyle();
        style.setBarPaint(fill);
        style.setBarsVisible(true);
        return style;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Ice Cream Sales");
                frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(600, 400, 400, 350);
                frame.setContentPane(new BarsWithLabels());
                frame.setVisible(true);
            }
        });
    }
}
