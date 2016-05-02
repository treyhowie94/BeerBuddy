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
@WebServlet("/findNewBeer")
public class TryANewBeer extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This get request takes in one parameter and returns a new beer for a user
	 * to try. The response contains a JSON object with a valid flag.
	 *
	 * Will accept one parameter and will handle appropriate cases for other
	 * input.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String buddyid = request.getParameter("buddyid");

		// Construct objects
		String responseMessage = ResponseMessage.DEFAULT_RESPONSE;
		String[] parameters = { buddyid };

		VerifyValues verification = new VerifyValues(parameters);
		boolean valid = verification.isValid();

		JSONArray innerArray = new JSONArray();
		if (valid) {
			try {
				Connection connection = DatabaseConnection.getConnection();

				PreparedStatement statement = null;
				statement = connection
						.prepareStatement("SELECT distinct B.id_beer, B.name, B.icon, B.abv, B.ibu, B.barArea, "
								+ "GBT.value as generalType, SBT.value as specificType, "
								+ "Br.name as breweryName, Br.icon as breweryIcon, L.city, L.state "
								+ "FROM beer B, brewry Br, GeneralBeerType GBT, SpecificBeerType SBT, location L, BeerList BL "
								+ "WHERE Bl.id_beer = B.id_beer and "
								+ "B.id_Brewry = Br.id_Brewry and "
								+ "B.GeneralBeerType_id_GeneralBeerType = GBT.id_GeneralBeerType and "
								+ "B.SpecificBeerType_id_SpecificBeerType = SBT.id_SpecificBeerType and "
								+ "Br.id_location = L.id_location and "
								+ "GBT.id_GeneralBeerType = ( "
								+ "SELECT distinct GBT.id_GeneralBeerType as generalType "
								+ "FROM rating rat, beer B, brewry Br, GeneralBeerType GBT, SpecificBeerType SBT, location L, BeerList BL "
								+ "WHERE rat.id_beer = B.id_beer and  " + "Bl.id_beer = B.id_beer and "
								+ "B.id_Brewry = Br.id_Brewry and "
								+ "B.GeneralBeerType_id_GeneralBeerType = GBT.id_GeneralBeerType and "
								+ "B.SpecificBeerType_id_SpecificBeerType = SBT.id_SpecificBeerType and "
								+ "Br.id_location = L.id_location and " + "rat.id_buddy = " + buddyid
								+ " ORDER BY rand() ASC limit 1 ) ORDER BY rand() limit 1");

				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					JSONObject beerInfo = new JSONObject();

					beerInfo.put("id_beer", rs.getString("id_Beer"));
					beerInfo.put("name", rs.getString("name"));
					beerInfo.put("icon", rs.getString("icon"));
					beerInfo.put("abv", rs.getString("abv"));
					beerInfo.put("ibu", rs.getString("ibu"));
					beerInfo.put("generalType", rs.getString("generalType"));
					beerInfo.put("specificType", rs.getString("specificType"));
					beerInfo.put("breweryName", rs.getString("breweryName"));
					beerInfo.put("breweryIcon", rs.getString("breweryIcon"));
					beerInfo.put("city", rs.getString("city"));
					beerInfo.put("state", rs.getString("state"));

					innerArray.put(beerInfo);
				}

				statement.close();
				connection.close();

				valid = true;
				responseMessage = ResponseMessage.LIST_GENERATED;

			} catch (Exception e) {
				valid = false;
				System.out.print(e);
			}
		} else {
			responseMessage = ResponseMessage.INVALID_INPUT;
		}

		JSONObject innerObject = new JSONObject();

		innerObject.put("list", innerArray);
		innerObject.put("valid", valid);
		innerObject.put("response", responseMessage);

		innerObject.write(response.getWriter());

	}
}
