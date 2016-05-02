package com.beerbuddy.account;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beerbuddy.database.DatabaseInsert;

/**
 * This servlet is used to add a buddy. It takes in two buddy ids. the first
 * buddy id is the person issuing the buddy request and the second id is for the
 * buddy to add.
 *
 * Adding a buddy is a post request and is the only operation in the servlet.
 *
 * @author Christian Witchger
 *
 */
@WebServlet("/addABuddy")
public class AddABuddy extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The post method takes in two parameters in the request and attempts to
	 * add the buddy relationship in the database. The response contains a JSON
	 * object with a valid flag.
	 *
	 * Will accept any two strings and will handle appropriate cases for other
	 * input.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {

		// get request parameters for userID and password
		String buddy1 = request.getParameter("buddy1");
		String buddy2 = request.getParameter("buddy2");

		String[] args = { "id_Buddy1", buddy1, "id_Buddy2", buddy2 };

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { buddy1, buddy2 };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		if (valid) {
			DatabaseInsert insert = new DatabaseInsert(args, "buddylist");
			valid = insert.insert();

			if (valid) {
				responseMessage = ResponseMessage.ADDED_FRIEND;
			} else {
				responseMessage = ResponseMessage.ERROR_ADDING_FRIEND;
			}
		}

		JSONObject innerObject = new JSONObject();

		innerObject.put("valid", valid);
		innerObject.put("response", responseMessage);

		innerObject.write(response.getWriter());

	}

}
