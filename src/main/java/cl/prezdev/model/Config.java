package cl.prezdev.model;

import cl.prezdev.events.RequestRootPasswordEvent;
import cl.prezdev.exceptions.CannotCreateFileException;

import java.io.*;
import java.util.Properties;

public class Config {
    private final String CONFIG_PATH = "config.properties";
    private final String ROOT_PASSWORD_KEY = "root.password";

    private final Properties properties;
    private final RequestRootPasswordEvent requestRootPasswordEvent;

    public static String ROOT_PASSWORD;

    public Config(RequestRootPasswordEvent requestRootPasswordEvent) throws CannotCreateFileException {
        this.properties = new Properties();
        this.requestRootPasswordEvent = requestRootPasswordEvent;

        checkConfigFile();
        load();
        checkRootPassword();
    }

    private void checkRootPassword() {
        if(!isRootPasswordExist()){
            requestRootPasswordEvent.requestRootPasswordEvent(this);
        }else{
            ROOT_PASSWORD = getRootPassword();
        }
    }

    private void checkConfigFile() throws CannotCreateFileException {
        if(!exist()){
            createFile();
        }
    }

    private void createFile() throws CannotCreateFileException {
        try {
            boolean created = new File(CONFIG_PATH).createNewFile();
            if(!created)
                throw new CannotCreateFileException("Cannot create " + CONFIG_PATH);
        } catch (IOException ioException) {
            throw new CannotCreateFileException(ioException.getMessage());
        }
    }

    private boolean exist() {
        return new File(CONFIG_PATH).exists();
    }

    private void load() {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRootPasswordExist() {
        String rootPassword = properties.getProperty(ROOT_PASSWORD_KEY);

        return rootPassword != null;
    }

    public void setRootPassword(String rootPassword) {
        properties.put(ROOT_PASSWORD_KEY, rootPassword);
        ROOT_PASSWORD = rootPassword;
        store();
    }

    private void store() {
        try {
            properties.store(new FileOutputStream(CONFIG_PATH), null);
        } catch (Exception e) {}
    }

    public String getRootPassword() {
        return properties.getProperty(ROOT_PASSWORD_KEY);
    }
}
