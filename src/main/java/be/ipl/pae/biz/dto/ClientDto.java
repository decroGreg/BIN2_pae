package be.ipl.pae.biz.dto;



public interface ClientDto {

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getRue();

  void setRue(String rue);

  String getNumero();

  void setNumero(String numero);

  String getBoite();

  void setBoite(String boite);

  int getCodePostal();

  void setCodePostal(int codePostal);

  String getVille();

  void setVille(String ville);

  String getEmail();

  void setEmail(String email);

  String getTelephone();

  void setTelephone(String telephone);

  int getIdUtilisateur();

  void setIdUtilisateur(int idUtilisateur);

  int getIdClient();

  void setIdClient(int idClient);
}
