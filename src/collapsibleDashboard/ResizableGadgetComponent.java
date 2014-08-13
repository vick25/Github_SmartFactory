package collapsibleDashboard;
/*
 * @(#)ResizableGadgetComponent.java 6/23/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

import com.jidesoft.dashboard.Gadget;
import com.jidesoft.dashboard.GadgetComponent;
import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.pane.event.CollapsiblePaneEvent;
import com.jidesoft.pane.event.CollapsiblePaneListener;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.plaf.basic.Painter;
import com.jidesoft.swing.JideCursors;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ResizableGadgetComponent extends JPanel implements GadgetComponent {

    private final GadgetComponent _delegate;

    public <G extends Component & GadgetComponent> ResizableGadgetComponent(final G delegate) {
        this._delegate = delegate;

        setLayout(new BorderLayout());
        setOpaque(false);

        add(delegate);

        final JPanel resizePanel = new JPanel() {
            private Painter painter;

            @Override
            protected void paintComponent(Graphics g) {
                if (painter != null) {
                    painter.paint(this, g, new Rectangle(1, 1, getWidth() - 2, getHeight() - 2),
                            SwingConstants.HORIZONTAL, 0);
                }
            }

            @Override
            public void updateUI() {
                super.updateUI();
                painter = (Painter) UIDefaultsLookup.get("JideSplitPaneDivider.gripperPainter");
            }
        };
        resizePanel.setPreferredSize(new Dimension(8, 8));
        resizePanel.setOpaque(false);
        resizePanel.setCursor(JideCursors.getPredefinedCursor(JideCursors.VSPLIT_CURSOR));

        if (delegate instanceof CollapsiblePaneGadget) {
            delegate.addPropertyChangeListener(CollapsiblePane.COLLAPSED_PROPERTY,
                    new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            resizePanel.setVisible(!(Boolean) evt.getNewValue());
                        }
                    });
        }
        MouseMotionListener adapter = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                ResizableGadgetComponent component = ResizableGadgetComponent.this;
                Point point = SwingUtilities.convertPoint(resizePanel, e.getPoint(), component.getParent());
                Dimension size = component.getPreferredSize();
                size.height = point.y - component.getY();
                size.height = Math.max(component.getMinimumSize().height, size.height);
                size.height = Math.min(component.getMaximumSize().height, size.height);

                if (delegate instanceof CollapsiblePaneGadget) {
                    JComponent content = ((CollapsiblePaneGadget) delegate).getContentPane();
                    size.height -= (delegate.getHeight() - content.getHeight());
                    size.width -= (delegate.getWidth() - content.getWidth());
                    content.setPreferredSize(size);
                    component.setPreferredSize(null);
                    delegate.invalidate();
                } else {
                    component.setPreferredSize(size);
                }

                CollapsiblePaneGadget.repaintAncestors(component);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
        resizePanel.addMouseMotionListener(adapter);
        add(resizePanel, BorderLayout.SOUTH);
        delegate.addMouseListener(delegate.getGadget().getGadgetManager().getDragAndDropMouseInputListener());
        delegate.addMouseMotionListener(delegate.getGadget().getGadgetManager().getDragAndDropMouseInputListener());
        if (delegate instanceof CollapsiblePane) {
            ((CollapsiblePane) delegate).addCollapsiblePaneListener(new CollapsiblePaneListener() {
                @Override
                public void paneExpanding(CollapsiblePaneEvent event) {
                }

                @Override
                public void paneExpanded(CollapsiblePaneEvent event) {
                    ResizableGadgetComponent component = ResizableGadgetComponent.this;
                    component.setPreferredSize(null);
                    delegate.invalidate();
                    component.getParent().invalidate();
                    component.getParent().repaint();
                }

                @Override
                public void paneCollapsing(CollapsiblePaneEvent event) {
                }

                @Override
                public void paneCollapsed(CollapsiblePaneEvent event) {
                    ResizableGadgetComponent component = ResizableGadgetComponent.this;
                    component.setPreferredSize(null);
                    delegate.invalidate();
                    component.getParent().invalidate();
                    component.getParent().repaint();
                }
            });
        }
    }

    @Override
    public Gadget getGadget() {
        return _delegate.getGadget();
    }

    @Override
    public Map<String, String> getSettings() {
        return _delegate.getSettings();
    }

    @Override
    public void setSettings(Map<String, String> arg0) {
        _delegate.setSettings(arg0);
    }

    public GadgetComponent getActualGadgetComponent() {
        return _delegate;
    }
}
