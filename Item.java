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
	private String useTarget; // what the player must use it on to work. null for no use
	private String hintText; // hint text if the player uses it incorrectly
	
	/**
	 * Item Constructor
	 * @param name: the name of the item
	 * @param description: item description
	 * @param useTarget: the valid use location/item for this object
	 */
	public Item(String name, String description, String useTarget, String hintText) {
		this.name = name;
		this.description = description;
		this.useTarget = useTarget;
		this.hintText = hintText;
	}
	
	/**
	 * Use an Item
	 * @param target
	 * @return
	 */
	public boolean use(String target) {
		if (useTarget == null) {
		    System.out.println("It looks like this item can't be used...");
		    return false;
		}
		if (useTarget.toLowerCase().equals(target.toLowerCase())) {
		    System.out.println("You used " + name + " on " + target);
	        return true;
		}
		System.out.println("It looks like you can't use " + name + " on " + target +
		        "\nTry using it on \"" + hintText + "\"");
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
