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
    String requeteSql = "INSERT INTO init.photos VALUES(DEFAULT, ?, ?, ?, ?)";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setString(1, photoDto.getUrlPhoto());
      ps.setInt(2, photoDto.getIdAmenagement());
      ps.setInt(3, photoDto.getIdDevis());
      ps.setBoolean(4, photoDto.isVisible());
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException();
    }
    return true;
  }

  @Override
  public List<PhotoDto> voirTousPhotos() {
    List<PhotoDto> listePhoto = new ArrayList<PhotoDto>();
    String requeteSql = "SELECT * FROM init.photos";
    ps = services.getPreparedSatement(requeteSql);
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDto photo = factory.getPhotoDto();
          photo.setIdPhoto(rs.getInt(1));
          photo.setUrlPhoto(rs.getString(2));
          photo.setIdAmenagement(rs.getInt(3));
          photo.setIdDevis(rs.getInt(4));
          photo.setVisible(rs.getBoolean(5));
          listePhoto.add(photo);
        }
        return listePhoto;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  public List<PhotoDto> voirPhotoSonJardin(int idClient) {
    List<PhotoDto> listePhoto = new ArrayList<PhotoDto>();
    String requeteSql = "SELECT p.* FROM init.photos p , init.devis d"
        + "            WHERE p.id_devis = d.id_devis AND d.id_client = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idClient);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDto photo = factory.getPhotoDto();
          photo.setIdPhoto(rs.getInt(1));
          photo.setUrlPhoto(rs.getString(2));
          photo.setIdAmenagement(rs.getInt(3));
          photo.setIdDevis(rs.getInt(4));
          photo.setVisible(rs.getBoolean(5));
          listePhoto.add(photo);
        }
        return listePhoto;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

  @Override
  public List<PhotoDto> voirPhotoTypeDAmenagement(int idTypeAmenagement) {
    List<PhotoDto> listePhoto = new ArrayList<PhotoDto>();
    String requeteSql =
        "SELECT p.id_photo , p.photo , p.id_amenagement , p.id_devis , p.visible FROM init.photos p , init.amenagements a "
            + "WHERE a.id_amenagement = p.id_amenagement AND a.id_type_amenagement = ?";
    ps = services.getPreparedSatement(requeteSql);
    try {
      ps.setInt(1, idTypeAmenagement);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDto photo = factory.getPhotoDto();
          photo.setIdPhoto(rs.getInt(1));
          photo.setUrlPhoto(rs.getString(2));
          photo.setIdAmenagement(rs.getInt(3));
          photo.setIdDevis(rs.getInt(4));
          photo.setVisible(rs.getBoolean(5));
          listePhoto.add(photo);
        }
        return listePhoto;
      }
    } catch (SQLException ex) {
      throw new DalException(ex.getMessage());
    }
  }

}
