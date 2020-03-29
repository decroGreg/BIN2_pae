package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DaoServicesUCC;

import java.util.Collections;
import java.util.List;

public class ClientUccImpl implements ClientUcc {

  private ClientDao clientDao;
  private Factory userFactory;
  private DaoServicesUCC daoServicesUcc;

  /**
   * Cree un objet UserUccImpl
   * 
   * @param userFactory une userFactory.
   * @param userDao un userDao.
   */
  public ClientUccImpl(Factory userFactory, ClientDao clientDao, DaoServicesUCC daoServicesUcc) {
    super();
    this.clientDao = clientDao;
    this.userFactory = userFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  public List<ClientDto> getClients() {
    List<ClientDto> clients = null;
    try {
      daoServicesUcc.demarrerTransaction();
      // clients = userDAO.trouverClients();
    } catch (IllegalArgumentException ex) {
      daoServicesUcc.rollback();
      ex.printStackTrace();
      throw new IllegalStateException(ex.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(clients);
  }
}
