package be.ipl.pae.dal.impl;

import java.sql.PreparedStatement;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;

public class PhotoDaoImpl {

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
