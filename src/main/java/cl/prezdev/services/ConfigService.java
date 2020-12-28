package cl.prezdev.services;

import cl.prezdev.events.RequestRootPasswordEvent;
import cl.prezdev.exceptions.CancelEnterRootPasswordException;
import cl.prezdev.exceptions.CannotCreateFileException;
import cl.prezdev.model.Config;
import cl.prezdev.model.PasswordInputPanel;

import javax.swing.*;

public class ConfigService implements RequestRootPasswordEvent {
    private Config config;
    private PasswordInputPanel passwordInputPanel;

    public ConfigService(){
        this.passwordInputPanel = new PasswordInputPanel();
        init();
    }

    public void init() {
        try {
            config = new Config(this);
        } catch (CannotCreateFileException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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

    public Config getConfig() {
        return config;
    }
}
