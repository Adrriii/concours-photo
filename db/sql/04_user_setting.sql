use photodb;

CREATE TABLE user_setting (
    user int UNSIGNED,
    setting int UNSIGNED,
    value text,
    public boolean,

    PRIMARY KEY (user, setting)
)