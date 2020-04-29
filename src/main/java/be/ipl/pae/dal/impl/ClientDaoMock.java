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
  public ClientDaoMock(boolean voirTousClient, boolean createClien, boolean getClientMail,
      boolean testDalException) {
    this.voirTousClient = voirTousClient;
    this.createClient = createClien;
    this.getClientMail = getClientMail;
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
    // TODO Auto-generated method stub
    return null;
  }
}
