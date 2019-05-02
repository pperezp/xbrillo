package xbrillo.model;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Terminal {

    public static String ROOT_PASSWORD;

    // brillo -S 45
    public static String execute(String command) throws Exception {
        if (ROOT_PASSWORD != null) {
            System.out.println("Command to execute: "+command);
            String result = "";
            
            InputStreamReader input;
            OutputStreamWriter output;

            //Create the process and start it.
            Process pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", "/usr/bin/sudo -S " + command + " 2>&1"}).start();
            output = new OutputStreamWriter(pb.getOutputStream());
            input = new InputStreamReader(pb.getInputStream());

            int bytes;
            char buffer[] = new char[1024];
            while ((bytes = input.read(buffer, 0, 1024)) != -1) {
                if (bytes == 0) {
                    continue;
                }
                //Output the data to console, for debug purposes
                String data = String.valueOf(buffer, 0, bytes);
                // Check for password request
                if (data.contains("[sudo] password")) {
                    // Here you can request the password to user using JOPtionPane or System.console().readPassword();
                    // I'm just hard coding the password, but in real it's not good.
                    char password[] = ROOT_PASSWORD.toCharArray();
                    output.write(password);
                    output.write('\n');
                    output.flush();
                    // erase password data, to avoid security issues.
                    Arrays.fill(password, '\0');
                }else{
                    result += data;
                }
            }
            
            return result;
        }else{
            throw new Exception("Por favor ingrese la password de usuario root: Terminal.PASSWORD = \"tu pass\"");
        }
    }
}
