package be.ipl.pae.dal.daoservices;

import java.sql.PreparedStatement;


public interface DaoServices {

  PreparedStatement getPreparedSatement(String requeteSql);

}
