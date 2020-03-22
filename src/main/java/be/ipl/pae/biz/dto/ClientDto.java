package be.ipl.pae.biz.dto;



public interface ClientDto {

  String getPseudo();

  void setPseudo(String pseudo);

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getVille();

  void setVille(String ville);

  String getEmail();

  void setEmail(String email);

  String getDateInscription();

  String getMotDePasse();

  void setMotDePasse(String motDePasse);

  String getStatut();

  void setStatut(String statut);

  int getIdClient();

}
