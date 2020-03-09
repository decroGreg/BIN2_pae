package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOServices {

  private String url = "jdbc:postgresql://127.0.0.1/postgres";//"jdbc:postgresql://172.24.2.6:5432/dbmariabouraga";
  private Connection conn = null;

  public DAOServices() {


    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      conn = DriverManager.getConnection(url, "postgres", "1234"); //(url, "mariabouraga", "S3CIMX1NU")
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }

  }

  public PreparedStatement tryPreparedSatement(PreparedStatement ps) {
    try {
      ps = conn.prepareStatement("SELECT * FROM projet.clients WHERE email = ?");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ps;
  }
}
