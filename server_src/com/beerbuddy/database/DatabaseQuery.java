package com.beerbuddy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Christian Witchger
 *
 */
public class DatabaseQuery {

	public static final String INSERT = "INSERT INTO ";

	public static final String NAME = "name";

	public static final String EMAIL = "email";

	public static final String USERNAME = "username";

	public static final String PASSWORD = "password";

	/**
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return id
	 */
	public static String loginWithUsername(String username, String password) {
		String buddyid = null;

		String dbPassword = "";
		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = null;
			statement = connection.prepareStatement("SELECT * FROM buddy WHERE username = ?");
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				dbPassword = rs.getString("password");
				if (password.equals(dbPassword)) {
					buddyid = rs.getString("id_buddy");
				}
			}

			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.print(e);
		}

		return buddyid;
	}

	private String[] args;

	private String databaseName;

	public DatabaseQuery(String[] a, String db) {
		this.args = a;
		this.databaseName = db;
	}

	public String[] getArgs() {
		return this.args;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

}
