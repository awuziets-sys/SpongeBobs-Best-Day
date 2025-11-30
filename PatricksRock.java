import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: PatricksRock
 * @author Sam Adeyemo
 * Course: CSE 201
 * 
 * Purpose:
 * Room 3 of the game. SpongeBob must cheer Patrick up by making him
 * an ice cream cone. Throughout this level, the player will go through
 * different locations to find the required ingredients to make the cone,
 * and then give it to Patrick.
 */
public class PatricksRock extends Room {

    // The list of locations inside Patrick's rock (nodes the player can move between)
    private List<Location> locationsList = new ArrayList<Location>();

    // Stores the names of the locations (for parsing player input)
    private List<String> locationNameList = new ArrayList<String>();

    // Reference to the player
    private Player player;

    // The current location SpongeBob is in
    private Location currLocation;

    // Names of the ingredients needed for the ice cream
    private static final String Waffle_Cone_Name = "Waffle Cone";
    private static final String Ice_Cream_Name = "Vanilla Ice Cream";
    private static final String Sprinkles_Name = "Chocolate Sprinkles";
    private static final String Ice_Cream_Cone_Name = "Ice Cream Cone";
    
    /**
     * Full constructor.
     *
     * @param roomName        the room name
     * @param roomDescription description of the room
     * @param roomComplete    true if already complete, false otherwise
     * @param nextRoom        reference to the next room in sequence
     */
    public PatricksRock(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }

    /**
     * Default constructor with preset name/description.
     */
    public PatricksRock() {
        this("Patrick's Rock",
             "Patrick is crying on his couch. Make him an Ice Cream Cone to cheer him up.",
             false,
             null);
    }

    /**
     * Called when the player first enters Patrick's Rock.
     * Sets up locations, items, and prints the story intro and objectives.
     *
     * @param player SpongeBob (the Player object)
     */
    @Override
    public void enterRoom(Player player) {
        this.player = player;

        // Create the required items
        Item waffleCone = new Item(Waffle_Cone_Name, "crunchy waffle cone", null, null);
        Item vanillaIceCream = new Item(Ice_Cream_Name, "vanilla ice cream", null, null);
        Item chocolateSprinkles = new Item(Sprinkles_Name, "chocolate sprinkles", null, null);
        
        // Create locations inside Patrick's rock
        Location livingRoom = new Location("Living Room", chocolateSprinkles); // sprinkles here
        Location kitchen = new Location("Kitchen", vanillaIceCream);          // ice cream here
        Location pantry = new Location("Pantry", waffleCone);                 // cone here
        Location closet = new Location("Closet", null);                       // empty for now
        
        // Link locations (navigation graph)
        livingRoom.addSubLocation(kitchen);
        livingRoom.addSubLocation(closet);

        kitchen.addSubLocation(livingRoom);
        kitchen.addSubLocation(pantry);

        pantry.addSubLocation(kitchen);

        closet.addSubLocation(livingRoom);
        
        // Add locations to list and register their names
        locationsList.add(livingRoom);
        locationNameList.add(livingRoom.getLocationName());

        locationsList.add(kitchen);
        locationNameList.add(kitchen.getLocationName());

        locationsList.add(pantry);
        locationNameList.add(pantry.getLocationName());

        locationsList.add(closet);
        locationNameList.add(closet.getLocationName());
        
        // Set starting location
        currLocation = livingRoom;
        
        // Story / objective text
        System.out.println("You've entered Patrick's Rock.\n");
        System.out.println("******** Objectives ********");
        System.out.println("Patrick is on the couch, bawling loudly while watching TV.");
        System.out.println("Patrick: 'Spongebob, I don't have my boating license.'");
        System.out.println("Spongebob decides to cheer him up by making him an ice cream cone.\n");
        System.out.println("Find the waffle cone, vanilla ice cream, and chocolate sprinkles,");
        System.out.println("then make the ice cream cone and give it to Patrick.\n");
        
        System.out.println("Useful Commands:");
        System.out.println("[1] or 'Search'");
        System.out.println("[2] <Location Name> or 'Move <Location Name>'");
        System.out.println("[3] or 'Take'");
        System.out.println("[4] or 'See Inventory'");
        System.out.println("'Make Ice Cream'");
        System.out.println("'Give Ice Cream Cone'\n");
    }

    /**
     * Checks if the room is complete.
     * This room is marked complete in giveIceCreamCone()
     * when the cone is successfully given to Patrick.
     */
    @Override
    public boolean checkCompletionOfRoom() {
        return isRoomComplete();
    }
    
    /**
     * Main loop for this room.
     * Handles searching, moving, picking up items, making the ice cream cone,
     * and giving it to Patrick.
     */
    @Override
    public void runRoom() {

        Scanner userInput = new Scanner(System.in);

        while (!isRoomComplete()) {

            System.out.println("\nYou are in the " + currLocation.getLocationName() + ".");
            System.out.println("What would you like to do?");
            System.out.println("[1] Search");
            System.out.println("[2] Move <Location>");
            System.out.println("[3] Take");
            System.out.println("[4] See Inventory");
            System.out.println("Or if you have your Ice Cream ingredients, type:");
            System.out.println("  'Make Ice Cream'  or  'Give Ice Cream Cone'");
            System.out.print("i> ");

            String userChoice = userInput.nextLine().trim();
            
            if (userChoice.equals("1") || userChoice.equalsIgnoreCase("Search")) {
                boolean found = currLocation.search();
                if (found && currLocation.hasItem()) {
                    System.out.println("Use [3] or 'Take' to pick up this item.");
                }
            }
            else if (userChoice.toLowerCase().startsWith("move") || userChoice.startsWith("2")) {
                moveMethod(userChoice);
            }
            else if (userChoice.equals("3") || userChoice.equalsIgnoreCase("Take")) {
                takeItem();
            }
            else if (userChoice.equals("4") || userChoice.equalsIgnoreCase("See Inventory")) {
                player.getInventory().printInventory();
            }
            else if (userChoice.equalsIgnoreCase("Make Ice Cream")) {
                makeIceCream();
            }
            else if (userChoice.equalsIgnoreCase("Give Ice Cream Cone")) {
                giveIceCreamCone();
            }
            else {
                System.out.println("That is not a valid choice. Try again!");
            }
        }
    }

    /**
     * Gives the ice cream cone to Patrick and completes the room.
     */
    private void giveIceCreamCone() {
        if (player.getInventory().contains(Ice_Cream_Cone_Name)) {
            player.getInventory().removeItem(Ice_Cream_Cone_Name);
            
            System.out.println("\nPatrick stops crying and looks at the ice cream in your hand.");
            System.out.println("He lights up and rushes towards you. He grabs the cone and");
            System.out.println("inhales it in one bite.");
            System.out.println("'Thanks Spongebob!! I feel so much better now.'");

            // Mark this room as complete (Room.completeRoom())
            completeRoom();

            System.out.println("\nPatrick is smiling again. You've completed this room.");
            System.out.println("You can now move on to the next location.");
        }
        else {
            System.out.println("You need to make the Ice Cream Cone before you give it to Patrick.");
        }
    }

    /**
     * Attempts to create the ice cream cone from the required ingredients.
     */
    private void makeIceCream() {

        boolean hasCone = player.getInventory().contains(Waffle_Cone_Name);
        boolean hasIceCream = player.getInventory().contains(Ice_Cream_Name);
        boolean hasSprinkles = player.getInventory().contains(Sprinkles_Name);
        
        if (hasCone && hasIceCream && hasSprinkles) {

            // Remove ingredients from inventory
            player.getInventory().removeItem(Waffle_Cone_Name);
            player.getInventory().removeItem(Ice_Cream_Name);
            player.getInventory().removeItem(Sprinkles_Name);
            
            // Create the ice cream cone item
            Item iceCreamCone = new Item(
                Ice_Cream_Cone_Name,
                "a delicious ice cream cone for Patrick",
                null,
                null
            );

            player.getInventory().addItem(iceCreamCone);

            System.out.println("You combined the ingredients and made an Ice Cream Cone!");
        } else {
            System.out.println("You don't have all the ingredients yet.");
            System.out.println("You still need a waffle cone, vanilla ice cream, and chocolate sprinkles.");
        }
    }
    
    /**
     * Handles picking up an item at the current location.
     */
    private void takeItem() {
        if (currLocation.hasItem()) {
            Item item = currLocation.getItems();
            player.getInventory().addItem(item);
            System.out.println(item.getName() + " was added to your inventory.");
            currLocation.removeItem(item.getName());
        } else {
            System.out.println("This location does not have anything to take.");
        }
    }
    
    /**
     * Handles player movement based on the input.
     *
     * @param userChoice the user’s input string
     */
    private void moveMethod(String userChoice) {

        String targetName = "";

        // If user typed "Move Kitchen"
        if (userChoice.toLowerCase().startsWith("move")) {
            if (userChoice.length() > 4) {
                targetName = userChoice.substring(4).trim();
            }
        }
        // If user typed "2 Kitchen"
        else if (userChoice.startsWith("2")) {
            if (userChoice.length() > 2) {
                targetName = userChoice.substring(2).trim();
            }
        }

        if (targetName.length() == 0) {
            System.out.println("Please specify a location name to move to.");
            return;
        }

        if (!locationNameList.contains(targetName)) {
            System.out.println("That location does not exist. Use 'Search' to see nearby locations.");
            return;
        }

        int index = locationNameList.indexOf(targetName);
        Location targetLocation = locationsList.get(index);

        // Check if target is reachable from current room
        if (currLocation.getsubLocations().contains(targetLocation)) {
            currLocation = targetLocation;
            System.out.println("You've entered the " + targetLocation.getLocationName() + ".");
        } else {
            System.out.println("You cannot move there from your current location.");
        }
    }
}
