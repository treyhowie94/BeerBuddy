package com.beerbuddy.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beerbuddy.database.DatabaseInsert;
import com.beerbuddy.database.DatabaseQuery;

/**
 * @author Christian Witchger
 *
 */
@WebServlet("/createAccount")
public class CreateAccount extends HttpServlet {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Takes a post request with a first name, last name, username, and password
	 * and creates an account for the user. Error checking is done on the user
	 * side to ensure the fields are not empty.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {

		// get request parameters for userID and password
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String buddyid = null;
		String[] parameters = { name, username, password };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		if (valid) {
			// Create account
			String[] args = { DatabaseQuery.NAME, name, DatabaseQuery.USERNAME, username, DatabaseQuery.PASSWORD,
					password };

			DatabaseInsert insert = new DatabaseInsert(args, "buddy");
			valid = insert.insert();

			if (valid) {
				buddyid = DatabaseQuery.loginWithUsername(username, password);
				if (buddyid != null) {
					valid = true;
					responseMessage = ResponseMessage.VALID_ACCOUNT;
				} else {
					valid = false;
					responseMessage = ResponseMessage.WRONG_PASSWORD;
				}
			} else {
				responseMessage = ResponseMessage.ERROR_CREATING_ACCOUNT;
			}
		} else {
			responseMessage = ResponseMessage.INVALID_INPUT;
		}

		JSONObject innerObject = new JSONObject();

		innerObject.put("username", username);
		innerObject.put("valid", valid);
		innerObject.put("id", buddyid);
		innerObject.put("response", responseMessage);

		innerObject.write(response.getWriter());
	}
}
