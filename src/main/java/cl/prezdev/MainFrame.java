package cl.prezdev;

import cl.prezdev.events.RequestRootPasswordEvent;
import cl.prezdev.events.BrightSliderMouseMotionListener;
import cl.prezdev.exceptions.CancelEnterRootPasswordException;
import cl.prezdev.exceptions.CannotCreateFileException;
import cl.prezdev.exceptions.CommandNotFoundException;
import cl.prezdev.exceptions.PasswordRootIsNotCorrectException;
import cl.prezdev.model.Config;
import cl.prezdev.model.PasswordInputPanel;
import cl.prezdev.model.Terminal;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame implements RequestRootPasswordEvent {
    private JPanel containerPanel;
    private JSlider brightSlider;
    private JLabel percentageLabel;
    private PasswordInputPanel passwordInputPanel;
    private final int MAXIMUM = 100000;
    private final int DIVIDER = 1000;

    private Config config;

    public MainFrame(){
        passwordInputPanel = new PasswordInputPanel();

        loadConfig();
        init();

        brightSlider.addMouseMotionListener(new BrightSliderMouseMotionListener(percentageLabel, brightSlider, DIVIDER));
    }

    private void loadConfig() {
        try {
            config = new Config(this);
        } catch (CannotCreateFileException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void init() {
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
                requestRootPasswordEvent(config);
            }
        }
    }

    public Container getContainerPanel() {
        return this.containerPanel;
    }

    @Override
    public void requestRootPasswordEvent(Config config) {
        passwordInputPanel = new PasswordInputPanel();

        try {
            String rootPassword = passwordInputPanel.showPasswordDialog("Root password: ");
            config.setRootPassword(rootPassword);
        } catch (CancelEnterRootPasswordException e) {
            System.exit(1);
        }
    }
}
