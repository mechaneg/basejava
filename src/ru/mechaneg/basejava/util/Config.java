package ru.mechaneg.basejava.util;


import java.io.*;
import java.util.Properties;

public class Config {
    private static Config INSTANCE = new Config();

    private final File PROPS_FILE = new File("./config/resumes.properties");
    private Properties props = new Properties();

    public String getStorageDir() {
        return props.getProperty("storage.dir");
    }
    public String getDbUrl() {
        return props.getProperty("db.url");
    }
    public String getDbUser() {
        return props.getProperty("db.user");
    }
    public String getDbPassword() {
        return props.getProperty("db.password");
    }

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS_FILE)) {
            props.load(is);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to open properties files", ex);
        }
    }
}
