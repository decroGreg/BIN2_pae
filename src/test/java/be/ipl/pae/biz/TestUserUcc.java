package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.UserDao;

import org.junit.jupiter.api.BeforeEach;
import org.mindrot.bcrypt.BCrypt;

import java.io.IOException;
import java.lang.reflect.Constructor;

class TestUserUcc {

  UserDao userDao;
  Constructor<?> userDaoConstruct;
  UserUcc userUcc;
  Constructor<?> userUccConstruct;
  Factory bizFactory;
  UserDto userDto;
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
    userDto = bizFactory.getUserDto();
    String salt = BCrypt.gensalt();
    String hashPassword = BCrypt.hashpw(userDto.getMotDePasse(), salt);
    userDto.setMotDePasse(hashPassword);

    userDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class);
    userUccConstruct = Class.forName(Config.getConfigPropertyAttribute(UserUcc.class.getName()))
        .getConstructor(Factory.class, UserDao.class, DaoServicesUcc.class);
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
