package dashboard;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

/**
 * Classe de test pour la classe ScreenManager.
 * 
 * @author Julien CHABLE (webmaster@neogamedev.com)
 * @version 1.0 20/06/2004
 */
public class ScreenTest extends Frame {

    // Notre classe qui va nous permettre de gerer notre ecran
    private ScreenManager sm = new ScreenManager();
    // Pour faire tourner notre 'aiguille'
    private double t = 0.0;

    // La fonction principale
    public static void main(String[] args) {
        DisplayMode dm;

        // Construction d'une resolution d'�cran
        if (args.length == 3) {
            dm = new DisplayMode(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]),
                    DisplayMode.REFRESH_RATE_UNKNOWN);
        } else {
            // Resolution par defaut, la plus courante
            dm = new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        }

        ScreenTest screen = new ScreenTest();
        // Test le plein ecran !
        screen.testFullScreen(dm);
    }

    public void testFullScreen(DisplayMode dm) {
        sm.setFullScreen(this, dm);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // Do nothing
        } finally {
            sm.restoreFullScreen();
        }
    }

    @Override
    public void paint(Graphics g) {
        Point centerScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        g.drawOval(centerScreen.x / 2, centerScreen.y / 2, getWidth() / 2, getHeight() / 2);
        g.drawLine(centerScreen.x, centerScreen.y, (int) (Math.cos(t) * getWidth() / 4) + centerScreen.x, 
                (int) (Math.sin(t) * getHeight() / 4) + centerScreen.y);
        t += 0.5;
        try {
            Thread.sleep(230);
        } catch (InterruptedException e) {
        }
        repaint();
    }
}
