package dashboard;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import javax.swing.JPanel;

/**
 *
 * @author Victor Kadiata
 */
public class VerticalMultiChartPanel extends JPanel {

    private final Paint chartBackground = new GradientPaint(0, 0, Color.GRAY, 0, 150, Color.WHITE);
    private final JPanel chartPanel = new JPanel();
    private final JPanel legendPanel = new JPanel();
    private final Legend legend = new Legend();

    public VerticalMultiChartPanel(Chart chartTotalProd, Chart chartRateProd) {
        setLayout(new BorderLayout());

        GridBagLayout gridBagLayout = new GridBagLayout();
        chartPanel.setLayout(gridBagLayout);
        legend.setColumns(4);

        legendPanel.add(legend);
        this.add(chartPanel, BorderLayout.CENTER);
        if (DashBoard.isShowTotalProd()) {
            chartTotalProd.setAxisLabelPadding(0);
            chartTotalProd.setChartBackground(chartBackground);
            chartTotalProd.getXAxis().setTicksVisible(true);
            if (!DashBoard.isShowRateProd()) {
                chartTotalProd.setPreferredSize(new Dimension(200, 200));
                chartTotalProd.getXAxis().setVisible(true);
            } else {
                chartTotalProd.setPreferredSize(new Dimension(200, 150));
                chartTotalProd.getXAxis().setTicksVisible(true);
                chartTotalProd.getXAxis().setVisible(false);
            }

            GridBagConstraints gbc_1 = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0);
            chartPanel.add(chartTotalProd, gbc_1);
            legend.addChart(chartTotalProd);
        }

        if (DashBoard.isShowRateProd()) {
            chartRateProd.setAxisLabelPadding(0);
            chartRateProd.setPreferredSize(new Dimension(200, 200));
            chartRateProd.setChartBackground(chartBackground);
            chartRateProd.getXAxis().setVisible(true);
            GridBagConstraints gbc_2 = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 20);
            chartPanel.add(chartRateProd, gbc_2);
            legend.addChart(chartRateProd);
        }
        this.add(legendPanel, BorderLayout.SOUTH);
    }
}
