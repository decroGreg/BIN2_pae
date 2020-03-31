package be.ipl.pae.dal.impl;

import java.util.List;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

public class TypeDAmenagementMock implements TypeDAmenagementDao {

  private boolean voirTypeDAmenagement;

  public TypeDAmenagementMock(boolean voirTypeDAmenagement) {
    this.voirTypeDAmenagement = voirTypeDAmenagement;
  }

  @Override
  public List<TypeDAmenagementDto> voirTypeDAmenagement() {
    // TODO Auto-generated method stub
    return null;
  }



}
