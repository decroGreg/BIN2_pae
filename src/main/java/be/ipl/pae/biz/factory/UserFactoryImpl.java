package be.ipl.pae.biz.factory;

import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.UserFactory;

public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDTO getUserDTO(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse) {
    return new UserImpl(pseudo, nom, prenom, ville, email, motDePasse);
  }

}
