package database_tools;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection; 

import static java.lang.System.err;
import static java.lang.System.out;

public class DBConnection {
	// connection info
	private static final String DB_CONN     = "jdbc:mysql://24.211.134.170:3306/mydb";
	private static final String ROOT_USER   = "rootuser";
	private static final String ROOT_PASS   = "BeBuddyEr";
 	private static final String DRIVER	    = "com.mysql.jdbc.Driver";
	
	// connection reference to make changes to the database
 	public static Connection connection = null;
	
	// number of tries connecting to the database
 	// when this is 3 the program will exit
 	private static int connectionTries = 0;
	
	
	private DBConnection() {
		//static class ~~~> prevent instantiation
	}
	
	/**
	 * This subroutine creates a connection object that defines the connection to the remote database
	 */
	public static void loadDB() {
		try {
			// make sure there isn't already a connection
			if (connection == null || connection.isClosed()) {
				out.println("establishing database connection");
				connectDB();
			}
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> could not connect to the Database");
		}
	}
	
	/**
	 * This subroutine connects to the Driver class and then attempts to connect to the remote database.
	 * If successful, connection will be set to a connection object. If failure, it retries 3 times and if there
	 * is still no connection formed the program exits with error code -1.
	 */
	private static void connectDB() {
		try {
			// get driver class object
			Class.forName(DRIVER);
			
			// form connection
			connection = DriverManager.getConnection(DB_CONN, ROOT_USER, ROOT_PASS);
			
			out.println ("database connection established");
		} catch (ClassNotFoundException e) {
			err.println("could not locate class: com.mysql.jdbc.Driver"
					+ " ~~~> unable to establish connection driver");
			System.exit(-1);  // irrecoverable error ~~~> no point in continuing
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> could not connect to the Database");
			
			// check to see how many tries to connect to the databse there has been
			if (connectionTries < 3) {	
				connectionTries++;  // update number of attempts
				connectDB();  // retry
			}
			else {
				System.exit(-1); // cannot connect to the database ~~~> can't continue
			}
		}
	}
}

