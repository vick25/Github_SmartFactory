package chartTypes;

import com.jidesoft.chart.BarResizePolicy;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.LabelPlacement;
import com.jidesoft.chart.LineMarker;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.ChartSelectionEvent;
import com.jidesoft.chart.event.MouseWheelZoomer;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.event.PointSelectionEvent;
import com.jidesoft.chart.event.RectangleSelectionEvent;
import com.jidesoft.chart.event.RubberBandZoomer;
import com.jidesoft.chart.event.ZoomFrame;
import com.jidesoft.chart.event.ZoomListener;
import com.jidesoft.chart.event.ZoomLocation;
import com.jidesoft.chart.event.ZoomOrientation;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.Axis3DRenderer;
import com.jidesoft.chart.render.CylinderBarRenderer;
import com.jidesoft.chart.render.DefaultPointRenderer;
import com.jidesoft.chart.render.PointLabeler;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.LabelStyle;
import com.jidesoft.chart.util.ColorFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Positionable;
import com.jidesoft.range.Range;
import eventsPanel.StatKeyFactory;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import mainFrame.MainFrame;
import productionPanel.Flag;
import productionPanel.ProdStatKeyFactory;
import productionPanel.ProductionPane;
import productionPanel.ReadSmartServerIni;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

/**
 *
 * @author Victor Kadiata
 */
public class BarChart extends Chart implements CumulativeSubractedValues {

    public static ArrayList<String> getMessageFlag() {
        return messageFlag;
    }

    public static Flag getFlagDialog() {
        return flagDialog;
    }

    public static void setFlagDialog(Flag flagDialog) {
        BarChart.flagDialog = flagDialog;
    }

    public static ArrayList<Integer> getMaxValue() {
        return maxValue;
    }

    public boolean isInShifts() {
        return _inShifts;
    }

    public DefaultChartModel getModelPoints() {
//        return (DefaultChartModel) getChart().getModel();
        return modelPoints;
    }

    public List getDateWithShiftsList() {
        if (dateWithShiftsList != null) {
            return Collections.unmodifiableList(dateWithShiftsList);
        }
        return null;
    }

    public void setDateWithShiftsList(List dateWithShiftsList) {
        this.dateWithShiftsList = dateWithShiftsList;
    }

    public Chart getChart() {
        return chart;
    }

    public BarChart(final int myConfigNo, final String myQuery, final boolean myInShifts, String myMachineTitle,
            String myChanTitle, Date myStartDate, Date myEndDate) throws SQLException {
        super();
        this._inShifts = myInShifts;
        this.machineTitle = myMachineTitle;
        this._startD = myStartDate;
        this._endD = myEndDate;
        clearChart(); // wax old chart data first.
        chart = new Chart("Total");
        range = new CategoryRange<>();
        maxValue.clear();
        alDateHour.clear();
        alValues.clear();
        flagLogTime.clear();

        //SQL query
        PreparedStatement ps = ConnectDB.con.prepareStatement(myQuery);
        ps.setInt(1, myConfigNo);
        ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(myStartDate));
        ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(myEndDate));
//        System.out.println(ps.toString());
        ConnectDB.res = ps.executeQuery();
        loopQueryFound = false;
        //End SQL query
        if (_inShifts) {//Case with shifts
            ArrayList<String> dateData = new ArrayList<>();//get only the date from the database
            while (ConnectDB.res.next()) {
                String s = ConnectDB.res.getString(1);
                flagLogTime.add(s);
                dateData.add(s.substring(0, 10));//list to store each sorted date retrieved form the values arrays
            }
            ps.close();
            dateWithShiftsList = new ArrayList(new TreeSet<>(dateData));//list to store each sorted date retrieved form the values arrays
            for (short q = 0; q < dateWithShiftsList.size(); ++q) {
                Category cShifts = new ChartCategory(dateWithShiftsList.get(q), range);
                range.add(cShifts);
            }
            StringBuilder queryShiftBuilder;
//            String queryShift;
            //case with shifts
            sumHourValues = new String[ProductionPane.getTableOfTime().getRowCount()][dateWithShiftsList.size()];
            for (int k = 0; k < dateWithShiftsList.size(); k++) {//dates
                String fVal = "", sVal = "";
                for (int j = 0; j < ProductionPane.getTableOfTime().getRowCount(); j++) {//shifts as row.
                    alValues.clear();
                    alDateHour.clear();
                    subtractValues.clear();
                    if (j == 2) {//if line is 3rd shift
                        byte x = 0;//shift splited in 2 periods
                        while (x <= 1) {
                            if (ProductionPane.getTableOfTime().getValueAt(j, 1).toString().substring(0, 2).equals("22")
                                    || (Integer.parseInt(ProductionPane.getTableOfTime().getValueAt(j, 1).toString().
                                            substring(0, 2)) < 24)) {
                                if (x == 0) {//first split
                                    fVal = dateWithShiftsList.get(k).toString() + " "
                                            + ProductionPane.getTableOfTime().getValueAt(j, 1).toString() + ":00";
                                    sVal = dateWithShiftsList.get(k).toString() + " " + "23:59:59";
                                } else {//(00:00:00 to 06:00:00) of the next day
                                    fVal = addDate(dateWithShiftsList.get(k).toString()) + " " + "00:00:00";
                                    sVal = addDate(dateWithShiftsList.get(k).toString()) + " "
                                            + ProductionPane.getTableOfTime().getValueAt(j, 2).toString() + ":00";
                                }
                            }
                            queryShiftBuilder = new StringBuilder().append(myQuery.substring(0, myQuery.indexOf("ORDER")).trim()).
                                    append(" AND (d.LogTime BETWEEN '").append(fVal).append("' AND '").
                                    append(sVal).append("') \n ORDER BY 'Time' ASC");
                            alValues.clear();
                            runQueryShift(x, queryShiftBuilder.toString(), myConfigNo, myStartDate, myEndDate);
//                                if (x == 0) {
//                                    for (int i = 0; i < alValues.size(); i++) {
//                                        System.out.println(alValues.get(i));
//                                    }
//                                }
                            getSubtractedValues(x, alValues);
                            x++;
                        }
                    } else {//First and Second shift
                        fVal = dateWithShiftsList.get(k).toString() + " "
                                + ProductionPane.getTableOfTime().getValueAt(j, 1).toString() + ":00";
                        sVal = dateWithShiftsList.get(k).toString() + " "
                                + ProductionPane.getTableOfTime().getValueAt(j, 2).toString() + ":00";
                        queryShiftBuilder = new StringBuilder().append(myQuery.substring(0, myQuery.indexOf("ORDER")).trim()).
                                append("\nAND (d.LogTime BETWEEN '").append(fVal).append("' AND '").
                                append(sVal).append("')\n ORDER BY 'Time' ASC");
                        runQueryShift((byte) -1, queryShiftBuilder.toString(), myConfigNo, myStartDate, myEndDate);
                    }
                    int sum = 0;
                    if (loopQueryFound) {
                        if (j != 2) {//First and Second shift
                            getSubtractedValues((byte) -1, alValues);//send the values to get the difference in each hour
                            for (String subtractValue : subtractValues) {
                                if (subtractValue.contains(dateWithShiftsList.get(k).toString())) {
                                    String[] val = subtractValue.split(";");
                                    sum += Integer.parseInt(val[1]);
                                }
                            }
                        } else {
                            int v = k;
                            while (v < dateWithShiftsList.size()) {
                                for (String subtractValue : subtractValues) {
                                    if (subtractValue.contains(dateWithShiftsList.get(v).toString())) {
                                        String[] val = subtractValue.split(";");
                                        sum += Integer.parseInt(val[1]);
                                    }
                                }
//                                 for (int i = 0; i < subtractValues.size(); i++) {
//                                    if (subtractValues.get(i).contains(DateWithShifts.get(v).toString())) {
//                                        String[] val = subtractValues.get(i).split(";");
//                                        sum += Integer.parseInt(val[1]);
//                                    }
//                                }
                                v++;
                            }
                        }
                        int w = k;//copy the index of each date as k to w
                        sumHourValues[j][w] = String.valueOf(sum);
                        maxValue.add(sum);
                    }
                }//end of each shift in the tableTime
            }//end of each date
            if (loopQueryFound) {
//                    for (int s = 0; s < sumHourValues.length; s++) {
//                        for (int y = 0; y < sumHourValues[s].length; y++) {
//                            System.out.print(sumHourValues[s][y] + "   ");
//                        }
//                        System.out.println();
//                    }
                List<List<String>> list = new ArrayList<>();
                for (String[] row : sumHourValues) {
                    list.add(new ArrayList<>(Arrays.asList(row)));//Treat each row of timeDifference as an array
                }
                chart.setTitle(new AutoPositionedLabel(new StringBuilder("\"").append(this.machineTitle)
                        .append("\" Total Production Per Shift").toString(), Color.BLACK, ConnectDB.TITLEFONT));
                ProductionPane.setChartTitle(chart.getTitle().toString());
                chart.setBarsGrouped(true);
                chart.setBarGroupGapProportion(0.6);//changed from 0.5 for the size of the bar
                CylinderBarRenderer barR = new CylinderBarRenderer();
                barR.setAlwaysShowOutlines(ConnectDB.pref.getBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, false));
                barR.setZeroHeightBarsVisible(true);
                barR.setOutlineWidth(5f);
                chart.setBarRenderer(barR);
                chart.getXAxis().setAxisRenderer(new Axis3DRenderer());
//                RaisedBarRenderer renderer = new RaisedBarRenderer(5);
//                renderer.setSelectionColor(Color.black);
//                renderer.setOutlineWidth(5f);
//                renderer.setZeroHeightBarsVisible(true);
//                chart.setBarRenderer(renderer);
//                chart.getXAxis().setAxisRenderer(new Axis3DRenderer());

                byte shName = 0;
                ColorFactory colorFactory = new ColorFactory(Color.red, Color.orange, Color.blue);
                DefaultChartModel modelShift = null;
                for (List<String> row : list) {
//                        System.out.println("row:" + row);
                    row.add(0, new StringBuilder("Shift ").append(++shName).toString());
                    String modelName = row.remove(0);//get each line of data
                    modelShift = new DefaultChartModel(modelName);
                    int column = 1;
                    for (String rowValue : row) {
                        Double value = (rowValue == null) ? Double.valueOf("0") : Double.parseDouble(rowValue);
                        modelShift.addPoint(range.getCategory(column), value);
                        column++;
                    }
                    ChartStyle styleS = new ChartStyle(colorFactory.create()).withBars();
//                    styleS.setBarWidth(50);
//                    styleS.setPointsVisible(true);
                    chart.addModel(modelShift, styleS);
                }
                modelPoints = modelShift;//save and send the modelshift
                list.clear();
                raiseFlag();//method for the flag
            }
        } else {        //****************No time Shifts*************************
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                String s = ConnectDB.res.getString(1);
                flagLogTime.add(s);
                alDateHour.add(s.substring(0, 13));//only the Date and Hour
                alValues.add(ConnectDB.res.getString(2));
            }
            ps.close();
            subtractValues.clear();
            getSubtractedValues((byte) -1, alValues);//send the values to get the difference in each hour
            eachDateH = new ArrayList(new TreeSet<>(alDateHour));//sort the Time data and remove duplicates
            List<Category> dateCategoryChart = new ArrayList<>();
            for (int i = 0; i < eachDateH.size(); i++) {
                Category c = new ChartCategory((Object) ConnectDB.reverseWords(new StringBuilder(eachDateH.get(i)).
                        append("h:00").toString()), range);
                dateCategoryChart.add(c);
                range.add(c);
                countBar = i;
            }
            DefaultChartModel modelShift = new DefaultChartModel("Hourly Total parts");
            for (int j = 0; j < dateCategoryChart.size(); ++j) {
                int sum = 0;
                for (String subtractValue : subtractValues) {
                    if (subtractValue.contains(eachDateH.get(j))) {
                        String[] val = subtractValue.split(";");
                        sum += Integer.parseInt(val[1]);
                    }
                }
                maxValue.add(sum);
                modelShift.addPoint(dateCategoryChart.get(j), sum);
            }
            chart.setTitle(new AutoPositionedLabel(new StringBuilder("\"").append(this.machineTitle).
                    append("\" Hourly Total Production").toString(), Color.BLACK, ConnectDB.TITLEFONT));
            ProductionPane.setChartTitle(chart.getTitle().toString());
            chart.addModel(modelShift);
            chart.setPanelBackground(new Color(153, 153, 153));
            modelPoints = modelShift;//save the modelshift
            raiseFlag();//method for the flag
        }
        //
        if (loopQueryFound) {
            double target = ConnectDB.getMachineTarget(machineTitle, "Cumulative");
            switch (ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour")) {
                case "second":
                    target *= 3600;
                    break;
                case "minute":
                    target *= 60;
                    break;
            }
            chart.setPreferredSize(new Dimension(600, 300));
            CategoryAxis xAxis = new CategoryAxis<>(range);
            chart.setXAxis(xAxis);
//            xAxis.setTickLabelRotation(Math.PI / 4);
            chart.getXAxis().setTicksVisible(true);
            int maxNumber = ConnectDB.maxNumber(maxValue);
            if (maxNumber < target) {
                maxNumber = (int) target;
            }
            NumericAxis yAxis = new NumericAxis(0, maxNumber + (int) ((maxNumber < 100 ? 0.35 : 0.08) * maxNumber));

            chart.setLayout(new BorderLayout());
            chart.setBorder(new EmptyBorder(5, 5, 10, 15));
//            chart.setShadowVisible(true);

            if (_inShifts) {
                yAxis.setLabel(new AutoPositionedLabel("Total Parts", Color.BLACK));
                chart.setGridColor(new Color(150, 150, 150));
                chart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f,
                        Color.LIGHT_GRAY));
            } else {
                //No shifts
                yAxis.setLabel(new AutoPositionedLabel("Total/hrs", Color.BLACK));
                ChartStyle style;
                if (countBar > 25) {//lines
                    JOptionPane.showMessageDialog(chart, "Too many data points for a bar plot. Switching to a line chart.",
                            "Chart", JOptionPane.INFORMATION_MESSAGE);
                    DefaultPointRenderer pointRenderer = new DefaultPointRenderer();
                    pointRenderer.setAlwaysShowOutlines(true);
                    pointRenderer.setOutlineColor(Color.WHITE);
                    pointRenderer.setOutlineWidth(1f);
                    chart.setPointRenderer(pointRenderer);
                    chart.setAutoRanging(true);
//                    chart.addMousePanner();
                    MouseWheelZoomer zoomer = new MouseWheelZoomer(chart, true, false);
                    zoomer.setZoomLocation(ZoomLocation.MOUSE_CURSOR);
                    chart.addMouseWheelListener(zoomer);
                    style = new ChartStyle(new Color(0, 75, 190), true, true);
                    style.setLineFill(new Color(0, 75, 190, 75));
                    style.setLineWidth(3);
                    style.setPointSize(13);
                    style.setPointShape(PointShape.BOX);
                    ChartStyle selectionStyle = new ChartStyle(style);
                    selectionStyle.setPointSize(15);
                    chart.setHighlightStyle(ConnectDB.SELECTION_HIGHLIGHT, selectionStyle);
                } else {//bars
                    chart.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
                    chart.setBarGap(8);
                    RaisedBarRenderer barRenderer = new RaisedBarRenderer(5);
                    LabelStyle labelStyle = new LabelStyle();
                    labelStyle.setColor(Color.RED);
                    barRenderer.setZeroHeightBarsVisible(true);
                    barRenderer.setLabelStyle(labelStyle);
                    barRenderer.setLabelsVisible(true);// Add this to show labels for bars
                    barRenderer.setOutlineColor(Color.WHITE);//the oultine color of a bar
                    barRenderer.setOutlineWidth(1.6f);
                    barRenderer.setAlwaysShowOutlines(true);
                    // This is optional to format the display string
                    barRenderer.setPointLabeler(new PointLabeler() {
                        @Override
                        public String getDisplayText(Chartable p) {
                            Positionable yPos;
                            try {
                                yPos = p.getY();
                                return yPos == null ? "" : String.format("%.0f", yPos.position());
                            } catch (Exception e) {
                                return "";
                            }
                        }
                    });
                    chart.setBarRenderer(barRenderer);
//                    chart.setAnimateOnShow(true);
                    chart.setRolloverEnabled(true);
                    style = new ChartStyle(Color.GREEN, false, false);
                    style.setBarsVisible(true);
                    chart.setGridColor(new Color(150, 150, 150));
                    chart.setChartBackground(new GradientPaint(0f, 0f, Color.LIGHT_GRAY.brighter(), 300f, 300f,
                            Color.LIGHT_GRAY));
                    chart.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            PointDescriptor shape = chart.containingShape(e.getPoint());
                            if (shape != null) {
                                if (e.getClickCount() == 2) {
                                    try {
                                        String chartPoint = ((Category) shape.getChartable().getX()).getValue().toString();
                                        HourlyRecords hourlyRecords = new HourlyRecords(chartPoint, myConfigNo);
                                        if (hourlyRecordsDialog == null) {
                                            hourlyRecordsDialog = new JDialog(MainFrame.getFrame(), false);
                                            hourlyRecordsDialog.setLayout(new BorderLayout());
                                            hourlyRecordsDialog.setTitle("Hourly Records");
                                            hourlyRecordsDialog.setSize(new Dimension(500, 300));
                                            hourlyRecordsDialog.setLocationRelativeTo(MainFrame.getFrame());
                                        } else {
                                            hourlyRecordsDialog.getContentPane().removeAll();
                                        }
                                        hourlyRecordsDialog.getContentPane().add(hourlyRecords);
                                        hourlyRecordsDialog.setVisible(true);
                                        hourlyRecords.btnClose.addActionListener(new ActionListener() {

                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                hourlyRecordsDialog.dispose();
                                            }
                                        });
                                    } catch (SQLException ex) {
                                        ConnectDB.catchSQLException(ex);
                                    }
                                }
                            }
                        }
                    });
                }
                chart.setVerticalGridLinesVisible(false);
                chart.setStyle(modelPoints, style);
            }
            setupRubberBandZoomer(this.chart, this.chart.getXAxis(), this.chart.getYAxis());
            chart.getYAxis().setTicksVisible(true);
            chart.getYAxis().setVisible(true);
            chart.setYAxis(yAxis);

            /*Get and apply the machine target to the chart */
            if (target > 0d) {
                LineMarker marker = new LineMarker(chart, Orientation.horizontal, target, Color.RED);
                marker.setLabel("Target");
                marker.setLabelPlacement(LabelPlacement.NORTH_WEST);
                chart.addDrawable(marker);
            }
            chart.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (Chart.PROPERTY_CURRENT_CHART_POINT.equals(evt.getPropertyName())) {
                        Chartable chartable = chart.getCurrentChartPoint();
                        if (chartable == null) {
                            chart.setToolTipText(null);
                        } else {
                            if (_inShifts) {
                                try {
                                    Point screenLocation = MouseInfo.getPointerInfo().getLocation();
                                    Point p1 = new Point(screenLocation);
                                    SwingUtilities.convertPointFromScreen(p1, chart);
                                    PointDescriptor descriptor = chart.containingBar(p1);
                                    chartable = descriptor.getChartable();
                                    ChartModel model = descriptor.getModel();
                                    PointDescriptor subDescriptor = descriptor.getSubPoint();
                                    if (subDescriptor != null) {
                                        chartable = subDescriptor.getChartable();
                                        model = subDescriptor.getModel();
                                    }
                                    String toolTipText;
                                    if (chartable instanceof SpecialPoint) {
                                        // Extract the additional information and display it in the tool tip
                                        SpecialPoint sp = (SpecialPoint) chartable;
                                        toolTipText = String.format("%s %.2f %s", model.getName(),
                                                sp.getY().position(), sp.getSpecialString());
                                    } else {
                                        toolTipText = String.format("%s: %.2f", model.getName(),
                                                chartable.getY().position());
                                    }
                                    chart.setToolTipText(toolTipText);
                                } catch (NullPointerException | HeadlessException e1) {
                                }
                            } else {
                                int xPos = (int) chartable.getX().position() - 1;
                                StringBuilder builder = new StringBuilder("<html><table>");
                                builder.append(String.format("<tr><td><b>Date</b></td><td>%sh:00</td></tr>",
                                        eachDateH.get(xPos)));
                                builder.append(String.format("<tr><td><b>Value</b></td><td>%.0f part(s)</td></tr>",
                                        chartable.getY().position()));
                                builder.append("</table></html>");
                                chart.setToolTipText(builder.toString());
                            }
                        }
                    }
                }
            });
            chart.update();
        } else {
            chart = null;
            ConnectDB.showChartMessageDialog(MainFrame.getFrame());
        }
    }

    private void clearChart() {
        if (chart == null) {
            return;
        }
        getModelPoints().clearPoints();
    }

    private void setupRubberBandZoomer(final Chart chartToZoom, final Axis xAxis, final Axis yAxis) {
        zoomStack = new Stack<>();
        RubberBandZoomer rubberBand = new RubberBandZoomer(chartToZoom);
        rubberBand.setZoomOrientation(ZoomOrientation.BOTH);
        rubberBand.setOutlineColor(Color.GREEN);
        rubberBand.setOutlineStroke(new BasicStroke(2f));
        rubberBand.setFill(new Color(100, 128, 100, 50));
        rubberBand.setKeepWidthHeightRatio(true);

        chartToZoom.addDrawable(rubberBand);
        chartToZoom.addMouseListener(rubberBand);
        chartToZoom.addMouseMotionListener(rubberBand);

        rubberBand.addZoomListener(new ZoomListener() {
            @Override
            public void zoomChanged(ChartSelectionEvent event) {
                if (event instanceof RectangleSelectionEvent) {
                    Range<?> currentXRange = chartToZoom.getXAxis().getOutputRange();
                    Range<?> currentYRange = chartToZoom.getYAxis().getOutputRange();
                    ZoomFrame frame = new ZoomFrame(currentXRange, currentYRange);
                    zoomStack.push(frame);
                    Rectangle selection = (Rectangle) event.getLocation();
                    Point topLeft = selection.getLocation();
                    //Point bottomRight = new Point(topLeft.x + selection.width, topLeft.y + selection.height);
                    // Always select down to the x axis
                    Point bottomRight = new Point(topLeft.x + selection.width, chartToZoom.getYStart());
                    assert bottomRight.x >= topLeft.x;
                    Point2D rp1 = chartToZoom.calculateUserPoint(topLeft);
                    Point2D rp2 = chartToZoom.calculateUserPoint(bottomRight);
                    if (rp1 != null && rp2 != null) {
                        assert rp2.getX() >= rp1.getX() : rp2.getX() + " must be greater than or equal to " + rp1.getX();
                        CategoryRange xRange = new CategoryRange((CategoryRange) currentXRange);
                        xRange.setMinimum(rp1.getX());
                        xRange.setMaximum(rp2.getX());
                        assert rp1.getY() >= rp2.getY() : rp1.getY() + " must be greater than or equal to " + rp2.getY();
                        Range<?> yRange = new NumericRange(rp2.getY(), rp1.getY());
                        xAxis.setRange(xRange);
                        yAxis.setRange(yRange);
                    }
                } else if (event instanceof PointSelectionEvent) {
                    if (zoomStack.size() > 0) {
                        ZoomFrame frame = zoomStack.pop();
                        Range<?> xRange = frame.getXRange();
                        Range<?> yRange = frame.getYRange();
                        xAxis.setRange(xRange);
                        yAxis.setRange(yRange);
                    }
                }
            }
        });
    }

    @Override
    public void getSubtractedValues(byte x, ArrayList<String> alValues) throws SQLException {
        ArrayList<String> prodRateArrayList = getProductionRate();
        for (int i = 0; i < alValues.size(); i++) {
            int xDiff;
            if (i == 0) {
                if (x == 1) {//Third shift and next day
                    xDiff = Integer.parseInt(alValues.get(i)) - preShiftLastValue;
                    if (xDiff < 0) {//Case where the difference is negative
                        xDiff = 1000000 - preShiftLastValue + Integer.parseInt(alValues.get(i));
                    }
                    subtractValues.add(new StringBuilder().append(alDateHour.get(++countAlValues)).append(";").append(xDiff).toString());
                } else {//Same day
                    xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i));
                    subtractValues.add(new StringBuilder().append(alDateHour.get(i)).append(";").append(xDiff).toString());
                }
                continue;
            }
            int sum = 0;
            xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i - 1));
            if (xDiff < 0) {//Case where the value is negative (rollover)
                xDiff = 1000000 - Integer.parseInt(alValues.get(i - 1)) + Integer.parseInt(alValues.get(i));
            } else if (xDiff > 500) {
                byte rollback = 5;
                int yr = i;
                while (rollback > 5) {
                    System.out.println("jump over value recorded");
                    try {
                        sum += (int) Double.parseDouble(prodRateArrayList.get(yr));
                        yr--;
                        rollback--;
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                xDiff = sum / rollback;
            }
//            try {
//                int cumulVal = Integer.parseInt(alValues.get(i - 1)),
//                        nextCumulVal = Integer.parseInt(alValues.get(i)),
//                        rateVal = (int) Double.parseDouble(prodRateArrayList.get(i + 1));
//                //Case where the cumulative values are not sequential by adding the production rate number
//                if ((cumulVal + rateVal + 1 == nextCumulVal) || (cumulVal + rateVal == nextCumulVal)
//                        || (cumulVal + rateVal + 2 == nextCumulVal)) {
//                    xDiff = Integer.parseInt(alValues.get(i)) - Integer.parseInt(alValues.get(i - 1));
//                    if (xDiff < 0) {//Case where the value is negative
//                        xDiff = 1000000 - Integer.parseInt(alValues.get(i - 1)) + Integer.parseInt(alValues.get(i));
//                    }
//                } else {// Not sequential
//                    xDiff = rateVal;
//                }
////                System.out.println(cumulVal + " -- " + nextCumulVal + " -- " + rateVal + " --- " + xDiff);
//            } catch (IndexOutOfBoundsException e) {
//                xDiff = (int) Double.parseDouble(prodRateArrayList.get(i - 1));
//            }
            if (x == 1) {//Third shift and next day
                subtractValues.add(new StringBuilder().append(alDateHour.get(++countAlValues)).append(";").append(xDiff).toString());
            } else {//Same day
                subtractValues.add(new StringBuilder().append(alDateHour.get(i)).append(";").append(xDiff).toString());
            }
        }
    }

    private void runQueryShift(byte x, String query, int IDChannel, Date myStartDate, Date myEndDate) throws SQLException {
        try (PreparedStatement psShift = ConnectDB.con.prepareStatement(query)) {
            psShift.setInt(1, IDChannel);
            psShift.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(myStartDate));
            psShift.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(myEndDate));
            ConnectDB.res = psShift.executeQuery();
//            System.out.println(psShift.toString());
            while (ConnectDB.res.next()) {
                loopQueryFound = true;
                alDateHour.add(ConnectDB.res.getString(1).substring(0, 13));//Date and Hour
                alValues.add(ConnectDB.res.getString(2));
            }
            if (x == 0) {
                countAlValues = alValues.size() - 1;
                if (countAlValues != -1) {
                    preShiftLastValue = Integer.parseInt(alValues.get(countAlValues).toString());
                }
            }
        }
    }

    private String addDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date date = formatter.parse(dateInString);
            c.setTime(date);
            c.add(Calendar.DATE, 1);//add one day
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(c.getTime());
    }

    private ArrayList<String> getProductionRate() throws SQLException {
        int configNo = -1;
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_CONFIGNO)) {
            ps.setString(1, "rate");
            ps.setString(2, machineTitle);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                configNo = ConnectDB.res.getInt(1);
            }
        }
        ArrayList<String> listProductionRate = new ArrayList<>();
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.DATALOG_PRODUCTION)) {
            ps.setInt(1, configNo);
            ps.setString(2, ConnectDB.SDATE_FORMAT_HOUR.format(_startD));
            ps.setString(3, ConnectDB.SDATE_FORMAT_HOUR.format(_endD));
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                listProductionRate.add(ConnectDB.res.getString(2)); //Values
            }
        }
        return listProductionRate;
    }

    private void raiseFlag() {
        Thread flagThread = new Thread() {

            @Override
            public void run() {
                messageFlag = new ArrayList<>();
                showFlagUI = false;
                int flagTime = ConnectDB.pref.getInt(ProdStatKeyFactory.ProdFeatures.SPFLAGTIMEFRAME, 10);
                for (int i = 0; i < flagLogTime.size() - 1; i++) {
                    try {
                        /* Time difference in minutes. */
                        double[] diffs = ConnectDB.getTimeDifference(
                                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(flagLogTime.get(i))),
                                ConnectDB.SDATE_FORMAT_HOUR.parse(ConnectDB.correctToBarreDate(flagLogTime.get(i + 1))));
                        if (diffs[2] > flagTime) {
                            //create a timer to make text blink periodically
                            makeItBlink = new Timer(blinkInterval, new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e1) {
                                    try {
                                        if (blinkState) {
                                            ProductionPane.btnMessage.setIcon(
                                                    new ImageIcon(getClass().getResource("/images/icons/light_red.png")));
                                            if (y == 10) {
                                                blinkState = false;
                                                makeItBlink.stop();
                                            }
                                        } else {
                                            ProductionPane.btnMessage.setIcon(
                                                    new ImageIcon(getClass().getResource("/images/icons/light_on.png")));
                                        }
                                        blinkState = !blinkState;
                                        y++;
                                    } catch (NullPointerException e) {
                                        makeItBlink.stop();
                                    }
                                }
                            });
                            // start it up
                            makeItBlink.start();
                            ++countFlag;
                            messageFlag.add(countFlag + ". " + flagLogTime.get(i) + " and "
                                    + flagLogTime.get(i + 1) + " time slot. (" + diffs[2] + " min)");
                            ProductionPane.btnMessage.setToolTipText(countFlag + " flag(s) raised.");
                            showFlagUI = true;
                        } else {
                            ProductionPane.btnMessage.setIcon(
                                    new ImageIcon(getClass().getResource("/images/icons/light_on.png")));
                            ProductionPane.btnMessage.setToolTipText(countFlag + " flag(s) raised.");
                        }
                    } catch (ParseException | NumberFormatException ex) {
//                        ex.printStackTrace();
                    }
                }
                if (showFlagUI) {
                    populateListeFlag();
                }
            }
        };
        flagThread.start();
        if (ReadSmartServerIni.logOperation.equals("Schedule")) {
        }
    }

    private void populateListeFlag() {
        if (flagDialog != null) {
            flagDialog.dispose();
            showFlagUI = false;
        }
        flagDialog = new Flag(MainFrame.getFrame(), false, messageFlag);
        flagDialog.setVisible(true);
    }

    private class SpecialPoint extends ChartPoint {

        private final String specialString;

        SpecialPoint(Positionable x, double y, String specialString) {
            super(x, y);
            this.specialString = specialString;
        }

        public String getSpecialString() {
            return specialString;
        }
    }

    private List dateWithShiftsList;
    private final String machineTitle;
    private final Date _startD, _endD;
    private int countBar, countAlValues, preShiftLastValue, countFlag, y = 0;
    private static final int blinkInterval = 500;   // in milliseconds
    private volatile boolean blinkState = true, loopQueryFound = false, showFlagUI = false;
    private static final ArrayList alDateHour = new ArrayList(), alValues = new ArrayList();
    private static CategoryRange range;
//    private final String _machineTitle;
//    private final Date _startD, _endD;
    private boolean _inShifts;
    private DefaultChartModel modelPoints;
    public static String[][] sumHourValues;
    public static List<String> eachDateH;
    private ArrayList<String> subtractValues = new ArrayList<>(), flagLogTime = new ArrayList<>();
    private static ArrayList<String> messageFlag = new ArrayList<>();
    private static final ArrayList<Integer> maxValue = new ArrayList<>();
    private static Flag flagDialog;
    private Chart chart;
    public Timer makeItBlink;
    private JDialog hourlyRecordsDialog;
    private Stack<ZoomFrame> zoomStack = null;
}
