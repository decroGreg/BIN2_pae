package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
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


  public ClientDaoMock(boolean voirTousClient, boolean createClien, boolean getClientMail,
      boolean testDalException, Factory factory) {
    this.voirTousClient = voirTousClient;
    this.createClient = createClien;
    this.getClientMail = getClientMail;
    this.testDalException = testDalException;
    this.factory = factory;
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
}
