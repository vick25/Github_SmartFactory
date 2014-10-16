package testCode;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.CheckBoxListWithSelectable;
import com.jidesoft.swing.SearchableUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Demoed Component: {@link com.jidesoft.swing.CheckBoxListWithSelectable} <br> Required jar files: jide-common.jar <br>
 * Required L&F: any L&F
 */
public class CheckBoxListWithSelectableDemo extends JFrame {
    private static final long serialVersionUID = -2462584914427598103L;

    private CheckBoxListWithSelectable _list;

    public CheckBoxListWithSelectableDemo() {
        this.setSize(500, 400);
        this.getContentPane().add(getDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setLayout(new BorderLayout(4, 4));
        panel.add(new JLabel("List of countries: "), BorderLayout.BEFORE_FIRST_LINE);
        _list = createCheckBoxList();
        panel.add(new JScrollPane(_list));
        return panel;
    }

    private CheckBoxListWithSelectable createCheckBoxList() {
        String[] names = {CheckBoxList.ALL, "Vick", "Munya", "Rosa", "Rita", "Nelson"};
        CheckBoxListWithSelectable list = new CheckBoxListWithSelectable(names);
// uncomment the lines below to see a customize cell renderer.
//        list.setCellRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
//                return label;
//            }
//        });        
        SearchableUtils.installSearchable(list);
        list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
// you can use item listener to detect any change in the check box selection.
                System.out.println(e.getItem() + " " + e.getStateChange());
            }
        });
//        ((Selectable) list.getModel().getElementAt(2)).setEnabled(false);
//        ((Selectable) list.getModel().getElementAt(5)).setEnabled(false);
//        ((Selectable) list.getModel().getElementAt(9)).setEnabled(false);
//        list.setSelectedObjects(new Object[]{"Albania", "China", "United States"});
        return list;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                new CheckBoxListWithSelectableDemo().setVisible(true);
            }
        });

    }

    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        JButton selectAllButton = new JButton(new AbstractAction("Select All") {
            private static final long serialVersionUID = 8895551324466265191L;

            public void actionPerformed(ActionEvent e) {
                _list.selectAll();
            }
        });
        JButton selectNoneButton = new JButton(new AbstractAction("Select None") {
            private static final long serialVersionUID = 2758269583107061665L;

            public void actionPerformed(ActionEvent e) {
                _list.selectNone();
            }
        });

        final JCheckBox checkBoxEnabled = new JCheckBox("Enabled Checking");
        checkBoxEnabled.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -5539557087369293073L;

            public void actionPerformed(ActionEvent e) {
                _list.setCheckBoxEnabled(checkBoxEnabled.isSelected());
            }
        });
        checkBoxEnabled.setSelected(_list.isCheckBoxEnabled());

        final JCheckBox clickInCheckBoxOnly = new JCheckBox("Click only valid in CheckBox");
        clickInCheckBoxOnly.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 5234198740430142668L;

            public void actionPerformed(ActionEvent e) {
                _list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
            }
        });
        clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

        panel.add(selectAllButton);
        panel.add(selectNoneButton);
        panel.add(checkBoxEnabled);
        panel.add(clickInCheckBoxOnly);
        return panel;
    }
}
