package beer_garden_rscs;

import static java.lang.System.err;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This class is used to connect to Tap Hunter on the Raleigh Beer Garden site, get the Xml page after all of the 
 * JavaScript has executed and then passes the String representation to JSoup so that a Html Document can be 
 * created and used by BeerInformationScavenger.
 * 
 * @author treyhowie
 *
 */
public class WebConnection {

	// URL for the Tap Hunter page on the Raleigh Beer Garden site
	private static final String TAP_HUNTER = "http://www.theraleighbeergarden.com/pages/taphunter";
	// HtmlUnit WebClient used to get the web page from RBG
	private static final WebClient CLIENT = new WebClient(BrowserVersion.CHROME);
	
	// JSoup document used for data scraping in BeerInformationScavenger
	public static Document DOC = null;
	
	// HtmlUnit HtmlPage that is recieved from the Tap Hunter site on RBG
	private static HtmlPage TH_AS_XML = null;
	
	/**
	 * Creates the WebClient used to grab the html/xml page and then passes it to JSoup to create a Document
	 * useable by BeerInformationScavenger
	 */
	public static void createDoc() {
		createWebClient();	// create CLIENT and set the proper setting for web browsing
		
		String docAsXml = connectToBeerGarden();	// get the Xml string related to the received HtmlPage by CLIENT
		// parse the JSoup document from the Xml String recieved by CLIENT from the Tap Hunter site
		DOC = Jsoup.parse(docAsXml, "", Parser.xmlParser());
	}
	
	/**
	 * This subroutine is used to create a HtmlUnit WebClient and specify all of the options used for web browsing.
	 * This allows CLIENT to get the Tap Hunter page after it has properly loaded in order to create a useable
	 * JSoup Document
	 */
	private static void createWebClient() {
		// turn off console process logging because its annoying
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); 

		// set brwosing options
		CLIENT.getOptions().setThrowExceptionOnScriptError(false);
		CLIENT.getOptions().setThrowExceptionOnFailingStatusCode(false);
		CLIENT.getOptions().setCssEnabled(false);
		CLIENT.getOptions().setJavaScriptEnabled(true);
		CLIENT.waitForBackgroundJavaScriptStartingBefore(30 * 1000);
	}
	
	/**
	 * This subroutine gets the html page for Tap Hunter from the HtmlUnit CLIENT and then, if successful,
	 * returns a string representation of the Xml page. This allows JSoup to have the finalized String version
	 * of the Xml after all the javascript on the page has executed.
	 * 
	 * @return String - the Xml page as a String
	 */
	@SuppressWarnings("finally")
	private static String connectToBeerGarden() {
		try {
			// get the html page after all of the javascript has executed
			TH_AS_XML = ((HtmlPage) CLIENT.getPage(TAP_HUNTER));
	    } catch (FailingHttpStatusCodeException | IOException e1) {
			err.println("unable to get");
		} finally {
			// see if the process failed
			if (TH_AS_XML == null) {
				return null;
			}
			else {	
				// return string representation of the Xml page
				return TH_AS_XML.asXml();
			}
		}
	}
	
}
