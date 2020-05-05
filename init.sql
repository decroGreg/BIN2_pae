DROP SCHEMA IF EXISTS init CASCADE;
CREATE SCHEMA init;

CREATE TABLE init.utilisateurs(
    id_utilisateur SERIAL PRIMARY KEY,
    pseudo VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL, 
    ville VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    date_inscription TIMESTAMP NOT NULL,
    mot_de_passe VARCHAR(100) NOT NULL,
    statut CHAR(1)
);

CREATE TABLE init.clients(
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    rue VARCHAR(100) NOT NULL,
    numero VARCHAR(100) NOT NULL, 
    boite VARCHAR(3),
    code_postal INTEGER NOT NULL,
    ville VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telephone VARCHAR(14) NOT NULL,
    id_utilisateur INTEGER REFERENCES init.utilisateurs(id_utilisateur)
);

CREATE TABLE init.types_amenagement(
    id_type_amenagement SERIAL PRIMARY KEY,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE init.devis(
    id_devis SERIAL PRIMARY KEY,
    id_client INTEGER REFERENCES init.clients(id_client) NOT NULL,
    date TIMESTAMP NOT NULL,
    montant DOUBLE PRECISION NOT NULL,
    photo_preferee INTEGER,
    duree_travaux VARCHAR(100) NOT NULL,
    etat VARCHAR(3) NOT NULL,
	date_debut_travaux TIMESTAMP
);

CREATE TABLE init.amenagements(
    id_amenagement SERIAL PRIMARY KEY,
    id_type_amenagement INTEGER REFERENCES init.types_amenagement(id_type_amenagement) NOT NULL,
    id_devis INTEGER REFERENCES init.devis(id_devis) NOT NULL
);

CREATE TABLE init.photos(
    id_photo SERIAL PRIMARY KEY,
    photo VARCHAR NOT NULL,
    id_amenagement INTEGER REFERENCES init.amenagements(id_amenagement),
    id_devis INTEGER REFERENCES init.devis(id_devis),
    visible BOOLEAN NOT NULL
);

ALTER TABLE init.devis 
ADD CONSTRAINT photo_preferee
FOREIGN KEY (photo_preferee) REFERENCES init.photos(id_photo);

-- Insert utilisateurs ouvriers --
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'bri', 'Lehmann', 'Brigitte', 'Wavre', 'brigitte.lehmann@vinci.be', '2020-04-01', '$2a$10$/fddOs4BcbDWhP5FthwITO0ZP8KscJtoRy7DPLrqXPlhN77VE3pPy', 'O');
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'lau', 'Leleux', 'Laurent', 'Bruxelles', 'Laurent.leleux@vinci.be', '2020-04-01', '$2a$10$zRvDsdhamQav9AYGZmdq1u39maqXerh5oFHXUqPYlRuZ7JYF2T.8a', 'O');
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'chri', 'Damas', 'Christophe', 'Bruxelles', 'christophe.damas@vinci.be', '2020-04-01', '$2a$10$TkdqcAe6BmOtPqHVkt5l4urvHgddQdcz0cmCfAExGwM6QCWGgUqT6', 'O');

--Insert utilisateurs clients --
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'achil', 'Ile', 'Achille', 'Verviers', 'ach.ile@gmail.com', '2020-04-01', '$2a$10$W.N5Uvvpll4VQPZ0qn4J0O9EBRwOhmQ6f7RL0WPg2D5XpeJIlkNfO', 'C');
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'bazz', 'Ile', 'Basile', 'Liege', 'bas.ile@gmail.be', '2020-04-01', '$2a$10$W.N5Uvvpll4VQPZ0qn4J0O9EBRwOhmQ6f7RL0WPg2D5XpeJIlkNfO', 'C');
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'caro', 'Line', 'Caroline', 'Stoumont', 'caro.line@hotmail.com', '2020-04-01', '$2a$10$W.N5Uvvpll4VQPZ0qn4J0O9EBRwOhmQ6f7RL0WPg2D5XpeJIlkNfO', 'C');
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'theo', 'Ile', 'Theophile', 'Stoumont', 'theo.phile@proximus.be', '2020-04-29', '$2a$10$YeRA34j8895.iziUYFgP1OwKv4It0E.ijit0poPToxZknTlzdesCG', 'C');

--Insert clients--

INSERT INTO init.clients VALUES(DEFAULT, 'Line', 'Caroline', 'Rue de l''Eglise', 11, NULL, 4987, 'Stoumont', 'caro.line@hotmail.com', '080.12.56.78', 6);
INSERT INTO init.clients VALUES(DEFAULT, 'Ile', 'Theophile', 'Rue de Renkin', 7, NULL, 4800, 'Verviers', 'theo.phile@proximus.be', '087.25.69.74', 7);
INSERT INTO init.clients VALUES(DEFAULT, 'Ile', 'Basile', 'Rue des Minieres', 45, NULL, 4800, 'Verviers', 'bas.ile@gmail.be', '087.12.34.56', 5);




--Insert types d'aménagements--
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Amenagement de jardin de ville');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Amenagement de jardin');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Amenagement de parc paysagiste');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Creation de potagers');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Entretien de vergers haute-tige');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Entretien de vergers basse-tige');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Amenagement d''etang');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Installation de systeme d''arrosage automatique');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Terrasses en bois');
INSERT INTO init.types_amenagement VALUES(DEFAULT, 'Terrasses en pierres naturelles');


--Insert devis--

INSERT INTO init.devis VALUES(DEFAULT, 1, '2018-12-11', 4260, NULL, '5 jours', 'V', '2019-03-01');
INSERT INTO init.devis VALUES(DEFAULT, 1, '2018-12-15', 18306, NULL, '25 jours', 'V', '2019-03-15');
INSERT INTO init.devis VALUES(DEFAULT, 1, '2019-12-11', 8540, NULL, '10 jours', 'FD', '2020-03-30');
INSERT INTO init.devis VALUES(DEFAULT, 2, '2020-01-10', 6123, NULL, '7 jours', 'FF', '2020-03-02');
INSERT INTO init.devis VALUES(DEFAULT, 3, '2020-03-30', 895, NULL, '1 jour', 'I', NULL);
INSERT INTO init.devis VALUES(DEFAULT, 3, '2018-12-19', 1500, NULL, '2 jours', 'V', '2019-03-20');


--Insert photos avant amenagements--
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 1, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 2, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 2, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 3, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 3, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 3, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 5, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', NULL, 6, false);


--Insert aménagements--

INSERT INTO init.amenagements VALUES(DEFAULT, 6, 1);
INSERT INTO init.amenagements VALUES(DEFAULT, 5, 2);
INSERT INTO init.amenagements VALUES(DEFAULT, 3, 3);
INSERT INTO init.amenagements VALUES(DEFAULT, 1, 4);
INSERT INTO init.amenagements VALUES(DEFAULT, 8, 4);
INSERT INTO init.amenagements VALUES(DEFAULT, 1, 5);
INSERT INTO init.amenagements VALUES(DEFAULT, 5, 6);

--Insert photos apres amenagements--
    --Entretien de vergers basse-tige Caro--
INSERT INTO init.photos VALUES(DEFAULT,' ', 1, 1, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 1, 1, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 1, 1, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 1, 1, true);
INSERT INTO init.photos VALUES(DEFAULT,' ', 1, 1, true); --photo-pref--

    --Entretien de vergers haute-tige Caro--
INSERT INTO init.photos VALUES(DEFAULT,' ' , 2, 2, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 2, 2, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 2, 2, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 2, 2, false);
INSERT INTO init.photos VALUES(DEFAULT,' ' , 2, 2, true);  --photo pref--

    --Entretien de vergers haute-tige Basile--
INSERT INTO init.photos VALUES(DEFAULT,' ', 7, 6, false);
INSERT INTO init.photos VALUES(DEFAULT, ' ', 7, 6, true);
INSERT INTO init.photos VALUES(DEFAULT, ' ', 7, 6, true);  --photo pref--    



--Insert photos preferees--
UPDATE init.devis SET photo_preferee = 13 WHERE id_devis = 1;
UPDATE init.devis SET photo_preferee = 18 WHERE id_devis = 2;
UPDATE init.devis SET photo_preferee = 21 WHERE id_devis = 6;




