package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDAmenagementDaoImpl implements TypeDAmenagementDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  /**
   * Constructeur Type D'amenagement Dao
   * 
   * @param daoServices
   */
  public TypeDAmenagementDaoImpl(DaoServices daoServices) {
    this.services = daoServices;
    this.factory = new FactoryImpl();
  }

  @Override
  public List<TypeDAmenagementDto> voirTypeDAmenagement() {
    List<TypeDAmenagementDto> listeType = new ArrayList<TypeDAmenagementDto>();
    String requeteSql = "SELECT * FROM init.types_amenagement";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          TypeDAmenagementDto type = factory.getTypeDAmenagementDto();
          type.setId(rs.getInt(1));
          type.setDescription(rs.getString(2));
          listeType.add(type);
        }
        return listeType;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }
}
