package com.beerbuddy.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.mockito.Mockito;

import com.beerbuddy.database.DatabaseConnection;

public class TestAddABuddy extends Mockito {

	private boolean deleteAddABuddy(String username) {
		boolean ret = true;
		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = null;
			statement = connection.prepareStatement("delete FROM buddylist WHERE id_Buddy1 = 56 and id_Buddy2 = 57");

			statement.executeUpdate();

			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.print(e);
			ret = false;
		}
		return ret;
	}

	@Test
	public void TestAddABuddy_AlreadyABuddy() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("buddy1")).thenReturn("56");
		when(request.getParameter("buddy2")).thenReturn("57");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new AddABuddy().doPost(request, response);

		verify(request, atLeast(1)).getParameter("buddy1");

		writer.flush();

		BufferedReader br = new BufferedReader(new FileReader("TestFile.txt"));
		String output = br.readLine();

		JSONObject responseValue = (JSONObject) new JSONTokener(output).nextValue();
		String validText = responseValue.get("valid").toString();

		boolean valid = Boolean.parseBoolean(validText);
		String responseText = responseValue.get("response").toString();

		Assert.assertTrue(responseText, valid);
		br.close();

		// Check that buddy is already there

		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		when(request.getParameter("buddy1")).thenReturn("56");
		when(request.getParameter("buddy2")).thenReturn("57");

		writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new AddABuddy().doPost(request, response);

		verify(request, atLeast(1)).getParameter("buddy1");

		writer.flush();

		br = new BufferedReader(new FileReader("TestFile.txt"));
		output = br.readLine();

		responseValue = (JSONObject) new JSONTokener(output).nextValue();
		validText = responseValue.get("valid").toString();

		valid = Boolean.parseBoolean(validText);
		responseText = responseValue.get("response").toString();

		Assert.assertFalse(responseText, valid);

		Assert.assertTrue(deleteAddABuddy("57"));

		br.close();
	}

	@Test
	public void TestAddABuddy_invalidName() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("buddy1")).thenReturn("56");
		when(request.getParameter("buddy2")).thenReturn("");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new AddABuddy().doPost(request, response);

		verify(request, atLeast(1)).getParameter("buddy1");

		writer.flush();

		BufferedReader br = new BufferedReader(new FileReader("TestFile.txt"));
		String output = br.readLine();

		JSONObject responseValue = (JSONObject) new JSONTokener(output).nextValue();
		String validText = responseValue.get("valid").toString();

		boolean valid = Boolean.parseBoolean(validText);
		String responseText = responseValue.get("response").toString();

		Assert.assertFalse(responseText, valid);
		br.close();
	}

	@Test
	public void TestAddABuddy_validCase() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("buddy1")).thenReturn("56");
		when(request.getParameter("buddy2")).thenReturn("57");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new AddABuddy().doPost(request, response);

		verify(request, atLeast(1)).getParameter("buddy1");

		writer.flush();

		BufferedReader br = new BufferedReader(new FileReader("TestFile.txt"));
		String output = br.readLine();

		JSONObject responseValue = (JSONObject) new JSONTokener(output).nextValue();
		String validText = responseValue.get("valid").toString();

		boolean valid = Boolean.parseBoolean(validText);
		String responseText = responseValue.get("response").toString();

		Assert.assertTrue(responseText, valid);
		br.close();

		Assert.assertTrue(deleteAddABuddy("57"));

		br.close();
	}

}