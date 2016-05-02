package com.beerbuddy.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beerbuddy.database.DatabaseConnection;

/**
 * @author Christian Witchger
 */
@WebServlet("/buddyInfo")
public class BuddyInfo extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get parameters from user
		String buddyid = request.getParameter("buddyid");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { buddyid };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();
		String username = "";
		String name = "";
		@SuppressWarnings("unused")
		String beerListNumber = "";

		if (valid) {
			try {
				Connection connection = DatabaseConnection.getConnection();

				PreparedStatement statement = null;
				statement = connection
						.prepareStatement("SELECT buddy.name, buddy.username, buddy.beerListNumber FROM buddy WHERE id_buddy = "
								+ buddyid);

				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					username = rs.getString("username");
					name = rs.getString("name");
					beerListNumber = rs.getString("beerListNumber");

				}

				statement.close();
				connection.close();
				responseMessage = ResponseMessage.VALID_REQUEST;
			} catch (Exception e) {
				valid = false;
				System.out.print(e);
			}
		} else {
			responseMessage = ResponseMessage.INVALID_INPUT;
		}

		JSONObject innerObject = new JSONObject();

		innerObject.put("username", username);
		innerObject.put("name", name);
		innerObject.put("beerListNumber", buddyid);
		innerObject.put("valid", valid);
		// innerObject.put("id", buddyid);
		innerObject.put("response", responseMessage);

		innerObject.write(response.getWriter());

	}
}
