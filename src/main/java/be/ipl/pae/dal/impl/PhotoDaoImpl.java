package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhotoDaoImpl implements PhotoDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public PhotoDaoImpl(DaoServices daoService) {
    this.services = daoService;
    this.factory = new FactoryImpl();
  }


  public boolean createPhoto(PhotoDto photoDto) {
    String requeteSql = "INSERT INTO init.photos VALUES(DEFAULT, null, ?, ?, ?)";
    ps = services.getPreparedSatement(requeteSql);
    try {
      // ps.setString(1, photoDto.getUrlPhoto());
      ps.setInt(1, photoDto.getIdAmenagement());
      ps.setInt(2, photoDto.getIdDevis());
      ps.setString(3, photoDto.getUrlPhoto());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException();
    }
    return true;
  }

}
