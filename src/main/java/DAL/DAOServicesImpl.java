<<<<<<< HEAD
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import be.ipl.pae.biz.interfaces.DAOServices;

public class DAOServicesImpl implements DAOServices {

  private String url = "jdbc:postgresql://127.0.0.1/Projet";
  // private String url = "jdbc:postgresql://172.24.2.6:5432/dbmariabouraga";
  private Connection conn = null;

  public DAOServicesImpl() {


    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      conn = DriverManager.getConnection(url, "postgres", "Greg171598");
      // conn = DriverManager.getConnection(url, "mariabouraga", "S3CIMX1NU");
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }

  }

  @Override
  public PreparedStatement tryPreparedSatement(String requeteSQL) {
    PreparedStatement ps = null;
    try {
      ps = conn.prepareStatement(requeteSQL);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ps;
  }
}
=======
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.interfaces.DAOServices;

public class DAOServicesImpl implements DAOServices {

  // private String url = "jdbc:postgresql://127.0.0.1/Projet";
  // private String url = "jdbc:postgresql://172.24.2.6:5432/dbmariabouraga";
  // private String url = "jdbc:postgresql://127.0.0.1/init";

	
  Config conf = new Config();
  private String url = (String)conf.getConfigPropertyAttribute("db.url");
  private String login = (String)conf.getConfigPropertyAttribute("db.login");
  private String mdp = (String)conf.getConfigPropertyAttribute("db.mdp");
  private Connection conn = null;

  public DAOServicesImpl() {
	  
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      // conn = DriverManager.getConnection(url, "postgres", "Greg171598");
      //conn = DriverManager.getConnection(url, "mariabouraga", "S3CIMX1NU");
        conn = DriverManager.getConnection(url, login, mdp);

    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }

  }

  @Override
  public PreparedStatement tryPreparedSatement(String requeteSQL) {
    PreparedStatement ps = null;
    try {
      ps = conn.prepareStatement(requeteSQL);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ps;
  }
}
>>>>>>> refs/remotes/origin/master
