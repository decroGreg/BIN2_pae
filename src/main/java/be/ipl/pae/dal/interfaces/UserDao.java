package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserDao {

  UserDto getUserConnexion(String email);

  boolean createInscription(UserDto userDto);

  List<UserDto> voirTousUser();

  List<UserDto> voirUserPasConfirmer();

  boolean lierClientUser(int client, int user);

  boolean confirmerUtilisateur(UserDto user, Character etat);
}
