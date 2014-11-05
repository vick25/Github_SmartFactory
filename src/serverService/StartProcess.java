package serverService;

import java.io.IOException;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class StartProcess {

    public static void main(String[] args) {
        try {
            String[] params = new String[18];
            String filePath = ConnectDB.findFileOnClassPath(new StringBuilder("/").append("build").append("/").
                    append("classes").append("/").append("bin").append("/").
                    append("myService.exe").toString()).getAbsolutePath();
            System.out.println(filePath);

            params[0] = " //IS//MySMFService";
            params[1] = " --Install=" + filePath;
            params[2] = " --Description=SmartFactory Report Service";
            params[3] = " --Jvm=auto";
            params[4] = " --Classpath=" + filePath.substring(0, filePath.length() - 18);
            params[5] = " --StartMode=jvm";
            params[6] = " --StartClass=" + filePath.substring(0, filePath.length() - 18)
                    + "/serverService.SMFServerService";
            params[7] = " --StartMethod=windowsService";
            params[8] = " --StartParams=start";
            params[9] = " --StopMode=jvm";
            params[10] = " --StopClass=" + filePath.substring(0, filePath.length() - 18)
                    + "/serverService.SMFServerService";
            params[11] = " --StopMethod=windowsService";
            params[12] = " --StopParams=stop";
            params[13] = " --LogPath=" + filePath.substring(0, filePath.length() - 18)
                    + "/logs";
            params[14] = " --StdOutput=auto";
            params[15] = " --StdError=auto";

            String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"", filePath};
            Process p = Runtime.getRuntime().exec(commands);
//            InputStream is = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while((line = br.readLine()) != null){
//                System.out.println(line);
//            }
//            System.out.println("Program Terminated");
            p.waitFor();
            System.out.println(p.exitValue());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
