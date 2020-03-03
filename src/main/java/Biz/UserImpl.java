package Biz;

import java.sql.Timestamp;
import DAL.DAOClient;

public class UserImpl implements User {

  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String email;
  private Timestamp dateInscription;
  private String motDePasse;
  private char statut;
  private DAOClient DAO;



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
    this.DAO = new DAOClient();
  }

  public UserImpl(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse) {
    this(pseudo, nom, prenom, ville, email, motDePasse, ' ');
  }

  public UserDTO checkEmail(String email) {

    return DAO.getPreparedStatementConnexion(email);
  }

  public boolean checkMotDePasse(String motDePasseAVerifier, String motDePasse) {

    return BCrypt.checkpw(motDePasseAVerifier, motDePasse);
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  @Override
  public String getMotDepasse() {
    return this.getMotDepasse();
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public DAOClient getDAO() {
    return DAO;
  }

}
