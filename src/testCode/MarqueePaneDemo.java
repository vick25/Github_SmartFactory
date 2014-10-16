package testCode;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.MarqueePane;
import com.jidesoft.swing.MultilineLabel;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Demoed Component: {@link com.jidesoft.swing.MarqueePane} <br> Required jar files: jide-common.jar <br> Required L&F:
 * any L&F
 */
public class MarqueePaneDemo extends JFrame {

    private static final long serialVersionUID = 5611828470716987509L;
//    MarqueePane _horizonMarqueeLeft;
//    MarqueePane _verticalMarqueeUp;
//    MarqueePane _verticalMarqueeDown;

    public MarqueePaneDemo() {
        this.setSize(500, 200);
        this.getContentPane().add(getDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        _verticalMarqueeUp.startAutoScrolling();
    }

//    public Component getOptionsPanel() {
//        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
//        JCheckBox freezeCheckBox = new JCheckBox("Freeze Auto Scrolling");
//        freezeCheckBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    _horizonMarqueeLeft.stopAutoScrolling();
//                    _verticalMarqueeUp.stopAutoScrolling();
//                    _verticalMarqueeDown.stopAutoScrolling();
//                } else {
//                    _horizonMarqueeLeft.startAutoScrolling();
//                    _verticalMarqueeUp.startAutoScrolling();
//                    _verticalMarqueeDown.startAutoScrolling();
//                }
//            }
//        });
//        panel.add(freezeCheckBox);
//        return panel;
//    }
    private Component getDemoPanel() {
        StyledLabel styledLabel = new StyledLabel();
        customizeStyledLabel(styledLabel);

        MarqueePane horizonMarqueeLeft = new MarqueePane(styledLabel);
        horizonMarqueeLeft.setScrollDelay(60);
        horizonMarqueeLeft.setScrollAmount(5);
        horizonMarqueeLeft.setStayPosition(15);
        horizonMarqueeLeft.setPreferredSize(new Dimension(250, 40));
//        horizonMarqueeLeft.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Left", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
//                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        JPanel demoPanel = new JPanel(new BorderLayout(5, 5));
        demoPanel.add(horizonMarqueeLeft, BorderLayout.BEFORE_FIRST_LINE);

//        JTable table = new JideTable(DemoData.createQuoteTableModel(false));
        MultilineLabel textArea = new MultilineLabel();
        textArea.setText("Obama welcomes bill to regulate tobacco \n"
                + "Fake Rockefeller found guilty of kidnapping \n"
                + "Al Qaeda fighters relocating, officials say \n"
                + "Navarrette: Haters looking for scapegoats \n"
                + "Avlon: 'Wingnuts' spread hate of Obama, Jews \n"
                + "Ticker: Palin knocks 'perverted' Letterman \n"
                + "Spokesman: Chastity Bono changing gender \n"
                + "iReport.com: Share stories of gender change \n"
                + "Robin Meade: Packing for presidential skydive \n"
                + "WLUK: Girl gets excuse note from Obama \n"
                + "Woman gives up home, car to help kids \n"
                + "9-month-old snatched from home  \n"
                + "WPLG: Cat killings becoming more violent \n"
                + "Cargo containers become beautiful homes \n"
                + "Fortune: Dare you ask for a raise now? \n"
                + "Truck loses load of cash, causes car jam  \n"
                + "Flying fish smack boater in head   \n"
                + "Dog eats bag of pot, gets high");

        MarqueePane verticalMarqueeUp = new MarqueePane(textArea);
        verticalMarqueeUp.setScrollDirection(MarqueePane.SCROLL_DIRECTION_UP);
        verticalMarqueeUp.setPreferredSize(new Dimension((int) horizonMarqueeLeft.getPreferredSize().getWidth(), 38));
        verticalMarqueeUp.setScrollAmount(2);
        verticalMarqueeUp.setStayPosition(14);
        verticalMarqueeUp.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Up", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

//        MarqueePane verticalMarqueeDown = new MarqueePane(table);
//        verticalMarqueeDown.setScrollDirection(MarqueePane.SCROLL_DIRECTION_DOWN);
//        verticalMarqueeDown.setScrollDelay(200);
//        verticalMarqueeDown.setStayDelay(1000);
//        verticalMarqueeDown.setPreferredSize(new Dimension((int) horizonMarqueeLeft.getPreferredSize().getWidth(), 320));
//        verticalMarqueeDown.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Down", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
//                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        demoPanel.add(verticalMarqueeUp, BorderLayout.CENTER);
//        demoPanel.add(verticalMarqueeDown, BorderLayout.AFTER_LAST_LINE);
//        _horizonMarqueeLeft = horizonMarqueeLeft;
//        _verticalMarqueeUp = verticalMarqueeUp;
//        _verticalMarqueeDown = verticalMarqueeDown;
        return demoPanel;
    }

    private void customizeStyledLabel(StyledLabel styledLabel) {
        styledLabel.setText("GOOG   429.11   -6.51          DIA   87.64   -0.1          FXI   39.19   +1.12          "
                + "GLD   93.62   -0.21          USO   39   +0.81          MSFT   22.25   +0.17");
        styledLabel.setForeground(Color.WHITE);
        int[] steps = new int[]{16, 5, 24, 4, 24, 5, 24, 5, 21, 5, 25, 5};
        int index = 0;
        System.out.println(steps.length);
        for (int i = 0; i < steps.length; i++) {
            if (i % 2 == 0) {
//                System.out.println(steps[i]);
//                System.out.println(styledLabel.getText().substring(index, steps[i]));
                styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.WHITE, Color.BLACK, 0, Color.WHITE));
            } else {
                if (styledLabel.getText().charAt(index) == '-') {
                    styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.RED, Color.BLACK, 0, Color.WHITE));
                } else {
                    styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.GREEN, Color.BLACK, 0, Color.WHITE));
                }
            }
            index += steps[i];
        }
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                new MarqueePaneDemo().setVisible(true);
            }
        });
    }
}
