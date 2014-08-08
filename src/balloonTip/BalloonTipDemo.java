package balloonTip;

import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.tooltip.BalloonShape;
import com.jidesoft.tooltip.BalloonTip;
import com.jidesoft.tooltip.ShadowStyle;
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

    private BalloonTip _balloonTip;
    private final BalloonShape _shape = new RoundedRectangularBalloonShape();
    private final int _position = SwingConstants.BOTTOM;
    private final ShadowStyle _shadowStyle = null;
    String text = "";
    int x, y;
    JComponent c;
    protected JPanel _optionsPanel = new JPanel();

    public BalloonTipDemo(JComponent c, String text) {
        this.c = c;
        this.c.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                hideToolTip();
            }
        });
        if (c instanceof JPanel) {
            x = this.c.getWidth() / 2;
        } else {
            x = this.c.getWidth() / 4;
        }
        y = this.c.getHeight() / 2;
        this.text = text;
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
        _balloonTip = new BalloonTip(createToolTipContent()) {
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
            ((RoundedRectangularBalloonShape) _shape).setPosition(_position);
        }
        _balloonTip.setBalloonShape(_shape);
        _balloonTip.setPreferredSize(new Dimension(600, 600));
        _balloonTip.setShadowStyle(_shadowStyle);
        _balloonTip.show(this.c, x, y);
    }

    private void hideToolTip() {
        if (_balloonTip != null) {
            _balloonTip.hide();
            _balloonTip = null;
        }
    }

    public Component getOptionsPanel() {
        _optionsPanel = new JPanel();
        _optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        _optionsPanel.setLayout(new JideBoxLayout(_optionsPanel, JideBoxLayout.Y_AXIS, 6));
        if (_balloonTip != null) {
            hideToolTip();
        }
        return _optionsPanel;
    }

    private JPanel createToolTipContent() {
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
