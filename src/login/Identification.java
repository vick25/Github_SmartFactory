package login;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.OverlayableIconsFactory;
import com.jidesoft.swing.OverlayableUtils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import mainFrame.MainFrame;
import productionQuickView.ProductionQuickView1;
import resources.Constants;
import resources.ReadPropertiesFile;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;
import smartfactoryV2.SplashScreen;
import target.MachinesProductionTarget;
import user.ChangePassword;
import user.CreateUser;
import user.PasswordForgot;

public class Identification extends javax.swing.JDialog {

    public static CreateUser getCreateUser() {
        return _createUser;
    }

    public static void setCreateUser(CreateUser _createUser) {
        Identification._createUser = _createUser;
    }

    public static boolean isFrameSaved() {
        return mainFrameSaved;
    }

    public static void setFrameSaved(boolean frameSaved) {
        Identification.mainFrameSaved = frameSaved;
    }

    public static MainFrame getMainFrame() {
        return _mainFrame;
    }

    public static void setMainFrame(MainFrame _mainFrame) {
        Identification._mainFrame = _mainFrame;
    }

    public static JFrame getShowMainFrame() {
        return _showMainFrame;
    }

    public static void setShowMainFrame(JFrame _showMainFrame) {
        Identification._showMainFrame = _showMainFrame;
    }

    public int getUserID() {
        return userID;
    }

    public static JFrame getQuickViewFrame() {
        return quickViewFrame;
    }

    public static void setQuickViewFrame(JFrame quickViewFrame) {
        Identification.quickViewFrame = quickViewFrame;
    }

    public static ProductionQuickView1 getProductionQuickView() {
        return productionQuickView;
    }

    public static void setProductionQuickView(ProductionQuickView1 productionQuickView) {
        Identification.productionQuickView = productionQuickView;
    }

    public Identification(JFrame parent, boolean modal) {
        super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        _showMainFrame = parent;
        initComponents();
        initValues();
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                btnQuitterActionPerformed(null);
            }
        });
        Timer time = new Timer(150, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtLogin.getText().isEmpty() && !String.copyValueOf(txtPassword.getPassword()).isEmpty()
                        && String.copyValueOf(txtPassword.getPassword()).length() >= 6) {
                    btnConnexion.setEnabled(true);
                } else {
                    btnConnexion.setEnabled(false);
                }
                if (!txtLogin.getText().isEmpty() || !String.copyValueOf(txtPassword.getPassword()).isEmpty()) {
                    btnEffacer.setEnabled(true);
                } else {
                    btnEffacer.setEnabled(false);
                }
                if ("root".equals(txtLogin.getText())) {
                    hlChangePasswrd.setEnabled(false);
                    hlForgotPassword.setEnabled(false);
                    chkSaveLoginDetails.setSelected(false);
                    chkSaveLoginDetails.setEnabled(false);
                    hlCreateUser.setEnabled(true);
                } else {
                    hlCreateUser.setEnabled(false);
                    hlChangePasswrd.setEnabled(true);
                    hlForgotPassword.setEnabled(true);
//                    chkSaveLoginDetails.setSelected(true);
                    chkSaveLoginDetails.setEnabled(true);
                }
            }
        });
//        time.setCoalesce(true);
//        time.setRepeats(true);
//        time.setInitialDelay(0);
        time.start();
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        this.getRootPane().setDefaultButton(btnConnexion);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icons/contact-new.png")).getImage());
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pantout = new org.jdesktop.swingx.JXPanel();
        btnConnexion = new javax.swing.JButton();
        btnEffacer = new javax.swing.JButton();
        btnQuitter = new javax.swing.JButton();
        txtLogin = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        chkSaveLoginDetails = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        hlIPServer = new org.jdesktop.swingx.JXHyperlink();
        hlChangePasswrd = new org.jdesktop.swingx.JXHyperlink();
        hlForgotPassword = new org.jdesktop.swingx.JXHyperlink();
        hlCreateUser = new org.jdesktop.swingx.JXHyperlink();
        lblOverlaybleInfo = new javax.swing.JLabel(OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.INFO));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login & Server Connection");
        setIconImage(null);
        setResizable(false);

        pantout.setBackground(new java.awt.Color(1, 6, 29));
        pantout.setForeground(java.awt.Color.white);

        btnConnexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/b_usrcheck.png"))); // NOI18N
        btnConnexion.setMnemonic('V');
        btnConnexion.setText("Validate");
        btnConnexion.setEnabled(false);
        btnConnexion.setFocusable(false);
        btnConnexion.setOpaque(false);
        btnConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnexionActionPerformed(evt);
            }
        });

        btnEffacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/iClear.png"))); // NOI18N
        btnEffacer.setText("Clear");
        btnEffacer.setEnabled(false);
        btnEffacer.setFocusable(false);
        btnEffacer.setOpaque(false);
        btnEffacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEffacerActionPerformed(evt);
            }
        });

        btnQuitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit(5).png"))); // NOI18N
        btnQuitter.setText("Exit");
        btnQuitter.setToolTipText("Exit application");
        btnQuitter.setFocusable(false);
        btnQuitter.setOpaque(false);
        btnQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitterActionPerformed(evt);
            }
        });

        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
        });

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Login:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Password:");

        chkSaveLoginDetails.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkSaveLoginDetails.setForeground(java.awt.Color.white);
        chkSaveLoginDetails.setText("Save login details");
        chkSaveLoginDetails.setFocusable(false);
        chkSaveLoginDetails.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Server IP:");

        hlIPServer.setForeground(new java.awt.Color(255, 255, 255));
        hlIPServer.setText("127.0.0.1");
        hlIPServer.setClickedColor(new java.awt.Color(0, 255, 51));
        hlIPServer.setFocusable(false);
        hlIPServer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hlIPServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        hlIPServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlIPServerActionPerformed(evt);
            }
        });

        hlChangePasswrd.setBackground(new java.awt.Color(255, 252, 252));
        hlChangePasswrd.setForeground(new java.awt.Color(240, 0, 0));
        hlChangePasswrd.setText("Change password");
        hlChangePasswrd.setClickedColor(new java.awt.Color(0, 255, 51));
        hlChangePasswrd.setFocusable(false);
        hlChangePasswrd.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hlChangePasswrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlChangePasswrdActionPerformed(evt);
            }
        });

        hlForgotPassword.setBackground(new java.awt.Color(255, 252, 252));
        hlForgotPassword.setForeground(new java.awt.Color(240, 0, 0));
        hlForgotPassword.setText("Password forgotten?");
        hlForgotPassword.setClickedColor(new java.awt.Color(0, 255, 51));
        hlForgotPassword.setFocusable(false);
        hlForgotPassword.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hlForgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlForgotPasswordActionPerformed(evt);
            }
        });

        hlCreateUser.setBackground(new java.awt.Color(255, 252, 252));
        hlCreateUser.setForeground(new java.awt.Color(240, 0, 0));
        hlCreateUser.setText("Create user");
        hlCreateUser.setClickedColor(new java.awt.Color(0, 255, 51));
        hlCreateUser.setFocusable(false);
        hlCreateUser.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hlCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlCreateUserActionPerformed(evt);
            }
        });

        lblOverlaybleInfo.setToolTipText("Click for help ...");
        lblOverlaybleInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblOverlaybleInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JidePopup popup = new JidePopup();
                JLabel label = new JLabel("<HTML>Type in <b>\"root\"</b> "
                    + "as Login and <b>\"root123\"</b><BR>as password to create a user, then proceed"
                    + "<BR>to log in with the new credentials.</HTML>");
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                popup.add(label);
                popup.showPopup(new Insets(-5, 0, -5, 0), lblOverlaybleInfo);
            }
        });
        lblOverlaybleInfo.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pantoutLayout = new javax.swing.GroupLayout(pantout);
        pantout.setLayout(pantoutLayout);
        pantoutLayout.setHorizontalGroup(
            pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pantoutLayout.createSequentialGroup()
                .addComponent(hlCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hlChangePasswrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hlForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pantoutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkSaveLoginDetails)
                    .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pantoutLayout.createSequentialGroup()
                            .addComponent(btnConnexion)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEffacer)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pantoutLayout.createSequentialGroup()
                            .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2))
                            .addGap(13, 13, 13)
                            .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pantoutLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hlIPServer, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblOverlaybleInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pantoutLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnConnexion, btnEffacer, btnQuitter});

        pantoutLayout.setVerticalGroup(
            pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pantoutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hlIPServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOverlaybleInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkSaveLoginDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnexion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEffacer)
                    .addComponent(btnQuitter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pantoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hlForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hlChangePasswrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hlCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pantoutLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnConnexion, btnEffacer, btnQuitter});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pantout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pantout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hlIPServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlIPServerActionPerformed
        new ServerIPDialog(this, true).setVisible(true);
        String serverIPText = ServerIPDialog.getTxtServerIP().getText();
        if (!serverIPText.isEmpty() && !serverIPText.equalsIgnoreCase("0.0.0.0")) {
            hlIPServer.setText(serverIPText);
        }
    }//GEN-LAST:event_hlIPServerActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.selectAll();
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        btnConnexionActionPerformed(evt);
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.selectAll();
    }//GEN-LAST:event_txtLoginFocusGained

    private void btnQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitterActionPerformed
        if (isFrameSaved()) {
            if (JOptionPane.showConfirmDialog(this, "A session is opened. All work will be lost.\n"
                    + "Do you really want to close ?", "Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_btnQuitterActionPerformed

    private void btnEffacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEffacerActionPerformed
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtLogin.setText("");
        txtPassword.setText("");
        hlIPServer.setText(ConnectDB.pref.get(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectDB.getServerIP()));
        txtLogin.requestFocus();
        chkSaveLoginDetails.setSelected(false);
    }//GEN-LAST:event_btnEffacerActionPerformed

    private void btnConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnexionActionPerformed
        Thread worker = new Thread() {
            @Override
            public void run() {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ConnectDB.getConnectionInstance();
                try {
                    if (ConnectDB.con != null) {
                        ReadPropertiesFile.readConfig();//read the property file to get the time and delay for the schedule                   
                        //Add the userlist table
                        createUserTableInDatabase();
                        //         
                        boolean userFound = false;
                        String login = txtLogin.getText(),
                                password = String.copyValueOf(txtPassword.getPassword());
                        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT * FROM userlist")) {
                            ResultSet resultSet = ps.executeQuery();
                            ResultSetMetaData rsmd = resultSet.getMetaData();
                            byte nbCols = (byte) rsmd.getColumnCount();
                            while (resultSet.next()) {
                                for (byte i = 1; i <= nbCols; i++) {
                                    if ((login.equalsIgnoreCase(resultSet.getString(i)))
                                            && (password.equalsIgnoreCase(ConnectDB.decrypter(resultSet.getString(i + 1))))) {
                                        userFound = true;
                                        if (!okID) {
                                            ConnectDB.setServerIP(hlIPServer.getText());
                                            String status = resultSet.getString("status");
                                            userID = resultSet.getInt("IDuser");
                                            if (chkSaveLoginDetails.isSelected()) {
                                                saveLoginCredentials();//save the login credentials
                                            } else {
                                                if (!status.isEmpty()) {
                                                    try {
                                                        pref.clear();
                                                    } catch (BackingStoreException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                }
                                            }
                                            dispose();//close the login interface
                                            createTablesInDatabase();//Creating the MachinesProductionTarget table in the database
                                        /* Check if the Server IP Address has changed and remplace it */
                                            if (!Constants.ipAddress.equals(ConnectDB.getServerIP())) {
                                                setSettingsInPropertieFile(Constants.ipAddress);
                                            }
//                                ConnectDB.saveIP(ConnectDB.serverIP);
                                            if ("root".equals(login)) {
                                                _createUser = new CreateUser(_mainFrame, true);
                                                _createUser.setVisible(true);
                                                SplashScreen.setIdentification(new Identification(_mainFrame, true));
                                                SplashScreen.getIdentification().setVisible(true);
                                            } else {
                                                if (mainFrameSaved) {
                                                    _showMainFrame.setVisible(true);
                                                    if (quickViewFrame != null) {
                                                        quickViewFrame.setVisible(true);
                                                    }
                                                    _showMainFrame.revalidate();
                                                } else {
                                                    if (_mainFrame == null) {
                                                        _mainFrame = new MainFrame(userID);
                                                        _mainFrame.showFrame();
                                                        if (ConnectDB.pref.getBoolean(SettingKeyFactory.DefaultProperties.SHOWPRODUCTIONQVIEW, false)) {
                                                            quickViewFrame = new JFrame("Production Quick View");
                                                            quickViewFrame.setSize(950, 570);
                                                            productionQuickView = new ProductionQuickView1(quickViewFrame);
                                                            quickViewFrame.setContentPane(productionQuickView);
                                                            quickViewFrame.setIconImage(new ImageIcon(getClass().
                                                                    getClassLoader().getResource("images/smart_factory_logo_icon.png")).getImage());
                                                            quickViewFrame.setLocationRelativeTo(_mainFrame);
                                                            quickViewFrame.setVisible(true);
                                                            quickViewFrame.addWindowListener(new WindowAdapter() {

                                                                @Override
                                                                public void windowClosing(WindowEvent e) {
                                                                    quickViewFrame = null;
                                                                    System.gc();
                                                                }
                                                            });
                                                            if (!MachinesProductionTarget.isTargetFound()) {
                                                                new MachinesProductionTarget(quickViewFrame, true).setVisible(true);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        okID = true;
                                    }
                                }
                            }
                            if (!userFound) {
                                JOptionPane.showMessageDialog(Identification.this, "Please check your connection "
                                        + "settings :\nLogin and/or Password... ", "Login & Server Connection", 
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        } catch (SQLException ex) {
                            ConnectDB.catchSQLException(ex);
                        }
//                    executeService();
//                    String filePath = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
//                            append("classes").append(pathSeparator).append("bin").append(pathSeparator).
//                            append("myService.exe").toString()).getAbsolutePath();
//                    System.out.println(filePath);
//                    System.out.println(filePath.substring(0, filePath.length() - 17));
//                    System.out.println(filePath.substring(0, filePath.length() - 25));
                    }
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ConnectDB.appendToFileException(ex);
                }
            }
        };
        worker.start();
    }//GEN-LAST:event_btnConnexionActionPerformed

    private void hlChangePasswrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlChangePasswrdActionPerformed
        new ChangePassword(this, true).setVisible(true);
    }//GEN-LAST:event_hlChangePasswrdActionPerformed

    private void hlForgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlForgotPasswordActionPerformed
        new PasswordForgot(this).setVisible(true);
    }//GEN-LAST:event_hlForgotPasswordActionPerformed

    private void hlCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlCreateUserActionPerformed
        btnConnexionActionPerformed(evt);
    }//GEN-LAST:event_hlCreateUserActionPerformed

    private void initValues() {
        hlIPServer.setText(ConnectDB.pref.get(SettingKeyFactory.Connection.SERVERIPADDRESS, hlIPServer.getText()));
        txtLogin.setText(this.pref.get("login", txtLogin.getText()));
        txtPassword.setText(this.pref.get("password", String.copyValueOf(txtPassword.getPassword())));
        chkSaveLoginDetails.setSelected(ConnectDB.pref.getBoolean(SettingKeyFactory.Privacy.SAVELOGININFO,
                chkSaveLoginDetails.isSelected()));
        if (txtLogin.getText().isEmpty()) {
            txtLogin.requestFocus();
        } else {
            txtPassword.requestFocus();
        }
    }

    private void saveLoginCredentials() {
        ConnectDB.pref.put(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectDB.getServerIP());
        this.pref.put("login", txtLogin.getText());
        this.pref.put("password", String.copyValueOf(txtPassword.getPassword()));
        ConnectDB.pref.putBoolean(SettingKeyFactory.Privacy.SAVELOGININFO, chkSaveLoginDetails.isSelected());
    }

    private void createUserTableInDatabase() {
        @SuppressWarnings("ReplaceStringBufferByString")
        StringBuilder query = new StringBuilder(1024).append("USE smartfactory; \n"
                + "CREATE TABLE IF NOT EXISTS `userlist` ( \n"
                + " `IDuser` smallint(6) NOT NULL AUTO_INCREMENT, \n"
                + " `firstname` varchar(50) NOT NULL, \n"
                + " `lastname` varchar(50) NOT NULL, \n"
                + " `othername` varchar(50) NOT NULL, \n"
                + " `email` text NOT NULL, \n"
                + " `login` varchar(20) NOT NULL, \n"
                + " `password` varchar(15) NOT NULL, \n"
                + " `question` text NOT NULL, \n"
                + " `answer` text NOT NULL, \n"
                + " `privilege` varchar(20) NOT NULL, \n"
                + " `status` varchar(20) NOT NULL, \n"
                + " PRIMARY KEY (`IDuser`)) \n"
                + " ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1; \n"
                + "INSERT INTO `userlist` \n(`IDuser`, `firstname`,`lastname`,`othername`,`email`,"
                + "`login`,`password`,`question`,`answer`,`privilege`,`status`) VALUES \n"
                + "(1,'root','root','mf','smartfactory','root','").append(ConnectDB.crypter("root123")).
                append("','ideax'," + "'").append(ConnectDB.crypter("smartfactory")).append("','Admin','mainUser');");
        try (Statement stmt = ConnectDB.con.createStatement()) {
            stmt.executeUpdate(query.toString());
        } catch (SQLException ex) {
        }
    }

    private void createTablesInDatabase() throws SQLException {
        try (Statement stmt = ConnectDB.con.createStatement()) {
            String[] queries = {Queries.CREATE_TABLE_BREAKS, Queries.CREATE_TABLE_TARGET,
                Queries.CREATE_TABLE_STARTENDTIME, Queries.CREATE_TABLE_TIMEBREAKS};
            for (String query : queries) {
                stmt.executeUpdate(query);
            }
        }
    }

    private boolean serviceRunning() {
        return false;
    }

    @SuppressWarnings("UnusedAssignment")
    private void setSettingsInPropertieFile(String ipAddress) throws IOException {
        Properties props = new Properties();
        OutputStream out = null;
        File classpathFile = null;
        try {
            classpathFile = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
                    append("classes").append(pathSeparator).append("resources").append(pathSeparator).
                    append("smfProperties.properties").toString());
            if (classpathFile.exists()) {
                props.load(new FileReader(classpathFile));
                //Change the values here
                props.setProperty("ipAddress", ipAddress);
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
//            writeException(e, classpathFile);//If any exception arise, write it to a file
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

    @SuppressWarnings("NestedAssignment")
    private void executeService() throws FileNotFoundException {
        String[] params = new String[18];
        String filePath = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
                append("classes").append(pathSeparator).append("bin").append(pathSeparator).
                append("myService.exe").toString()).getAbsolutePath();
        System.out.println(filePath);
        params[0] = "cmd.exe";
        params[1] = filePath;
        params[2] = " //IS//MySMFService";
        params[3] = " --Install=" + filePath;
        params[4] = " --Description=SmartFactory Report Service";
        params[5] = " --Jvm=auto";
        params[6] = " --Classpath=" + filePath.substring(0, filePath.length() - 18);
        params[7] = " --StartMode=jvm";
        params[8] = " --StartClass=" + filePath.substring(0, filePath.length() - 18)
                + pathSeparator + "serverService.SMFServerService";
        params[9] = " --StartMethod=windowsService";
        params[10] = " --StartParams=start";
        params[11] = " --StopMode=jvm";
        params[12] = " --StopClass=" + filePath.substring(0, filePath.length() - 18)
                + pathSeparator + "serverService.SMFServerService";
        params[13] = " --StopMethod=windowsService";
        params[14] = " --StopParams=stop";
        params[15] = " --LogPath=" + filePath.substring(0, filePath.length() - 18)
                + pathSeparator + "logs";
        params[16] = " --StdOutput=auto";
        params[17] = " --StdError=auto";

        try {
            final Process p = Runtime.getRuntime().exec(params, null, new File(filePath));
//            System.out.println(p.toString());

            int result = p.waitFor();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
//                @SuppressWarnings("NestedAssignment")
                        String line;
                        try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                            while ((line = input.readLine()) != null) {
                                System.out.println(line);
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            thread.start();
            System.out.println(p.exitValue());
//            thread.join();
            if (result != 0) {
                System.out.println("Process failed with status: " + result);
            }
        } catch (IOException e) {
            System.out.println("Procccess not read " + e);
        } catch (InterruptedException ex) {
            Logger.getLogger(Identification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeel();
                com.jidesoft.utils.Lm.verifyLicense("OSFAC", "OSFAC-DMT", "vx1xhNgC4CtD2SQc.kC5mp99mO0Bs1d2");
//                System.out.println(System.getProperty("jdbc.drivers"));
                Identification dialog = new Identification(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnexion;
    private javax.swing.JButton btnEffacer;
    private javax.swing.JButton btnQuitter;
    private javax.swing.JCheckBox chkSaveLoginDetails;
    private org.jdesktop.swingx.JXHyperlink hlChangePasswrd;
    private org.jdesktop.swingx.JXHyperlink hlCreateUser;
    private org.jdesktop.swingx.JXHyperlink hlForgotPassword;
    private org.jdesktop.swingx.JXHyperlink hlIPServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblOverlaybleInfo;
    private org.jdesktop.swingx.JXPanel pantout;
    public static javax.swing.JTextField txtLogin;
    public static javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
    public boolean okID = false;
    private final String pathSeparator = File.separator;
    private int userID = -1;
    private static boolean mainFrameSaved = false;//boolean to check if the mainframe is saved 
    private static MainFrame _mainFrame;
    private static JFrame quickViewFrame;//production quick view interface that holds the productionQuickView panal
    private static ProductionQuickView1 productionQuickView = null;
    private static JFrame _showMainFrame = null;
    private static CreateUser _createUser = null;//create user interface
    private final Preferences pref = Preferences.userNodeForPackage(Identification.class);
}
