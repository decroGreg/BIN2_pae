package be.ipl.pae.biz.factory;

import be.ipl.pae.biz.dto.AmenagementDTO;
import be.ipl.pae.biz.dto.ClientDTO;
import be.ipl.pae.biz.dto.DevisDTO;
import be.ipl.pae.biz.dto.PhotoDTO;
import be.ipl.pae.biz.dto.TypeDAmenagementDTO;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.impl.AmenagementImpl;
import be.ipl.pae.biz.impl.ClientImpl;
import be.ipl.pae.biz.impl.DevisImpl;
import be.ipl.pae.biz.impl.PhotosImpl;
import be.ipl.pae.biz.impl.TypeDAmenagementImpl;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;

public class FactoryImpl implements Factory {

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }

  @Override
  public DevisDTO getDevisDTO() {
    // TODO Auto-generated method stub
    return new DevisImpl();
  }

  @Override
  public AmenagementDTO getAmenagementDTO() {
    // TODO Auto-generated method stub
    return new AmenagementImpl();
  }

  @Override
  public ClientDTO getClientDTO() {
    // TODO Auto-generated method stub
    return new ClientImpl();
  }

  @Override
  public PhotoDTO getPhotoDTO() {
    // TODO Auto-generated method stub
    return new PhotosImpl();
  }

  @Override
  public TypeDAmenagementDTO getTypeDAmenagementDTO() {
    // TODO Auto-generated method stub
    return new TypeDAmenagementImpl();
  }
}
