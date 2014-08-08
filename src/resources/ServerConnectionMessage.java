package resources;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

/**
 *
 * @author Victor Kadiata
 */
public class ServerConnectionMessage extends javax.swing.JDialog {

    public ServerConnectionMessage(java.awt.Frame parent, boolean modal, String text) {
        super(parent, modal);
        initComponents();
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                btnCloseActionPerformed(null);
            }
        });
        lblMessage.setText("<html><p>Database connection "
                + "is lost: Please verify that the SmartFactory server connected @ "
                + "<font color=red><strong>127.0.0.1</strong></font>"
                + " on your local network is started,"
                + " and retry connecting ...</p>");
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        final int taskBarHeight = screenSize.height - winSize.height;
        height = (int) screenSize.getHeight();
        width = (int) screenSize.getWidth();
        time = new Timer(5, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step == (2 * taskBarHeight) + 73) {
                    time.stop();
                    time3.start();
                } else {
                    setLocation(width - 374, height - step);
                    step++;
                }
            }
        });
        time2 = new Timer(5, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step == 0) {
                    time2.stop();
                    dispose();
                } else {
                    setLocation(width - 374, height - step);
                    step--;
                }
            }
        });
        time3 = new Timer(700, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (close) {
                    time.stop();
                    time3.stop();
                    time2.start();
                }
            }
        });
        this.setLocation(width, height);
        time.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SmartFactory Server Connection");
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        lblMessage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMessage.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.setFocusable(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        close = true;
    }//GEN-LAST:event_btnCloseActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ServerConnectionMessage dialog = new ServerConnectionMessage(new javax.swing.JFrame(), true, "");
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        close = true;
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables
    Timer time, time2, time3;
    int height, width, step, delay;
    static boolean close;
}
