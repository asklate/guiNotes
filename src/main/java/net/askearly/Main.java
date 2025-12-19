package net.askearly;

import net.askearly.settings.Settings;
import net.askearly.views.GuiApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Settings settings;

    static void main() {
        settings = new Settings();
        settings.init();

        SwingUtilities.invokeLater(Main::run);
    }

    static void run() {
        GuiApp guiApp = new GuiApp(settings);
        guiApp.init();
    }
}
