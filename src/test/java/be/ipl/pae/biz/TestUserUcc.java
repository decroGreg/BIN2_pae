package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

class TestUserUcc {

  UserDao userDao;
  Constructor<?> userDaoConstruct;
  UserUcc userUcc;
  Constructor<?> userUccConstruct;
  Factory Factory;
  UserDto userDto;
  DaoServices dalServices;

  @BeforeEach
  void setUp() throws Exception {}

  @Test
  void testLogin() {
    fail("Not yet implemented");
  }

  @Test
  void testSinscrire() {
    fail("Not yet implemented");
  }

  @Test
  void testConfirmerInscription() {
    fail("Not yet implemented");
  }

  @Test
  void testGetUtilisateurs() {
    fail("Not yet implemented");
  }

  @Test
  void testVoirUtilisateurEnAttente() {
    fail("Not yet implemented");
  }

}
