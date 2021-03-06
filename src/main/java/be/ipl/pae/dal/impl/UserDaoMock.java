package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMock implements UserDao {

  private boolean getUserConnexion;
  private boolean createInscription;
  private boolean voirTousUser;
  private boolean voirUserPasConfirmer;
  private boolean testDalException;
  private boolean lierClientUser;
  private boolean confirmerUtilisateur;
  private boolean getUserViaId;
  private boolean voirUserAvecCritere;
  private boolean nomUser;
  private Factory factory;

  /**
   * Cree un objet UserDaoMock.
   * 
   * @param getUserConnexion attribut boolean.
   * @param createInscription attribut boolean.
   * @param voirTousUser attribut boolean.
   * @param voirUserPasConfirmer attribut boolean.
   * @param lierClientUser attribut boolean.
   * @param confirmerUtilisateur attribut boolean.
   * @param getUserViaId attribut boolean.
   * @param voirUserAvecCritere attribut boolean.
   * @param nomUser attribut boolean.
   * @param testDalException attribut boolean.
   */
  public UserDaoMock(boolean getUserConnexion, boolean createInscription, boolean voirTousUser,
      boolean voirUserPasConfirmer, boolean lierClientUser, boolean confirmerUtilisateur,
      boolean getUserViaId, boolean voirUserAvecCritere, boolean nomUser,
      boolean testDalException) {
    this.getUserConnexion = getUserConnexion;
    this.createInscription = createInscription;
    this.voirTousUser = voirTousUser;
    this.voirUserPasConfirmer = voirUserPasConfirmer;
    this.testDalException = testDalException;
    this.lierClientUser = lierClientUser;
    this.confirmerUtilisateur = confirmerUtilisateur;
    this.getUserViaId = getUserViaId;
    this.voirUserAvecCritere = voirUserAvecCritere;
    this.nomUser = nomUser;
    this.factory = new FactoryStub();
  }

  @Override
  public UserDto getUserConnexion(String email) {
    testDalException();
    if (getUserConnexion) {
      return factory.getUserDto();
    }
    return null;
  }

  @Override
  public boolean createInscription(UserDto userDto) {
    testDalException();
    return createInscription;
  }

  @Override
  public List<UserDto> voirTousUser() {
    testDalException();
    if (voirTousUser) {
      List<UserDto> users = new ArrayList<UserDto>();
      users.add(factory.getUserDto());
      return users;
    }
    return null;
  }

  @Override
  public List<UserDto> voirUserPasConfirmer() {
    testDalException();
    if (voirUserPasConfirmer) {
      List<UserDto> users = new ArrayList<UserDto>();
      users.add(factory.getUserDto());
      return users;
    }
    return null;
  }

  @Override
  public boolean lierClientUser(int client, int user) {
    testDalException();
    return lierClientUser;
  }

  @Override
  public boolean confirmerUtilisateur(UserDto user, Character etat) {
    testDalException();
    return confirmerUtilisateur;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }

  @Override
  public UserDto getUserViaId(int id) {
    testDalException();
    if (getUserViaId) {
      return factory.getUserDto();
    }
    return null;
  }

  @Override
  public List<UserDto> voirUserAvecCritere(String nom, String ville) {
    testDalException();
    if (voirUserAvecCritere) {
      List<UserDto> usersAvecCritere = new ArrayList<UserDto>();
      usersAvecCritere.add(factory.getUserDto());
      return usersAvecCritere;
    }
    return null;
  }

  @Override
  public List<String> nomUser() {
    testDalException();
    if (nomUser) {
      List<String> noms = new ArrayList<String>();
      noms.add(factory.getUserDto().getNom());
      return noms;
    }
    return null;
  }
}
