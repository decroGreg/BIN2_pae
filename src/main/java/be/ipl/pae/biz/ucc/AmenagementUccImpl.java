package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.interfaces.AmenagementUcc;

public class AmenagementUccImpl implements AmenagementUcc {
  // private AmenagementDao amenagementDao;
  // private Factory bizFactory;
  // private DaoServicesUcc daoServicesUcc;

  /**
   * Cree un objet AmenagementUccImpl.
   * 
   * @param amenagementDao l amenagement dao.
   * @param bizFactory la factory.
   * @param daoServicesUcc le dal services.
   * 
   *        public AmenagementUccImpl(AmenagementDao amenagementDao, Factory bizFactory,
   *        DaoServicesUcc daoServicesUcc) { super(); this.amenagementDao = amenagementDao;
   *        this.bizFactory = bizFactory; this.daoServicesUcc = daoServicesUcc; }
   */



  /**
   * public void ajouterAmenagement(List<String> listeIdTypeAmenagement, int idDevis) { try {
   * daoServicesUcc.demarrerTransaction(); if (!listeIdTypeAmenagement.isEmpty()) { for (String
   * idTypeAmenagement : listeIdTypeAmenagement) {
   * amenagementDao.createAmenagement(Integer.parseInt(idTypeAmenagement), idDevis); } } } catch
   * (DalException de) { daoServicesUcc.rollback(); throw new IllegalArgumentException(); }
   * daoServicesUcc.commit(); }
   **/
}
