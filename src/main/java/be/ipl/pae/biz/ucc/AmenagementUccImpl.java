package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;

public class AmenagementUccImpl implements AmenagementUcc {
  // private AmenagementDao amenagementDao;
  private Factory amenagementFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet AmenagementUccImpl.
   * 
   * @param amenagementFactory la factory.
   * @param daoServicesUcc la dalServices.
   */
  public AmenagementUccImpl(/** AmenagementDao amenagementDao, */
  Factory amenagementFactory, DaoServicesUcc daoServicesUcc) {
    super();
    // this.amenagementDao = amenagementDao;
    this.amenagementFactory = amenagementFactory;
    this.daoServicesUcc = daoServicesUcc;
  }
}
