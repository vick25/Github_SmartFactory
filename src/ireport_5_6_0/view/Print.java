package ireport_5_6_0.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.Locale;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class Print extends javax.swing.JFrame {

    public Print(InputStream is, boolean isXMLFile) throws JRException {
        this(is, isXMLFile, true);
    }

    public Print(InputStream is, boolean isXMLFile, boolean isExitOnClose) throws JRException {
        this(is, isXMLFile, isExitOnClose, null);
    }

    public Print(InputStream is, boolean isXMLFile, boolean isExitOnClose, Locale locale) throws JRException {
        if (locale != null) {
            setLocale(locale);
        }
        this.isExitOnClose = isExitOnClose;
        initComponents();
        setIconImage(new javax.swing.ImageIcon(
                getClass().getResource("/images/icons/fileprint(21).png")).getImage());
        this.setSize(new Dimension(769, 578));
        this.setLocationRelativeTo(null);
        this.viewer = new JRViewer(is, isXMLFile, locale);
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
    }

    public Print(java.awt.Frame parent, JasperPrint jasperPrint) {
        this(parent, jasperPrint, true);
    }

    public Print(java.awt.Frame parent, JasperPrint jasperPrint, boolean isExitOnClose) {
        this(parent, jasperPrint, isExitOnClose, null);
    }

    public Print(java.awt.Frame parent, JasperPrint jasperPrint, boolean isExitOnClose, Locale locale) {
        if (locale != null) {
            setLocale(locale);
        }
        this.isExitOnClose = isExitOnClose;
        initComponents();
        this.setSize(new Dimension(769, 578));
        this.setLocationRelativeTo(parent);
        this.viewer = new JRViewer(jasperPrint, locale);
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(":::  Preview And Print  ::: ");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/icons/fileprint(21).png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (this.isExitOnClose) {
            System.exit(0);
        } else {
            this.setVisible(false);
            this.viewer.clear();
            this.viewer = null;
            this.getContentPane().removeAll();
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    public void setZoomRatio(float zoomRatio) {
        viewer.setZoomRatio(zoomRatio);
    }

    public void setFitWidthZoomRatio() {
        viewer.setFitWidthZoomRatio();
    }

    public void setFitPageZoomRatio() {
        viewer.setFitPageZoomRatio();
    }

    public static void viewReport(java.awt.Frame parent, JasperPrint jasperPrint) {
        viewReport(parent, jasperPrint, true, null);
    }

    public static void viewReport(java.awt.Frame parent, JasperPrint jasperPrint, boolean isExitOnClose) {
        viewReport(parent, jasperPrint, isExitOnClose, null);
    }

    public static void viewReport(java.awt.Frame parent, JasperPrint jasperPrint, boolean isExitOnClose, Locale locale) {
        Print impression = new Print(parent, jasperPrint, isExitOnClose, locale);
        impression.setVisible(true);
    }

    public static void viewReport(InputStream is, boolean isXMLFile) throws JRException {
        viewReport(is, isXMLFile, true, null);
    }

    public static void viewReport(InputStream is, boolean isXMLFile, boolean isExitOnClose) throws JRException {
        viewReport(is, isXMLFile, isExitOnClose, null);
    }

    public static void viewReport(InputStream is, boolean isXMLFile, boolean isExitOnClose, Locale locale)
            throws JRException {
        Print impression = new Print(is, isXMLFile, isExitOnClose, locale);
        impression.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

    private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
    private JRViewer viewer = null;
    private boolean isExitOnClose = true;
}
