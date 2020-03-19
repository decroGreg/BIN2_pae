package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.interfaces.Amenagement;
import be.ipl.pae.biz.interfaces.Devis;


public interface PhotoDTO {

  Amenagement getIdAmenagement();

  void setIdAmenagement(Amenagement idAmenagement);

  Devis getIdDevis();

  void setIdDevis(Devis idDevis);

}
