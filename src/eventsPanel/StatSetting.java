package eventsPanel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.dialog.PageList;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideSwingUtilities;
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
import setting.SettingIconsFactory;
import smartfactoryV2.ConnectDB;

public class StatSetting extends MultiplePageDialog {

    public StatSetting(JFrame owner, String title) throws HeadlessException {
        super(owner, title);
//        parent = this;
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
        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
        setDefaultCancelAction(cancelAction);
        setDefaultAction(okAction);
        this.getApplyButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
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
        StatSetting dialog = new StatSetting(null, "Setting");
        dialog.setIconImage(SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.DIALOGICON).getImage());
        dialog.setStyle(MultiplePageDialog.ICON_STYLE);
        PageList model = new PageList();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // setup model
        OptionPageGeneral panel1 = new OptionPageGeneral("Chart Features", SettingIconsFactory.getImageIcon(SettingIconsFactory.Options.GENERAL));
        model.append(panel1);
        dialog.setPageList(model);
        dialog.pack();
        JideSwingUtilities.globalCenterWindow(dialog);
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
            chartFeaturePan = new ChartFeature(this, parent);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(chartFeaturePan, BorderLayout.CENTER);
        }
    }

    public static void main(String[] argv) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        showOptionsDialog();
    }

    private void applyChartFeature() {
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBLineLabel, ChartFeature.RBLineLabel.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBSimpleLabel, ChartFeature.RBSimpleLabel.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBNoLabel, ChartFeature.RBNoLabel.isSelected());
        ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.SLAngle, ChartFeature.SLAngle.getValue());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBExplodedSegment, ChartFeature.ChBExplodedSegment.isSelected());

        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.BarChBVLine, ChartFeature.BarChBVLine.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.BarChBHLine, ChartFeature.BarChBHLine.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.LineChBVLine, ChartFeature.LineChBVLine.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.LineChBHLine, ChartFeature.LineChBHLine.isSelected());
        setColorFromKey(ChartFeature.CBColor.getSelectedColor(), StatKeyFactory.ChartFeatures.CBColor);
        setColorFromKey(ChartFeature.CBColor2.getSelectedColor(), StatKeyFactory.ChartFeatures.CBColor2);
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RandomColor, ChartFeature.RandomColor.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.OneColor, ChartFeature.OneColor.isSelected());
        ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.SPLineWidth, Integer.parseInt(ChartFeature.SPLineWidth.getValue().toString()));

        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBFlat, ChartFeature.RBFlat.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RBRaised, ChartFeature.RBRaised.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.RB3D, ChartFeature.RB3D.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBShadow, ChartFeature.ChBShadow.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBRollover, ChartFeature.ChBRollover.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.CHKOUTLINE, ChartFeature.ChBOutline.isSelected());
        ConnectDB.pref.putBoolean(StatKeyFactory.ChartFeatures.ChBSelectionOutline, ChartFeature.ChBSelectionOutline.isSelected());
        ConnectDB.pref.putInt(StatKeyFactory.ChartFeatures.CMBTIME, ChartFeature.cmbTime.getSelectedIndex());
        if (!EventsStatistic.btnBarChart.isEnabled()) {
            EventsStatistic.createBarChart();
        } else if (!EventsStatistic.btnPieChart.isEnabled()) {
            EventsStatistic.createPieChart();
        } else if (!EventsStatistic.btnLineChart.isEnabled()) {
            EventsStatistic.createLineChart();
        }
    }

    private void setColorFromKey(Color color, String key) {
        ConnectDB.pref.put(key, color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
    }
    static ChartFeature chartFeaturePan;
    static JDialog parent;
}
