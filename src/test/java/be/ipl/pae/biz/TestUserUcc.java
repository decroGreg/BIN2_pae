package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    userDaoConstruct = Class.forName(Config.getConfigPropertyAttribute(UserDao.class.getName()))
        .getConstructor(boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, boolean.class, boolean.class);
    userUccConstruct = Class.forName(Config.getConfigPropertyAttribute(UserUcc.class.getName()))
        .getConstructor(Factory.class, UserDao.class, DaoServicesUcc.class);
  }


  @Test
  @DisplayName("email null")
  void testLogin() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.login(null, "test"));
  }

  @Test
  @DisplayName("mot de passe null")
  void testLogin2() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.login("test@gmail.com", null));
  }

  @Test
  @DisplayName("email null et mot de passe null")
  void testLogin3() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.login(null, null));
  }

  @Test
  @DisplayName("test si la personne qui veut se connecter n'existe pas")
  void testLogin4() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class,
        () -> userUcc.login(userDto.getEmail(), userDto.getMotDePasse()));
  }

  @Test
  @DisplayName("test si la personne qui recuperer dans la db est null")
  void testLogin5() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class,
        () -> userUcc.login(userDto.getEmail(), userDto.getMotDePasse()));
  }

  @Test
  @DisplayName("test si le mdp est incorrect")
  void testLogin6() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(true, false, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertNull(userUcc.login(userDto.getEmail(), "test"));
  }

  @Test
  @DisplayName("login ok")
  void testLogin7() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(true, false, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userUcc.login(userDto.getEmail(), "Jaune;10.");
  }

  @Test
  @DisplayName("test dalException")
  void testLogin8() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(true, false, false, false, false, false, false,
        false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class, () -> userUcc.login(userDto.getEmail(), "Jaune;10."));
  }


  @Test
  @DisplayName("user est null")
  void testSinscrire() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, true, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(NullPointerException.class, () -> userUcc.sinscrire(null));
  }

  @Test
  @DisplayName("format de l'email incorrect")
  void testSinscrire2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, true, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.sinscrire(userDto));
  }

  @Test
  @DisplayName("erreur lors du cryptage du mdp")
  void testSinscrire3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, true, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setMotDePasse(null);
    assertThrows(BizException.class, () -> userUcc.sinscrire(userDto));
  }

  @Test
  @DisplayName("email deja utilise")
  void testSinscrire4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(true, true, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.sinscrire(userDto));
  }

  @Test
  @DisplayName("inscription ok")
  void testSinscrire5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, true, false, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setEmail("samuel.vancampenhout@student.vinci.be");
    userUcc.sinscrire(userDto);
  }

  @Test
  @DisplayName("test dalException")
  void testSinscrire6() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, true, false, false, false, false, false,
        false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setEmail("samuel.vancampenhout@student.vinci.be");
    assertThrows(FatalException.class, () -> userUcc.sinscrire(userDto));
  }

  @Test
  @DisplayName("utilisateur null")
  void testConfirmerInscription() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, true, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(NullPointerException.class,
        () -> userUcc.confirmerInscription(null, bizFactory.getClientDto(), 'C'));
  }

  @Test
  @DisplayName("client null")
  void testConfirmerInscription2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, true, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(NullPointerException.class,
        () -> userUcc.confirmerInscription(userDto, null, 'C'));
  }

  @Test
  @DisplayName("etat incorrect")
  void testConfirmerInscription3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, true, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class,
        () -> userUcc.confirmerInscription(userDto, bizFactory.getClientDto(), 'M'));
  }

  @Test
  @DisplayName("confirmer inscription reussie")
  void testConfirmerInscription4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, true, true, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userUcc.confirmerInscription(userDto, bizFactory.getClientDto(), 'C');
  }

  @Test
  @DisplayName("confirmation du user raté")
  void testConfirmerInscription5() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class,
        () -> userUcc.confirmerInscription(userDto, bizFactory.getClientDto(), 'C'));
  }

  @Test
  @DisplayName("liste d'utilisateurs null")
  void testGetUtilisateurs() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(NullPointerException.class, () -> userUcc.getUtilisateurs());
  }

  @Test
  @DisplayName("liste d'utilisateurs ok")
  void testGetUtilisateurs2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, true, false, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertEquals(userUcc.getUtilisateurs().get(0).getIdUser(), userDto.getIdUser());
  }

  @Test
  @DisplayName("test dalException")
  void testGetUtilisateurs3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class, () -> userUcc.getUtilisateurs());
  }

  @Test
  @DisplayName("liste d'utilisateurs en attente null")
  void testVoirUtilisateurEnAttente() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(NullPointerException.class, () -> userUcc.voirUtilisateurEnAttente());
  }

  @Test
  @DisplayName("liste d'utilisateurs en attente ok")
  void testVoirUtilisateurEnAttente2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, true, false, false, false,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setStatut(' ');
    assertEquals(userUcc.voirUtilisateurEnAttente().get(0).getIdUser(), userDto.getIdUser());
  }

  @Test
  @DisplayName("test dalException")
  void testVoirUtilisateurEnAttente3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setStatut(' ');
    assertThrows(FatalException.class, () -> userUcc.voirUtilisateurEnAttente());
  }

  @Test
  @DisplayName("id = 0")
  void testLoginViaToken() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false, true,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    userDto.setIdUser(0);
    assertThrows(BizException.class, () -> userUcc.loginViaToken(userDto.getIdUser()));
  }

  @Test
  @DisplayName("userDb = null")
  void testLoginViaToken2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(BizException.class, () -> userUcc.loginViaToken(userDto.getIdUser()));
  }

  @Test
  @DisplayName("test ok")
  void testLoginViaToken3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false, true,
        false, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertEquals(userUcc.loginViaToken(userDto.getIdUser()).getIdUser(), userDto.getIdUser());
  }

  @Test
  @DisplayName("test dalException")
  void testLoginViaToken4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, false, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class, () -> userUcc.loginViaToken(userDto.getIdUser()));
  }

  @Test
  @DisplayName("nom et ville non null")
  void testRechercherUtilisateurs() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertEquals(userUcc.rechercherUtilisateurs("testNom", "testVille").get(0).getIdUser(),
        userDto.getIdUser());
  }

  @Test
  @DisplayName("nom et ville null")
  void testRechercherUtilisateurs2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertEquals(userUcc.rechercherUtilisateurs(null, null).get(0).getIdUser(),
        userDto.getIdUser());
  }

  @Test
  @DisplayName("nom  null et ville  non null")
  void testRechercherUtilisateurs3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, false);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertEquals(userUcc.rechercherUtilisateurs(null, "testVille").get(0).getIdUser(),
        userDto.getIdUser());
  }

  @Test
  @DisplayName("test dalException")
  void testRechercherUtilisateurs4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    userDao = (UserDao) userDaoConstruct.newInstance(false, false, false, false, false, false,
        false, true, true);
    userUcc = (UserUcc) userUccConstruct.newInstance(bizFactory, userDao, dalServices);
    assertThrows(FatalException.class,
        () -> userUcc.rechercherUtilisateurs(null, "testVille").get(0).getIdUser());
  }
}
