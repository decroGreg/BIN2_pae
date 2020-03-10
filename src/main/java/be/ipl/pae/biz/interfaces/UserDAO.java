package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDTO;


public interface UserDAO {

  UserDTO getPreparedStatementConnexion(String email);

}
