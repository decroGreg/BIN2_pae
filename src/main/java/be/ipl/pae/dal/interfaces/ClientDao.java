package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

import java.util.List;

public interface ClientDao {
  List<DevisDto> getDevisClient(ClientDto client);

  List<ClientDto> voirTousClient();
}
