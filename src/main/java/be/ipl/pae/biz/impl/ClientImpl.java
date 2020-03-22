package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.interfaces.Client;

public class ClientImpl implements Client {

  private int idClient;
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String email;
  private String dateInscription;
  private String motDePasse;
  private String statut;

  /**
   * Cree un objet ClientImpl
   * 
   * @param idClient l'id du client.
   * @param pseudo le pseudo du client.
   * @param nom le nom du client.
   * @param prenom le prenom du client.
   * @param ville la ville du client.
   * @param email l'email du client.
   * @param motDePasse le mot de passe du client.
   * @param statut le statut du client.
   */
  public ClientImpl(int idClient, String pseudo, String nom, String prenom, String ville,
      String email, String motDePasse, String statut) {
    super();
    this.idClient = idClient;
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
    this.ville = ville;
    this.email = email;
    this.motDePasse = motDePasse;
    this.statut = statut;
  }

  public ClientImpl() {
    super();
  }

  @Override
  public String getPseudo() {
    return pseudo;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getPrenom() {
    return prenom;
  }

  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @Override
  public String getVille() {
    return ville;
  }

  @Override
  public void setVille(String ville) {
    this.ville = ville;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getDateInscription() {
    return dateInscription;
  }

  @Override
  public String getMotDePasse() {
    return motDePasse;
  }

  @Override
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  @Override
  public String getStatut() {
    return statut;
  }

  @Override
  public void setStatut(String statut) {
    this.statut = statut;
  }

  public int getIdClient() {
    return idClient;
  }
}
