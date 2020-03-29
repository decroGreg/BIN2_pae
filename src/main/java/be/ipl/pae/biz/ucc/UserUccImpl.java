package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.User;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.interfaces.DaoServicesUCC;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;



public class UserUccImpl implements UserUcc {
  private UserDao userDao;
  private Factory userFactory;
  private DaoServicesUCC daoServicesUcc;

  /**
   * Cree un objet UserUccImpl
   * 
   * @param userFactory une userFactory.
   * @param userDao un userDao.
   */
  public UserUccImpl(Factory userFactory, UserDao userDao, DaoServicesUCC daoServicesUcc) {
    super();
    this.userDao = userDao;
    this.userFactory = userFactory;
    this.daoServicesUcc = daoServicesUcc;
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
        daoServicesUcc.demarrerTransaction();
        // Email deja utilise
        UserDto userConnu = userDao.getUserConnexion(user.getEmail());

        if (userConnu != null) {
          daoServicesUcc.commit();
          throw new BizException("Email deja utilise");
        }

        userDao.createInscription(user);
        UserDto userAjoute = userDao.getUserConnexion(user.getEmail());
        daoServicesUcc.commit();
        return userAjoute;

      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new IllegalArgumentException();
      }
    } else {
      throw new BizException("Inscription impossible, infos manquantes");
    }
  }

  @Override
  public UserDto login(String email, String motDePasse) {
    User user = (User) userFactory.getUserDto();
    user.setEmail(email);
    user.setMotDePasse(motDePasse);
    if (user.checkUser()) {
      UserDto userDb = null;
      try {
        daoServicesUcc.demarrerTransaction();
        userDb = userDao.getUserConnexion(user.getEmail());
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new IllegalArgumentException();
      }
      daoServicesUcc.commit();
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
      daoServicesUcc.demarrerTransaction();
      utilisateurs = userDao.voirTousUser();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalStateException();
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(utilisateurs);
  }

  public void confirmerInscription(UserDto utilisateur, ClientDto client) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (client != null) {
        if (utilisateur.getEmail().equals(client.getEmail())) {
          userDao.lierUserClient(client, utilisateur);
        }
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }

  public void introduireDevis(ClientDto client, DevisDto devis) {
    try {
      daoServicesUcc.demarrerTransaction();
      devis.setIdClient(client.getIdClient());
      userDao.createDevis(client.getIdClient(), devis);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }

  public void confirmerDateDebut(DevisDto devis) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (devis.getEtat().equals(Etat.DDI)) {
        // userDao.confirmerDateDebut(devis);
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }
}
