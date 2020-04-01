package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.DalException;

import java.util.List;

public class AmenagementUccImpl implements AmenagementUcc {
  private AmenagementDao amenagementDao;
  private Factory amenagementFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet AmenagementUccImpl.
   * 
   * @param amenagementDao l amenagement dao.
   * @param amenagementFactory la factory.
   * @param daoServicesUcc le dal services.
   */
  public AmenagementUccImpl(AmenagementDao amenagementDao, Factory amenagementFactory,
      DaoServicesUcc daoServicesUcc) {
    super();
    this.amenagementDao = amenagementDao;
    this.amenagementFactory = amenagementFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  public void ajouterAmenagement(List<Integer> listeIdTypeAmenagement, int idDevis) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (!listeIdTypeAmenagement.isEmpty()) {
        for (Integer idTypeAmenagement : listeIdTypeAmenagement) {
          amenagementDao.createAmenagement(idTypeAmenagement, idDevis);
        }
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }
}
