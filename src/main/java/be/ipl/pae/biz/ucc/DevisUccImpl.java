package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DaoServicesUCC;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.exceptions.DalException;

import java.util.Collections;
import java.util.List;

public class DevisUccImpl implements DevisUcc {

  private ClientDao clientDao;
  private UserDao userDao;
  private Factory userFactory;
  private DaoServicesUCC daoServicesUcc;


  /**
   * Cree un objet DevisUccImpl
   * 
   * @param clientDao le clientDao
   * @param userFactory le userFactory
   */
  public DevisUccImpl(Factory userFactory, ClientDao clientDao, UserDao userDao,
      DaoServicesUCC daoServicesUcc) {
    super();
    this.clientDao = clientDao;
    this.userFactory = userFactory;
    this.userDao = userDao;
    this.daoServicesUcc = daoServicesUcc;
  }

  // Afficher les devis d'un client
  @Override
  public List<DevisDto> voirDevis(ClientDto client) {
    List<DevisDto> devis = null;
    try {
      daoServicesUcc.demarrerTransaction();
      devis = clientDao.getDevisClient(client);
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
      // devis = clientDao.getDevis();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new IllegalArgumentException();
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(devis);
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
}
