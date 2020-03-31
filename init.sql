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
    boite VARCHAR(3) NOT NULL,
    code_postal INTEGER NOT NULL,
    ville VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telephone VARCHAR(10) NOT NULL,
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
    etat VARCHAR(3) NOT NULL
);

CREATE TABLE init.amenagements(
    id_amenagement SERIAL PRIMARY KEY,
    id_type_amenagement INTEGER REFERENCES init.types_amenagement(id_type_amenagement) NOT NULL,
    id_devis INTEGER REFERENCES init.devis(id_devis) NOT NULL
);

CREATE TABLE init.photos(
    id_photo SERIAL PRIMARY KEY,
    id_amenagement INTEGER REFERENCES init.amenagements(id_amenagement),
    id_devis INTEGER REFERENCES init.devis(id_devis)
);

ALTER TABLE init.devis 
ADD CONSTRAINT photo_preferee
FOREIGN KEY (photo_preferee) REFERENCES init.photos(id_photo);

-- Insert 2 utilisateurs --
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'mbourag', 'Bouraga', 'Maria', 'Namur', 'maria.bouraga@student.vinci.be', '2020-03-03', 'mdp', NULL);
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'jdupont', 'Dupont', 'Jean', 'Bruxelles', 'samuYahoo@hotmail.be', '2020-03-03', '$2a$10$qx7UPfRmazPnRmNgO5fv/u/ZhxGwQhSmyXjVxBZQ55MCWQv5vBTEG', 'C');
-- Insert 1 type amenagement --
INSERT INTO init.types_amenagement VALUES(DEFAULT,'Amenagement d etang');
-- Insert 2 clients --
INSERT INTO init.clients VALUES(DEFAULT,'Vch','Samuel','Avenue des roses','13A','129',1040,'Bruxelles','samuYahoo@hotmail.be','0489685897',2);
-- Insert 2 devis --
INSERT INTO init.devis VALUES(DEFAULT,1,'2020-03-31',1500,NULL,'2 semaines','I');
--Insert 1 amenagement --
INSERT INTO init.amenagements VALUES(DEFAULT,1,1);
-- Insert 1 photos -- 
INSERT INTO init.photos VALUES(DEFAULT,1,1);





