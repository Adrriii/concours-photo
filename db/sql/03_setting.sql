use photodb;

CREATE TABLE setting (
    label VARCHAR(24),
    default_value text,
    default_public boolean,

    PRIMARY KEY (label)
)