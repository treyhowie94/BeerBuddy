package beer_garden_rscs;

import java.util.ArrayList; 

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import final_data.GlobalConstants;
import tools.BeerInformationFormatter;
import static java.lang.System.out;

/**
 * This Class is used to grab all important and related information related to the beers
 * found on the Tap Hunter page on the Raleigh Beer Garden website. This is the main web scraping algorithm
 * and is an integral part in updating the information in the Beer Buddy database.
 * 
 * @author treyhowie
 *
 */
public class BeerInformationScavenger {
	// List of all beers and their respective information to be found on the Tap Hunter website
	public static ArrayList<ArrayList<String>> beers = new ArrayList<ArrayList<String>>(0);
	
	/**
	 * This subroutine creates a JSoup document from the Tap Hunter URL and then carries out all of the calls 
	 * to the information getting subroutines defined below.
	 */
	public static void gatherBeerInformation() {
		try {
			// creates the JSoup document from the WebConnection class
			WebConnection.createDoc();
		} catch (InvalidValue e) {
			// exit with error ~~~> not possible to connect to the information needed to run
			System.exit(-1);
		}	
		
		// make sure the JSoup document of Tap Hunter was created
		if (!(WebConnection.DOC == null)) {
			out.println("analyzing page...");
			// grab all beer info
			analyzePage(WebConnection.DOC);
		}
	}
	
	/**
	 * This subroutine searches through the entire Tap Hunter JSoup document and grabs all web menus available.
	 * It then sifts through all the menus and gets each tap div (beer) and grabs the information from the div
	 * and adds it to the beers ArrayList.
	 * 
	 * @param doc - the Tap Hunter JSoup document to analyze
	 */
	private static void analyzePage(Document doc) {	
		// get all the web menus found on TapHunter
		ArrayList<Element> webMenus = grabWebMenus(doc);
		
		// loop through each web menu found
		for (int i=0; i<webMenus.size(); i++) {
			// get all taps in the current web menu, a tap has the class name "taphunter-row"
			Elements taps = webMenus.get(i).getElementsByClass("taphunter-row");
			
			// use the web menu index to determine the string name of the bar area at the Raleigh Beer Garden
			String barArea = determineBarArea(i);
			String beerTypeStr = "";	// the current style in the web menu
			
			// loop through all taps found in the current bar area
			for (Element currTap: taps) {	
				// there are "taphunter-row"'s that define the style of beer for the folowing "taphunter-row"'s
				if (currTap.hasClass("category-header")) {
					// set the style of the folling beers
					beerTypeStr = currTap.getElementsByClass("heading").get(0).text();
				}
				// get the information of the beer if the current row is a beer
				else {
					ArrayList<String> tmp = grabBeerInfomation(barArea, beerTypeStr, currTap);
					beers.add(tmp);
				}
			}
		}
		
	}
	
	/**
	 * This subroutine determines the string value for bar area given a menu index. There are four menus on the Raleigh
	 * Beer Garden site and menuIndex specifies which bar area is being searched through.
	 * 
	 * @param menuIndex - determines the bar area being searched surrently
	 * @return String - the bar area name being searched through, which is used to classify beers by bar area
	 */
	private static String determineBarArea(int menuIndex) {
		assert !(menuIndex > 3);
		
		if (menuIndex == 0) {
			return "Bottom Bar";
		}
		else if (menuIndex == 1) {
			return "Top Bar";
		}
		else if (menuIndex == 2) {  
			return "Rooftop";
		}
		else {
			return "Bottles/Cans";
		}
	}
	
	/**
	 * This subroutine takes an element that defines the web information for the current tap (beer). It takes all
	 * of the divs inside of the passed JSoup Element and grabs the beer's specific information based on the
	 * element's names that describe where the information is. All the information is put in an ArrayList and is 
	 * returned by the routine.
	 * 
	 * Information ArrayList layout:
	 * 
	 * name | number | bar | beerType | specificType | brewery | brewLocation | abv | ibu | pintPrice | 
	 * ten_oz | five_oz | logoLocation
	 * 
	 * 
	 * @param barArea - the String name of the bar area that the beer is located at
	 * @param beerType - the String name of the general type of beer the beer is
	 * @param tap - the JSoup Element that describes the area in the web page that the beer information is located
	 * @return ArrayList<String> - List of all the current beer's related information
	 */
	private static ArrayList<String> grabBeerInfomation(String barArea, String beerType, Element tap) {
		assert !(barArea == null);
		assert !(beerType == null);
		assert !(tap == null);
		
		ArrayList<String> rtn = new ArrayList<String>(0);
		
		// String containers for the information to be found from the tap Element
		// figured it would be more straight forward to read and understand with a bunch of strings rather than an array
		String logoLocation = GlobalConstants.INFORMATION_UNAVAILABLE;
		String number	    = GlobalConstants.INFORMATION_UNAVAILABLE;
		String name			= GlobalConstants.INFORMATION_UNAVAILABLE;
		String specificType = GlobalConstants.INFORMATION_UNAVAILABLE;
		String abv          = GlobalConstants.INFORMATION_UNAVAILABLE;
		String ibu			= GlobalConstants.INFORMATION_UNAVAILABLE;
		String brewery		= GlobalConstants.INFORMATION_UNAVAILABLE;
		String brewLocation = GlobalConstants.INFORMATION_UNAVAILABLE;
		String pintPrice    = GlobalConstants.INFORMATION_UNAVAILABLE;
		String ten_oz       = GlobalConstants.INFORMATION_UNAVAILABLE;
		String five_oz      = GlobalConstants.INFORMATION_UNAVAILABLE;
		
		// Search through each div found inside the tap Element
		for (Element info: tap.getElementsByTag("div")) {
			// grab the class that contains the brewery logo image url 
			if (info.hasClass("brewerylogo")) {
				// make sure that there is, in fact, a URL available for the logo
				// otherwise there will be an array out of bounds exception
				if (!info.getElementsByClass("img-responsive").isEmpty()) {	
					// grab the brewery logo src URL and store in logoLocation
					logoLocation = info.getElementsByClass("img-responsive").get(0).attr("src");
				}
			}
			// grab the class that contains the current beer's name and number on the menu
			else if (info.hasClass("beer-header")) {
				// make sure that there is, in fact, text available for the beer's name
				// otherwise there will be an array out of bounds exception
				if (!info.getElementsByClass("beer-name").isEmpty()) {
					// use split name and number method from the BeerInformationFormatter class.
					// this allows for the storage of name and number in two different variables
					String[] tmp = BeerInformationFormatter.splitNameAndNumber(
							info.getElementsByClass("beer-name").get(0).text());
					
					// place the information in the corresponding variables
					number = tmp[0];
					name   = tmp[1];
				}
			}
			// grab the class that contains the current beer's specific style of beer, abv, and ibu on the menu
			else if (info.hasClass("beer-section")) {
				// again, make sure that the information exists to prevent Array out of bounds exception,
				// then place information in their respective variables
				if (!info.getElementsByClass("beer-style").isEmpty()) {	
					specificType = info.getElementsByClass("beer-style").get(0).text();
				}
				if (!info.getElementsByClass("beer-abv").isEmpty()) {
					abv = BeerInformationFormatter.abvFormat(
							info.getElementsByClass("beer-abv").get(0).text());
				}
				if (!info.getElementsByClass("beer-ibu").isEmpty()) {
					ibu = BeerInformationFormatter.ibuFormat(
							info.getElementsByClass("beer-ibu").get(0).text());
				}
			}
			// grab the class that contains the current beer's brewery name and the city/state location on the menu
			else if (info.hasClass("brewery-section")) {
				// again, make sure that the information exists to prevent Array out of bounds exception,
				// then place information in their respective variables
				if (!info.getElementsByClass("brewery-name").isEmpty()) {	
					brewery = info.getElementsByClass("brewery-name").get(0).text();
				}
				if (!info.getElementsByClass("brewery-location").isEmpty()) {
					brewLocation = info.getElementsByClass("brewery-location").get(0).text();
				}
			}
			// grab the class that contains the current beer's pricing information on the menu
			else if (info.hasClass("price-section")) {
				// get all the pricing information from the class
				Elements prices = info.getElementsByClass("sizeprice");
				
				// loop through all the price info found and add the info to its appropriate variable,
				// depending on the loop index
				for (int i=0; i<prices.size(); i++) {
					if (i == 0) {
						pintPrice = BeerInformationFormatter.priceFormat(prices.get(i).text());
					}
					else if (i == 1) {
						ten_oz = BeerInformationFormatter.priceFormat(prices.get(i).text());
					}
					else if (i == 2) {
						five_oz = BeerInformationFormatter.priceFormat(prices.get(i).text());
					}
				}
			}
		}
		
		// add all information to the ArrayList
		rtn.add(name);
		rtn.add(number);
		rtn.add(barArea);
		rtn.add(beerType);
		rtn.add(specificType);
		rtn.add(brewery);
		rtn.add(brewLocation);
		rtn.add(abv);
		rtn.add(ibu);
		rtn.add(pintPrice);
		rtn.add(ten_oz);
		rtn.add(five_oz);
		rtn.add(logoLocation);
		
		return rtn;
	}
	
	/**
	 * This subroutine grabs all of the web menu elements from Tap Hunter on the Raleigh Beer Garden web site
	 * 
	 * @param doc - JSoup document that defines the Tap Hunter page
	 * @return ArrayList<Element> - List of all the web menu Elements
	 */
	private static ArrayList<Element> grabWebMenus(Document doc) {
		assert !(doc== null);
		
		ArrayList<Element> webMenus = new ArrayList<Element>(0);
		
		webMenus.add(doc.getElementById("taphunter-webmenu"));
		webMenus.add(doc.getElementById("taphunterwebmenu2"));
		webMenus.add(doc.getElementById("taphunterwebmenu3"));
		webMenus.add(doc.getElementById("taphunterwebmenu4"));
		
		return webMenus;
	}
	
}
