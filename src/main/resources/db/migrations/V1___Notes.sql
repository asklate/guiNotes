CREATE TABLE notes (
    id INTEGER PRIMARY KEY,
    title TEXT NULL,
    content TEXT NULL,
    filename TEXT NULL,
    created_dt TEXT DEFAULT (DATETIME('now', 'localtime')),
    updated_dt TEXT DEFAULT (DATETIME('now', 'localtime'))
);