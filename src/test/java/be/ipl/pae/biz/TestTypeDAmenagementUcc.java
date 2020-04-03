package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.daoservices.DaoServicesUcc;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class TestTypeDAmenagementUcc {

  TypeDAmenagementDao typeDAmenagementDao;
  Constructor<?> typeDAmenagementDaoConstruct;
  TypeDAmenagementUcc typeDAmenagementUcc;
  Constructor<?> typeDAmenagementUccConstruct;
  Factory bizFactory;
  TypeDAmenagementDto typeDAmenagementDto;
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
    typeDAmenagementDto = bizFactory.getTypeDAmenagementDto();

    typeDAmenagementDaoConstruct =
        Class.forName(Config.getConfigPropertyAttribute(TypeDAmenagementDao.class.getName()))
            .getConstructor(boolean.class, boolean.class);
    typeDAmenagementUccConstruct =
        Class.forName(Config.getConfigPropertyAttribute(TypeDAmenagementUcc.class.getName()))
            .getConstructor(Factory.class, TypeDAmenagementDao.class, DaoServicesUcc.class);
  }


  @Test
  @DisplayName("liste de type d'amenagement null")
  void testVoirTypeDAmenagement() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    typeDAmenagementDao =
        (TypeDAmenagementDao) typeDAmenagementDaoConstruct.newInstance(false, false);
    typeDAmenagementUcc = (TypeDAmenagementUcc) typeDAmenagementUccConstruct.newInstance(bizFactory,
        typeDAmenagementDao, dalServices);
    assertThrows(NullPointerException.class, () -> typeDAmenagementUcc.voirTypeDAmenagement());
  }

  @Test
  @DisplayName("liste de type d'amenagement ok")
  void testVoirTypeDAmenagement2() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    typeDAmenagementDao =
        (TypeDAmenagementDao) typeDAmenagementDaoConstruct.newInstance(true, false);
    typeDAmenagementUcc = (TypeDAmenagementUcc) typeDAmenagementUccConstruct.newInstance(bizFactory,
        typeDAmenagementDao, dalServices);
    assertEquals(typeDAmenagementUcc.voirTypeDAmenagement().get(0).getId(),
        typeDAmenagementDto.getId());
  }

  @Test
  @DisplayName("test dalException")
  void testVoirTypeDAmenagement3() throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    typeDAmenagementDao =
        (TypeDAmenagementDao) typeDAmenagementDaoConstruct.newInstance(false, true);
    typeDAmenagementUcc = (TypeDAmenagementUcc) typeDAmenagementUccConstruct.newInstance(bizFactory,
        typeDAmenagementDao, dalServices);
    assertThrows(IllegalArgumentException.class, () -> typeDAmenagementUcc.voirTypeDAmenagement());
  }
}
