package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.interfaces.Photo;

public class PhotosImpl implements Photo {

  private int idPhoto;
  private String urlPhoto;
  private int idAmenagement;
  private int idDevis;

  /**
   * Cree un objet PhotoImpl
   * 
   * @param idPhoto l'id de la photo.
   * @param urlPhoto l'url de la photo
   * @param idAmenagement l'id de l'amenagement.
   * @param idDevis l'id du devis.
   */
  public PhotosImpl(int idPhoto, String urlPhoto, int idAmenagement, int idDevis) {
    super();
    this.idPhoto = idPhoto;
    this.urlPhoto = urlPhoto;
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

  public void setIdPhoto(int idPhoto) {
    this.idPhoto = idPhoto;
  }

  public String getUrlPhoto() {
    return urlPhoto;
  }

  public void setUrlPhoto(String urlPhoto) {
    this.urlPhoto = urlPhoto;
  }

  @Override
  public boolean checkPhoto() {
    if (this.idDevis > 0 && this.idAmenagement > 0 && !this.urlPhoto.equals(" ")) {
      return true;
    }
    return false;
  }
}
