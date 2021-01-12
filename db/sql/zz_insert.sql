use photodb;

-- Insert the user settings along with their default value

INSERT INTO setting (label, default_value)
VALUES
    ("mail","Not provided"),
    ("birthday","Not provided"),
    ("gender","Not provided"),
    ("location","Not provided"),
    ("bio",""),
    ("victories","0"),
    ("points","0");


-- Insert default necessary values

INSERT INTO label (label) VALUES ("");

INSERT INTO theme (title, state, photo_url, winner, date)
VALUES
    ("Current theme", "active", "url", null, "2020-12-24");