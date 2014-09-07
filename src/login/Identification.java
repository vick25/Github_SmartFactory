package login;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import mainFrame.MainFrame;
import productionQuickView.ProductionQuickView;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;
import smartfactoryV2.Queries;
import target.TargetInsert;
import user.ChangePassword;
import user.CreateUser;
import user.PasswordForgot;

public class Identification extends javax.swing.JDialog {

    public static boolean isFrameSaved() {
        return frameSaved;
    }

    public static void setFrameSaved(boolean frameSaved) {
        Identification.frameSaved = frameSaved;
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
        hlIPServer.setFocusable(false);
        hlIPServer.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        hlIPServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        hlIPServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlIPServerActionPerformed(evt);
            }
        });

        hlChangePasswrd.setBackground(new java.awt.Color(255, 252, 252));
        hlChangePasswrd.setForeground(new java.awt.Color(240, 0, 0));
        hlChangePasswrd.setText("Change password");
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
                JLabel label = new JLabel("<HTML>Type in \"root\" "
                    + "as Login and \"root123\"<BR>as password to create a user, then proceed"
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
        txtLogin.setText("");
        txtPassword.setText("");
        hlIPServer.setText(ConnectDB.pref.get(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectDB.serverIP));
        txtLogin.requestFocus();
        chkSaveLoginDetails.setSelected(false);
    }//GEN-LAST:event_btnEffacerActionPerformed

    private void btnConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnexionActionPerformed
//        dialog = this;
        ConnectDB.getConnectionInstance();
        try {
            //Add the userlist table
            createUserTableInDatabase();
            //         
            boolean userFound = false;
            String login = txtLogin.getText(),
                    password = String.copyValueOf(txtPassword.getPassword());
            try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT * FROM userlist")) {
                resultSet = ps.executeQuery();
                ResultSetMetaData rsmd = resultSet.getMetaData();
                byte nbCols = (byte) rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (byte i = 1; i <= nbCols; i++) {
                        if ((login.equalsIgnoreCase(resultSet.getString(i)))
                                && (password.equalsIgnoreCase(ConnectDB.decrypter(resultSet.getString(i + 1))))) {
                            userFound = true;
                            if (!okID) {
                                ConnectDB.serverIP = hlIPServer.getText();
                                String status = resultSet.getString("status");
                                userID = resultSet.getInt("IDuser");
                                if (chkSaveLoginDetails.isSelected()) {
                                    this.saveLoginCredentials();//save the login credentials
                                } else {
                                    if (!status.isEmpty()) {
                                        try {
                                            pref.clear();
                                        } catch (BackingStoreException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                                this.dispose();
                                createTablesInDatabase();//Creating the TargetInsert table in the database
//                                ConnectDB.saveIP(ConnectDB.serverIP);
                                if ("root".equals(login)) {
                                    new CreateUser(_mainFrame, true).setVisible(true);
                                    new Identification(_mainFrame, true).setVisible(true);
                                } else {
                                    if (frameSaved) {
                                        _showMainFrame.setVisible(true);
                                        if (quickViewFrame != null) {
                                            quickViewFrame.setVisible(true);
                                        }
                                        _showMainFrame.revalidate();
                                    } else {
                                        _mainFrame = new MainFrame(userID);
                                        _mainFrame.showFrame();
                                        if (ConnectDB.pref.getBoolean(SettingKeyFactory.DefaultProperties.SHOWPRODUCTIONQVIEW, false)) {
                                            quickViewFrame = new JFrame("Production Quick View");
                                            quickViewFrame.setSize(915, 570);
                                            productionQuickView = new ProductionQuickView(quickViewFrame);
                                            quickViewFrame.setContentPane(productionQuickView);
                                            quickViewFrame.setIconImage(new ImageIcon(getClass().getResource("/images/smart_factory_logo_icon.png")).getImage());
                                            quickViewFrame.setLocationRelativeTo(_mainFrame);
                                            quickViewFrame.setVisible(true);
                                            quickViewFrame.addWindowListener(new WindowAdapter() {

                                                @Override
                                                public void windowClosing(WindowEvent e) {
                                                    quickViewFrame = null;
                                                }
                                            });
                                            if (!TargetInsert.isTargetFound()) {
                                                new TargetInsert(quickViewFrame, true).setVisible(true);
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
                    JOptionPane.showMessageDialog(this, "Please check your connection settings :\n"
                            + "Login and/or Password... ", "Login & Server Connection", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ConnectDB.catchSQLException(ex);
            }
        } catch (HeadlessException ex) {
            ex.printStackTrace();
        }
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
        ConnectDB.pref.put(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectDB.serverIP);
        this.pref.put("login", txtLogin.getText());
        this.pref.put("password", String.copyValueOf(txtPassword.getPassword()));
        ConnectDB.pref.putBoolean(SettingKeyFactory.Privacy.SAVELOGININFO, chkSaveLoginDetails.isSelected());
    }

    private void createUserTableInDatabase() {
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
//
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
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
    private int userID = -1;
    private static boolean frameSaved = false;
    private static MainFrame _mainFrame;
    public static JFrame quickViewFrame;
    private static JFrame _showMainFrame = null;
    public static ProductionQuickView productionQuickView = null;
//    public static Identification dialog;
//    private final JDialog parent;
    private ResultSet resultSet = null;
    private final Preferences pref = Preferences.userNodeForPackage(Identification.class);
}
