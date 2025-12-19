CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NULL,
    content TEXT NULL,
    filename TEXT NULL,
    created_dt TEXT DEFAULT (DATETIME('now', 'localtime')),
    updated_dt TEXT DEFAULT (DATETIME('now', 'localtime'))
);