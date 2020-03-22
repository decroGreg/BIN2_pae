package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDto;


public interface UserUcc {

  UserDto login(String email, String motDePasse);

}
