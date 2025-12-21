package net.askearly.views;

import net.askearly.actions.DeleteNoteAction;
import net.askearly.actions.EditNoteAction;
import net.askearly.actions.NewNoteAction;
import net.askearly.model.Note;
import net.askearly.model.NoteTableModel;
import net.askearly.settings.Settings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NoteTab extends JPanel {

    private final Settings settings;
    private NoteTableModel model;
    private JTable table;
    private JButton openButton;
    private JButton editNoteButton;
    private JButton deleteButton;

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
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        editNoteButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                        Object cellData = model.getValueAt(selectedRow, 3);
                        if (!cellData.equals("")) {
                            openButton.setEnabled(true);
                        } else {
                            openButton.setEnabled(false);
                        }
                    } else {
                        editNoteButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                        openButton.setEnabled(false);
                    }
                }
            }
        });
        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton(settings.getProperties().getProperty("button.note.delete"));
        deleteButton.setActionCommand("delete.note");
        deleteButton.addActionListener(e -> {
            int value = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this note?");
            if (value == JOptionPane.OK_OPTION) {
                new DeleteNoteAction(settings, model, table).execute(e);
                this.model.setDataList(settings.getDatabase().getAllNotes());
                this.model.fireTableDataChanged();
                deleteButton.setEnabled(false);
                openButton.setEnabled(false);
                editNoteButton.setEnabled(false);
            }
        });
        deleteButton.setEnabled(false);
        panel.add(deleteButton);

        openButton = new JButton(settings.getProperties().getProperty("button.note.open"));
        openButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object cellData = model.getValueAt(selectedRow, 3);
                try {
                    Desktop.getDesktop().open(new File(cellData.toString()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        openButton.setEnabled(false);
        panel.add(openButton);

        editNoteButton = new JButton(settings.getProperties().getProperty("button.note.edit"));
        editNoteButton.setActionCommand("edit.note");
        editNoteButton.addActionListener(new EditNoteAction(settings, model, table));
        editNoteButton.setEnabled(false);
        panel.add(editNoteButton);

        JButton newNoteButton = new JButton(settings.getProperties().getProperty("button.note.new"));
        newNoteButton.setActionCommand("new.note");
        newNoteButton.addActionListener(new NewNoteAction(settings, model));
        panel.add(newNoteButton);
        return panel;
    }
}
