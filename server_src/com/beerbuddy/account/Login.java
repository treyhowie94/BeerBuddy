package com.beerbuddy.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beerbuddy.database.DatabaseQuery;

/**
 * @author Christian Witchger
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {

		// Get parameters from user
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String buddyid = null;
		String[] parameters = { username, password };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

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
