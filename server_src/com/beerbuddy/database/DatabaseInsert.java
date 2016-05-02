package com.beerbuddy.database;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Christian Witchger
 *
 */
public class DatabaseInsert extends DatabaseQuery {

	public DatabaseInsert(String[] a, String s) {
		super(a, s);
	}

	private boolean executeInsert(String query) {
		boolean ret = true;
		try {
			Connection connection = DatabaseConnection.getConnection();

			Statement statement = null;
			statement = connection.createStatement();

			statement.executeUpdate(query);

			statement.close();
			connection.close();

		} catch (Exception e) {
			ret = false;
			System.out.print(e);
		}

		return ret;
	}

	public boolean insert() {
		boolean ret = false;
		String query = makeQuery(getArgs());

		ret = executeInsert(query);

		return ret;
	}

	/**
	 * Makes the insert query with the arguments parameters
	 *
	 * @param args
	 * @return
	 */
	private String makeQuery(String[] args) {
		String query = DatabaseQuery.INSERT + " " + getDatabaseName() + " (";

		for (int i = 0; i < args.length; i = i + 2) {
			query += args[i] + ", ";
		}

		// Remove last comma
		query = query.substring(0, query.length() - 2) + ") VALUES (";

		for (int i = 1; i < args.length; i = i + 2) {
			query += "'" + args[i] + "', ";
		}

		// Remove last comma
		query = query.substring(0, query.length() - 2) + ")";

		return query;
	}

}
