package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.UserDTO;

public interface User extends UserDTO {

  public boolean checkEmail(String password);

  public boolean checkMotDePasse(String password);

}
