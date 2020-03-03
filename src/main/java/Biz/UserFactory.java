package Biz;

public interface UserFactory {

  UserDTO getUserDTO(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse);

}
