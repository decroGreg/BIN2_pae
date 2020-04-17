package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.Photo;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

public class PhotoUccImpl implements PhotoUcc {

  private PhotoDao photoDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet PhotoUccImpl.
   * 
   * @param photoDao le dao de photo.
   * @param bizFactory la biz factory.
   * @param daoServicesUcc le daoServices.
   */
  public PhotoUccImpl(PhotoDao photoDao, Factory bizFactory, DaoServicesUcc daoServicesUcc) {
    super();
    this.photoDao = photoDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  @Override
  public void ajouterPhotoAvantAmenagement(int idDevis, String urlPhoto, int idAmenagement) {
    Photo photo = (Photo) bizFactory.getPhotoDto();
    if (photo.checkPhoto()) {
      try {
        daoServicesUcc.demarrerTransaction();
        // photoDao.introduirePhotoAvantAmenagement(photo);
        daoServicesUcc.commit();
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
    } else {
      throw new BizException("Ajout de photo impossible, infos manquantes");
    }
  }
}
