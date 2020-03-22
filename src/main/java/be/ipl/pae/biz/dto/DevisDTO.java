package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.impl.DevisImpl.Etat;

import java.sql.Timestamp;


public interface DevisDTO {

  int getIdClient();

  void setIdClient(int idClient);

  Timestamp getDate();

  void setDate(Timestamp date);

  double getMontant();

  void setMontant(double montant);

  String getDureeTravaux();

  void setDureeTravaux(String dureeTravaux);

  int getIdPhotoPreferee();

  void setIdPhotoPreferee(int photoPreferee);

  Etat getEtat();

  void setEtat(Etat etat);

  int getIdDevis();

}
