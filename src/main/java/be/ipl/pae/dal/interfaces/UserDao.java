package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserDao {

  UserDto getUserConnexion(String email);

  UserDto getUserViaId(int id);

  boolean createInscription(UserDto userDto);

  List<UserDto> voirTousUser();

  List<UserDto> voirUserPasConfirmer();

  boolean lierClientUser(int client, int user);

  List<UserDto> voirUserAvecCritere(String nom, String ville);

  boolean confirmerUtilisateur(UserDto user, Character etat);

  List<String> nomUser();

}
