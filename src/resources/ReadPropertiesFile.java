package resources;

/**
 *
 * @author Victor Kadiata
 */
import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertiesFile {

    public static void readConfig() throws Exception {
//        try {
        Properties pro = new Properties();
        String path = System.getProperty("user.dir") + "/src/resources/smfProperties.properties";
        pro.load(new FileInputStream(path));

        Constants.delay = pro.getProperty("delay");
        Constants.timetoquery = pro.getProperty("timetoquery");
        Constants.ipAddress = pro.getProperty("ipAddress");
        Constants.running = pro.getProperty("running");
//            Constants.setPassword = pro.getProperty("setPassword");
//            System.out.println(Constants.delay );
//            System.out.println(Constants.timetoquery );
//            System.out.println(Constants.ipAddress);
//            System.out.println(Constants.running);
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
    }

//    public static void main(String[]args) throws Exception{
//        ReadPropertiesFile.readConfig();
//    }
}
