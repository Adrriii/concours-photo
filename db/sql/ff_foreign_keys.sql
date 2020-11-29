use photodb;

ALTER TABLE user_setting ADD CONSTRAINT FK_US_user FOREIGN KEY (user) REFERENCES user(id);
ALTER TABLE user_setting ADD CONSTRAINT FK_US_setting FOREIGN KEY (setting) REFERENCES setting(id);