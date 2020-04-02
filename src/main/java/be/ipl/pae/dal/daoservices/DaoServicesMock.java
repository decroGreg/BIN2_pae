package be.ipl.pae.dal.daoservices;

import java.sql.Connection;

public class DaoServicesMock implements DaoServicesUcc {

  @Override
  public void demarrerTransaction() {
    // TODO Auto-generated method stub

  }

  @Override
  public void commit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void rollback() {
    // TODO Auto-generated method stub

  }

  @Override
  public ThreadLocal<Connection> getConnections() {
    // TODO Auto-generated method stub
    return null;
  }



}
