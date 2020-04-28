package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDaoImpl implements PhotoDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public PhotoDaoImpl(DaoServices daoService) {
    this.services = daoService;
    this.factory = new FactoryImpl();
  }


  public boolean introduirePhoto(PhotoDto photoDto) {
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

  public List<PhotoDto> voirTousPhotos() {
    List<PhotoDto> listePhoto = new ArrayList<PhotoDto>();
    String requeteSql = "SELECT * FROM init.photos";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDto photo = factory.getPhotoDto();
          photo.setIdPhoto(rs.getInt(1));
          // photo.setPhoto(rs.getString(2));
          photo.setIdAmenagement(rs.getInt(3));
          photo.setIdDevis(rs.getInt(4));
          photo.setUrlPhoto(rs.getString(5));
          listePhoto.add(photo);
        }
        return listePhoto;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

}
