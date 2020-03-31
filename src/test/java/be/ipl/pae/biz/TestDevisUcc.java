package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.fail;

import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.dal.daoservices.DaoServices;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

class TestDevisUcc {

  DevisDao devisDao;
  Constructor<?> devisDaoConstruct;
  DevisUcc devisUcc;
  Constructor<?> devisUccConstruct;
  ClientDao clientDao;
  Factory Factory;
  DevisDto devisDto;
  DaoServices dalServices;

  @BeforeEach
  void setUp() throws Exception {}

  @Test
  void testVoirDevisClientDto() {
    fail("Not yet implemented");
  }

  @Test
  void testVoirDevis() {
    fail("Not yet implemented");
  }

  @Test
  void testIntroduireDevis() {
    fail("Not yet implemented");
  }

  @Test
  void testConfirmerDateDebut() {
    fail("Not yet implemented");
  }

}
