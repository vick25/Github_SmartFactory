package resources;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class ReadPropertiesFile {

    private ReadPropertiesFile() {
    }

    public static void readConfig() throws Exception {
        String pathSeparator = File.separator;
        Properties properties = new Properties();
//        String path = System.getProperty("user.dir") + "/src/resources/smfProperties.properties";
//        InputStream is = CL.getResourceAsStream("resources/smfProperties.properties");
        File classpathFile = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
                append("classes").append(pathSeparator).append("resources").append(pathSeparator).
                append("smfProperties.properties").toString());
        properties.load(new FileReader(classpathFile));
//        Properties properties = new Properties();
////        String path = System.getProperty("user.dir") + "/src/resources/smfProperties.properties";
//        properties.load(ReadPropertiesFile.class.getResourceAsStream("/resources/smfProperties.properties"));

        Constants.delay = properties.getProperty("delay");
        Constants.timetoquery = properties.getProperty("timetoquery");
        Constants.ipAddress = properties.getProperty("ipAddress");
        Constants.running = properties.getProperty("running");
    }
//    private static final ClassLoader CL = ReadPropertiesFile.class.getClassLoader();
}
