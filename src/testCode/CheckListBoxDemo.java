package testCode;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.SearchableUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.Position;

/**
 * Demoed Component: {@link com.jidesoft.swing.CheckBoxList} <br> Required jar files: jide-common.jar <br> Required L&F:
 * any L&F
 */
public class CheckListBoxDemo extends JFrame {

    private CheckBoxList _list;
    private static final long serialVersionUID = -5982509597978327419L;
    private DefaultListModel _model;

    public CheckListBoxDemo() {
        this.setSize(500, 400);
        this.getContentPane().add(getDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        System.out.println(Runtime.getRuntime().availableProcessors());
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

    private CheckBoxList createCheckBoxList() {
        _model = new DefaultListModel();
        String[] name = {CheckBoxList.ALL, "Vick", "Munya", "Rosa", "Rita", "Nelson"};
        for (String s : name) {
            _model.addElement(s);
        }
        final CheckBoxList list = new CheckBoxList(_model) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }

            @Override
            public boolean isCheckBoxEnabled(int index) {
                return !_model.getElementAt(index).equals("Afghanistan")
                        && !_model.getElementAt(index).equals("Albania")
                        && !_model.getElementAt(index).equals("Antarctica");
            }
        };
        list.setClickInCheckBoxOnly(false);
        list.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

// uncomment the lines below to see a customize cell renderer.
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
                return label;
            }
        });
        SearchableUtils.installSearchable(list);
        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (list.getCheckBoxListSelectedValues().length <= 0) {
                    System.out.println("clear table");
                } else {
                    System.out.println(manyCriteria(list.getCheckBoxListSelectedValues()));
                }
            }
        });
//        list.getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (!e.getValueIsAdjusting()) {
////                    int[] selected = list.getCheckBoxListSelectedIndices();
////                    for (int i = 0; i < selected.length; i++) {
////                        int select = selected[i];
////                        System.out.print(select + " ");
////                    }
////                    System.out.println("\n---");
//                }
//            }
//        });
//        list.setCheckBoxListSelectedIndices(new int[]{2, 3, 20});
        return list;
    }

    private String manyCriteria(Object[] list) {
        String values = "";
        for (int i = 0; i < list.length; i++) {
            values += "\'" + list[i].toString() + "\',";
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                new CheckListBoxDemo().setVisible(true);
            }
        });

    }

    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
        JButton selectAllButton = new JButton(new AbstractAction("Select All") {
            private static final long serialVersionUID = 6274336964872530476L;

            @Override
            public void actionPerformed(ActionEvent e) {
                _list.getCheckBoxListSelectionModel().addSelectionInterval(0, _list.getModel().getSize() - 1);
            }
        });
        JButton selectNoneButton = new JButton(new AbstractAction("Select None") {
            private static final long serialVersionUID = -4521675380480250420L;

            @Override
            public void actionPerformed(ActionEvent e) {
                _list.getCheckBoxListSelectionModel().clearSelection();
            }
        });

        final JCheckBox checkBoxEnabled = new JCheckBox("Enabled Checking");
        checkBoxEnabled.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -2419513753995612223L;

            @Override
            public void actionPerformed(ActionEvent e) {
                _list.setCheckBoxEnabled(checkBoxEnabled.isSelected());
            }
        });
        checkBoxEnabled.setSelected(_list.isCheckBoxEnabled());

        final JCheckBox clickInCheckBoxOnly = new JCheckBox("Check only valid inside CheckBox");
        clickInCheckBoxOnly.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 5234198740430142668L;

            @Override
            public void actionPerformed(ActionEvent e) {
                _list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
            }
        });
        clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

        final JCheckBox allowAll = new JCheckBox("Enable (All)");
        allowAll.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -380261992533230412L;

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = allowAll.isSelected();
                if (selected) {
                    _model.insertElementAt(CheckBoxList.ALL, 0);
                } else {
                    _model.remove(0);
                }
            }
        });
        allowAll.setSelected(false);

        final JButton removeSelected = new JButton("Remove Selected Row");
        removeSelected.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 3785843307574034034L;

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _list.getSelectedIndex();
                if (index != -1) {
                    ((DefaultListModel) _list.getModel()).remove(index);
                }
            }
        });

        panel.add(selectAllButton);
        panel.add(selectNoneButton);
        panel.add(removeSelected);
        panel.add(checkBoxEnabled);
        panel.add(allowAll);
        panel.add(clickInCheckBoxOnly);
        return panel;
    }
}
