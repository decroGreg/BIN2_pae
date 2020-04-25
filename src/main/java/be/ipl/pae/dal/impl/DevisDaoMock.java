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

  /**
   * Constructeur Devis Mock
   * 
   * @param createDevis methode boolean.
   * @param voirTousDevis methode boolean.
   * @param getDevisClient methode boolean.
   * @param confirmerDateDevis methode boolean.
   * @param getIdDernierDevis methode boolean.
   * @param testDalException methode boolean.
   */
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

  @Override
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


  @Override
  public boolean changerEtatDevis(DevisDto devis) {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public DevisDto getDevisViaId(int idDevis) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public boolean ajouterPhotoPrefereeDevis(DevisDto devis, int idPhoto) {
    // TODO Auto-generated method stub
    return false;
  }
}
