package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoImpl implements DevisDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory bizfactory;

  /**
   * Constructeur Devis Dao.
   * 
   * @param daoService classe service.
   */
  public DevisDaoImpl(DaoServices daoService) {
    this.services = daoService;
    this.bizfactory = new FactoryImpl();
  }


  @Override
  public boolean createDevis(int idClient, DevisDto devis) {
    String requestSql = "INSERT INTO init.devis VALUES (DEFAULT,?,?,?,null,?,?,?)";
    ps = services.getPreparedSatement(requestSql);
    try {
      ps.setInt(1, idClient);
      ps.setTimestamp(2, devis.getDate());
      ps.setDouble(3, devis.getMontant());
      ps.setString(4, devis.getDureeTravaux());
      ps.setString(5, "I");
      ps.setTimestamp(6, devis.getDateDebutTravaux());
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
          DevisDto devis = bizfactory.getDevisDto();
          devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          devis.setMontant(rs.getDouble(4));
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          devis.setEtat(Etat.valueOf(rs.getString(7)));
          devis.setDateDebutTravaux(rs.getTimestamp(8));
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
          DevisDto devis = bizfactory.getDevisDto();
          devis.setIdDevis(rs.getInt(1));
          devis.setIdClient(rs.getInt(2));
          devis.setDate(rs.getTimestamp(3));
          devis.setMontant(rs.getDouble(4));
          devis.setIdPhotoPreferee(rs.getInt(5));
          devis.setDureeTravaux(rs.getString(6));
          devis.setEtat(Etat.valueOf(rs.getString(7)));
          devis.setDateDebutTravaux(rs.getTimestamp(8));
          listeDevis.add(devis);
        }
        return listeDevis;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public boolean confirmerDateDevis(DevisDto devis) {
    String requeteSql = "UPDATE init.devis SET etat = ?,date_debut_travaux = ? WHERE id_devis = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setString(1, devis.getEtat().name());
      ps.setInt(3, devis.getIdDevis());
      ps.setTimestamp(2, devis.getDateDebutTravaux());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors de la confirmation de la Date" + ex.getMessage());
    }
    return true;
  }

  public boolean changerEtatDevis(DevisDto devis) {
    String requeteSql = "UPDATE init.devis SET etat = ? WHERE id_devis = ?";
    // int idDevis = devis.getIdDevis();
    ps = services.getPreparedSatement(requeteSql);
    // String etat = devis.getEtat().name();
    try {
      ps.setString(1, devis.getEtat().name());
      ps.setInt(2, devis.getIdDevis());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors du changement d'etat d'un devis" + ex.getMessage());
    }
    return true;
  }

  public boolean ajouterPhotoPrefereeDevis(DevisDto devis, int idPhoto) {
    String requeteSql = "UPDATE init.devis SET photo_preferee = ? WHERE id_devis = ?";
    int idDevis = devis.getIdDevis();
    ps = services.getPreparedSatement(requeteSql);
    String etat = devis.getEtat().name();
    try {
      ps.setInt(1, idPhoto);
      ps.setInt(2, idDevis);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors du changement d'etat d'un devis" + ex.getMessage());
    }
    return true;
  }

  public DevisDto getDevisViaId(int idDevis) {
    DevisDto devisDto = bizfactory.getDevisDto();
    String requeteSql = "SELECT * FROM init.devis WHERE id_devis=?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idDevis);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          devisDto.setIdDevis(rs.getInt(1));
          devisDto.setIdClient(rs.getInt(2));
          devisDto.setDate(rs.getTimestamp(3));
          devisDto.setMontant(rs.getDouble(4));
          devisDto.setIdPhotoPreferee(rs.getInt(5));
          devisDto.setDureeTravaux(rs.getString(6));
          devisDto.setEtat(Etat.valueOf(rs.getString(7)));
          devisDto.setDateDebutTravaux(rs.getTimestamp(8));
        }
      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    } catch (SQLException sql) {
      sql.printStackTrace();
      System.exit(1);
    }
    return devisDto;
  }

  public boolean repousserDateDebut(DevisDto devis) {
    String requeteSql = "UPDATE init.devis SET date_debut_travaux = ? WHERE id_devis = ?";
    int idDevis = devis.getIdDevis();
    Timestamp dateDebut = devis.getDateDebutTravaux();
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setTimestamp(1, dateDebut);
      ps.setInt(2, idDevis);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors du changement de date debut du devis" + ex.getMessage());
    }
    return true;
  }

  @Override
  public int getIdDernierDevis() {
    int idDevis = 0;
    String requeteSql = "SELECT MAX(id_devis) FROM init.devis";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          idDevis = rs.getInt(1);
        }
      }
    } catch (SQLException ex) {
      throw new DalException("Erreur dans la recherche du dernier devis : " + ex.getMessage());
    }
    return idDevis;
  }
}

