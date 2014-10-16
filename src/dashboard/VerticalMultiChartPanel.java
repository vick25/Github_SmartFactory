package dashboard;

import chartTypes.RandomColor;
import com.jidesoft.chart.Chart;
import com.jidesoft.swing.MarqueePane;
import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class VerticalMultiChartPanel extends JPanel {

    public static MarqueePane getHorizonMarqueeLeft() {
        return _horizonMarqueeLeft;
    }

    public static void setCurrentTarget(double currentTarget) {
        VerticalMultiChartPanel.currentTarget = currentTarget;
    }

    public static void setActualBarValue(double actualBarValue) {
        VerticalMultiChartPanel.actualBarValue = actualBarValue;
    }

    public VerticalMultiChartPanel(Chart chartTotalProd, Chart chartRateProd, String myMachineTitle) {
        this.machineName = myMachineTitle;
        setLayout(new BorderLayout());

        GridBagLayout gridBagLayout = new GridBagLayout();
        chartPanel.setLayout(gridBagLayout);
//        legend.setBorder(null);
//        legend.setColumns(4);
//        legend.setBackground(Color.WHITE);
        //
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    createLegendPanelComponents();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        //
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
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
            chartPanel.add(chartTotalProd, gbc_1);
//            legend.addChart(chartTotalProd);
        }

        if (DashBoard.isShowRateProd()) {
            chartRateProd.setAxisLabelPadding(0);
            chartRateProd.setPreferredSize(new Dimension(200, 200));
//            chartRateProd.setChartBackground(chartBackground);
            chartRateProd.getXAxis().setVisible(true);
            GridBagConstraints gbc_2 = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 20);
            chartPanel.add(chartRateProd, gbc_2);
//            legend.addChart(chartRateProd);
        }
        chartPanel.setOpaque(true);
        chartPanel.setBackground(randomColor.randomColor());
        this.add(chartPanel, BorderLayout.CENTER);
        this.add(legendPanel, BorderLayout.SOUTH);
    }

    /* Method to create and display the chart infos below the visible chart */
    private void createLegendPanelComponents() throws SQLException {
//        legendPanel.setLayout(new GridLayout(0, 1));
        legendPanel.setLayout(new BorderLayout());
        legendPanel.setBackground(Color.BLACK);
        legendPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
//        JPanel currentTargetPanel = new JPanel(new BorderLayout(5, 5));
//        currentTargetPanel.setBackground(Color.WHITE);
//        currentTargetPanel.add(new JLabel(Double.toString(currentTarget)), BorderLayout.CENTER);
//        currentTargetPanel.add(new JLabel("Current Target: "), BorderLayout.BEFORE_LINE_BEGINS);
//        setLabelFont(currentTargetPanel);
//        legendPanel.add(currentTargetPanel);

//        JPanel actualValuePanel = new JPanel(new BorderLayout(5, 5));
//        actualValuePanel.setBackground(Color.WHITE);
//        actualValuePanel.add(new JLabel(Double.toString(actualBarValue)), BorderLayout.CENTER);
//        actualValuePanel.add(new JLabel("Actual Value: "), BorderLayout.BEFORE_LINE_BEGINS);
//        setLabelFont(actualValuePanel);
//        legendPanel.add(actualValuePanel);
        variance = actualBarValue - currentTarget;
//        JPanel variancePanel = new JPanel(new BorderLayout(5, 5));
//        variancePanel.setBackground(Color.WHITE);
//        final JLabel labelVariance = new JLabel(ConnectDB.DECIMALFORMAT.format(variance));
//        if (variance < 0) {
//            labelVariance.setBackground(Color.WHITE);
//            labelVariance.setForeground(Color.RED);
//        } else {
//            labelVariance.setBackground(Color.BLACK);
//            labelVariance.setForeground(Color.GREEN);
//        }
//        variancePanel.add(labelVariance, BorderLayout.CENTER);
//        variancePanel.add(new JLabel("Variance: "), BorderLayout.BEFORE_LINE_BEGINS);
//        setLabelFont(variancePanel);
//        legendPanel.add(variancePanel);

//        legendPanel.add(legend);
        StyledLabel styledLabel = new StyledLabel();
        customizeStyledLabel(styledLabel, variance, actualBarValue, currentTarget);

        MarqueePane horizonMarqueeLeft = new MarqueePane(styledLabel);
        horizonMarqueeLeft.setScrollAmount(6);
//        horizonMarqueeLeft.setScrollDelay(60);
        horizonMarqueeLeft.setStayPosition(17);
        horizonMarqueeLeft.setPreferredSize(new Dimension(250, 25));

        JPanel demoPanel = new JPanel(new BorderLayout());
        demoPanel.add(horizonMarqueeLeft);
        _horizonMarqueeLeft = horizonMarqueeLeft;
        legendPanel.add(demoPanel);
    }

    private void customizeStyledLabel(StyledLabel styledLabel, double variance, double actualBar, double currentTarget) throws SQLException {
        styledLabel.setText("     Current Target: " + currentTarget + "      Actual Value: " + actualBar
                + "      Variance: " + ConnectDB.DECIMALFORMAT.format(variance) + getEventsInformation());
//        System.out.println("00000Current0Target:0" + currentTarget + "00000000Actual0Value:0" + actualBar
//                + "000000Variance:0" + ConnectDB.DECIMALFORMAT.format(variance) + getEventsInformation());
//        System.out.println(styledLabel.getText().length());
        styledLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
//        styledLabel.setBackground(Color.BLACK);
        styledLabel.setForeground(Color.RED);
//        int[] steps = new int[]{20, 9, 19, 9, 13, 11, 15, 11, 15, 20, 15, 23};
        int[] steps = new int[]{20, 9, 19, 9, 13, 11, 15, 11, 15, 20, 15, 23};
//        char[] letter = new char[]{'C', 'A', 'V', 'O', 'D', 'P'};
//        for (int i = 0; i < letter.length; i++) {
//            System.out.println(styledLabel.getText().indexOf(letter[i]));
//        }
        int index = 0;
        for (int i = 0; i < steps.length; i++) {
            try {
//                System.out.print(index + "----" + (index + steps[i]) + " ==>");
//                System.out.println(styledLabel.getText().substring(index, index + steps[i]));
                if (i % 2 == 0) {
                    styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.GREEN, Color.BLACK, 0, Color.WHITE));
                } else {
                    if (styledLabel.getText().charAt(index + 1) == '-') {
                        styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.GREEN, Color.BLACK, 0, Color.WHITE));
                    } else {
                        styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.GREEN, Color.BLACK, 0, Color.WHITE));
                    }
                }
                index += steps[i];
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                continue;
            }
        }
    }

    private String getEventsInformation() throws SQLException {
        String operator = "", downtime = "", product = "";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("(SELECT c.Description, e.Value FROM eventlog e, customlist c, hardware h \n"
                + "WHERE h.`HwNo` = e.`HwNo` AND c.`Code` = e.`CustomCode` AND \n"
                + "e.`HwNo` =? AND c.`Code`=1 ORDER BY e.EventTime DESC LIMIT 1) \n"//Operator
                + "UNION \n"
                + "(SELECT c.Description, e.Value FROM eventlog e, customlist c, hardware h \n"
                + "WHERE h.`HwNo` = e.`HwNo` AND c.`Code` = e.`CustomCode` AND \n"
                + "e.`HwNo` =? AND c.`Code`=6 ORDER BY e.EventTime DESC LIMIT 1) \n"//Downtime
                + "UNION \n"
                + "(SELECT c.Description, e.Value FROM eventlog e, customlist c, hardware h \n"
                + "WHERE h.`HwNo` = e.`HwNo` AND c.`Code` = e.`CustomCode` AND \n"
                + "e.`HwNo` =? AND c.`Code`=4 ORDER BY e.EventTime DESC LIMIT 1)")) {//Products
            int machineID = ConnectDB.getIDMachine(machineName);
            ps.setInt(1, machineID);
            ps.setInt(2, machineID);
            ps.setInt(3, machineID);
            ResultSet resultSet = ps.executeQuery();
//            System.out.println(ps.toString());
            boolean getFristValue = true, getSecondValue = true;
            while (resultSet.next()) {
                if (getFristValue) {
                    operator = resultSet.getString(2);
                    getFristValue = false;
                } else if (getSecondValue) {
                    downtime = resultSet.getString(2);
                    getSecondValue = false;
                } else {
                    product = resultSet.getString(2);
                }
            }
//            return new StringBuilder("000000Operator:0").append(operator).append("0000000000Downtime:0").append(downtime).
//                    append("0000000000Product:0").append(product).append("0000").toString();
            return new StringBuilder("      Operator: ").append(operator).append("          Downtime: ").append(downtime).
                    append("          Product: ").append(product).append("    ").toString();
        }
    }

//    private void setLabelFont(JPanel paneComponent) {
//        Component[] components = paneComponent.getComponents();
//        for (Component component : components) {
//            if (component instanceof JLabel) {
//                component.setFont(new Font("Tahoma", Font.PLAIN, 13));
//            }
//        }
//    }
//    private final Paint chartBackground = new GradientPaint(0, 0, Color.GRAY, 0, 150, Color.WHITE);
    private final JPanel chartPanel = new JPanel();
    private final JPanel legendPanel = new JPanel();
    private final String machineName;
//    private final Legend legend = new Legend();
    private static double currentTarget, actualBarValue, variance;
    private static MarqueePane _horizonMarqueeLeft;
    private final RandomColor randomColor = new RandomColor();
}
