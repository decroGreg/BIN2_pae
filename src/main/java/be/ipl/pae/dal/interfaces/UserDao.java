package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserDao {

  UserDto getUserConnexion(String email);

  boolean createInscription(UserDto userDTO);

  public List<UserDto> voirTousUser();

  public boolean lierUserUtilisateur(ClientDto idClient, UserDto idUtilisateur, Character etat);

  List<UserDto> voirUserPasConfirmer();
}
