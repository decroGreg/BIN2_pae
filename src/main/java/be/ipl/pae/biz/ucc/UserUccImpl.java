package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.User;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;



public class UserUccImpl implements UserUcc {
  private UserDao userDao;
  private Factory userFactory;

  /**
   * Cree un objet UserUccImpl
   * 
   * @param userFactory une userFactory.
   * @param userDao un userDao.
   */
  public UserUccImpl(Factory userFactory, UserDao userDao) {
    super();
    this.userDao = userDao;
    this.userFactory = userFactory;
  }

  public UserDto sinscrire(UserDto userDTO) {
    User user = (User) userDTO;
    LocalDate now = LocalDate.now();
    Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
    user.setDateInscription(timestamp);
    if (user.checkUser()) {
      if (!user.checkEmail()) {
        throw new BizException("Format de l'email incorrect");
      }
      if (!user.encryptMotDePasse()) {
        throw new IllegalArgumentException("Erreur lors du cryptage du mot de passe");
      }
      try {
        // Email deja utilise
        UserDto userConnu = userDao.getUserConnexion(user.getEmail());

        if (userConnu != null) {
          throw new BizException("Email deja utilise");
        }

        userDao.createInscription(user);
        UserDto userAjoute = userDao.getUserConnexion(user.getEmail());
        return userAjoute;

      } catch (Exception exception/* DalException de */) {
        throw new IllegalArgumentException();
      }
    } else {
      throw new BizException("Inscription impossible, infos manquantes");
    }
  }

  @Override
  public UserDto login(String email, String motDePasse) {
    User user = new UserImpl();
    user.setEmail(email);
    user.setMotDePasse(motDePasse);
    if (user.checkUser()) {
      UserDto userDb = null;
      try {
        userDb = userDao.getUserConnexion(user.getEmail());
      } catch (Exception exception) {
        exception.printStackTrace();
      }
      if (userDb == null) {
        throw new BizException("Email incorrect");
      }
      if (!user.checkMotDePasse(userDb.getMotDePasse())) {
        throw new BizException("Mot de passe incorrect");
      }
      userDb.setMotDePasse(null);
      return userDb;
    } else {
      throw new BizException("Login impossible, infos manquantes");
    }
  }

  public List<UserDto> getUtilisateurs() {
    List<UserDto> utilisateurs = null;
    try {
      // utilisateurs = userDAO.trouverUtilisateurs();
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
      throw new IllegalStateException(ex.getMessage());
    }
    return Collections.unmodifiableList(utilisateurs);
  }

  public void confirmerInscription(String email, char statut, String emailClient) {
    try {
      UserDto user = userDao.getUserConnexion(email);
      user.setStatut(statut);
      // userDao.modifiterUser(user);
      if (emailClient != " ") {
        // ClientDto client = clientDao.getClient(emailClient);
        // if(user.getEmail().equals(client.getEmail()) {
        // client.setIdUtilisateur(user.getIdUser());
      }
    } catch (Exception exception/* DalException de */) {
      exception.printStackTrace();
      throw new IllegalArgumentException(exception.getMessage());
    }
  }
}
