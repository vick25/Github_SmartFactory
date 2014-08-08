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
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import login.Identification;
import productionPanel.ProductionPane;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import static smartfactoryV2.ConnectDB.lookAndFeel;
import static smartfactoryV2.ConnectDB.pref;
import static smartfactoryV2.ConnectDB.tempDir;
import user.CreateUser;
import userguide.About;

public class MainFrame extends DefaultDockableBarDockableHolder {

    public MainFrame(int idUser) throws HeadlessException {
        ConnectDB.getConnectionInstance();
        MainFrame.idUser = idUser;
        lookAndFeel = pref.getInt(SettingKeyFactory.Theme.LOOKANDFEEL, lookAndFeel);
        LookAndFeelFactory.installJideExtension(lookAndFeel);//Set the theme
    }

    public DefaultDockableBarDockableHolder showFrame() throws SQLException {
//        _frame = new MainFrame(idUser);
        _frame = Identification.mainFrame;
        productionPane = new ProductionPane(_frame);
//        connectionToServer();
        _frame.setTitle("Smartfactory Production Report Tool 1.0");
        _frame.setIconImage(new ImageIcon(_frame.getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
        // add a window listener to do clear up when windows closing.
        _windowListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                actionExit();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                productionPane.viewData.setVisible(false);
            }
        };
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
        // add toolbar
        cbf = new MainFrameCommandBarFactory(_frame);
        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createMenuCommandBar());
        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createStandardCommandToolBar());
//        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createOptionsCommandBar());
//        _frame.getDockableBarManager().addDockableBar(MainFrameCommandBarFactory.createToolsCommandBar());

        // add status bar
        _statusBar = createStatusBar();
        _frame.getContentPane().add(_statusBar, BorderLayout.AFTER_LAST_LINE);
        _frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);
        _frame.getDockingManager().setUndoLimit(10);
        _frame.getDockingManager().beginLoadLayoutData();
        // add all dockable frames
//        _frame.getDockingManager().addFrame(MainFrameCommandBarFactory.createFrameMenuTree());
        _frame.getDockingManager().addFrame(MainFrameCommandBarFactory.createFrameMenuMain());
        _frame.getDockingManager().setShowGripper(true);
        _frame.getDockingManager().setOutlineMode(DockingManager.TRANSPARENT_OUTLINE_MODE);
        _frame.getDockingManager().setPopupMenuCustomizer(new com.jidesoft.docking.PopupMenuCustomizer() {

            @Override
            public void customizePopupMenu(JPopupMenu menu, final DockingManager dockingManager, final DockableFrame dockableFrame, boolean onTab) {
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
//        timer = new Timer(300, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (_documentPane.isDocumentOpened("Training")
//                        && !Training.propertiesToSave.equals(Training.propertiesToSavePrevious)) {
//                    MainFrameCommandBarFactory.save.setEnabled(true);
//                } else {
//                    MainFrameCommandBarFactory.save.setEnabled(false);
//                }
//                MainFrameCommandBarFactory.MISave.setEnabled(MainFrameCommandBarFactory.save.isEnabled());
//                MainFrameCommandBarFactory.MISaveAs.setEnabled(MainFrameCommandBarFactory.MISave.isEnabled());
//
//                if (_documentPane.isDocumentOpened("Training")
//                        && Training.indexCurrentAction > 1) {
//                    MainFrameCommandBarFactory.undo.setEnabled(false);
//                } else {
//                    MainFrameCommandBarFactory.undo.setEnabled(false);
//                }
//                if (_documentPane.isDocumentOpened("Training")
//                        && Training.indexCurrentAction < Training.vectorAction.size() - 1) {
//                    MainFrameCommandBarFactory.redo.setEnabled(false);
//                } else {
//                    MainFrameCommandBarFactory.redo.setEnabled(false);
//                }
//                MainFrameCommandBarFactory.MIRedo.setEnabled(MainFrameCommandBarFactory.redo.isEnabled());
//                MainFrameCommandBarFactory.MIUndo.setEnabled(MainFrameCommandBarFactory.undo.isEnabled());
//            }
//        });
//        timer.start();
        _frame.toFront();
//        JideSwingUtilities.globalCenterWindow(_frame);
        return _frame;
    }

//    private void connectionToServer() {
//        try {
//            final Socket sClient = new Socket(DBConnection.IPServer, DBConnection.PORTMAINSERVER);
//            Thread th = new Thread() {
//
//                @Override
//                public void run() {
//                    try {
//                        DataInputStream in = new DataInputStream(sClient.getInputStream());
//                        DataOutputStream out = new DataOutputStream(sClient.getOutputStream());
//                        byte[] buffer = new byte[512];
//                        int nbbit;
//                        while ((nbbit = in.read(buffer)) != -1) {
//                            Thread.sleep(5000);
//                            out.write("test connection".getBytes());
//                        }
//                    } catch (IOException | InterruptedException e) {
//                        JXErrorPane.showDialog(_frame, new ErrorInfo("Fatal error", "The server's connection isn't "
//                                + "available anymore ...\n" + e.getMessage(), null, null, e, Level.SEVERE, null));
//                        System.exit(0);
//                        System.err.println("Connection's not available");
////                        ex.printStackTrace();
//                    }
//                }
//            };
//            th.start();
//        } catch (IOException e) {
//            JXErrorPane.showDialog(_frame, new ErrorInfo("Fatal error", "Unable to connect to the "
//                    + "FTP server ...\n" + e.getMessage(), null, null, e, Level.SEVERE, null));
//            System.exit(0);
//        }
//    }
    private static void clearUp(String close) {
        if ("logout".equals(close)) {
            Identification.mainFrame = _frame;
            _frame.setVisible(false);
            return;
        }
        _frame.removeWindowListener(_windowListener);
        _windowListener = null;
        if (_frame.getLayoutPersistence() != null) {
            _frame.getLayoutPersistence().saveLayoutData();
        }
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

    public static StatusBar createStatusBar() {
        StatusBar statusBar = new StatusBar();
        lblStatus = new LabelStatusBarItem("Status");
        lblStatus.setFont(new Font("Tahoma", 0, 11));
        setStatusMessage("Ready");
        lblStatus.setAlignment(JLabel.LEADING);
        statusBar.add(lblStatus, JideBoxLayout.VARY);

        progress = new ProgressStatusBarItem();
        statusBar.add(progress, JideBoxLayout.VARY);

        labelServer = new LabelStatusBarItem("Line");
        labelServer.setFont(new Font("Tahoma", 0, 11));
        labelServer.setText(serverConnectionLabel);
        labelServer.setAlignment(JLabel.CENTER);
        statusBar.add(labelServer, JideBoxLayout.FLEXIBLE);

        final LabelStatusBarItem label = new LabelStatusBarItem("Line");
        label.setText("admin@ideax-africa.com");
        label.setAlignment(JLabel.CENTER);
        statusBar.add(label, JideBoxLayout.FLEXIBLE);

        timeStatusBar = new TimeStatusBarItem();
        timeStatusBar.setTextFormat(new SimpleDateFormat(ConnectDB.pref.get(SettingKeyFactory.General.DATEFORMAT,
                "MMMM dd, yyyy") + "  HH:mm:ss aa"));
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
                    ((JideTabbedPane) group).addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
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
//
//    public static void actionSearchEdit() {
//        if (!_documentPane.isDocumentOpened("Search and Edit")) {
//            final DocumentComponent document = new DocumentComponent(new SearchEdit(_frame), "Search and Edit", "Search and Edit"
//                    + "", new ImageIcon(_frame.getClass().getResource("/images/search16x16.png")));
//            _documentPane.openDocument(document);
//            CONFIRMCLOSETAB(document);
//        }
//        _documentPane.setActiveDocument("Search and Edit");
//    }
//    public static void actionStatistic() {
//        if (!_documentPane.isDocumentOpened("Statistic")) {
//            final DocumentComponent document = new DocumentComponent(new Statistic(_frame), "Statistic", "Statistic"
//                    + "", new ImageIcon(_frame.getClass().getResource("/images/kchart12.png")));
//            _documentPane.openDocument(document);
//            CONFIRMCLOSETAB(document);
//        }
//        _documentPane.setActiveDocument("Statistic");
//    }
//
//    public static void actionTemplate() {
//        if (!_documentPane.isDocumentOpened("Template")) {
//            final DocumentComponent document = new DocumentComponent(new Template(), "Template", "Template"
//                    + "", new ImageIcon(_frame.getClass().getResource("/images/bookmark-add12.png")));
//            _documentPane.openDocument(document);
//            CONFIRMCLOSETAB(document);
//        }
//        _documentPane.setActiveDocument("Template");
//    }
//
//    public static void actionDoc() {
//        if (!_documentPane.isDocumentOpened("Documentation")) {
//            final DocumentComponent document = new DocumentComponent(new Docs(_frame), "Documentation", "Documentation"
//                    + "", new ImageIcon(_frame.getClass().getResource("/images/bookcase16x16.png")));
//            _documentPane.openDocument(document);
//            CONFIRMCLOSETAB(document);
//        }
//        _documentPane.setActiveDocument("Documentation");
//    }
//    
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
        _frame = Identification.mainFrame = new MainFrame(1);
        _frame.showFrame();

//        if (!_documentPane.isDocumentOpened("Training")) {
//            final DocumentComponent document = new DocumentComponent(new Training(_frame), "Training", "Training"
//                    + "", new ImageIcon(_frame.getClass().getResource("/images/document_new12.png")));
//            _documentPane.openDocument(document);
//            CONFIRMCLOSETAB(document);
//        }
//        _documentPane.setActiveDocument("Training");
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
//
//    public static void actionSaveProperties() {
//        if (Training.pathFile.equals("")) {
//            JFileChooser fc = new JFileChooser();
//            fc.setFileFilter(new FileNameExtensionFilter("OSFAC TMT File(.tmt)", "tmt"));
////            fc.setSelectedFile(new File(nameFileToTitle + ".tmt"));
//            int result = fc.showSaveDialog(_frame);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                String choose = fc.getSelectedFile().getAbsolutePath();
//                if (fc.getSelectedFile().exists()) {
//                    if (JOptionPane.showConfirmDialog(_frame, "The file " + fc.getSelectedFile().getName() + " already exists, "
//                            + "would you like to overwrite it?", "Confirmation", 0) == 0) {
//                        if (!choose.endsWith(".tmt")) {
//                            choose = choose + ".tmt";
//                        }
//                        Training.pathFile = choose;
//                        saveProperties(Training.pathFile, Training.propertiesToSave);
//                    } else {
//                        actionSaveProperties();
//                    }
//                } else {
//                    if (!choose.endsWith(".tmt")) {
//                        choose = choose + ".tmt";
//                    }
//                    Training.pathFile = choose;
//                    saveProperties(Training.pathFile, Training.propertiesToSave);
//                }
//            }
//        } else {
//            saveProperties(Training.pathFile, Training.propertiesToSave);
//        }
//    }
//
//    public static void actionSaveAsProperties() {
//        JFileChooser fc = new JFileChooser();
//        fc.setFileFilter(new FileNameExtensionFilter("File(.tmt)", "tmt"));
//        int result = fc.showSaveDialog(_frame);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            String choose = fc.getSelectedFile().getAbsolutePath();
//            if (fc.getSelectedFile().exists()) {
//                if (JOptionPane.showConfirmDialog(_frame, "The file " + fc.getSelectedFile().getName() + " already exists, "
//                        + "would you like to overwrite it?", "Confirmation", 0) == 0) {
//                    if (!choose.endsWith(".tmt")) {
//                        choose = choose + ".tmt";
//                    }
//                    Training.pathFile = choose;
//                    Training.propertiesToSavePrevious = Training.propertiesToSave;
//                    saveProperties(Training.pathFile, Training.propertiesToSavePrevious);
//                } else {
//                    actionSaveAsProperties();
//                }
//            } else {
//                if (!choose.endsWith(".tmt")) {
//                    choose = choose + ".tmt";
//                }
//                Training.pathFile = choose;
//                Training.propertiesToSavePrevious = Training.propertiesToSave;
//                saveProperties(Training.pathFile, Training.propertiesToSavePrevious);
//            }
//        }
//    }

    public static void actionLogOut() {
        if (Identification.quickViewFrame != null) {
            Identification.quickViewFrame.setVisible(false);
        }
        clearUp("logout");
        Identification.okID = false;
        Identification.saveFrame = true;
        Identification iDialog = new Identification(Identification.mainFrame, false);
        iDialog.setVisible(true);
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
//            System.out.println("err");
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        }
    }

//    public static void actionUndo() {
//        Training.setPropertiesSaved(Training.vectorAction.elementAt(--Training.indexCurrentAction));
//    }
//
//    public static void actionRedo() {
//        Training.setPropertiesSaved(Training.vectorAction.elementAt(++Training.indexCurrentAction));
//    }
//    
    public static void actionExportData() {
//        new ExportImportData(_frame, true).setVisible(true);
    }

    public static void actionCreateUser() {
        new CreateUser(_frame, true).setVisible(true);
    }

    public static void actionChangePassword() {
//        new ChangePassword(_frame, true).setVisible(true);
    }

    public static void actionHelp() {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                tempDir = new File(ConnectDB.DEFAULTDIRECTORY + File.separator + "SmartFactory Data");
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
                dirIcon = tempDir.getAbsolutePath() + File.separator + "SmartFactory_Report_User_Guide.pdf";
                if (!new File(dirIcon).exists()) {
                    inputStream = MainFrame.class.getResourceAsStream("/userguide/SmartFactory_Report_User_Guide.pdf");
                    outputStream = new FileOutputStream(new File(dirIcon));
                    int read;
                    byte[] bytes = new byte[4096];
                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
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
                    JOptionPane.showMessageDialog(_frame, "Can not open this file. "
                            + "Please export it to your machine.",
                            "File", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(_frame, "The file you want to open does not "
                        + "exist or is damaged!", "File", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | HeadlessException ex) {
            ex.printStackTrace();
        }
//        new help.Help().setVisible(true);
    }

    public static void actionAbout() {
        new About(_frame, true).setVisible(true);
    }

    public static void actionCheckUpdate() {
    }

//    private static void saveProperties(String dest, String values) {
//        FileWriter monFichier = null;
//        BufferedWriter TAMPON = null;
//        try {
//            monFichier = new FileWriter(dest);
//            TAMPON = new BufferedWriter(monFichier);
//            TAMPON.write(values);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                TAMPON.flush();
//                TAMPON.close();
//                monFichier.close();
//                Training.propertiesToSave = values;
//                Training.propertiesToSavePrevious = Training.propertiesToSave;
//                progress.setProgress(100);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//    private static void openFileProperties(String choose) {
//        DataInputStream monFichier = null;
//        String textFile = "";
//        try {
//            monFichier = new DataInputStream(new FileInputStream(choose));
//            int nbbit;
//            byte tbyte[] = new byte[1024 * 8];
//            while (((nbbit = monFichier.read(tbyte)) != -1)) {
//                textFile = new String(tbyte, 0, nbbit);
//            }
//            Training.setPropertiesSaved(textFile);
//
//            Training.pathFile = choose;
//            Training.propertiesToSave = textFile;
//            Training.propertiesToSavePrevious = Training.propertiesToSave;
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        } finally {
//            try {
//                monFichier.close();
//            } catch (IOException exception1) {
//                exception1.printStackTrace();
//            }
//        }
//    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
//                    System.out.println(System.getProperty("user.home") + File.separator + "error.log");
                    com.jidesoft.utils.Lm.verifyLicense("OSFAC", "OSFAC-DMT", "vx1xhNgC4CtD2SQc.kC5mp99mO0Bs1d2");
                    LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                    new MainFrame(1).showFrame();
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
    }

    static String dirIcon = "";
    public static int idUser = 1;
//    static Timer timer;
    private static WindowAdapter _windowListener;
    private static final String PROFILE_NAME = "SMF_Main_Frame";
    private static byte[] _fullScreenLayout;
    private static boolean _autohideAll = false;
    static MainFrameCommandBarFactory cbf;
    static TimeStatusBarItem timeStatusBar;
    static String serverConnectionLabel = "<html><font color=black><strong>Connected @ "
            + ConnectDB.pref.get("IPServerAddress", ConnectDB.serverIP) + "</strong></font>";
    public static MainFrame _frame;
    public static Date dashBoardDate;
    public static LabelStatusBarItem labelServer, lblStatus;
    public static StatusBar _statusBar;
    public static ProductionPane productionPane;
    public static ProgressStatusBarItem progress;
    public static DocumentPane _documentPane;
}
