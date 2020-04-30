package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.Collections;
import java.util.List;

public class ClientUccImpl implements ClientUcc {

  private ClientDao clientDao;
  private Factory bizFactory;
  private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet ClientUccImpl.
   * 
   * @param bizFactory la factory.
   * @param clientDao le dao client.
   * @param daoServicesUcc le dao services.
   */
  public ClientUccImpl(Factory bizFactory, ClientDao clientDao, DaoServicesUcc daoServicesUcc) {
    super();
    this.clientDao = clientDao;
    this.bizFactory = bizFactory;
    this.daoServicesUcc = daoServicesUcc;
  }

  @Override
  public List<ClientDto> getClients() {
    List<ClientDto> clients;
    try {
      daoServicesUcc.demarrerTransaction();
      clients = clientDao.voirTousClient();
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(clients);
  }

  @Override
  public List<ClientDto> rechercherClients(String nom, String ville, int codePostal) {
    List<ClientDto> clientsCorrespondants;
    try {
      daoServicesUcc.demarrerTransaction();
      clientsCorrespondants = clientDao.voirClientAvecCritere(nom, ville, codePostal);
    } catch (DalException de) {
      daoServicesUcc.rollback();
      throw new FatalException(de.getMessage());
    }
    daoServicesUcc.commit();
    return Collections.unmodifiableList(clientsCorrespondants);
  }
}
