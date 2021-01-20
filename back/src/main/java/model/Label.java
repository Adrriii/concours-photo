package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {
    public final String label;

    public Label() {
        this(null);
    }

    public Label(String label) {
        this.label = label;
    }
}