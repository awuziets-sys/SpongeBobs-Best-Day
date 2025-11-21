/**
 * Class: Player
 * Author: Sam Adeyemo
 * @version 1.0
 * Course: CSE 201
 * Purpose: Represents the player(SpongeBob) in the game. Handles
 * movement between rooms, managing inventory, and performing actions
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
        this.currentRoom = room;
        System.out.println(name + " moved to " + room.getRoomName());
    }
    
    /**
     * 
     * @param item the item to pick up
     * @return true if successful, false
     */
    public boolean pickup(Item item) {
    	if (item !=null) {
    		inventory.addItem(item);
    		System.out.println(item.getName() + " was added to your inventory");
    		return true;
    	} else {
    		System.out.println("nothing to pick up.");
    		return false;
    	}

    }
    
    /**
     * 
     * @param item: the item being used
     * @param target: the target the item is used on
     * @return true if the use is successful, false if not
     */
    public boolean use(Item item, String target) {
        if (item == null) {
        	System.out.println("You dont have the item.");
        	return false;
        }
        System.out.println(name + " use " + item.getName() + " on " + target);
        return item.use(target);
    }
    
    /**
     * 
     * @param item1 the first item
     * @param item2 the second item
     * @return a new combined item or null if the combination doesn't work
     */
    public Item combine(Item item1, Item item2) {
    	 if (item1 == null || item2 == null) {
             System.out.println("You need two items to combine.");
             return null;
         }

         // placeholder logic for combining (can be expanded later)
         String combinedName = item1.getName() + " + " + item2.getName();
         String combinedDesc = "new item created by combining " 
                                 + item1.getName() + " and " + item2.getName() + ".";
         Item newItem = new Item(combinedName, combinedDesc, null, null);

         System.out.println("You combined " + item1.getName() + " and " + item2.getName() + " to make " + newItem.getName());
         return newItem;        

    }
    
    /**
     * gets the players current inventory
     * @return the inventory object
     */
    public Inventory getInventory() {
    	return inventory;
    }
    
    public Room getCurrentRoom() {
    	return currentRoom;
    }
}
