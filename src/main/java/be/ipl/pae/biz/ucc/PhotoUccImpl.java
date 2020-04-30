package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.Photo;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.Collections;
import java.util.List;

public class PhotoUccImpl implements PhotoUcc {

  private PhotoDao photoDao;
  private DevisDao devisDao;
  private AmenagementDao amenagementDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet PhotoUccImpl.
   * 
   * @param photoDao le dao de photo.
   * @param devisDao le dao de devis.
   * @param amenagementDao le dao d'amanagement.
   * @param bizFactory la biz factory.
   * @param daoServicesUcc le daoServices.
   */
  public PhotoUccImpl(PhotoDao photoDao, DevisDao devisDao, AmenagementDao amenagementDao,
      Factory bizFactory, DaoServicesUcc daoServicesUcc) {
    super();
    this.photoDao = photoDao;
    this.devisDao = devisDao;
    this.amenagementDao = amenagementDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  @Override
  public void ajouterPhotoAvantAmenagement(int idDevis, String urlPhoto) {
    Photo photo = (Photo) bizFactory.getPhotoDto();
    photo.setIdDevis(idDevis);
    photo.setUrlPhoto(urlPhoto);
    photo.setVisible(false);
    if (photo.checkPhoto()) {
      try {
        daoServicesUcc.demarrerTransaction();
        photoDao.introduirePhoto(photo);
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
      daoServicesUcc.commit();
    } else {
      throw new BizException("Ajout de photo impossible, infos manquantes");
    }
  }

  @Override
  public void ajouterPhotoApresAmenagement(AmenagementDto amenagementDto, String urlPhoto,
      boolean visible) {
    try {
      daoServicesUcc.demarrerTransaction();
      DevisDto devis = devisDao.getDevisViaId(amenagementDto.getIdDevis());
      if (devis == null) {
        throw new BizException("Pas devis trouvé pour cette id");
      }
      Photo photo = (Photo) bizFactory.getPhotoDto();
      photo.setIdDevis(amenagementDto.getIdDevis());
      photo.setIdAmenagement(amenagementDto.getIdAmenagement());
      photo.setUrlPhoto(urlPhoto);
      if (devis.getEtat().equals(Etat.FF) && photo.checkPhoto()) {
        photoDao.introduirePhoto(photo);
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
  }

  @Override
  public List<PhotoDto> voirPhotos() {
    List<PhotoDto> photos;
    try {
      daoServicesUcc.demarrerTransaction();
      photos = photoDao.voirTousPhotos();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(photos);
  }

  @Override
  public List<PhotoDto> voirPhotoParTypeAmenagement(TypeDAmenagementDto typeAmenagementDto) {
    List<PhotoDto> photoParTypeAmenagement = null;
    try {
      daoServicesUcc.demarrerTransaction();
      photoParTypeAmenagement = photoDao.voirPhotoTypeDAmenagement(typeAmenagementDto.getId());
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(photoParTypeAmenagement);
  }

  @Override
  public List<PhotoDto> voirPhotoSonJardin(int idClient) {
    List<PhotoDto> photoDeTonJardin;
    try {
      daoServicesUcc.demarrerTransaction();
      photoDeTonJardin = photoDao.voirPhotoSonJardin(idClient);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(photoDeTonJardin);
  }
}
