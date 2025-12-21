CREATE TABLE journal (
    id INTEGER PRIMARY KEY,
    content TEXT NULL,
    created_dt TEXT DEFAULT (DATETIME('now', 'localtime'))
);