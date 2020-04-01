package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

import java.util.List;

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
