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

import org.json.JSONArray;
import org.json.JSONObject;

import com.beerbuddy.database.DatabaseConnection;

/**
 * @author Christian Witchger
 *
 */
@WebServlet("/findBuddy")
public class FindBuddy extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String buddy = request.getParameter("buddy");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { buddy };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		JSONArray innerArray = new JSONArray();

		if (valid) {

			try {
				Connection connection = DatabaseConnection.getConnection();

				PreparedStatement statement = null;
				statement = connection
						.prepareStatement("SELECT buddy.username, buddy.id_Buddy, buddy.name FROM buddy WHERE buddy.name like '%"
				                + buddy + "%' OR buddy.username like '%" + buddy + "%';");

				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					JSONObject beerInfo = new JSONObject();

					beerInfo.put("username", rs.getString("username"));
					beerInfo.put("name", rs.getString("name"));
					beerInfo.put("buddyid", rs.getString("id_Buddy"));

					innerArray.put(beerInfo);
				}

				statement.close();
				connection.close();
				responseMessage = ResponseMessage.LIST_GENERATED;
			} catch (Exception e) {
				valid = false;
				System.out.print(e);
			}
		} else {
			responseMessage = ResponseMessage.INVALID_INPUT;
		}

		JSONObject innerObject = new JSONObject();

		innerObject.put("valid", valid);
		innerObject.put("response", responseMessage);

		innerObject.put("list", innerArray);

		innerObject.write(response.getWriter());

	}

}