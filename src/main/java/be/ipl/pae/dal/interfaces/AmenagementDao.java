package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.AmenagementDto;

import java.util.List;

public interface AmenagementDao {

  boolean createAmenagement(int idTypeAmenagement, int idDevis);

  List<AmenagementDto> voirTousAmenagement();

}
