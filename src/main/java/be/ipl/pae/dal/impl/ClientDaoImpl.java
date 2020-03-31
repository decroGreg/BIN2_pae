package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.exceptions.DalException;

public class ClientDaoImpl implements ClientDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public ClientDaoImpl(DaoServices daoServices) {
    this.services = daoServices;
    this.factory = new FactoryImpl();
  }

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
}
