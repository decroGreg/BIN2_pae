package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDto;

public interface User extends UserDto {

  boolean checkEmail();

  boolean checkMotDePasse(String password);

  boolean encryptMotDePasse();

  boolean checkUser();

}
