use photodb;

CREATE TABLE setting (
    id int UNSIGNED AUTO_INCREMENT,
    label text NOT NULL UNIQUE,

    PRIMARY KEY (id)
)