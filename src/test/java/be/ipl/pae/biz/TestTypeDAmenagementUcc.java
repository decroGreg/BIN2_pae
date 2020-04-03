package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.lang.reflect.Constructor;

class TestTypeDAmenagementUcc {

  TypeDAmenagementDao typeDAmenagementDao;
  Constructor<?> typeDAmenagementDaoConstruct;
  TypeDAmenagementUcc typeDAmenagementUcc;
  Constructor<?> typeDAmenagementUccConstruct;
  Factory bizFactory;
  TypeDAmenagementDto typeDAmenagementDto;
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
    typeDAmenagementDto = bizFactory.getTypeDAmenagementDto();

    typeDAmenagementDaoConstruct =
        Class.forName(Config.getConfigPropertyAttribute(TypeDAmenagementDao.class.getName()))
            .getConstructor(boolean.class, boolean.class);
    typeDAmenagementUccConstruct =
        Class.forName(Config.getConfigPropertyAttribute(TypeDAmenagementUcc.class.getName()))
            .getConstructor(Factory.class, TypeDAmenagementDao.class, DaoServicesUcc.class);
  }

  /**
   * @Test void testVoirTypeDAmenagement() { fail("Not yet implemented"); }
   */

}
