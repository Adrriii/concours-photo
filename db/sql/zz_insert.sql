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

INSERT INTO label (label) 
VALUES 
    ("Portrait"),
    ("Paysage"),
    ("Macro");

INSERT INTO theme (title, state, photo_url, winner, date)
VALUES
    ("Current theme", "active", "url", null, "2020-12-24"),
    ("Les chiens", "proposed", "url", null, "2020-12-24"),
    ("Les chats", "proposed", "url", null, "2020-12-24"),
    ("La cuisine", "proposed", "url", null, "2020-12-24"),
    ("Ancient theme", "ended", "url", null, "2020-12-24");


-- Insert admin accounts

INSERT INTO user (username,sha,userlevel,victories,score) 
VALUES
    ("Adri","f1ef21ffb3c4521188d2d08a8306bf71f87cbc8246ab296018f33c63b4f14e93",10,999,9999),
    ("coucou","37acbd7dc4f8b1dfe7954b59d775da26f4d017df28bde97ef34a77b013a5f0f6",10,999,9999),
    ("Alexandre","2",10,999,9999),
    ("JD","3",10,999,9999);

-- Insert Demo posts

INSERT INTO post (title, author, label, theme, photo_url, delete_url)
VALUES
    ("TestPost",1,"Portrait",1,"https://batiment.imag.fr/files/imag.png",""),
    ("Post",1,null,1,"https://cybersavoir.csdm.qc.ca/bibliotheques/files/2018/11/10_banques_dimages_gratuites_libres_de_droits-300x169.jpg",""),
    ("Chien",1,null,2,"https://media.nature.com/lw800/magazine-assets/d41586-020-01430-5/d41586-020-01430-5_17977552.jpg",""),
    ("OtherPost",2,"Macro",1,"https://www.canon.fr/media/IMG_5302-5325.tif_1200x800_tcm79-1374042.jpg","")
    ;

-- Insert Demo comments
    
INSERT INTO comment (author, post, parent, content)
VALUES
    (1, 1, null, "Je suis un super commentaire !"),
    (2, 1,  1, "Ouais c'est ça"),
    (3, 1,  null, "Super photo !"),
    (4, 1,  null, "Ouaip");