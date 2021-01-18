package model;

public enum ReactionName {
    LIKE("like"),
    DISLIKE("dislike");

    String value;

    ReactionName(String value) {
        this.value = value;
    }
}