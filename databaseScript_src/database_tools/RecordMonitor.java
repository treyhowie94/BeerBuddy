package database_tools;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;

import final_data.GlobalConstants;
import static java.lang.System.err;

/**
 * This static class is used to make sure a record exists in the database by taking a table name and the values to
 * check for in the database. Used throughout the DatabasePopulation main functioning to make sure no duplicate 
 * entries are being added to the database.
 * 
 * @author treyhowie
 *
 */
public class RecordMonitor {
	
	/**
	 * Private constructor for static class
	 */
	private RecordMonitor() {
		//static class ~~~> prevent instantiation
	}
	
	/**
	 * This method take a table name and values to check for in that table, both passed as parameters, and queries the
	 * database to see if the record corresponding to the passed values exists.
	 * 
	 * @param table - String that denotes the table name to search
	 * @param values - String... that denotes the values to check for in the given table
	 * @return boolean - whether the record exists in the table or not
	 */
	public static boolean recordExists(String table, String... values) {
		try {
			// general case where we're not searching the location table
			if (!table.equals("Location") && (values.length == 1)) {
				// query for the db
				String queryCheck = "SELECT * FROM " + table + " WHERE " + getColumn(table) + " = ?";
				
				PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
				// set the query variable to the entry name passed in values
				ps.setString(1, values[0]);
				
				// get results from query
				ResultSet resultSet = ps.executeQuery();
				
				int count = 0;	// count the number of entries found by the query
				while (resultSet.next()) {
					count = resultSet.getInt(1);
				}
				
				// if any entries found, return true
				if (count > 0) {
			    	return true;
			    }
			    else {
			    	return false;
			    }
			}
			// special case for location table. The location needs to be exactly the same as all location details
			else {
				// query for db
				String queryCheck = "SELECT * FROM " + table + " WHERE " + "city = ? "
						+ "AND state = ? AND zip = ?";
				
				PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
				// set the location details to the query variables
				ps.setString(1, values[0]);
				ps.setString(2, values[1]);
				
				// if zip is available, set the passed value
				if (!values[2].equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
					ps.setString(3, values[2]);
				}
				// set query value for zip to null
				else {
					ps.setNull(3, java.sql.Types.VARCHAR);
				}
				
				// get query results
				ResultSet resultSet = ps.executeQuery();
				
				int count = 0;	// count number of found entries from query
				while (resultSet.next()) {
					count = resultSet.getInt(1);
				}
				
				// if there are results, return true
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
	
	/**
	 * This method gets the column name to query in recordExists(). the column to use as the query is determined by the
	 * table name passed as a String.
	 * 
	 * @param tableName - String denoting the table name
	 * @return
	 */
	private static String getColumn(String tableName) {
		// check to see which table is being passed and then returning the column name needed
		if (tableName.equals("Buddy")) {
			return "username";
		}
		else if (tableName.equals("Restaurant") || tableName.equals("Beer") || tableName.equals("Beer")) {
			return "name";
		}
		else if (tableName.equals("GeneralBeerType") || tableName.equals("SpecificBeerType")) {
			return "value";
		}
		else {
			return "INVAliDD_$$";
		}
	}
	
}
