package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

import java.util.List;


public interface ClientUcc {

  List<DevisDto> voirDevis(ClientDto client);

}
