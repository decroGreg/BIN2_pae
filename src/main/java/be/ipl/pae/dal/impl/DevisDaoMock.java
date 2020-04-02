package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.exceptions.DalException;

import java.sql.Timestamp;
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
      boolean confirmerDateDevis) {
    this.createDevis = createDevis;
    this.voirTousDevis = voirTousDevis;
    this.getDevisClient = getDevisClient;
    this.confirmerDateDevis = confirmerDateDevis;
  }

  public DevisDaoMock(boolean createDevis, boolean voirTousDevis, boolean getDevisClient,
      boolean confirmerDateDevis, boolean getIdDernierDevis, boolean testDalException) {
    super();
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
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<DevisDto> voirTousDevis() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DevisDto> getDevisClient(ClientDto client) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean confirmerDateDevis(int idDevis, Timestamp dateDebutTravaux) {
    // TODO Auto-generated method stub
    return false;
  }

  public int getIdDernierDevis() {
    return 0;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }
}
