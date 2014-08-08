package productionPanelChartsPanel;

import chartTypes.LineChart;
import static chartTypes.LineChart.alTime;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Drawable;
import com.jidesoft.chart.event.PointSelection;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.model.Highlightable;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author Victor Kadiata
 */
public class ProductionRate extends javax.swing.JPanel {

    public ProductionRate(Chart chart, final String unit) {
        initComponents();
        this.chartPanel = chart;
        this.removeAll();
        this.repaint();
        setLayout(new BorderLayout());

        MouseMotionListener listener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                PointSelection ps = chartPanel.nearestPoint(p, LineChart.model);
                double distance = ps.getDistance();
                if (distance < 50) {
                    if (selectedPoint != ps.getSelected()) {
                        if (selectedPoint != null) {
                            Highlightable h = (Highlightable) selectedPoint;
                            h.setHighlight(null);
                        }
                        selectedPoint = ps.getSelected();
                        if (selectedPoint instanceof Highlightable) {
                            Highlightable h = (Highlightable) selectedPoint;
                            h.setHighlight(selectionHighlight);
                            if (toolTip == null) {
                                toolTip = new CustomToolTip();
                                chartPanel.addDrawable(toolTip);
                            }
                            try {
                                int xPos = (int) selectedPoint.getX().position() - 1;
                                toolTip.setText(String.format("%.0f " + unit, selectedPoint.getY().position()));
//                                toolTip.setText(String.format("%.0f parts/hr", selectedPoint.getY().position()));
                                toolTip.setSubText(String.format(alTime.get(xPos), selectedPoint.getX().position()));
                                chartPanel.repaint();
                            } catch (java.lang.IndexOutOfBoundsException ex) {
                            }
                        }
                        moveToolTipTo(getToolTipLocation(ps));
                    }
                }
                ps.getSelected();
            }
        };
        if (this.chartPanel != null) {
            this.chartPanel.addMouseMotionListener(listener);
            this.add(this.chartPanel, BorderLayout.CENTER);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void moveToolTipTo(final Point targetLocation) {
        if (toolTipTimer != null && toolTipTimer.isRunning()) {
            toolTipTimer.stop();
        }
        Point2D currentPosition = toolTip.getLocation();
        if ((currentPosition != null && currentPosition.getX() == 0.0 && currentPosition.getY() == 0)
                || !toolTip.isVisible()) {
            // Go straight to the correct location for the first one
            toolTip.setVisible(true);
            toolTip.setLocation(targetLocation);
            return;
        }
        toolTipLocation = currentPosition == null ? null : new Point2D.Double(currentPosition.getX(),
                currentPosition.getY());
        ActionListener toolTipMover = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (toolTipLocation == null) {
                    return;
                }
                double xDiff = targetLocation.x - toolTipLocation.getX();
                double yDiff = targetLocation.y - toolTipLocation.getY();
                if (Math.abs(xDiff) < 1 && Math.abs(yDiff) < 1) {
                    toolTipTimer.stop();
                    toolTipDisappearDelay();
                } else {
                    final double convergenceSpeed = 5.0;
                    double x1 = toolTipLocation.getX() + xDiff / convergenceSpeed;
                    double y1 = toolTipLocation.getY() + yDiff / convergenceSpeed;
                    toolTipLocation = new Point2D.Double(x1, y1);
                    toolTip.setLocation(toolTipLocation);
                    chartPanel.repaint();
                }
            }
        };
        toolTipTimer = new Timer(30, toolTipMover);
        toolTipTimer.start();
    }

    /**
     * Starts a timer for the disappearance of the tooltip
     */
    private void toolTipDisappearDelay() {
        ActionListener disappearListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                toolTip.setVisible(false);
                chartPanel.repaint();
            }
        };
        toolTipTimer = new Timer(6000, disappearListener);
        toolTipTimer.setRepeats(false);
        toolTipTimer.start();
    }

    /**
     * Uses some basic intelligence about the size of the chartPanel object to compute the location of the
     * tool tip
     */
    private Point getToolTipLocation(PointSelection ps) {
        Chartable chartable = ps.getSelected();
        Point2D userPoint = new Point2D.Double(chartable.getX().position(), chartable.getY().position());
        Point pixelPoint = chartPanel.calculatePixelPoint(userPoint);
        Point location = new Point(pixelPoint.x + TOOLTIPXOFFSET, pixelPoint.y + TOOLTIPYOFFSET);
        if (location.y < 0) {
            location.y = 0;
        }
        if (location.y + TOOL_TIP_HEIGHT > chartPanel.getChartHeight()) {
            location.y = chartPanel.getChartHeight() - TOOL_TIP_HEIGHT;
        }

        if (location.x + TOOL_TIP_WIDTH > chartPanel.getXEnd()) {
            location.x = pixelPoint.x - TOOL_TIP_WIDTH - TOOLTIPXOFFSET;
        }
        return location;
    }

    class CustomToolTip implements Drawable {

        private final Color background = Color.BLACK;
        private final Color foreground = Color.WHITE;
        private final Font labelFont = UIManager.getFont("Label.font");
        private final Font textFont = labelFont.deriveFont(Font.BOLD, 10f);
        private final Font subTextFont = labelFont.deriveFont(Font.PLAIN, 10f);
        private Point2D location;
        private boolean visible;
        private String text, subText;

        public CustomToolTip() {
        }

        public Point2D getLocation() {
            return location;
        }

        public void setLocation(Point2D location) {
            this.location = location;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setSubText(String text) {
            this.subText = text;
        }

        @Override
        public void draw(Graphics g) {
            int width = TOOL_TIP_WIDTH;
            int height = TOOL_TIP_HEIGHT;
            if (g == null || !visible || width == 0 || height == 0) {
                return;
            }
            Point2D loc = getLocation();
            try {
                if (loc != null) {
                    Graphics2D g2 = (Graphics2D) g.create((int) loc.getX(), (int) loc.getY(), width, height);
                    g2.setColor(background);
                    g2.fillRoundRect(0, 0, width, height, 12, 12);
                    g2.setColor(foreground);
                    FontMetrics fm = g2.getFontMetrics(textFont);
                    FontMetrics fm2 = g2.getFontMetrics(subTextFont);
                    int textWidth = fm.stringWidth(text);
                    int subTextWidth = fm2.stringWidth(subText);
                    g2.setFont(textFont);
                    g2.setColor(Color.white);
                    g2.drawString(text, (TOOL_TIP_WIDTH - textWidth) / 2, 20);
                    g2.setFont(subTextFont);
                    g2.setColor(new Color(0, 75, 190).brighter());
                    g2.drawString(subText, (TOOL_TIP_WIDTH - subTextWidth) / 2, 38);
                    g2.setColor(Color.gray);
                    g2.setStroke(new BasicStroke(3f));
                    g2.drawRoundRect(0, 0, width - 1, height - 1, 12, 12);
                    g2.dispose();
                }
            } catch (Exception e) {
                System.out.println("too much data");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    Chart chartPanel;
    private Timer toolTipTimer;
    private CustomToolTip toolTip;
    private Point2D toolTipLocation;
    private static Chartable selectedPoint;
    private static final Highlight selectionHighlight = new Highlight("selection");
    static final int TOOL_TIP_WIDTH = 100,
            TOOL_TIP_HEIGHT = 50,
            TOOLTIPXOFFSET = 20,
            TOOLTIPYOFFSET = -25;
}
