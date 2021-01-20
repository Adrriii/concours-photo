CREATE DATABASE IF NOT EXISTS `photodb`;
GRANT ALL ON *.* TO 'photodb'@'%' WITH GRANT OPTION;

DELIMITER $
CREATE PROCEDURE update_ranks()
BEGIN
    SET @r=0;
    UPDATE user SET `rank` = null;
    UPDATE user SET `rank`= @r:= (@r+1) WHERE id IN (SELECT author FROM post) ORDER BY score DESC;
END$
DELIMITER ;

CREATE TABLE demo (
    id int UNSIGNED AUTO_INCREMENT,
    PRIMARY KEY (id)
);