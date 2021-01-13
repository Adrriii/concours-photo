use photodb;

CREATE TABLE theme (
    id int UNSIGNED AUTO_INCREMENT,
    title TEXT NOT NULL,
    state TEXT NOT NULL,
    photo_url TEXT NOT NULL,
    winner int UNSIGNED,
    author int UNSIGNED,
    date TEXT NOT NULL,
    PRIMARY KEY (id)
)