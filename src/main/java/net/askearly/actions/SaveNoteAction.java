package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.NewNoteScreen;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SaveNoteAction implements Executiable {

    private static final Logger logger = LogManager.getLogger(SaveNoteAction.class);
    private final NewNoteScreen parent;
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
        this.parent = newNoteScreen;
    }

    @Override
    public void execute(ActionEvent e) {
        List<String> errors = new ArrayList<>();

        validate(errors);

        if (errors.isEmpty()) {
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

                logger.info("Existing note: {}", existingNote);

                if (existingNote.getFilename() == null && fileName != null) {
                    logger.info("Saving note 1: {} {}", existingNote.getFilename(), fileName);
                    fileName = saveFile(this.selectedFile.get());
                } else if (fileName != null && !existingNote.getFilename().equals(fileName)) {
                    logger.info("Saving note 2: {} {}", existingNote.getFilename(), fileName);
                    fileName = saveFile(this.selectedFile.get());
                }

                Note note = new Note(id, this.title, this.content, fileName, null, null);

                logger.info("Note To Save {}", note);

                this.settings.getDatabase().updateNote(note);
                this.model.setDataList(settings.getDatabase().getAllNotes());
                this.model.fireTableDataChanged();
            }
        } else {
            String errorMessage = errors.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            parent.showMessage(errorMessage);
        }
    }

    private void validate(List<String> errors) {
        if (title.trim().isEmpty()) {
            errors.add("Title cannot be empty");
        }

        if (content.trim().isEmpty()) {
            errors.add("Content cannot be empty");
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

        Path fileName = Paths.get(directory.toString(), "/", makeUnique(directory.toString(), file.getName()));

        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Files.copy(file.toPath(), fileName, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Couldn't copy file {}", file.getAbsolutePath(), e);
        }

        return fileName.toFile().getAbsolutePath();
    }

    private String makeUnique(String directory, String fileName) {
        String basename = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);

        if (!extension.isEmpty()) {
            extension = "." + extension;
        }

        File file = new File(directory, basename + extension);
        int counter = 0;

        while (file.exists()) {
            counter++;
            String newName = basename + "(" + counter + ")" + extension;
            file = new File(directory, newName);
        }

        return file.getName();
    }
}
