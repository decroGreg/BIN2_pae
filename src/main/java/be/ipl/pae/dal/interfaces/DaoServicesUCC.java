package be.ipl.pae.dal.interfaces;

import java.sql.Connection;

public interface DaoServicesUCC {

  void demarrerTransaction();

  void commit();

  void rollback();

  ThreadLocal<Connection> getConnections();

}
