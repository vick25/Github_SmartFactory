package testCode;

/**
 *
 * @author Victor Kadiata
 */
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class GlassPaneExample extends JFrame implements ActionListener {

    private JButton btnDisable;
    private JButton btnTestOne;
    private JButton btnTestTwo;
    private MyGlassPane glass;
    private boolean actionAllowed = true;

    public GlassPaneExample() {

        // init JFrame graphics
        setBounds(300, 300, 300, 110);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);

        // init buttons
        btnTestOne = new JButton("Button one");
        add(btnTestOne);
        btnTestTwo = new JButton("Button two");
        add(btnTestTwo);
        btnDisable = new JButton("Disable ActionListeners for 2 seconds");
        add(btnDisable);

        // create Glass pane
        glass = new MyGlassPane();
        setGlassPane(glass);

        // add listeners
        btnTestOne.addActionListener(this);
        btnTestTwo.addActionListener(this);
        btnDisable.addActionListener(this);
    }

    public static void main(String[] args) {
        new GlassPaneExample();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.equals(btnDisable)) {

            // setting glasspane visibility to 'true' allows it to receive mouse events
            glass.setVisible(true);
            setCursor(new Cursor(Cursor.WAIT_CURSOR));

            SwingWorker sw = new SwingWorker() {

                @Override
                protected Object doInBackground()
                        throws Exception {
                    Thread.sleep(2000);
                    return null;
                }

                @Override
                public void done() {
                    // set cursor and GlassPane back to default state
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    glass.setVisible(false);
                    // allow actions to be received again
                    actionAllowed = true;
                }
            };
            sw.execute();

        } else if (actionAllowed) {
            if (src.equals(btnTestOne)) {
                JOptionPane.showMessageDialog(this, "BUTTON ONE PRESSED");
            } else if (src.equals(btnTestTwo)) {
                JOptionPane.showMessageDialog(this, "BUTTON TWO PRESSED");
            }
        }
    }

    class MyGlassPane extends JPanel {

        MyGlassPane() {

            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    actionAllowed = false;
                }
            });
        }

        //Draw an cross to indicate glasspane visibility 
//        @Override
//        public void paintComponent(Graphics g) {
//            g.setColor(Color.red);
//            g.drawLine(0, 0, getWidth(), getHeight());
//            g.drawLine(getWidth(), 0, 0, getHeight());
//        }
    }
}
