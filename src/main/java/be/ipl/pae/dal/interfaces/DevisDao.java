package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

import java.sql.Timestamp;
import java.util.List;


public interface DevisDao {

  boolean createDevis(int idClient, DevisDto devis);

  List<DevisDto> voirTousDevis();

  List<DevisDto> getDevisClient(ClientDto client);

  boolean confirmerDateDevis(int idDevis, Timestamp dateDebutTravaux);
}
