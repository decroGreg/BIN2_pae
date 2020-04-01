package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.dal.interfaces.DevisDao;

import java.sql.Timestamp;
import java.util.List;

public class DevisDaoMock implements DevisDao {

  private boolean createDevis;
  private boolean voirTousDevis;
  private boolean getDevisClient;
  private boolean confirmerDateDevis;

  public DevisDaoMock(boolean createDevis, boolean voirTousDevis, boolean getDevisClient,
      boolean confirmerDateDevis) {
    this.createDevis = createDevis;
    this.voirTousDevis = voirTousDevis;
    this.getDevisClient = getDevisClient;
    this.confirmerDateDevis = confirmerDateDevis;
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
}
