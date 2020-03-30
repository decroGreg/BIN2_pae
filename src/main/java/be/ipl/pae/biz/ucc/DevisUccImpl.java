package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUCC;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.exceptions.DalException;

import java.util.Collections;
import java.util.List;

public class DevisUccImpl implements DevisUcc {

  private DevisDao devisDao;
  private Factory userFactory;
  private DaoServicesUCC daoServicesUcc;


  /**
   * Cree un objet DevisUccImpl
   * 
   * @param userFactory la factory
   * @param devisDao le devisDao
   * @param daoServicesUcc le daoServices
   */
  public DevisUccImpl(Factory userFactory, DevisDao devisDao, DaoServicesUCC daoServicesUcc) {
    super();
    this.userFactory = userFactory;
    this.devisDao = devisDao;
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
  public void introduireDevis(ClientDto client, DevisDto devis) {
    try {
      daoServicesUcc.demarrerTransaction();
      devisDao.createDevis(client.getIdClient(), devis);
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
        // userDao.confirmerDateDebut(devis);
      }
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
  }
}
