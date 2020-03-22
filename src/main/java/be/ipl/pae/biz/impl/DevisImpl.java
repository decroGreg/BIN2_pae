package be.ipl.pae.biz.impl;


import be.ipl.pae.biz.interfaces.Devis;

import java.sql.Timestamp;



public class DevisImpl implements Devis {

  private int idDevis;
  private int idClient;
  private Timestamp date;
  private double montant;
  private String dureeTravaux;
  private int idPhotoPreferee;
  private Etat etat;

  public enum Etat {
    I, DDI, ANP, DC, A, EC, FM, T, FF, V
  }

  public DevisImpl(int idDevis, int idClient, Timestamp date, double montant, String dureeTravaux,
      Etat etat) {
    super();
    this.idDevis = idDevis;
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
  public int getIdClient() {
    return idClient;
  }

  @Override
  public void setIdClient(int idClient) {
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
  public Etat getEtat() {
    return etat;
  }

  @Override
  public void setEtat(Etat etat) {
    this.etat = etat;
  }

  public int getIdDevis() {
    return idDevis;
  }

  public int getIdPhotoPreferee() {
    return idPhotoPreferee;
  }

  public void setIdPhotoPreferee(int idPhotoPreferee) {
    this.idPhotoPreferee = idPhotoPreferee;
  }
}
