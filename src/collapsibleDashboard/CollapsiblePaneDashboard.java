package collapsibleDashboard;

import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.dashboard.AbstractGadget;
import com.jidesoft.dashboard.Dashboard;
import com.jidesoft.dashboard.DashboardTabbedPane;
import com.jidesoft.dashboard.Gadget;
import com.jidesoft.dashboard.GadgetComponent;
import com.jidesoft.dashboard.GadgetEvent;
import com.jidesoft.dashboard.GadgetListener;
import com.jidesoft.dashboard.GadgetManager;
import com.jidesoft.dashboard.GadgetPalette;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.pane.CollapsiblePaneTitleButton;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.SimpleScrollPane;
import dashboard.DashBoard;
import dashboard.GadgetFactory;
import dashboard.GadgetSettings;
import dashboard.Machine;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import resources.Constants;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class CollapsiblePaneDashboard extends TimerTask {

    public Map<String, CollapsiblePaneGadget> getMapMach() {
        return Collections.unmodifiableMap(mapMach);
    }

    public void setMapMach(Map<String, CollapsiblePaneGadget> mapMach) {
        this.mapMach = mapMach;
    }

    public DashboardTabbedPane getTabbedPane() {
        return _tabbedPane;
    }

    public CollapsiblePaneDashboard(ArrayList<Machine> machines) {
        this._machines = machines;
    }

    public Component getDemoPanel() {
        manager = new GadgetManager() {
            @Override
            protected boolean validateGadgetDragging(Gadget gadget, Container targetContainer) {
                return super.validateGadgetDragging(gadget, targetContainer);
            }
        };
        mapMach.clear();//clear the machine HashMap
        /*Get the machine and call the createGadget method the create 
         it by adding to the dashboard palette*/
        Enumeration e = Collections.enumeration(_machines);
        while (e.hasMoreElements()) {
            Machine machName = (Machine) e.nextElement();
            manager.addGadget(createGadget("<html><font color=darkblue size=4><strong>"
                    + machName.getMachineName() + "</strong></font>"));
        }
        manager.setColumnResizable(true);

        _tabbedPane = new DashboardTabbedPane(manager) {
            @Override
            protected Container createToolBarComponent() {
                return super.createToolBarComponent();
            }

            @Override
            protected GadgetPalette createGadgetPalette() {
                GadgetPalette palette = new GadgetPalette(getGadgetManager(), this) {
                    @Override
                    protected String getResourceString(String key) {
                        if (_vertical && "GadgetPalette.hint".equals(key)) {
                            return "...";
                        }
                        return super.getResourceString(key);
                    }

                    @Override
                    protected Container createGadgetButtonContainer() {
                        Container container = super.createGadgetButtonContainer();
                        ((SimpleScrollPane) container).setHorizontalUnitIncrement(50); // please specify a number you think appropriate
                        return container;
                    }

//                    @Override
//                    protected JPanel createDescriptionPanel(Component statusComponent) {
//                        return null;
//                    }
                };
                if (_vertical) {
                    palette.setButtonLayout(new GridLayout(0, 1));
                } else {
                    palette.setButtonLayout(new GridLayout(1, 0));
                }
//                palette.setButtonLayout(new GridLayout(2, 0)); // you could uncomment this to have two rows of gadget buttons
                return palette;
            }

//            @Override
//            public void showPalette(Component invoker) {
//                super.showPalette(invoker);
//                Container c = getValidParent(invoker);
//                GadgetPalette palette = null;
//                for (Component comp : c.getComponents()) {
//                    if (comp instanceof GadgetPalette) {
//                        palette = (GadgetPalette) comp;
//                        break;
//                    }
//                }
//                if (palette != null) {
//                    c.remove(palette);
////                    c.add(palette, BorderLayout.BEFORE_FIRST_LINE);
//                }
//            }
//            @Override
//            public Dashboard createDashboard(String key) {
//                return super.createDashboard("Machines DashBoard1"); //To change body of generated methods, choose Tools | Templates.
//            }
        };

        Container toolBarComponent = _tabbedPane.getToolBarComponent();
        for (Component component : toolBarComponent.getComponents()) {
            if (component instanceof JideButton) {
                JideButton jideButton = (JideButton) component;
                if (jideButton.getText() == null) {
                    jideButton.setVisible(false);
                }
            }
        }
        _tabbedPane.setShowCloseButton(false);
        _tabbedPane.setFocusable(false);
        _tabbedPane.setPreferredSize(new Dimension(1125, 700));
        _tabbedPane.setCloseAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _tabbedPane.getSelectedIndex();
                if (index >= 0 && index < _tabbedPane.getGadgetManager().getDashboardCount()
                        && _tabbedPane.getGadgetManager().getDashboardCount() > 1) {
                    Dashboard dashboard = _tabbedPane.getGadgetManager().getDashboard(index);
                    if (dashboard != null) {
                        _tabbedPane.getGadgetManager().removeDashboard(dashboard.getKey());
                    }
                }
            }
        });

        final Dashboard dashBoard = _tabbedPane.createDashboard("Machines DashBoard (click show palette)");
        dashBoard.setColumnCount(3);
        dashBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        _tabbedPane.setUseFloatingPalette(false);
        _tabbedPane.getGadgetManager().addDashboard(dashBoard);

        /*Gadget options when maximized or restored*/
        manager.addGadgetListener(new GadgetListener() {
            @Override
            public void eventHappened(GadgetEvent e) {
                if (e.getID() == GadgetEvent.GADGET_COMPONENT_MAXIMIZED) {
                    GadgetComponent gadgetComponent = e.getGadgetComponent();
                    while (gadgetComponent instanceof ResizableGadgetComponent) {
                        gadgetComponent = ((ResizableGadgetComponent) gadgetComponent).getActualGadgetComponent();
                    }
                    if (gadgetComponent instanceof CollapsiblePaneGadget) {
                        ((CollapsiblePaneGadget) gadgetComponent).setMaximized(true);
                    }
                } else if (e.getID() == GadgetEvent.GADGET_COMPONENT_RESTORED) {
                    GadgetComponent gadgetComponent = e.getGadgetComponent();
                    while (gadgetComponent instanceof ResizableGadgetComponent) {
                        gadgetComponent = ((ResizableGadgetComponent) gadgetComponent).getActualGadgetComponent();
                    }
                    if (gadgetComponent instanceof CollapsiblePaneGadget) {
                        ((CollapsiblePaneGadget) gadgetComponent).setMaximized(false);
                    }
                }
            }
        });

        /* My Options */
        _tabbedPane.getGadgetManager().setAllowMultipleGadgetInstances(false);
        _tabbedPane.setPaletteSide(SwingConstants.SOUTH);

        return _tabbedPane;
    }

    protected AbstractGadget createGadget(String keyMachineName) {
//        IconsFactory.getImageIcon(CollapsiblePaneDashboard.class, "/icons/" + key.toLowerCase() + "_32x32.png")
        AbstractGadget dashboardElement = new AbstractGadget(keyMachineName,
                IconsFactory.getImageIcon(CollapsiblePaneDashboard.class, "/icons/machine_24x24.png"),
                IconsFactory.getImageIcon(CollapsiblePaneDashboard.class, "/icons/machine_24x24.png")) {
                    @Override
                    public GadgetComponent createGadgetComponent() {
                        final CollapsiblePaneGadget gadget = new CollapsiblePaneGadget(this);
                        try {
                            final short cumulativeConfigNo = (short)getMachineConfigNo(getKey())[0];
                            if (cumulativeConfigNo > 0) {
                                /** setGadgetComponentPane method for the content pane of the gadget */
                                DashBoard.bslTime.setText(removeHtmlTag(getKey()) + " chart loaded.");
                                //Create gadget -- important
                                setGadgetComponentPane(getMachineConfigNo(getKey()), removeHtmlTag(getKey()), gadget);
                                /**/
                                CollapsiblePaneTitleButton toolSettingsButton = new CollapsiblePaneTitleButton(gadget,
                                        IconsFactory.getImageIcon(CollapsiblePaneDashboard.class, "/icons/gadget_tool.png"));
                                toolSettingsButton.addActionListener(new AbstractAction() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            GadgetSettings.showSettingsDialog(getMachineConfigNo(getKey()),
                                                    removeHtmlTag(getKey()), gadget);//Settings for each gadget
                                        } catch (SQLException ex) {
                                            ConnectDB.catchSQLException(ex);
                                        }
                                    }
                                });
                                gadget.addButton(toolSettingsButton, 1);
                                gadget.setMessage("Last update on " + ObjectConverterManager.toString(ConnectDB.CALENDAR));
                                final ResizableGadgetComponent actualGadgetComponent = new ResizableGadgetComponent(gadget);
                                gadget._maximizeButton.addActionListener(new AbstractAction() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        actualGadgetComponent.getGadget().getGadgetManager().maximizeGadgetComponent(actualGadgetComponent);
                                        gadget.setMaximized(true);
                                    }
                                });
                                gadget._restoreButton.addActionListener(new AbstractAction() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        actualGadgetComponent.getGadget().getGadgetManager().restoreGadgetComponent();
                                        gadget.setMaximized(false);
                                    }
                                });
                                return actualGadgetComponent;
                            } else {
                                JOptionPane.showMessageDialog(_tabbedPane, getKey() + " does not have a "
                                        + "configuration channel.", "Machine DashBoard", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (HeadlessException ex) {
                        }
                        return null;
                    }

                    @Override
                    public void disposeGadgetComponent(GadgetComponent component) {
                        mapMach.remove(getName());
//                        System.out.println(component.getGadget().getName());
                    }
                };
//        dashboardElement.setDescription("Description is " + key);
        return dashboardElement;
    }

    private int[] getMachineConfigNo(String machineName) throws SQLException {
        int[] configNum = new int[2];
        String query = "SELECT DISTINCT c.ConfigNo FROM configuration c, hardware h\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND c.AvMinMax = 'Cumulative'\n"
                + "AND h.Machine =?\n"
                + "AND c.Active = 1 ORDER BY h.HwNo ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ps.setString(1, removeHtmlTag(machineName));
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                configNum[0] = ConnectDB.res.getInt(1);//Cumalative configNo
            }
        }

        query = "SELECT DISTINCT c.ConfigNo FROM configuration c, hardware h\n"
                + "WHERE h.HwNo = c.HwNo\n"
                + "AND c.AvMinMax = 'Rate'\n"
                + "AND h.Machine =?\n"
                + "AND c.Active = 1 ORDER BY h.HwNo ASC";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(query)) {
            ps.setString(1, removeHtmlTag(machineName));
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                configNum[1] = ConnectDB.res.getInt(1);//Rate configNo
            }
        }
        return configNum;
    }

    private String removeHtmlTag(String s) {
        return s.replaceAll("\\<.*?>", "");
    }

    public void setGadgetComponentPane(int[] configNo, String keyMachineName, CollapsiblePaneGadget gadget) throws SQLException {
        gadget.getContentPane().removeAll();
        gadget.getContentPane().setPreferredSize(new Dimension(200, 300));
        gadget.getContentPane().setLayout(new BorderLayout());
        gadget.getContentPane().setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        /* Create the chart for the selection of each machine */
        Component chart = GadgetFactory.createChart(configNo, keyMachineName, DashBoard.getDate());
        if (keyMachineName.startsWith("Machine")) {
            gadget.getContentPane().add(chart);
        } else {
//            gadget.getContentPane().setPreferredSize(new Dimension(200, 100 + (int) (Math.random() * 200)));
            gadget.getContentPane().add(chart);
            gadget.revalidate();
            gadget.repaint();
            mapMach.put(removeHtmlTag(keyMachineName), gadget);
        }
    }

    @Override
    public void run() {
        if (mapMach.size() > 0) {
            for (String key : mapMach.keySet()) {// "for each key in the map's key set"
                try {
                    int[] configNo = getMachineConfigNo(key);
                    CollapsiblePaneGadget value = mapMach.get(key);
                    DashBoard.bslTime.setText("Updating " + key + " in a while.");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    setGadgetComponentPane(configNo, key, value);//set the gadget component pane with a chart
                    _tabbedPane.revalidate();
                    _tabbedPane.repaint();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            DashBoard.bslTime.setText("Scheduler of " + Constants.timetoquery + " is running to refresh the "
                    + "chart(s) if any is shown...");
        }
    }

    private DashboardTabbedPane _tabbedPane;
    public boolean _vertical = false;
//    private Date _startDate;
    private final ArrayList<Machine> _machines;
    private GadgetManager manager;
    private Map<String, CollapsiblePaneGadget> mapMach = new HashMap<>();
}
