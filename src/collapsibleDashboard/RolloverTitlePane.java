package collapsibleDashboard;
/*
 * @(#)RolloverTitlePane.java 7/25/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.NullPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RolloverTitlePane extends NullPanel {

    private final JLabel _messageLabel;
    private final JPanel _buttonPanel;
    private final CardLayout _layout;

    public RolloverTitlePane(CollapsiblePane pane) {
        _layout = new CardLayout();

        setLayout(_layout);
        _messageLabel = new JLabel("");
        _messageLabel.setForeground(null);
        _messageLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        add(_messageLabel, "Message");
        _buttonPanel = new NullPanel();
        _buttonPanel.setLayout(new JideBoxLayout(_buttonPanel, JideBoxLayout.X_AXIS));
        _buttonPanel.add(Box.createGlue(), JideBoxLayout.VARY);
        add(_buttonPanel, "Buttons");
        pane.addPropertyChangeListener("rollover", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (Boolean.TRUE.equals(evt.getNewValue())) {
                    showButtons();
                } else {
                    showMessage();
                }
            }
        });
        JideSwingUtilities.setOpaqueRecursively(RolloverTitlePane.this, false);
    }

    public void setMessage(String message) {
        _messageLabel.setText(message);
    }

    public void showMessage() {
        _layout.show(this, "Message");
    }

    public void showButtons() {
        _layout.show(this, "Buttons");
    }

    public void addButton(AbstractButton button) {
        _buttonPanel.add(button);
    }

    public void addButton(AbstractButton button, int index) {
        _buttonPanel.add(button, index);
    }

    public void removeButton(AbstractButton button) {
        _buttonPanel.remove(button);
    }

    public void removeButton(int index) {
        _buttonPanel.remove(index);
    }
}
