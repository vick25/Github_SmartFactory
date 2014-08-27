package dashboard;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class VerticalMultiChartPanel extends JPanel {

//    private final Paint chartBackground = new GradientPaint(0, 0, Color.GRAY, 0, 150, Color.WHITE);
    private final JPanel chartPanel = new JPanel();
    private final JPanel legendPanel = new JPanel();
    private final Legend legend = new Legend();
    private static double currentTarget, actualBarValue, variance;

//    public static double getCurrentTarget() {
//        return currentTarget;
//    }
    public static void setCurrentTarget(double currentTarget) {
        VerticalMultiChartPanel.currentTarget = currentTarget;
    }

//    public static double getActualBarValue() {
//        return actualBarValue;
//    }
    public static void setActualBarValue(double actualBarValue) {
        VerticalMultiChartPanel.actualBarValue = actualBarValue;
    }

    public VerticalMultiChartPanel(Chart chartTotalProd, Chart chartRateProd) {
        setLayout(new BorderLayout());

        GridBagLayout gridBagLayout = new GridBagLayout();
        chartPanel.setLayout(gridBagLayout);
        legend.setBorder(null);
        legend.setColumns(4);
        legend.setBackground(Color.WHITE);
        //
        createLegendPanelComponents();
        //
        this.add(chartPanel, BorderLayout.CENTER);
        if (DashBoard.isShowTotalProd()) {
            chartTotalProd.setAxisLabelPadding(0);
//            chartTotalProd.setChartBackground(chartBackground);
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
//            chartRateProd.setChartBackground(chartBackground);
            chartRateProd.getXAxis().setVisible(true);
            GridBagConstraints gbc_2 = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 20);
            chartPanel.add(chartRateProd, gbc_2);
            legend.addChart(chartRateProd);
        }
        this.add(legendPanel, BorderLayout.SOUTH);
    }

    private void createLegendPanelComponents() {
        legendPanel.setLayout(new GridLayout(0, 2));
        legendPanel.setBackground(Color.LIGHT_GRAY);
        legendPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        JPanel currentTargetPanel = new JPanel(new BorderLayout(6, 6));
        currentTargetPanel.setBackground(Color.WHITE);
        currentTargetPanel.add(new JLabel(Double.toString(currentTarget)), BorderLayout.CENTER);
        currentTargetPanel.add(new JLabel("Current Target: "), BorderLayout.BEFORE_LINE_BEGINS);
        legendPanel.add(currentTargetPanel);

        JPanel actualValuePanel = new JPanel(new BorderLayout(6, 6));
        actualValuePanel.setBackground(Color.WHITE);
        actualValuePanel.add(new JLabel(Double.toString(actualBarValue)), BorderLayout.CENTER);
        actualValuePanel.add(new JLabel("Actual Value: "), BorderLayout.BEFORE_LINE_BEGINS);
        legendPanel.add(actualValuePanel);

        variance = actualBarValue - currentTarget;
        JPanel variancePanel = new JPanel(new BorderLayout(6, 6));
        variancePanel.setBackground(Color.WHITE);
        final JLabel labelVariance = new JLabel(ConnectDB.DECIMALFORMAT.format(variance));
        if (variance < 0) {
            labelVariance.setForeground(Color.RED);
        } else {
            labelVariance.setForeground(Color.GREEN);
        }
        labelVariance.setFont(new Font("Tahoma", Font.BOLD, 12));
        variancePanel.add(labelVariance, BorderLayout.CENTER);
        variancePanel.add(new JLabel("Variance: "), BorderLayout.BEFORE_LINE_BEGINS);
        legendPanel.add(variancePanel);

        legendPanel.add(legend);
    }
}
