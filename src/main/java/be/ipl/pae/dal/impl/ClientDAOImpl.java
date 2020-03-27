package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDAO;
import be.ipl.pae.dal.interfaces.DAOServices;
import be.ipl.pae.exceptions.DALException;

public class ClientDAOImpl implements ClientDAO {

  private PreparedStatement ps;
  private DAOServices services;
  private Factory factory;

  public ClientDAOImpl() {
    this.services = new DAOServicesImpl();
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
          devis.setMontant(rs.getDouble(4));
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          listeDevis.add(devis);
          String c = rs.getString(7);
          // devis.setEtat(c);

        }
        return listeDevis;
      }
    } catch (SQLException e) {
      throw new DALException(e.getMessage());
    }
  }
}
