package cl.prezdev.services;

import cl.prezdev.exceptions.CommandNotFoundException;
import cl.prezdev.exceptions.PasswordRootIsNotCorrectException;
import cl.prezdev.model.Config;
import cl.prezdev.model.Terminal;

import javax.swing.*;
import java.io.IOException;

public class BrightService {
    private ConfigService configService;

    private final int MAXIMUM = 100000;
    public static final int DIVIDER = 1000;

    private final JSlider brightSlider;
    private final JLabel percentageLabel;
    private final Config config;

    public BrightService(JSlider brightSlider, JLabel percentageLabel){
        this.brightSlider = brightSlider;
        this.percentageLabel = percentageLabel;
        this.configService = new ConfigService();
        this.config = configService.getConfig();
    }

    public void init(){
        brightSlider.setMaximum(MAXIMUM);

        while(true){
            try {
                String rootPassword = config.getRootPassword();
                String commandOutput = Terminal.executeAsRoot("brillo -G", rootPassword).trim();
                Float currentBright = Float.parseFloat(commandOutput);

                int value = (int) (currentBright * DIVIDER);

                brightSlider.setValue(value);
                percentageLabel.setText((brightSlider.getValue() / DIVIDER)+ "%");

                break;
            } catch (CommandNotFoundException commandNotFoundException) {
                JOptionPane.showMessageDialog(null, "Install \"brillo\" in your operating system");
                System.exit(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                break;
            } catch (PasswordRootIsNotCorrectException e) {
                configService.requestRootPasswordEvent(config);
            }
        }
    }
}
