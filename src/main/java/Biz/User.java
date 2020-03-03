package Biz;

public interface User extends UserDTO {

  public boolean checkEmail(String password);

  public boolean checkMotDePasse(String password);

}
