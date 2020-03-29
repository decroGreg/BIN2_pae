package be.ipl.pae.biz.impl;


import java.sql.Timestamp;
import be.ipl.pae.biz.interfaces.Devis;



public class DevisImpl implements Devis {

  public enum Etat {
    I, DDI, ANP, DC, A, EC, FM, T, FF, V;

    @Override
    public String toString() {
      return super.toString();
    }
  }

  private int idDevis;
  private int idClient;
  private Timestamp date;
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

  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;

  }
}
