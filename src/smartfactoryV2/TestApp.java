package smartfactoryV2;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.window.TestFrame;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.toolbar.WhiteSpace;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Victor Kadiata
 */
public class TestApp {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                WebLookAndFeel.install();
                WebLookAndFeel.initializeManagers();
                final JTextArea textArea = new JTextArea("Simple text area");
                final JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(300, 150));
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

                final JProgressBar progressBar = new JProgressBar();
                progressBar.setIndeterminate(true);

                final JButton ok = new JButton("Ok");
                final JButton cancel = new JButton("Cancel");

                TestFrame.show(new GroupPanel(GroupingType.fillFirst, 5, false, scrollPane, progressBar,
                        new GroupPanel(GroupingType.fillFirst, 5, new WhiteSpace(), ok, cancel)), 5);
            }
        });
    }
}
