use photodb;

CREATE TABLE setting (
    label text NOT NULL UNIQUE,
    default_value text,

    PRIMARY KEY (label)
)