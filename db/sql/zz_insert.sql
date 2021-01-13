use photodb;

-- Insert the user settings along with their default value

INSERT INTO setting (label, default_value, default_public)
VALUES
    ("mail","Not provided",0),
    ("birthday","Not provided",0),
    ("gender","Not provided",0),
    ("location","Not provided",0),
    ("bio","",1),
    ("victories","0",1),
    ("points","0",1);


-- Insert default necessary values

INSERT INTO label (label) VALUES ("");

INSERT INTO theme (title, state, photo_url, winner, date)
VALUES
    ("Current theme", "active", "url", null, "2020-12-24");