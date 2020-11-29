package model;

public enum SettingName {
    MAIL("mail"),
    BIRTHDAY("birthday"),
    GENDER("gender"),
    LOCATION("location"),
    BIO("bio"),
    VICTORIES("victories"),
    POINTS("points");

    String value;

    SettingName(String value) {
        this.value = value;
    }
}