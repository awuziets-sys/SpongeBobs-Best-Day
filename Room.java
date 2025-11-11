/**
 * Class: Room
* @author Testimony Awuzie
* @version 1.0
* Course : CSE 201 Fall 2025
* Written: November 6, 2025
*
* Purpose: – Abstract parent class representing a generic room in SpongeBob’s Best Day Ever game.
 * - Each specific room (like SpongeBob’s Pineapple, Sandy’s Dome, etc.)
 *   will extend this parent class and implement its own  logic.
 */
public abstract class Room {
	private String roomName;
	private String roomDescription;
	private boolean roomComplete;
	private Room nextRoom;
	
	/**
	 * Constructor for Room
	 * Initializes a Room object with the specified Name, Description, Completion status, and Next Room.
	 * By Default, this room is not complete 
	 * @param roomName
	 * @param roomDescription
	 * @param roomComplete
	 * @param nextRoom
	 */
	public Room (String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
		this.roomName = roomName;
		this.roomDescription = roomDescription;
		this.roomComplete = false;
		this.nextRoom = nextRoom;
	}
	
	// --------------------------------------------------
	// Abstract Methods 
	// (Child classes will have different implementations of these)
	// --------------------------------------------------
	
	/**
	 * Defines what happens when the player enters this room.
     * Must be implemented by each subclass.
	 */
	public abstract void enterRoom();
	
	/**
     * Checks if the room’s goal has been completed.
     * @return true if the room is complete, false otherwise
     */
	public abstract boolean checkCompletionOfRoom();
	
	/**
	 * Marks The Room as complete
	 */
	public void completeRoom() {
		this.roomComplete = true;
		System.out.println(roomName + " completed! Moving on...");
	}
	
	// -----------------------------------------
	// Getters and Setters
	// -----------------------------------------
	/**
	 * Gets Room Name
	 * @return room Name
	 */
	public String getRoomName() {
        return roomName;
    }

	/**
	 * Gets Room's Description
	 * @return room's description
	 */
    public String getRoomDescription() {
        return roomDescription;
    }

    /**
     * Checks if Room is Complete
     * @return true if room is complete, false otherwise
     */
    public boolean isRoomComplete() {
        return roomComplete;
    }

    /**
     * Gets the Next Room
     * @return next room after current
     */
    public Room getNextRoom() {
        return nextRoom;
    }

    /**
     * Sets the next Room
     * TODO: Reminder to have last room have no next room
     * @param nextRoom
     */
    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }
    
    /**
     * Gets Locations in a Room
     * @return
     */
    public Location getLocations() {
		// TODO:
		return null;

    }
}
