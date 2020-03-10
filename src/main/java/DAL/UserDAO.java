package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.factory.UserFactoryImpl;
import be.ipl.pae.biz.interfaces.UserFactory;

public class UserDAO {

  private PreparedStatement trouverUtilisateurParEmail;
  private DAOServices services;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String eMail;
  private String motDePasse;
  private UserFactory factory;

  public UserDAO() {
    this.services = new DAOServices();
    factory = new UserFactoryImpl();
  }

  public UserDTO getPreparedStatementConnexion(String email) {
    UserDTO userD = null;
    trouverUtilisateurParEmail = services.tryPreparedSatement(trouverUtilisateurParEmail);
    try {
      trouverUtilisateurParEmail.setString(1, email);
      try (ResultSet rs = trouverUtilisateurParEmail.executeQuery()) {
        while (rs.next()) {
          pseudo = rs.getString(1);
          nom = rs.getString(2);
          prenom = rs.getString(3);
          ville = rs.getString(4);
          eMail = rs.getString(5);
          motDePasse = rs.getString(6);
        }
        userD = factory.getUserDTO();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return userD;
  }
}
