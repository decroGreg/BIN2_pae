package be.ipl.pae.dal.daoservices;

import java.sql.Connection;

public interface DaoServicesUCC {

  void demarrerTransaction();

  void commit();

  void rollback();

  ThreadLocal<Connection> getConnections();

}
