package be.ipl.pae.dal.interfaces;

import java.sql.PreparedStatement;


public interface DaoServices {

  PreparedStatement getPreparedSatement(String requeteSQL);

}
