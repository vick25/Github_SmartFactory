package productionQuickView;

import java.awt.Graphics2D;

public class Ball {

    private static final int DIAMETER = 10;
    private final ProductionQuickView quickBall;

    public Ball(ProductionQuickView quickBall) {
        this.quickBall = quickBall;
    }

    public void paint(Graphics2D g) {
        g.setColor(this.quickBall.ballColor);
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
    private final int x = 0, y = 5;
}
