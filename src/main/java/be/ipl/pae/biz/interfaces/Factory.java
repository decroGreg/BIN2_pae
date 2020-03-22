package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.dto.UserDto;

public interface Factory {

  UserDto getUserDto();

  DevisDto getDevisDto();

  AmenagementDto getAmenagementDto();

  ClientDto getClientDto();

  PhotoDto getPhotoDto();

  TypeDAmenagementDto getTypeDAmenagementDto();

}
