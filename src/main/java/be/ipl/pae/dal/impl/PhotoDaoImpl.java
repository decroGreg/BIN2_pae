package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.PhotoDao;

import java.sql.PreparedStatement;

public class PhotoDaoImpl implements PhotoDao {

  private PreparedStatement ps;
  private DaoServices services;
  private Factory factory;

  public PhotoDaoImpl(DaoServices daoService) {
    this.services = daoService;
    this.factory = new FactoryImpl();
  }

  /**
   * public boolean createPhoto(PhotoDto photoDto){
   * 
   * }
   **/
}
