package model;

public class Reactions {
    public final String reaction;
    public final Integer count;

    public Reactions() {
        this(null, null);
    }

    public Reactions(String reaction, Integer count) {
        this.reaction = reaction;
        this.count = count;
    }
}
