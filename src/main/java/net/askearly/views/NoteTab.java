package net.askearly.views;

import net.askearly.actions.EditNoteAction;
import net.askearly.actions.NewNoteAction;
import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NoteTab extends JPanel {

    private final Settings settings;
    private NoteTableModel model;
    private JTable table;

    public NoteTab(Settings settings) {
        this.settings = settings;

        setLayout(new BorderLayout());
        add(createNoteTable(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createNoteTable() {
        List<Note> notes = settings.getDatabase().getAllNotes();
        model = new NoteTableModel(notes);
        table = new JTable(model);
        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editNoteButton = new JButton(settings.getProperties().getProperty("button.note.edit"));
        editNoteButton.setActionCommand("edit.note");
        editNoteButton.addActionListener(new EditNoteAction(settings, model, table));
        panel.add(editNoteButton);

        JButton newNoteButton = new JButton(settings.getProperties().getProperty("button.note.new"));
        newNoteButton.setActionCommand("new.note");
        newNoteButton.addActionListener(new NewNoteAction(settings, model));
        panel.add(newNoteButton);
        return panel;
    }
}
