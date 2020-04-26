package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.Collections;
import java.util.List;

public class AmenagementUccImpl implements AmenagementUcc {
  private AmenagementDao amenagementDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet AmenagementUccImpl.
   * 
   * @param amenagementDao l amenagement dao.
   * @param bizFactory la factory.
   * @param daoServicesUcc le dal services.
   */
  public AmenagementUccImpl(AmenagementDao amenagementDao, Factory bizFactory,
      DaoServicesUcc daoServicesUcc) {
    super();
    this.amenagementDao = amenagementDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  @Override
  public void ajouterAmenagement(List<String> listeIdTypeAmenagement, int idDevis) {
    if (idDevis >= 1) {
      try {
        daoServicesUcc.demarrerTransaction();
        if (!listeIdTypeAmenagement.isEmpty()) {
          for (String idTypeAmenagement : listeIdTypeAmenagement) {
            amenagementDao.createAmenagement(Integer.parseInt(idTypeAmenagement), idDevis);
          }
        }
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException();
      }
      daoServicesUcc.commit();
    } else {
      throw new BizException("L'id du devis " + idDevis + " n'est pas correcte");
    }
  }

  @Override
  public List<AmenagementDto> voirAmenagement() {
    List<AmenagementDto> amenagements;
    try {
      daoServicesUcc.demarrerTransaction();
      amenagements = amenagementDao.voirTousAmenagement();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(amenagements);
  }
}
