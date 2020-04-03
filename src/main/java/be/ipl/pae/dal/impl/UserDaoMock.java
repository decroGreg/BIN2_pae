package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
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
  private boolean lierUserUtilisateur;
  private boolean voirUserPasConfirmer;
  private boolean testDalException;
  private Factory factory;
  private boolean lierClientUser;
  private boolean confirmerUtilisateur;

  /**
   * Constructeur Mock User
   * 
   * @param getUserConnexion
   * @param createInscription
   * @param voirTousUser
   * @param lierUserUtilisateur
   * @param voirUserPasConfirmer
   * @param lierClientUser
   * @param confirmerUtilisateur
   * @param testDalException
   */
  public UserDaoMock(boolean getUserConnexion, boolean createInscription, boolean voirTousUser,
      boolean lierUserUtilisateur, boolean voirUserPasConfirmer, boolean lierClientUser,
      boolean confirmerUtilisateur, boolean testDalException) {
    this.getUserConnexion = getUserConnexion;
    this.createInscription = createInscription;
    this.voirTousUser = voirTousUser;
    this.lierUserUtilisateur = lierUserUtilisateur;
    this.voirUserPasConfirmer = voirUserPasConfirmer;
    this.testDalException = testDalException;
    this.lierClientUser = lierClientUser;
    this.confirmerUtilisateur = confirmerUtilisateur;
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
  public boolean createInscription(UserDto userDTO) {
    testDalException();
    return createInscription;
  }

  @Override
  public List<UserDto> voirTousUser() {
    testDalException();

    return null;
  }

  @Override
  public boolean lierUserUtilisateur(ClientDto client, UserDto user, Character etat) {
    testDalException();
    return lierUserUtilisateur;
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

  public boolean lierClientUser(int client, int user) {
    testDalException();
    return lierClientUser;
  }

  public boolean confirmerUtilisateur(UserDto user, Character etat) {
    testDalException();
    return confirmerUtilisateur;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }
}
