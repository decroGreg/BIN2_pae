package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;

import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Constructor;

class TestUserUcc {

  UserDao userDao;
  Constructor<?> userDaoConstruct;
  UserUcc userUcc;
  Constructor<?> userUccConstruct;
  Factory bizFactory;
  UserDto userDto;
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
    userDto = bizFactory.getUserDto();

    userDaoConstruct = Class.forName(config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class);
    userUccConstruct = Class.forName(config.getConfigPropertyAttribute(UserUcc.class.getName()))
        .getConstructor(Factory.class, UserDao.class, DaoServices.class);
  }

  /**
   * @Test void testLogin() { fail("Not yet implemented"); }
   * 
   * @Test void testSinscrire() { fail("Not yet implemented"); }
   * 
   * @Test void testConfirmerInscription() { fail("Not yet implemented"); }
   * 
   * @Test void testGetUtilisateurs() { fail("Not yet implemented"); }
   * 
   * @Test void testVoirUtilisateurEnAttente() { fail("Not yet implemented"); }
   */

}
