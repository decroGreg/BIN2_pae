package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmenagementDaoImpl implements AmenagementDao {
  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  /**
   * Constructeur Amenagement Dao.
   * 
   * @param daoServices classe service.
   */
  public AmenagementDaoImpl(DaoServices daoServices, Factory factory) {
    this.services = daoServices;
    this.factory = factory;
  }

  @Override
  public boolean createAmenagement(int idTypeAmenagement, int idDevis) {
    String requeteSql = "INSERT INTO init.amenagements VALUES (DEFAULT, ?,?)";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idTypeAmenagement);
      ps.setInt(2, idDevis);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("Erreur lors de la creation d'un amenagement : " + ex.getMessage());
    }
    return true;
  }

  @Override
  public List<AmenagementDto> voirTousAmenagement() {
    List<AmenagementDto> listeAmenagement = new ArrayList<AmenagementDto>();
    String requeteSql = "SELECT * FROM init.amenagements";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          AmenagementDto amenagement = factory.getAmenagementDto();
          amenagement.setIdAmenagement(rs.getInt(1));
          amenagement.setIdTypeAmenagement(rs.getInt(2));
          amenagement.setIdDevis(rs.getInt(3));
          listeAmenagement.add(amenagement);
        }
        return listeAmenagement;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }
}
