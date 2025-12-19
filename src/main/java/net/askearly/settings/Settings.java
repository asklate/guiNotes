package net.askearly.settings;

import net.askearly.utils.PropertyUtils;

import java.util.Properties;

public class Settings {

    private Properties properties;
    private Properties databaseProperties;

    public Settings() {

    }

    public void init() {
        properties = PropertyUtils.getProperties("app.properties");
        databaseProperties = PropertyUtils.getProperties("db.properties");
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Properties getDatabaseProperties() {
        return this.databaseProperties;
    }
}
