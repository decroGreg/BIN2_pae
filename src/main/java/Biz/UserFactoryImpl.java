package Biz;

public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDTO getUserDTO(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse) {
    return new UserImpl(pseudo, nom, prenom, ville, email, motDePasse);
  }

}
