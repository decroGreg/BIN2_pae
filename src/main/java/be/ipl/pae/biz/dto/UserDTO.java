package be.ipl.pae.biz.dto;

import java.sql.Timestamp;


public interface UserDTO {

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

  String getMotDePasse();

  void setMotDePasse(String motDePasse);

  char getStatut();

  void setStatut(char statut);

  Timestamp getDateInscription();

}
