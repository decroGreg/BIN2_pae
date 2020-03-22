package DAL;

import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

  private PreparedStatement trouverUtilisateurParEmail;
  private DAOServices services;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String eMail;
  private String motDePasse;
  private Factory factory;
  private int id;

  public UserDAOImpl() {
    this.services = new DAOServicesImpl();
    factory = new FactoryImpl();
  }

  @Override
  public UserDTO getPreparedStatementConnexion(String email) {
    UserDTO userD = factory.getUserDTO();
    String requeteSQL = "SELECT * FROM init.utilisateurs WHERE email = ?";
    trouverUtilisateurParEmail = services.tryPreparedSatement(requeteSQL);
    try {
      trouverUtilisateurParEmail.setString(1, email);
      try (ResultSet rs = trouverUtilisateurParEmail.executeQuery()) {
        while (rs.next()) {
          id = rs.getInt(1);
          System.out.println(id);
          pseudo = rs.getString(2);
          nom = rs.getString(3);
          prenom = rs.getString(4);
          ville = rs.getString(5);
          eMail = rs.getString(6);
          motDePasse = rs.getString(8);
        }
        userD.setPseudo(pseudo);
        System.out.println(pseudo);
        userD.setNom(nom);
        userD.setPrenom(prenom);
        userD.setVille(ville);
        userD.setEmail(eMail);
        userD.setMotDePasse(motDePasse);
        userD.setIdUser(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return userD;
  }
}
