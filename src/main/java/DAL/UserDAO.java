package DAL;

import be.ipl.pae.biz.dto.UserDto;


public interface UserDAO {

  UserDto getPreparedStatementConnexion(String email);

}
