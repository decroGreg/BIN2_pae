package be.ipl.pae.biz.factory;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.impl.AmenagementImpl;
import be.ipl.pae.biz.impl.ClientImpl;
import be.ipl.pae.biz.impl.DevisImpl;
import be.ipl.pae.biz.impl.PhotosImpl;
import be.ipl.pae.biz.impl.TypeDAmenagementImpl;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;

public class FactoryImpl implements Factory {

  @Override
  public UserDto getUserDto() {
    return new UserImpl();
  }

  @Override
  public DevisDto getDevisDto() {
    // TODO Auto-generated method stub
    return new DevisImpl();
  }

  @Override
  public AmenagementDto getAmenagementDto() {
    // TODO Auto-generated method stub
    return new AmenagementImpl();
  }

  @Override
  public ClientDto getClientDto() {
    // TODO Auto-generated method stub
    return new ClientImpl();
  }

  @Override
  public PhotoDto getPhotoDto() {
    // TODO Auto-generated method stub
    return new PhotosImpl();
  }

  @Override
  public TypeDAmenagementDto getTypeDAmenagementDto() {
    // TODO Auto-generated method stub
    return new TypeDAmenagementImpl();
  }
}
