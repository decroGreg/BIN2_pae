package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.UserDto;
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
  private Factory factory;

  /**
   * Constructeur User Dao .
   * 
   * @param daoServices classe service.
   */
  public UserDaoImpl(DaoServices daoServices, Factory factory) {
    this.services = daoServices;
    this.factory = factory;
  }

  @Override
  public UserDto getUserViaId(int id) {
    UserDto userD = factory.getUserDto();
    String requeteSql = "SELECT * FROM init.utilisateurs WHERE id_utilisateur=?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          userD.setIdUser(rs.getInt(1));
          userD.setPseudo(rs.getString(2));
          userD.setNom(rs.getString(3));
          userD.setPrenom(rs.getString(4));
          userD.setVille(rs.getString(5));
          userD.setEmail(rs.getString(6));
          String status = rs.getString(9);
          if (status != null) {
            userD.setStatut(status.charAt(0));
          }
        }
      } catch (SQLException ex) {
        throw new DalException(ex.getMessage());
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
    return userD;
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

          userD.setIdUser(rs.getInt(1));
          userD.setPseudo(rs.getString(2));
          userD.setNom(rs.getString(3));
          userD.setPrenom(rs.getString(4));
          userD.setVille(rs.getString(5));
          userD.setEmail(rs.getString(6));
          userD.setMotDePasse(rs.getString(8));
          String status = rs.getString(9);
          if (status != null) {
            userD.setStatut(status.charAt(0));
          }
        }
      } catch (SQLException ex) {
        throw new DalException(ex.getMessage());
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
    if (userD.getEmail() == null) {
      return null;
    }
    return userD;
  }

  @Override
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
      throw new DalException(ex.getMessage());
    }
    return true;
  }


  @Override
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

  @Override
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

  @Override
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

  @Override
  public List<UserDto> voirUserAvecCritere(String nom, String ville) {

    String villeSql = "%" + ville + "%";
    String nomSql = "%" + nom + "%";
    String requestSql = "SELECT * FROM init.utilisateurs WHERE nom LIKE ? AND ville LIKE ? ";
    List<UserDto> listeUser = new ArrayList<UserDto>();
    if (ville == null) {
      villeSql = "%";
    }
    if (nomSql == null) {
      nomSql = "%";
    }
    ps = services.getPreparedSatement(requestSql);
    try {
      ps.setString(1, nomSql);
      ps.setString(2, villeSql);
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
          "Erreur lors de la prise des utilisateurs avec criteres : " + ex.getMessage());
    }
  }
}
