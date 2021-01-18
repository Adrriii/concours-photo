use photodb;

CREATE TABLE post (
    id int UNSIGNED AUTO_INCREMENT,
    d DATETIME DEFAULT (CURRENT_TIMESTAMP),
    title TEXT NOT NULL,
    author int UNSIGNED NOT NULL,
    label VARCHAR(24),
    theme int UNSIGNED NOT NULL,
    score int DEFAULT 0,
    nb_votes int DEFAULT 0,
    nb_comment int DEFAULT 0,
    photo_url TEXT NOT NULL,
    delete_url TEXT NOT NULL,

    PRIMARY KEY (id)
)