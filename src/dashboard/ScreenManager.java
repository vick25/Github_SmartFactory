package dashboard;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * Cette classe est un gestionnaire d'ecran et permet de changer la resolution
 * de l'ecran ou de mettre le mode plein-ecran.
 * 
 * @author Julien CHABLE (webmaster@neogamedev.com)
 * @version 1.0 20/06/2004
 */
public class ScreenManager {

    // Notre device (abstraction de la carte graphique ...) graphique sur lequel
    // nous allons effectuer les changements de r�solution, le plein-�cran, ...
    private GraphicsDevice gd;

    /**
     * Constructeur par defaut. Initialise la variable du GraphicsDevice.
     *  
     */
    public ScreenManager() {
        // On recupere l'environnement graphique du systeme d'exploitation,
        // celui-ci va nous permettre de recuperer un certain nombre d'elements
        // sur la configuration graphique du systeme ...
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // On recupere le device par defaut ... vous pouvez recuperer l'ensemble
        // des devices du systeme avec la methode getScreenDevices(), le centre
        // de l'ecran avec getCenterPoint(), ...
        gd = ge.getDefaultScreenDevice();
    }

    /**
     * Passage de la fenetre en mode plein ecran. Changement de mode graphique (resolution et profondeur). Regarder la
     * classe DisplayMode pour plus d'informations sur les resolutions d'ecran.
     * 
     * @param window
     *            La fenetre a passer en plein-ecran.
     * @param displayMode
     *            Le mode graphique a appliquer.
     */
    public void setFullScreen(Frame window, DisplayMode displayMode) {
        if (window == null) {
            return;
        }
        // On ne veut pas des bords de la fenetre
        window.setUndecorated(true);
        // La fenetre n'est pas redimensionnable
        window.setResizable(false);
        // Passage en mode plein-ecran
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(window);
        }
        // Changement de resolution
        if (displayMode != null && gd.isDisplayChangeSupported()) {
            try {
                // Affectation du mode graphique au device graphique.
                gd.setDisplayMode(displayMode);
            } catch (Exception e) {
                // Do nothing
            }
        }
    }

    /**
     * Obtenir la fen�tre en plein-�cran.
     */
    public Window getFullScreen() {
        return gd.getFullScreenWindow();
    }

    /**
     * Obtenir le GraphicsDevice par defaut de la fenetre.
     */
    /*public GraphicsDevice getGraphicsDevice(){
     return gd;
     }*/
    /**
     * Arr�te le mode plein-�cran pour revenir en mode fenetre ...
     */
    public void restoreFullScreen() {
        // On recupere notre fenetre en mode plein-ecran
        Window window = getFullScreen();
        //
        if (window != null) {
            window.dispose();
        }
        // On arr�te le plein-�cran.
        gd.setFullScreenWindow(null);
    }
}
