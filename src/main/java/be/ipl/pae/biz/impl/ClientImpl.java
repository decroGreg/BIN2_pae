package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.ClientDTO;
import be.ipl.pae.biz.interfaces.Client;

public class ClientImpl implements Client, ClientDTO {
  private String pseudo;
  private String nom;
  private String prenom;
  private String ville;
  private String email;
  private String dateInscription;
  private String motDePasse;
  private String statut;

  public ClientImpl(String pseudo, String nom, String prenom, String ville, String email,
      String motDePasse, String statut) {
    super();
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
  public void setDateInscription(String dateInscription) {
    this.dateInscription = dateInscription;
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
}
