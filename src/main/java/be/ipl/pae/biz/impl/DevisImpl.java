package be.ipl.pae.biz.impl;


import be.ipl.pae.biz.interfaces.Devis;

import java.sql.Timestamp;



public class DevisImpl implements Devis {

  public enum Etat {
    I, FD, DC, A, FM, FF, V;
  }

  private int idDevis;
  private int idClient;
  private Timestamp date;
  private Timestamp dateDebutTravaux;
  private double montant;
  private String dureeTravaux;
  private int idPhotoPreferee;
  private Etat etat;


  /**
   * Cree un objet DevisImpl
   * 
   * @param idDevis l'id du devis.
   * @param idClient l'id du client.
   * @param date la date du devis.
   * @param montant le montant du devis.
   * @param dureeTravaux la duree des travaux du devis.
   * @param etat l'etat du devis.
   */
  public DevisImpl(int idDevis, int idClient, Timestamp date, Timestamp dateDebutTravaux,
      double montant, String dureeTravaux, int idPhotoPreferee, Etat etat) {
    super();
    this.idDevis = idDevis;
    this.idClient = idClient;
    this.date = date;
    this.dateDebutTravaux = dateDebutTravaux;
    this.montant = montant;
    this.dureeTravaux = dureeTravaux;
    this.idPhotoPreferee = idPhotoPreferee;
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

  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;

  }

  @Override
  public boolean checkEtat() {
    if (etat.equals(Etat.I) || etat.equals(Etat.FD) || etat.equals(Etat.DC) || etat.equals(Etat.A)
        || etat.equals(Etat.FM) || etat.equals(Etat.FF) || etat.equals(Etat.V)) {
      return true;
    }
    return false;
  }

  public Timestamp getDateDebutTravaux() {
    return dateDebutTravaux;
  }

  public void setDateDebutTravaux(Timestamp dateDebutTravaux) {
    this.dateDebutTravaux = dateDebutTravaux;
  }
}
