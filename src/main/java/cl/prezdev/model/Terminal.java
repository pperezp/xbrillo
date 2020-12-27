package cl.prezdev.model;

import cl.prezdev.exceptions.CommandNotFoundException;
import cl.prezdev.exceptions.PasswordRootIsNotCorrectException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Terminal {

    // brillo -S 45
    public static String executeAsRoot(String command, String rootPassword) throws CommandNotFoundException, IOException, PasswordRootIsNotCorrectException {
        System.out.println("Command to execute: " + command);
        StringBuilder commandResult = new StringBuilder();

        InputStreamReader input;
        OutputStreamWriter output;

        String[] processParams = new String[]{"/bin/bash", "-c", "/usr/bin/sudo -S " + command + " 2>&1"};
        Process process = new ProcessBuilder(processParams).start();

        output = new OutputStreamWriter(process.getOutputStream());
        input = new InputStreamReader(process.getInputStream());

        int bytes;
        char[] buffer = new char[1024];
        while ((bytes = input.read(buffer, 0, 1024)) != -1) {
            if (bytes == 0) {
                continue;
            }

            String commandOutput = String.valueOf(buffer, 0, bytes);
            System.out.println(commandOutput);

            if (commandOutput.contains("[sudo] password")) {
                System.out.println("Trying with [" + rootPassword + "] password");
                output.write(rootPassword.toCharArray());
                output.write('\n');
                output.flush();
            } else {
                commandResult.append(commandOutput);
            }
        }

        output.close();
        input.close();
        process.destroyForcibly();

        if(commandResult.toString().contains("command not found")){
            System.out.println("throw new CommandNotFoundException");
            throw new CommandNotFoundException();
        }

        if(commandResult.toString().contains("Sorry, try again")){
            System.out.println("throw new PasswordRootIsNotCorrectException");
            throw new PasswordRootIsNotCorrectException();
        }

        return commandResult.toString();
    }
}
