package smartfactoryV2;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.TestFrame;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Victor Kadiata
 */
public class TestApp2 {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
                    WebLookAndFeel.initializeManagers();

                    final JButton jButton = new JButton("Nimbus button");
                    final WebButton webButton = new WebButton("WebLaF button");
                    TestFrame.show(new GroupPanel(5, jButton, webButton), 5);
                } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException |
                        UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
