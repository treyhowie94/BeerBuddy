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

public class TestGetBeerByStyle extends Mockito {

	@Test
	public void TestGetBeerByStyle_BlankName() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("style")).thenReturn("");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerByStyle().doGet(request, response);

		verify(request, atLeast(1)).getParameter("style");

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
	public void TestGetBeerByStyle_validName() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("style")).thenReturn("Pilsners and Pale Lagers");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerByStyle().doGet(request, response);

		verify(request, atLeast(1)).getParameter("style");

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
	public void TestGetBeerByStyle_WithWrongCharacters() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("style")).thenReturn("aasdf\nasd@fas$df");

		PrintWriter writer = new PrintWriter("TestFile.txt");
		when(response.getWriter()).thenReturn(writer);

		new BeerByStyle().doGet(request, response);

		verify(request, atLeast(1)).getParameter("style");

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
