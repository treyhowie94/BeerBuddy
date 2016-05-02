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
@WebServlet("/favoriteBeer")
public class FavoriteBeer extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {

		// get request parameters for userID and password
		String beerid = request.getParameter("beerid");
		String listnumber = request.getParameter("listNumber");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { beerid, listnumber };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		if (valid) {
			String[] args = { "id_beer", beerid, "listNumber", listnumber, };

			DatabaseInsert insert = new DatabaseInsert(args, "beerList");
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
