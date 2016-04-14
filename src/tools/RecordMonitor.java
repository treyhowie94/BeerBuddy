package tools;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;

import database_tools.DBConnection;
import final_data.GlobalConstants;
import static java.lang.System.err;

public class RecordMonitor {
	
	private RecordMonitor() {
		//static class ~~~> prevent instantiation
	}
	
	public static boolean recordExists(String table, String... values) {
		try {
			if (!table.equals("Location") && (values.length == 1)) {
				final String queryCheck = "SELECT * FROM " + table + " WHERE " + getColumn(table) + " = ?";
				
				PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
				ps.setString(1, values[0]);
				
				final ResultSet resultSet = ps.executeQuery();
				
				int count = 0;
				while (resultSet.next()) {
					count = resultSet.getInt(1);
				}
				
				if (count > 0) {
			    	return true;
			    }
			    else {
			    	return false;
			    }
			}
			else {
				final String queryCheck = "SELECT * FROM " + table + " WHERE " + "city = ? "
						+ "AND state = ? AND zip = ?";
				
				PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
				ps.setString(1, values[0]);
				ps.setString(2, values[1]);
				
				if (!values[2].equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
					ps.setString(3, values[2]);
				}
				else {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}
				
				final ResultSet resultSet = ps.executeQuery();
				
				int count = 0;
				while (resultSet.next()) {
					count = resultSet.getInt(1);
				}
				
				if (count > 0) {
			    	return true;
			    }
			    else {
			    	return false;
			    }
			}
			
		} catch (SQLException e) {
			err.println("SQL error ~~~> unable to search " + table + " for the specified values");
			e.printStackTrace();
			return false;
		}
	}
	
	private static String getColumn(String t_id) {
		assert !(t_id == null);
		
		if (t_id.equals("Buddy")) {
			return "username";
		}
		else if (t_id.equals("Restaurant") || t_id.equals("Beer") || t_id.equals("Beer")) {
			return "name";
		}
		else if (t_id.equals("GeneralBeerType") || t_id.equals("SpecificBeerType")) {
			return "value";
		}
		else {
			return "INVAliDD_$$";
		}
	}
	
}
