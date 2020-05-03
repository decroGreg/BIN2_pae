package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;

import java.sql.Timestamp;
import java.util.List;


public interface DevisDao {

  boolean createDevis(int idClient, DevisDto devis);

  List<DevisDto> voirTousDevis();

  List<DevisDto> getDevisClient(ClientDto client);

  boolean confirmerDateDevis(DevisDto devis);

  DevisDto getDevisViaId(int idDevis);

  boolean ajouterPhotoPrefereeDevis(DevisDto devis, int idPhoto);

  boolean changerEtatDevis(DevisDto devis);

  boolean repousserDateDebut(DevisDto devis);

  int getIdDernierDevis();

  List<DevisDto> voirDevisAvecCritere(Timestamp dateDevis, String nomClient, double prixMin,
      double prixMax, int typeDAmenagementRecherche, int idUtilisateur);
}
