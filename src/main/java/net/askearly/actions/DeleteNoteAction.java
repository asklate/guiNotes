package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteNoteAction implements Executiable {

    private static final Logger logger = LogManager.getLogger(DeleteNoteAction.class);
    private final Settings settings;
    private final NoteTableModel model;
    private final JTable table;

    public DeleteNoteAction(Settings settings, NoteTableModel model, JTable table) {
        this.settings = settings;
        this.model = model;
        this.table = table;
    }

    @Override
    public void execute(ActionEvent e) {
        if (e.getActionCommand().equals("delete.note")) {
            logger.info("In delete note action");
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex >= 0) {
                int modelRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                Object firstColumnValue = model.getValueAt(modelRowIndex, 0);
                logger.info("Note to delete {}", firstColumnValue);
                long id = Long.parseLong(firstColumnValue.toString());
                Note note = settings.getDatabase().getNote(id);
                if (note.getFilename() != null) {
                    deleteFile(note.getFilename());
                }
                settings.getDatabase().deleteNote(id);
            }
        }
    }

    private void deleteFile(String filename) {
        try {
            logger.info("Deleting file {}", filename);
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            logger.error("Unable to delete file {}", filename);
        }
    }
}
