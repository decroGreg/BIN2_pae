package be.ipl.pae.dal.interfaces;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;


public interface UserDao {

  UserDto getUserConnexion(String email);

  boolean createInscription(UserDto userDTO);

  public List<UserDto> voirTousUser();

  public boolean lierUserUtilisateur(ClientDto client, UserDto user, Character etat);

  List<UserDto> voirUserPasConfirmer();

  boolean lierClientUser(ClientDto client, UserDto user);

  boolean confirmerUtilisateur(UserDto user, Character etat);
}
