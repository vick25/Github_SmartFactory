package serverService;

import java.io.IOException;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class Exec {
    
    public static void main(String[] args) throws IOException {
        String filePath = ConnectDB.findFileOnClassPath(new StringBuilder("/").append("build").append("/").
                append("classes").append("/").append("bin").append("/").
                append("myService.exe").toString()).getAbsolutePath();
        System.out.println(filePath);
        String[] commands = new String[]{"cmd.exe", "/c", "runas /profile /savecred /user:administrator" + filePath};
        Runtime.getRuntime().exec(commands);
    }
}
