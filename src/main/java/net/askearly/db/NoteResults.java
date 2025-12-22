package net.askearly.db;

import net.askearly.model.Note;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteResults {

    private final ResultSet rs;

    public NoteResults(ResultSet rs) throws SQLException {
        this.rs = rs;
    }

    public Note getNote() throws SQLException {
        Note note = new Note();
        note.setId(rs.getLong(Tables.Notes.id.name()));
        note.setTitle(rs.getString(Tables.Notes.title.name()));
        note.setContent(rs.getString(Tables.Notes.content.name()));
        note.setFilename(rs.getString(Tables.Notes.filename.name()));
        note.setCreatedDt(rs.getString(Tables.Notes.created_dt.name()));
        note.setUpdatedDt(rs.getString(Tables.Notes.updated_dt.name()));
        return note;
    }
}
