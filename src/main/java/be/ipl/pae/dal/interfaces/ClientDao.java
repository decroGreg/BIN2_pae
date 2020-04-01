package be.ipl.pae.dal.interfaces;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;

public interface ClientDao {
  List<ClientDto> voirTousClient();

  boolean createClient(ClientDto clientDto);

  ClientDto getClientMail(String email);

}

