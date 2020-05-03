package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;

import java.util.List;


public interface TypeDAmenagementDao {

  List<TypeDAmenagementDto> voirTypeDAmenagement();

  boolean createTypeAmenagement(String nomType);

}
