package chartTypes;

import com.alee.global.StyleConstants;
import com.jidesoft.chart.BarResizePolicy;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.range.Positionable;
import com.jidesoft.range.TimeRange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class HourlyRecords extends javax.swing.JPanel implements CumulativeSubractedValues {

    public HourlyRecords(String myBarChartPoint, int myConfigNo) throws SQLException {
        ConnectDB.getConnectionInstance();
        initComponents();
        chart = new Chart();
        String timeDate = myBarChartPoint.trim().replaceAll("\n\t", "");
        getLogData(timeDate, myConfigNo);

        Date from = null, to = null;
        try {
            from = ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(
                    new StringBuilder().append(ConnectDB.reverseWords(timeDate).substring(0, 13)).append(":00:00").toString()));
            to = ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(
                    new StringBuilder().append(ConnectDB.reverseWords(timeDate).substring(0, 13)).append(":59:59").toString()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        timeRange = new TimeRange(from.getTime(), to.getTime());
        xAxis = new TimeAxis(timeRange, "Time");
        chart.setXAxis(xAxis);
        yAxis = new NumericAxis(0, maxNumber + 10D);
        yAxis.setLabel(new AutoPositionedLabel("Total Parts", Color.BLACK));
        chart.setYAxis(yAxis);
//        chart.setLayout(new BorderLayout());
//        chart.setBorder(new EmptyBorder(5, 5, 10, 15));
        chart.autoRange();
        this.panChart.add(chart, BorderLayout.CENTER);
        this.panChart.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        this.panChart.setPaintFocus(true);
        this.panChart.setRound(StyleConstants.largeRound);
//        chart.addMousePanner(true, false).addMouseZoomer(true, false);
        ChartStyle style = new ChartStyle(new Color(60, 60, 220)).withBars();
        style.setBarWidthProportion(0.8);

        try {
            chart.addModel(this.createModel(), style);
            chart.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
        } catch (ParseException ex) {
            Logger.getLogger(HourlyRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Optional - can set a different bar renderer
        RaisedBarRenderer renderer = new RaisedBarRenderer();
        renderer.setZeroHeightBarsVisible(true);
        chart.setBarRenderer(renderer);

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
                    Positionable pos = c.getX();
                    double value = c.getY().position();
                    String tooltip = String.format("%s : %.1f", pos.position(), value);
                    chart.setToolTipText(tooltip);
                }
            }
        };
        chart.addMouseMotionListener(listener);
        chart.setVerticalGridLinesVisible(false);

//        xAxis.setTickLabelRotation(Math.PI / 16);
        xAxis.setTickLabelOffset(6);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panChart = new com.alee.laf.panel.WebPanel();
        btnClose = new com.alee.laf.button.WebButton();

        setBackground(new java.awt.Color(255, 255, 255));

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit16x16.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 413, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panChart, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void getLogData(String hourRecord, int configNo) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT LogTime, LogData FROM datalog \n"
                + "WHERE ConfigNo =? AND LogTime BETWEEN ? AND ?")) {
            ps.setInt(1, configNo);
            ps.setString(2, new StringBuilder().append(ConnectDB.reverseWords(hourRecord).substring(0, 13)).
                    append(":00:00").toString());
            ps.setString(3, new StringBuilder().append(ConnectDB.reverseWords(hourRecord).substring(0, 13)).
                    append(":59:59").toString());
            ConnectDB.res = ps.executeQuery();
            logDataList = new ArrayList<>();
            logTimeList = new ArrayList<>();
            subtractedLogData = new ArrayList<>();
            while (ConnectDB.res.next()) {
                logTimeList.add(ConnectDB.res.getString(1));
                logDataList.add(ConnectDB.res.getString(2));
            }
        }
        this.getSubtractedValues((byte) 0, logDataList);
    }

    @Override
    public void getSubtractedValues(byte x, ArrayList<String> alValues) {
        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i));
                subtractedLogData.add(new StringBuilder().append(logTimeList.get(i)).append(";").
                        append(xDiff).toString());
                continue;
            }
            xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i - 1));
            subtractedLogData.add(new StringBuilder().append(logTimeList.get(i)).append(";").
                    append(xDiff).toString());
            if (xDiff > maxNumber) {
                maxNumber = xDiff;
            }
        }
    }

    private DefaultChartModel createModel() throws ParseException {
        DefaultChartModel model = new DefaultChartModel();
        for (String string : subtractedLogData) {
            StringTokenizer value = new StringTokenizer(string, ";");
//            ChartCategory category = new ChartCategory(ConnectDB.SDATE_FORMAT_HOUR.parse(
//                    ConnectDB.correctToBarreDate(value[0])).getTime(), timeRange);
//            
//            Chartable p = new ChartPoint(category, Double.valueOf(value[1]));
            model.addPoint(ConnectDB.SDATE_FORMAT_HOUR.parse(
                    ConnectDB.correctToBarreDate(value.nextToken())).getTime(), Double.valueOf(value.nextToken()));
        }
        return model;
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Hourly Records");
        frame.setSize(500, 300);
        frame.getContentPane().add(new HourlyRecords("14h:00 2014-08-27", 16));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.alee.laf.button.WebButton btnClose;
    private com.alee.laf.panel.WebPanel panChart;
    // End of variables declaration//GEN-END:variables
    private int maxNumber;
    private ArrayList<String> logTimeList, logDataList, subtractedLogData;
    private final Chart chart;
    private final TimeAxis xAxis;
    private final NumericAxis yAxis;
    private final TimeRange timeRange;
}
