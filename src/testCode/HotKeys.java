package testCode;

/**
 *
 * @author Victor Kadiata
 */
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ActionMapUIResource;

public class HotKeys extends JFrame {

    public static void main(String arg[]) {
        JLabel Name = new JLabel("Name");
        JTextField tf1 = new JTextField(20);
        Name.setLabelFor(tf1);
        Name.setDisplayedMnemonic('N');

        JLabel Regno = new JLabel("RegNO");
        JTextField tf2 = new JTextField(20);
        Regno.setLabelFor(tf2);
        Regno.setDisplayedMnemonic('R');

        JLabel Mark1 = new JLabel("Mark1");
        JTextField tf3 = new JTextField(20);
        Mark1.setLabelFor(tf3);
        Mark1.setDisplayedMnemonic('1');

        JLabel Mark2 = new JLabel("Mark2");
        JTextField tf4 = new JTextField(20);
        Mark2.setLabelFor(tf4);
        Mark2.setDisplayedMnemonic('2');

        JButton b1 = new JButton("Save");
        JButton b2 = new JButton("eXit");

        JFrame f = new JFrame();
        JPanel p = new JPanel();

        p.add(Name);
        p.add(tf1);
        p.add(Regno);
        p.add(tf2);
        p.add(Mark1);
        p.add(tf3);
        p.add(Mark2);
        p.add(tf4);
        p.add(b1);
        p.add(b2);

        // *****************************************************
        ActionMap actionMap = new ActionMapUIResource();
        actionMap.put("action_save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save action performed.");
            }
        });
        actionMap.put("action_exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit action performed.");
            }
        });

        InputMap keyMap = new ComponentInputMap(p);
        keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK), "action_save");
        keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
                java.awt.Event.CTRL_MASK), "action_exit");
        SwingUtilities.replaceUIActionMap(p, actionMap);
        SwingUtilities.replaceUIInputMap(p, JComponent.WHEN_IN_FOCUSED_WINDOW,
                keyMap);
        // *****************************************************

        f.add(p);
        f.setVisible(true);
        f.pack();
        f.setDefaultCloseOperation(3);
    }
}
