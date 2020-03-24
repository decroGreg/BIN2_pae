package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.UserDAO;

import java.util.Collections;
import java.util.List;

public class ClientUccImpl implements ClientUcc {
  private UserDAO userDao;
  private Factory userFactory;

  /**
   * Cree un objet UserUccImpl
   * 
   * @param userFactory une userFactory.
   * @param userDao un userDao.
   */
  public ClientUccImpl(Factory userFactory, UserDAO userDao) {
    super();
    this.userDao = userDao;
    this.userFactory = userFactory;
  }

  @Override
  public List<DevisDto> voirDevis(ClientDto client) {
    List<DevisDto> devis = null;
    try {
      // devis = userDao.getDevisClient(client);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException();
    }
    return Collections.unmodifiableList(devis);
  }
}
