package net.askearly.views;

import net.askearly.db.SQLiteConnectionPoolManager;
import net.askearly.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiApp {

    private final Settings settings;

    public GuiApp(Settings settings) {
        this.settings = settings;
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle(settings.getAppTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(createJTabbedPane(), BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SQLiteConnectionPoolManager.closeConnection();
            }
        });
        frame.setVisible(true);
    }

    private JTabbedPane createJTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add(settings.getProperties().getProperty("tab.notes"), new NoteTab(settings));

        return tabbedPane;
    }
}
