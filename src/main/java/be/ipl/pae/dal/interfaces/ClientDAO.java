package be.ipl.pae.dal.interfaces;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

public interface ClientDAO {
  List<DevisDto> getDevisClient(ClientDto client);
}