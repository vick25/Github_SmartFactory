package mainFrame;

import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentComponentAdapter;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.document.DocumentPane.TabbedPaneCustomizer;
import com.jidesoft.document.IDocumentGroup;
import com.jidesoft.document.IDocumentPane;
import com.jidesoft.document.PopupMenuCustomizer;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.MemoryStatusBarItem;
import com.jidesoft.status.ProgressStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.status.TimeStatusBarItem;
import com.jidesoft.swing.ContentContainer;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.utils.Lm;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import login.Identification;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.SplashScreen;
import user.CreateUser;
import userguide.About;

public class MainFrame extends DefaultDockableBarDockableHolder {

    public static MainFrame getFrame() {
        return _frame;
    }

    public static DocumentPane getDocumentPane() {
        return _documentPane;
    }

    public static int getUserID() {
        return _UserID;
    }

    public static Date getDashBoardDate() {
        return dashBoardDate;
    }

    public static void setDashBoardDate(Date dashBoardDate) {
        MainFrame.dashBoardDate = dashBoardDate;
    }

    public MainFrame(int UserID) throws HeadlessException {
        ConnectDB.getConnectionInstance();
        MainFrame._UserID = UserID;
    }

    public DefaultDockableBarDockableHolder showFrame() throws SQLException {
        _frame = Identification.getMainFrame();
        _frame.setTitle("Smartfactory Production Report Tool 2.0");
        _frame.setIconImage(new ImageIcon(_frame.getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
        // add toolbar
        MainFrameCommandBarFactory.setParent(_frame);
        // add a window listener to do clear up when windows closing.
        _windowListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                actionExit();
            }
        };
        _frame.addMouseMotionListener(new MouseAdapter() {
            final int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
            final int dismissDelayMinutes = (int) TimeUnit.MINUTES.toMillis(1);// 1 minutes

            @Override
            public void mouseEntered(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(dismissDelayMinutes);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);
            }
        });
        _frame.addWindowListener(_windowListener);
        // set the profile key
        _frame.getLayoutPersistence().setProfileKey(PROFILE_NAME);
        _frame.getLayoutPersistence().setXmlFormat(true);
        // create tabbed-document interface and add it to workspace area
        _documentPane = createDocumentTab();
        _documentPane.setTabbedPaneCustomizer(new TabbedPaneCustomizer() {

            @Override
            public void customize(final JideTabbedPane tabbedPane) {
                tabbedPane.setShowCloseButtonOnTab(true);
                tabbedPane.setShowCloseButtonOnSelectedTab(true);
            }
        });
        _frame.getDockingManager().getWorkspace().setLayout(new BorderLayout());
        _frame.getDockingManager().getWorkspace().add(_documentPane, BorderLayout.CENTER);
        _frame.getDockableBarManager().setProfileKey(PROFILE_NAME);
        //hide two toolbar
//        _frame.getDockableBarManager().hideDockableBar("Tools");
//        _frame.getDockableBarManager().hideDockableBar("Options");
        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createMenuCommandBar());
        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createStandardCommandToolBar());
//        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createOptionsCommandBar());
//        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createToolsCommandBar());
        // add status bar
        _statusBar = createStatusBar();
        _frame.getContentPane().add(_statusBar, BorderLayout.AFTER_LAST_LINE);

        _frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);
//        _frame.getDockingManager().setUndoLimit(10);
        _frame.getDockingManager().beginLoadLayoutData();
        // add all dockable frames
//        _frame.getDockingManager().addFrame(MainFrameCommandBarFactory.createFrameMenuTree());
        _frame.getDockingManager().addFrame(MainFrameCommandBarFactory.createFrameMenuMain());

        _frame.getDockingManager().setShowGripper(true);
        _frame.getDockingManager().setOutlineMode(DockingManager.TRANSPARENT_OUTLINE_MODE);
        _frame.getDockingManager().setPopupMenuCustomizer(new com.jidesoft.docking.PopupMenuCustomizer() {

            @Override
            public void customizePopupMenu(JPopupMenu menu, final DockingManager dockingManager,
                    final DockableFrame dockableFrame, boolean onTab) {
                menu.addSeparator();
                menu.add(new AbstractAction("Move to Document Area") {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dockingManager.removeFrame(dockableFrame.getKey(), true);

                        DocumentComponent documentComponent = new DocumentComponent((JComponent) dockableFrame.getContentPane(),
                                dockableFrame.getKey(),
                                dockableFrame.getTitle(),
                                dockableFrame.getFrameIcon());
                        _documentPane.openDocument(documentComponent);
                        _documentPane.setActiveDocument(documentComponent.getName());
                    }
                });
            }
        });
        // load layout information from previous session
        _frame.getLayoutPersistence().loadLayoutData();
        if (Lm.DEMO) {
            Lm.z();
        }
        _frame.toFront();
//        JideSwingUtilities.globalCenterWindow(_frame);
        return _frame;
    }

    private static void clearUp(String close) {
        if (_frame.getLayoutPersistence() != null) {
            _frame.getLayoutPersistence().saveLayoutData();
        }
        if ("logout".equals(close)) {
            Identification.setMainFrame(_frame);
            _frame.setVisible(false);
            return;
        }
        _frame.removeWindowListener(_windowListener);
        _windowListener = null;
        if (_documentPane != null) {
            _documentPane.dispose();
            _documentPane = null;
        }
        if (_statusBar != null && _statusBar.getParent() != null) {
            _statusBar.getParent().remove(_statusBar);
        }
//        timer.stop();
        _statusBar = null;
        _frame.dispose();
        _frame = null;
    }

    private StatusBar createStatusBar() {
        StatusBar statusBar = new StatusBar();
        lblStatus = new LabelStatusBarItem("Status");
        lblStatus.setFont(new Font("Tahoma", 0, 11));
        setStatusMessage("Ready");
        lblStatus.setAlignment(JLabel.LEADING);
        statusBar.add(lblStatus, JideBoxLayout.VARY);

        progress = new ProgressStatusBarItem();
        statusBar.add(progress, JideBoxLayout.VARY);

        final LabelStatusBarItem labelServer = new LabelStatusBarItem("Line");
        labelServer.setFont(new Font("Tahoma", 0, 11));
        labelServer.setText("<html><font color=black><strong>Connected @ "
                + ConnectDB.pref.get("IPServerAddress", ConnectDB.serverIP) + "</strong></font>");
        labelServer.setAlignment(JLabel.CENTER);
        statusBar.add(labelServer, JideBoxLayout.FLEXIBLE);

        final LabelStatusBarItem label = new LabelStatusBarItem("Line");
        label.setText("admin@ideax-africa.com");
        label.setAlignment(JLabel.CENTER);
        statusBar.add(label, JideBoxLayout.FLEXIBLE);

        timeStatusBar = new TimeStatusBarItem();
        timeStatusBar.setTextFormat(new SimpleDateFormat(ConnectDB.pref.get(SettingKeyFactory.General.DATEFORMAT,
                "MMMM dd, yyyy") + " HH:mm:ss aa"));
        statusBar.add(timeStatusBar, JideBoxLayout.FLEXIBLE);

        final MemoryStatusBarItem gc = new MemoryStatusBarItem();
        statusBar.add(gc, JideBoxLayout.FLEXIBLE);
        return statusBar;
    }

    public static void setStatusMessage(final String text) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                lblStatus.setText(text);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                lblStatus.setText("Ready");
            }
        };
        t.start();
    }

    private static DocumentPane createDocumentTab() {
        DocumentPane documentPane = new DocumentPane() {
            // add function to maximize (autohideAll) the document pane when mouse double clicks on the tabs of DocumentPane.

            @Override
            protected IDocumentGroup createDocumentGroup() {
                IDocumentGroup group = super.createDocumentGroup();
                if (group instanceof JideTabbedPane) {
                    ((Component) group).addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                                byte[] _fullScreenLayout = null;
                                if (!_autohideAll) {
                                    _fullScreenLayout = _frame.getDockingManager().getLayoutRawData();
                                    _frame.getDockingManager().autohideAll();
                                    _autohideAll = true;
                                } else {
                                    if (_fullScreenLayout != null) {
                                        _frame.getDockingManager().setLayoutRawData(_fullScreenLayout);
                                    }
                                    _autohideAll = false;
                                }
                                Component lastFocusedComponent = _documentPane.getActiveDocument().getLastFocusedComponent();
                                if (lastFocusedComponent != null) {
                                    lastFocusedComponent.requestFocusInWindow();
                                }
                            }
                        }
                    });
                }
                return group;
            }
        };
        documentPane.setTabPlacement(ConnectDB.pref.getInt(SettingKeyFactory.General.TABPLACEMENT, 0) + 1);
        documentPane.setPopupMenuCustomizer(new PopupMenuCustomizer() {

            @Override
            public void customizePopupMenu(JPopupMenu menu, final IDocumentPane pane, final String dragComponentName,
                    final IDocumentGroup dropGroup, boolean onTab) {
                if (!pane.isDocumentFloating(dragComponentName)) {
                    menu.addSeparator();
                    menu.add(new AbstractAction("Dock to the Side") {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DocumentComponent documentComponent = pane.getDocument(dragComponentName);
                            if (documentComponent != null) {
                                pane.closeDocument(dragComponentName);
                                // check if the document is really closed. There are cases a document is not closable or veto closing happens which can keep the document open after closeDocument call.
                                if (!pane.isDocumentOpened(dragComponentName)) {
                                    final DockableFrame frame = new DockableFrame(documentComponent.getName(), documentComponent.getIcon());
                                    frame.setTabTitle(documentComponent.getTitle());
                                    frame.getContentPane().add(documentComponent.getComponent());
                                    frame.setInitIndex(0);
                                    frame.setInitSide(DockContext.DOCK_SIDE_EAST);
                                    frame.setInitMode(DockContext.STATE_FRAMEDOCKED);
                                    _frame.getDockingManager().addFrame(frame);
                                    _frame.getDockingManager().activateFrame(frame.getKey());
                                }
                            }
                        }
                    });
                }
            }
        });
        return documentPane;
    }

    @Override
    protected ContentContainer createContentContainer() {
        return new LogoContentContainer();
    }

    public static void setDateFormat(String dateFormat) {
        timeStatusBar.setTextFormat(new SimpleDateFormat(dateFormat + "  HH:mm:ss"));
        ConnectDB.dateFormat = new SimpleDateFormat(dateFormat, new DateFormatSymbols());
    }

    private class LogoContentContainer extends ContentContainer {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                ImageIcon imageIcon = new ImageIcon(_frame.getClass().getResource("/images/smfLogo.png"));
                imageIcon.paintIcon(this, g, getWidth() - imageIcon.getIconWidth() - 2, -6);
            } catch (Exception e) {
            }
        }
    }

    public static void confirmCloseTab(final DocumentComponent document) {
        if (ConnectDB.pref.getBoolean(SettingKeyFactory.General.CONFIRMCLOSETAB, false)) {
            document.addDocumentComponentListener(new DocumentComponentAdapter() {

                @Override
                public void documentComponentClosing(DocumentComponentEvent e) {
                    int ret = JOptionPane.showConfirmDialog(_frame, "Do you want to close "
                            + document.getTitle() + "?", "Close Tab", JOptionPane.YES_OPTION);
                    if (ret == JOptionPane.YES_OPTION) {
                        // save it
                        document.setAllowClosing(true);
                    } else if (ret == JOptionPane.NO_OPTION) {
                        // don't save it
                        document.setAllowClosing(false);
                    } else if (ret == JOptionPane.CANCEL_OPTION) {
                        // don't save it
                        document.setAllowClosing(false);
                    }
                }
            });
        }
    }

    public static void tabPlacement(int index, final boolean val) {
        _documentPane.setTabPlacement(index);
        _documentPane.setTabbedPaneCustomizer(new TabbedPaneCustomizer() {

            @Override
            public void customize(final JideTabbedPane tabbedPane) {
                tabbedPane.setShowCloseButtonOnTab(true);
                tabbedPane.setShowCloseButtonOnSelectedTab(val);
            }
        });
    }

    public static void actionSetting() {
        setting.SettingsOptionsDialog.showOptionsDialog();
    }

    public static void actionNewReport() throws SQLException {
        _frame.dispose();
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        if (Identification.quickViewFrame != null) {
            Identification.quickViewFrame.dispose();
            Identification.quickViewFrame = null;
        }
        Identification.setMainFrame(new MainFrame(SplashScreen.getIdentification().getUserID()));
        _frame = Identification.getMainFrame();
        _frame.showFrame();
    }

    public static void actionOpenWorkingFolder() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(ConnectDB.DEFAULTDIRECTORY + File.separator + "SmartFactory Data"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//        JFileChooser fc = new JFileChooser();
//        fc.setFileFilter(new FileNameExtensionFilter("OSFAC TMT File(.tmt)", "tmt"));
//        int result = fc.showOpenDialog(_frame);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            String choose = fc.getSelectedFile().getAbsolutePath();
//            if (choose.endsWith(".tmt")) {
//                actionNewReport();
//                openFileProperties(choose);
//            } else {
//                JOptionPane.showMessageDialog(_frame, "This file format is not acceptable..."
//                        + "", "Warning", JOptionPane.WARNING_MESSAGE);
//            }
//        }
    }

    public static void actionLogOut() {
        if (Identification.quickViewFrame != null) {
            Identification.quickViewFrame.setVisible(false);
        }
        clearUp("logout");
        SplashScreen.getIdentification().okID = false;
        Identification.setShowMainFrame(_frame);
    }

    public static void actionExit() {
        if (ConnectDB.pref.getBoolean(SettingKeyFactory.General.CONFIRMCLOSEMAINFRAME, false)) {
            if (JOptionPane.showConfirmDialog(_frame, "Exiting SmartFactory Report Tool ?",
                    "Exit", JOptionPane.YES_OPTION) == 0) {
                clearUp("");
                System.exit(0);
            } else {
                _frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        } else {
            clearUp("");
            System.exit(0);
        }
    }

    public static void actionTheme(int theme) {
        try {
            LookAndFeelFactory.installJideExtension(theme);
            ConnectDB.pref.putInt(setting.SettingKeyFactory.Theme.LOOKANDFEEL, theme);
            _frame.getDockableBarManager().updateComponentTreeUI();
            _frame.getDockingManager().updateComponentTreeUI();
            SwingUtilities.updateComponentTreeUI(_frame);
        } catch (NullPointerException e) {
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        }
    }

    public static void actionCreateUser() {
        new CreateUser(_frame, true).setVisible(true);
    }
//
//    public static void actionChangePassword() {
////        new ChangePassword(_frame, true).setVisible(true);
//    }
//

    public static void actionHelp() {
        try {
            String dirIcon = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                ConnectDB.setMainDir(new File(ConnectDB.DEFAULTDIRECTORY + File.separator + "SmartFactory Data"));
                if (!ConnectDB.getMainDir().exists()) {
                    ConnectDB.getMainDir().mkdirs();
                }
                dirIcon = ConnectDB.getMainDir().getAbsolutePath() + File.separator + "SmartFactory_Report_User_Guide.pdf";
                if (!new File(dirIcon).exists()) {
                    inputStream = MainFrame.class.getResourceAsStream("/userguide/SmartFactory_Report_User_Guide.pdf");
                    outputStream = new FileOutputStream(new File(dirIcon));
                    int readFile;
                    byte[] bytes = new byte[4096];
                    while ((readFile = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, readFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            File fileToOpen = new File(dirIcon);
            if (fileToOpen.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(fileToOpen);
                } else {
                    JOptionPane.showMessageDialog(_frame, "Can not open this file. Please export it to your machine.",
                            "File", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(_frame, "The file you want to open does not "
                        + "exist or is damaged!", "File", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    public static void actionAbout() {
        new About(_frame, true).setVisible(true);
    }

//    public static void actionCheckUpdate() {
//    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
////                    System.out.println(System.getProperty("user.home") + File.separator + "error.log");
//                    LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
//                    new MainFrame(1).showFrame();
//                } catch (HeadlessException ex) {
//                    ex.printStackTrace();
//                } catch (SQLException ex) {
//                    ConnectDB.catchSQLException(ex);
//                }
//            }
//        });
//    }
    private static int _UserID = 1;
    private static WindowAdapter _windowListener;
    private static final String PROFILE_NAME = "SMF_Main_Frame";
    private static boolean _autohideAll = false;
    private static TimeStatusBarItem timeStatusBar;
    private static MainFrame _frame;
    private static Date dashBoardDate = null;
    private static LabelStatusBarItem lblStatus;
    private static StatusBar _statusBar = null;
    private ProgressStatusBarItem progress = null;
    private static DocumentPane _documentPane = null;
}
