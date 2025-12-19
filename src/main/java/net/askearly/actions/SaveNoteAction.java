package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.NewNoteScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class SaveNoteAction implements Executiable {

    private final Settings settings;
    private final NoteTableModel model;
    private final String title;
    private final String content;
    private final AtomicReference<File> selectedFile;

    public SaveNoteAction(NewNoteScreen newNoteScreen) {
        this.settings = newNoteScreen.getSettings();
        this.model = newNoteScreen.getModel();
        this.title = newNoteScreen.getTitleField().getText();
        this.content = newNoteScreen.getContent().getText();
        this.selectedFile = newNoteScreen.getSelectedFile();
    }

    @Override
    public void execute(ActionEvent e) {
        if (e.getActionCommand().equals("save.note")) {
            String fileName = null;
            if (this.selectedFile.get() != null) {
                fileName = this.selectedFile.get().getAbsolutePath();
            }
            Note note = new Note(0, this.title, this.content, fileName, null, null);
            System.out.println(note);
            this.settings.getDatabase().saveNote(note);
            this.model.setDataList(settings.getDatabase().getAllNotes());
            this.model.fireTableDataChanged();
        }
    }
}
