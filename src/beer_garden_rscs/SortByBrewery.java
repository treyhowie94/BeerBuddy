package beer_garden_rscs;

import java.util.ArrayList; 

import final_data.GlobalConstants;
import static java.lang.System.out;

/**
 * This class is used to sort each beer into a list who's index allows for easy reference to a brewery's
 * name, location and logo. This allows for the creation of brewery tables and lists in the Beer Buddy database.
 * 
 * @author treyhowie
 *
 */
public class SortByBrewery {

	// list of lists of beers where each list at an index is correlated with the beers' brewery's index in the breweries
	// index
	public static ArrayList<ArrayList<String>> beersSortedByBreweries = new ArrayList<ArrayList<String>>(0);
	// list that stores brewery names as reference for indexing for the beersSortedByBreweries list
	public static ArrayList<String> breweries = new ArrayList<String>(0);
	// locations related to each brewery, indexes match to each brewery
	public static ArrayList<String> locations = new ArrayList<String>(0); 
	// icons related to each brewery, indexes match to each brewery
	public static ArrayList<String> iconUrls  = new ArrayList<String>(0);
	
	/**
	 * This subroutine sorts all the beers found on the Tap Hunter site, by 
	 * BeerInformationScavenger.gatherBeerInformation() by their respective breweries. This is used for adding 
	 * new brewery tables to the Beer Buddy database in the DatabasePopulation class.
	 */
	@SuppressWarnings("serial")
	public static void sortBeersByBrewery() {
		// make sure there are beers found and to sort by brewery
		if (BeerInformationScavenger.beers.isEmpty()) {
			return;
		}
		
		// list of all beers that don't have a brewery associated with it on the Tap Hunter site
		ArrayList<String> noBrewery = new ArrayList<String>(0);
		
		// loop through all beers found in BeerInformation Scavenger
		for (ArrayList<String> beer: BeerInformationScavenger.beers) {
			// check to see if brewery doesn't exist on the site
			if (beer.get(5).equals(GlobalConstants.INFORMATION_UNAVAILABLE)) {
				// add the beer to no brewery for further sorting later
				noBrewery.add(beer.get(0));
			}
			// check to see if the brewery is already in the list
			else if (!breweries.contains(beer.get(5))) {
				// update each list with the new info
				breweries.add(beer.get(5));
				locations.add(beer.get(6));	
				beersSortedByBreweries.add(new ArrayList<String>(0) {{ add(beer.get(0)); }});
				iconUrls.add(beer.get(12));
			}
			// check to see if the brewery is already in the list, if so add the beer to its related brewery index
			// in beersSortedByBreweries
			else if (breweries.contains(beer.get(5))) {
				// index reference for the brewery just found
				int indexToAddAt = breweries.indexOf(beer.get(5));
				// add beer to the related position in brewery
				beersSortedByBreweries.get(indexToAddAt).add(beer.get(0));
			}
		}
	}
	
}
