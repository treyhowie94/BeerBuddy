package final_data;

public class RestaurantData {
	
	public static String[] getRestaurantInfo(String res_name) {
		if (res_name == null) {
			throw new IllegalArgumentException("restaurant name was null");
		}
		
		if (res_name.equals("Raleigh Beer Garden")) {
			return new String[] {"Raleigh", "NC", "27603", "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwj-0d7SzOTLAhXBRCYKHQEoAZQQjRwIBw&url=https%3A%2F%2Ftwitter.com%2Fralbeergarden&psig=AFQjCNEdvrFw6SZt6dEbKlAy3BIMxjC2tg&ust=1459296341754428"};
											 
		}
		else {
			return null;
		}
	}
}
