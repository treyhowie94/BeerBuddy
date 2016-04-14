package database_tools;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection; 

import static java.lang.System.err;
import static java.lang.System.out;

public class DBConnection {
	private static final String DB_CONN     = "jdbc:mysql://24.211.134.170:3306/mydb";
	private static final String ROOT_USER   = "rootuser";
	private static final String ROOT_PASS   = "BeBuddyEr";
 	private static final String DRIVER	    = "com.mysql.jdbc.Driver";
	
	public static Connection connection = null;
	
	
	private DBConnection() {
		//static class ~~~> prevent instantiation
	}
	
	public static void loadDB() {
		try {
			if (connection == null || connection.isClosed()) {
				out.println("establishing database connection");
				connectDB();
			}
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> could not connect to the Database");
		}
	}
	
	private static void connectDB() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DB_CONN, ROOT_USER, ROOT_PASS);
			
			assert !(connection == null);
			
			out.println ("database connection established");
		} catch (ClassNotFoundException e) {
			err.println("could not locate class: com.mysql.jdbc.Driver"
					+ " ~~~> unable to establish connection driver");
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> could not connect to the Database");
		}
	}
}

