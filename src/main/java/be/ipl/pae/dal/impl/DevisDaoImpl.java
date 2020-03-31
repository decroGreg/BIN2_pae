package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.exceptions.DalException;

public class DevisDaoImpl implements DevisDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public DevisDaoImpl(DaoServices daoService) {
    this.services = daoService;
    this.factory = new FactoryImpl();
  }

  @Override
  public boolean createDevis(int idClient, DevisDto devis) {
    String requestSql = "INSERT INTO init.devis VALUES (DEFAULT,?,?,?,null,?,?)";
    ps = services.getPreparedSatement(requestSql);
    try {
      ps.setInt(1, idClient);
      ps.setTimestamp(2, devis.getDate());
      ps.setDouble(3, devis.getMontant());
      ps.setString(4, devis.getDureeTravaux());
      ps.setString(5, "I");
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors de l'ajout du devis : " + ex.getMessage());
    }
    return true;
  }

  @Override
  public List<DevisDto> voirTousDevis() {
    List<DevisDto> listeDevis = new ArrayList<DevisDto>();
    String requeteSql = "SELECT * FROM init.devis";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          DevisDto devis = factory.getDevisDto();
          devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          devis.setMontant(rs.getDouble(4));
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          devis.setEtat(Etat.valueOf(rs.getString(7)));

          listeDevis.add(devis);
        }
        return listeDevis;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public List<DevisDto> getDevisClient(ClientDto client) {
    List<DevisDto> listeDevis = new ArrayList<DevisDto>();
    int idClient = client.getIdClient();
    String requeteSql = "SELECT * FROM init.devis WHERE id_client = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idClient);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          DevisDto devis = factory.getDevisDto();
          devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          devis.setMontant(rs.getDouble(4));
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          devis.setEtat(Etat.valueOf(rs.getString(7)));
          listeDevis.add(devis);
        }
        return listeDevis;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  public boolean confirmerDateDevis(int idDevis) {
    String requeteSql = "UPDATE init.devis SET statut = 'DDI' WHERE id_devis = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idDevis);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors de la confirmation de la Date" + ex.getMessage());
    }
    return true;
  }
}
