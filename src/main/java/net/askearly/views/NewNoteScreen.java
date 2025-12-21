package net.askearly.views;

import net.askearly.actions.SaveNoteAction;
import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.swing.ContextMenuTextArea;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

public class NewNoteScreen extends JDialog {

    private final Settings settings;
    private final NoteTableModel model;
    private final long id;
    private final JTextField titleField = new JTextField(20);
    private final ContextMenuTextArea content = new ContextMenuTextArea(20, 30);
    private final AtomicReference<File> selectedFile = new AtomicReference<>();
    private final JLabel fileNameLabel = new JLabel("");

    public NewNoteScreen(Settings settings, NoteTableModel model, long id) {
        this.settings = settings;
        this.model = model;
        this.id = id;

        generateForm(settings);
    }

    public NewNoteScreen(Settings settings, NoteTableModel model, long id, Note note) {
        this.settings =  settings;
        this.model = model;
        this.id = id;
        titleField.setText(note.getTitle());
        content.setText(note.getContent());
        content.setCaretPosition(0);
        if (note.getFilename() != null) {
            fileNameLabel.setText(Paths.get(note.getFilename()).getFileName().toString());
            selectedFile.set(new File(note.getFilename()));
        }

        generateForm(settings);
    }

    private void generateForm(Settings settings) {
        setTitle(settings.getProperties().getProperty("title.new.note"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setLayout(new BorderLayout());
        setSize(650, 300);
        setResizable(false);
        add(createForm(), BorderLayout.CENTER);
        setLocationRelativeTo(this.getParent());
        setVisible(true);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel(settings.getProperties().getProperty("label.note.title"));
        titleLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);

        titlePanel.add(titleField);

        topPanel.add(titlePanel);

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel fileLabel = new JLabel(settings.getProperties().getProperty("label.note.file"));
        fileLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(fileLabel);

        JButton openButton = new JButton(settings.getProperties().getProperty("button.note.open"));
        if (selectedFile.get() == null) {
            openButton.setEnabled(false);
        }

        JButton clearButton = new JButton(settings.getProperties().getProperty("button.note.clear"));
        clearButton.setEnabled(false);
        if (getId() > 0) {
            clearButton.setVisible(false);
        }

        JButton fileButton = new JButton(settings.getProperties().getProperty("button.note.file"));
        fileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile.set(chooser.getSelectedFile());
                fileNameLabel.setText(selectedFile.get().toPath().getFileName().toString());
                openButton.setEnabled(true);
                clearButton.setEnabled(true);
            }
        });
        if (selectedFile.get() == null) {
            titlePanel.add(fileButton);
        }

        clearButton.addActionListener(e -> {
            fileNameLabel.setText("");
            selectedFile.set(null);
            openButton.setEnabled(false);
            clearButton.setEnabled(false);
        });
        titlePanel.add(clearButton);

        openButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(selectedFile.get().getAbsolutePath()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        titlePanel.add(openButton);

        //topPanel.add(filePanel);

        topPanel.add(fileNameLabel);

        panel.add(topPanel, BorderLayout.NORTH);

        panel.add(new JScrollPane(content), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton(settings.getProperties().getProperty("button.note.save"));
        if (getId() > 0) {
            saveButton.setActionCommand("update.note");
        } else {
            saveButton.setActionCommand("save.note");
        }
        saveButton.addActionListener(e -> {
            new SaveNoteAction(this).execute(e);
            this.dispose();
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton(settings.getProperties().getProperty("button.note.cancel"));
        cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public Settings getSettings() {
        return settings;
    }

    public NoteTableModel getModel() {
        return model;
    }

    public long getId() {
        return id;
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

    public void showMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
