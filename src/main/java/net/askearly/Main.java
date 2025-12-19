package net.askearly;

import net.askearly.settings.Settings;
import net.askearly.views.GuiApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.swing.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Settings settings;

    static void main(String[] args) {
        settings = new Settings();
        settings.init();

        if (args.length == 1 && args[0].equals("--flyway")) {
            Flyway flyway = Flyway.configure()
                    .dataSource(settings.getDatabaseProperties().getProperty("datasource.url"),
                            null,
                            null)
                    .locations("classpath:db/migrations")
                    .load();
            flyway.migrate();
        } else {
            SwingUtilities.invokeLater(Main::run);
        }
    }

    static void run() {
        GuiApp guiApp = new GuiApp(settings);
        guiApp.init();
    }
}
