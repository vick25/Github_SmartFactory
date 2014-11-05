package productionQuickView;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;
import smartfactoryV2.ConnectDB;

public class QuickViewFeature extends javax.swing.JPanel {

    public JTextField getTxtGreen() {
        return txtGreen;
    }
    
    public JTextField getTxtAmberLess() {
        return txtAmberLess;
    }

    public JTextField getTxtAmberMore() {
        return txtAmberMore;
    }

    public JTextField getTxtRed() {
        return txtRed;
    }

    public QuickViewFeature(AbstractDialogPage page, JDialog parent) {
        this._page = page;
        this._parent = parent;
        initComponents();
        ConnectDB.collapsiblePaneProperties(collapsiblePane1);
        PlainDocument doc = (PlainDocument) txtAmberLess.getDocument();
        doc.setDocumentFilter(new MyIntFilter());

        doc = (PlainDocument) txtGreen.getDocument();
        doc.setDocumentFilter(new MyIntFilter());
        
        initValues();
//        txtShiftsMaxValue.getDocument().addDocumentListener(new DocumentListener() {
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
//            }
//        });
//        this.jScrollPane1.requestFocus();
//        spFlagTime.getEditor().get
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        btnRestore = new javax.swing.JButton();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAmberLess = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAmberMore = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRed = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtGreen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        btnRestore.setText("Restore Default");
        btnRestore.setFocusable(false);
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        collapsiblePane1.setBackground(new java.awt.Color(255, 255, 255));
        collapsiblePane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane1.setTitle("Targets Percentage");
        collapsiblePane1.setFocusable(false);

        jLabel1.setBackground(new java.awt.Color(19, 136, 8));
        jLabel1.setForeground(new java.awt.Color(19, 136, 8));
        jLabel1.setText("Green");

        jLabel2.setBackground(new java.awt.Color(255, 194, 0));
        jLabel2.setForeground(new java.awt.Color(255, 194, 0));
        jLabel2.setText("Amber");

        jLabel4.setBackground(new java.awt.Color(255, 0, 0));
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Red");

        jLabel3.setText("production value >=");

        txtAmberLess.setText("50");

        jLabel5.setText("% of target <= production value <");

        txtAmberMore.setText("75");
        txtAmberMore.setFocusable(false);

        jLabel6.setText("% of target");

        txtRed.setText("50");
        txtRed.setFocusable(false);

        jLabel7.setText("production value <");

        jLabel8.setText("% of target");

        txtGreen.setText("75");

        jLabel9.setText("% of target");

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsiblePane1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRed, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addGroup(collapsiblePane1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(collapsiblePane1Layout.createSequentialGroup()
                        .addComponent(txtAmberLess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmberMore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        collapsiblePane1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAmberLess, txtAmberMore, txtRed});

        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtAmberLess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmberMore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(txtRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(collapsiblePane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRestore)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        try {
            if (JOptionPane.showConfirmDialog(this, "Would you like to restore default features ?", "Settings", 0) == 0) {
                txtAmberLess.setText("50");
                txtGreen.setText("75");
                txtAmberMore.setText(txtGreen.getText());
                txtRed.setText(txtAmberLess.getText());
                ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.AMBERLESS, txtAmberLess.getText());
                ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.AMBERMORE, txtAmberMore.getText());
                ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.RED, txtRed.getText());
                ConnectDB.pref.put(QuickViewKeyFactory.QuickViewKeys.GREEN, txtGreen.getText());
                _parent.dispose();
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void initValues() {
        txtGreen.setText(ConnectDB.pref.get(QuickViewKeyFactory.QuickViewKeys.GREEN, "75"));
        txtAmberLess.setText(ConnectDB.pref.get(QuickViewKeyFactory.QuickViewKeys.AMBERLESS, "50"));
        txtAmberMore.setText(ConnectDB.pref.get(QuickViewKeyFactory.QuickViewKeys.AMBERMORE, txtGreen.getText()));
        txtRed.setText(ConnectDB.pref.get(QuickViewKeyFactory.QuickViewKeys.RED, txtAmberLess.getText()));
    }

    private class MyIntFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
            txtAmberMore.setText(txtGreen.getText());
            txtRed.setText(txtAmberLess.getText());
            _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
        }

        private boolean test(String text) {
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
            }
            txtAmberMore.setText(txtGreen.getText());
            txtRed.setText(txtAmberLess.getText());
            _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // warn the user and don't allow the insert
            }
            txtAmberMore.setText(txtGreen.getText());
            txtRed.setText(txtAmberLess.getText());
            _page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestore;
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAmberLess;
    private javax.swing.JTextField txtAmberMore;
    private javax.swing.JTextField txtGreen;
    private javax.swing.JTextField txtRed;
    // End of variables declaration//GEN-END:variables
    private final AbstractDialogPage _page;
    private final JDialog _parent;
}
