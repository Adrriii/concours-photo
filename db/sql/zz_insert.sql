use photodb;

-- Insert the user settings along with their default value

INSERT INTO setting (label, default_value, default_public)
VALUES
    ("mail","Undefined",0),
    ("birthday","Undefined",0),
    ("gender","Undefined",0),
    ("location","Undefined location",0),
    ("bio","Currently no description provided",1),
    ("victories","0",1),
    ("points","0",1);


-- Insert default necessary values

INSERT INTO label (label) 
VALUES 
    ("Portrait"),
    ("Paysage"),
    ("Macro");