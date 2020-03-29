package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.Client;

public class ClientImpl implements Client, ClientDto {

  private int idClient;
  private String nom;
  private String prenom;
  private String rue;
  private String numero;
  private String boite;
  private int codePostal;
  private String ville;
  private String email;
  private String telephone;
  private int idUtilisateur;

  /**
   * Cree un objet ClientImpl
   * 
   * @param idClient l'id du client
   * @param nom le nom du client
   * @param prenom le prenom du client
   * @param rue la rue du client
   * @param numero le numero de la rue du client
   * @param boite la boite postale du client
   * @param codePostal le code postal du client
   * @param ville la ville du client
   * @param email l'email du client
   * @param telephone le telephone du client
   * @param idUtilisateur l'id de l'utilisateur lie au client
   */
  public ClientImpl(int idClient, String nom, String prenom, String rue, String numero,
      String boite, int codePostal, String ville, String email, String telephone,
      int idUtilisateur) {
    super();
    this.idClient = idClient;
    this.nom = nom;
    this.prenom = prenom;
    this.rue = rue;
    this.numero = numero;
    this.boite = boite;
    this.codePostal = codePostal;
    this.ville = ville;
    this.email = email;
    this.telephone = telephone;
    this.idUtilisateur = idUtilisateur;
  }

  public ClientImpl(int idClient, String nom, String prenom, String rue, String numero,
      String boite, int codePostal, String ville, String email, String telephone) {
    super();
    this.idClient = idClient;
    this.nom = nom;
    this.prenom = prenom;
    this.rue = rue;
    this.numero = numero;
    this.boite = boite;
    this.codePostal = codePostal;
    this.ville = ville;
    this.email = email;
    this.telephone = telephone;
  }

  public ClientImpl() {
    super();
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
  public String getRue() {
    return rue;
  }

  @Override
  public void setRue(String rue) {
    this.rue = rue;
  }

  @Override
  public String getNumero() {
    return numero;
  }

  @Override
  public void setNumero(String numero) {
    this.numero = numero;
  }

  @Override
  public String getBoite() {
    return boite;
  }

  @Override
  public void setBoite(String boite) {
    this.boite = boite;
  }

  @Override
  public int getCodePostal() {
    return codePostal;
  }

  @Override
  public void setCodePostal(int codePostal) {
    this.codePostal = codePostal;
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
  public String getTelephone() {
    return telephone;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public int getIdUtilisateur() {
    return idUtilisateur;
  }

  @Override
  public void setIdUtilisateur(int idUtilisateur) {
    this.idUtilisateur = idUtilisateur;
  }

  @Override
  public int getIdClient() {
    return idClient;
  }

  public void setIdClient(int idClient) {
    this.idClient = idClient;
  }
}
