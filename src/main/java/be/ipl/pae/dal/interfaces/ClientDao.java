package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.ClientDto;

import java.util.List;

public interface ClientDao {
  List<ClientDto> voirTousClient();

  boolean createClient(ClientDto clientDto);

  ClientDto getClientMail(String email);

  List<ClientDto> voirClientAvecCritere(String nom, String ville, int codePostal);

}

