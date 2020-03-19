package be.ipl.pae.biz.impl;


import be.ipl.pae.biz.interfaces.Client;
import be.ipl.pae.biz.interfaces.Devis;
import be.ipl.pae.biz.interfaces.Photo;

import java.sql.Timestamp;



public class DevisImpl implements Devis {
  private Client idClient;
  private Timestamp date;
  private double montant;
  private String dureeTravaux;
  private Photo photoPreferee;
  private Etat etat;

  public enum Etat {
    I, DDI, ANP, DC, A, EC, FM, T, FF, V
  }

  public DevisImpl(Client idClient, Timestamp date, double montant, String dureeTravaux,
      Etat etat) {
    super();
    this.idClient = idClient;
    this.date = date;
    this.montant = montant;
    this.dureeTravaux = dureeTravaux;
    this.etat = etat;
  }

  public DevisImpl() {
    super();
  }

  @Override
  public Client getIdClient() {
    return idClient;
  }

  @Override
  public void setIdClient(Client idClient) {
    this.idClient = idClient;
  }

  @Override
  public Timestamp getDate() {
    return date;
  }

  @Override
  public void setDate(Timestamp date) {
    this.date = date;
  }

  @Override
  public double getMontant() {
    return montant;
  }

  @Override
  public void setMontant(double montant) {
    this.montant = montant;
  }

  @Override
  public String getDureeTravaux() {
    return dureeTravaux;
  }

  @Override
  public void setDureeTravaux(String dureeTravaux) {
    this.dureeTravaux = dureeTravaux;
  }

  @Override
  public Photo getPhotoPreferee() {
    return photoPreferee;
  }

  @Override
  public void setPhotoPreferee(Photo photoPreferee) {
    this.photoPreferee = photoPreferee;
  }

  @Override
  public Etat getEtat() {
    return etat;
  }

  @Override
  public void setEtat(Etat etat) {
    this.etat = etat;
  }
}
