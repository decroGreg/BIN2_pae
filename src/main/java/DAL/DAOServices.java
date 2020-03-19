package DAL;

import java.sql.PreparedStatement;


public interface DAOServices {

  PreparedStatement tryPreparedSatement(String requeteSQL);

}
