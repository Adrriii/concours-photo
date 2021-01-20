use photodb;

CREATE TABLE comment (
    id int UNSIGNED AUTO_INCREMENT,
    
    author int UNSIGNED NOT NULL,
    post int UNSIGNED NOT NULL,
    parent int UNSIGNED,
    content TEXT NOT NULL,
    d TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
)