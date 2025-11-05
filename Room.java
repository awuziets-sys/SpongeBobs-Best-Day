/**
 * Class: Room
* @author Testimony Awuzie
* @version 1.0
* Course : CSE 201 Fall 2025
* Written: November 5, 2025
*
* Purpose: – Room Parent Class Definition
 */
public class Room {
	private String roomName;
	private String roomDescription;
	private boolean roomComplete;
	private Room nextRoom;
	
	/**
	 * Constructor for Room
	 * Initializes a Room object with the specified Name, Description, Completion status, and Next Room node. 
	 * @param roomName
	 * @param roomDescription
	 * @param roomComplete
	 * @param nextRoom
	 */
	public Room (String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
		this.roomName = roomName;
		this.roomDescription = roomDescription;
		this.roomComplete = false;
		this.nextRoom = null;
	}
	
	/**
	 * 
	 */
	public void enterRoom() {
		// TODO:
	}
	
	public void checkCompletionOfRoom() {
		// TODO:
	}
	
	public void completeRoom() {
		// TODO:
	}
	
	public Location getLocations() {
		// TODO:
		return null;
		
	} 
}
