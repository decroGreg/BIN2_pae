package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;

import java.util.List;

public interface PhotoUcc {

  void ajouterPhotoAvantAmenagement(int idDevis, String urlPhoto);

  void ajouterPhotoApresAmenagement(AmenagementDto amenagementDto, String urlPhoto,
      boolean visible);

  List<PhotoDto> voirPhotos();

  List<PhotoDto> voirPhotoParTypeAmenagement(TypeDAmenagementDto typeAmenagementDto);

  List<PhotoDto> voirPhotoSonJardin(int idClient);
}
