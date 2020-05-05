package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class TestPhotoUcc {
  PhotoDao photoDao;
  DevisDao devisDao;
  PhotoUcc photoUcc;
  PhotoDto photoDto;
  Constructor<?> photoDaoConstruct;
  Constructor<?> devisDaoConstruct;
  Constructor<?> photoUccConstruct;
  Factory bizFactory;
  DaoServicesUcc dalServices;

  {
    try {
      Config.init("test.properties");
    } catch (IOException ex) {

      ex.printStackTrace();
    }
  }


  @BeforeEach
  void setUp() throws Exception {
    bizFactory = (Factory) Class.forName(Config.getConfigPropertyAttribute(Factory.class.getName()))
        .getConstructor().newInstance();
    dalServices = (DaoServicesUcc) Class
        .forName(Config.getConfigPropertyAttribute(DaoServices.class.getName())).getConstructor()
        .newInstance();
    photoDto = bizFactory.getPhotoDto();
    devisDao = (DevisDao) Class.forName(Config.getConfigPropertyAttribute(DevisDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class)
        .newInstance(false, false, false, false, false, false, false, false, false, false, false);
    devisDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class);
    photoDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(PhotoDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class);
    photoUccConstruct = Class.forName(Config.getConfigPropertyAttribute(PhotoUcc.class.getName()))
        .getConstructor(PhotoDao.class, DevisDao.class, Factory.class, DaoServicesUcc.class);
  }

  @Test
  @DisplayName("urlPhoto null")
  void ajouterPhotoAvantAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(BizException.class,
        () -> photoUcc.ajouterPhotoAvantAmenagement(photoDto.getIdPhoto(), null));
  }

  @Test
  @DisplayName("idDevis incorrect")
  void ajouterPhotoAvantAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(BizException.class,
        () -> photoUcc.ajouterPhotoAvantAmenagement(0, photoDto.getUrlPhoto()));
  }

  @Test
  @DisplayName("test ok")
  void ajouterPhotoAvantAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    photoUcc.ajouterPhotoAvantAmenagement(photoDto.getIdPhoto(), photoDto.getUrlPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void ajouterPhotoAvantAmenagement4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, true);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> photoUcc.ajouterPhotoAvantAmenagement(photoDto.getIdPhoto(), photoDto.getUrlPhoto()));
  }

  @Test
  @DisplayName("amenagementDto null")
  void ajouterPhotoApresAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, false);
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class, () -> photoUcc.ajouterPhotoApresAmenagement(null,
        photoDto.getUrlPhoto(), photoDto.isVisible()));
  }

  @Test
  @DisplayName("devis null dans la methode")
  void ajouterPhotoApresAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, false);
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(BizException.class,
        () -> photoUcc.ajouterPhotoApresAmenagement(bizFactory.getAmenagementDto(),
            photoDto.getUrlPhoto(), photoDto.isVisible()));
  }

  @Test
  @DisplayName("test ok")
  void ajouterPhotoApresAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, false);
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        true, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    photoUcc.ajouterPhotoApresAmenagement(bizFactory.getAmenagementDto(), photoDto.getUrlPhoto(),
        photoDto.isVisible());
  }

  @Test
  @DisplayName("test dalException")
  void ajouterPhotoApresAmenagement4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(true, false, false, false, false, true);
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        true, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> photoUcc.ajouterPhotoApresAmenagement(bizFactory.getAmenagementDto(),
            photoDto.getUrlPhoto(), photoDto.isVisible()));
  }

  @Test
  @DisplayName("photos null")
  void voirPhotos() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class, () -> photoUcc.voirPhotos());
  }

  @Test
  @DisplayName("test ok")
  void voirPhotos2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, true, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertEquals(photoUcc.voirPhotos().get(0).getIdPhoto(), photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void voirPhotos3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, true, false, false, false, true);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class, () -> photoUcc.voirPhotos());
  }

  @Test
  @DisplayName("photosParTypeAmenagement null")
  void voirPhotoParTypeAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class,
        () -> photoUcc.voirPhotoParTypeAmenagement(bizFactory.getTypeDAmenagementDto()));
  }

  @Test
  @DisplayName("typeAmenagementDto null")
  void voirPhotoParTypeAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class, () -> photoUcc.voirPhotoParTypeAmenagement(null));
  }

  @Test
  @DisplayName("test ok")
  void voirPhotoParTypeAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, true, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertEquals(photoUcc.voirPhotoParTypeAmenagement(bizFactory.getTypeDAmenagementDto()).get(0)
        .getIdPhoto(), photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void voirPhotoParTypeAmenagement4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, true, false, false, true);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> photoUcc.voirPhotoParTypeAmenagement(bizFactory.getTypeDAmenagementDto()));
  }

  @Test
  @DisplayName("photoDeTonJardin null")
  void voirPhotoSonJardin() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class,
        () -> photoUcc.voirPhotoSonJardin(bizFactory.getClientDto().getIdClient()));
  }

  @Test
  @DisplayName("test ok")
  void voirPhotoSonJardin2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, true, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertEquals(
        photoUcc.voirPhotoSonJardin(bizFactory.getClientDto().getIdClient()).get(0).getIdPhoto(),
        photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void voirPhotoSonJardin3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, true, false, true);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> photoUcc.voirPhotoSonJardin(bizFactory.getClientDto().getIdClient()));
  }

  @Test
  @DisplayName("devisDto null")
  void recupererPhotoPreferee() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(NullPointerException.class, () -> photoUcc.recupererPhotoPreferee(null));
  }

  @Test
  @DisplayName("idDevis = 0")
  void recupererPhotoPreferee2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, false, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    DevisDto devisDto = bizFactory.getDevisDto();
    devisDto.setIdPhotoPreferee(0);
    assertNull(photoUcc.recupererPhotoPreferee(devisDto));
  }

  @Test
  @DisplayName("test ok")
  void recupererPhotoPreferee3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, true, false);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertEquals(photoUcc.recupererPhotoPreferee(bizFactory.getDevisDto()).getIdPhoto(),
        photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void recupererPhotoPreferee4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    photoDao = (PhotoDao) photoDaoConstruct.newInstance(false, false, false, false, true, true);
    photoUcc =
        (PhotoUcc) photoUccConstruct.newInstance(photoDao, devisDao, bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> photoUcc.recupererPhotoPreferee(bizFactory.getDevisDto()).getIdPhoto());
  }


}
