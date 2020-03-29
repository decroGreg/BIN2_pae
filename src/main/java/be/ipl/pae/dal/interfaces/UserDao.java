package be.ipl.pae.dal.interfaces;

import java.util.List;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;


public interface UserDao {

  UserDto getUserConnexion(String email);

  boolean createInscription(UserDto userDTO);

  public boolean createDevis(int idClient, DevisDto devis);

  public List<DevisDto> voirTousDevis();

  public List<UserDto> voirTousUser();

  public List<ClientDto> voirTousClient();

  public boolean lierUserClient(ClientDto client, UserDto user);
}
