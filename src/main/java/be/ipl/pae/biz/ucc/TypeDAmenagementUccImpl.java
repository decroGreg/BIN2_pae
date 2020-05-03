package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.Collections;
import java.util.List;

public class TypeDAmenagementUccImpl implements TypeDAmenagementUcc {

  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;
  private TypeDAmenagementDao typeDAmenagementDao;

  /**
   * Cree un objet TypeDAmenagementUccImpl.
   * 
   * @param bizFactory la factory.
   * @param typeDAmenagementDao le dao de type d'amenagement.
   * @param daoServicesUcc le dao services.
   */
  public TypeDAmenagementUccImpl(Factory bizFactory, TypeDAmenagementDao typeDAmenagementDao,
      DaoServicesUcc daoServicesUcc) {
    super();
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
    this.typeDAmenagementDao = typeDAmenagementDao;
  }

  @Override
  public List<TypeDAmenagementDto> voirTypeDAmenagement() {
    List<TypeDAmenagementDto> typeDAmenagement = null;
    try {
      daoServicesUcc.demarrerTransaction();
      typeDAmenagement = typeDAmenagementDao.voirTypeDAmenagement();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(typeDAmenagement);
  }

  @Override
  public void ajouterTypeAmenagement(String descriptif) {
    if (descriptif != null) {
      try {
        daoServicesUcc.demarrerTransaction();
        if (!typeDAmenagementDao.createTypeAmenagement(descriptif)) {
          daoServicesUcc.commit();
          throw new BizException("La création du type d'aménagement a échoué");
        }
        daoServicesUcc.commit();
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
    } else {
      throw new BizException("La description est vide");
    }
  }

}
