package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.ClientDto;

import java.util.List;

public interface ClientUcc {

  List<ClientDto> getClients();

  List<ClientDto> rechercherClients(String nom, String ville, int codePostal);

  List<String> getVilles();
}
