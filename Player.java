/**
 * Class: Player
 * Author: Sam Adeyemo
 * @version 1.0
 * Course: CSE 201
 * Purpose: represents the player(spongebob) in the game. Handles
 * movement between rooms, managing inventory, and perfoming actions
 */
public class Player {
    private Room currentRoom; // tracks the current room the player is in
    private Inventory inventory; // the players inventory
    private String id; // the players ID
    private String name; // the player name
    
    
    /**
     * makes a play with an initial room, inventory, ID, and name
     * @param currentRoom: the starting room
     * @param inventory: the players inventory
     * @param id: the players id
     * @param name: the players name
     */
    public Player(Room currentRoom, Inventory inventory, String id, String name) {
        this.currentRoom = currentRoom;
        this.inventory = inventory;
        this.id = id;
        this.name = name;
    }
    
    /**
     * moves the player to a different room.
     * @param room the room to enter
     */
    public void enterRoom(Room room) {
        currentRoom = room;
    }
    
    /**
     * 
     * @param item the item to pick up
     * @return true if successful, false
     */
    public boolean pickup(Item item) {
        // TODO: Add pickup logic logic once Inventory class is implemented 
    	// ex: 
    	// if (item != null) {
    	// 		inventory.addItem(item);
    	// 		return true;
    	// }
        return false;
    }
    
    /**
     * 
     * @param item: the item being used
     * @param target: the target the item is used on
     * @return true if the use is successful, false if not
     */
    public boolean use(Item item, Object target) {
        // TODO: add use 
        
        return false;
    }
    
    /**
     * 
     * @param item1 the first item
     * @param item2 the second item
     * @return a new combined item or null if the combination doesnt work
     */
    public Item combine(Item item1, Item item2) {
        // TODO: Add combine code
        
        return null;
    }
    
    /**
     * gets the players current inventory
     * @return the inventory object
     */
    public Inventory getInventory() {
    	return inventory;
    }
}
