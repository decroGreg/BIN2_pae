package DAL;

import java.sql.PreparedStatement;

public class DAO_Client {

  private PreparedStatement trouverUtilisateurParEmail;
  private DAO_Services services;

  public DAO_Client() {
    this.services = new DAO_Services();
  }

  public PreparedStatement getPreparedStatement(PreparedStatement ps) {
    services.tryPreparedSatement(ps);
    return ps;
  }
}
