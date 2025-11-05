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
	private String name;
	private String description;
	
	/**
	 * Item Constructor
	 * @param name
	 * @param description
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
		// TODO:
		return false;
	}
	
	/**
	 * Gets the Item Name
	 * @return name
	 */
	public String getName() {
		return name;
	}
}
