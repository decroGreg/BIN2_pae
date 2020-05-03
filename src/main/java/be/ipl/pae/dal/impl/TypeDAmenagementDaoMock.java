package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class TypeDAmenagementDaoMock implements TypeDAmenagementDao {

  private boolean voirTypeDAmenagement;
  private boolean testDalException;
  private Factory factory;

  /**
   * Constructeur de type d'amenagement mock.
   * 
   * @param voirTypeDAmenagement methode boolean.
   * @param testDalException methode boolean.
   */
  public TypeDAmenagementDaoMock(boolean voirTypeDAmenagement, boolean testDalException) {
    this.voirTypeDAmenagement = voirTypeDAmenagement;
    this.testDalException = testDalException;
    this.factory = new FactoryStub();
  }

  @Override
  public List<TypeDAmenagementDto> voirTypeDAmenagement() {
    testDalException();
    if (voirTypeDAmenagement) {
      List<TypeDAmenagementDto> types = new ArrayList<TypeDAmenagementDto>();
      types.add(factory.getTypeDAmenagementDto());
      return types;
    }
    return null;
  }


  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }

  @Override
  public boolean createTypeAmenagement(String nomType) {
    // TODO Auto-generated method stub
    return false;
  }


}
