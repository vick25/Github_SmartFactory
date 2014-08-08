package dashboard;

import com.jidesoft.dashboard.Dashboard;
import com.jidesoft.dashboard.DashboardPersistenceUtils;
import com.jidesoft.dashboard.DashboardTabbedPane;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.ButtonStyle;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import mainFrame.MainFrame;
import mainFrame.ShortCutPanel;
import org.xml.sax.SAXException;
import productionQuickView.Target;
import resources.Constants;
import resources.ReadPropertiesFile;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class DashBoardSettings extends javax.swing.JDialog {

    public DashBoardSettings(final JFrame parent, boolean modal, boolean vertical, DashboardTabbedPane tabbedPane) {
        super(parent, modal);
        initComponents();
        _parent = parent;
        _tabbedPane = tabbedPane;
        _vertical = vertical;
        setTitle("DashBoard Settings");
        setLayout(new BorderLayout());
        getContentPane().add(this.getOptionsPanel());
        setLocationRelativeTo(parent);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        setFocusable(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("<html><font color=red>*</font>Dashboard will reload after any changes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 255, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Component getOptionsPanel() {
        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JCheckBox columnResizable = new JCheckBox("Resize charts");
        columnResizable.setFocusable(false);
        columnResizable.setBackground(Color.WHITE);
        columnResizable.setSelected(_tabbedPane.getGadgetManager().isColumnResizable());
        columnResizable.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.getGadgetManager().setColumnResizable(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        buttonPanel.add(columnResizable);

        JCheckBox dragOutside = new JCheckBox("Allow Drag Outside");
        dragOutside.setFocusable(false);
        dragOutside.setBackground(Color.WHITE);
        dragOutside.setSelected(_tabbedPane.getGadgetManager().isAllowDragOutside());
        dragOutside.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.getGadgetManager().setAllowDragOutside(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
//        buttonPanel.add(dragOutside);

//        JCheckBox allowMultipleInstances = new JCheckBox("Allow Multiple Gadget Instances");
//        allowMultipleInstances.setSelected(_tabbedPane.getGadgetManager().isAllowMultipleGadgetInstances());
//        allowMultipleInstances.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                _tabbedPane.getGadgetManager().setAllowMultipleGadgetInstances(e.getStateChange() == ItemEvent.SELECTED);
//            }
//        });
//        buttonPanel.add(allowMultipleInstances);
        JPanel comboBoxPanel = new JPanel(new BorderLayout(6, 6));
        JComboBox flowLayout = new JComboBox(new Object[]{"Jide Box Layout", "Grid Layout", "Flow Layout"});
        flowLayout.setFocusable(false);
        flowLayout.setBackground(Color.WHITE);
        flowLayout.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if ("Jide Box Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(3);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(
                                new JideBoxLayout(_tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0),
                                        BoxLayout.Y_AXIS, Dashboard.V_GAP));
                    } else if ("Grid Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(1);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(
                                new GridLayout(0, 3));
                    } else if ("Flow Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(1);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(
                                new FlowLayout(FlowLayout.LEFT));
                    }
                }
            }
        });
        comboBoxPanel.setBackground(Color.WHITE);
        comboBoxPanel.add(flowLayout, BorderLayout.CENTER);
        comboBoxPanel.add(new JLabel("Select a Layout Manager: "), BorderLayout.BEFORE_LINE_BEGINS);
        buttonPanel.add(comboBoxPanel);

        JPanel palettePanel = new JPanel(new BorderLayout(6, 6));
        JComboBox paletteSideComboBox = new JComboBox(new Object[]{"SOUTH", "NORTH", "EAST", "WEST"});
        paletteSideComboBox.setFocusable(false);
        paletteSideComboBox.setBackground(Color.WHITE);
        paletteSideComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if ("SOUTH".equals(e.getItem())) {
                        _vertical = false;
                        _tabbedPane.setPaletteSide(SwingConstants.SOUTH);
                    } else if ("NORTH".equals(e.getItem())) {
                        _vertical = false;
                        _tabbedPane.setPaletteSide(SwingConstants.NORTH);
                    } else if ("EAST".equals(e.getItem())) {
                        _vertical = true;
                        _tabbedPane.setPaletteSide(SwingConstants.EAST);
                    } else if ("WEST".equals(e.getItem())) {
                        _vertical = true;
                        _tabbedPane.setPaletteSide(SwingConstants.WEST);
                    }
//                    if (_vertical) {
//                       _tabbedPane palette.setButtonLayout(new GridLayout(0, 1));
//                    } else {
//                        palette.setButtonLayout(new GridLayout(1, 0));
//                    }
                }
            }
        });

        palettePanel.setBackground(Color.WHITE);
        palettePanel.add(paletteSideComboBox, BorderLayout.CENTER);
        palettePanel.add(new JLabel("Select Palette Side: "), BorderLayout.BEFORE_LINE_BEGINS);
        buttonPanel.add(palettePanel);

        buttonPanel.addButton(new JButton(new AbstractAction("Save current layout...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    DashboardPersistenceUtils.save(tabbedPane, "C:\\dashboard.xml");
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Save the layout as an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setFileView(new ExampleFileView());
//                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Save");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        DashboardPersistenceUtils.save(_tabbedPane, chooser.getSelectedFile().getAbsolutePath() + ".xml");
                    }
                } catch (ParserConfigurationException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        }));

        buttonPanel.addButton(new JButton(new AbstractAction("Load saved layout...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    DashboardPersistenceUtils.load(tabbedPane, "c:\\dashboard.xml");
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Load an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setFileView(new ExampleFileView());
//                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        DashboardPersistenceUtils.load(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                } catch (SAXException | ParserConfigurationException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        }));

        JPanel spinnerSlidePanel = new JPanel(new BorderLayout(6, 6));
        final JSpinner spinner = new JSpinner(new javax.swing.SpinnerNumberModel(
                ConnectDB.pref.getInt(SettingKeyFactory.DefaultProperties.REFRESHTIME,
                        Integer.valueOf(Constants.timetoquery.replaceAll("\\D+", ""))), 1, 60, 1));
        spinner.setFocusable(false);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    int value = (int) spinner.getValue();
                    ConnectDB.pref.putInt(SettingKeyFactory.DefaultProperties.REFRESHTIME, value);
                    setSettings(value + "m");
                    ReadPropertiesFile.readConfig();
                    DashBoard.bslTime.setText("Scheduler of " + Constants.timetoquery + " is running "
                            + "to refresh the chart(s) if any is shown...");
                    timeUpdated = true;
                    MainFrame.dashBoardDate = DashBoard.dtSpinner.getDate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        spinnerSlidePanel.setBackground(Color.WHITE);
        spinnerSlidePanel.add(spinner, BorderLayout.CENTER);
        spinnerSlidePanel.add(new JLabel("<html><font color=red>*</font>Refresh every"), BorderLayout.BEFORE_LINE_BEGINS);
        spinnerSlidePanel.add(new JLabel("min"), BorderLayout.AFTER_LINE_ENDS);
        buttonPanel.add(spinnerSlidePanel);

        JPanel paletteTarget = new JPanel(new BorderLayout(6, 6));
        JideButton btnTarget = new JideButton("Show/Edit Target(s)");
        btnTarget.setFocusable(false);
        btnTarget.setForeground(Color.BLUE);
        btnTarget.setOpaque(true);
        btnTarget.setButtonStyle(ButtonStyle.TOOLBOX_STYLE);
        btnTarget.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Target(_parent, true).setVisible(true);
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        paletteTarget.setBackground(Color.WHITE);
        paletteTarget.add(btnTarget, BorderLayout.WEST);
        buttonPanel.add(paletteTarget);

        buttonPanel.add(new JPanel(new BorderLayout(6, 6)));
        buttonPanel.add(new JPanel(new BorderLayout(6, 6)));

        buttonPanel.addButton(new JButton(new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        }), ButtonPanel.AFFIRMATIVE_BUTTON);
//        buttonPanel.addButton(new JButton(new AbstractAction("Remove \"Clock\" Gadget") {
//            private static final long serialVersionUID = -8616408184059236279L;
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GadgetManager gadgetManager = _tabbedPane.getGadgetManager();
//                if (gadgetManager.getGadget("Clock") == null) {
//                    gadgetManager.addGadget(createGadget("Clock"), 2);
//                    ((JButton) e.getSource()).setText("Remove \"Clock\" Gadget");
//                }
//                else {
//                    gadgetManager.removeGadget("Clock");
//                    ((JButton) e.getSource()).setText("Add \"Clock\" Gadget");
//                }
//            }
//        }));
//        buttonPanel.addButton(new JButton(new AbstractAction("Show \"Calculator\" Gadget at the second column") {
//            private static final long serialVersionUID = -71583815415701766L;
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GadgetManager gadgetManager = _tabbedPane.getGadgetManager();
//                Gadget gadget = gadgetManager.getGadget("Calculator");
//                if (gadget != null) {
//                    gadgetManager.showGadget(gadget, gadgetManager.getDashboard(gadgetManager.getActiveDashboardKey()), 1, 0);
//                }
//            }
//        }));
        return buttonPanel;
    }

    private void setSettings(String timeToQuery) {
        Properties props = new Properties();
        OutputStream out = null;
        try {
            File f = new File(ConnectDB.WORKINGDIR + File.separator + "src\\resources\\smfProperties.properties");
            if (f.exists()) {
                props.load(new FileReader(f));
                //Change the values here
                props.setProperty("timetoquery", timeToQuery);
            } else {
                //Set default values
                props.setProperty("running", "open");
                props.setProperty("timetoquery", "5m");
                props.setProperty("delay", "2s");
                props.setProperty("ipAddress", "127.0.0.1");
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            props.store(out, "This is the property file for the smartfactory reporting tool"
                    + " application.\nrunning: Check if application is running;\n"
                    + "timetoquery: Repeat for every given time.\n"
                    + "delay: Start after 2 seconds for the first time.\n\nVictor Kadiata");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    System.out.println("IOException: Could not close myApp.properties output stream; "
                            + ex.getMessage());
                }
            }
        }
    }

    private void close() {
        if (timeUpdated) {
            try {
                File temp = File.createTempFile("dashBoard", ".xml");
                DashboardPersistenceUtils.save(_tabbedPane, temp.getAbsolutePath());
                this._parent.dispose();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
                ShortCutPanel.showDashBoard();
                timeUpdated = false;
                DashboardPersistenceUtils.load(DashBoard._colDashBoard._tabbedPane, temp.getAbsolutePath());
                DashBoard._colDashBoard._tabbedPane.revalidate();
                temp.deleteOnExit();
            } catch (ParserConfigurationException | IOException | SAXException ex) {
                Logger.getLogger(DashBoardSettings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    DashBoardSettings dialog = new DashBoardSettings(new javax.swing.JFrame(), true, false, null);
//                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                        @Override
//                        public void windowClosing(java.awt.event.WindowEvent e) {
//                            System.exit(0);
//                        }
//                    });
//                    dialog.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    private final DashboardTabbedPane _tabbedPane;
    private boolean _vertical;
    public static boolean timeUpdated = false;
    static String _lastDirectory = ".";
    private final JFrame _parent;
}
