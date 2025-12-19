package net.askearly.db;

import net.askearly.settings.Settings;

import java.util.Properties;

public class Database {

    private  final Settings settings;
    private final Properties databaseProperties;
    private final Properties properties;

    public Database(Settings settings) {
        this.settings = settings;
        this.databaseProperties = this.settings.getDatabaseProperties();
        this.properties = this.settings.getProperties();
    }
}
