package net.askearly.db;

import net.askearly.model.Journal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JournalResults {

    private final ResultSet rs;
    public JournalResults(ResultSet rs) throws SQLException {
        this.rs = rs;
    }


    public Journal getJournalEntry() throws SQLException {
        Journal journalEntry = new Journal();
        journalEntry.setId(rs.getLong(Tables.Journal.id.name()));
        journalEntry.setContent(rs.getString(Tables.Journal.content.name()));
        journalEntry.setCreatedDt(rs.getString(Tables.Journal.created_dt.name()));
        return journalEntry;
    }
}
