/**
 * Class: Location
* @author Testimony Awuzie
* @version 1.0
* Course : CSE 201 Fall 2025
* Written: November 5, 2025
*
* Purpose: – Location Class Definition
 */
public class Location {
	private String locationName;
	private Item items;
	private Location subLocations;
	
	/**
	 * Constructor for Location
	 * Initializes a Location object with the specified Name, Items in it, and sublocations
	 * @param locationName
	 * @param items
	 * @param subLocations
	 */
	public Location(String locationName, Item items, Location subLocations) {
		this.locationName = locationName;
		this.items = items;
		this.subLocations = subLocations;
	}
	
	/**
	 * Search for Item in Location
	 * @return
	 */
	public Location search () {
		// TODO: 
		return null;
	}
	
	/**
	 * Adds Item to Location
	 */
	public void addItem(Item item) {
		// TODO: 
		// Test for Invalid Input
		
		
		// Add if Valid
	}
	
	/**
	 * Removes Item from Location
	 * @param itemName
	 * @return item removed
	 */
	public Item removeItem(String itemName) {
		// TODO:
		return items;
		
	}
}
