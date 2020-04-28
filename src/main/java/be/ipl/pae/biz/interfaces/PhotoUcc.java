package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.PhotoDto;

import java.util.List;

public interface PhotoUcc {

  void ajouterPhotoAvantAmenagement(int idDevis, String urlPhoto);

  void ajouterPhotoApresAmenagement(AmenagementDto amenagementDto, String urlPhoto);

  List<PhotoDto> voirPhotos();
}
