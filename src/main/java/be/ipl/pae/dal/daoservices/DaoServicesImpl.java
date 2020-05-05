
package be.ipl.pae.dal.daoservices;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.exceptions.DalException;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoServicesImpl implements DaoServices, DaoServicesUcc {


  private BasicDataSource ds; // C'est la connection pool
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

  /*
   * Creer une connexion afin d'effectuer une requete sql
   */
  @Override
  public void demarrerTransaction() {
    if (connections.get() != null) { // Verifie si il existe deja un thread courant
      throw new DalException("Transaction déja ouverte");
    }
    try {
      Connection conn = this.ds.getConnection(); // etablit une connection
      connections.set(conn);
      System.out.println(connections.get());
      conn.setAutoCommit(false); // Considere toutes les requetes SQL comme une transaction
    } catch (SQLException ex) {
      throw new DalException("Erreur dans le demarrage de la transaction ");
    }
  }

  /*
   * Commit la transaction et supprime la connection
   */
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

  /**
   * Annule les modifications faites par la transaction en cas d'erreur pour pas garder de fausses
   * valeurs dans la db
   */
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

  /**
   * prepare une requete sql
   * 
   * @param requeteSql la requete en question.
   * @return le resultat du query.
   */
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
