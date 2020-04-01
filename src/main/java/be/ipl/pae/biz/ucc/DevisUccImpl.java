package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;

import java.util.Collections;
import java.util.List;

public class DevisUccImpl implements DevisUcc {

  private DevisDao devisDao;
  private ClientDao clientDao;
  private UserDao userDao;
  private AmenagementDao amenagementDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;


  /**
   * Cree un objet DevisUccImpl.
   * 
   * @param userFactory la factory.
   * @param devisDao le dao du devis.
   * @param daoServicesUcc le dao services.
   */
  public DevisUccImpl(Factory bizFactory, DevisDao devisDao, UserDao userDao, ClientDao clientDao,
      AmenagementDao amenagementDao, DaoServicesUcc daoServicesUcc) {
    super();
    this.bizFactory = bizFactory;
    this.devisDao = devisDao;
    this.clientDao = clientDao;
    this.userDao = userDao;
    this.amenagementDao = amenagementDao;
    this.daoServicesUcc = daoServicesUcc;
  }

  // Afficher les devis d'un client
  @Override
  public List<DevisDto> voirDevis(ClientDto client) {
    List<DevisDto> devis = null;
    try {
      daoServicesUcc.demarrerTransaction();
      devis = devisDao.getDevisClient(client);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devis);
  }

  // Afficher tous les devis
  @Override
  public List<DevisDto> voirDevis() {
    List<DevisDto> devis = null;
    try {
      daoServicesUcc.demarrerTransaction();
      devis = devisDao.voirTousDevis();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devis);
  }

  @Override
  public void introduireDevis(ClientDto nouveauClient, int idUtilisateur, DevisDto devis,
      List<String> listeIdTypeAmenagement) {
    int idDevis = 0;
    try {
      daoServicesUcc.demarrerTransaction();
      if (nouveauClient == null) {
        devisDao.createDevis(idUtilisateur, devis);
      } else {
        if (!clientDao.createClient(nouveauClient)) {
          daoServicesUcc.commit();
          throw new BizException("Impossible de créer un client");
        }
        if (idUtilisateur > 0) {
          // userDao.lierUserUtilisateur(nouveauClient.getIdClient(), , etat)
        }
        devisDao.createDevis(nouveauClient.getIdClient(), devis);
        idDevis = devisDao.getIdDernierDevis();
        for (String idTypeAmenagement : listeIdTypeAmenagement) {
          int idType = Integer.parseInt(idTypeAmenagement);
          amenagementDao.createAmenagement(idType, idDevis);
        }
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }

  @Override
  public void confirmerDateDebut(DevisDto devis) {
    try {
      daoServicesUcc.demarrerTransaction();
      if (devis.getEtat().equals(Etat.DDI)) {

        devisDao.confirmerDateDevis(devis.getIdDevis(), devis.getDateDebutTravaux());
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }
}
