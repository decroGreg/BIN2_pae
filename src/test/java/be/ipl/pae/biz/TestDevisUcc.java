package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class TestDevisUcc {

  DevisDao devisDao;
  ClientDao clientDao;
  UserDao userDao;

  Constructor<?> clientDaoConstruct;
  Constructor<?> devisDaoConstruct;
  DevisUcc devisUcc;
  Constructor<?> devisUccConstruct;

  AmenagementDao amenagementDao;
  Factory bizFactory;
  DevisDto devisDto;
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
    devisDto = bizFactory.getDevisDto();

    clientDao =
        (ClientDao) Class.forName(Config.getConfigPropertyAttribute(ClientDao.class.getName()))
            .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class)
            .newInstance(false, false, false, false);
    userDao = (UserDao) Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class)
        .newInstance(false, false, false, false, false, false, false, false);

    amenagementDao = (AmenagementDao) Class
        .forName(Config.getConfigPropertyAttribute(AmenagementDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class)
        .newInstance(false, false, false);

    clientDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(ClientDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class);
    devisDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class);
    devisUccConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisUcc.class.getName()))
        .getConstructor(Factory.class, DevisDao.class, UserDao.class, ClientDao.class,
            AmenagementDao.class, DaoServicesUcc.class);
  }


  @Test
  @DisplayName("Client null")
  void testVoirDevisClientDto() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.voirDevis(null));
  }

  @Test
  @DisplayName("test dalException")
  void testVoirDevisClientDto2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.voirDevis(bizFactory.getClientDto()));
  }

  @Test
  @DisplayName("client ok")
  void testVoirDevisClientDto3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, true, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertEquals(devisUcc.voirDevis(bizFactory.getClientDto()).get(0).getIdDevis(),
        devisDto.getIdDevis());
  }

  @Test
  @DisplayName("devis null")
  void testVoirDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.voirDevis());
  }

  @Test
  @DisplayName("test dalException")
  void testVoirDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.voirDevis());
  }

  @Test
  @DisplayName("devis ok")
  void testVoirDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, true, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertEquals(devisUcc.voirDevis().get(0).getIdDevis(), devisDto.getIdDevis());
  }

  @Test
  @DisplayName("email deja utilise")
  void testIntroduireDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, true, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> listIdTypeAmenagement = new ArrayList<String>();
    assertThrows(BizException.class, () -> devisUcc.introduireDevis(bizFactory.getClientDto(),
        bizFactory.getUserDto().getIdUser(), devisDto, listIdTypeAmenagement));
  }

  @Test
  @DisplayName("creation d'un devis pour un client existant")
  void testIntroduireDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> listIdTypeAmenagement = new ArrayList<String>();
    devisUcc.introduireDevis(null, bizFactory.getClientDto().getIdClient(), devisDto,
        listIdTypeAmenagement);
  }

  @Test
  @DisplayName("creation d'un devis pour un client null et que l'id est incorrect")
  void testIntroduireDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> listIdTypeAmenagement = new ArrayList<String>();
    assertThrows(FatalException.class,
        () -> devisUcc.introduireDevis(null, 0, devisDto, listIdTypeAmenagement));
  }

  @Test
  @DisplayName("creation d'un nouveau client mais deja existant")
  void testIntroduireDevis4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> listIdTypeAmenagement = new ArrayList<String>();
    assertThrows(BizException.class, () -> devisUcc.introduireDevis(bizFactory.getClientDto(), 2,
        devisDto, listIdTypeAmenagement));
  }

  @Test
  @DisplayName("si j'arrive pas à lier un client")
  void testIntroduireDevis5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> listIdTypeAmenagement = new ArrayList<String>();
    assertThrows(FatalException.class, () -> devisUcc.introduireDevis(bizFactory.getClientDto(), 1,
        devisDto, listIdTypeAmenagement));
  }

  /**
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @Test @DisplayName("introduire un devis pour un nouveau client et lier le client à son
   *       utilisateur") void testIntroduireDevis6() throws InstantiationException,
   *       IllegalAccessException, IllegalArgumentException, InvocationTargetException { devisDao =
   *       (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false);
   *       clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, false, false);
   *       devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao,
   *       clientDao, amenagementDao, dalServices); List<String> listIdTypeAmenagement = new
   *       ArrayList<String>(); devisUcc.introduireDevis(bizFactory.getClientDto(), 1, devisDto,
   *       listIdTypeAmenagement); }
   */

  @Test
  @DisplayName("devis null")
  void testConfirmerDateDebut() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.confirmerDateDebut(null));
  }

  @Test
  @DisplayName("devis ok")
  void testConfirmerDateDebut2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.confirmerDateDebut(devisDto);
  }

  @Test
  @DisplayName("etat != FD")
  void testConfirmerDateDebut3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertEquals(Etat.FD, devisDto.getEtat());
  }

  @Test
  @DisplayName("test dalException")
  void testConfirmerDateDebut4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.confirmerDateDebut(devisDto));
  }

  @Test
  @DisplayName("devis est null")
  void testchangerEtat() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.changerEtat(null));
  }

  @Test
  @DisplayName("devis pas null et etat correct")
  void testchangerEtat2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, true,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.changerEtat(devisDto);
  }

  @Test
  @DisplayName("test dalException")
  void testchangerEtat3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.changerEtat(devisDto));
  }

  @Test
  @DisplayName("devis est null")
  void choisirPhotoPreferee() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    assertThrows(NullPointerException.class,
        () -> devisUcc.choisirPhotoPreferee(null, photoDto.getIdPhoto()));
  }

  @Test
  @DisplayName("devis ok avec etat = V")
  void choisirPhotoPreferee2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.V);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    devisUcc.choisirPhotoPreferee(devisDto, photoDto.getIdPhoto());
    assertEquals(devisDto.getIdPhotoPreferee(), photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void choisirPhotoPreferee3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.V);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    assertThrows(FatalException.class,
        () -> devisUcc.choisirPhotoPreferee(devisDto, photoDto.getIdPhoto()));
  }

  @Test
  @DisplayName("devis est null")
  void repousserDateDebut() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.repousserDateDebut(null));
  }

  @Test
  @DisplayName("devis avec mauvais etat")
  void repousserDateDebut2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(BizException.class, () -> devisUcc.repousserDateDebut(devisDto));
  }

  @Test
  @DisplayName("etat ok et devis ok")
  void repousserDateDebut3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.I);
    devisUcc.repousserDateDebut(devisDto);
  }

  @Test
  @DisplayName("test dalException")
  void repousserDateDebut4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.I);
    assertThrows(FatalException.class, () -> devisUcc.repousserDateDebut(devisDto));
  }
}
