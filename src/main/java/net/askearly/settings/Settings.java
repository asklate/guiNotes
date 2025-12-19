package net.askearly.settings;

import net.askearly.db.Database;
import net.askearly.utils.PropertyUtils;

import java.util.Properties;

public class Settings {

    private Properties properties;
    private Properties databaseProperties;
    private Database database;

    public Settings() {

    }

    public void init() {
        properties = PropertyUtils.getProperties("app.properties");
        databaseProperties = PropertyUtils.getProperties("db.properties");
        database = new Database(this);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Properties getDatabaseProperties() {
        return this.databaseProperties;
    }

    public Database getDatabase() {
        return this.database;
    }

    public String getAppTitle() {
        return properties.getProperty("app.title");
    }
}
