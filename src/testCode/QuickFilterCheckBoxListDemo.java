package testCode;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.list.FilterableCheckBoxList;
import com.jidesoft.list.QuickListFilterField;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.SearchableUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

/**
 * Demoed Component: {@link com.jidesoft.list.FilterableCheckBoxList} <br> Required jar files:
 * jide-common.jar&jide-grids.jar <br> Required L&F: any L&F
 */
public class QuickFilterCheckBoxListDemo extends JFrame {
    private FilterableCheckBoxList _list;
    private static final long serialVersionUID = -5982509597978327419L;

    public QuickFilterCheckBoxListDemo() {
        this.setSize(500, 400);
        this.getContentPane().add(getDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private Component getDemoPanel() {
        final DefaultListModel model = new DefaultListModel();
        String[] names = {CheckBoxList.ALL, "Vick", "Munya", "Rosa", "Rita", "Nelson"};
        for (int i = 0; i < names.length; i++) {
            String s = names[i];
            model.addElement(s);
        }

        JPanel quickSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        final QuickListFilterField field = new QuickListFilterField(model);
        field.setHintText("Type here to filter countries");
        quickSearchPanel.add(field);
        quickSearchPanel.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "QuickListFilterField", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP));

        _list = new FilterableCheckBoxList(field.getDisplayListModel()) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }

            @Override
            public boolean isCheckBoxEnabled(int index) {
                return !getModel().getElementAt(index).equals("Afghanistan")
                        && !getModel().getElementAt(index).equals("Albania")
                        && !getModel().getElementAt(index).equals("Antarctica");
            }
        };
        _list.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

// uncomment the lines below to see a customize cell renderer.
//        list.setCellRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
//                return label;
//            }
//        });
        SearchableUtils.installSearchable(_list);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setLayout(new BorderLayout(4, 4));
        panel.add(new JLabel("List of countries: "), BorderLayout.BEFORE_FIRST_LINE);
        panel.add(new JScrollPane(_list));

        final JList selectedList = new JList();
        JPanel selectedPanel = new JPanel(new BorderLayout(4, 4));
        selectedPanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Selected Countries", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 0, 0, 0)));
        selectedPanel.add(new JScrollPane(selectedList));
        _list.getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
//                    DefaultListModel selectedModel = new DefaultListModel();
//                    CheckBoxListSelectionModel originalSelectionModel = ((CheckBoxListSelectionModelWithWrapper) _list.getCheckBoxListSelectionModel()).getOriginalSelectionModel();
//                    ListModel actualListModel = ListModelWrapperUtils.getActualListModel(_list.getModel());
//                    for (int i = originalSelectionModel.getMinSelectionIndex(); i <= originalSelectionModel.getMaxSelectionIndex(); i++) {
//                        if (originalSelectionModel.isSelectedIndex(i)) {
//                            selectedModel.addElement(actualListModel.getElementAt(i));
//                        }
//                    }
//                    selectedList.setModel(selectedModel);
                }
            }
        });
        _list.setCheckBoxListSelectedIndices(new int[]{2, 3, 20});

        JPanel demoPanel = new JPanel(new BorderLayout(4, 4));
        demoPanel.add(quickSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        demoPanel.add(panel);
        demoPanel.add(selectedPanel, BorderLayout.AFTER_LAST_LINE);
        return demoPanel;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                new QuickFilterCheckBoxListDemo().setVisible(true);
            }
        });
    }

    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
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

        final JCheckBox clickInCheckBoxOnly = new JCheckBox("Click only valid in CheckBox");
        clickInCheckBoxOnly.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 5234198740430142668L;

            public void actionPerformed(ActionEvent e) {
                _list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
            }
        });
        clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

        final JButton removeSelected = new JButton("Remove Selected Row");
        removeSelected.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 3785843307574034034L;

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
        panel.add(clickInCheckBoxOnly);
        return panel;
    }
}
