package Biz;

import java.sql.Timestamp;

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

  @Override
  public String getEmail() {
    // TODO Auto-generated method stub
    return this.email;
  }

  @Override
  public String getMotDepasse() {
    // TODO Auto-generated method stub
    return this.getMotDepasse();
  }

  @Override
  public void setEmail(String email) {
    // TODO Auto-generated method stub
    this.email = email;
  }

  @Override
  public void setMotDePasse(String motDePasse) {
    // TODO Auto-generated method stub
    this.motDePasse = motDePasse;
  }
}
