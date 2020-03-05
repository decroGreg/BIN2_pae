package be.ipl.pae.biz.impl;

import java.sql.Timestamp;
import be.ipl.pae.biz.interfaces.User;

public class UserImpl implements User {

  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String email;
  private Timestamp dateInscription;
  private String motDePasse;
  private char statut;
  // private DAOClient DAO;


  public UserImpl(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse, char statut) {
    super();
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
    this.ville = ville;
    this.email = email;
    this.motDePasse = motDePasse;
    this.statut = statut;
  }

  public UserImpl(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse) {
    this(pseudo, nom, prenom, ville, email, motDePasse, ' ');
  }

  public UserImpl() {
    super();
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public char getStatut() {
    return statut;
  }

  public void setStatut(char statut) {
    this.statut = statut;
  }

  public Timestamp getDateInscription() {
    return dateInscription;
  }

  public boolean checkEmail(String email) {
    // return DAO_client.checkEmail(email);
    return false;
  }

  public boolean checkMotDePasse(String motDePasse) {
    /*
     * String salt = BCrypt.gensalt(); String mdpHache = BCrypt.hashpw(motDePasseConnexion, salt);
     */
    // String password = DAO.getPassword();
    // return BCrypt.checkpw(motDePasse, );
    return false;
  }
}
