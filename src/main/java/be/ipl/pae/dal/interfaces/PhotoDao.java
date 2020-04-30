package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.PhotoDto;

import java.util.List;

public interface PhotoDao {

  boolean introduirePhoto(PhotoDto photoDto);

  List<PhotoDto> voirTousPhotos();

  List<PhotoDto> voirPhotoTypeDAmenagement(int idTypeAmenagement);

}
