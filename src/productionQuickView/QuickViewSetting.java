package productionQuickView;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.dialog.PageList;
import com.jidesoft.plaf.UIDefaultsLookup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import login.Identification;
import mainFrame.MainFrame;
import setting.SettingIconsFactory;
import smartfactoryV2.ConnectDB;

public class QuickViewSetting extends MultiplePageDialog {

    public QuickViewSetting(JFrame owner, String title) throws HeadlessException {
        super(owner, title);
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getIndexPanel().setVisible(false);
        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        getPagesPanel().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        parent = this;
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = super.createButtonPanel();
        AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getApplyButton().isEnabled()) {
                    applyTargetFeature();
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
        buttonPanel.getButtonByName(ButtonNames.OK).setFocusable(false);
        buttonPanel.getButtonByName(ButtonNames.CANCEL).setFocusable(false);
        ((AbstractButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
        ((AbstractButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
        setDefaultCancelAction(cancelAction);
        setDefaultAction(okAction);
        this.getApplyButton().setFocusable(false);
        this.getApplyButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                applyTargetFeature();
            }
        });
        return buttonPanel;
    }

    @Override

    public Dimension getPreferredSize() {
        return new Dimension(450, 350);
    }

    public static void showOptionsDialog() {
        QuickViewSetting dialog = new QuickViewSetting(Identification.getQuickViewFrame(), "Settings");
        dialog.setIconImage(SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.DIALOGICON).getImage());
        dialog.setStyle(MultiplePageDialog.ICON_STYLE);
        PageList model = new PageList();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // setup model
        OptionPageGeneral panel = new OptionPageGeneral("Production Target Features",
                SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.GENERAL));
        model.append(panel);
        dialog.setPageList(model);
        dialog.pack();
        dialog.requestFocus();
        dialog.setLocationRelativeTo(MainFrame.getFrame());
//        JideSwingUtilities.globalCenterWindow(dialog);
        dialog.setVisible(true);
    }

    private static class OptionPage extends AbstractDialogPage {

        OptionPage(String name) {
            super(name);
        }

        OptionPage(String name, Icon icon) {
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
            headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.WHITE,
                    Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY));
            setLayout(new BorderLayout());
            add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
        }
    }

    private static class OptionPageGeneral extends OptionPage {

//        QuickViewFeature quickViewFeaturePan;

        OptionPageGeneral(String name, Icon icon) {
            super(name, icon);
            quickViewFeaturePan = new QuickViewFeature(this, parent);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(quickViewFeaturePan, BorderLayout.CENTER);
        }
    }

//    public static void main(String[] argv) {
//        LookAndFeelFactory.installDefaultLookAndFeel();
//        showOptionsDialog();
//    }
    
    private void applyTargetFeature() {
        ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.GREEN, quickViewFeaturePan.getTxtGreen().getText());
        ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.AMBERLESS, quickViewFeaturePan.getTxtAmberLess().getText());
        ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.AMBERMORE, quickViewFeaturePan.getTxtAmberMore().getText());
        ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.RED, quickViewFeaturePan.getTxtRed().getText());
    }
    private static QuickViewFeature quickViewFeaturePan;
    private static JDialog parent;
}
