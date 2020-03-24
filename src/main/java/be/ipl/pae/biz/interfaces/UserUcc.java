package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDto;

import java.util.List;


public interface UserUcc {

  UserDto login(String email, String motDePasse);

  UserDto sinscrire(UserDto userDTO);

  List<UserDto> getUtilisateurs();
}
