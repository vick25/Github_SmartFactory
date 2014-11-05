package serverservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import resources.Constants;
import resources.ReadPropertiesFile;

/**
 * SmartFactory Server Java Service
 *
 * @author Victor Kadiata
 */
public class SMFServerService {

    /**
     * Flag to know if this service 
     * instance has been stopped
     */
    static volatile boolean running = true;
    /**
     * Single static instance of the service class
     */
    private static final SMFServerService serviceInstance = new SMFServerService();
    private Connection con = null;
    private ServerConnectionMessage message = null;

    /**
     * @param args the command line arguments
     */
    public static void windowsService(String agrs[]) {
        String cmd = "start";
        if (agrs.length > 0) {
            cmd = agrs[0];
        }
        if ("start".equals(cmd)) {
            serviceInstance.start();
        } else {
            serviceInstance.stop();
        }
    }

    public void start() {
        running = false;
        System.out.println("My Service Started " + new java.util.Date());
        while (!running) {
            System.out.println("My Service Executing " + new java.util.Date());
            synchronized (this) {
                try {
                    ReadPropertiesFile.readConfig();//read the property file to get the time and delay for the schedule 
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://" + Constants.ipAddress
                                + ":3306/smartfactory?dontTrackOpenResources=true&allowMultiQueries=true", "root",
                                "wnnr123");
                    } catch (ClassNotFoundException ex) {
                        new ServerConnectionMessage(null, true, "<html><p>No driver"
                                + ex.getMessage() + "</p>").setVisible(true);
                    } catch (SQLException ex) {
                        con = null;
                        String text = "<html><p>Database connection "
                                + "is lost: Please verify that the SmartFactory server connected @ "
                                + "<font color=red><strong>" + Constants.ipAddress + "</strong></font>"
                                + " on your local network is started,"
                                + " and retry connecting ...</p>";
                        message = new ServerConnectionMessage(null, true, text);
                        message.setVisible(true);
                        return;
                    }
                    this.wait(120000);  // wait 2 minutes
                } catch (Exception ex) {
                    Logger.getLogger(SMFServerService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("My Service Finished " + new java.util.Date());
    }
    
    public static void main(String[] argv) {
        new SMFServerService().start();
    }

    public void stop() {
        running = true;
        synchronized (this) {
            this.notify();
        }
    }
}
