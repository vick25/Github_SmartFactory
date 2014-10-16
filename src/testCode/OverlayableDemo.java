package testCode;

/**
 *
 * @author Victor Kadiata
 */
/*
 * @(#)OverlayableDemo.java 3/2/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.InfiniteProgressPanel;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.Overlayable;
import com.jidesoft.swing.OverlayableUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Demoed Component: {@link com.jidesoft.swing.Overlayable} <br> Required jar files: jide-common.jar
 */
public class OverlayableDemo extends JFrame {

//    private JPanel _contentPanel;
    private Thread _thread1;
//    private Thread _thread2;

    public OverlayableDemo() {
        this.setSize(700, 500);
        this.getContentPane().add(getDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

////    protected JPanel createOptionPanel() {
////        JPanel panel = new JPanel();
////        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
////        panel.add(new JLabel("Overlay Location: "));
////        panel.add(Box.createVerticalStrut(4));
////        panel.add(JideSwingUtilities.createLeftPanel(createLocationPanel()));
////        panel.add(Box.createVerticalStrut(6));
////        panel.add(new JLabel("Extended Margin"));
////        panel.add(Box.createVerticalStrut(4));
////        InsetsComboBox insetsComboBox = new InsetsComboBox();
////        insetsComboBox.setSelectedInsets(new Insets(0, 0, 0, 0));
////        insetsComboBox.addItemListener(new ItemListener() {
////            @Override
////            public void itemStateChanged(ItemEvent e) {
////                if (e.getStateChange() == ItemEvent.SELECTED) {
////                    Insets insets = (Insets) e.getItem();
////                    setExtendedMargin(insets);
////                    if (_contentPanel != null) {
////                        _contentPanel.validate();
////                    }
////                }
////            }
////        });
////        insetsComboBox.setSelectedInsets(new Insets(0, 5, 0, 5));
////        panel.add(insetsComboBox);
////        return panel;
////    }
//    private void setExtendedMargin(Insets insets) {
//        for (Overlayable overlayable : overlayables) {
//            overlayable.setOverlayLocationInsets(insets);
//        }
//    }
//    public void setLocation(int location) {
//        for (Overlayable overlayable : overlayables) {
//            overlayable.setOverlayLocation(overlayable.getOverlayComponents()[0], location);
//        }
//    }
//    private final List<Overlayable> overlayables = new ArrayList();
    private Component getDemoPanel() {
        // add to the parent panel
        JPanel panel = new JPanel(new GridLayout(1, 1, 10, 10));
//        JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
//        panel.add(pane1);
        panel.add(pane2);

//        pane1.add(createTextArea());
//        pane1.add(createTable());
        pane2.add(createLoadingTextArea1());
//        pane2.add(createLoadingTextArea2());

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.add(panel);

//        JideSplitPane miscPanel = new JideSplitPane();
//        miscPanel.add(createMiscPanel());
//        miscPanel.add(createFormPanel());
//        contentPanel.add(createMiscPanel(), BorderLayout.AFTER_LAST_LINE);
//        _contentPanel = contentPanel;
        return contentPanel;
    }

    private static JPanel createTitledPanel(String title, Component component) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(component, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    protected JPanel createLoadingTextArea1() {
        final JPanel panelText = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                g.drawString("Java", 10, 40);
            }
        };
//        final JTextArea textArea = new OverlayTextArea();
//        textArea.setColumns(50);
//        textArea.setRows(10);
//        textArea.setWrapStyleWord(false);
//        textArea.setLineWrap(true);

        final DefaultOverlayable overlayTextArea = new DefaultOverlayable(panelText);

        final InfiniteProgressPanel progressPanel = new InfiniteProgressPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(20, 20);
            }
        };
        overlayTextArea.addOverlayComponent(progressPanel);
        progressPanel.stop();
        overlayTextArea.setOverlayVisible(false);

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        buttonPanel.addButton(new JButton(new AbstractAction("Start") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_thread1 == null || !_thread1.isAlive()) {
                    _thread1 = createThread(progressPanel, panelText);
                    _thread1.start();
                    progressPanel.start();
                }
            }
        }));
        buttonPanel.addButton(new JButton(new AbstractAction("Stop") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_thread1 != null) {
                    _thread1.interrupt();
                    _thread1 = null;
                    progressPanel.stop();
                }
            }
        }));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(overlayTextArea);
        panel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
        return createTitledPanel("Overlayable JTextArea (Indeterminate Loading Demo):", panel);
    }

//    @SuppressWarnings({"UnusedDeclaration"})
    private Thread createThread(final InfiniteProgressPanel progressPanel, final JComponent textArea) {
        return new Thread() {
            @Override
            public void run() {
                Overlayable overlayable = OverlayableUtils.getOverlayable(textArea);
                if (overlayable != null) {
                    overlayable.setOverlayVisible(true);
                }
                while (true) {
//                    textArea.append("A");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (overlayable != null) {
                    overlayable.setOverlayVisible(false);
                }
            }
        };
    }

//    protected JComponent createLocationPanel() {
//        ButtonGroup group = new ButtonGroup();
//        ImageIcon icon = OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.INFO);
//        JideToggleButton center = new JideToggleButton(icon);
//        center.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.CENTER);
//                }
//            }
//        });
//        JideToggleButton northEast = new JideToggleButton(icon);
//        northEast.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.NORTH_EAST);
//                }
//            }
//        });
//        JideToggleButton northWest = new JideToggleButton(icon);
//        northWest.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.NORTH_WEST);
//                }
//            }
//        });
//        JideToggleButton southEast = new JideToggleButton(icon);
//        southEast.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.SOUTH_EAST);
//                }
//            }
//        });
//        JideToggleButton southWest = new JideToggleButton(icon);
//        southWest.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.SOUTH_WEST);
//                }
//            }
//        });
//        JideToggleButton north = new JideToggleButton(icon);
//        north.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.NORTH);
//                }
//            }
//        });
//        JideToggleButton south = new JideToggleButton(icon);
//        south.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.SOUTH);
//                }
//            }
//        });
//        JideToggleButton west = new JideToggleButton(icon);
//        west.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.WEST);
//                }
//            }
//        });
//        JideToggleButton east = new JideToggleButton(icon);
//        east.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setLocation(SwingConstants.EAST);
//                }
//            }
//        });
//        group.add(center);
//        group.add(north);
//        group.add(east);
//        group.add(south);
//        group.add(west);
//        group.add(northWest);
//        group.add(northEast);
//        group.add(southWest);
//        group.add(southEast);
//        JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
//        panel.add(northWest);
//        panel.add(north);
//        panel.add(northEast);
//        panel.add(west);
//        panel.add(center);
//        panel.add(east);
//        panel.add(southWest);
//        panel.add(south);
//        panel.add(southEast);
//        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
//        return panel;
//    }
//    protected JPanel createTextArea() {
//        final JTextArea textArea = new OverlayTextArea();
//        textArea.setColumns(50);
//        textArea.setRows(10);
//
//        final DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));
//        textArea.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if (textArea.getDocument().getLength() > 0) {
//                    overlayTextArea.setOverlayVisible(false);
//                }
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                if (textArea.getDocument().getLength() == 0) {
//                    overlayTextArea.setOverlayVisible(true);
//                }
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//            }
//        });
//        textArea.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                overlayTextArea.setOverlayVisible(false);
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                overlayTextArea.setOverlayVisible(textArea.getDocument().getLength() == 0);
//            }
//        });
//        overlayTextArea.addOverlayComponent(StyledLabelBuilder.createStyledLabel("{Enter description here:f:gray}"));
//        return createTitledPanel("Overlayable JTextArea:", overlayTextArea);
//    }
//    protected JPanel createTable() {
//        final TableModel tableModel = new DefaultTableModel(DemoData.QUOTE_COLUMNS, 0);
//        final JTable table = new JTable(tableModel) {
//            @Override
//            public Dimension getPreferredScrollableViewportSize() {
//                return new Dimension(300, 100);
//            }
//        };
//        table.setColumnSelectionAllowed(false);
//        table.setRowSelectionAllowed(true);
//
//        final DefaultOverlayable overlayTable = new DefaultOverlayable(new JScrollPane(table));
//        table.getParent().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) {
//                    ((DefaultTableModel) table.getModel()).addRow(new String[]{"QQQQ", "QQQQ", "100.00", "10.0"});
//                }
//            }
//        });
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) {
//                    ((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
//                }
//            }
//        });
//        table.getModel().addTableModelListener(new TableModelListener() {
//            public void tableChanged(TableModelEvent e) {
//                overlayTable.setOverlayVisible(table.getModel().getRowCount() == 0);
//            }
//        });
//        overlayTable.addOverlayComponent(StyledLabelBuilder.createStyledLabel("{Double click to insert a row:f:gray}"));
//
//        return createTitledPanel("Overlayable JTable:", overlayTable);
//    }
//    protected JPanel createLoadingTextArea2() {
//        final JTextArea textArea = new OverlayTextArea();
//        textArea.setColumns(40);
//        textArea.setRows(10);
//        textArea.setWrapStyleWord(false);
//        textArea.setLineWrap(true);
//
//        final DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));
//
//        final JProgressBar progressBar = new JProgressBar(0, 100);
//        overlayTextArea.addOverlayComponent(progressBar);
//        overlayTextArea.setOverlayVisible(false);
//
//        ButtonPanel buttonPanel = new ButtonPanel();
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
//        buttonPanel.addButton(new JButton(new AbstractAction("Start") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (_thread2 == null || !_thread2.isAlive()) {
//                    _thread2 = createThread(progressBar, textArea);
//                    _thread2.start();
//                    progressBar.setValue(0);
//                }
//            }
//        }));
//        buttonPanel.addButton(new JButton(new AbstractAction("Stop") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (_thread2 != null) {
//                    _thread2.interrupt();
//                    _thread2 = null;
//                }
//            }
//        }));
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(overlayTextArea);
//        panel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
//        return createTitledPanel("Overlayable JTextArea (Determinate Loading Demo):", panel);
//    }
//    private Thread createThread(final JProgressBar bar, final JTextArea textArea) {
//        return new Thread() {
//            @Override
//            public void run() {
//                bar.setValue(0);
//                Overlayable overlayable = OverlayableUtils.getOverlayable(textArea);
//                if (overlayable != null) {
//                    overlayable.setOverlayVisible(true);
//                }
//                while (true) {
//                    textArea.append("A");
//                    int i = bar.getValue();
//                    if (i < bar.getMaximum()) {
//                        i++;
//                        bar.setValue(i);
//                    } else {
//                        break;
//                    }
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        break;
//                    }
//                }
//                if (overlayable != null) {
//                    overlayable.setOverlayVisible(false);
//                }
//            }
//        };
//    }
//    protected JPanel createFormPanel() {
//        JPanel namePanel = new JPanel(new BorderLayout(4, 4));
//        JLabel nameLabel = new JLabel("User Name: ");
//        JTextField nameField = new OverlayTextField(10);
//        namePanel.add(nameLabel, BorderLayout.BEFORE_LINE_BEGINS);
//        namePanel.add(nameField);
//        nameLabel.setLabelFor(nameField);
//
//        JPanel passwordPanel = new JPanel(new BorderLayout(4, 4));
//        JLabel passwordLabel = new JLabel("Password: ");
//        JTextField passwordField = new OverlayTextField(10);
//        passwordPanel.add(passwordLabel, BorderLayout.BEFORE_LINE_BEGINS);
//        passwordPanel.add(passwordField);
//        passwordLabel.setLabelFor(passwordField);
//
//        nameLabel.setPreferredSize(new Dimension(Math.max(nameLabel.getPreferredSize().width, passwordLabel.getPreferredSize().width), nameLabel.getPreferredSize().height));
//        passwordLabel.setPreferredSize(new Dimension(Math.max(nameLabel.getPreferredSize().width, passwordLabel.getPreferredSize().width), passwordLabel.getPreferredSize().height));
//
//        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
//        panel.add(namePanel);
//        panel.add(passwordPanel);
//
//        JLabel correctIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.CORRECT));
//        correctIcon.setToolTipText("User name is correct");
//
//        JLabel errorIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.ERROR));
//        errorIcon.setToolTipText("Incorrect password");
//
//        DefaultOverlayable overlayable = new DefaultOverlayable(panel);
//        overlayable.addOverlayComponent(correctIcon, nameField, Overlayable.NORTH_EAST, -1);
//        overlayable.addOverlayComponent(errorIcon, passwordField, Overlayable.NORTH_EAST, -1);
//
//        return createTitledPanel("Overlayble over a Form: ", JideSwingUtilities.createTopPanel(overlayable));
//    }
//    protected JPanel createMiscPanel() {
//        JLabel correctIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.CORRECT));
//        correctIcon.setToolTipText("Correct ...");
//        correctIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        final JLabel infoIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.INFO));
//        infoIcon.setToolTipText("Click for help ...");
//        infoIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        infoIcon.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                JidePopup popup = new JidePopup();
//                JLabel label = new JLabel("<HTML>This overlay icon allows you to set any help information<BR>with another component so that can get help easily.</HTML>");
//                label.setOpaque(true);
//                label.setBackground(Color.WHITE);
//                popup.add(label);
//                popup.showPopup(new Insets(-5, 0, -5, 0), infoIcon);
//            }
//        });
//        JLabel questionIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.QUESTION));
//        questionIcon.setToolTipText("Question ...");
//        questionIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        JLabel attentionIcon = new JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.ATTENTION));
//        attentionIcon.setToolTipText("Need attention ...");
//        attentionIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//        DefaultOverlayable overlayableRadioButton = new DefaultOverlayable(new OverlayRadioButton("Radio Button"), infoIcon, DefaultOverlayable.SOUTH_EAST);
//        overlayables.add(overlayableRadioButton);
//
//        OverlayCheckBox component = new OverlayCheckBox("Check Box");
//        DefaultOverlayable overlayableCheckBox = new DefaultOverlayable(component, questionIcon, DefaultOverlayable.SOUTH_EAST);
//        overlayables.add(overlayableCheckBox);
//
//        DefaultOverlayable overlayableTextField = new DefaultOverlayable(new OverlayTextField("Text Field", 10), correctIcon, DefaultOverlayable.SOUTH_WEST);
//        overlayables.add(overlayableTextField);
//
//        OverlayComboBox comboBox = new OverlayComboBox(new String[]{"Item 1", "Item 2", "Item 3",});
//        comboBox.setPrototypeDisplayValue("AAAAAAAAAA");
//        DefaultOverlayable overlayableComboBox = new DefaultOverlayable(comboBox, attentionIcon, DefaultOverlayable.SOUTH_WEST);
//        overlayables.add(overlayableComboBox);
//
//        JPanel controlPanel = new JPanel(new FlowLayout(SwingConstants.LEADING, 20, 5));
//        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
//        controlPanel.add(overlayableRadioButton);
//        controlPanel.add(overlayableCheckBox);
//        controlPanel.add(overlayableTextField);
//        controlPanel.add(overlayableComboBox);
//
////        JPanel panel = new JPanel(new BorderLayout());
////        panel.add(createOptionPanel(), BorderLayout.BEFORE_LINE_BEGINS);
////        panel.add(JideSwingUtilities.createTopPanel(controlPanel), BorderLayout.CENTER);
////        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
//        return createTitledPanel("Customize the Overlayable: ", panel);
//    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                new OverlayableDemo().setVisible(true);
            }
        });
    }
}
