package net.askearly.actions;

import net.askearly.model.JournalTableModel;
import net.askearly.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.internal.database.base.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteJournalAction implements Executiable {

    private static final Logger logger = LogManager.getLogger(DeleteJournalAction.class);
    private final Settings settings;
    private final JournalTableModel model;
    private final JTable table;

    public DeleteJournalAction(Settings settings, JournalTableModel model, JTable table) {
        this.settings = settings;
        this.model = model;
        this.table = table;
    }

    @Override
    public void execute(ActionEvent e) {
        if (e.getActionCommand().equals("delete.journal")) {
            logger.info("In delete journal action");
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex >= 0) {
                int modelRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                Object firstColumnValue = model.getValueAt(modelRowIndex, 0);
                logger.info("Journal Entry to delete {}", firstColumnValue);
                long id = Long.parseLong(firstColumnValue.toString());
                settings.getDatabase().deleteJournalEntry(id);
            }
        }
    }
}
