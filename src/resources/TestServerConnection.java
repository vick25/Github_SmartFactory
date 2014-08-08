package resources;

/**
 *
 * @author Victor Kadiata
 */
import com.jidesoft.plaf.LookAndFeelFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestServerConnection extends TimerTask {

    @Override
    public void run() {
        if ("open".equalsIgnoreCase(Constants.running)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                con = DriverManager.getConnection("jdbc:mysql://" + Constants.ipAddress
                        + ":3306/smartfactory?dontTrackOpenResources=true&allowMultiQueries=true", "root", "wnnr123");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                String text = "<html><p>Database connection "
                        + "is lost: Please verify that the SmartFactory server connected @ "
                        + "<font color=red><strong>" + Constants.ipAddress + "</strong></font>"
                        + " on your local network is started,"
                        + " and retry connecting ...</p>";
                new ServerConnectionMessage(null, true, text).setVisible(true);
                System.out.println(text);
                return;
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Email Sent Succesfully...");
    }
    Connection con = null;
}
