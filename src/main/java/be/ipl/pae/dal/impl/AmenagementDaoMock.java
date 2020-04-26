package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class AmenagementDaoMock implements AmenagementDao {

  private boolean createAmenagement;
  private boolean voirTousAmenagement;
  private boolean testDalException;
  private Factory factory;

  /**
   * Cree un mock d'Amenagement.
   * 
   * @param createAmenagement
   * @param voirTousAmenagement
   * @param testDalException
   */
  public AmenagementDaoMock(boolean createAmenagement, boolean voirTousAmenagement,
      boolean testDalException) {
    super();
    this.createAmenagement = createAmenagement;
    this.voirTousAmenagement = voirTousAmenagement;
    this.testDalException = testDalException;
    this.factory = new FactoryStub();
  }

  @Override
  public boolean createAmenagement(int idTypeAmenagement, int idDevis) {
    testDalException();
    return createAmenagement;
  }

  @Override
  public List<AmenagementDto> voirTousAmenagement() {
    testDalException();
    if (voirTousAmenagement) {
      List<AmenagementDto> amenagements = new ArrayList<AmenagementDto>();
      amenagements.add(factory.getAmenagementDto());
      return amenagements;
    }
    return null;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }
}
