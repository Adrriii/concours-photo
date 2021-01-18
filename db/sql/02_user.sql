use photodb;

CREATE TABLE user (
    id int UNSIGNED AUTO_INCREMENT,
    username VARCHAR(24) NOT NULL UNIQUE,
    sha TEXT NOT NULL,
    theme int UNSIGNED,
    userlevel int UNSIGNED,
    victories int UNSIGNED,
    score int UNSIGNED,
    rank int UNSIGNED,
    photo_url TEXT,
    delete_url TEXT,
    PRIMARY KEY (id)
);
