package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.User;
import be.ipl.pae.biz.interfaces.UserUCC;

import DAL.UserDAO;

public class UserUCCImpl implements UserUCC {
  private UserDAO userDAO;
  private Factory userFactory;

  public UserUCCImpl(Factory userFactory, UserDAO userDAO) {
    super();
    this.userDAO = userDAO;
    this.userFactory = userFactory;
  }

  @Override
  public UserDTO login(String email, String motDePasse) {
    User user = new UserImpl();
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
