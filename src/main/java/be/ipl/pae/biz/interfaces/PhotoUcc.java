package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;

public interface PhotoUcc {

  void ajouterPhotoAvantAmenagement(int idDevis, String urlPhoto, int idAmenagement);

  void ajouterPhotoApresAmenagement(AmenagementDto amenagementDto, String urlPhoto);
}
