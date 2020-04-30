package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.factory.FactoryStub;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class PhotoDaoMock implements PhotoDao {

  private boolean introduirePhoto;
  private boolean voirTousPhotos;
  private boolean testDalException;
  private Factory factory;


  /**
   * Cree un mock de photoDao.
   * 
   * @param introduirePhoto boolean.
   * @param voirTousPhotos liste de photos;
   * @param testDalException boolean.
   */
  public PhotoDaoMock(boolean introduirePhoto, boolean voirTousPhotos, boolean testDalException) {
    super();
    this.introduirePhoto = introduirePhoto;
    this.voirTousPhotos = voirTousPhotos;
    this.testDalException = testDalException;
    this.factory = new FactoryStub();
  }

  @Override
  public boolean introduirePhoto(PhotoDto photoDto) {
    testDalException();
    return introduirePhoto;
  }

  @Override
  public List<PhotoDto> voirTousPhotos() {
    testDalException();
    if (voirTousPhotos) {
      List<PhotoDto> photos = new ArrayList<PhotoDto>();
      photos.add(factory.getPhotoDto());
      return photos;
    }
    return null;
  }

  private void testDalException() {
    if (testDalException) {
      throw new DalException();
    }
  }
}
