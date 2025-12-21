package net.askearly.views;

import net.askearly.db.SQLiteConnectionPoolManager;
import net.askearly.settings.Settings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuiApp {

    private final Settings settings;
    private JFrame frame;
    private JTabbedPane tabs;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss a");

    public GuiApp(Settings settings) {
        this.settings = settings;
    }

    public void init() {
        frame = new JFrame();
        frame.setTitle(settings.getAppTitle());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(createJMenuBar());
        frame.setLayout(new BorderLayout());
        frame.add(createJTabbedPane(), BorderLayout.CENTER);
        frame.add(createStatusBar(), BorderLayout.SOUTH);
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

    private JPanel createStatusBar() {
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(frame.getWidth(), 24));

        JLabel timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        Timer timer = new Timer(1000, e -> updateClock(timeLabel));
        timer.start();
        updateClock(timeLabel);

        panel.add(timeLabel);

        return panel;
    }

    private void updateClock(JLabel timeLabel) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(FORMATTER);
        timeLabel.setText(formattedDateTime);
    }

    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
            JMenuItem noteMenuItem = new JMenuItem("Notes");
            noteMenuItem.setMnemonic(KeyEvent.VK_N);
            noteMenuItem.addActionListener(_ -> tabs.setSelectedIndex(0));
            fileMenu.add(noteMenuItem);

            JMenuItem journalMenuItem = new JMenuItem("Journal");
            journalMenuItem.setMnemonic(KeyEvent.VK_J);
            journalMenuItem.addActionListener(_ -> tabs.setSelectedIndex(1));
            fileMenu.add(journalMenuItem);

            fileMenu.addSeparator();

            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setMnemonic(KeyEvent.VK_X);
            exitMenuItem.addActionListener(_ -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
            fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    private JTabbedPane createJTabbedPane() {
        tabs = new JTabbedPane();

        tabs.add(settings.getProperties().getProperty("tab.notes"), new NoteTab(settings));
        tabs.add(settings.getProperties().getProperty("tab.journal"), new JournalTab(settings));

        return tabs;
    }
}
