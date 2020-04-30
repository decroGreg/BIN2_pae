package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  /**
   * Constructeur Client Dao Impl.
   * 
   * @param daoServices classe service.
   */
  public ClientDaoImpl(DaoServices daoServices, Factory factory) {
    this.services = daoServices;
    this.factory = factory;
  }

  @Override
  public List<ClientDto> voirTousClient() {
    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    String requeteSql = "SELECT * FROM init.clients";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ClientDto client = factory.getClientDto();
          client.setIdClient(rs.getInt(1));
          client.setNom(rs.getString(2));
          client.setPrenom(rs.getString(3));
          client.setRue(rs.getString(4));
          client.setNumero(rs.getString(5));
          client.setBoite(rs.getString(6));
          client.setCodePostal(rs.getInt(7));
          client.setVille(rs.getString(8));
          client.setEmail(rs.getString(9));
          client.setTelephone(rs.getString(10));
          client.setIdUtilisateur(rs.getInt(11));
          listeClient.add(client);
        }
        return listeClient;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public boolean createClient(ClientDto clientDto) {
    String requeteSql =
        "INSERT INTO init.clients VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, null)";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setString(1, clientDto.getNom());
      ps.setString(2, clientDto.getPrenom());
      ps.setString(3, clientDto.getRue());
      ps.setString(4, clientDto.getNumero());
      ps.setString(5, clientDto.getBoite());
      ps.setInt(6, clientDto.getCodePostal());
      ps.setString(7, clientDto.getVille());
      ps.setString(8, clientDto.getEmail());
      ps.setString(9, clientDto.getTelephone());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur de la creation du client : " + ex.getMessage());
    }
    return true;
  }

  @Override
  public List<ClientDto> voirClientAvecCritere(String nom, String ville, int codePostal) {

    String villeSql = "%" + ville + "%";
    String nomSql = "%" + nom + "%";
    String codePostalSql = "%" + codePostal + "%";
    String requestSql =
        "SELECT * FROM init.clients WHERE nom LIKE ? AND ville LIKE ? AND code_postal LIKE ?";
    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    if (ville == "") {
      villeSql = "%";
    }
    if (nom == "") {
      nomSql = "%";
    }
    if (codePostal == 0) {
      codePostalSql = "%";
    }
    ps = services.getPreparedSatement(requestSql);
    try {
      ps.setString(1, nomSql);
      ps.setString(2, villeSql);
      ps.setString(3, codePostalSql);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ClientDto client = factory.getClientDto();
          client.setIdClient(rs.getInt(1));
          client.setNom(rs.getString(2));
          client.setPrenom(rs.getString(3));
          client.setRue(rs.getString(4));
          client.setNumero(rs.getString(5));
          client.setBoite(rs.getString(6));
          client.setCodePostal(rs.getInt(7));
          client.setVille(rs.getString(8));
          client.setEmail(rs.getString(9));
          client.setTelephone(rs.getString(10));
          client.setIdUtilisateur(rs.getInt(11));
          listeClient.add(client);
        }
        return listeClient;
      }
    } catch (SQLException ex) {
      throw new DalException(
          "Erreur lors de la prise des utilisateurs avec criteres : " + ex.getMessage());
    }
  }

  @Override
  public ClientDto getClientMail(String email) {
    ClientDto clientDto = factory.getClientDto();
    String requeteSql = "SELECT * FROM init.clients WHERE email = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          clientDto.setIdClient(rs.getInt(1));
          clientDto.setNom(rs.getString(2));
          clientDto.setPrenom(rs.getString(3));
          clientDto.setRue(rs.getString(4));
          clientDto.setNumero(rs.getString(5));
          clientDto.setBoite(rs.getString(6));
          clientDto.setCodePostal(rs.getInt(7));
          clientDto.setVille(rs.getString(8));
          clientDto.setEmail(rs.getString(9));
          clientDto.setTelephone(rs.getString(10));
          clientDto.setIdUtilisateur(rs.getInt(11));
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    if (clientDto.getEmail() == null) {
      return null;
    }
    return clientDto;
  }
}
