package net.askearly.views;

import net.askearly.actions.SaveNoteAction;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class NewNoteScreen extends JDialog {

    private final Settings settings;
    private final NoteTableModel model;
    private final JTextField titleField = new JTextField(20);
    private final JTextArea content = new JTextArea();
    private final AtomicReference<File> selectedFile =  new AtomicReference<>();

    public NewNoteScreen(Settings settings, NoteTableModel model) {
        this.settings = settings;
        this.model = model;

        setTitle(settings.getProperties().getProperty("title.new.note"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 400);
        setResizable(false);
        add(createForm(), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel(settings.getProperties().getProperty("label.note.title"));
        titleLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);

        titlePanel.add(titleField);

        topPanel.add(titlePanel);

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel fileLabel = new JLabel(settings.getProperties().getProperty("label.note.file"));
        fileLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filePanel.add(fileLabel);

        JLabel fileNameLabel = new JLabel("");

        JButton fileButton = new JButton(settings.getProperties().getProperty("button.note.file"));
        fileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile.set(chooser.getSelectedFile());
                fileNameLabel.setText(selectedFile.get().getAbsolutePath());
            }
        });
        filePanel.add(fileButton);

        JButton clearButton = new JButton(settings.getProperties().getProperty("button.note.clear"));
        clearButton.addActionListener(e -> {
            fileNameLabel.setText("");
            selectedFile.set(null);
        });
        filePanel.add(clearButton);

        topPanel.add(filePanel);

        topPanel.add(fileNameLabel);

        panel.add(topPanel, BorderLayout.NORTH);

        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setMargin(new Insets(10, 10, 10, 10));
        panel.add(new JScrollPane(content), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton(settings.getProperties().getProperty("button.note.save"));
        saveButton.setActionCommand("save.note");
        saveButton.addActionListener(e -> {
            new SaveNoteAction(this).execute();
        });
        buttonPanel.add(saveButton);

        JButton cancelbutton = new JButton(settings.getProperties().getProperty("button.note.cancel"));
        cancelbutton.addActionListener(e -> this.dispose());
        buttonPanel.add(cancelbutton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public Settings getSettings() {
        return settings;
    }

    public NoteTableModel getModel() {
        return model;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextArea getContent() {
        return content;
    }

    public AtomicReference<File> getSelectedFile() {
        return selectedFile;
    }
}
