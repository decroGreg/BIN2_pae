package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;

import java.util.List;

public interface AmenagementUcc {

  List<AmenagementDto> voirAmenagement();

  void ajouterAmenagement(List<String> listeIdTypeAmenagement, int idDevis);
}
