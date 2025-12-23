package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpenNoteFileAction  implements ActionListener {

    private final Settings settings;
    private final NoteTableModel model;
    private final JTable table;

    public OpenNoteFileAction(Settings settings, NoteTableModel model, JTable table) {
        this.settings = settings;
        this.model = model;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open.note.file")) {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex >= 0) {
                int modelRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                Object firstColumnValue = model.getValueAt(modelRowIndex, 0);
                Note note = settings.getDatabase().getNote(Long.parseLong(firstColumnValue.toString()));
                try {
                    Desktop.getDesktop().open(new File(note.getFilename()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
