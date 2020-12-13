use photodb;

CREATE TABLE post (
    id int UNSIGNED AUTO_INCREMENT,
    title TEXT NOT NULL,
    author int UNSIGNED NOT NULL,
    label VARCHAR(24),
    theme int UNSIGNED NOT NULL,
    photo_url TEXT NOT NULL,
    delete_url TEXT NOT NULL,

    PRIMARY KEY (id)
)