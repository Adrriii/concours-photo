package model;

public class Reaction {
    public final Integer user;
    public final Integer post;
    public final ReactionName reaction;

    public Reaction() {
        this(null, null, null);
    }

    public Reaction(Integer user, Integer post, ReactionName reaction) {
        this.user = user;
        this.post = post;
        this.reaction = reaction;
    }
}
