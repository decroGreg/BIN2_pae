package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.PhotoDao;

public class PhotoUccImpl implements PhotoUcc {
  private PhotoDao photoDao;
  private Factory factory;
  private DaoServices daoServicesUcc;

  public PhotoUccImpl(PhotoDao photoDao, Factory factory, DaoServices daoServicesUcc) {
    super();
    this.photoDao = photoDao;
    this.factory = factory;
    this.daoServicesUcc = daoServicesUcc;
  }


}
