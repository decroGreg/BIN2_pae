package be.ipl.pae.biz.factory;

import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;

public class FactoryImpl implements Factory {

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }
}
