package net.askearly;

import net.askearly.settings.Settings;
import net.askearly.views.GuiApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Settings settings;

    static void main(String[] args) {
        settings = new Settings();
        settings.init();

        logger.info("Program called with arguments: {}", Arrays.toString(args));

        if ((args.length == 1 && args[0].equals("--flyway")) ||
                !new File(settings.getDatabaseProperties().getProperty("database.file")).exists()) {
            Flyway flyway = Flyway.configure()
                    .dataSource(settings.getDatabaseProperties().getProperty("datasource.url"),
                            null,
                            null)
                    .locations("classpath:db/migrations")
                    .load();
            flyway.migrate();
        }
        SwingUtilities.invokeLater(Main::run);
    }

    static void run() {
        GuiApp guiApp = new GuiApp(settings);
        guiApp.init();
    }
}
