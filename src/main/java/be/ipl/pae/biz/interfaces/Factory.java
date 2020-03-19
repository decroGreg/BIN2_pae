package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.AmenagementDTO;
import be.ipl.pae.biz.dto.ClientDTO;
import be.ipl.pae.biz.dto.DevisDTO;
import be.ipl.pae.biz.dto.PhotoDTO;
import be.ipl.pae.biz.dto.TypeDAmenagementDTO;
import be.ipl.pae.biz.dto.UserDTO;

public interface Factory {

  UserDTO getUserDTO();

  DevisDTO getDevisDTO();

  AmenagementDTO getAmenagementDTO();

  ClientDTO getClientDTO();

  PhotoDTO getPhotoDTO();

  TypeDAmenagementDTO getTypeDAmenagementDTO();

}
