package be.ipl.pae.biz.factory;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.impl.AmenagementImpl;
import be.ipl.pae.biz.impl.ClientImpl;
import be.ipl.pae.biz.impl.DevisImpl;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.impl.PhotosImpl;
import be.ipl.pae.biz.impl.TypeDAmenagementImpl;
import be.ipl.pae.biz.impl.UserImpl;
import be.ipl.pae.biz.interfaces.Factory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FactoryStub implements Factory {

  public FactoryStub() {

  }

  @Override
  public UserDto getUserDto() {
    return new UserImpl(1, "testPseudo", "testNom", "testPrenom", "testVille", "testEmail",
        "$2a$10$PFufDLXls/2hw1wzMkZmz.AdaQcI3DYUaj8hOLrwz4SFuqYxGwSHK");
  }

  @Override
  public DevisDto getDevisDto() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse("2020-03-30" + " 00:00:00.000");
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Long time = parsedDate.getTime();
    Timestamp timestamp = new Timestamp(time);
    return new DevisImpl(1, 1, timestamp, timestamp, 850, "1 jours", Etat.FD);
  }

  @Override
  public AmenagementDto getAmenagementDto() {
    return new AmenagementImpl(1, 1, 1);
  }

  @Override
  public ClientDto getClientDto() {
    return new ClientImpl(1, "testNom", "testPrenom", "testRue", "129", "1A", 1040, "testVille",
        "testEmail", "+32842568985", 1);
  }

  @Override
  public PhotoDto getPhotoDto() {
    return new PhotosImpl(1, " ", 1, 1);
  }

  @Override
  public TypeDAmenagementDto getTypeDAmenagementDto() {
    return new TypeDAmenagementImpl(1, "testDescription");
  }
}
