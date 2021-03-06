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
          ClientDto client = getClientDto(rs);
          listeClient.add(client);
        }
        return listeClient;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public List<String> nomClient() {
    List<String> listeNom = new ArrayList<String>();
    String requeteSql = "SELECT DISTINCT nom FROM init.clients";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String nom = rs.getString(1);
          listeNom.add(nom);
        }
      }
      return listeNom;
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
    int codePostalSql = codePostal;
    String requestSql = "SELECT * FROM init.clients WHERE nom LIKE ? AND ville LIKE ? ";
    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    if (ville == "") {
      villeSql = "%";
    }
    if (nom == "") {
      nomSql = "%";
    }
    if (codePostal != 0) {
      requestSql += " AND code_postal = ?";
    }
    ps = services.getPreparedSatement(requestSql);
    try {
      ps.setString(1, nomSql);
      ps.setString(2, villeSql);
      if (codePostal != 0) {
        ps.setInt(3, codePostalSql);
      }
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ClientDto client = getClientDto(rs);
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
          clientDto = getClientDto(rs);
        }
      } catch (SQLException ex) {
        throw new DalException(ex.getMessage());
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
    if (clientDto.getEmail() == null) {
      return null;
    }
    return clientDto;
  }

  @Override
  public ClientDto getClientById(int id) {
    ClientDto clientDto = factory.getClientDto();
    String requeteSql = "SELECT * FROM init.clients WHERE id_client = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          clientDto = getClientDto(rs);
        }
      } catch (SQLException ex) {
        throw new DalException(ex.getMessage());
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
    if (clientDto.getEmail() == null) {
      return null;
    }
    return clientDto;
  }

  @Override
  public List<String> getVilles() {
    List<String> listeVille = new ArrayList<String>();
    String requeteSql = "SELECT DISTINCT ville FROM init.clients";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String ville = rs.getString(1);
          listeVille.add(ville);
        }
        return listeVille;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public int getIdDernierClient() {
    int idClient = 0;
    String requeteSql = "SELECT MAX(id_client) FROM init.client";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          idClient = rs.getInt(1);
        }
      }
    } catch (SQLException ex) {
      throw new DalException("Erreur dans la recherche du dernier devis : " + ex.getMessage());
    }
    return idClient;
  }

  private ClientDto getClientDto(ResultSet rs) throws SQLException {
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
    return client;
  }
}
