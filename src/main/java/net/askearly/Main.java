package net.askearly;

import net.askearly.settings.Settings;
import net.askearly.views.GuiApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    static void main() {
        Settings settings = new Settings();
        settings.init();

        GuiApp guiApp = new GuiApp(settings);
        guiApp.init();
    }
}
