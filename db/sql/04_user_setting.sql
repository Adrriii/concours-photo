use photodb;

CREATE TABLE user_setting (
    user int UNSIGNED,
    setting VARCHAR(24),
    value text,
    public boolean,

    PRIMARY KEY (user, setting)
)