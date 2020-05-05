package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
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
  Constructor<?> devisUccConstruct;
  Constructor<?> amenagementDaoConstruct;
  Constructor<?> userDaoConstruct;
  DevisUcc devisUcc;


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

    /**
     * clientDao = (ClientDao)
     * Class.forName(Config.getConfigPropertyAttribute(ClientDao.class.getName()))
     * .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
     * boolean.class, boolean.class) .newInstance(false, false, false, false, false, false, false);
     * userDao = (UserDao) Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
     * .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
     * boolean.class, boolean.class, boolean.class, boolean.class) .newInstance(false, false, false,
     * false, false, false, false, false, false);
     **/
    amenagementDao = (AmenagementDao) Class
        .forName(Config.getConfigPropertyAttribute(AmenagementDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class)
        .newInstance(false, false, false);

    userDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class);
    amenagementDaoConstruct =
        Class.forName(Config.getConfigPropertyAttribute(AmenagementDao.class.getName()))
            .getConstructor(boolean.class, boolean.class, boolean.class);
    clientDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(ClientDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class);
    devisDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class);
    devisUccConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisUcc.class.getName()))
        .getConstructor(Factory.class, DevisDao.class, UserDao.class, ClientDao.class,
            AmenagementDao.class, DaoServicesUcc.class);
  }


  @Test
  @DisplayName("Client null")
  void testVoirDevisClientDto() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.voirDevis(null));
  }

  @Test
  @DisplayName("test dalException")
  void testVoirDevisClientDto2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.voirDevis(bizFactory.getClientDto()));
  }

  @Test
  @DisplayName("client ok")
  void testVoirDevisClientDto3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, true, false, false, false,
        false, false, false, false, false);
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
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.voirDevis());
  }

  @Test
  @DisplayName("test dalException")
  void testVoirDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.voirDevis());
  }

  @Test
  @DisplayName("devis ok")
  void testVoirDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, true, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertEquals(devisUcc.voirDevis().get(0).getIdDevis(), devisDto.getIdDevis());
  }

  @Test
  @DisplayName("devis null")
  void modifierDateDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.modifierDateDevis(null));
  }

  @Test
  @DisplayName("devis ok et etat = FD")
  void modifierDateDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.modifierDateDevis(devisDto);
  }

  @Test
  @DisplayName("devis ok et etat = DC")
  void modifierDateDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, true, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.DC);
    devisUcc.modifierDateDevis(devisDto);
  }

  @Test
  @DisplayName("test dalException")
  void modifierDateDevis4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FD);
    assertThrows(FatalException.class, () -> devisUcc.modifierDateDevis(devisDto));
  }

  @Test
  @DisplayName("devis est null")
  void testchangerEtat() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.changerEtat(null));
  }

  @Test
  @DisplayName("devis pas null et etat correct")
  void testchangerEtat2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, true,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.changerEtat(devisDto);
  }

  @Test
  @DisplayName("test dalException")
  void testchangerEtat3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class, () -> devisUcc.changerEtat(devisDto));
  }

  @Test
  @DisplayName("devis est null")
  void choisirPhotoPreferee() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    assertThrows(NullPointerException.class,
        () -> devisUcc.choisirPhotoPreferee(null, photoDto.getIdPhoto()));
  }

  @Test
  @DisplayName("devis ok avec etat = FF")
  void choisirPhotoPreferee2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FF);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    devisUcc.choisirPhotoPreferee(devisDto, photoDto.getIdPhoto());
    assertEquals(devisDto.getIdPhotoPreferee(), photoDto.getIdPhoto());
  }

  @Test
  @DisplayName("test dalException")
  void choisirPhotoPreferee3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FF);
    PhotoDto photoDto = bizFactory.getPhotoDto();
    assertThrows(FatalException.class,
        () -> devisUcc.choisirPhotoPreferee(devisDto, photoDto.getIdPhoto()));
  }

  @Test
  @DisplayName("devis est null")
  void repousserDateDebut() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> devisUcc.repousserDateDebut(null));
  }

  @Test
  @DisplayName("devis avec mauvais etat")
  void repousserDateDebut2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FF);
    assertThrows(BizException.class, () -> devisUcc.repousserDateDebut(devisDto));
  }

  @Test
  @DisplayName("etat ok et devis ok")
  void repousserDateDebut3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FD);
    devisUcc.repousserDateDebut(devisDto);
  }

  @Test
  @DisplayName("test dalException")
  void repousserDateDebut4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setEtat(Etat.FD);
    assertThrows(FatalException.class, () -> devisUcc.repousserDateDebut(devisDto));
  }

  @Test
  @DisplayName("devis avec mauvais id")
  void creerAmenagementPourDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setIdDevis(0);
    assertThrows(BizException.class,
        () -> devisUcc.creerAmenagementPourDevis(devisDto.getIdDevis(), null));
  }

  @Test
  @DisplayName("devis avec bon id et une liste id type amenagement")
  void creerAmenagementPourDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(true, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> idTypeAmenagement = new ArrayList<String>();
    String idTypeAm = Integer.toString(bizFactory.getAmenagementDto().getIdTypeAmenagement());
    idTypeAmenagement.add(idTypeAm);
    devisUcc.creerAmenagementPourDevis(devisDto.getIdDevis(), idTypeAmenagement);
  }

  @Test
  @DisplayName("test dalException")
  void creerAmenagementPourDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(true, false, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    List<String> idTypeAmenagement = new ArrayList<String>();
    String idTypeAm = Integer.toString(bizFactory.getAmenagementDto().getIdTypeAmenagement());
    idTypeAmenagement.add(idTypeAm);
    assertThrows(FatalException.class,
        () -> devisUcc.creerAmenagementPourDevis(devisDto.getIdDevis(), idTypeAmenagement));
  }

  @Test
  @DisplayName("nouveau client null")
  void creerDevisNouveauClient() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class,
        () -> devisUcc.creerDevisNouveauClient(null, devisDto, 0));
  }

  @Test
  @DisplayName("le nouveau client existe deja")
  void creerDevisNouveauClient2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, true, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(BizException.class,
        () -> devisUcc.creerDevisNouveauClient(bizFactory.getClientDto(), devisDto, 0));
  }

  @Test
  @DisplayName("le nouveau client n'existe pas mais on arrive pas à le créer")
  void creerDevisNouveauClient3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(BizException.class,
        () -> devisUcc.creerDevisNouveauClient(bizFactory.getClientDto(), devisDto, 0));
  }

  @Test
  @DisplayName("le devis se créee sans liason avec utilisateur")
  void creerDevisNouveauClient4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, false, false, false, false,
        true, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.creerDevisNouveauClient(bizFactory.getClientDto(), devisDto, 0);
  }

  @Test
  @DisplayName("le devis se créee avec liason avec utilisateur")
  void creerDevisNouveauClient5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, false, false, false, false,
        true, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, false, false,
        false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisUcc.creerDevisNouveauClient(bizFactory.getClientDto(), devisDto,
        bizFactory.getUserDto().getIdUser());
  }

  @Test
  @DisplayName("test dalException")
  void creerDevisNouveauClient6() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, true, false, false, false,
        true, false, true);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, false, false,
        false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class,
        () -> devisUcc.creerDevisNouveauClient(bizFactory.getClientDto(), devisDto,
            bizFactory.getUserDto().getIdUser()));
  }

  @Test
  @DisplayName("devis null")
  void introduireDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    ClientDto clientDto = bizFactory.getClientDto();
    clientDto.setNom(null);
    assertThrows(NullPointerException.class,
        () -> devisUcc.introduireDevis(clientDto, 0, null, null));
  }

  @Test
  @DisplayName("client null")
  void introduireDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class,
        () -> devisUcc.introduireDevis(null, 0, devisDto, null));
  }

  @Test
  @DisplayName("liste id type amenagement null")
  void introduireDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, true, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    ClientDto clientDto = bizFactory.getClientDto();
    clientDto.setNom(null);
    assertThrows(NullPointerException.class,
        () -> devisUcc.introduireDevis(clientDto, clientDto.getIdUtilisateur(), devisDto, null));
  }

  @Test
  @DisplayName("cree un devis pour client existant")
  void introduireDevis4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, true, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    ClientDto clientDto = bizFactory.getClientDto();
    clientDto.setNom(null);
    List<String> idTypeAmenagement = new ArrayList<String>();
    String idTypeAm = Integer.toString(bizFactory.getAmenagementDto().getIdTypeAmenagement());
    idTypeAmenagement.add(idTypeAm);
    assertEquals(devisUcc.introduireDevis(clientDto, clientDto.getIdUtilisateur(), devisDto,
        idTypeAmenagement), clientDto.getIdClient());
  }

  @Test
  @DisplayName("cree un devis pour nouveau client")
  void introduireDevis5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(true, false, false, false, true, false,
        false, false, false, false, false);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, false, false, false, false,
        true, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, false, false,
        false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    ClientDto clientDto = bizFactory.getClientDto();
    List<String> idTypeAmenagement = new ArrayList<String>();
    String idTypeAm = Integer.toString(bizFactory.getAmenagementDto().getIdTypeAmenagement());
    idTypeAmenagement.add(idTypeAm);
    assertEquals(devisUcc.introduireDevis(clientDto, clientDto.getIdUtilisateur(), devisDto,
        idTypeAmenagement), clientDto.getIdClient());
  }

  @Test
  @DisplayName("test dalException")
  void introduireDevis6() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, true, false,
        false, false, false, false, true);
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, true, true, false, false, false,
        true, false, false);
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, false, false,
        false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    ClientDto clientDto = bizFactory.getClientDto();
    clientDto.setNom(null);
    assertThrows(FatalException.class,
        () -> devisUcc.introduireDevis(clientDto, clientDto.getIdUtilisateur(), devisDto, null));
  }

  @Test
  @DisplayName("devisDto null")
  void rechercheSurDevis() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, true, false,
        false, false, false, false, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(NullPointerException.class,
        () -> devisUcc.rechercheSurDevis(null, 0.0, 0.0, null, null));
  }

  @Test
  @DisplayName("recherche uniquement sur le nom")
  void rechercheSurDevis2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, true, false,
        false, false, false, true, false);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    devisDto.setDate(null);
    devisDto.setIdClient(0);
    assertEquals(devisUcc.rechercheSurDevis(devisDto, 0.0, 0.0, null, "te").get(0).getIdDevis(),
        devisDto.getIdDevis());
  }

  @Test
  @DisplayName("test dalException")
  void rechercheSurDevis3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    devisDao = (DevisDao) devisDaoConstruct.newInstance(false, false, false, false, true, false,
        false, false, false, true, true);
    devisUcc = (DevisUcc) devisUccConstruct.newInstance(bizFactory, devisDao, userDao, clientDao,
        amenagementDao, dalServices);
    assertThrows(FatalException.class,
        () -> devisUcc.rechercheSurDevis(devisDto, 0.0, 0.0, null, null));
  }

}
