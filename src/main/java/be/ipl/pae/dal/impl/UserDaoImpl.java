package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;

public class UserDaoImpl implements UserDao {

  private PreparedStatement ps;
  private DaoServices services;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String eMail;
  private String motDePasse;
  private Factory factory;
  private int id;

  public UserDaoImpl() {
    this.services = new DaoServicesImpl();
    factory = new FactoryImpl();
  }

  @Override
  public UserDto getUserConnexion(String email) {
    UserDto userD = factory.getUserDto();
    String requeteSQL = "SELECT * FROM init.utilisateurs WHERE email = ?";
    ps = services.getPreparedSatement(requeteSQL);
    try {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
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
    if (userD.getEmail() == null) {
      return null;
    }
    return userD;
  }

  public boolean createInscription(UserDto user) {
    String requestSQL = "INSERT INTO init.utilisateurs VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    ps = services.getPreparedSatement(requestSQL);
    try {
      ps.setString(1, user.getPseudo());
      ps.setString(2, user.getNom());
      ps.setString(3, user.getPrenom());
      ps.setString(4, user.getVille());
      ps.setString(5, user.getEmail());
      ps.setTimestamp(6, user.getDateInscription());
      ps.setString(7, user.getMotDePasse());
      ps.setString(8, null);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(500);
      return false;
    }
    return true;
  }

}
