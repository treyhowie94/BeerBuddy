package tools;

import final_data.GlobalConstants;

public class BeerInformationFormatter {

	public static String[] splitNameAndNumber(String text) {
		String[] rtn = new String[] {"", ""};
		
		if (text.contains(".")) {
charLoop:   for (int i=0; i<text.length(); i++) {
				if (text.charAt(i) == '.') {
					rtn[0] = text.substring(0, i);
					rtn[1] = text.substring(i+2, text.length());
					break charLoop;
				}
			}
		}
		else {
			rtn[0] = GlobalConstants.INFORMATION_UNAVAILABLE;
			rtn[1] = text;
		}
		
		return rtn;
	}
	
	public static String abvFormat(String abv) {
		String rtn = "";
		
		for (int i=0; i<abv.length(); i++) {
			if (abv.charAt(i) == '%') {
				rtn = abv.substring(0, i);
			}
		}
		
		return rtn;
	}
	
	public static String ibuFormat(String ibu) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<ibu.length(); i++) {
			if (Character.isDigit(ibu.charAt(i))) {
				sb.append(ibu.charAt(i));
			}
		}
		
		return sb.toString();
	}
	
	public static String priceFormat(String price) {
		String rtn = "";
		
		for (int i=0; i<price.length(); i++) {
			if (price.charAt(i) == '$') {
				rtn = price.substring(i+1, price.length());
			}
		}
		
		return rtn;
	}
	
	public static String[] locationFormat(String text) {
		String[] cityState = new String[] {"", "", GlobalConstants.INFORMATION_UNAVAILABLE};
		
		if (text.contains(",")) {
			for (int i=0; i<text.length(); i++) {
				if (text.charAt(i) == ',') {
					cityState[0] = text.substring(0, i);
					cityState[1] = text.substring(i+2, text.length());
				}
			}
		}
		else {
			cityState[0] = GlobalConstants.INFORMATION_UNAVAILABLE;
			cityState[1] = GlobalConstants.INFORMATION_UNAVAILABLE;
		}
		
		return cityState;
	}
	
}
