package net.askearly.db;

import net.askearly.model.Note;
import net.askearly.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    private static final Logger logger = LogManager.getLogger(Database.class);
    private final Properties databaseProperties;
    private final Properties properties;

    public Database(Settings settings) {
        this.databaseProperties = settings.getDatabaseProperties();
        this.properties = settings.getProperties();
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        String query = databaseProperties.getProperty("sql.all.notes");

        try (Connection conn = SQLiteConnectionPoolManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getLong(Tables.Notes.id.name()));
                note.setTitle(rs.getString(Tables.Notes.title.name()));
                note.setContent(rs.getString(Tables.Notes.content.name()));
                note.setFilename(rs.getString(Tables.Notes.filename.name()));
                note.setCreatedDt(rs.getString(Tables.Notes.created_dt.name()));
                note.setUpdatedDt(rs.getString(Tables.Notes.updated_dt.name()));
                notes.add(note);
            }
        } catch (SQLException e) {
            logger.error("Get All Notes Error", e);
        }

        return notes;
    }

    public void saveNote(Note note) {
        String query = databaseProperties.getProperty("sql.save.note");

        try (Connection conn = SQLiteConnectionPoolManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            if (note.getFilename() != null) {
                stmt.setString(3, note.getFilename());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Save Note Error", e);
        }
    }

    public Note getote(long id) {
        Note note = null;

        ResultSet rs = null;

        String query = databaseProperties.getProperty("sql.get.note");

        try (Connection conn = SQLiteConnectionPoolManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                note = new Note();
                note.setId(rs.getLong(Tables.Notes.id.name()));
                note.setTitle(rs.getString(Tables.Notes.title.name()));
                note.setContent(rs.getString(Tables.Notes.content.name()));
                note.setFilename(rs.getString(Tables.Notes.filename.name()));
                note.setCreatedDt(rs.getString(Tables.Notes.created_dt.name()));
                note.setUpdatedDt(rs.getString(Tables.Notes.updated_dt.name()));
            }
        } catch (SQLException e) {
            logger.error("Get Note Error", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error closing result set (Get Note)", e);
                }
            }
        }

        return note;
    }

    public void updateNote(Note note) {
        String query =  databaseProperties.getProperty("sql.update.note");

        try (Connection conn = SQLiteConnectionPoolManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            if (note.getFilename() != null) {
                stmt.setString(3, note.getFilename());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            stmt.setLong(4, note.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update Note Error", e);
        }
    }

    public void deleteNote(long id) {
        String query = databaseProperties.getProperty("sql.delete.note");

        try (Connection conn = SQLiteConnectionPoolManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete Note Error", e);
        }
    }
}
