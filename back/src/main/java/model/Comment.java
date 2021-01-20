package model;

public class Comment {
    public final Integer id;
    public final User author;
    public final Post post;
    public final Comment parent;
    public final String content;
    public final String date;

    public Comment() {
        this(null, null, null, null, null, null);
    }

    public Comment(User author, Post post, Comment parent, String content, String date, Integer id) {
        this.id = id;
        this.author = author;
        this.post = post;
        this.parent = parent;
        this.content = content;
        this.date = date;
    }

    public Comment(User author, Post post, Comment parent, String content) {
        this(author, post, parent, content, null, null);
    }
}