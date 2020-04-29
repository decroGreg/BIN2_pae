package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.User;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;



public class UserUccImpl implements UserUcc {
  private UserDao userDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet UserUccImpl.
   * 
   * @param bizFactory la factory.
   * @param userDao la dao du user.
   * @param daoServicesUcc la dao services.
   */
  public UserUccImpl(Factory bizFactory, UserDao userDao, DaoServicesUcc daoServicesUcc) {
    super();
    this.userDao = userDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  @Override
  public UserDto sinscrire(UserDto userDto) {
    User user = (User) userDto;
    LocalDate now = LocalDate.now();
    Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
    user.setDateInscription(timestamp);
    if (user.checkUser()) {
      if (!user.checkEmail()) {
        throw new BizException("Format de l'email incorrect");
      }
      if (!user.encryptMotDePasse()) {
        throw new FatalException("Erreur lors du cryptage du mot de passe");
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
        throw new FatalException(de.getMessage());
      }
    } else {
      throw new BizException("Inscription impossible, infos manquantes");
    }
  }

  @Override
  public UserDto loginViaToken(int id) {
    UserDto userDb;
    if (id >= 1) {
      try {
        daoServicesUcc.demarrerTransaction();
        userDb = userDao.getUserViaId(id);
        if (userDb == null) {
          daoServicesUcc.commit();
          throw new BizException("Aucun utilisateur n'a cette id : " + id);
        }
      } catch (DalException dal) {
        dal.printStackTrace();
        daoServicesUcc.rollback();
        throw new FatalException(dal.getMessage());
      }
      daoServicesUcc.commit();
      return userDb;
    } else {
      throw new BizException("L'id utilisateur " + id + " est incorrect");
    }
  }

  @Override
  public UserDto login(String email, String motDePasse) {
    User user = (User) bizFactory.getUserDto();
    user.setEmail(email);
    user.setMotDePasse(motDePasse);
    if (user.checkUser()) {
      UserDto userDb = null;
      try {
        daoServicesUcc.demarrerTransaction();
        userDb = userDao.getUserConnexion(user.getEmail());
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
      daoServicesUcc.commit();
      if (userDb == null) {
        throw new BizException("Email incorrect");
      }
      if (!user.checkMotDePasse(userDb.getMotDePasse())) {
        return null;
      }
      userDb.setMotDePasse(null);
      return userDb;
    } else {
      throw new BizException("Login impossible, infos manquantes");
    }
  }

  @Override
  public List<UserDto> getUtilisateurs() {
    List<UserDto> utilisateurs = null;
    try {
      daoServicesUcc.demarrerTransaction();
      utilisateurs = userDao.voirTousUser();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(utilisateurs);
  }

  @Override
  public void confirmerInscription(UserDto utilisateur, ClientDto client, char etat) {
    User user = (User) utilisateur;
    try {
      daoServicesUcc.demarrerTransaction();
      if (!user.checkStatut(etat)) {
        daoServicesUcc.commit();
        throw new BizException("L'etat n'est pas correct");
      }
      userDao.confirmerUtilisateur(utilisateur, etat);
      userDao.lierClientUser(client.getIdClient(), utilisateur.getIdUser());
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
  }

  @Override
  public List<UserDto> voirUtilisateurEnAttente() {
    List<UserDto> listeUtilisateursNonConfirmes;
    try {
      daoServicesUcc.demarrerTransaction();
      listeUtilisateursNonConfirmes = userDao.voirUserPasConfirmer();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(listeUtilisateursNonConfirmes);
  }

  @Override
  public List<UserDto> rechercherUtilisateurs(String nom, String ville) {
    List<UserDto> utilisateursCorrespondants = null;
    try {
      daoServicesUcc.demarrerTransaction();
      // utilisateursCorrespondants = userDao.rechercherUtilisateurs(nom,ville);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(utilisateursCorrespondants);
  }
}
