import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: SpongebobsHouse
 * @author: Andrew Albert
 * @version: 1.0
 * Course: CSE 201 Fall 2025
 *
 * Purpose:
 * Room 1 of the game. SpongeBob must find:
 *  - His Work Hat
 *  - Gary's Food
 *
 * This class extends the abstract Room class and
 * defines the specific logic for SpongeBob's house.
 */
public class SpongebobsHouse extends Room {

    // Stores all locations in the house
    List<Location> locationsList = new ArrayList<>();

    // Stores location names (used for movement commands)
    List<String> locationNameList = new ArrayList<>();

    // Reference to the player in this room
    Player player;

    // The current location SpongeBob is standing in
    Location currLocation;
    
    /**
     * Full constructor
     */
    public SpongebobsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }
    
    /**
     * Default constructor
     * Sets default name, description, and nextRoom
     */
    public SpongebobsHouse() {
        this("Spongebob's House", "The first level of the game!", false, null);
    }

    /**
     * Called when the player first enters this room.
     * Creates locations, items, and connects everything together.
     */
    @Override
    public void enterRoom(Player player) {

        // Save reference to player
        this.player = player;

        // Creating the items to collect
        Item hat = new Item("Work Hat", "Your work hat, you should put it on", null, null);
        Item food = new Item("Gary's Food", "Gary is hungry, feed it to him before you leave", null, null);
        
        // Creating all the rooms of the house
        Location bedroom = new Location("Bedroom", null);
        Location closet = new Location("Closet", hat);    // Hat is in the closet
        Location kitchen = new Location("Kitchen", food); // Food is in the kitchen
        Location livingroom = new Location("Livingroom", null);
        Location stairs = new Location("Staircase", null);
        Location bathroom = new Location("Bathroom", null);
        
        // Linking the locations (creating walkable paths)
        bedroom.addSubLocation(closet);
        bedroom.addSubLocation(stairs);
        
        closet.addSubLocation(bedroom);
        
        stairs.addSubLocation(bedroom);
        stairs.addSubLocation(livingroom);
        
        livingroom.addSubLocation(stairs);
        livingroom.addSubLocation(kitchen);
        livingroom.addSubLocation(bathroom);
        
        kitchen.addSubLocation(livingroom);
        bathroom.addSubLocation(livingroom);
        
        // Add locations to collections
        locationsList.add(bedroom);
        locationsList.add(kitchen);
        locationsList.add(closet);
        locationsList.add(stairs);
        locationsList.add(livingroom);
        locationsList.add(bathroom);
        
        // Store their names separately for move commands
        for (Location l : locationsList) {
            locationNameList.add(l.getLocationName());
        }
        
        // Set the starting location
        currLocation = bedroom;

        // Display intro message
        System.out.println(
            "Good morning Spongebob! It's another exciting day in Bikini Bottom.\n" 
          + "Before you can take on the world you need to get ready.\n"
          + "Put on your Work Hat and feed Gary before moving on with your day.\n"
        );
    }
    
    /**
     * Checks if the room is completed.
     * SpongeBob must have both required items.
     */
    @Override
    public boolean checkCompletionOfRoom() {
        return player.getInventory().contains("Work Hat") &&
               player.getInventory().contains("Gary's Food");
    }

    /**
     * Main loop for this room.
     * Handles player input and actions.
     */
    @Override
    public void runRoom() {

        Scanner userInput = new Scanner(System.in);
        
        while(!super.isRoomComplete()) {

            // Display current location
            System.out.println("\nYou are in " + currLocation.getLocationName());

            // Available options
            System.out.println("What would you like to do?\n"
                    + "1] Search\n"
                    + "2] Move [Location Name]\n"
                    + "3] Take\n"
                    + "4] See Inventory\n"
                    + "5] Quit");

            System.out.print("> ");
            String choice = userInput.nextLine();
            
            switch (choice.toLowerCase()) {

            // Show items and connected rooms
            case "search":
            case "1":
                currLocation.search();
                break;

            // Take item if it exists
            case "take":
            case "3":
                if (currLocation.hasItem()) {
                    player.getInventory().addItem(currLocation.getItems());
                    currLocation.removeItem(currLocation.getItems().getName());
                } else {
                    System.out.println("This location doesn't have anything to take.");
                }
                break;

            // Print inventory
            case "inventory":
            case "see inventory":
            case "4":
                player.getInventory().printAllItemDescriptions();
                break;

            // Exit room early
            case "quit":
                return;
            }

            // Move command is handled separately
            if (choice.toLowerCase().contains("move") || choice.contains("2")) {
                moveMethod(choice);
            }
            
            // If both items are found, the room is complete
            if (checkCompletionOfRoom()) {

                System.out.println(
                    "Spongebob feeds Gary his food and puts on his trusty hat " +
                    "before heading off to begin the day!"
                );

                // Remove Gary's food from inventory after feeding
                player.getInventory().removeItem("Gary's Food");

                // Mark the room as done
                super.completeRoom();
                return;
            }
        }     
    }
    
    /**
     * Handles moving between connected locations in the house.
     * @param choice User input (e.g., "Move Kitchen")
     */
    private void moveMethod(String choice) {

        if (choice.length() >= 6) {

            // Move using full command: "Move Kitchen"
            if (locationNameList.contains(choice.substring(5))) {

                int i = locationNameList.indexOf(choice.substring(5));
                Location l = locationsList.get(i);
                
                if (currLocation.getsubLocations().contains(l)) {
                    currLocation = l;
                    System.out.println("You moved to " + l.getLocationName());
                } else {
                    System.out.println("That location is not accessible.");
                }
                
            }

            // Move using command: "2 Kitchen"
            else if (locationNameList.contains(choice.substring(2))) {

                int i = locationNameList.indexOf(choice.substring(2));
                Location l = locationsList.get(i);
                
                if (currLocation.getsubLocations().contains(l)) {
                    currLocation = l;
                    System.out.println("You moved to " + l.getLocationName());
                } else {
                    System.out.println("That location is not accessible.");
                }
            }

            // Invalid location
            else {
                System.out.println("That location is not valid.\nUse Search to see nearby locations.");
            }

        } else {
            System.out.println("Invalid Move command. Use: Move <Location Name>");
        }
    }
}
