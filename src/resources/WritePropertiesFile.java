package resources;

/**
 *
 * @author Victor Kadiata
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartfactoryV2.ConnectDB;

public class WritePropertiesFile {

//    String file = getClass().getResource("/resources/myApp.properties").toString();
//    String workingDir = System.getProperty("user.dir");

    public WritePropertiesFile(String running, String IPAddress, String timeToQuery) {
        Properties props = new Properties();
        OutputStream out = null;
        if (!"open".equalsIgnoreCase(running) && !"127.0.0.1".equals(ConnectDB.serverIP)) {
            try {
                URL resource = this.getClass().getClassLoader().getResource("resources/smfProperties.properties");
                File f = new File(resource.toURI());
//                FileInputStream input = new FileInputStream(file);
//                File f = new File(ConnectDB.WORKINGDIR + File.separator + "src\\resources\\smfProperties.properties");
                if (f.exists()) {
                    props.load(new FileReader(f));
                    //Change the values here
                    props.setProperty("running", running);
                    props.setProperty("timetoquery", timeToQuery);
                    props.setProperty("ipAddress", ConnectDB.serverIP);
                } else {
                    //Set default values?
                    props.setProperty("running", "open");
                    props.setProperty("timetoquery", "5m");
                    props.setProperty("delay", "2s");
                    props.setProperty("ipAddress", "127.0.0.1");
                    f.createNewFile();
                }
                out = new FileOutputStream(f);
                props.store(out, "This is the property file for the smartfactory reporting tool"
                        + " application.\nrunning: Check if application is running;\n"
                        + "timetoquery: Repeat for every given time.\n"
                        + "delay: Start after 2 seconds for the first time.\n\nVictor Kadiata");
//                System.out.println(props.get("running"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException ex) {
                Logger.getLogger(WritePropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        System.out.println("IOException: Could not close myApp.properties output stream; " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //workingDir + File.separator + "src\\sqldump\\smartNewDump.sql"
//    public static void main(String[] args) {
//        new WritePropertiesFile("open", "127.0.0.1", "5m");
//        /*Results after first run:
//
//         #This is an optional header comment string
//         #Thu Feb 28 13:04:11 CST 2013
//         ServerPort=8080
//         ServerAddress=DullDefault
//         ThreadCount=456
//         */
//
//        /*Results after second run (and each subsequent run):
//
//         #This is an optional header comment string
//         #Thu Feb 28 13:04:49 CST 2013
//         ServerPort=8080
//         ServerAddress=ThatNewCoolValue
//         ThreadCount=456
//         */
//    }
}
