package tools;

import final_data.GlobalConstants;

/**
 * This static class defines some methods used to modify the beer information found on the TapHunter site.
 * This is used to format the data so that it can be properly used and then later inserted into the database. 
 * 
 * @author treyhowie
 *
 */
public class BeerInformationFormatter {

	/**
	 * This method is used to split a beer name and the number associated with it, on the TaoHunter site,
	 * so that they can be used to order the beers and have their name in the proper format for insertion into the db.
	 * 
	 * @param text - String that contains the beer name and its respective number on the menu
	 * @return String[] - an array of size 2 where the menu number is at position 1 and the beer name is at position 2.
	 */
	public static String[] splitNameAndNumber(String text) {
		// initialize return object
		String[] rtn = new String[] {"", ""};
		
		// make sure the text contains a period because the period is the split point
		if (text.contains(".")) {
charLoop:   for (int i=0; i<text.length(); i++) {
				// find the location of the period
				if (text.charAt(i) == '.') {
					// get the substring containing the menu number
					rtn[0] = text.substring(0, i);
					// get the substring containing the beer name
					rtn[1] = text.substring(i+2, text.length());
					// end loop there
					break charLoop;
				}
			}
		}
		// no number
		else {
			// make the number unavailable
			rtn[0] = GlobalConstants.INFORMATION_UNAVAILABLE;
			// the beer name is just the whole text string
			rtn[1] = text;
		}
		
		return rtn;
	}
	
	/**
	 * This method formats the abv value for a specific beer so that the percent sign is removed. This
	 * is used so that the abv value can be inserted as a double value into the database.
	 * 
	 * @param abv - String that is the abv value
	 * @return String - the abv value without the percent sign
	 */
	public static String abvFormat(String abv) {
		String rtn = "";
		
		// loop to find percent sign index
		for (int i=0; i<abv.length(); i++) {
			if (abv.charAt(i) == '%') {
				// set the return value to the substring without the percent sign
				rtn = abv.substring(0, i);
			}
		}
		
		return rtn;
	}
	
	/**
	 * This method formats the ibu value for a specific beer so that the percent sign is removed. This
	 * is used so that the ibu value can be inserted as a double value into the database.
	 * 
	 * @param ibu - String that is the ibu value
	 * @return String - the ibu value without the percent sign
	 */
	public static String ibuFormat(String ibu) {
		StringBuilder sb = new StringBuilder();
		
		// loop through the ibu value and add all characters that are part of the number to the StringBuilder
		for (int i=0; i<ibu.length(); i++) {
			// add char if it's a digit
			if (Character.isDigit(ibu.charAt(i))) {
				sb.append(ibu.charAt(i));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * This method removes the dollar sign from a price value so that it can be inserted into the database as an integer
	 * value.
	 * 
	 * @param price - String that denotes the price
	 * @return String - price value without the dollar sign
	 */
	public static String priceFormat(String price) {
		String rtn = "";
		
		// loop through the string to find the index of the dollar sign
		for (int i=0; i<price.length(); i++) {
			// find the index of the dollar sign and take the substring without it
			if (price.charAt(i) == '$') {
				rtn = price.substring(i+1, price.length());
			}
		}
		
		return rtn;
	}
	
	/**
	 * This method splits a location up so that it can be easily added to the location table.
	 * 
	 * @param text - String that denotes the location
	 * @return String[] - an array that has City, State, Zip split into three indices
	 */
	public static String[] locationFormat(String text) {
		// init return object
		String[] cityState = new String[] {"", "", GlobalConstants.INFORMATION_UNAVAILABLE};
		
		// make sure the text contains a comma because that is the split point
		if (text.contains(",")) {
			for (int i=0; i<text.length(); i++) {
				// find the comma
				if (text.charAt(i) == ',') {
					// take substring up to the comma, City value
					cityState[0] = text.substring(0, i);
					// take substring after comma, State value
					cityState[1] = text.substring(i+2, text.length());
				}
			}
		}
		// info not available so create invalid values for the array
		else {
			cityState[0] = GlobalConstants.INFORMATION_UNAVAILABLE;
			cityState[1] = GlobalConstants.INFORMATION_UNAVAILABLE;
		}
		
		return cityState;
	}
	
}
