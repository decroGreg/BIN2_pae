package be.ipl.pae.dal.impl;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.dal.interfaces.ClientDao;

public class ClientDaoMock implements ClientDao {

  private boolean voirTousClient;
  private boolean createClient;

  public ClientDaoMock(boolean voirTousClient, boolean createClien) {
    this.voirTousClient = voirTousClient;
    this.createClient = createClien;
  }

  @Override
  public List<ClientDto> voirTousClient() {
    return null;
  }

  @Override
  public boolean createClient(ClientDto clientDto) {
    return true;
  }

  public ClientDto getClientMail(String email) {
    return null;
  }
}
