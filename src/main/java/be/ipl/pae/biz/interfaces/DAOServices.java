package be.ipl.pae.biz.interfaces;

import java.sql.PreparedStatement;


public interface DAOServices {

  PreparedStatement tryPreparedSatement(String requeteSQL);

}
