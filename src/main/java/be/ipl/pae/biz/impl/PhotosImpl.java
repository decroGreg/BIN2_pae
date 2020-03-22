package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.interfaces.Photo;

public class PhotosImpl implements Photo {

  private int idPhoto;
  private int idAmenagement;
  private int idDevis;

  public PhotosImpl(int idPhoto, int idAmenagement, int idDevis) {
    super();
    this.idPhoto = idPhoto;
    this.idAmenagement = idAmenagement;
    this.idDevis = idDevis;
  }

  public PhotosImpl() {
    super();
  }

  @Override
  public int getIdAmenagement() {
    return idAmenagement;
  }

  @Override
  public void setIdAmenagement(int idAmenagement) {
    this.idAmenagement = idAmenagement;
  }

  @Override
  public int getIdDevis() {
    return idDevis;
  }

  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;
  }

  public int getIdPhoto() {
    return idPhoto;
  }
}
