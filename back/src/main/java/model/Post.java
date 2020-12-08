package model;

import java.util.List;

public class Post {
    public final Integer id;
    public final String title;
    public final String reacted;
    public final List<Reaction> reactions;
    public final User author;
    public final Label label;
    public final Theme theme;
    public final String photo;

    public Post(Integer id, String title, String reacted, List<Reaction> reactions, User author, Label label, Theme theme, String photo) {
        this.id = id;
        this.title = title;
        this.reacted = reacted;
        this.reactions = reactions;
        this.author = author;
        this.label = label;
        this.theme = theme;
        this.photo = photo;
    }
}
