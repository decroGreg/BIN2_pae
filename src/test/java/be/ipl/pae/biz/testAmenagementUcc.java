package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class testAmenagementUcc {
  AmenagementDao amenagementDao;
  Constructor<?> amenagementDaoConstruct;
  AmenagementUcc amenagementUcc;
  Constructor<?> amenagementUccConstruct;
  Factory bizFactory;
  AmenagementDto amenagementDto;
  DaoServicesUcc dalServices;

  {
    try {
      Config.init("test.properties");
    } catch (IOException ex) {

      ex.printStackTrace();
    }
  }

  @BeforeEach
  void setUp() throws Exception {
    bizFactory = (Factory) Class.forName(Config.getConfigPropertyAttribute(Factory.class.getName()))
        .getConstructor().newInstance();
    dalServices = (DaoServicesUcc) Class
        .forName(Config.getConfigPropertyAttribute(DaoServices.class.getName())).getConstructor()
        .newInstance();
    amenagementDto = bizFactory.getAmenagementDto();
    amenagementDaoConstruct =
        Class.forName(Config.getConfigPropertyAttribute(AmenagementDao.class.getName()))
            .getConstructor(boolean.class, boolean.class, boolean.class);
    amenagementUccConstruct =
        Class.forName(Config.getConfigPropertyAttribute(AmenagementUcc.class.getName()))
            .getConstructor(AmenagementDao.class, Factory.class, DaoServicesUcc.class);
  }

  @Test
  @DisplayName("amenagements null")
  void testVoirAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, false, false);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertThrows(NullPointerException.class, () -> amenagementUcc.voirAmenagement());
  }

  @Test
  @DisplayName("test dalException")
  void testVoirAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, false, true);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertThrows(FatalException.class, () -> amenagementUcc.voirAmenagement());
  }

  @Test
  @DisplayName("amenagements ok")
  void testVoirAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, true, false);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertEquals(amenagementUcc.voirAmenagement().get(0).getIdDevis(), amenagementDto.getIdDevis());
  }

  @Test
  @DisplayName("list id type amenagement null")
  void testAjouterAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, false, false);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertThrows(NullPointerException.class,
        () -> amenagementUcc.ajouterAmenagement(null, amenagementDto.getIdDevis()));
  }

  @Test
  @DisplayName("idDevis incorrect")
  void testAjouterAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    List<String> idTypeAmenagement = new ArrayList<String>();
    idTypeAmenagement.add("1");
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(true, false, false);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertThrows(BizException.class, () -> amenagementUcc.ajouterAmenagement(idTypeAmenagement, 0));
  }

  @Test
  @DisplayName("list id type amenagement pas vide ")
  void testAjouterAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    List<String> idTypeAmenagement = new ArrayList<String>();
    idTypeAmenagement.add("1");
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(true, false, false);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    amenagementUcc.ajouterAmenagement(idTypeAmenagement, 1);
  }

  @Test
  @DisplayName("test dalException")
  void testAjouterAmenagement4() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    List<String> idTypeAmenagement = new ArrayList<String>();
    idTypeAmenagement.add("1");
    amenagementDao = (AmenagementDao) amenagementDaoConstruct.newInstance(false, false, true);
    amenagementUcc = (AmenagementUcc) amenagementUccConstruct.newInstance(amenagementDao,
        bizFactory, dalServices);
    assertThrows(FatalException.class,
        () -> amenagementUcc.ajouterAmenagement(idTypeAmenagement, 1));
  }
}
