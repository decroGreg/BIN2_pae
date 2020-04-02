package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.UserDao;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.lang.reflect.Constructor;

class TestDevisUcc {

  DevisDao devisDao;
  Constructor<?> devisDaoConstruct;
  DevisUcc devisUcc;
  Constructor<?> devisUccConstruct;
  ClientDao clientDao;
  UserDao userDao;
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
            .getConstructor(boolean.class, boolean.class).newInstance(false, false);
    userDao = (UserDao) Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class)
        .newInstance(false, false, false, false, false);
    amenagementDao = (AmenagementDao) Class
        .forName(Config.getConfigPropertyAttribute(AmenagementDao.class.getName()))
        .getConstructor(boolean.class).newInstance(false, false);
    devisDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class);
    devisUccConstruct = Class.forName(Config.getConfigPropertyAttribute(DevisUcc.class.getName()))
        .getConstructor(Factory.class, DevisDao.class, UserDao.class, ClientDao.class,
            AmenagementDao.class, DaoServicesUcc.class);
  }

  /**
   * @Test void testVoirDevisClientDto() { fail("Not yet implemented"); }
   * 
   * @Test void testVoirDevis() { fail("Not yet implemented"); }
   * 
   * @Test void testIntroduireDevis() { fail("Not yet implemented"); }
   * 
   * @Test void testConfirmerDateDebut() { fail("Not yet implemented"); }
   */

}
