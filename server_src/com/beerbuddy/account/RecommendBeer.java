package com.beerbuddy.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beerbuddy.database.DatabaseInsert;

/**
 * @author Christian
 *
 */
@WebServlet("/recommendBeer")
public class RecommendBeer extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This post request takes in two parameters and adds a beer for a user to
	 * try. The response contains a JSON object with a valid flag.
	 *
	 * Will accept two parameters and will handle appropriate cases for other
	 * input.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {

		// get request parameters for userID and password
		String beerid = request.getParameter("beerid");
		String buddyid = request.getParameter("buddyid");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { beerid, buddyid };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		if (valid) {
			String[] args = { "beer_id_beer", beerid, "buddy_id_buddy", buddyid, };

			DatabaseInsert insert = new DatabaseInsert(args, "RecommendedBeerList");
			valid = insert.insert();

			if (valid) {
				valid = true;
				responseMessage = ResponseMessage.VALID_REQUEST;
			} else {
				responseMessage = ResponseMessage.INVALID_INPUT;
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
