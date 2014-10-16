package mainFrame;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.action.CommandMenuBar;
import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.PersistenceUtils;
import com.jidesoft.utils.Lm;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import login.Identification;
import org.w3c.dom.Document;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.SplashScreen;

public class MainFrameCommandBarFactory extends CommandBarFactory {

    public static void setParent(MainFrame parent) {
        MainFrameCommandBarFactory.parent = parent;
    }

    public static CommandBar createMenuCommandBar() {
        CommandBar commandBar = new CommandMenuBar("Menu Bar");
        commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
        commandBar.setInitIndex(0);
        commandBar.setInitSubindex(0);
        commandBar.setPaintBackground(false);
        commandBar.setStretch(true);
        commandBar.setFloatable(true);

        commandBar.add(createFileMenu());
        commandBar.add(createViewMenu());
//        commandBar.add(createEditMenu());
//        commandBar.add(createOptionMenu());
        commandBar.add(createToolsMenu());
        commandBar.add(createThemeMenu());
////        commandBar.add(CommandBarFactory.createLookAndFeelMenu(parent));
        commandBar.add(createWindowsMenu());
        commandBar.add(createHelpMenu());
        return commandBar;
    }

    private static JideMenu createThemeMenu() {
        JideMenu ThemeMenu = new JideMenu("Themes");
        JMenuItem MIOffice2003 = new JMenuItem("Office 2003 Style");
        JMenuItem MIOffice2007 = new JMenuItem("Office 2007 Style");
        JMenuItem MIEclipse = new JMenuItem("Eclipse 3x Style");
        JMenuItem MIXerto = new JMenuItem("Xerto Style");
        JMenuItem MIVsnet = new JMenuItem("Vsnet Style");
        JMenuItem MIDefault = new JMenuItem("Default Theme");
        MIOffice2003.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.OFFICE2003_STYLE);
            }
        });
        MIOffice2007.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.OFFICE2007_STYLE);
            }
        });
        MIEclipse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.EXTENSION_STYLE_ECLIPSE3X);
            }
        });
        MIXerto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.XERTO_STYLE);
            }
        });
        MIVsnet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.VSNET_STYLE);
            }
        });
        MIDefault.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionTheme(LookAndFeelFactory.XERTO_STYLE);
            }
        });
        ThemeMenu.add(MIVsnet);
        ThemeMenu.add(new Separator());
        ThemeMenu.add(MIOffice2003);
        ThemeMenu.add(MIOffice2007);
        ThemeMenu.add(new Separator());
        ThemeMenu.add(MIEclipse);
        ThemeMenu.add(MIXerto);
        ThemeMenu.add(new Separator());
        ThemeMenu.add(MIDefault);
        return ThemeMenu;
    }

    private static JideMenu createFileMenu() {
        JideMenu fileMenu = new JideMenu("File");
        JMenuItem MINewReport = new JMenuItem();
        JMenuItem MIOpenFolder = new JMenuItem();
        Separator jSeparator1 = new JPopupMenu.Separator();
//        MISave = new JMenuItem();
//        MISaveAs = new JMenuItem();
//        Separator jSeparator2 = new JPopupMenu.Separator();
        JMenuItem MILogout = new JMenuItem();
        Separator jSeparator3 = new JPopupMenu.Separator();
        JMenuItem MIExit = new JMenuItem();

        MINewReport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        MINewReport.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.NEW)); // NOI18N
        MINewReport.setText("New Production Report");
        MINewReport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.actionNewMainInterface();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        fileMenu.add(MINewReport);
        MIOpenFolder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        MIOpenFolder.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.OPEN)); // NOI18N
        MIOpenFolder.setText("Open Working Directory");
        MIOpenFolder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionOpenWorkingFolder();
            }
        });
        fileMenu.add(MIOpenFolder);
        fileMenu.add(jSeparator1);
//        MISave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        MISave.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.SAVE)); // NOI18N
//        MISave.setText("Save");
//        MISave.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                MainFrame.actionSaveProperties();
//            }
//        });
////        MISave.setEnabled(false);
//        fileMenu.add(MISave);
//        MISaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
//        MISaveAs.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.SAVEALL)); // NOI18N
//        MISaveAs.setText("Save As...");
//        MISaveAs.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                MainFrame.actionSaveAsProperties();
//            }
//        });
////        MISaveAs.setEnabled(false);
//        fileMenu.add(MISaveAs);
//        fileMenu.add(jSeparator2);
        MILogout.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.LOGOUT)); // NOI18N
        MILogout.setText("Log out");
        MILogout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionLogOut();
                Identification.setFrameSaved(true);
                SplashScreen.getIdentification().setVisible(true);
            }
        });
        fileMenu.add(MILogout);
        fileMenu.add(jSeparator3);
        MIExit.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.EXIT)); // NOI18N
        MIExit.setText("Exit");
        MIExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionExit();
            }
        });
        fileMenu.add(MIExit);
        return fileMenu;
    }

    private static JideMenu createToolsMenu() {
        JideMenu menu = new JideMenu("Tools");
        JMenuItem MICreateUser = new JMenuItem();
        JMenuItem MIChangePassword = new JMenuItem();
        JMenuItem MISettings = new JMenuItem();
        Separator separator = new Separator();
        MICreateUser.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.USER)); // NOI18N
        MICreateUser.setText("Create User...");
        MICreateUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionCreateUser();
            }
        });
        menu.add(MICreateUser);
        MIChangePassword.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.CHANGEPASSWORD)); // NOI18N
        MIChangePassword.setText("Change Password...");
        MIChangePassword.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionChangePassword();
            }
        });
        MIChangePassword.setEnabled(false);
        menu.add(MIChangePassword);
        menu.add(separator);
        MISettings.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.SETTING)); // NOI18N
        MISettings.setText("Settings...");
        KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
        MISettings.setAccelerator(ctrlXKeyStroke);
        MISettings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionSetting();
            }
        });
        menu.add(MISettings);
        return menu;
    }

    private static JideMenu createHelpMenu() {
        JideMenu HelpMenu = new JideMenu();
        JMenuItem MIHelp = new JMenuItem();
        JMenuItem MIAbout = new JMenuItem();
        JMenuItem MICheck = new JMenuItem();
        Separator separator = new Separator();
        HelpMenu.setText("Help");
        MIHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        MIHelp.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.HELP)); // NOI18N
        MIHelp.setText("Help");
        MIHelp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionHelp();
            }
        });
        HelpMenu.add(MIHelp);
        MIAbout.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.ABOUT)); // NOI18N
        MIAbout.setText("About...");
        MIAbout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionAbout();
            }
        });
        HelpMenu.add(MIAbout);
        HelpMenu.add(separator);
        MICheck.setIcon(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.CHECKUPDATE)); // NOI18N
        MICheck.setText("Check for Updates");
        MICheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionCheckUpdate();
            }
        });
        MICheck.setEnabled(false);
        HelpMenu.add(MICheck);
        return HelpMenu;
    }

    private static JideMenu createWindowsMenu() {
        JideMenu menu = new JideMenu("Window");
        menu.setMnemonic('W');
        JMenuItem item;

        item = new JMenuItem("Load Default Layout");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    parent.getLayoutPersistence().loadLayoutData();
                }
            }
        });
        menu.add(item);

        item = new JMenuItem("Load Design Layout");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    parent.getLayoutPersistence().loadLayoutDataFrom("design");
                }
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Save as Default Layout");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    parent.getLayoutPersistence().saveLayoutData();
                }
            }
        });
        menu.add(item);

        item = new JMenuItem("Save as Design Layout");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    parent.getLayoutPersistence().saveLayoutDataAs("design");
                }
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Reset Layout");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    parent.getLayoutPersistence().resetToDefault();
                }
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Save XML Layout to...");
        item.addActionListener(new AbstractAction() {

            private static final long serialVersionUID = 4119879332837884473L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder && parent.getLayoutPersistence() != null) {
                    try {
                        JFileChooser chooser = new JFileChooser() {

                            @Override
                            protected JDialog createDialog(Component parent) throws HeadlessException {
                                JDialog dialog = super.createDialog(parent);
                                dialog.setTitle("Save the layout as an \".xml\" file");
                                return dialog;
                            }
                        };
                        chooser.setCurrentDirectory(new File(_lastDirectory));
                        int result = chooser.showDialog(((JComponent) e.getSource()).getTopLevelAncestor(), "Save");
                        if (result == JFileChooser.APPROVE_OPTION) {
                            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.newDocument();
                            parent.getLayoutPersistence().saveLayoutTo(document);
                            PersistenceUtils.saveXMLDocumentToFile(document, chooser.getSelectedFile().getAbsolutePath(),
                                    PersistenceUtils.getDefaultXmlEncoding());
                        }
                    } catch (IOException | HeadlessException | ParserConfigurationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menu.add(item);

        item = new JMenuItem("Load XML Layout from...");
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent instanceof DefaultDockableBarDockableHolder) {
                    JFileChooser chooser = new JFileChooser() {

                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Load an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    int result = chooser.showDialog(((JComponent) e.getSource()).getTopLevelAncestor(), "Open");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        parent.getLayoutPersistence().loadLayoutDataFromFile(chooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });
        menu.add(item);
        menu.addSeparator();

        item = new JMenuItem("Toggle Auto Hide All");
        item.setMnemonic('T');
        item.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_autohideAll) {
                    _fullScreenLayout = parent.getDockingManager().getLayoutRawData();
                    parent.getDockingManager().autohideAll();
                    _autohideAll = true;
                } else {
                    if (_fullScreenLayout != null) {
                        parent.getDockingManager().setLayoutRawData(_fullScreenLayout);
                    }
                    _autohideAll = false;
                }
            }
        });
        menu.add(item);
        return menu;
    }

    private static JideMenu createViewMenu() {
        JideMenu menu = new JideMenu("View");
        menu.setMnemonic('P');

        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Frames Floatable");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setFloatable(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isFloatable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Autohidable");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setAutohidable(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isAutohidable());

        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Hidable");
        checkBoxMenuItem.setMnemonic('H');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setHidable(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Rearrangable");
        checkBoxMenuItem.setMnemonic('R');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setRearrangable(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Resizable");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setResizable(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isResizable());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JMenu buttonsMenu = new JideMenu("Available Titlebar Buttons");

        checkBoxMenuItem = new JCheckBoxMenuItem("Close Button");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = ((AbstractButton) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_CLOSE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Autohide Button");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = ((AbstractButton) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_AUTOHIDE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Float Button");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = ((AbstractButton) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_FLOATING);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Maximize Button");
        checkBoxMenuItem.setMnemonic('M');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = ((AbstractButton) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_MAXIMIZE);
            }
        });
        checkBoxMenuItem.setSelected(false);
        buttonsMenu.add(checkBoxMenuItem);

        menu.add(buttonsMenu);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Continuous Layout");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setContinuousLayout(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isContinuousLayout()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Continuous Layout</FONT></B><FONT FACE='Tahoma'>"
                                + "<FONT FACE='Tahoma' SIZE='3'><BR><BR><B>An option to continuously layout affected components during resizing."
                                + "<BR></B><BR>This is the same option as in JSplitPane. If the option is true, when you resize"
                                + "<BR>the JSplitPane's divider, it will continuously redisplay and laid out during user"
                                + "<BR>intervention."
                                + "<BR><BR>Default: off</FONT>"
                                + "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isContinuousLayout());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Easy Tab Docking");
        checkBoxMenuItem.setMnemonic('E');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setEasyTabDock(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isEasyTabDock()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<B><FONT COLOR='BLUE' FACE='Tahoma' SIZE='4'>Easy Tab Docking </FONT></B>"
                                + "<BR><BR><FONT FACE='Tahoma' SIZE='3'><B>An option to make the tab-docking of a dockable frame easier</B>"
                                + "<BR><BR>It used to be dragging a dockable frame and pointing to the title"
                                + "<BR>bar of another dockable frame to tab-dock with it. However if "
                                + "<BR>this option on, pointing to the middle portion of any dockable "
                                + "<BR>frame will tab-dock with that frame."
                                + "<BR><BR>Default: off</FONT>"
                                + "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isEasyTabDock());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Allow Nested Floating Windows");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setNestedFloatingAllowed(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isNestedFloatingAllowed()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<FONT FACE='Tahoma' SIZE='4'><B><FONT COLOR='#0000FF'>Nested Floating Windows<BR></FONT></B><BR></FONT>"
                                + "<FONT FACE='Tahoma' SIZE='3'><B>An option to allow nested windows when in floating mode</B>"
                                + "<BR><BR>JIDE Docking Framework can allow you to have as many nested windows in one floating "
                                + "<BR>container as you want. However, not all your users want to have that complexity. So we "
                                + "<BR>leave it as an option and you can choose to turn it on or off. "
                                + "<BR><BR>Default: off</FONT> <BR>"
                                + "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isNestedFloatingAllowed());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show Gripper");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setShowGripper(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isShowGripper()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show Gripper</B><BR></FONT><BR></FONT>"
                                + "<FONT FACE='Tahoma' SIZE='3'><B>An option to give user a visual hint that the dockable frame can be dragged<BR></B>"
                                + "<BR>Normal tabs in JTabbedPane can not be dragged. However in our demo, "
                                + "<BR>most of them can be dragged. To make it obvious to user, we added an "
                                + "<BR>option so that a gripper is painted on the tab or the title bar of those "
                                + "<BR>dockable frames which can be dragged."
                                + "<BR><BR>Default: off</FONT><BR>"
                                + "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(true);//frame.getDockingManager().isShowGripper());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show TitleBar");
        checkBoxMenuItem.setMnemonic('T');
        checkBoxMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setShowTitleBar(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isShowTitleBar()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show TitleBar</B><BR></FONT><BR></FONT>"
                                + "<FONT FACE='Tahoma' SIZE='3'><B>An option to show/hide dockable frame's title bar<BR></B>"
                                + "<BR><BR>Default: on</FONT><BR>"
                                + "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isShowTitleBar());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("SideBar Rollover");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setSidebarRollover(((AbstractButton) e.getSource()).isSelected());
                    if (parent.getDockingManager().isSidebarRollover()) {
                        Lm.showPopupMessageBox("<HTML>"
                                + "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>SideBar Rollover</B><BR></FONT><BR></FONT>"
                                + "<FONT FACE='Tahoma' SIZE='3'><B>An option to control the sensibility of tabs on sidebar<BR></B>"
                                + "<BR>Each tab on four sidebars is corresponding to a dockable frame. Usually when "
                                + "<BR>user moves mouse over the tab, the dockable frame will show up. However in Eclipse"
                                + "<BR>you must click on it to show the dockable frame. This option will allow you to "
                                + "<BR>control the sensibility of it."
                                + "<BR><BR>Default: on</FONT><BR>"
                                + "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isSidebarRollover());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem("Draw Full Outline When Dragging");
        radioButtonMenuItem1.setMnemonic('D');
        radioButtonMenuItem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButtonMenuItem) {
                    parent.getDockingManager().setOutlineMode(DefaultDockingManager.FULL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>"
                            + "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>"
                            + "<FONT SIZE='4'>"
                            + "<FONT COLOR='#0000FF' SIZE='3'><BR><BR><B>An option of how to paint the outline during dragging.</B></FONT>"
                            + "<BR><BR><FONT SIZE='3'>Since our demo is purely based on Swing, and there is no way to have transparent native "
                            + "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. "
                            + "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as "
                            + "<BR>an option to user to choose. You can try each of the option and see which one you like most."
                            + "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B><BR>Pros: Fast, very smooth, works the best if user "
                            + "of your application always keeps it as full screen"
                            + "<BR>Cons: Partial outline or no outline at all if outside main frame although it's there wherever "
                            + "your mouse is."
                            + "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>"
                            + "<BR>Pros: It always draw the full outline"
                            + "<BR>Cons: Sometimes it's flickering. Slower comparing with partial outline mode."
                            + "<BR><BR>Default: PARTIAL_OUTLINE_MODE</FONT>"
                            + "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem1.setSelected(parent.getDockingManager().getOutlineMode() == DefaultDockingManager.FULL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem1);

        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("Draw Partial Outline When Dragging");
        radioButtonMenuItem2.setMnemonic('P');
        radioButtonMenuItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButtonMenuItem) {
                    parent.getDockingManager().setOutlineMode(DefaultDockingManager.PARTIAL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>"
                            + "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>"
                            + "<FONT SIZE='4'><FONT COLOR='#0000FF'><BR></FONT><BR></FONT><B>An option of how to paint the outline during dragging. "
                            + "<BR><BR></B>Since our demo is purely based on Swing, and there is no way to have transparent native "
                            + "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. "
                            + "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as "
                            + "<BR>an option to user to choose. You can try each of the option and see which one you like most."
                            + "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B>"
                            + "<BR>Pros: Fast, very smooth"
                            + "<BR>Cons: Partial outline or no outline at all if outside main frame although it&#39;s there wherever your mouse is."
                            + "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>"
                            + "<BR>Pros: It always draw the full outline<BR>Cons: Sometimes it&#39;s flickering. Slower comparing with partial outline mode.</FONT>"
                            + "<BR><BR><FONT FACE='Tahoma'>Default: PARTIAL_OUTLINE_MODE</FONT>"
                            + "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem2.setSelected(parent.getDockingManager().getOutlineMode() == DefaultDockingManager.PARTIAL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem2);

        JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem("Draw Transparent Pane When Dragging");
        radioButtonMenuItem3.setMnemonic('P');
        radioButtonMenuItem3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButtonMenuItem) {
                    parent.getDockingManager().setOutlineMode(DefaultDockingManager.TRANSPARENT_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>"
                            + "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>"
                            + "<FONT SIZE='4'><FONT COLOR='#0000FF'><BR></FONT><BR></FONT><B>An option of how to paint the outline during dragging. "
                            + "<BR><BR></B>Instead of drawing an outline as all other options, this option will draw a transparent pane"
                            + "<BR>which looks better than the outline only."
                            + "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem3.setSelected(parent.getDockingManager().getOutlineMode() == DefaultDockingManager.TRANSPARENT_OUTLINE_MODE);
        menu.add(radioButtonMenuItem3);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMenuItem1);
        buttonGroup.add(radioButtonMenuItem2);
        buttonGroup.add(radioButtonMenuItem3);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Show Title on Outline");
        checkBoxMenuItem.setMnemonic('O');
        checkBoxMenuItem.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    parent.getDockingManager().setShowTitleOnOutline(((AbstractButton) e.getSource()).isSelected());
                }
            }
        });
        checkBoxMenuItem.setSelected(parent.getDockingManager().isShowTitleOnOutline());
        menu.add(checkBoxMenuItem);
        return menu;
    }

    private static void toggleButton(boolean selected, int button) {
        if (parent instanceof DockableHolder) {
            Collection<String> names = parent.getDockingManager().getAllFrames();
            for (String name : names) {
                DockableFrame f = parent.getDockingManager().getFrame(name);
                if (selected) {
                    f.setAvailableButtons(f.getAvailableButtons() | button);
                } else {
                    f.setAvailableButtons(f.getAvailableButtons() & ~button);
                }
            }
        }
    }

    /* Main Interface toolbar*/
    public static CommandBar createStandardCommandToolBar() {
        CommandBar commandBar = new CommandBar("Standard");
        commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
        commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
        commandBar.setInitIndex(1);
        commandBar.setInitSubindex(0);

        AbstractButton mainInterface = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.NEW));
        mainInterface.setToolTipText("New Report Interface");
        mainInterface.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.gc();
                    MainFrame.actionNewMainInterface();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        AbstractButton openWorkingFolder = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.OPEN));
        openWorkingFolder.setToolTipText("Open Working Folder");
        openWorkingFolder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionOpenWorkingFolder();
            }
        });
        save = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.SAVE));
        save.setToolTipText("Save");
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionSaveProperties();
            }
        });
        undo = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.UNDO));
        undo.setVisible(false);
        undo.setToolTipText("Undo (Ctrl+Z)");
        undo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionUndo();
            }
        });
        redo = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.REDO));
        redo.setVisible(false);
        redo.setToolTipText("Redo (Ctrl+Y)");
        redo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionRedo();
            }
        });
        AbstractButton help = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.HELP));
        help.setToolTipText("Help");
        help.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.actionHelp();
            }
        });
        commandBar.add(mainInterface);
        commandBar.add(openWorkingFolder);
        commandBar.add(save);
        commandBar.addSeparator();
        commandBar.add(undo);
        commandBar.add(redo);
//        commandBar.addSeparator();
        commandBar.add(help);
        return commandBar;
    }

//    public static CommandBar createOptionsCommandBar() {
//        CommandBar commandBar = new CommandBar("Options");
//        commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
//        commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
//        commandBar.setInitIndex(1);
//        commandBar.setInitSubindex(0);
//
//        AbstractButton search = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.SEARCHEDIT));
//        search.setToolTipText("Search and Edit");
//        search.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionSearchEdit();
//            }
//        });
//        AbstractButton doc = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.DOC));
//        doc.setToolTipText("Documentation");
//        doc.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionDoc();
//            }
//        });
//        AbstractButton template = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.TEMPLATE));
//        template.setToolTipText("Template");
//        template.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionTemplate();
//            }
//        });
//        AbstractButton stat = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.STAT));
//        stat.setToolTipText("Statistics");
//        stat.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionStatistic();
//            }
//        });
//        commandBar.add(search);
//        commandBar.add(doc);
//        commandBar.add(template);
//        commandBar.add(stat);
//        return commandBar;
//    }
//
//    public static CommandBar createToolsCommandBar() {
//        CommandBar commandBar = new CommandBar("Tools");
//        commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
//        commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
//        commandBar.setInitIndex(1);
//        commandBar.setInitSubindex(0);
//
//        AbstractButton exportD = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.EXPORTDATA));
//        exportD.setToolTipText("Export Data");
//        exportD.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionExportData();
//            }
//        });
//
//        AbstractButton user = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.USER));
//        user.setToolTipText("Create User");
//        user.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.actionCreateUser();
//            }
//        });
//        AbstractButton password = createButton(MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.Standard.CHANGEPASSWORD));
//        password.setToolTipText("Change Password");
//        password.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                MainFrame.actionChangePassword();
//            }
//        });
//        commandBar.add(exportD);
//        commandBar.add(user);
//        commandBar.add(password);
//        return commandBar;
//    }
//    
//    public static DockableFrame createFrameMenuTree() {
//        DockableFrame frame = new DockableFrame("Main Menu"
//                + "", MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.ShortCut.SHORTCUT));
//        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
//        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
//        frame.getContext().setInitIndex(1);
//        frame.getContentPane().add(new MenuInTree());
//        frame.setPreferredSize(new Dimension(200, 200));
//        return frame;
//    }
//    
    public static DockableFrame createFrameMenuMain() {
        DockableFrame frame = new DockableFrame("Main Menu", MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.ShortCut.SHORTCUT));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
        frame.getContext().setInitIndex(1);
        frame.getContentPane().add(new MainMenuPanel(parent));
        frame.setPreferredSize(new Dimension(200, 200));
        return frame;
    }

//    public static DockableFrame createFrameMenuTree() {
//        DockableFrame frame = new DockableFrame("Main Menu"
//                + "", MainFrameIconsFactory.getImageIcon(MainFrameIconsFactory.ShortCut.SHORTCUT));
//        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
//        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
//        frame.getContext().setInitIndex(1);
////        frame.getContentPane().add(new MenuInTree());
//        frame.setPreferredSize(new Dimension(200, 200));
//        return frame;
//    }
    private static String _lastDirectory = ".";
    private static byte[] _fullScreenLayout;
    private static boolean _autohideAll = false;
    private static MainFrame parent;
//    private static JMenuItem MIUndo;
//    private static JMenuItem MIRedo;
    private static AbstractButton undo;
    private static AbstractButton redo;
    private static AbstractButton save;
//    public static JMenuItem MISave;
//    public static JMenuItem MISaveAs;
}
