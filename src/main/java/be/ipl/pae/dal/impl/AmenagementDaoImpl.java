package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.DalException;

public class AmenagementDaoImpl implements AmenagementDao {
  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public AmenagementDaoImpl(DaoServices daoServices) {
    this.services = daoServices;
    this.factory = new FactoryImpl();
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
}
