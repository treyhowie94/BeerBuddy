package database_tools;

import java.sql.PreparedStatement;   
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 

import tools.BeerInformationFormatter;
import final_data.GlobalConstants;
import beer_garden_rscs.BeerInformationScavenger;
import beer_garden_rscs.SortByBrewery;
import static java.lang.System.err;

/**
 * This static class defines all of the subroutines that add information, found on the TapHunter 
 * page for Raleigh Beer Garden, and adds the information to their proper tables, in the proper order.
 * This class' uploadInfo method is the driver for all of these processes. The method call to proceed()
 * initializes all global variables that are in charge of the main functionality of the script, as well
 * as makes calls to the BeerInformationScaveneger static class to actually accomplish all of the
 * data aggregation. 
 * 
 *  @author treyhowie
 *
 */

public class DatabasePopulation {
	// ArrayList that coordinates beers into lists by their respective brewery
	private static ArrayList<Integer> beerListCoordination = new ArrayList<Integer>(0);
	
	
	/**
	 * This subroutine is the main method that takes care of all getting, sorting, and adding data to the database.
	 */
	public static void uploadInfo() {
		// allow to proceed ~~~> check proceed() pls
		if (proceed()) {
			// go through all unique breweries and add each brewerie's info to the database
			for (int i=0; i<SortByBrewery.breweries.size(); i++) {
				// quick check to make sure we're not adding a brewery without a name
				if (!SortByBrewery.breweries.get(i).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
					addBrewery(i);
				}
			}
			
			// go through all unique beers and add each beer's info to the database
			for (ArrayList<String> beer: BeerInformationScavenger.beers) {
				// quick check to make sure we're not adding a beer without a name
				if (!beer.get(0).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
					addBeer(beer);
				}
			}
			
			// go through all unique breweries and add a new beer list
			for (int i=0; i<SortByBrewery.breweries.size(); i++) {
				// quick check to make sure we're not adding a list for an unnamed brewery
				if (!SortByBrewery.breweries.get(i).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
					updateBeerListBeers(beerListCoordination.get(i), i);
				}
			}
		}
		else {
			err.println("unable to proceed adding new data");
			return;
		}
		
	}
	
	/**
	 * This method takes the information for the current beer being passed as a parameter and adds 
	 * its general and specific beer type, if they have not been added already, to their respective 
	 * tables in the database. Finally, it adds all of the beer's information as new entries into 
	 * the beer table of the database.
	 * 
	 * @param beerInfo - ArrayList<String> the current beer to add's information
	 */
	private static void addBeer(ArrayList<String> beerInfo) {
		// make sure the general beer type String is not empty
		boolean notNull = beerInfo.get(3).equals(GlobalConstants.INFORMATION_UNAVAILABLE);
		// check to see if this type has already been added to the db
		boolean exists  = RecordMonitor.recordExists("GeneralBeerType", beerInfo.get(3));
		
		int genType_id = -1;	// stores the int value for the general beer type once it has been added of found in the db
		// check to see if a new general beer type entry needs to be made in its respective table in the db
		if (!notNull && !exists) {
			genType_id = addGeneralBeerType(beerInfo.get(3));	// carry out adding the newly found type
		}
		// check if the current beer has a general beer type, but the entry already exists in the db
		else if (!notNull && exists) {
			try {
				// query to query the db with
				String query = "SELECT id_GeneralBeerType FROM GeneralBeerType WHERE value = ?";
				// secure statement to send to the db with the query
				PreparedStatement statement = DBConnection.connection.prepareStatement(query);
				// set the query variable to the general beer type of this beer
				statement.setString(1, beerInfo.get(3));
				
				// get the resulting rows from the db
				ResultSet rs = statement.executeQuery();
				
				// make sure there are entries in the result set
				if (rs.next()) {	
					genType_id = rs.getInt(1);	// set the gen beer type to the first result found 
				}								// *there's only supposed to be one*
				
			} catch (SQLException e) {
				err.println("unable to search for general beer type when finding general beer type id");
				e.printStackTrace();
			}
			
		}
		
		/*
		 * below is the exact same process as above for the general beer type, but for the specific beer type field
		 */
		
		notNull = beerInfo.get(4).equals(GlobalConstants.INFORMATION_UNAVAILABLE);
		exists  = RecordMonitor.recordExists("SpecificBeerType", beerInfo.get(4));
		
		int specType_id = -1;
		if (!notNull && !exists) {
			specType_id = addSpecificBeerType(beerInfo.get(4));
		}
		else if (!notNull && exists) {
			String query;
			try {
				query = "SELECT id_SpecificBeerType FROM SpecificBeerType WHERE value = ?";
				PreparedStatement statement = DBConnection.connection.prepareStatement(query);
				statement.setString(1, beerInfo.get(4));
				
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {	
					specType_id = rs.getInt(1);
				}

			} catch (SQLException e) {
				err.println("unable to search for general beer type when finding general beer type id");
				e.printStackTrace();
			}
		}
		
		int brewery_id = getBreweryID(beerInfo.get(5));	// get the brewery id for the beer by passing its string value to getBrewery()
														// all the breweries have been added at this point so its guaranteed to work  
		
		// carry out adding all the beer's information to a new row in the beer table
		try {
			// query to send to the db
			String insert = "INSERT INTO Beer(name, icon, abv, ibu, id_Brewry, id_Rating, barArea, "
					+ "GeneralBeerType_id_GeneralBeerType, SpecificBeerType_id_SpecificBeerType) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			// secured statement for the query
			PreparedStatement statement = DBConnection.connection.prepareStatement(insert);
			
			/*
			 * add all of the respective information for the new beer into the db
			 * 
			 * GlobalConstants.INFORMATION_UNAVAILABLE equality check is to make sure the information
			 * exists for the field if not, add the respective field's null value to the column
			 */
			
			if (!beerInfo.get(0).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
				statement.setString(1, beerInfo.get(0));
			}
			else {
				statement.setNull(1, java.sql.Types.VARCHAR);
			}
			if (!beerInfo.get(12).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
				statement.setString(2, beerInfo.get(12));
			}
			else {
				statement.setNull(2, java.sql.Types.VARCHAR);
			}
			if (!beerInfo.get(7).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
				statement.setDouble(3, Double.parseDouble(beerInfo.get(7)));
			}
			else {
				statement.setNull(3, java.sql.Types.DOUBLE);
			}
			if (!beerInfo.get(8).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
				statement.setInt(4, Integer.parseInt(beerInfo.get(8)));
			}
			else {
				statement.setNull(4, java.sql.Types.INTEGER);
			}
			if (brewery_id == -1) {
				statement.setNull(5, java.sql.Types.BIGINT);
			}
			else {
				statement.setInt(5, brewery_id);
			}
			
			statement.setNull(6, java.sql.Types.BIGINT);
			
			if (!beerInfo.get(2).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
				statement.setString(7, beerInfo.get(2));
			}
			else {
				statement.setNull(7, java.sql.Types.VARCHAR);
			}
			if (genType_id == -1) {
				statement.setNull(8, java.sql.Types.INTEGER);
			}
			else {
				statement.setInt(8, genType_id);
			}
			if (specType_id == -1) {
				statement.setNull(9, java.sql.Types.INTEGER);
			}
			else {
				statement.setInt(9, specType_id);
			}
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> unable to insert values into beer");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method adds a new brewery based on the index, passed as a parameter, for the next
	 * brewery in the SortByBrewery.breweries ArrayList.
	 * 
	 * @param index - the index that indicates which ArrayList to pull from the SortByBrewery.breweries ArrayList
	 */
	private static void addBrewery(int index) {
		// update the list id for the new brewery
		int beerList_id = findBeerListId() + 1;
		// add the new list of beers to the list ArrayList
		beerListCoordination.add(beerList_id);
		
		int loc_id = 0; // id of the breweries location from the location table in the db
		
		// make sure the location information is available
		if (!SortByBrewery.locations.get(index).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
			// format the location info into a usable array
			String[] locationInfo = BeerInformationFormatter.locationFormat(SortByBrewery.locations.get(index));
			// get the location id for the brewery
			loc_id = getLocationId(locationInfo);
		}
		
		// add the new entry into the brewery table
		try {
			// query for the db
			String insertBrewery = "INSERT INTO Brewry(name, id_Location, icon, beerListNumber) VALUES(?, ?, ?, ?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(insertBrewery);
			
			// put the information into the query variables
			statement.setString(1, SortByBrewery.breweries.get(index));
			statement.setInt(2, loc_id);
			statement.setString(3, SortByBrewery.iconUrls.get(index));
			statement.setInt(4, beerList_id);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			err.println("unable to update brewry with new brewry row");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method adds a new general beer type to the general beer type table in the database. This
	 * takes a String that acts as the new general beer type and inserts it into the table.
	 * 
	 * @param type - String that acts as the general type name for the new entry
	 * @return int - the id of the new entry
	 */
	private static int addGeneralBeerType(String type) {
		// makes sure that there is a type to add rather than unavailable information
		if (type.equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
			return -1;
		}
		
		// add the new entry if valid description is available
		try {
			// query for the db
			String newList = "INSERT INTO GeneralBeerType(value) VALUES (?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newList);
			
			// add type into the query variable
			statement.setString(1, type);
			statement.executeUpdate();
			
			// query to get the id of the new entry
			String idQuery = "SELECT id_GeneralBeerType FROM GeneralBeerType WHERE value = ?";
			statement = DBConnection.connection.prepareStatement(idQuery);
			statement.setString(1, type);
			
			// results found in the table (there should only be one)
			ResultSet rs = statement.executeQuery();
			
			// make sure there is an entry found
			if (rs.next()) {	
				// return the id of the new entry
				return rs.getInt(1);
			}
			else {
				return -1;
			}
			
		} catch (SQLException e) {
			err.println("SQL exception ~~~> unable to create new beer list");
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * This method adds a new location entry in the location table of the database and returns its id.
	 * 
	 * @param locationDetails - String[] of location details: City, State, Zip
	 * @return int - the id of the location
	 */
	private static int addLocation(String[] locationDetails) {
		try {
			// query for the db
			String newLocation = "INSERT INTO Location(city, state, zip) VALUES(?, ?, ?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newLocation);
			
			// set the query variables with the location details
			statement.setString(1, locationDetails[0]);	
			statement.setString(2, locationDetails[1]);
			statement.setString(3, locationDetails[2]);
			 
			statement.executeUpdate();
			
			// get the id for the newly created location entry from the location table
			String idQuery = "SELECT id_Location FROM Location WHERE city = \"" + locationDetails[0] +
					"\" AND state = \"" + locationDetails[1] + "\" AND zip = \"" + locationDetails[2] + "\"";
			statement = DBConnection.connection.prepareStatement(idQuery);
			
			// get the results of the query
			ResultSet rs = statement.executeQuery();
			
			// if theres a result, get the id of the first one (there should only be one)
			if (rs.next()) {	
				return rs.getInt(1);
			}
			// failure finding the new entry
			else {
				return -1;
			}
			
		} catch (SQLException e) {
			err.println("SQL exception ~~~> unable to create new location");
			e.printStackTrace();
			return -1;
		}
	
	}
	
	/**
	 * This method adds a new specific beer type to the specific beer type table in the database. This
	 * takes a String that acts as the new specific beer type and inserts it into the table.
	 * 
	 * @param type - String that acts as the specific type name for the new entry
	 * @return int - the id of the new entry
	 */
	private static int addSpecificBeerType(String type) {
		// makes sure that there is a type to add rather than unavailable information
		if (type.equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
			return -1;
		}
		
		// add the new entry if valid description is available
		try {
			// the query for the db
			String newList = "INSERT INTO SpecificBeerType(value) VALUES (?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newList);
			
			// set the type to the query variable
			statement.setString(1, type);
			statement.executeUpdate();
			
			// get the id for the new entry
			String idQuery = "SELECT id_SpecificBeerType FROM SpecificBeerType WHERE value = ?";
			statement = DBConnection.connection.prepareStatement(idQuery);
			statement.setString(1, type);
			
			// get the results from the query
			ResultSet rs = statement.executeQuery();
			
			// make sure there was a result (there should only be one)
			if (rs.next()) {	
				// return the id from the result
				return rs.getInt(1);
			}
			else {
				return -1;
			}
			
		} catch (SQLException e) {
			err.println("SQL exception ~~~> unable to create new beer list");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method finds the current list id by finding the tables max id value, 
	 * which corresponds to the current id value/
	 * 
	 * @return int - the current beerList id
	 */
	private static int findBeerListId() {
		// find the id from db query
		try {
			// query for the db
			String findNext_id = "SELECT MAX(listNumber) FROM BeerList";
			
			PreparedStatement statement = DBConnection.connection.prepareStatement(findNext_id);
			
			// get the results from the query
			ResultSet rs = statement.executeQuery();
			
			// make sure an entry was found (it always should be)
			if (rs.next()) {	
				// return the current id from the table
				return rs.getInt(1);
			}
			else {
				return -1;
			}
		} catch (SQLException e) {
			err.println("unable to find the BeerList current entry count in findBeerListID");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method gets an id corresponding to the brewery name denoted by the parameter breweryName.
	 * 
	 * @param breweryName - String that denotes the brewery's name to search for in the db
	 * @return int - the brewery id
	 */
	private static int getBreweryID(String breweryName) {
		// find the id of the brewery name
		try {
			// query for the db
			String findNext_id = "SELECT id_Brewry FROM Brewry WHERE name = ?";
			PreparedStatement statement = DBConnection.connection.prepareStatement(findNext_id);
			
			// set breweryName to the query variable
			statement.setString(1, breweryName);
			
			// get the query result
			ResultSet rs = statement.executeQuery();
			
			// make sure an entry is found
			if (rs.next()) {	
				// return the id from the result
				return rs.getInt(1);
			}
			else {
				return -1;
			}
		} catch (SQLException e) {
			err.println("unable to find the BeerList current entry count in findBeerListID");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method gets the location id form the entry in the location table based on the locationInfo
	 * details passed as a parameter.
	 * 
	 * @param locationInfo - String[] that contains location info: City, State, Zip
	 * @return - int - the id corresponding to the found entry
	 */
	private static int getLocationId(String[] locationInfo) {
		// check to see if an entry for the passed details exists in the location table
		if (!RecordMonitor.recordExists("Location", locationInfo)) {
			// if so, create a new location entry in the table
			return addLocation(locationInfo);
		}
		// otherwise, search the db for the entry 
		else {
			 try {
				// query for the db
				String query = "SELECT id_Location FROM Location WHERE city = \"" + locationInfo[0] +
							"\" AND state = \"" + locationInfo[1] + "\" AND zip = \"" + locationInfo[2] + "\"";
				 
				PreparedStatement statement = DBConnection.connection.prepareStatement(query);
				
				// get the result from the query
				ResultSet rs = statement.executeQuery();
				 
				// check to if there were any results
				if (rs.next()) {
					// return the id of the result
					return rs.getInt(1);
				}
				else {
					return -1;
				}
				 
			} catch (SQLException e) {
				err.println("failed to get location id from Location in addBrewery");
				e.printStackTrace();
				return -1;
			}
		}
	}
	
	/**
	 * This method makes sure that all program wide variables have been initialized properly so that
	 * execution can occur properly.
	 * 
	 * @return boolean - whether or not the program can proceed adding data to the database
	 */
	private static boolean proceed() {
		// make a database connection using the DBConnection static class reference
		DBConnection.loadDB();
		
		// make sure there was a connection
		if (DBConnection.connection == null) {
			err.println("failed to connect to database ~~~> unable to add information at this time");
			return false;
		}
		
		// scan the TapHunter web site for all data available
		BeerInformationScavenger.gatherBeerInformation();
		
		// make sure entries were found to add to the database
		if (BeerInformationScavenger.beers.isEmpty()) {
			err.println("resource gathering failed ~~~> unable to add information at this time");
			return false;
		}
		
		// sort all of the breweries found on TapHunter
		SortByBrewery.sortBeersByBrewery();
		
		// make sure entries exist to add to the breweries table
		if (SortByBrewery.breweries.isEmpty()) {
			err.println("beer sorting failed ~~~> unable to add information at this time");
			return false;
		}
		
		// success if all data was initialized properly
		return true;
	}
	
	
	/**
	 * This method adds a new entry in beer list with all of the beers that are part of the designated list
	 * by adding their ids to the list.
	 * 
	 * @param list_id - int that points to the list in the beer list table
	 * @param listIndex - int that denotes the list index in SortByBrewery.beersSortedByBreweries for the 
	 * 					  beers that are to be added to the new list 
	 */
	private static void updateBeerListBeers(int list_id, int listIndex) {
		// loop through the list found at index listIndex
		for (String beer: SortByBrewery.beersSortedByBreweries.get(listIndex)) {
			int currBeer_id = 0;	// variable for the current beer's id
			try {
				// the db query
				String beer_quer = "SELECT id_Beer FROM Beer WHERE name = ?";
				
				PreparedStatement statement = DBConnection.connection.prepareStatement(beer_quer);
				// set the beer name in the query variable
				statement.setString(1, beer);
				
				// the result from the query
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					currBeer_id = rs.getInt(1);	// set the beer id variable to the result column for id
				}
				else {
					err.println("failed to get beer id in in order to add BeerList Beer");
				}
				
				// add a new beer id to the list
				String newListQuery = "INSERT INTO BeerList(id_Beer, listNumber) VALUES(?, ?)";
				statement = DBConnection.connection.prepareStatement(newListQuery);
				statement.setInt(1, currBeer_id);
				statement.setInt(2, list_id);
				
				statement.executeUpdate();
				
			} catch (SQLException e) {
				err.println("unable to update BeerList entry");
				e.printStackTrace();
				break;
			}
		}
	}
		
}
