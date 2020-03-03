package DAL;

import java.sql.PreparedStatement;

public class DAOClient {

  private PreparedStatement trouverUtilisateurParEmail;
  private DAOServices services;

  public DAOClient() {
    this.services = new DAOServices();
  }

  public PreparedStatement getPreparedStatement(PreparedStatement ps) {
    services.tryPreparedSatement(ps);
    return ps;
  }
}
