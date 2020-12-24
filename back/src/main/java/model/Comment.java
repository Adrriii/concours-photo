package model;

public class Comment {
    public final Integer id;
    public final User author;
    public final Post post;
    public final Comment parent;

    public Comment() {
        this(null, null, null, null);
    }

    public Comment(Integer id, User author, Post post, Comment parent) {
        this.id = id;
        this.author = author;
        this.post = post;
        this.parent = parent;
    }
}