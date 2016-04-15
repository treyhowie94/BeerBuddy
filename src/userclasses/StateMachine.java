/**
 * Your application code goes here
 * 
 */

package userclasses;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.util.Resources;

import generated.StateMachineBase;
 
/**
 *
 * @author Cody Barnes
 */
public class StateMachine extends StateMachineBase {
	ArrayList<String> idList = new ArrayList<String>();
	String userId;
	String userName;
	ListModel buddyListModel;
	private Map<String, Object> createListEntry(String name, String abv, String ibu) {
		  Map<String, Object> entry = new HashMap<String, Object>();
		  entry.put("beerName", name);
		  entry.put("abv", abv);
		  entry.put("ibu", ibu);
		  
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
    protected void onMain_Button1Action(Component c, ActionEvent event) {
    	//this is for the create account page
    	String name = findNameSUField(c).getText(); //gets name
    	String username = findUsernameSUField(c).getText(); //gets username
    	String password = findPasswordSUField(c).getText(); //gets password
    	
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
    			
		        prop = (String)m.get("valid"); //all we need is if it is true or false so we get the first.
		        userId = (String)m.get("id"); //sets the user id so we can use it later
    			System.out.println(prop);
    			if(prop == "true"){
    	    	Dialog.show("Success","You are now signed up.", "OK",null);
    	    	userName = (String)m.get("username");
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
    protected void onLogin_ButtonAction(Component c, ActionEvent event) {
    	//this is to login
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
    	    	showForm("Home",null);
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
    protected void onHome_ButtonAction(final Component c, ActionEvent event) {
    	//finds form you are on
    	//Form currentForm = Display.getInstance().getCurrent();
    	String keyWords = findBeerSearchField().getText();
    	String selection = (String)findSearchBy().getSelectedItem();
    	idList.clear();
    	//MultiList beer = findBeerList(c);
    	final ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	  	ConnectionRequest r = new ConnectionRequest() {
    		Map<String, Object> m;
    		
    		@Override
    		protected void postResponse(){
    			
    			findBeerList(c).setModel(new DefaultListModel(data));
    			
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
    		      
    		       String name = (String)obj.get("name");
    		       String ibu = (String)obj.get("ibu");
    		       String id = (String)obj.get("id_beer");
    		       idList.add(id);
    		        System.out.println(abv);
    		        data.add(createListEntry(name,"ABV " + abv, "IBU " + ibu));
    		       // beer.setModel(new DefaultListModel(data));
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

    @Override
    protected void onHome_FavoriteButtonAction(Component c, ActionEvent event) {
    	//adds a beer to a buddies favorite list
    	int size = findBeerList().size(); //gets size of generated search list
    	for(int i = 0 ; i < size ; i++) {
    	    Map<String, Object> value = (Map<String, Object>)findBeerList().getModel().getItemAt(i);
    	   String selected = (String)value.get("emblem"); //checks if anything is selected
    	    if(selected != null && selected.equals("true")) { //if it is then it does stuff with the id. 
    	    	String id = idList.get(i);
    	         System.out.println(id);
    	    }
    	}
    
    }

    //FIX THIS
   

    @Override
    protected boolean initListModelBuddyList(List cmp) {
    
	  	ConnectionRequest r = new ConnectionRequest() {
		Map<String, Object> m;
		Vector vec = new Vector();
		Hashtable h = new Hashtable();
		
		@Override
		protected void postResponse(){
		      buddyListModel = new DefaultListModel(vec);

			
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
 		       h.put("username", username);
 		       h.put("name", name);
 		       vec.addElement(h);
		       // beer.setModel(new DefaultListModel(data));
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
    
		cmp.setModel(buddyListModel);
        return true;
    } 
}
