package net.askearly.db;

public class Tables {

    public enum Journal {
        id,
        content,
        created_dt;
    }

    public enum Notes {
        id,
        title,
        content,
        filename,
        created_dt,
        updated_dt;
    }
}
