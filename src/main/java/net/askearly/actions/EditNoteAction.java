package net.askearly.actions;

import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.NewNoteScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditNoteAction implements ActionListener {

    private final Settings settings;
    private final NoteTableModel model;
    private final JTable table;

    public EditNoteAction(Settings settings, NoteTableModel model, JTable table) {
        this.settings = settings;
        this.model = model;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit.note")) {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex >= 0) {
                int modelRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                Object firstColumnValue = table.getModel().getValueAt(modelRowIndex, 0);
                Note note = settings.getDatabase().getote(Long.parseLong(firstColumnValue.toString()));
                new NewNoteScreen(settings, model, Long.parseLong(firstColumnValue.toString()), note);
            }
        }
    }
}
