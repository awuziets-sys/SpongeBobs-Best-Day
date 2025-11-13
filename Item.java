/**
 * Class: Item
* @author Testimony Awuzie
* @version 1.0
* Course : CSE 201 Fall 2025
* Written: November 5, 2025
*
* Purpose: – Item Class Definition
 */
public class Item {
	private String name; // the name of the item
	private String description; // description of the item
	
	/**
	 * Item Constructor
	 * @param name: the name of the item
	 * @param description: item description
	 */
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Use an Item
	 * @param target
	 * @return
	 */
	public boolean use(Object target) {
		System.out.println("You used " + name + " on " + target);
		return false;
	}
	
	/**
	 * Gets the Item Name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the description of the item
	 * @return the item description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * sets a description for the item
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * the items details
	 */
	public void printInfo() {
		System.out.println("Item: " + name + " -- " + description); 
	}
			
}
