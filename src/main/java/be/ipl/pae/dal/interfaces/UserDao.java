package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.UserDto;


public interface UserDao {

  UserDto getUserConnexion(String email);

  boolean createInscription(UserDto userDTO);

}
