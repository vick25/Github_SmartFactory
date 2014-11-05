package dashboard;

import com.jidesoft.dashboard.Dashboard;
import com.jidesoft.dashboard.DashboardPersistenceUtils;
import com.jidesoft.dashboard.DashboardTabbedPane;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.ButtonStyle;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.MarqueePane;
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
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import mainFrame.MainMenuPanel;
import org.xml.sax.SAXException;
import resources.Constants;
import resources.ReadPropertiesFile;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import target.MachinesProductionTarget;

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
//        this.setResizable(false);
        this.setTitle("DashBoard Settings");
        this.setLayout(new BorderLayout());
        this.getContentPane().add(this.getOptionsPanel());
        this.setLocationRelativeTo(parent);
        this.setFocusable(true);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                close();//method when closing this dialog
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
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
        JComboBox flowLayout = new JComboBox(new Object[]{"Box Layout", "Grid Layout", "Flow Layout"});
        flowLayout.setFocusable(false);
        flowLayout.setBackground(Color.WHITE);
        flowLayout.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if ("Box Layout".equals(e.getItem())) {
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
                        DashBoard.getColDashBoard().setVertical(false);
                        _tabbedPane.setPaletteSide(SwingConstants.SOUTH);
                    } else if ("NORTH".equals(e.getItem())) {
                        DashBoard.getColDashBoard().setVertical(false);
                        _tabbedPane.setPaletteSide(SwingConstants.NORTH);
                    } else if ("EAST".equals(e.getItem())) {
                        DashBoard.getColDashBoard().setVertical(true);
                        _tabbedPane.setPaletteSide(SwingConstants.EAST);
                    } else if ("WEST".equals(e.getItem())) {
                        DashBoard.getColDashBoard().setVertical(true);
                        _tabbedPane.setPaletteSide(SwingConstants.WEST);
                    }
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
                    _lastDirectory = ConnectDB.pref.get(SettingKeyFactory.General.DASHBOARD_FOLDER, ConnectDB.DEFAULT_DIRECTORY);
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = chooser.showDialog(((JComponent) e.getSource()).getTopLevelAncestor(), "Save");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        ConnectDB.pref.put(SettingKeyFactory.General.DASHBOARD_FOLDER, chooser.getSelectedFile().getAbsolutePath());
                        _lastDirectory = ConnectDB.pref.get(SettingKeyFactory.General.DASHBOARD_FOLDER, ConnectDB.DEFAULT_DIRECTORY);
                        DashboardPersistenceUtils.save(_tabbedPane, _lastDirectory + ".xml");
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
                    _lastDirectory = ConnectDB.pref.get(SettingKeyFactory.General.DASHBOARD_FOLDER, ConnectDB.DEFAULT_DIRECTORY);
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = chooser.showDialog(((JComponent) e.getSource()).getTopLevelAncestor(), "Open");
                    if (result == JFileChooser.APPROVE_OPTION) {
//                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        DashboardPersistenceUtils.load(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                } catch (SAXException | ParserConfigurationException | IOException ex) {
                    ex.printStackTrace();
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
                    timeUpdated = true;
                    //Save the value in the propertie file
                    setSettingsInPropertieFile(value + "m");
                    //Read the file
                    ReadPropertiesFile.readConfig();
                    DashBoard.getBslTime().setText(new StringBuilder().append("Scheduler of ").
                            append(Constants.timetoquery).append(" is running to refresh the chart(s) if any "
                                    + "is shown...").toString());
                    MainFrame.setDashBoardDate(DashBoard.getDate());
                } catch (Exception ex) {
                    writeException(ex, null);
                }
            }
        });
        spinnerSlidePanel.setBackground(Color.WHITE);
        spinnerSlidePanel.add(spinner, BorderLayout.CENTER);
        spinnerSlidePanel.add(new JLabel("<html><font color=red>Refresh every</font>"), BorderLayout.BEFORE_LINE_BEGINS);
        spinnerSlidePanel.add(new JLabel("min(s)"), BorderLayout.AFTER_LINE_ENDS);
        buttonPanel.add(spinnerSlidePanel);

        JPanel panelMarquee = new JPanel(new GridLayout(0, 1, 5, 5));
        panelMarquee.setBackground(Color.WHITE);
        final JCheckBox freezeCheckBox = new JCheckBox("Freeze Auto Scrolling");
        freezeCheckBox.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.DefaultProperties.FREEZE_SCROLL, false));
        freezeCheckBox.setFocusable(false);
        freezeCheckBox.setBackground(Color.WHITE);
        freezeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    Set<String> keySet = DashBoard.getColDashBoard().getMapHorizonMarqueeLeft().keySet();
                    Iterator<String> keySetIterator = keySet.iterator();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        while (keySetIterator.hasNext()) {
                            String key = keySetIterator.next();
                            MarqueePane value = DashBoard.getColDashBoard().getMapHorizonMarqueeLeft().get(key);
                            value.stopAutoScrolling();
                        }
                    } else {
                        while (keySetIterator.hasNext()) {
                            String key = keySetIterator.next();
                            MarqueePane value = DashBoard.getColDashBoard().getMapHorizonMarqueeLeft().get(key);
                            value.startAutoScrolling();
                        }
                    }
                    ConnectDB.pref.putBoolean(SettingKeyFactory.DefaultProperties.FREEZE_SCROLL, 
                            freezeCheckBox.isSelected());
                } catch (Exception ex) {
                    ConnectDB.appendToFileException(ex);
                }
            }
        });
        panelMarquee.add(freezeCheckBox);
        buttonPanel.add(panelMarquee);

        final JPanel paletteTarget = new JPanel(new BorderLayout(6, 6));
        JideButton btnTarget = new JideButton("Show/Edit Target(s)");
        btnTarget.setFocusable(false);
        btnTarget.setFont(ConnectDB.TITLEFONT);
        btnTarget.setForeground(Color.BLUE);
        btnTarget.setOpaque(true);
        btnTarget.setButtonStyle(ButtonStyle.TOOLBOX_STYLE);
        final JLabel currentUnit = new JLabel("current unit: " + ConnectDB.pref.get(
                SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour"));
        btnTarget.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MachinesProductionTarget(_parent, true).setVisible(true);
                    currentUnit.setText("current unit: " + ConnectDB.pref.get(
                            SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour"));
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        paletteTarget.setBackground(Color.WHITE);
        paletteTarget.add(btnTarget, BorderLayout.WEST);
        paletteTarget.add(currentUnit);
        buttonPanel.add(paletteTarget);

        buttonPanel.add(new JPanel(new BorderLayout(6, 6)));
        buttonPanel.add(new JPanel(new BorderLayout(6, 6)));

        buttonPanel.addButton(new JButton(new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                close();//method call when closing this dialog
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

    private void setSettingsInPropertieFile(String timeToQuery) throws IOException {
        Properties props = new Properties();
        OutputStream out = null;
        File classpathFile = null;
        try {
            String pathSeparator = File.separator;
            classpathFile = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
                    append("classes").append(pathSeparator).append("resources").append(pathSeparator).
                    append("smfProperties.properties").toString());
//            System.out.println(classpathFile.getName());
//            URL resource = getClass().getClassLoader().getResource("resources/smfProperties.properties");
//            resource.
//            file = new File(resource.getFile());
//            InputStream is = this.getClass().getResourceAsStream("resources/smfProperties.properties");
//            file = new File(Thread.currentThread().getContextClassLoader().getResource("resources/smfProperties.properties").getFile());
//            file = new File(ConnectDB.WORKINGDIR + File.separator + "src\\resources\\smfProperties.properties");
//            classpathFile = new File(new File(ConnectDB.WORKINGDIR).getParentFile() + File.separator + "src\\resources\\smfProperties.properties");
//            System.out.println(file.getCanonicalPath());
            if (classpathFile.exists()) {
                props.load(new FileReader(classpathFile));
                //Change the values here
                props.setProperty("timetoquery", timeToQuery);
            } else {
                //Set default values
                props.setProperty("running", "open");
                props.setProperty("timetoquery", "1m");
                props.setProperty("delay", "2s");
                props.setProperty("ipAddress", "127.0.0.1");
                classpathFile.createNewFile();
            }
            out = new FileOutputStream(classpathFile);
            props.store(out, "This is the property file for the smartfactory reporting tool"
                    + " application.\nrunning: Check if application is running;\n"
                    + "timetoquery: Repeat for every given time.\n"
                    + "delay: Start after 2 seconds for the first time.\n\nVictor Kadiata");
        } catch (NullPointerException | IOException e) {
            timeUpdated = false;
            writeException(e, classpathFile);//If any exception arise, write it to a file
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
        new Thread() {

            @Override
            public void run() {
//                File temp = null;
                try {
                    if (timeUpdated) {
                        ReadPropertiesFile.readConfig();
//                        ArrayList<Machine> _machines = DashBoard.getColDashBoard().getMachines();
//                        mapMach = DashBoard.getColDashBoard().getMapMach();
//                        try {
//                            temp = File.createTempFile("dashBoard", ".xml");
//                            DashboardPersistenceUtils.save(_tabbedPane, temp.getAbsolutePath());
//                            DashBoard.setColDashBoard(new CollapsiblePaneDashboard(_machines));
                        //set the _tabbedPane variable
//                            DashBoard.getColDashBoard().getDemoPanel();
                        DashBoard.setDashBoardTimer(new DashBoardTimer(DashBoard.getColDashBoard(),
                                DashBoard.getColDashBoard().getMapMach(), false));
                        DashBoard.getTimer().cancel();//Cancel the previous timer
//                            _parent.dispose();
//                            try {
//                                Thread.sleep(200);
//                            } catch (InterruptedException ex) {
//                            }
//                            MainMenuPanel.showDashBoard();
                        DashBoard.setTimer(new Timer());
                        DashBoard.getTimer().scheduleAtFixedRate(DashBoard.getDashBoardTimer(),
                                ConnectDB.getTimePrecision(Constants.delay),
                                ConnectDB.getTimePrecision(Constants.timetoquery));
//                        DashBoard.getColDashBoard().setMapMach(mapMach);
//                            _parent.setVisible(true);
                        timeUpdated = false;
//                            DashboardPersistenceUtils.load(DashBoard.getColDashBoard().getTabbedPane(), temp.getAbsolutePath());
//                            DashBoard.getColDashBoard().getTabbedPane().revalidate();
//                            temp.deleteOnExit();
//                        } catch (ParserConfigurationException | IOException | SAXException ex) {
//                            Logger.getLogger(DashBoardSettings.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                    dispose();
                } catch (Exception e) {
                    timeUpdated = false;
//                    temp.deleteOnExit();
                    dispose();
                }
            }
        }.start();
    }

    private void writeException(Exception ex, File file) {
        FileOutputStream fos = null;
        PrintStream ps = null;
        try {
            fos = new FileOutputStream(new File("SmartFactoryException.txt"), true);
            ps = new PrintStream(fos);
            ps.print(ConnectDB.SDATE_FORMAT_HOUR.format(System.currentTimeMillis()));
            ex.printStackTrace(ps);
            ps.println();
            if (file != null) {
                ps.print("File path is: " + file.getCanonicalPath());
            }
        } catch (IOException ex1) {
            Logger.getLogger(DashBoardSettings.class.getName()).log(Level.SEVERE, null, ex1);
        } finally {
            ps.println();
            ps.println();
            try {
                fos.close();
            } catch (IOException ex1) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private final DashboardTabbedPane _tabbedPane;
    private static boolean timeUpdated = false;
    private static String _lastDirectory = ".";
    private final JFrame _parent;
}
