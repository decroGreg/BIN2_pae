package be.ipl.pae.biz.impl;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.bcrypt.BCrypt;
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

  @Override
  public String getPseudo() {
    return pseudo;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getPrenom() {
    return prenom;
  }

  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @Override
  public String getVille() {
    return ville;
  }

  @Override
  public void setVille(String ville) {
    this.ville = ville;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getMotDePasse() {
    return motDePasse;
  }

  @Override
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  @Override
  public char getStatut() {
    return statut;
  }

  @Override
  public void setStatut(char statut) {
    this.statut = statut;
  }

  @Override
  public Timestamp getDateInscription() {
    return dateInscription;
  }

  public boolean checkUser() {
    if (this == null || this.getEmail() == null || this.getMotDePasse() == null) {
      return false;
    }
    return true;
  }

  public boolean checkEmail() {
    Pattern pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public boolean checkMotDePasse(String motDePasseDb) {
    if (BCrypt.checkpw(this.getMotDePasse(), motDePasseDb)) {
      return true;
    }
    return false;
  }

  public boolean encryptMotDePasse() {
    String salt = BCrypt.gensalt();
    String hashMotDePasse = BCrypt.hashpw(motDePasse, salt);
    if (hashMotDePasse != null) {
      setMotDePasse(hashMotDePasse);
      return true;
    }
    return false;
  }
}
