package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

import java.util.List;


public interface DevisUcc {

  // Afficher les devis d'un client
  List<DevisDto> voirDevis(ClientDto client);

  // Afficher tous les devis
  List<DevisDto> voirDevis();

  void introduireDevis(ClientDto nouveauClient, int idClient, DevisDto devis,
      List<String> listeIdTypeAmenagement);

  void confirmerDateDebut(DevisDto devis);

  void changerEtat(DevisDto devis);
}
