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
    mot_de_passe VARCHAR(30) NOT NULL,
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
    etat CHAR(3) NOT NULL
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
INSERT INTO init.utilisateurs VALUES(DEFAULT, 'jdupont', 'Dupont', 'Jean', 'Bruxelles', 'jean.dupont@hotmail.com', '2020-03-03', 'mdpJean', NULL);