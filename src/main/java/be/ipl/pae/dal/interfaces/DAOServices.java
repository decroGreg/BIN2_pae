package be.ipl.pae.dal.interfaces;

import java.sql.PreparedStatement;


public interface DAOServices {

  PreparedStatement getPreparedSatement(String requeteSQL);

}
