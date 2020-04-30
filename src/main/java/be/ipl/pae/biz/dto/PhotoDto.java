package be.ipl.pae.biz.dto;

public interface PhotoDto {

  int getIdAmenagement();

  void setIdAmenagement(int idAmenagement);

  int getIdDevis();

  void setIdDevis(int idDevis);

  int getIdPhoto();

  void setIdPhoto(int idPhoto);

  String getUrlPhoto();

  void setUrlPhoto(String urlPhoto);

  boolean isVisible();

  void setVisible(boolean visible);

}
