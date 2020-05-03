package be.ipl.pae.dal.daoservices;


public interface DaoServicesUcc {

  void demarrerTransaction();

  void commit();

  void rollback();


}
