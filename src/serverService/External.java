package serverService;

import java.io.IOException;
import java.util.Map;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class External {

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = ConnectDB.findFileOnClassPath(new StringBuilder("/").append("build").append("/").
                append("classes").append("/").append("bin").append("/").
                append("myService.exe").toString()).getAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "\"DummyTitle\"",
                filePath, "//IS//MySMFService", "--Install=" + filePath, "--Description=SmartFactory Report Service",
                "--Jvm=auto", "--Classpath=" + filePath.substring(0, filePath.length() - 18),
                "--StartMode=jvm", "--StartClass=" + filePath.substring(0, filePath.length() - 18)
                + "/serverService.SMFServerService",
                "--StartMethod=windowsService", "--StartParams=start", "--StopMode=jvm",
                "--StopClass=" + filePath.substring(0, filePath.length() - 18)
                + "/serverService.SMFServerService",
                "--StopMethod=windowsService", "--StopParams=stop", "--LogPath=" + filePath.substring(0, filePath.length() - 18)
                + "/logs",
                "--StdOutput=auto", "--StdError=auto");
        Map<String, String> env = pb.environment();
        
        Process process = pb.start();

//        int result = process.waitFor();
//        System.out.println(process.exitValue());
//        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//
//        System.out.printf("Output of running %s is:", Arrays.toString(args));
//
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }
    }
}
