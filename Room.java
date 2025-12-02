import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: Room
 * @author: Testimony Awuzie
 * @version 2.0
 * Course : CSE 201 Fall 2025
 *
 * Purpose:
 * Abstract parent class representing a generic room in SpongeBob’s Best Day Ever.
 * All specific rooms (SpongeBob’s House, Squidward’s House, Patrick’s Rock,
 * Sandy’s Dome, Krusty Krab) extend this class and implement their own logic.
 *
 * This class now:
 *  - Holds shared state for all rooms (player, locations, current location)
 *  - Shares a Scanner for user input (provided by Game)
 *  - Provides helper methods for moving between locations
 */
public abstract class Room {

    // Basic room info
    protected String roomName;
    protected String roomDescription;
    protected boolean roomComplete;

    // Link to the next room in the sequence
    protected Room nextRoom;

    // Shared across all rooms:
    // - The current player in this room
    // - The current location within the room
    // - The set of locations within this room
    // - A list of location names (used for parsing input)
    protected Player player;
    protected Location currLocation;
    protected List<Location> locationsList;
    protected List<String> locationNameList;

    // Shared Scanner provided by Game (do NOT close in rooms)
    protected Scanner input;

    /**
     * Constructor for Room.
     *
     * @param roomName        The name of the room.
     * @param roomDescription A textual description of the room.
     * @param roomComplete    Whether this room starts as complete.
     * @param nextRoom        The next room in the sequence (or null if last).
     */
    public Room(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomComplete = roomComplete;
        this.nextRoom = nextRoom;

        this.locationsList = new ArrayList<>();
        this.locationNameList = new ArrayList<>();
    }

    /**
     * Sets the shared Scanner for this room.
     * This should be called from Game so that all rooms share the same input source.
     *
     * @param input Shared Scanner from Game.
     */
    public void setScanner(Scanner input) {
        this.input = input;
    }

    // --------------------------------------------------
    // Abstract methods to be implemented by child rooms
    // --------------------------------------------------

    /**
     * Defines what happens when the player enters this room.
     * Typically sets up locations, items, and prints an intro.
     *
     * @param player The current Player object.
     */
    public abstract void enterRoom(Player player);

    /**
     * Runs the room-specific main loop.
     * This should handle input until the room is either completed
     * or the player chooses to leave.
     */
    public abstract void runRoom();

    /**
     * Checks if this room's goal has been completed.
     *
     * @return true if the room is complete, false otherwise.
     */
    public abstract boolean checkCompletionOfRoom();

    // --------------------------------------------------
    // Common behavior and helpers
    // --------------------------------------------------

    /**
     * Marks the room as complete and prints a message.
     */
    public void completeRoom() {
        this.roomComplete = true;
        System.out.println(roomName + " completed! Moving on...");
    }

    /**
     * @return true if the room has been marked complete.
     */
    public boolean isRoomComplete() {
        return roomComplete;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public Room getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    /**
     * Registers a Location in this room and tracks its name.
     * Child classes can call this instead of directly adding to both lists.
     *
     * @param location Location to add to this room.
     */
    protected void addLocation(Location location) {
        if (location != null) {
            locationsList.add(location);
            locationNameList.add(location.getLocationName());
        }
    }

    /**
     * Attempts to move to a new location based on the user input string.
     * Supports:
     *  - "Move Kitchen"
     *  - "move Grill"
     *  - "2 Kitchen"
     *
     * @param userChoice The raw command string entered by the player.
     * @return true if movement succeeded, false otherwise.
     */
    protected boolean handleMoveCommand(String userChoice) {
        if (userChoice == null || userChoice.isEmpty()) {
            System.out.println("Please specify where you want to move.");
            return false;
        }

        String lower = userChoice.toLowerCase();
        String targetName = "";

        // If command starts with "move"
        if (lower.startsWith("move")) {
            if (userChoice.length() > 4) {
                targetName = userChoice.substring(4).trim();
            }
        }
        // If user typed "2 Kitchen"
        else if (userChoice.startsWith("2")) {
            if (userChoice.length() > 2) {
                targetName = userChoice.substring(2).trim();
            }
        } else {
            // Not a movement command format we recognize
            System.out.println("To move, use: 'Move <Location>' or '2 <Location>'.");
            return false;
        }

        if (targetName.isEmpty()) {
            System.out.println("Please specify a location name to move to.");
            return false;
        }

        // Check against known location names (case-sensitive match)
        if (!locationNameList.contains(targetName)) {
            System.out.println("That location does not exist. Use 'Search' to see nearby locations.");
            return false;
        }

        int index = locationNameList.indexOf(targetName);
        Location targetLocation = locationsList.get(index);

        // Ensure the target is reachable from the current location
        if (currLocation != null && currLocation.getsubLocations().contains(targetLocation)) {
            currLocation = targetLocation;
            System.out.println("You moved to " + targetLocation.getLocationName() + ".");
            return true;
        } else {
            System.out.println("You cannot move there from your current location.");
            return false;
        }
    }
}
