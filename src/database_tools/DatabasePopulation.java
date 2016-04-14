package database_tools;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 

import tools.BeerInformationFormatter;
import tools.RecordMonitor;
import final_data.GlobalConstants;
import final_data.RestaurantData;
import beer_garden_rscs.BeerInformationScavenger;
import beer_garden_rscs.SortByBrewery;
import static java.lang.System.err;
import static java.lang.System.out;

public class DatabasePopulation {
	private static ArrayList<Integer> beerListCoordination = new ArrayList<Integer>(0);
	
	
	public static void uploadInfo() {
		if (proceed()) {
			for (int i=0; i<SortByBrewery.breweries.size(); i++) {
				if (!SortByBrewery.breweries.get(i).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
					addBrewery(i);
				}
			}
			
			for (ArrayList<String> beer: BeerInformationScavenger.beers) {
				if (!beer.get(0).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
					addBeer(beer);
				}
			}
			
			for (int i=0; i<SortByBrewery.breweries.size(); i++) {
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
	
	private static void addBeer(ArrayList<String> beerInfo) {
		boolean notNull = beerInfo.get(3).equals(GlobalConstants.INFORMATION_UNAVAILABLE);
		boolean exists  = RecordMonitor.recordExists("GeneralBeerType", beerInfo.get(3));
		
		int genType_id = -1;
		if (!notNull && !exists) {
			genType_id = addGeneralBeerType(beerInfo.get(3));
		}
		else if (!notNull && exists) {
			String query;
			try {
				query = "SELECT id_GeneralBeerType FROM GeneralBeerType WHERE value = ?";
				PreparedStatement statement = DBConnection.connection.prepareStatement(query);
				statement.setString(1, beerInfo.get(3));
				
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {	
					genType_id = rs.getInt(1);
				}

			} catch (SQLException e) {
				err.println("unable to search for general beer type when finding general beer type id");
				e.printStackTrace();
			}
			
		}
		
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
		
		int brewery_id = getBreweryID(beerInfo.get(5));
		
		try {
			String insert = "INSERT INTO Beer(name, icon, abv, ibu, id_Brewry, id_Rating, barArea, "
					+ "GeneralBeerType_id_GeneralBeerType, SpecificBeerType_id_SpecificBeerType) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(insert);
			
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
	
	private static void addBrewery(int index) {
		int beerList_id = findBeerListId() + 1;
		beerListCoordination.add(beerList_id);
		
		int loc_id = 0;
		if (!SortByBrewery.locations.get(index).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {	
			String[] locationInfo = BeerInformationFormatter.locationFormat(SortByBrewery.locations.get(index));
			loc_id = getLocationId(locationInfo);
		}
		
		out.println("id in addBrew: " + loc_id);
		
		PreparedStatement statement;
		try {
			String insertBrewery = "INSERT INTO Brewry(name, id_Location, icon, beerListNumber) VALUES(?, ?, ?, ?)";
			statement = DBConnection.connection.prepareStatement(insertBrewery);
			
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
	
	private static void updateBeerListBeers(int list_id, int listIndex) {
		for (String beer: SortByBrewery.beersSortedByBreweries.get(listIndex)) {
			int currBeer_id = 0;
			try {
				String beer_quer = "SELECT id_Beer FROM Beer WHERE name = ?";
				
				PreparedStatement statement = DBConnection.connection.prepareStatement(beer_quer);
				statement.setString(1, beer);
				
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					currBeer_id = rs.getInt(1);
				}
				else {
					err.println("failed to get beer id in in order to add BeerList Beer");
				}
				
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
	
	public static int getLocationId(String[] locationInfo) {
		if (!RecordMonitor.recordExists("Location", locationInfo)) {
			int add = addLocation(locationInfo);
			out.println("id in getLocationID: " + add);
			return add;
		}
		else {
			 try {
				 String query = "SELECT id_Location FROM Location WHERE city = \"" + locationInfo[0] +
							"\" AND state = \"" + locationInfo[1] + "\" AND zip = \"" + locationInfo[2] + "\"";
				 
				 PreparedStatement statement = DBConnection.connection.prepareStatement(query);
				 ResultSet rs = statement.executeQuery();
				 
				 if (rs.next()) {
					int add = rs.getInt(1);
					out.println("id in getLocationID: " + add);
					return add;
				 }
				 else {
					 out.println(-1);
					 return -1;
				 }
				 
			} catch (SQLException e) {
				err.println("failed to get location id from Location in addBrewery");
				e.printStackTrace();
				out.println(-1);
				return -1;
			}
		}
	}
	
	public static int addLocation(String[] locationDetails) {
		try {
			String newLocation = "INSERT INTO Location(city, state, zip) VALUES(?, ?, ?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newLocation);
			
			statement.setString(1, locationDetails[0]);	
			statement.setString(2, locationDetails[1]);
			statement.setString(3, locationDetails[2]);
			
			statement.executeUpdate();
			
			String idQuery = "SELECT id_Location FROM Location WHERE city = \"" + locationDetails[0] +
					"\" AND state = \"" + locationDetails[1] + "\" AND zip = \"" + locationDetails[2] + "\"";
			statement = DBConnection.connection.prepareStatement(idQuery);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {	
				int add = rs.getInt(1); 
				out.println("id in addLocation: " + add);
				return add;
			}
			else {
				out.println(-1);
				return -1;
			}
			
		} catch (SQLException e) {
			err.println("SQL exception ~~~> unable to create new location");
			e.printStackTrace();
			out.println(-1);
			return -1;
		}
	
	}
	
	private static int addGeneralBeerType(String type) {
		if (type.equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
			return -1;
		}
		
		try {
			String newList = "INSERT INTO GeneralBeerType(value) VALUES (?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newList);
			
			statement.setString(1, type);
			statement.executeUpdate();
			
			String idQuery = "SELECT id_GeneralBeerType FROM GeneralBeerType WHERE value = ?";
			statement = DBConnection.connection.prepareStatement(idQuery);
			statement.setString(1, type);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {	
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
	
	private static int addSpecificBeerType(String type) {
		if (type.equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
			return -1;
		}
		
		try {
			String newList = "INSERT INTO SpecificBeerType(value) VALUES (?)";
			PreparedStatement statement = DBConnection.connection.prepareStatement(newList);
			
			statement.setString(1, type);
			statement.executeUpdate();
			
			String idQuery = "SELECT id_SpecificBeerType FROM SpecificBeerType WHERE value = ?";
			statement = DBConnection.connection.prepareStatement(idQuery);
			statement.setString(1, type);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {	
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
	
	private static int getBreweryID(String breweryName) {
		PreparedStatement statement;
		try {
			String findNext_id = "SELECT id_Brewry FROM Brewry WHERE name = ?";
			statement = DBConnection.connection.prepareStatement(findNext_id);
			statement.setString(1, breweryName);
			
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {	
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
	
	private static int findBeerListId() {
		PreparedStatement statement;
		try {
			String findNext_id = "SELECT MAX(listNumber) FROM BeerList";
			
			statement = DBConnection.connection.prepareStatement(findNext_id);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {	
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
		
	private static boolean proceed() {
		DBConnection.loadDB();
		if (DBConnection.connection == null) {
			err.println("failed to connect to database ~~~> unable to add information at this time");
			return false;
		}
		
		BeerInformationScavenger.gatherBeerInformation();
		if (BeerInformationScavenger.beers.isEmpty()) {
			err.println("resource gathering failed ~~~> unable to add information at this time");
			return false;
		}
		
		SortByBrewery.sortBeersByBrewery();
		if (SortByBrewery.breweries.isEmpty()) {
			err.println("beer sorting failed ~~~> unable to add information at this time");
			return false;
		}
		
		return true;
	}
	
}
