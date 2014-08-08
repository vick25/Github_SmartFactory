package setting;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.dialog.PageList;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;
import mainFrame.MainFrame;
import setting.panel.ConnectionPanel;
import setting.panel.FontColorPanel;
import setting.panel.GeneralPanel;
import setting.panel.LanguagePanel;
import setting.panel.PrivacyPanel;
import setting.panel.ThemePanel;
import smartfactoryV2.ConnectDB;

public class SettingsOptionsDialog extends MultiplePageDialog {

    public SettingsOptionsDialog(Frame owner, String title) throws HeadlessException {
        super(owner, title);
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getIndexPanel().setBackground(Color.white);
        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        getPagesPanel().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = super.createButtonPanel();
        AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getApplyButton().isEnabled()) {
                    try {
                        applyAction();
                    } catch (SQLException ex) {
                        ConnectDB.catchSQLException(ex);
                    }
                }
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        };
        AbstractAction cancelAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_CANCELLED);
                setVisible(false);
                dispose();
            }
        };
        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setFocusable(false);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setFocusable(false);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
        setDefaultCancelAction(cancelAction);
        setDefaultAction(okAction);
        this.getApplyButton().setFocusable(false);
        this.getApplyButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    applyAction();
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                }
            }
        });
        return buttonPanel;
    }

    private void applyAction() throws SQLException {
        applyGeneral(); /* General settings */

        applyPrivacy(); /* Privacy settings */

        applyTheme(); /* Themes settings */

        applyFontColor(); /* Font color settings */

        applyConnection(); /* Connection settings */

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(650, 500);
    }

    public static void showOptionsDialog() {
        SettingsOptionsDialog dialog = new SettingsOptionsDialog(MainFrame._frame, "Settings");
        dialog.setIconImage(SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.DIALOGICON).getImage());
        dialog.setStyle(MultiplePageDialog.ICON_STYLE);
        PageList model = new PageList();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //setup model
        OptionPageGeneral panGeneral = new OptionPageGeneral("General", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.GENERAL));
        OptionPagePrivacy panPrivacy = new OptionPagePrivacy("Privacy", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.PRIVACY));
//        AbstractDialogPage panel3 = new OptionPage("Web Features", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.WEB));
        OptionPageTheme panThemes = new OptionPageTheme("Themes", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.THEMES));
//        OptionPageLanguage panel5 = new OptionPageLanguage("Language", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.LANGUAGE));
        AbstractDialogPage panFontsColor = new OptionPageFontColor("Fonts & Color", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.FONTSCOLOR));
        AbstractDialogPage panConnection = new OptionPageConnection("Connections", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.CONNECTION));

        model.append(panGeneral);
        model.append(panPrivacy);
//        model.append(panel3);
        model.append(panThemes);
//        model.append(panel5);
        model.append(panFontsColor);
        model.append(panConnection);

        dialog.setPageList(model);
        dialog.pack();
//        JideSwingUtilities.globalCenterWindow(dialog);
        dialog.setLocationRelativeTo(MainFrame._frame);
        dialog.setVisible(true);
    }

    private static class OptionPage extends AbstractDialogPage {

        public OptionPage(String name) {
            super(name);
        }

        public OptionPage(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void lazyInitialize() {
            initComponents();
        }

        public void initComponents() {
            BannerPanel headerPanel = new BannerPanel(getTitle(), null);
            headerPanel.setForeground(Color.WHITE);
            headerPanel.setBackground(new Color(10, 36, 106));
            headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.darkGray, Color.darkGray, Color.gray));

            setLayout(new BorderLayout());
            add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
        }
    }

    private static class OptionPageGeneral extends OptionPage {

        public OptionPageGeneral(String name, Icon icon) {
            super(name, icon);
            general = new GeneralPanel(this);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(general, BorderLayout.CENTER);
        }
    }

    private static class OptionPagePrivacy extends OptionPage {

        public OptionPagePrivacy(String name, Icon icon) {
            super(name, icon);
            privacy = new PrivacyPanel(this);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(privacy, BorderLayout.CENTER);
        }
    }

    private static class OptionPageTheme extends OptionPage {

        public OptionPageTheme(String name, Icon icon) {
            super(name, icon);
            theme = new ThemePanel(this);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(theme, BorderLayout.CENTER);
        }
    }

    private static class OptionPageFontColor extends OptionPage {

        public OptionPageFontColor(String name, Icon icon) {
            super(name, icon);
            fontColor = new FontColorPanel(this);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(fontColor, BorderLayout.CENTER);
        }
    }

    private static class OptionPageConnection extends OptionPage {

        public OptionPageConnection(String name, Icon icon) {
            super(name, icon);
            connection = new ConnectionPanel(this);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(connection, BorderLayout.CENTER);
        }
    }

//    private static class OptionPageLanguage extends OptionPage {
//
//        public OptionPageLanguage(String name, Icon icon) {
//            super(name, icon);
//            language = new LanguagePanel(this);
//        }
//
//        @Override
//        public void initComponents() {
//            super.initComponents();
//            add(language, BorderLayout.CENTER);
//        }
//    }
    private String getPassword(int idUser) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT password FROM userlist\n"
                + "WHERE IDuser =?")) {
            ps.setInt(1, idUser);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                return ConnectDB.decrypter(ConnectDB.res.getString(1));
            }
            ps.close();
        }
        return "";
    }

    private String getUserLogin(int idUser) throws SQLException {
        try (PreparedStatement ps = ConnectDB.con.prepareStatement("SELECT login FROM userlist\n"
                + "WHERE IDuser =?")) {
            ps.setInt(1, idUser);
            ConnectDB.res = ps.executeQuery();
            while (ConnectDB.res.next()) {
                return ConnectDB.res.getString(1);
            }
        }
        return "";
    }

    private void applyGeneral() {
        ConnectDB.pref.putBoolean(SettingKeyFactory.General.BOLDTABLEHEAD, GeneralPanel.chkBoldTableHead.isSelected());
        ConnectDB.pref.putBoolean(SettingKeyFactory.General.CENTERTABLEHEAD, GeneralPanel.chkCenterTableHead.isSelected());
        ConnectDB.pref.put(SettingKeyFactory.General.DATEFORMAT, GeneralPanel.chkDateFormat.getSelectedItem().toString());
        MainFrame.setDateFormat(ConnectDB.pref.get(SettingKeyFactory.General.DATEFORMAT, "MMMM dd, yyyy"));
        ConnectDB.pref.putBoolean(SettingKeyFactory.General.TABCLOSESELECTEDONLY, GeneralPanel.chkCloseSelectedOnly.isSelected());
        ConnectDB.pref.putInt(SettingKeyFactory.General.TABPLACEMENT, GeneralPanel.cmbTabPlacement.getSelectedIndex());
        MainFrame.tabPlacement(GeneralPanel.cmbTabPlacement.getSelectedIndex() + 1, GeneralPanel.chkCloseSelectedOnly.isSelected());
        ConnectDB.pref.putBoolean(SettingKeyFactory.General.CONFIRMCLOSEMAINFRAME, GeneralPanel.chkConfirmCloseMainFrame.isSelected());
        ConnectDB.pref.putBoolean(SettingKeyFactory.General.CONFIRMCLOSETAB, GeneralPanel.chkConfirmCloseTab.isSelected());
        ConnectDB.pref.putBoolean(SettingKeyFactory.DefaultProperties.SHOWPRODUCTIONQVIEW, GeneralPanel.chkProdQuickView.isSelected());
    }

    private void applyPrivacy() throws SQLException {
        ConnectDB.pref.putBoolean(SettingKeyFactory.Privacy.SAVELOGININFO, PrivacyPanel.chkRememberLogin.isSelected());
        if (!PrivacyPanel.chkRememberLogin.isSelected()) {
            ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAME, "");
            ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAMEPASSWORD, "");
            ConnectDB.pref.putBoolean(SettingKeyFactory.Privacy.SAVEUSERNAMETRUE, true);
        } else {
            ConnectDB.pref.putBoolean(SettingKeyFactory.Privacy.SAVEUSERNAMETRUE, PrivacyPanel.radRememberUserName.isSelected());
            if (PrivacyPanel.radRememberUserName.isSelected()) {
                ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAME, getUserLogin(MainFrame.idUser));
                ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAMEPASSWORD, "");
            } else {
                ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAME, getUserLogin(MainFrame.idUser));
                ConnectDB.pref.put(SettingKeyFactory.Privacy.SAVEUSERNAMEPASSWORD, getPassword(MainFrame.idUser));
            }
        }
    }

    private void applyTheme() {
        if (ThemePanel.cmbTheme.getSelectedItem().equals("Office 2003 Style")) {
            MainFrame.actionTheme(LookAndFeelFactory.OFFICE2003_STYLE);
        } else if (ThemePanel.cmbTheme.getSelectedItem().equals("Office 2007 Style")) {
            MainFrame.actionTheme(LookAndFeelFactory.OFFICE2007_STYLE);
        } else if (ThemePanel.cmbTheme.getSelectedItem().equals("Eclipse 3x Style")) {
            MainFrame.actionTheme(LookAndFeelFactory.ECLIPSE3X_STYLE);
        } else if (ThemePanel.cmbTheme.getSelectedItem().equals("Xerto Style")) {
            MainFrame.actionTheme(LookAndFeelFactory.XERTO_STYLE);
        } else if (ThemePanel.cmbTheme.getSelectedItem().equals("Vsnet Style")) {
            MainFrame.actionTheme(LookAndFeelFactory.VSNET_STYLE);
        }
    }

    private void applyFontColor() {
        setColorFromKey(FontColorPanel.RStripe21Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE21COLOR1);
        setColorFromKey(FontColorPanel.RStripe21Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE21COLOR2);
        setColorFromKey(FontColorPanel.RStripe22Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE22COLOR1);
        setColorFromKey(FontColorPanel.RStripe22Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE22COLOR2);
        setColorFromKey(FontColorPanel.RStripe3Color1.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR1);
        setColorFromKey(FontColorPanel.RStripe3Color2.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR2);
        setColorFromKey(FontColorPanel.RStripe3Color3.getSelectedColor(), SettingKeyFactory.FontColor.RSTRIPE3COLOR3);
    }

    private void applyConnection() {
        String[] rawTexts = ConnectionPanel.serverIPAddress.getRawText();
        boolean hasEmpty = false;
        boolean hasInput = false;
        for (String text : rawTexts) {
            if (text.isEmpty()) {
                hasInput = false;
                break;
            } else {
                if (text.length() <= 0 || text.equals("0")) {
                    hasEmpty = true;
                } else {
                    hasInput = true;
                }
            }
        }
        if (hasEmpty && hasInput) {
            ConnectDB.pref.put(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectionPanel.serverIPAddress.getText());
        } else {
            MainFrame.setStatusMessage("Error in your in IP Address settings. It has not be saved");
        }
    }

    private void setColorFromKey(Color color, String key) {
        ConnectDB.pref.put(key, color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
    }

    public static void main(String[] argv) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        showOptionsDialog();
    }

    static ThemePanel theme;
    static PrivacyPanel privacy;
    static LanguagePanel language;
    static GeneralPanel general;
    static FontColorPanel fontColor;
    static ConnectionPanel connection;
}
