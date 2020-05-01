package be.ipl.pae.biz.interfaces;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;

import java.util.List;


public interface TypeDAmenagementUcc {

  List<TypeDAmenagementDto> voirTypeDAmenagement();

  TypeDAmenagementDto ajouterTypeAmenagement(String descriptif);

}
