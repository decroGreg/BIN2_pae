package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.PhotoDao;

public class PhotoUccImpl implements PhotoUcc {
  private PhotoDao photoDao;
  private Factory bizFactory;
  private DaoServices daoServicesUcc;

  public PhotoUccImpl(PhotoDao photoDao, Factory bizFactory, DaoServices daoServicesUcc) {
    super();
    this.photoDao = photoDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }


}
