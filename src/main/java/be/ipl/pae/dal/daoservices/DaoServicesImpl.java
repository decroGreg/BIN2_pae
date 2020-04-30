
package be.ipl.pae.dal.daoservices;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.exceptions.DalException;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoServicesImpl implements DaoServices, DaoServicesUcc {

  // private String url = "jdbc:postgresql://127.0.0.1/Projet";
  // private String url = "jdbc:postgresql://172.24.2.6:5432/dbmariabouraga";
  // private String url = "jdbc:postgresql://127.0.0.1/init";

  private BasicDataSource ds;
  private ThreadLocal<Connection> connections;


  /**
   * Constructeur Dao Services.
   */
  public DaoServicesImpl() {
    connections = new ThreadLocal<Connection>();
    try {
      ds = new BasicDataSource();
      ds.setDriverClassName("org.postgresql.Driver");
      ds.setUrl(Config.getConfigPropertyAttribute("db.url"));
      ds.setUsername(Config.getConfigPropertyAttribute("db.login"));
      ds.setPassword(Config.getConfigPropertyAttribute("db.mdp"));
      // Class.forName("org.postgresql.Driver");
    } catch (Exception ex) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  @Override
  public void demarrerTransaction() {
    if (connections.get() != null) {
      throw new DalException("Transaction déja ouverte");
    }
    try {
      Connection conn = this.ds.getConnection();
      connections.set(conn);
      System.out.println(connections.get());
      conn.setAutoCommit(false);
    } catch (SQLException ex) {
      throw new DalException("Erreur dans le demarrage de la transaction ");
    }
  }

  @Override
  public void commit() {
    try (Connection conn = connections.get()) {
      if (conn == null) {
        throw new DalException("Ne peut pas commit car il n'y a pas de transaction");
      }
      conn.commit();
      conn.setAutoCommit(true);
    } catch (SQLException ex) {
      throw new DalException("Erreur dans le commit");
    } finally {
      connections.remove();
    }
  }

  @Override
  public void rollback() {
    try (Connection conn = connections.get()) {
      if (conn == null) {
        throw new DalException("Ne peut pas rollback car il n'y a pas de transaction");
      }
      conn.rollback();
      conn.setAutoCommit(true);
    } catch (SQLException ex) {
      throw new DalException("Erreur dans le rollback");
    } finally {
      connections.remove();
    }
  }

  @Override
  public PreparedStatement getPreparedSatement(String requeteSql) {
    try {
      System.out.println("test1:" + connections.toString());
      return connections.get().prepareStatement(requeteSql);
    } catch (SQLException ex) {
      throw new DalException("Erreur dans le prepared statement");
    }
  }

  public ThreadLocal<Connection> getConnections() {
    return connections;
  }
}
