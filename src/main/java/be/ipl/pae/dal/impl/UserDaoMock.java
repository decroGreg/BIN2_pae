package be.ipl.pae.dal.impl;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.dal.interfaces.UserDao;

public class UserDaoMock implements UserDao {

  private boolean getUserConnexion;
  private boolean createInscription;
  private boolean voirTousUser;
  private boolean lierUserUtilisateur;
  private boolean voirUserPasConfirmer;

  public UserDaoMock(boolean getUserConnexion, boolean createInscription, boolean voirTousUser,
      boolean lierUserUtilisateur, boolean VoirUserPasConfirmer) {
    this.getUserConnexion = getUserConnexion;
    this.createInscription = createInscription;
    this.voirTousUser = voirTousUser;
    this.lierUserUtilisateur = lierUserUtilisateur;
    this.voirUserPasConfirmer = voirUserPasConfirmer;
  }

  @Override
  public UserDto getUserConnexion(String email) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean createInscription(UserDto userDTO) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<UserDto> voirTousUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean lierUserUtilisateur(ClientDto client, UserDto user, Character etat) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<UserDto> voirUserPasConfirmer() {
    // TODO Auto-generated method stub
    return null;
  }

}
