package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Client;
import be.ipl.pae.biz.interfaces.Photo;

import java.sql.Timestamp;


public interface DevisDTO {

  Client getIdClient();

  void setIdClient(Client idClient);

  Timestamp getDate();

  void setDate(Timestamp date);

  double getMontant();

  void setMontant(double montant);

  String getDureeTravaux();

  void setDureeTravaux(String dureeTravaux);

  Photo getPhotoPreferee();

  void setPhotoPreferee(Photo photoPreferee);

  Etat getEtat();

  void setEtat(Etat etat);

}
