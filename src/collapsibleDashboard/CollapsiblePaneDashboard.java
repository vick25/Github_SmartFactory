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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import resources.Constants;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;

/**
 *
 * @author Victor Kadiata
 */
public class CollapsiblePaneDashboard extends TimerTask {

    public boolean isVertical() {
        return _vertical;
    }

    public void setVertical(boolean _vertical) {
        this._vertical = _vertical;
    }

    public Map<String, CollapsiblePaneGadget> getMapMach() {
        return Collections.unmodifiableMap(mapMach);
    }

    public void setMapMach(Map<String, CollapsiblePaneGadget> mapMach) {
        this.mapMach = mapMach;
    }

    public List<AbstractGadget> getGadgetList() {
        return Collections.unmodifiableList(gadgetList);
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
        gadgetList = new ArrayList<>();
        byte i = 0;
        while (e.hasMoreElements()) {
            Machine machName = (Machine) e.nextElement();
            StringBuilder builder = new StringBuilder("<html><font color=darkblue size=4><strong>");
            gadgetList.add(createGadget(builder.append(machName.getMachineName()).append("</strong></font>").toString()));
            manager.addGadget(gadgetList.get(i));
            i++;
//            manager.showGadget(createGadget(builder.append(machName.getMachineName()).append("</strong></font>").toString()));
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

        final Dashboard dashBoard = _tabbedPane.createDashboard("<html><font size=3><strong>Machines DashBoard</strong> "
                + "(click show palette)</font>");
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
                            final short cumulativeConfigNo = (short) getMachineConfigNo(getKey())[0];
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
                            ConnectDB.catchSQLException(ex);
                        } catch (HeadlessException | ParseException ex) {
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
        ResultSet resultSet;
        int[] configNum = new int[2];
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_CONFIGNO)) {
            ps.setString(1, "Cumulative");
            ps.setString(2, removeHtmlTag(machineName));
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                configNum[0] = resultSet.getInt(1);//Cumalative configNo
            }
        }
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(Queries.GET_CONFIGNO)) {
            ps.setString(1, "Rate");
            ps.setString(2, removeHtmlTag(machineName));
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                configNum[1] = resultSet.getInt(1);//Rate configNo
            }
        }
        return configNum;
    }

    private String removeHtmlTag(String s) {
        return s.replaceAll("\\<.*?>", "");
    }

    /* Main method setGadgetComponenPane to create gadget corresponding chart by calling the static method
     createChart of the GadgetFactory class */
    public void setGadgetComponentPane(int[] configNo, String keyMachineName, CollapsiblePaneGadget gadget)
            throws SQLException, ParseException {
        JComponent gadgetComponentPane = gadget.getContentPane();
        gadgetComponentPane.removeAll();
        gadgetComponentPane.setPreferredSize(new Dimension(200, 300));
        gadgetComponentPane.setLayout(new BorderLayout());
        gadgetComponentPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        /* Create the chart for the selection of each machine */
        Component chart = GadgetFactory.createChart(configNo, keyMachineName, DashBoard.getDate());
        if (keyMachineName.startsWith("Machine")) {
            gadgetComponentPane.add(chart);
        } else {
//            gadget.getContentPane().setPreferredSize(new Dimension(200, 100 + (int) (Math.random() * 200)));
            gadgetComponentPane.add(chart);
            gadget.revalidate();
            gadget.repaint();
            mapMach.put(removeHtmlTag(keyMachineName), gadget);
        }
    }

    @Override
    public void run() {
        if (mapMach.size() > 0) {
            Set<String> keySet = mapMach.keySet();
            Iterator<String> keySetIterator = keySet.iterator();
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
//            }
//            for (String key : mapMach.keySet()) {// "for each key in the map's key set"
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
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                DashBoard.bslTime.setText(new StringBuilder().append("Scheduler of ").append(Constants.timetoquery).
                        append(" is running to refresh the chart(s) if any is shown...").toString());
            }
        }
    }

    private DashboardTabbedPane _tabbedPane;
    private boolean _vertical = false;
    private final ArrayList<Machine> _machines;
    private GadgetManager manager;
    private List<AbstractGadget> gadgetList;
    private Map<String, CollapsiblePaneGadget> mapMach = new HashMap<>();
}
