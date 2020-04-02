package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

  private PreparedStatement ps;
  private DaoServices services;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String mail;
  private String motDePasse;
  private Factory factory;
  private int id;

  public UserDaoImpl(DaoServices daoServices) {
    this.services = daoServices;
    factory = new FactoryImpl();
  }

  @Override
  public UserDto getUserConnexion(String email) {
    UserDto userD = factory.getUserDto();
    String requeteSql = "SELECT * FROM init.utilisateurs WHERE email = ? AND statut IS NOT NULL";
    ps = services.getPreparedSatement(requeteSql);
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
          mail = rs.getString(6);
          motDePasse = rs.getString(8);
        }
        userD.setPseudo(pseudo);
        System.out.println(pseudo);
        userD.setNom(nom);
        userD.setPrenom(prenom);
        userD.setVille(ville);
        userD.setEmail(mail);
        userD.setMotDePasse(motDePasse);
        userD.setIdUser(id);
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    if (userD.getEmail() == null) {
      return null;
    }
    return userD;
  }

  public boolean createInscription(UserDto user) {
    String requestSql = "INSERT INTO init.utilisateurs VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    ps = services.getPreparedSatement(requestSql);
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
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.exit(500);
      return false;
    }
    return true;
  }


  public List<UserDto> voirTousUser() {
    List<UserDto> listeUser = new ArrayList<UserDto>();
    String requeteSql = "SELECT * FROM init.utilisateurs";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UserDto user = factory.getUserDto();
          user.setIdUser(rs.getInt(1));
          user.setPseudo(rs.getString(2));
          user.setNom(rs.getString(3));
          user.setPrenom(rs.getString(4));
          user.setVille(rs.getString(5));
          user.setEmail(rs.getString(6));
          user.setDateInscription(rs.getTimestamp(7));
          user.setMotDePasse(null);
          String status = rs.getString(9);
          if (status != null) {
            user.setStatut(status.charAt(0));
          }
          listeUser.add(user);
        }
        return listeUser;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  public boolean lierUserUtilisateur(ClientDto client, UserDto user, Character etat) {

    String requeteSql1;
    String requeteSql2;
    if (etat == 'O') {
      requeteSql1 = "UPDATE init.utilisateurs SET statut='O' WHERE id_utilisateur=?";
    } else {
      requeteSql1 = "UPDATE init.utilisateurs SET statut='C' WHERE id_utilisateur=?";
      if (client != null) {
        requeteSql2 = "UPDATE init.clients SET id_utilisateur=? WHERE id_client=?";
        ps = services.getPreparedSatement(requeteSql2);
        try {
          ps.setInt(1, user.getIdUser());
          ps.setInt(2, client.getIdClient());
          ps.execute();
        } catch (SQLException ex) {
          throw new DalException("Erreur lors de liaison dans la table client" + ex.getMessage());
        }
      }
    }
    ps = services.getPreparedSatement(requeteSql1);
    try {
      ps.setInt(1, user.getIdUser());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException(
          "Erreur lors de la liaison dans la table utilisateurs" + ex.getMessage());
    }
    return true;
  }

  @Override
  public boolean lierClientUser(int client, int user) {
    String requeteSql = "UPDATE init.clients SET id_utilisateur=? WHERE id_client=?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, user);
      ps.setInt(2, client);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors de liaison dans la table client" + ex.getMessage());
    }
    return true;
  }

  public boolean confirmerUtilisateur(UserDto user, Character etat) {
    String requeteSql;
    if (etat == 'O') {
      requeteSql = "UPDATE init.utilisateurs SET statut='O' WHERE id_utilisateur=?";
    } else {
      requeteSql = "UPDATE init.utilisateurs SET statut='C' WHERE id_utilisateur=?";
    }
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, user.getIdUser());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException(
          "Erreur lors de la liaison dans la table utilisateurs" + ex.getMessage());
    }
    return true;

  }

  public List<UserDto> voirUserPasConfirmer() {
    List<UserDto> listeUser = new ArrayList<UserDto>();
    String requestSql = "SELECT * FROM init.utilisateurs WHERE statut IS NULL ";
    ps = services.getPreparedSatement(requestSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UserDto user = factory.getUserDto();
          user.setIdUser(rs.getInt(1));
          user.setPseudo(rs.getString(2));
          user.setNom(rs.getString(3));
          user.setPrenom(rs.getString(4));
          user.setVille(rs.getString(5));
          user.setEmail(rs.getString(6));
          user.setDateInscription(rs.getTimestamp(7));
          user.setMotDePasse(null);
          listeUser.add(user);
        }
        return listeUser;
      }
    } catch (SQLException ex) {
      throw new DalException(
          "Erreur lors de la prise des utilisateurs non confirmer : " + ex.getMessage());
    }

  }
}
