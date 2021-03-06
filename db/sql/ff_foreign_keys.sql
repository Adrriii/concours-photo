use photodb;

ALTER TABLE user_setting ADD CONSTRAINT FK_US_user FOREIGN KEY (user) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE user_setting ADD CONSTRAINT FK_US_setting FOREIGN KEY (setting) REFERENCES setting(label) ON DELETE CASCADE;
ALTER TABLE post ADD CONSTRAINT FK_POST_author FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE post ADD CONSTRAINT FK_POST_label FOREIGN KEY (label) REFERENCES label(label) ON DELETE CASCADE;
ALTER TABLE post ADD CONSTRAINT FK_POST_theme FOREIGN KEY (theme) REFERENCES theme(id) ON DELETE CASCADE;
ALTER TABLE reaction ADD CONSTRAINT FK_REACTION_user FOREIGN KEY (user) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE reaction ADD CONSTRAINT FK_REACTION_post FOREIGN KEY (post) REFERENCES post(id) ON DELETE CASCADE;
ALTER TABLE comment ADD CONSTRAINT FK_COMMENT_author FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE;
ALTER TABLE comment ADD CONSTRAINT FK_COMMENT_post FOREIGN KEY (post) REFERENCES post(id) ON DELETE CASCADE;
ALTER TABLE comment ADD CONSTRAINT FK_COMMENT_parent FOREIGN KEY (parent) REFERENCES comment(id) ON DELETE CASCADE;
ALTER TABLE user ADD CONSTRAINT FK_USER_theme FOREIGN KEY (theme) REFERENCES theme(id);