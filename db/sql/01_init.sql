CREATE DATABASE IF NOT EXISTS `photodb`;
GRANT ALL ON *.* TO 'photodb'@'%' WITH GRANT OPTION;

DELIMITER $
CREATE PROCEDURE update_ranks()
BEGIN
    SET @r=0;
    UPDATE user SET `rank`= @r:= (@r+1) ORDER BY score DESC;
END$
DELIMITER ;