package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.UserDto;


public interface UserDAO {

  UserDto getUserConnexion(String email);

}
