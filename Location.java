import java.util.ArrayList;
import java.util.List;

/**
 * Class: Location
 * 
 * @author Talan Malicote
 * @version 1.0 Course: CSE 201 Fall 2025 Written: November 10, 2025
 *
 *          Purpose: – Location Class Definition
 */
public class Location {
	private String locationName;
	private Item items;
	
	// Sublocations act as a list of locations that this location connects to
	private List<Location> subLocations = new ArrayList<>();

	/**
	 * Constructor for Location Initializes a Location object with the specified
	 * Name, Items in it
	 * 
	 * @param locationName
	 * @param items
	 * 
	 */
	public Location(String locationName, Item items) {
		this.locationName = locationName;
		this.items = items;
	}

	/**
	 * Searches and lists for any item in the location and what 
	 * sublocations it connects to
	 * 
	 * @return A boolean for whether anything was found
	 */
	public boolean search() {
		boolean found = false;
	    
	    if (items != null) {
			System.out.println("You found an item: " + items.getName());
			found = true;
		}

		if (subLocations != null) {
			found = true;
		    System.out.println("This room connects to: ");
			for(Location l : subLocations) {
			    System.out.println(l.getLocationName());
			}
		}
		
		if (found == false) {
		    System.out.println("No items or locations found in " + locationName);
		}
		
		return found;
	}

	/**
	 * Adds Item to Location
	 * If an item already exists, it will be replaced
	 * 
	 * @para item the item to add
	 */
	public void addItem(Item item) {
		if (item == null) {
			System.out.println("Cannot add a null item.");
			return;
		}
		
		if (this.items != null) {
			System.out.println("Replacing existing item (" + this.items.getName() + ") with " + item.getName() + ".");
		} else {
			System.out.println("Adding " + item.getName() + " to " + locationName + ".");
		}
		
		this.items = item;
	}

	/**
	 * Removes Item from Location
	 * 
	 * @param itemName the name of the item to remove
	 * @return item removed, or null if not found
	 */
	public Item removeItem(String itemName) {
		if (items == null) {
			System.out.println("There are no items in " + locationName + "to remove.");
			return null;
		}

		if (items.getName().equals(itemName)) {
			Item removedItem = items;
			System.out.println("Removed " + removedItem.getName() + " from " + locationName + ".");
			return removedItem;
		}

		System.out.println(itemName + " not found in " + locationName + ".");
		return null;

	}
	
	public boolean hasItem() {
	    if (items == null) {
	        return false;
	    }
	    return true;
	}
	
	/*
	 *  Getters and Setters for Location, Item, and subLocation, 
	 */

	public String getLocationName() {
		return locationName;
	}

	public Item getItems() {
		return items;
	}

	public List<Location> getsubLocations() {
		return subLocations;
	}

	public void addSubLocation(Location l) {
		this.subLocations.add(l);
	}
	
	public void removeSubLocation(Location l) {
	    this.subLocations.remove(l);
	}
}
