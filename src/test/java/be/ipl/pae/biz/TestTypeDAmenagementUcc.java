package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.fail;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

class TestTypeDAmenagementUcc {

  TypeDAmenagementDao typeDAmenagementDao;
  Constructor<?> typeDAmenagementDaoConstruct;
  TypeDAmenagementUcc typeDAmenagementUcc;
  Constructor<?> typeDAmenagementUccConstruct;
  Factory Factory;
  TypeDAmenagementDto typeDAmenagementDto;
  DaoServices dalServices;

  @BeforeEach
  void setUp() throws Exception {}

  /**@Test
  void testVoirTypeDAmenagement() {
    fail("Not yet implemented");
  }*/

}