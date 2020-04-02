package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Constructor;

class TestTypeDAmenagementUcc {

  TypeDAmenagementDao typeDAmenagementDao;
  Constructor<?> typeDAmenagementDaoConstruct;
  TypeDAmenagementUcc typeDAmenagementUcc;
  Constructor<?> typeDAmenagementUccConstruct;
  Factory bizFactory;
  TypeDAmenagementDto typeDAmenagementDto;
  DaoServices dalServices;
  Config config;

  @BeforeEach
  void setUp() throws Exception {
    config = new Config();
    bizFactory = (Factory) Class.forName(config.getConfigPropertyAttribute(Factory.class.getName()))
        .getConstructor().newInstance();
    dalServices =
        (DaoServices) Class.forName(config.getConfigPropertyAttribute(DaoServices.class.getName()))
            .getConstructor().newInstance();
    typeDAmenagementDto = bizFactory.getTypeDAmenagementDto();

    typeDAmenagementDaoConstruct =
        Class.forName(config.getConfigPropertyAttribute(TypeDAmenagementDao.class.getName()))
            .getConstructor(boolean.class);
    typeDAmenagementUccConstruct =
        Class.forName(config.getConfigPropertyAttribute(TypeDAmenagementUcc.class.getName()))
            .getConstructor(Factory.class, TypeDAmenagementDao.class, DaoServices.class);
  }

  /**
   * @Test void testVoirTypeDAmenagement() { fail("Not yet implemented"); }
   */

}
