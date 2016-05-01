/**
 * Your application code goes here
 * 
 */

package userclasses;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.util.Resources;

import generated.StateMachineBase;
 
/**
 *
 * @author Cody Barnes
 */
public class StateMachine extends StateMachineBase {
	ArrayList<String> idList = new ArrayList<String>();
	ArrayList<String> buddyIdList = new ArrayList<String>();
	ArrayList<String> favoriteList = new ArrayList<String>();
	ArrayList<String> recommendBeerIdList = new ArrayList<String>();
	ArrayList<String> recommendedList = new ArrayList<String>();
	ArrayList<String> newBeerList = new ArrayList<String>();
	
	String userId;
	String userName;
	ListModel myBuddyListModel;
	ListModel myFavoriteListModel;
	ListModel myRecommendedListModel;
	ListModel myNewBeerListModel;
	//This creates a list entry for a beer
	private Map<String, Object> createListEntry(String name, String abv, String ibu) {
		  Map<String, Object> entry = new HashMap<String, Object>(); //creates hashmap to store data
		  entry.put("beerName", name); //sets beerName as the name of the beer
		  entry.put("abv", abv); //sets abv as the abv
		  entry.put("ibu", ibu); //sets ibu as ibu
		  
		  return entry;
		}
		//Same as above but for a buddy
	private Map<String, Object> createBuddyListEntry(String name, String username) {
		  Map<String, Object> entry = new HashMap<String, Object>();
		  entry.put("name", name); //sets name as name
		  entry.put("user", username); //sets username as the username
		  
		  return entry;
		}
	
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
	protected void initVars(Resources res){
	}

    @Override
    /*
     * This method makes a connection with the Beer Buddy server and
     * creates a new account for the user using the name, username, and password 
     * fields. 
     * 
     * This is an action event and displays a pop up window stating if the 
     * sign up was successful or not. It does not return anything.
     * 
     * @see generated.StateMachineBase#onMain_Button1Action(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onMain_Button1Action(Component c, ActionEvent event) {
    	//THIS IS FOR CREATE ACCOUNT
    	String name = findNameSUField(c).getText(); //gets name
    	String username = findUsernameSUField(c).getText(); //gets username
    	String password = findPasswordSUField(c).getText(); //gets password
    	
    	
    	ConnectionRequest r = new ConnectionRequest() { //connection request
    		Map<String, Object> m;
    		
    		
    		
    		@Override
    		protected void postResponse(){
    		
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			String prop = null;
    			JSONParser p = new JSONParser(); // creates our parser
    			m = p.parseJSON(new InputStreamReader(input)); //maps it
    			
		        prop = (String)m.get("valid"); //all we need is if it is true or false so we get the first.
		        userId = (String)m.get("id"); //sets the user id so we can use it later
    			if(prop == "true"){
    	    	Dialog.show("Success","You are now signed up.", "OK",null);
    	    	userName = (String)m.get("username");
    	    	NetworkManager.getInstance().shutdown();
    	    	showForm("Home",null);
    			}
    			else{
        	    	Dialog.show("Error","This username is already taken.", "OK",null);

    			}
    		}
    	};
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/createAccount"); //url to connect to
    	r.setPost(true); //says that we are posing data
    	r.addArgument("username", username); //username
    	r.addArgument("password", password); //password
    	r.addArgument("name", name); //name
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
		
    
    }

    @Override
    /*
     * This method makes a connection to the Beer Buddy server and logs a user
     * in for a session using their username and password.
     * 
     * This is an action event and displays a pop up window stating if the 
     * sign up was successful or not. It does not return anything.
     * 
     * @see generated.StateMachineBase#onLogin_ButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onLogin_ButtonAction(Component c, ActionEvent event) {
    	//THIS IS TO LOGIN
    	String username = findUsernameLField(c).getText();
    	String password = findPasswordLField(c).getText();
    	
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    		}
    		//this does the exact same thing as above just with the login data
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			String prop = null;
    			JSONParser p = new JSONParser();
    			m = p.parseJSON(new InputStreamReader(input));
    		    prop = (String)m.get("valid");
    		    userId = (String)m.get("id");
    			System.out.println(prop);
    			if(prop == "true"){
    	    	Dialog.show("Success","You are now logged in.", "OK",null);
    	    	userName = (String)m.get("username");
    	    	NetworkManager.getInstance().shutdown();
    	    	showForm("Home", null);
    	    
    			}
    			else{
        	    	Dialog.show("Error","Username or Password is incorrect.", "OK",null);

    			}
    		}
    	};
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/login");
    	r.setPost(true);
    	r.addArgument("username", username);
    	r.addArgument("password", password);
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
    
    }

    @Override
    /*
     * This method makes a connection to the Beer Buddy server and gets
     * the list returned by the server based on the keyWords taken from the 
     * textField. It determins if it should use the url for getting beers by
     * name or by brewery depending on the value of selection.  
     * 
     * This is an action event and the list is
     * rendered depending on the get respons. It does not return anything.
     * 
     * @see generated.StateMachineBase#onHome_ButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_ButtonAction(final Component c, ActionEvent event) {
    	//SEARCH BEER BY NAME/BREWERY
    	String keyWords = findBeerSearchField().getText();
    	String selection = (String)findSearchBy().getSelectedItem();
    	idList.clear(); //clears idList so that I know which beers have been selected
    
    	final ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	  	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    			findBeerList(c).setModel(new DefaultListModel(data)); //sets the model after the server responds
    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    		    	
    		       String abv = (String)obj.get("abv"); //pulls the abv from the list
    		      
    		       String name = (String)obj.get("name");
    		       String ibu = (String)obj.get("ibu");
    		       String id = (String)obj.get("id_beer");
    		       idList.add(id);
    		       
    		        data.add(createListEntry(name,"ABV " + abv, "IBU " + ibu));
    		      
    			}
    		}
    	};
    	if(selection.equals("Name")){
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/beerByName");
    	r.setPost(false);
    	r.addArgument("beer", keyWords);

    	}
    	if(selection.equals("Brewery")){
        	r.setUrl("http://24.211.134.170/BeerBuddyServer/beerByBrewery");
        	r.setPost(false);
        	r.addArgument("brewery", keyWords);

    	}
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
    }

    @Override
    protected void beforeHome(Form f) {
    findUsernameLabel(f).setText(userName);
    }

    @Override //ADDS BEER TO A FAVORITE LIST
    /*
     * This method connects to the Beer Buddy server and adds a selected beer
     * to the user's favorites list. It checks which beers are selected in the
     * list and then sends their id as a parameter in the url. 
     * 
     * This is an action event and displays a pop up window stating if
     * the beer was successfully added or not. It does not return anything.
     * 
     * @see generated.StateMachineBase#onHome_FavoriteButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_FavoriteButtonAction(Component c, ActionEvent event) {
    	//adds a beer to a buddies favorite list
    	int size = findBeerList().size(); //gets size of generated search list
    	for(int i = 0 ; i < size ; i++) {
    	    Map<String, Object> value = (Map<String, Object>)findBeerList().getModel().getItemAt(i);
    	   String selected = (String)value.get("emblem"); //checks if anything is selected
    	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
    	    	String id = idList.get(i);
    	    	String listNum = userId;
    	        
    	    	
    	    	ConnectionRequest r = new ConnectionRequest() {
    	    		Map<String, Object> m;
    	    		
    	    		@Override
    	    		protected void postResponse(){
    	    			
    	    		}
    	    		
    	    		@Override
    	    		protected void readResponse(InputStream input) throws IOException {
    	    			String prop = null;
    	    			JSONParser p = new JSONParser(); // creates our parser
    	    			m = p.parseJSON(new InputStreamReader(input)); //maps it
    	    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("serverResponse"); //gets the list of responses
    	    			System.out.println(myList);
    	    			for (Map<String, Object> obj : myList) {
    	    				prop = (String)obj.get("valid");
    	    			}
    	    			System.out.println(prop);
    	    			if(prop == "true"){
    	    	    	Dialog.show("Success","Your selection has been added to your favorite list.", "OK",null);
    	    			}
    	    			else{
    	        	    	Dialog.show("Error","This failed.", "OK",null);

    	    			}
    	    		}
    	    	};
    	    	r.setUrl("http://24.211.134.170/BeerBuddyServer/favoriteBeer"); //url to connect to
    	    	r.setPost(true); //says that we are posing data
    	    	r.addArgument("listNumber", listNum); //list id/user id.
    	    	r.addArgument("beerid", id); //beer id
    	    	InfiniteProgress prog = new InfiniteProgress();
    	    	Dialog dlg = prog.showInifiniteBlocking();
    	    	r.setDisposeOnCompletion(dlg);
    	    	NetworkManager.getInstance().addToQueueAndWait(r);
    		

    	    	
    	    	
    	    	
    	    }
    	}
    
    }
   

    @Override //LIST MODEL FOR BUDDY LIST
    protected boolean initListModelMyBuddyList(List cmp) {
    
	  	ConnectionRequest r = new ConnectionRequest() {
		Map<String, Object> m;
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		
		@Override
		protected void postResponse(){
		      myBuddyListModel = new DefaultListModel(data);

			
		}
		
		@Override
		protected void readResponse(InputStream input) throws IOException {
			JSONParser p = new JSONParser();
			Reader r = new InputStreamReader(input);
			m = p.parseJSON(r);
			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
			System.out.println(myList);
			for (Map<String, Object> obj : myList) {
				if(obj.get("username") == null){
					continue;
				}
 		       String username = (String)obj.get("username");
 		       String name = (String)obj.get("name");
 		       System.out.println(username);
		       data.add(createBuddyListEntry(name, username));

			}
		}
	};
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/buddyList");
    	r.setPost(false);
    	r.addArgument("buddyid", userId);
	InfiniteProgress prog = new InfiniteProgress();
	Dialog dlg = prog.showInifiniteBlocking();
	r.setDisposeOnCompletion(dlg);
	NetworkManager.getInstance().addToQueueAndWait(r);
    
		cmp.setModel(myBuddyListModel);
        return true;
    } 

    @Override //RECOMMENDS A BEER TO A BUDDY
    /*
     * This method sets up the recommending of a beer to a buddy. It pulls the
     * beer id from the list of beers rendered and adds it to the recommendBeerIdList.
     * 
     * This is an action event and moves on to the next form after the event.
     * It does not return anything.
     * @see generated.StateMachineBase#onHome_RecommendButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_RecommendButtonAction(Component c, ActionEvent event) {
    	
    	int size = findBeerList().size(); //gets size of generated search list
    	for(int i = 0 ; i < size ; i++) {
    	    Map<String, Object> value = (Map<String, Object>)findBeerList().getModel().getItemAt(i);
    	   String selected = (String)value.get("emblem"); //checks if anything is selected
    	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
    	    	String id = idList.get(i);
    	    	recommendBeerIdList.add(id);
    	    	System.out.println(id);
    	    }
    	}
    	showForm("Recommend Beer", null);
    }

    @Override //SEARCH FOR BUDDIES
    /*
     * This method connects to the Beer Buddy server and returns the list
     * of buddies depending on the keyWords read from the text field. 
     * 
     * This is an action event and renders a list depends on the get response
     * from the server. This does not return anything.
     * 
     * @see generated.StateMachineBase#onHome_SearchBuddiesAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_SearchBuddiesAction(final Component c, ActionEvent event) {
    	String keyWords = findBuddySearchArea().getText();
    	final ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	buddyIdList.clear();  	
    	
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    			findBuddyList(c).setModel(new DefaultListModel(data));
    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    		    	
    		       String user = (String)obj.get("username");
    		      
    		       String name = (String)obj.get("name");
    		       String id = (String)obj.get("buddyid");
    		       buddyIdList.add(id);
    		        data.add(createBuddyListEntry(name, user));
    		       
    			}
    		}
    	};
    	
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/findBuddy");
    	r.setPost(false);
    	r.addArgument("buddy", keyWords);

    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
    
    }

    @Override //ADD NEW BUDDY
    /*
     * This method checks which buddies are selected in the list and adds them 
     * based on id to the users buddy list. This is done by connecting to the Beer
     * Buddy server and adding the appropriate ids to the url parameters. 
     * 
     * This is an action event and displays a pop up window stating if the
     * buddy was added or not. This does not return anything.
     * @see generated.StateMachineBase#onHome_AddBuddyAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_AddBuddyAction(Component c, ActionEvent event) {
    	int size = findBuddyList().size(); //gets size of generated search list
    	for(int i = 0 ; i < size ; i++) {
    	    Map<String, Object> value = (Map<String, Object>)findBuddyList().getModel().getItemAt(i);
    	   String selected = (String)value.get("emblem"); //checks if anything is selected
    	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
    	    	String id = buddyIdList.get(i);
    	    	String user = userId;
    	    	
    	    	
    	    	ConnectionRequest r = new ConnectionRequest() {
    	    		Map<String, Object> m;
    	    		
    	    		@Override
    	    		protected void postResponse(){
    	    			
    	    		}
    	    		
    	    		@Override
    	    		protected void readResponse(InputStream input) throws IOException {
    	    			String prop = null;
    	    			JSONParser p = new JSONParser(); // creates our parser
    	    			m = p.parseJSON(new InputStreamReader(input)); //maps it
    	    			prop = (String)m.get("valid");
    	    			System.out.println(prop);
    	    			if(prop == "true"){
    	    	    	Dialog.show("Success","Your buddy has been added to your buddy list.", "OK",null);
    	    	    	//NetworkManager.getInstance().shutdown();
    	    	    	System.out.println("got to here");
    	    			}
    	    			else{
    	    				//NetworkManager.getInstance().shutdown();
    	        	    	Dialog.show("Error","You have already added this buddy.", "OK",null);
    	        	    	

    	    			}
    	    		}
    	    	};
    	    	r.setUrl("http://24.211.134.170/BeerBuddyServer/addABuddy"); //url to connect to
    	    	r.setPost(true); //says that we are posing data
    	    	r.addArgument("buddy1", user); //user id.
    	    	r.addArgument("buddy2", id); //other buddy id
    	    	InfiniteProgress prog = new InfiniteProgress();
    	    	Dialog dlg = prog.showInifiniteBlocking();
    	    	r.setDisposeOnCompletion(dlg);
    	    	NetworkManager.getInstance().addToQueueAndWait(r);
    	    	
    	    }
    	}
    }

    @Override //LIST MODEL FOR FAVORITE LIST
    protected boolean initListModelMyFavoriteList(List cmp) {
    	favoriteList.clear();
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    		
    		
    		@Override
    		protected void postResponse(){
    		      myFavoriteListModel = new DefaultListModel(data);

    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    				String abv = (String)obj.get("abv");
      		      	String id = (String)obj.get("id_beer");
     		        String name = (String)obj.get("name");
     		        String ibu = (String)obj.get("ibu");
     		      favoriteList.add(id);
     		      data.add(createListEntry(name,"ABV " + abv, "IBU " + ibu));
    			}
    		}
    	};
        	r.setUrl("http://24.211.134.170/BeerBuddyServer/beerList");
        	r.setPost(false);
        	r.addArgument("listId", userId);
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
        
    		cmp.setModel(myFavoriteListModel);
            return true;
    }

    @Override //THIS RATES A BEER
    /*
     * This method connects to the Beer Buddy server to rate a beer on the 
     * user's favorite list. It checks which checkBox is selected and then uses
     * that selection to set the parameters of the url. 
     * 
     * This is an action event and displays a pop up window stating if the
     * beer was rated or not. This does not return anything.
     * 
     * @see generated.StateMachineBase#onHome_RateButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onHome_RateButtonAction(Component c, ActionEvent event) {
    	String rating = null;
    	
    	if(findCheckBox().isSelected() == true){
    		rating = "1";
    		
    	}
    	if(findCheckBox1().isSelected() == true){
    		rating = "2";
    	}
    	if(findCheckBox2().isSelected() == true){
    		rating = "3";
    	}
    	if(findCheckBox3().isSelected() == true){
    		rating = "4";
    	}
    	if(findCheckBox4().isSelected() == true){
    		rating = "5";
    	}
    	int size = findMyFavoriteList().size(); //gets size of generated search list
    	System.out.println(size);
    	for(int i = 0 ; i < size ; i++) {
    	    Map<String, Object> value = (Map<String, Object>)findMyFavoriteList().getModel().getItemAt(i);
    	    String selected = (String)value.get("emblem"); //checks if anything is selected
    	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
    	    	String beerId = favoriteList.get(i);
    	    	String user = userId;
    	   System.out.println(beerId + "beer, " + user + "user, " + value);
    	
    	
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			String prop = null;
    			JSONParser p = new JSONParser(); // creates our parser
    			m = p.parseJSON(new InputStreamReader(input)); //maps it
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("serverResponse"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    				prop = (String)obj.get("valid");
    			}
    			System.out.println(prop);
    			if(prop == "true"){
    	    	Dialog.show("Success","Your selection has been rated.", "OK",null);
    	    	

    			}
    			else{
        	    	Dialog.show("Error","This failed. Please check a box.", "OK",null);

    			}
    		}
    	};
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/rateABeer"); //url to connect to
    	r.setPost(true); //says that we are posing data
    	r.addArgument("userid", user); //user id.
    	r.addArgument("beerid", beerId); //beer id
    	r.addArgument("value", rating); //rating
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
    	
    	    }
    	}
    }

    @Override //SEARCH FOR BUDDIES IN RECOMMEND SCREEN
    /*
     * This method does the same as the buddy search it is just for the
     * screen that you can recommend beers on.
     * @see generated.StateMachineBase#onRecommendBeer_SearchButtonAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onRecommendBeer_SearchButtonAction(final Component c, ActionEvent event) {
    	String keyWords = findRecommendBuddySearch().getText();
    	final ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	buddyIdList.clear();  	
    	
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    			findRecommendBuddyList(c).setModel(new DefaultListModel(data));
    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    		    	
    		       String user = (String)obj.get("username");
    		      
    		       String name = (String)obj.get("name");
    		       String id = (String)obj.get("buddyid");
    		       buddyIdList.add(id);
    		        data.add(createBuddyListEntry(name, user));
    		       
    			}
    		}
    	};
    	
    	r.setUrl("http://24.211.134.170/BeerBuddyServer/findBuddy");
    	r.setPost(false);
    	r.addArgument("buddy", keyWords);

    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
    
    }

    @Override //RECOMMEND A BEER
    /*
     * This method connects to the Beer Buddy server and recommends a beer to
     * another user. It checks which ids are in the buddyIdList and then 
     * adds them to the parameters for the url.
     * 
     * This is an action event and displays a pop up window stating if the
     * buddy was recommended or not. This does not return anything.
     * @see generated.StateMachineBase#onRecommendBeer_RecommendBeerAction(com.codename1.ui.Component, com.codename1.ui.events.ActionEvent)
     */
    protected void onRecommendBeer_RecommendBeerAction(Component c, ActionEvent event) {
    	int beerIdSize = recommendBeerIdList.size();
    	int buddyIdSize = buddyIdList.size();
    	System.out.println(beerIdSize + " beer id, " + buddyIdSize);
    	for(int i = 0; i< buddyIdSize; i++){
    		
    		  Map<String, Object> value = (Map<String, Object>)findRecommendBuddyList().getModel().getItemAt(i);
    		  
       	   String selected = (String)value.get("emblem"); //checks if anything is selected
       	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
       	    	String buddyid = buddyIdList.get(i);
       	    	for(int j=0; j< beerIdSize; j++){
       	    	String beerid = recommendBeerIdList.get(j);
       	    	System.out.println(beerid);
       	    	ConnectionRequest r = new ConnectionRequest() {
    	    		Map<String, Object> m;
    	    		
    	    		@Override
    	    		protected void postResponse(){
    	    			
    	    		}
    	    		
    	    		@Override
    	    		protected void readResponse(InputStream input) throws IOException {
    	    			String prop = null;
    	    			JSONParser p = new JSONParser(); // creates our parser
    	    			m = p.parseJSON(new InputStreamReader(input)); //maps it
    	    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("serverResponse"); //gets the list of responses
    	    			System.out.println(myList);
    	    			for (Map<String, Object> obj : myList) {
    	    				prop = (String)obj.get("valid");
    	    			}
    	    			System.out.println(prop);
    	    			if(prop == "true"){
    	    	    	Dialog.show("Success","You have recommended this beer.", "OK",null);
    	    				
    	    			}
    	    			else{
    	        	    	Dialog.show("Error","This failed.", "OK",null);

    	    			}
    	    		}
    	    	};
    	    	r.setUrl("http://24.211.134.170/BeerBuddyServer/recommendBeer"); //url to connect to
    	    	r.setPost(true); //says that we are posing data
    	    	r.addArgument("buddyid", buddyid); //list id/user id.
    	    	r.addArgument("beerid", beerid); //beer id
    	    	InfiniteProgress prog = new InfiniteProgress();
    	    	Dialog dlg = prog.showInifiniteBlocking();
    	    	r.setDisposeOnCompletion(dlg);
    	    	NetworkManager.getInstance().addToQueueAndWait(r);
    	    	
    	    	
       	    }
    	}
    }
    }

    @Override
    protected void onHome_RecommendedBeerAction(Component c, ActionEvent event) {
    	showForm("Recommended Beer",null);
    
    }

    @Override //RECOMMENDED BEER LIST MODEL
    protected boolean initListModelRecommendedBeerList(List cmp) {
    	
    	recommendedList.clear();
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    		
    		
    		@Override
    		protected void postResponse(){
    		      myRecommendedListModel = new DefaultListModel(data);

    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    				String abv = (String)obj.get("abv");
      		      	String id = (String)obj.get("id_beer");
     		        String name = (String)obj.get("name");
     		        String ibu = (String)obj.get("ibu");
     		      recommendedList.add(id);
     		      data.add(createListEntry(name,"ABV " + abv, "IBU " + ibu));
    			}
    		}
    	};
        	r.setUrl("http://24.211.134.170/BeerBuddyServer/recommendedBeerList");
        	r.setPost(false);
        	r.addArgument("buddyid", userId);
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
        
    		cmp.setModel(myRecommendedListModel);
            return true;
        
    }

    @Override //NEW BEER LIST MODEL
    protected boolean initListModelNewBeerList(List cmp) {
    	newBeerList.clear();
    	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    		
    		
    		@Override
    		protected void postResponse(){
    		      myNewBeerListModel = new DefaultListModel(data);

    			
    		}
    		
    		@Override
    		protected void readResponse(InputStream input) throws IOException {
    			JSONParser p = new JSONParser();
    			Reader r = new InputStreamReader(input);
    			m = p.parseJSON(r);
    			java.util.List<Map<String, Object>> myList = (java.util.List<Map<String,Object>>)m.get("list"); //gets the list of responses
    			System.out.println(myList);
    			for (Map<String, Object> obj : myList) {
    				String abv = (String)obj.get("abv");
      		      	String id = (String)obj.get("id_beer");
     		        String name = (String)obj.get("name");
     		        String ibu = (String)obj.get("ibu");
     		      newBeerList.add(id);
     		      data.add(createListEntry(name,"ABV " + abv, "IBU " + ibu));
    			}
    		}
    	};
        	r.setUrl("http://24.211.134.170/BeerBuddyServer/findNewBeer");
        	r.setPost(false);
        	r.addArgument("buddyid", userId);
    	InfiniteProgress prog = new InfiniteProgress();
    	Dialog dlg = prog.showInifiniteBlocking();
    	r.setDisposeOnCompletion(dlg);
    	NetworkManager.getInstance().addToQueueAndWait(r);
        
    		cmp.setModel(myNewBeerListModel);
            return true;
        
    	
    	
    }

    @Override
    protected void onHome_FindNewBeerAction(Component c, ActionEvent event) {
    	showForm("New Beer", null);
    
    }
}
