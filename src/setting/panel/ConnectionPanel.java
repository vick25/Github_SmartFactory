package setting.panel;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.pane.CollapsiblePane;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

public class ConnectionPanel extends javax.swing.JPanel {

    public ConnectionPanel(AbstractDialogPage page) {
        this.page = page;
        initComponents();
        ConnectDB.collapsiblePaneProperties(collapsiblePane1);
        initValues();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        collapsiblePane1 = new com.jidesoft.pane.CollapsiblePane();
        serverIPAddress = new com.jidesoft.field.IPTextField();

        collapsiblePane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        collapsiblePane1.setTitle("Server IP Address");
        collapsiblePane1.setFocusable(false);

        serverIPAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        serverIPAddress.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                serverIPAddressStateChanged(evt);
            }
        });

        javax.swing.GroupLayout collapsiblePane1Layout = new javax.swing.GroupLayout(collapsiblePane1.getContentPane());
        collapsiblePane1.getContentPane().setLayout(collapsiblePane1Layout);
        collapsiblePane1Layout.setHorizontalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(serverIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        collapsiblePane1Layout.setVerticalGroup(
            collapsiblePane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serverIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(collapsiblePane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void serverIPAddressStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_serverIPAddressStateChanged
        if (initValues) {
            page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
        }
    }//GEN-LAST:event_serverIPAddressStateChanged

    private void initValues() {
        initValues = false;
        serverIPAddress.setText(ConnectDB.pref.get(SettingKeyFactory.Connection.SERVERIPADDRESS, ConnectDB.serverIP));
        initValues = true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jidesoft.pane.CollapsiblePane collapsiblePane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static com.jidesoft.field.IPTextField serverIPAddress;
    // End of variables declaration//GEN-END:variables
    private AbstractDialogPage page;
    private boolean initValues;
}
