use photodb;

CREATE TABLE reaction (
    user int UNSIGNED,
    post int UNSIGNED,
    value TEXT,

    PRIMARY KEY (user, post)
)