package be.ipl.pae.dal.interfaces;



public interface DaoServicesUCC {

  void demarrerTransaction();

  void commit();

  void rollback();

}
