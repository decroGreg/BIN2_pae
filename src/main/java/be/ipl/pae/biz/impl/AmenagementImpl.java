package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.AmenagementDTO;
import be.ipl.pae.biz.interfaces.Amenagement;
import be.ipl.pae.biz.interfaces.Devis;
import be.ipl.pae.biz.interfaces.TypeDAmenagement;

public class AmenagementImpl implements Amenagement, AmenagementDTO {
  private TypeDAmenagement idTypeAmenagement;
  private Devis idDevis;

  public AmenagementImpl(TypeDAmenagement idTypeAmenagement, Devis idDevis) {
    super();
    this.idTypeAmenagement = idTypeAmenagement;
    this.idDevis = idDevis;
  }

  public AmenagementImpl() {
    super();
  }

  @Override
  public TypeDAmenagement getIdTypeAmenagement() {
    return idTypeAmenagement;
  }

  @Override
  public void setIdTypeAmenagement(TypeDAmenagement idTypeAmenagement) {
    this.idTypeAmenagement = idTypeAmenagement;
  }

  @Override
  public Devis getIdDevis() {
    return idDevis;
  }

  @Override
  public void setIdDevis(Devis idDevis) {
    this.idDevis = idDevis;
  }
}
