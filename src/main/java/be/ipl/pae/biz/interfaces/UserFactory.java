package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDTO;

public interface UserFactory {

  UserDTO getUserDTO(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse);

}
