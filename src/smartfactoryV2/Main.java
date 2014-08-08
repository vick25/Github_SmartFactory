package smartfactoryV2;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
//import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Victor Kadiata
 */
public class Main {

    public static void main(String[] args) {
        com.jidesoft.utils.Lm.verifyLicense("OSFAC", "OSFAC-DMT", "vx1xhNgC4CtD2SQc.kC5mp99mO0Bs1d2");
        com.jidesoft.utils.Lm.verifyLicense("iDeaX Africa", "SmartFactory", "Jhd8iWcEkRThgPAlv7OTh0GXMdo1GgL1");
//        BasicConfigurator.configure();
        Thread th = new Thread() {

            @Override
            public void run() {
                try {//run an empty report before starting
                    JasperFillManager.fillReport(getClass().getResourceAsStream("/jasper/test.jasper"),
                            null, new JREmptyDataSource());
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            }
        };
        th.start();
        new Main().doit();
//        try {
//            System.setErr(new PrintStream(new FileOutputStream(System.getProperty("user.home") + "/error.log")));
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
    }

    public void doit() {
        try {
            Socket clientSocket = new Socket(ConnectDB.serverIP, ConnectDB.PORTMAINSERVER);
            JOptionPane.showMessageDialog(null, "An instance of this application is already running.",
                    "SmartFactory Report Tool", JOptionPane.ERROR_MESSAGE);
            System.out.println("*** Already running! ***");
            System.exit(0);
        } catch (IOException | HeadlessException e) {
            new JustOneServer().start();
        }
        try {
            new SplashScreen().setVisible(true);
//            System.out.print(".");
//            Thread.sleep(5 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
