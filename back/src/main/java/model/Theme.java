package model;

public class Theme {
    
    public final Integer id;
    public final String title;
    public final String photo;
    public final String state; // Enum ?
    public final String date;
    public final User winner;
    public final User author;

    public Theme() {
        this(null, null, null, null);
    }

    public Theme(Integer id, String title, String photo, String state, String date, User winner, User author) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.state = state;
        this.date = date;
        this.winner = winner;
        this.author = author;
    }

    public Theme(String title, String photo, String state, String date, User winner) {
        this(null, title, photo, state, date, winner, null);
    }

    public Theme(String title, String photo, String state, String date) {
        this(null, title, photo, state, date, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (o instanceof Theme) {
            Theme other = (Theme) o;

            return  this.id.equals(other.id)        &&
                    this.title.equals(other.title)  &&
                    this.photo.equals(other.photo)  &&
                    this.state.equals(other.state)  &&
                    this.date.equals(other.date)    &&
                    this.winner == other.winner;

        }

        return false;
    }
}
