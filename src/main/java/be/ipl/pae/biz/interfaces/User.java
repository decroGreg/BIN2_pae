package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDTO;

public interface User extends UserDTO {

  boolean checkEmail();

  boolean checkMotDePasse(String password);

  boolean encryptMotDePasse();

  boolean checkUser();

}
