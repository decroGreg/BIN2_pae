package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserUcc {

  UserDto login(String email, String motDePasse);

  UserDto sinscrire(UserDto userDTO);

  void confirmerInscription(UserDto utilisateur, ClientDto client);

  List<UserDto> getUtilisateurs();

  public void introduireDevis(ClientDto client, DevisDto devis);
}
