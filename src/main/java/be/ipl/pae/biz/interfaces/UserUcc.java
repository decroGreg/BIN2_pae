package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserUcc {

  UserDto login(String email, String motDePasse);

  public UserDto loginViaToken(int id);

  UserDto sinscrire(UserDto userDto);

  void confirmerInscription(UserDto utilisateur, ClientDto client, char etat);

  List<UserDto> getUtilisateurs();

  List<UserDto> voirUtilisateurEnAttente();

}
