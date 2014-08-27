package resources;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Victor Kadiata
 */
public class ReadPropertiesFile {

    private ReadPropertiesFile() {
    }

    public static void readConfig() throws Exception {
        Properties properties = new Properties();
        String path = System.getProperty("user.dir") + "/src/resources/smfProperties.properties";
        properties.load(new FileInputStream(path));
//        Properties properties = new Properties();
////        String path = System.getProperty("user.dir") + "/src/resources/smfProperties.properties";
//        properties.load(ReadPropertiesFile.class.getResourceAsStream("/resources/smfProperties.properties"));

        Constants.delay = properties.getProperty("delay");
        Constants.timetoquery = properties.getProperty("timetoquery");
        Constants.ipAddress = properties.getProperty("ipAddress");
        Constants.running = properties.getProperty("running");
    }
}
