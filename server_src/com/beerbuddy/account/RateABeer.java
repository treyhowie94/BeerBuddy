/**
 *
 */
package com.beerbuddy.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beerbuddy.database.DatabaseConnection;
import com.beerbuddy.database.DatabaseInsert;

/**
 * @author Christian
 *
 */
@WebServlet("/rateABeer")
public class RateABeer extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The post method takes in three parameters in the request and adds a user
	 * rating to a beer. The response contains a JSON object with a valid flag.
	 *
	 * Will accept three strings and will handle appropriate cases for other
	 * input.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {

		// get request parameters for userID and password
		String beerid = request.getParameter("beerid");
		String userid = request.getParameter("userid");
		String rating = request.getParameter("value");

		int ratingV = Integer.valueOf(rating);
		if (ratingV < 1) {
			ratingV = 1;
		} else if (ratingV > 5) {
			ratingV = 5;
		}
		rating = ratingV + "";

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { beerid, userid, rating };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		if (valid) {
			String[] args = { "id_beer", beerid, "id_buddy", userid, "value", rating };

			DatabaseInsert insert = new DatabaseInsert(args, "rating");
			valid = insert.insert();

			if (valid) {
				valid = true;
				responseMessage = ResponseMessage.RATING_CREATED;
			} else {
				try {
					Connection connection = DatabaseConnection.getConnection();

					PreparedStatement statement = null;
					statement = connection
							.prepareStatement("UPDATE rating SET value = ? WHERE id_buddy = ?, id_beer = ?;");
					statement.setString(1, rating);
					statement.setString(2, userid);
					statement.setString(3, beerid);

					statement.executeUpdate();

					statement.close();
					connection.close();

					valid = true;
					responseMessage = ResponseMessage.RATING_CREATED;
				} catch (Exception e) {
					System.out.print(e);
					valid = false;
					responseMessage = ResponseMessage.ERROR_ADDING_RATING;
				}
			}
		} else {
			responseMessage = ResponseMessage.INVALID_INPUT;
		}

		JSONObject outerObject = new JSONObject();
		JSONArray outerArray = new JSONArray();
		JSONObject innerObject = new JSONObject();

		innerObject.put("valid", valid);
		innerObject.put("response", responseMessage);
		outerArray.put(innerObject);
		outerObject.put("serverResponse", outerArray);

		outerObject.write(response.getWriter());

	}

}
