package net.askearly.db;

import net.askearly.model.Note;
import net.askearly.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    private  final Settings settings;
    private final Properties databaseProperties;
    private final Properties properties;

    private List<Note> notes = new ArrayList<>();

    public Database(Settings settings) {
        this.settings = settings;
        this.databaseProperties = this.settings.getDatabaseProperties();
        this.properties = this.settings.getProperties();
        notes.add(new Note(1, "Title", "Content", "Filename", null, null));
    }

    public List<Note> getAllNotes() {
        return notes;
    }

    public void saveNote(Note note) {
        notes.add(note);
    }

    public Note getNote(long l) {
        return new Note(3, "Title", "Content", "Filename", null, null);
    }
}
