package balloonTip;

import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.tooltip.BalloonShape;
import com.jidesoft.tooltip.BalloonTip;
import com.jidesoft.tooltip.shapes.RoundedRectangularBalloonShape;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Victor Kadiata
 */
public class BalloonTipDemo extends BalloonTip {

    private final BalloonShape _shape = new RoundedRectangularBalloonShape();
    private BalloonTip _balloonTip;
    private final int x, y;
    String text = "";
    JComponent _comp;

    public BalloonTipDemo(JComponent c, String toolTipText) {
        _comp = c;
        _comp.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                hideToolTip();
            }
        });
        if (c instanceof JPanel) {
            x = _comp.getWidth() / 2;
        } else {
            x = _comp.getWidth() / 4;
        }
        y = _comp.getHeight() / 2;
        text = toolTipText;
        toggleToolTip();
    }

    private void toggleToolTip() {
        if (_balloonTip != null && _balloonTip.isVisible()) {
            hideToolTip();
        } else {
            showToolTip();
        }
    }

    private void showToolTip() {
        _balloonTip = new BalloonTip(createToolTipPanelContent()) {
            @Override
            protected void customizePopup(JidePopup popup) {
                super.customizePopup(popup);
                popup.addExcludedComponent(getOptionsPanel());
            }
        };
        if (_shape != null && _shape instanceof RoundedRectangularBalloonShape) {
            ((RoundedRectangularBalloonShape) _shape).setArrowLeftRatio(57 / 100.0);
            ((RoundedRectangularBalloonShape) _shape).setArrowRightRatio(20 / 100.0);
            ((RoundedRectangularBalloonShape) _shape).setVertexPosition(100 / 100.0);
            ((RoundedRectangularBalloonShape) _shape).setBalloonSizeRatio(73 / 100.0);
            ((RoundedRectangularBalloonShape) _shape).setCornerSize(6);
            ((RoundedRectangularBalloonShape) _shape).setPosition(SwingConstants.BOTTOM);
        }
        _balloonTip.setPreferredSize(new Dimension(600, 600));
        _balloonTip.setShadowStyle(null);
        _balloonTip.setBalloonShape(_shape);
        _balloonTip.show(this._comp, x, y);
    }

    private void hideToolTip() {
        if (_balloonTip != null) {
            _balloonTip.hide();
            _balloonTip = null;
        }
    }

    public Component getOptionsPanel() {
        JPanel _optionsPanel = new JPanel();
        _optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        _optionsPanel.setLayout(new JideBoxLayout(_optionsPanel, JideBoxLayout.Y_AXIS, 6));
        if (_balloonTip != null) {
            hideToolTip();
        }
        return _optionsPanel;
    }

    private JPanel createToolTipPanelContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        fieldPanel.add(new JLabel("Tip: "), BorderLayout.BEFORE_LINE_BEGINS);
        fieldPanel.add(new JLabel(this.text));
        panel.add(fieldPanel, BorderLayout.AFTER_LAST_LINE);
        panel.setOpaque(false);
        return panel;
    }
}
