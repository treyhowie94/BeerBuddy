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

public class TestCreateAccount extends Mockito {

	private boolean deleteRandomUser(String username) {
		boolean ret = true;
		try {
			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement statement = null;
			statement = connection.prepareStatement("delete FROM buddy WHERE username = ?");
			statement.setString(1, username);

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
	public void TestCreateAccount_AlreadyAUser() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("name")).thenReturn("aName");
		when(request.getParameter("username")).thenReturn("somethingRandom123412341234");
		when(request.getParameter("password")).thenReturn("somethingRandom123412341234");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new CreateAccount().doPost(request, response);

		verify(request, atLeast(1)).getParameter("username");

		writer.flush();

		BufferedReader br = new BufferedReader(new FileReader("TestFile.txt"));
		String output = br.readLine();

		JSONObject responseValue = (JSONObject) new JSONTokener(output).nextValue();
		String validText = responseValue.get("valid").toString();

		boolean valid = Boolean.parseBoolean(validText);
		String responseText = responseValue.get("response").toString();

		Assert.assertTrue(responseText, valid);
		br.close();

		// Check that account is already being used

		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		when(request.getParameter("name")).thenReturn("aName");
		when(request.getParameter("username")).thenReturn("somethingRandom123412341234");
		when(request.getParameter("password")).thenReturn("somethingRandom123412341234");

		writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new CreateAccount().doPost(request, response);

		verify(request, atLeast(1)).getParameter("username");

		writer.flush();

		br = new BufferedReader(new FileReader("TestFile.txt"));
		output = br.readLine();

		responseValue = (JSONObject) new JSONTokener(output).nextValue();
		validText = responseValue.get("valid").toString();

		valid = Boolean.parseBoolean(validText);
		responseText = responseValue.get("response").toString();

		Assert.assertFalse(responseText, valid);

		Assert.assertTrue(deleteRandomUser("somethingRandom123412341234"));

		br.close();
	}

	@Test
	public void TestCreateAccount_invalidName() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("name")).thenReturn("");
		when(request.getParameter("username")).thenReturn("somethingRandom123412341234");
		when(request.getParameter("password")).thenReturn("somethingRandom123412341234");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new CreateAccount().doPost(request, response);

		verify(request, atLeast(1)).getParameter("username");

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
	public void TestCreateAccount_validCase() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("name")).thenReturn("aName");
		when(request.getParameter("username")).thenReturn("somethingRandom123412341234");
		when(request.getParameter("password")).thenReturn("something");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new CreateAccount().doPost(request, response);

		verify(request, atLeast(1)).getParameter("username");

		writer.flush();

		BufferedReader br = new BufferedReader(new FileReader("TestFile.txt"));
		String output = br.readLine();

		JSONObject responseValue = (JSONObject) new JSONTokener(output).nextValue();
		String validText = responseValue.get("valid").toString();

		boolean valid = Boolean.parseBoolean(validText);
		String responseText = responseValue.get("response").toString();

		Assert.assertTrue(responseText, valid);

		Assert.assertTrue(deleteRandomUser("somethingRandom123412341234"));

		br.close();
	}

}