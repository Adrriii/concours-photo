package model;

public enum ReactionName {
    LIKE("LIKE"),
    DISLIKE("DISLIKE");

    String value;

    ReactionName(String value) {
        this.value = value;
    }
}