package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;

import java.util.Collections;
import java.util.List;

public class DevisUccImpl implements DevisUcc {

  private ClientDao clientDao;
  private Factory userFactory;


  /**
   * Cree un objet DevisUccImpl
   * 
   * @param clientDao le clientDao
   * @param userFactory le userFactory
   */
  public DevisUccImpl(Factory userFactory, ClientDao clientDao) {
    super();
    this.clientDao = clientDao;
    this.userFactory = userFactory;
  }

  // Afficher les devis d'un client
  @Override
  public List<DevisDto> voirDevis(ClientDto client) {
    List<DevisDto> devis = null;
    try {
      devis = clientDao.getDevisClient(client);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException();
    }
    return Collections.unmodifiableList(devis);
  }

  // Afficher tous les devis
  @Override
  public List<DevisDto> voirDevis() {
    List<DevisDto> devis = null;
    try {
      // devis = clientDao.getDevis();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException();
    }
    return Collections.unmodifiableList(devis);
  }
}
