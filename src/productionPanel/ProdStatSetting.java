package productionPanel;

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
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import mainFrame.MainFrame;
import setting.SettingIconsFactory;
import smartfactoryV2.ConnectDB;

public class ProdStatSetting extends MultiplePageDialog {

    public ProdStatSetting(JFrame owner, String title) throws HeadlessException {
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
                    applyChartFeature();
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
            public void actionPerformed(ActionEvent evt) {
                applyChartFeature();
            }
        });
        return buttonPanel;
    }

    @Override

    public Dimension getPreferredSize() {
        return new Dimension(450, 450);
    }

    public static void showOptionsDialog() {
        ProdStatSetting dialog = new ProdStatSetting(MainFrame._frame, "Settings");
        dialog.setIconImage(SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.DIALOGICON).getImage());
        dialog.setStyle(MultiplePageDialog.ICON_STYLE);
        PageList model = new PageList();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // setup model
        OptionPageGeneral panel1 = new OptionPageGeneral("Production Features",
                SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.GENERAL));
        model.append(panel1);
        dialog.setPageList(model);
        dialog.pack();
        dialog.setLocationRelativeTo(MainFrame._frame);
//        JideSwingUtilities.globalCenterWindow(dialog);
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
            headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                    Color.darkGray, Color.darkGray, Color.gray));
            setLayout(new BorderLayout());
            add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
        }
    }

    private static class OptionPageGeneral extends OptionPage {

        public OptionPageGeneral(String name, Icon icon) {
            super(name, icon);
            productionFeaturePan = new ProdFeature(this, parent);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(productionFeaturePan, BorderLayout.CENTER);
        }
    }

    public static void main(String[] argv) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        showOptionsDialog();
    }

    private void applyChartFeature() {
        ConnectDB.pref.putInt(ProdStatKeyFactory.ProdFeatures.SPFLAGTIMEFRAME,
                Integer.parseInt(ProdFeature.spFlagTime.getValue().toString()));
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, ProdFeature.radShiftOn.isSelected());
        if (ProdFeature.radShiftOn.isSelected()) {
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, true);
        } else {
            ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.CHKSHIFTON, false);
        }
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERMIN, ProdFeature.radPerMin.isSelected());
        ConnectDB.pref.putBoolean(ProdStatKeyFactory.ProdFeatures.RADPERHOUR, ProdFeature.radPerHour.isSelected());

        if (MainFrame.getDocumentPane().isDocumentOpened("Production")) {
            ProductionPane.setSettings();
        }
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.RBLineLabel, ChartFeature.RBLineLabel.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.RBSimpleLabel, ChartFeature.RBSimpleLabel.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.RBNoLabel, ChartFeature.RBNoLabel.isSelected());
//        ConnectDB.pref.putInt(StatKeyFactory.ProdFeatures.SLAngle, ChartFeature.SLAngle.getValue());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.ChBExplodedSegment, ChartFeature.ChBExplodedSegment.isSelected());
//
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.BarChBVLine, ChartFeature.BarChBVLine.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.BarChBHLine, ChartFeature.BarChBHLine.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.LineChBVLine, ChartFeature.LineChBVLine.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.LineChBHLine, ChartFeature.LineChBHLine.isSelected());
//        setColorFromKey(ChartFeature.CBColor.getSelectedColor(), StatKeyFactory.ProdFeatures.CBColor);
//        setColorFromKey(ChartFeature.CBColor2.getSelectedColor(), StatKeyFactory.ProdFeatures.CBColor2);
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.RandomColor, ChartFeature.RandomColor.isSelected());
//        ConnectDB.pref.putBoolean(StatKeyFactory.ProdFeatures.OneColor, ChartFeature.OneColor.isSelected());

//        if (!EventsStatistic.btnBarChart.isEnabled()) {
//            EventsStatistic.createBarChart();
//        } else if (!EventsStatistic.btnPieChart.isEnabled()) {
//            EventsStatistic.createPieChart();
//        } else if (!EventsStatistic.btnLineChart.isEnabled()) {
//            EventsStatistic.createLineChart();
//        }
    }
    static ProdFeature productionFeaturePan;
    static JDialog parent;
}
