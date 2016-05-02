package com.beerbuddy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Christian Witchger
 *
 *         Is used for getting and maintaining a connection with the database
 */
public class DatabaseConnection {

	/**
	 * the connection with the database
	 */
	private static Connection connection = null;

	/**
	 * Creates the database connection with the BeerBuddy database
	 */
	private static void establishDatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DatabaseConnection.connection = DriverManager.getConnection(
			        "jdbc:mysql://24.211.134.170:3306/mydb?useSSL=false", "rootuser", "BeBuddyEr");
		} catch (Exception e) {
			throw new SecurityException("Class not found " + e.toString());
		}
	}

	/**
	 * Creates a connection if one already isn't in use
	 *
	 * @return a database connection
	 */
	public static Connection getConnection() {
		try {
			if ((DatabaseConnection.connection == null) || DatabaseConnection.connection.isClosed()) {
				DatabaseConnection.establishDatabaseConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return DatabaseConnection.connection;
	}
}
