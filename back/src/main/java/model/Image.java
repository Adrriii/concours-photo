package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    public final String url;
    public final String delete_url;

    public Image() {
        this(null, null);
    }

    public Image(String url, String delete_url) {
        this.url = url;
        this.delete_url = delete_url;
    }

    @Override
    public String toString() {
        return "Image ( link: " + url
        + ", delete: " + delete_url
        + ")";
    }
}
