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

public class TestGetBeerList extends Mockito {
	@Test
	public void TestGetBeerList_BlankId() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("listId")).thenReturn("");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerList().doGet(request, response);

		verify(request, atLeast(1)).getParameter("listId");

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
	public void TestGetBeerList_validId() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("listId")).thenReturn("1");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerList().doGet(request, response);

		verify(request, atLeast(1)).getParameter("listId");

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

	@Test
	public void TestGetBeerList_wrongId() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("listId")).thenReturn("100000");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerList().doGet(request, response);

		verify(request, atLeast(1)).getParameter("listId");

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