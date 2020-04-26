package be.ipl.pae.dal.interfaces;

import be.ipl.pae.biz.dto.PhotoDto;

public interface PhotoDao {

  boolean createPhoto(PhotoDto photoDto);
}
