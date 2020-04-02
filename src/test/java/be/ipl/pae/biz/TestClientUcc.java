package be.ipl.pae.biz;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.ClientDao;

import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Constructor;

class TestClientUcc {
  ClientDao clientDao;
  Constructor<?> clientDaoConstruct;
  UserUcc userUcc;
  Constructor<?> clientUccConstruct;
  Factory bizFactory;
  ClientDto clientDto;
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
    clientDto = bizFactory.getClientDto();

    clientDaoConstruct = Class.forName(config.getConfigPropertyAttribute(ClientDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class);
    clientUccConstruct = Class.forName(config.getConfigPropertyAttribute(ClientUcc.class.getName()))
        .getConstructor(Factory.class, ClientDao.class, DaoServices.class);
  }


  /**
   * @Test void testGetClients() {
   * 
   *       }
   */
}
