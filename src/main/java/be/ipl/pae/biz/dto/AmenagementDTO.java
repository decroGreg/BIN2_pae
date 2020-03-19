package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.interfaces.Devis;
import be.ipl.pae.biz.interfaces.TypeDAmenagement;


public interface AmenagementDTO {

  TypeDAmenagement getIdTypeAmenagement();

  void setIdTypeAmenagement(TypeDAmenagement idTypeAmenagement);

  Devis getIdDevis();

  void setIdDevis(Devis idDevis);

}
