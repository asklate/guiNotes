package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.NewNoteScreen;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

public class SaveNoteAction implements Executiable {

    private final Settings settings;
    private final NoteTableModel model;
    private final long id;
    private final String title;
    private final String content;
    private final AtomicReference<File> selectedFile;

    public SaveNoteAction(NewNoteScreen newNoteScreen) {
        this.settings = newNoteScreen.getSettings();
        this.model = newNoteScreen.getModel();
        this.id = newNoteScreen.getId();
        this.title = newNoteScreen.getTitleField().getText();
        this.content = newNoteScreen.getContent().getText();
        this.selectedFile = newNoteScreen.getSelectedFile();
    }

    @Override
    public void execute(ActionEvent e) {
        if (e.getActionCommand().equals("save.note")) {
            String fileName = null;
            if (this.selectedFile.get() != null) {
                fileName = saveFile(this.selectedFile.get());
            }
            Note note = new Note(id, this.title, this.content, fileName, null, null);

            this.settings.getDatabase().saveNote(note);
            this.model.setDataList(settings.getDatabase().getAllNotes());
            this.model.fireTableDataChanged();
        } else if (e.getActionCommand().equals("update.note")) {
            String fileName = null;
            if (this.selectedFile.get() != null) {
                fileName = this.selectedFile.get().getAbsolutePath();
            }
            Note existingNote = settings.getDatabase().getote(this.id);
            if (fileName != null && !existingNote.getFilename().equals(fileName)) {
                fileName = saveFile(this.selectedFile.get());
            }
            Note note = new Note(id, this.title, this.content, fileName, null, null);

            this.settings.getDatabase().updateNote(note);
            this.model.setDataList(settings.getDatabase().getAllNotes());
            this.model.fireTableDataChanged();

        }
    }

    private String saveFile(File file) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String now = LocalDateTime.now().format(dtf);
        Path directory = Paths.get(
                settings.getProperties().getProperty("dir.io.files"),
                "/",
                now
        );

        Path fileName = Paths.get(directory.toString(), "/", file.getName());

        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        file.renameTo(fileName.toFile());

        return fileName.toString();
    }
}
