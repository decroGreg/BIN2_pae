package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


class TestClientUcc {
  ClientDao clientDao;
  Constructor<?> clientDaoConstruct;
  ClientUcc clientUcc;
  Constructor<?> clientUccConstruct;
  Factory bizFactory;
  ClientDto clientDto;
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
    clientDto = bizFactory.getClientDto();

    clientDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(ClientDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class);
    clientUccConstruct = Class.forName(Config.getConfigPropertyAttribute(ClientUcc.class.getName()))
        .getConstructor(Factory.class, ClientDao.class, DaoServicesUcc.class);
  }


  @Test
  @DisplayName("clients null")
  void testGetClients() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertThrows(NullPointerException.class, () -> clientUcc.getClients());
  }

  @Test
  @DisplayName("clients ok")
  final void testGetClients2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    clientDao = (ClientDao) clientDaoConstruct.newInstance(true, false, false, false, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertEquals(clientUcc.getClients().get(0).getEmail(), clientDto.getEmail());
  }

  @Test
  @DisplayName("test dalException")
  final void testGetClients3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, true);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertThrows(FatalException.class, () -> clientUcc.getClients());
  }

  @Test
  @DisplayName("nom, ville, code postal non null")
  void testRechercherClients() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, true, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertEquals(clientUcc.rechercherClients("testNom", "testVille", 1040).get(0).getIdClient(),
        clientDto.getIdClient());
  }

  @Test
  @DisplayName("nom, ville non null et code postal null")
  void testRechercherClients2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, true, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertEquals(clientUcc.rechercherClients("testNom", "testVille", 0).get(0).getIdClient(),
        clientDto.getIdClient());
  }

  @Test
  @DisplayName("nom, code postal non null et ville null")
  void testRechercherClients3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, true, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertEquals(clientUcc.rechercherClients("testNom", null, 1040).get(0).getIdClient(),
        clientDto.getIdClient());
  }

  @Test
  @DisplayName("nom, ville, code postal null")
  void testRechercherClients4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, true, false);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertEquals(clientUcc.rechercherClients(null, null, 0).get(0).getIdClient(),
        clientDto.getIdClient());
  }

  @Test
  @DisplayName("test dalException")
  void testRechercherClients5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    clientDao = (ClientDao) clientDaoConstruct.newInstance(false, false, false, false, true);
    clientUcc = (ClientUcc) clientUccConstruct.newInstance(bizFactory, clientDao, dalServices);
    assertThrows(FatalException.class,
        () -> clientUcc.rechercherClients(null, null, 0).get(0).getIdClient());
  }
}
