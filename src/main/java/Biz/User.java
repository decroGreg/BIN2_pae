package Biz;

public interface User extends UserDTO {

  public UserDTO checkEmail(String motDePasse);

  public boolean checkMotDePasse(String motDePasseAVerifier, String motDePasse);

}
