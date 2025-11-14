/**
 * Class: Location
 * 
 * @author Talan Malicote
 * @version 1.0 Course: CSE 201 Fall 2025 Written: November 10, 2025
 *
 * Purpose: – Location Class Definition
 */
public class Location {
	private String locationName;
	private Item items;
	private Location subLocations;

	/**
	 * Constructor for Location Initializes a Location object with the specified
	 * Name, Items in it, and sublocations
	 * 
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
	 * 
	 * @return the Location containing the item, or null if not found
	 */
	public Location search() {
		if (items != null) {
			System.out.println("You found an item: " + items.getName());
			return this;
		}

		if (subLocations != null) {
			System.out.println("Searching sublocation: " + subLocations.getLocationName());
			return subLocations.search();
		}
		
		System.out.println("No items found in " + locationName + " or its sublocations.");
		return null;
	}

	/**
	 * Adds Item to Location
	 * If an item already exists, it will be replaced
	 * 
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		if (item == null) {
			System.out.println("Cannot add a null item.");
			return;
		}
		
		if (this.items != null) {
			System.out.println("Replacing existing item (" + this.items.getName() + ") with " + items.getName() + ".");
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

		if (subLocations != null) {
			return subLocations.removeItem(itemName);
		}

		System.out.println(itemName + " not found in " + locationName + ".");
		return null;

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

	public Location getsubLocations() {
		return subLocations;
	}

	public void setsubLocations(Location subLocations) {
		this.subLocations = subLocations;
	}
}
