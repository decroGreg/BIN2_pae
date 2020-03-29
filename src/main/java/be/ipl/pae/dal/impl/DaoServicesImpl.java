
package be.ipl.pae.dal.impl;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.dal.interfaces.DaoServices;
import be.ipl.pae.dal.interfaces.DaoServicesUCC;
import be.ipl.pae.exceptions.DALException;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoServicesImpl implements DaoServices, DaoServicesUCC {

  // private String url = "jdbc:postgresql://127.0.0.1/Projet";
  // private String url = "jdbc:postgresql://172.24.2.6:5432/dbmariabouraga";
  // private String url = "jdbc:postgresql://127.0.0.1/init";


  Config conf = new Config();
  private String url = (String) conf.getConfigPropertyAttribute("db.url");
  private String login = (String) conf.getConfigPropertyAttribute("db.login");
  private String mdp = (String) conf.getConfigPropertyAttribute("db.mdp");
  private BasicDataSource ds;
  private ThreadLocal<Connection> connections;


  public DaoServicesImpl() {
    connections = new ThreadLocal<Connection>();
    try {
      ds = new BasicDataSource();
      ds.setDriverClassName("org.postgresql.Driver");
      ds.setUrl(url);
      ds.setUsername(login);
      ds.setPassword(mdp);
      // Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
    /**
     * try { // conn = DriverManager.getConnection(url, "postgres", "Greg171598"); // conn =
     * DriverManager.getConnection(url, "mariabouraga", "S3CIMX1NU"); conn =
     * DriverManager.getConnection(url, login, mdp);
     * 
     * } catch (SQLException e) { System.out.println("Impossible de joindre le server !");
     * System.exit(1); }
     **/

  }

  @Override
  public void demarrerTransaction() {
    if (connections.get() != null) {
      throw new DALException("Transaction déja ouverte");
    }
    try {
      Connection conn = this.ds.getConnection();
      connections.set(conn);
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commit() {
    try (Connection conn = connections.get()) {
      if (conn == null) {
        throw new DALException("Ne peut pas commit car il n'y a pas de transaction");
      }
      conn.commit();
      conn.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      connections.remove();
    }
  }

  @Override
  public void rollback() {
    try (Connection conn = connections.get()) {
      if (conn == null) {
        throw new DALException("Ne peut pas rollback car il n'y a pas de transaction");
      }
      conn.rollback();
      conn.setAutoCommit(true);
    } catch (SQLException e) {
      throw new DALException("Erreur dans le rollback");
    } finally {
      connections.remove();
    }
  }

  @Override
  public PreparedStatement getPreparedSatement(String requeteSQL) {
    try {
      return connections.get().prepareStatement(requeteSQL);
    } catch (SQLException e) {
      throw new DALException("Erreur dans le prepared statement");
    }
  }
}