package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class ClientDaoMock implements ClientDao {

  private boolean voirTousClient;
  private boolean createClient;
  private boolean getClientMail;
  private boolean voirClientAvecCritere;
  private boolean getClientById;
  private boolean getVilles;
  private boolean getIdDernierClient;
  private boolean testDalException;
  private Factory factory;

  /**
   * Constructeur Client Mock.
   * 
   * @param voirTousClient methode boolean.
   * @param createClien methode boolean.
   * @param getClientMail methode boolean.
   * @param testDalException methode boolean.
   */
  public ClientDaoMock(boolean voirTousClient, boolean createClient, boolean getClientMail,
      boolean getClientById, boolean getVilles, boolean voirClientAvecCritere,
      boolean getIdDernierClient, boolean testDalException) {
    this.voirTousClient = voirTousClient;
    this.createClient = createClient;
    this.getClientMail = getClientMail;
    this.voirClientAvecCritere = voirClientAvecCritere;
    this.getClientById = getClientById;
    this.getVilles = getVilles;
    this.getIdDernierClient = getIdDernierClient;
    this.testDalException = testDalException;
    this.factory = new FactoryStub();
  }

  @Override
  public List<ClientDto> voirTousClient() {
    testDalException();
    if (voirTousClient) {
      List<ClientDto> clients = new ArrayList<ClientDto>();
      clients.add(factory.getClientDto());
      return clients;
    }
    return null;
  }

  @Override
  public boolean createClient(ClientDto clientDto) {
    testDalException();
    return createClient;
  }

  @Override
  public ClientDto getClientMail(String email) {
    testDalException();
    if (getClientMail) {
      return factory.getClientDto();
    }
    return null;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }

  @Override
  public List<ClientDto> voirClientAvecCritere(String nom, String ville, int codePostal) {
    testDalException();
    if (voirClientAvecCritere) {
      List<ClientDto> clientsAvecCritere = new ArrayList<ClientDto>();
      clientsAvecCritere.add(factory.getClientDto());
      return clientsAvecCritere;
    }
    return null;
  }

  @Override
  public ClientDto getClientById(int id) {
    testDalException();
    if (getClientById) {
      return factory.getClientDto();
    }
    return null;
  }

  @Override
  public List<String> getVilles() {
    testDalException();
    if (getVilles) {
      List<String> villes = new ArrayList<String>();
      villes.add(factory.getClientDto().getVille());
      return villes;
    }
    return null;
  }

  @Override
  public int getIdDernierClient() {
    testDalException();
    if (getIdDernierClient) {
      return factory.getClientDto().getIdClient();
    }
    return 0;
  }
}
