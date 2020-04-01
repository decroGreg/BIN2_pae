package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.fail;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.ClientDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

class TestClientUcc {
  ClientDao clientDao;
  Constructor<?> clientDaoConstruct;
  UserUcc userUcc;
  Constructor<?> clientUccConstruct;
  Factory Factory;
  ClientDto clientDto;
  DaoServices dalServices;

  @BeforeEach
  void setUp() throws Exception {}

  /**@Test
  void testGetClients() {
    fail("Not yet implemented");
  }*/

}
