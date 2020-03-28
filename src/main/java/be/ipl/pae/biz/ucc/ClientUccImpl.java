package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;

import java.util.Collections;
import java.util.List;

public class ClientUccImpl implements ClientUcc {
  private ClientDao clientDao;
  private Factory userFactory;

  /**
   * Cree un objet UserUccImpl
   * 
   * @param userFactory une userFactory.
   * @param userDao un userDao.
   */
  public ClientUccImpl(Factory userFactory, ClientDao clientDao) {
    super();
    this.clientDao = clientDao;
    this.userFactory = userFactory;
  }

  public List<ClientDto> getClients() {
    List<ClientDto> clients = null;
    try {
      // clients = userDAO.trouverClients();
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
      throw new IllegalStateException(ex.getMessage());
    }
    return Collections.unmodifiableList(clients);
  }
}
