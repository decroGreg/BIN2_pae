package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DaoServices;
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

  public ClientDaoImpl() {
    this.services = new DaoServicesImpl();
    this.factory = new FactoryImpl();
  }

  @Override
  public List<DevisDto> getDevisClient(ClientDto client) {
    List<DevisDto> listeDevis = new ArrayList<DevisDto>();
    int idClient = client.getIdClient();
    String requeteSQL = "SELECT * FROM init.devis WHERE id_client = ?";
    ps = services.getPreparedSatement(requeteSQL);
    try {
      ps.setInt(1, idClient);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          DevisDto devis = factory.getDevisDto();
          // devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          // devis.setMontant(rs.getDouble(4)); montant doit etre float
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          listeDevis.add(devis);
          String c = rs.getString(7);
          // devis.setEtat(c);

        }
        return listeDevis;
      }
    } catch (SQLException e) {
      throw new DalException(e.getMessage());
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
      throw new DalException(e.getMessage());
    }
  }
}
