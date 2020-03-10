package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDTO;


public interface UserUCC {

  UserDTO login(String email, String motDePasse);

}
