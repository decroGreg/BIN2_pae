package be.ipl.pae.biz.ucc;

import DAL.UserDAO;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.interfaces.User;
import be.ipl.pae.biz.interfaces.UserFactory;

public class UserUCC {
  private UserDAO userDAO;
  private UserFactory userFactory;

  public UserUCC(UserDAO userDAO, UserFactory userFactory) {
    super();
    this.userDAO = userDAO;
    this.userFactory = userFactory;
  }

  public UserDTO login(String email, String motDePasse) {
    User user = (User) userFactory.getUserDTO();
    user.setEmail(email);
    user.setMotDePasse(motDePasse);
    if (user.checkUser()) {
      UserDTO userDB = null;
      try {
        userDB = userDAO.getPreparedStatementConnexion(user.getEmail());
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (userDB == null) {
        throw new IllegalArgumentException("Vous n'avez pas trouvé de user pour cet email");
      }
      if (!user.checkMotDePasse(userDB.getMotDePasse())) {
        throw new IllegalArgumentException("Mot de passe incorrect");
      }
      return userDB;
    } else {
      throw new IllegalArgumentException("Login impossible, infos manquantes");
    }
  }
}
