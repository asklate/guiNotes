package net.askearly.actions;

import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.NewNoteScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewNoteAction implements ActionListener {

    private final Settings settings;
    private final NoteTableModel model;

    public NewNoteAction(Settings settings, NoteTableModel model) {
        this.settings = settings;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("new.note")) {
            new NewNoteScreen(settings, model, 0);
        }
    }
}
