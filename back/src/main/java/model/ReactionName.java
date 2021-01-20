package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum ReactionName {
    LIKE("LIKE"),
    DISLIKE("DISLIKE");

    String value;

    ReactionName(String value) {
        this.value = value;
    }
}