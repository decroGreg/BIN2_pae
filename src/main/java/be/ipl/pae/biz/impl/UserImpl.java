package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.interfaces.User;

import org.mindrot.bcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserImpl implements User {

  private int idUser;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String email;
  private Timestamp dateInscription;
  private String motDePasse;
  private char statut;
  // private DAOClient DAO;

  /**
   * Cree un objet UserImpl
   * 
   * @param idUser l'id du user.
   * @param pseudo le pseudo du user.
   * @param nom le nom du user.
   * @param prenom le prenom du user.
   * @param ville la ville du user.
   * @param email l'email du user.
   * @param motDePasse le mot de passe du user.
   * @param statut le statut du user.
   */
  public UserImpl(int idUser, String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse, char statut) {
    super();
    this.idUser = idUser;
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
    this.ville = ville;
    this.email = email;
    this.motDePasse = motDePasse;
    this.statut = statut;
  }

  public UserImpl(int idUser, String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse) {
    this(idUser, pseudo, nom, prenom, ville, email, motDePasse, ' ');
  }

  public UserImpl() {
    super();
  }

  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
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

  public void setDateInscription(Timestamp dateInscription) {
    this.dateInscription = dateInscription;
  }

  @Override
  public boolean checkUser() {
    if (this == null || this.getEmail() == null || this.getMotDePasse() == null) {
      return false;
    }
    return true;
  }

  @Override
  public boolean checkEmail() {
    Pattern pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  @Override
  public boolean checkMotDePasse(String motDePasseDb) {
    if (BCrypt.checkpw(this.getMotDePasse(), motDePasseDb)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean encryptMotDePasse() {
    String salt = BCrypt.gensalt();
    String hashMotDePasse = BCrypt.hashpw(motDePasse, salt);
    if (hashMotDePasse != null && this.motDePasse != " ") {
      setMotDePasse(hashMotDePasse);
      return true;
    }
    return false;
  }

  @Override
  public boolean checkStatut(char statut) {
    if (statut != 'O' && statut != 'C') {
      return false;
    }
    return true;
  }
}
