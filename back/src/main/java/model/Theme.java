package model;

public class Theme {
    public final Integer id;
    public final String title;
    public final String photo;
    public final String state; // Enum ?
    public final String date;
    public final User winner;

    public Theme(Integer id, String title, String photo, String state, String date, User winner) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.state = state;
        this.date = date;
        this.winner = winner;
    }
}
