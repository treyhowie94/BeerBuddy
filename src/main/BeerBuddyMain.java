package main;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tools.BeerInformationFormatter;
import tools.DBTablePrinter;
import database_tools.DBConnection;
import database_tools.DatabasePopulation;
import beer_garden_rscs.BeerInformationScavenger;
import beer_garden_rscs.SortByBrewery;
import static java.lang.System.err;
import static java.lang.System.out;
//import static java.lang.System.out;

public class BeerBuddyMain {

	
	/**
	 * Information ArrayList layout:
	 * 
	 * name | number | bar | beerType | specificType | brewery | brewLocation | abv | ibu | pintPrice | 
	 * ten_oz | five_oz | logoLocation
	 * 
	 * 
	 * @param barArea
	 * @param beerType
	 * @param tap
	 * @return
	 */
	public static void main(String[] args) {
		DatabasePopulation.uploadInfo();
	}
	
	public static void deleteTableVal(int id, String table, String row) {
		try {
			PreparedStatement statement = DBConnection.connection.prepareStatement("DELETE FROM " + table + " WHERE " + row + " = ?");
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
