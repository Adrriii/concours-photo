use photodb;

CREATE TABLE comment {
    id int UNSIGNED AUTO_INCREMENT,
    
    author int UNSIGNED NOT NULL,
    post int UNSIGNED NOT NULL,
    parent int UNSIGNED,

    PRIMARY KEY (id)
}