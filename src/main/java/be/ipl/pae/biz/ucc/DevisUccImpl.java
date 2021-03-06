package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Devis;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.Collections;
import java.util.List;

public class DevisUccImpl implements DevisUcc {

  private DevisDao devisDao;
  private ClientDao clientDao;
  private UserDao userDao;
  private AmenagementDao amenagementDao;
  private DaoServicesUcc daoServicesUcc;


  /**
   * Cree un objet DevisUccImpl.
   * 
   * @param devisDao le dao du devis.
   * @param userDao le dao du user.
   * @param clientDao le dao du client.
   * @param amenagementDao le dao de l'amenagement.
   * @param daoServicesUcc le daoServiceUcc.
   */
  public DevisUccImpl(DevisDao devisDao, UserDao userDao, ClientDao clientDao,
      AmenagementDao amenagementDao, DaoServicesUcc daoServicesUcc) {
    super();
    this.devisDao = devisDao;
    this.clientDao = clientDao;
    this.userDao = userDao;
    this.amenagementDao = amenagementDao;
    this.daoServicesUcc = daoServicesUcc;
  }

  // Afficher les devis d'un client
  @Override
  public List<DevisDto> voirDevis(ClientDto client) {
    List<DevisDto> devis;
    try {
      daoServicesUcc.demarrerTransaction();
      devis = devisDao.getDevisClient(client);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devis);
  }

  // Afficher tous les devis
  @Override
  public List<DevisDto> voirDevis() {
    List<DevisDto> devis;
    try {
      daoServicesUcc.demarrerTransaction();
      devis = devisDao.voirTousDevis();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devis);
  }

  @Override
  public void creerAmenagementPourDevis(int idDevis, List<String> listeIdTypeAmenagement) {
    if (idDevis > 0) {
      try {
        for (String idTypeAmenagement : listeIdTypeAmenagement) {
          if (idTypeAmenagement != null) {
            int idType = Integer.parseInt(idTypeAmenagement);
            amenagementDao.createAmenagement(idType, idDevis);
          }
        }
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
    } else {
      throw new BizException("L'id du devis est incorrect");
    }
  }

  @Override
  public void creerDevisNouveauClient(ClientDto nouveauClient, DevisDto devis, int idUtilisateur) {
    try {
      // Email deja utilis??
      ClientDto client = clientDao.getClientMail(nouveauClient.getEmail());
      if (client != null) {
        daoServicesUcc.commit();
        throw new BizException("Email deja utilis??");
      }
      if (!clientDao.createClient(nouveauClient)) {
        daoServicesUcc.commit();
        throw new BizException("Impossible de cr??er un client");
      }

      int idDernierClient = clientDao.getIdDernierClient();

      if (idUtilisateur > 0) {
        userDao.lierClientUser(idDernierClient, idUtilisateur);
      }
      devisDao.createDevis(idDernierClient, devis);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
  }

  @Override
  public int introduireDevis(ClientDto client, int idUtilisateur, DevisDto devis,
      List<String> listeIdTypeAmenagement) {
    int idDevis = 0;
    try {
      daoServicesUcc.demarrerTransaction();
      if (client.getNom() == null) {
        devisDao.createDevis(devis.getIdClient(), devis);
      } else {
        creerDevisNouveauClient(client, devis, idUtilisateur);
      }
      idDevis = devisDao.getIdDernierDevis();
      creerAmenagementPourDevis(idDevis, listeIdTypeAmenagement);
      daoServicesUcc.commit();
      return idDevis;
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
  }

  @Override
  public void modifierDateDevis(DevisDto devis) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (devis.getEtat().equals(Etat.FD) || devis.getEtat().equals(Etat.DC)) {
        devisDao.confirmerDateDevis(devis);
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
  }

  @Override
  public void changerEtat(DevisDto devisDto) {
    Devis devis = (Devis) devisDto;
    if (devis.checkEtat()) {
      try {
        daoServicesUcc.demarrerTransaction();
        devisDao.changerEtatDevis(devisDto);
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
      daoServicesUcc.commit();
    }
  }

  @Override
  public void choisirPhotoPreferee(DevisDto devisDto, int idPhoto) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (devisDto.getEtat().equals(Etat.FF)) {
        devisDao.ajouterPhotoPrefereeDevis(devisDto, idPhoto);
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
  }

  @Override
  public void repousserDateDebut(DevisDto devisDto) {
    if (devisDto.getEtat().equals(Etat.FD) || devisDto.getEtat().equals(Etat.DC)) {
      try {
        daoServicesUcc.demarrerTransaction();
        devisDao.repousserDateDebut(devisDto);
      } catch (DalException de) {
        daoServicesUcc.rollback();
        throw new FatalException(de.getMessage());
      }
      daoServicesUcc.commit();
    } else {
      throw new BizException("L'etat du devis est incorrect");
    }
  }


  @Override
  public List<DevisDto> rechercheSurDevis(DevisDto devisDto, double prixMin, double prixMax,
      List<Integer> idTypeAmenagements, String nomClient) {
    List<DevisDto> devisCorrespondants = null;
    try {
      daoServicesUcc.demarrerTransaction();
      devisCorrespondants = devisDao.voirDevisAvecCritere(devisDto.getDate(), nomClient, prixMin,
          prixMax, idTypeAmenagements, devisDto.getIdClient());
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devisCorrespondants);
  }


}
