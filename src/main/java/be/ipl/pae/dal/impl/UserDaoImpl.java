package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.DALException;

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

  public boolean createDevis(int idClient, DevisDto devis) {
    String requestSQL = "INSERT INTO init.devis VALUES (DEFAULT,?,?,?,?,?,?)";
    ps = services.getPreparedSatement(requestSQL);
    try {
      ps.setInt(1, idClient);
      ps.setTimestamp(2, devis.getDate());
      // ps.setFloat(3, devis.getMontant()); changer montant devis de double en float
      ps.setInt(4, devis.getIdPhotoPreferee()); // Changer photo dans la DB pour avoir une FK
      ps.setString(5, devis.getDureeTravaux());
      ps.setString(6, null);
      ps.execute();
    } catch (SQLException e) {
      throw new DALException("Erreur lors de l'ajout du devis : " + e.getMessage());
    }
    return true;
  }

  public List<DevisDto> voirTousDevis() {
    List<DevisDto> listeDevis = new ArrayList<DevisDto>();
    String requeteSQL = "SELECT * FROM init.devis";
    ps = services.getPreparedSatement(requeteSQL);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          DevisDto devis = factory.getDevisDto();
          // devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          // devis.setMontant(rs.getDouble(4)); montant doit etre float
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          /**
           * String c = rs.getString(7); devis.setEtat(c);
           **/
          listeDevis.add(devis);
        }
        return listeDevis;
      }
    } catch (SQLException e) {
      throw new DALException(e.getMessage());
    }
  }

  public List<UserDto> voirTousUser() {
    List<UserDto> listeUser = new ArrayList<UserDto>();
    String requeteSQL = "SELECT * FROM init.utilisateurs";
    ps = services.getPreparedSatement(requeteSQL);
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
          user.setMotDePasse(rs.getString(8));
          String status = rs.getString(9);
          user.setStatut(status.charAt(0));
          listeUser.add(user);
        }
        return listeUser;
      }
    } catch (SQLException e) {
      throw new DALException(e.getMessage());
    }
  }

  public List<ClientDto> voirTousClient() {
    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    String requeteSQL = "SELECT * FROM init.clients";
    ps = services.getPreparedSatement(requeteSQL);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ClientDto client = factory.getClientDto();
          client.setIdClient(rs.getInt(1));
          client.setNom(rs.getString(2));
          client.setPrenom(rs.getString(3));
          client.setRue(rs.getString(4));
          // client.setNumero(rs.getInt(5)); Conflit , numero est String dans le Bizz mais Int dans
          // la Db
          client.setBoite(rs.getString(6));
          // client.setCodePostal(rs.getInt(7)); Pareil que Numero
          client.setVille(rs.getString(8));
          client.setEmail(rs.getString(9));
          client.setTelephone(rs.getString(10));
          client.setIdUtilisateur(rs.getInt(11));
          listeClient.add(client);
        }
        return listeClient;
      }
    } catch (SQLException e) {
      throw new DALException(e.getMessage());
    }
  }

  public boolean lierUserClient(ClientDto client, UserDto user) {

    String requestSQL1 = "UPDATE init.utilisateurs SET statut='c' WHERE id_utilisateur=?";
    String requestSQL2 = "UPDATE init.clients SET id_utilisateur=? WHERE id_client=?";
    ps = services.getPreparedSatement(requestSQL1);
    try {
      ps.setInt(1, user.getIdUser());
      ps.execute();
    } catch (SQLException e) {
      throw new DALException(
          "Erreur lors de la liaison dans la table utilisateurs" + e.getMessage());
    }
    ps = services.getPreparedSatement(requestSQL2);
    try {
      ps.setInt(1, user.getIdUser());
      ps.setInt(2, client.getIdClient());
    } catch (SQLException e) {
      throw new DALException("Erreur lors de liaison dans la table client" + e.getMessage());
    }
    return true;
  }
}
