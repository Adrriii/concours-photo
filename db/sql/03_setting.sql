use photodb;

CREATE TABLE setting (
    id int UNSIGNED AUTO_INCREMENT,
    label text NOT NULL UNIQUE,
    default_value text,

    PRIMARY KEY (id)
)