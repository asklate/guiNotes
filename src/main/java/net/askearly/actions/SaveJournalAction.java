package net.askearly.actions;

import net.askearly.model.JournalTableModel;
import net.askearly.settings.Settings;
import net.askearly.views.JournalTab;

import java.awt.event.ActionEvent;

public class SaveJournalAction implements Executiable {

    private final JournalTab journalTab;
    private final Settings settings;
    private final String content;
    private final JournalTableModel model;

    public SaveJournalAction(JournalTab journalTab) {
        this.journalTab = journalTab;
        this.settings = journalTab.getSettings();
        this.model = journalTab.getModel();
        this.content = journalTab.getContent().getText();
    }

    @Override
    public void execute(ActionEvent e) {
        if (e.getActionCommand().equals("save.journal")) {
            this.settings.getDatabase().saveJournalEntry(content);
            this.model.setDataList(settings.getDatabase().getAllJournalEntries());
            this.model.fireTableDataChanged();
        }
    }
}
