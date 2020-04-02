package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoMock implements DevisDao {

  private boolean createDevis;
  private boolean voirTousDevis;
  private boolean getDevisClient;
  private boolean confirmerDateDevis;
  private boolean getIdDernierDevis;
  private boolean testDalException;
  private Factory factory;


  public DevisDaoMock(boolean createDevis, boolean voirTousDevis, boolean getDevisClient,
      boolean confirmerDateDevis, boolean getIdDernierDevis, boolean testDalException) {
    this.createDevis = createDevis;
    this.voirTousDevis = voirTousDevis;
    this.getDevisClient = getDevisClient;
    this.confirmerDateDevis = confirmerDateDevis;
    this.getIdDernierDevis = getIdDernierDevis;
    this.testDalException = testDalException;
    this.factory = new FactoryStub();
  }


  @Override
  public boolean createDevis(int idClient, DevisDto devis) {
    testDalException();
    return createDevis;
  }

  @Override
  public List<DevisDto> voirTousDevis() {
    testDalException();
    if (voirTousDevis) {
      List<DevisDto> devis = new ArrayList<DevisDto>();
      devis.add(factory.getDevisDto());
      return devis;
    }
    return null;
  }

  @Override
  public List<DevisDto> getDevisClient(ClientDto client) {
    testDalException();
    if (getDevisClient) {
      List<DevisDto> devis = new ArrayList<DevisDto>();
      devis.add(factory.getDevisDto());
      return devis;
    }
    return null;
  }

  @Override
  public boolean confirmerDateDevis(int idDevis, Timestamp dateDebutTravaux) {
    testDalException();
    return confirmerDateDevis;
  }

  public int getIdDernierDevis() {
    testDalException();
    if (getIdDernierDevis) {
      return 1;
    }
    return -1;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }
}
