package net.askearly.views;

import net.askearly.db.SQLiteConnectionPoolManager;
import net.askearly.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiApp {

    private final Settings settings;
    private JFrame frame;

    public GuiApp(Settings settings) {
        this.settings = settings;
    }

    public void init() {
        frame = new JFrame();
        frame.setTitle(settings.getAppTitle());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(createJMenuBar());
        frame.setLayout(new BorderLayout());
        frame.add(createJTabbedPane(), BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    SQLiteConnectionPoolManager.closeConnection();
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        frame.setVisible(true);
    }

    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setMnemonic(KeyEvent.VK_X);
            exitMenuItem.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
            fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    private JTabbedPane createJTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add(settings.getProperties().getProperty("tab.notes"), new NoteTab(settings));

        return tabbedPane;
    }
}
