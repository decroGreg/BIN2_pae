package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.PhotoDTO;
import be.ipl.pae.biz.interfaces.Amenagement;
import be.ipl.pae.biz.interfaces.Devis;
import be.ipl.pae.biz.interfaces.Photo;

public class PhotosImpl implements Photo, PhotoDTO {
  private Amenagement idAmenagement;
  private Devis idDevis;

  public PhotosImpl(Amenagement idAmenagement, Devis idDevis) {
    super();
    this.idAmenagement = idAmenagement;
    this.idDevis = idDevis;
  }

  public PhotosImpl() {
    super();
  }

  @Override
  public Amenagement getIdAmenagement() {
    return idAmenagement;
  }

  @Override
  public void setIdAmenagement(Amenagement idAmenagement) {
    this.idAmenagement = idAmenagement;
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
