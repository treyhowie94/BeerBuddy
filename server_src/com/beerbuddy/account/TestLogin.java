package com.beerbuddy.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.mockito.Mockito;

import com.beerbuddy.database.DatabaseInsert;
import com.beerbuddy.database.DatabaseQuery;

public class TestLogin extends Mockito {

	@Test
	public void TestLogin_invalidPassword() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("username")).thenReturn("me8317");
		when(request.getParameter("password")).thenReturn("secret8317_WRONG");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new Login().doPost(request, response);

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
	public void TestLogin_invalidUsername() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("username")).thenReturn("me8317_WRONG");
		when(request.getParameter("password")).thenReturn("secret8317");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new Login().doPost(request, response);

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
	public void TestLogin_noUsernameOrPassword() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("username")).thenReturn("");
		when(request.getParameter("password")).thenReturn("");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new Login().doPost(request, response);

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
	public void TestLogin_validCase() throws Exception {

		String[] args = { DatabaseQuery.NAME, "me8317", DatabaseQuery.USERNAME, "me8317", DatabaseQuery.PASSWORD,
		        "secret8317" };

		DatabaseInsert insert = new DatabaseInsert(args, "buddy");
		insert.insert();

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("username")).thenReturn("me8317");
		when(request.getParameter("password")).thenReturn("secret8317");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new Login().doPost(request, response);

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
	}
}