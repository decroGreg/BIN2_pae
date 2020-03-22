package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.interfaces.Amenagement;

public class AmenagementImpl implements Amenagement {
  private int idAmenagement;
  private int idTypeAmenagement;
  private int idDevis;

  /**
   * Cree un objet AmenagementImpl.
   * 
   * @param idAmenagement l'id de l'amenagement.
   * @param idTypeAmenagement l'id du type d'amenagement.
   * @param idDevis l'id du devis.
   */
  public AmenagementImpl(int idAmenagement, int idTypeAmenagement, int idDevis) {
    super();
    this.idAmenagement = idAmenagement;
    this.idTypeAmenagement = idTypeAmenagement;
    this.idDevis = idDevis;
  }

  public AmenagementImpl() {
    super();
  }

  @Override
  public int getIdTypeAmenagement() {
    return idTypeAmenagement;
  }

  @Override
  public void setIdTypeAmenagement(int idTypeAmenagement) {
    this.idTypeAmenagement = idTypeAmenagement;
  }

  @Override
  public int getIdDevis() {
    return idDevis;
  }

  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;
  }

  public int getIdAmenagement() {
    return idAmenagement;
  }
}
