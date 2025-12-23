package net.askearly.views;

import net.askearly.actions.DeleteJournalAction;
import net.askearly.actions.SaveJournalAction;
import net.askearly.model.Journal;
import net.askearly.model.JournalTableModel;
import net.askearly.settings.Settings;
import net.askearly.swing.ContextMenuTextArea;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class JournalTab extends JPanel {

    private final Settings settings;
    private final ContextMenuTextArea content = new ContextMenuTextArea(10, 30);
    private JournalTableModel model;
    private JTable table;
    private JButton deleteButton;
    private JButton clearButton;

    public JournalTab(Settings settings) {
        this.settings = settings;

        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.add(createJournalTable());
        splitPane.add(createTextArea());
        splitPane.setDividerLocation(0.3d);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.SOUTH);
    }

    private JScrollPane createJournalTable() {
        List<Journal> journalEntries = settings.getDatabase().getAllJournalEntries();
        model = new JournalTableModel(journalEntries);
        table = new JTable(model);
        table.setFillsViewportHeight(false);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        content.setText((String) table.getValueAt(selectedRow, 1));
                        content.setCaretPosition(0);
                        deleteButton.setEnabled(true);
                        clearButton.setEnabled(true);
                    } else  {
                        deleteButton.setEnabled(false);
                        clearButton.setEnabled(false);
                    }
                }
            }
        });
        return new JScrollPane(table);
    }

    private JScrollPane createTextArea() {
        return new JScrollPane(content);
    }

    private JPanel createButtonPane() {
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        clearButton = new JButton(settings.getProperties().getProperty("button.journal.clear"));
        clearButton.setActionCommand("clear.journal");
        clearButton.setEnabled(false);
        clearButton.addActionListener(e -> {
            table.clearSelection();
            content.setText("");
        });
        buttonPane.add(clearButton);

        deleteButton = new JButton(settings.getProperties().getProperty(("button.journal.delete")));
        deleteButton.setActionCommand("delete.journal");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> {
            int value = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Journal Entry?");
            if (value == JOptionPane.OK_OPTION) {
                new DeleteJournalAction(settings, model, table).execute(e);
                this.model.setDataList(settings.getDatabase().getAllJournalEntries());
                this.model.fireTableDataChanged();
                content.setText("");
            }
        });
        buttonPane.add(deleteButton);

        JButton saveButton = new JButton(settings.getProperties().getProperty("button.journal.save"));
        saveButton.setActionCommand("save.journal");
        saveButton.addActionListener(e -> {
            if (content.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Content is required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new SaveJournalAction(this).execute(e);
            content.setText("");
        });

        buttonPane.add(saveButton);

        return buttonPane;
    }

    public ContextMenuTextArea getContent() {
        return this.content;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public JournalTableModel getModel() {
        return this.model;
    }
}
