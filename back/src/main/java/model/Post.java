package model;

import java.util.List;

public class Post {
    public final Integer id;
    public final String title;
    public final String reacted;
    public final List<Reactions> reactions;
    public final User author;
    public final Label label;
    public final Theme theme;
    public final String photo;
    public final String photoDelete;

    public Post() {
        this(null, null, null, null, null, null, null, null);
    }

    public Post(String title, String reacted, List<Reactions> reactions, User author, Label label, Theme theme, String photo, String photoDelete, Integer id) {
        this.id = id;
        this.title = title;
        this.reacted = reacted;
        this.reactions = reactions;
        this.author = author;
        this.label = label;
        this.theme = theme;
        this.photo = photo;
        this.photoDelete = photoDelete;
    }

    public Post(String title, String reacted, List<Reactions> reactions, User author, Label label, Theme theme, String photo, String photoDelete) {
        this(title, reacted, reactions, author, label, theme, photo, photoDelete, null);
    }

    @Override
    public String toString() {
        return "Post(id: " + this.id +
                ", title: " + this.title +
                ", reacted: " + this.reacted +
                ", reactions: " + this.reactions +
                ", author: " + this.author +
                ", label: " + this.label +
                ", theme: " + this.theme +
                ", photo: " + this.photo +
                ", photoDelete: " + this.photoDelete + ")";
    }
}
