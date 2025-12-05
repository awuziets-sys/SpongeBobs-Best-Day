import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: KrustyKrab 
 * @version 1.0
 * @author Talan Malicote 
 * Course: CSE 201
 * 
 * Purpose:
 * This class represents the final room of the "SpongeBob's Best Day Ever" game.
 * SpongeBob arrives at the Krusty Krab to serve his friends, but the spatula
 * he needs to make Krabby Patties is missing. The player must explore different
 * locations within the Krusty Krab (Kitchen, Fryer, Grill, Freezer Door, Counter)
 * to find the spatula and then complete a trivia-based challenge to unlock the
 * Golden Spatula. Completing this room marks the end of the game.
 */
public class KrustyKrab extends Room {

    // List of all locations in the Krusty Krab
    private List<Location> locationsList = new ArrayList<>();

    // Parallel list of names to help parse movement commands
    private List<String> locationNameList = new ArrayList<>();

    // Reference to the player in this room
    private Player player;

    // Current location SpongeBob is in
    private Location currLocation;

    // Shared Scanner for this room (do NOT close System.in)
    private Scanner input = new Scanner(System.in);

    // Item names used in this room
    private static final String SPATULA = "Spatula";
    private static final String GOLDEN_SPATULA = "Golden Spatula";

    /**
     * Default constructor, sets room name/description.
     */
    public KrustyKrab() {
        super("Krusty Krab",
              "The final room! Find the missing spatula and serve your friends Krabby Patties!",
              false,
              null);
    }

    /**
     * Called when the player first arrives at the Krusty Krab.
     * Sets up locations, items, and prints intro story.
     */
    @Override
    public void enterRoom(Player player) {
        this.player = player;

        // Items in the kitchen
        Item spatula = new Item(SPATULA, "A regular spatula used for making Krabby Patties", null, null);

        // Locations
        Location kitchen = new Location("Kitchen", null);
        Location fryer = new Location("Fryer", null);
        Location grill = new Location("Grill", null);
        Location freezer = new Location("Freezer Door", spatula); // The normal spatula is here
        Location counter = new Location("Counter", null);

        // Link locations (navigation graph)
        kitchen.addSubLocation(fryer);
        kitchen.addSubLocation(grill);
        kitchen.addSubLocation(counter);

        fryer.addSubLocation(kitchen);
        grill.addSubLocation(kitchen);
        counter.addSubLocation(kitchen);

        // Freezer door is reachable from the counter
        freezer.addSubLocation(counter);
        counter.addSubLocation(freezer);

        // Register locations and their names
        locationsList.add(kitchen);
        locationNameList.add(kitchen.getLocationName());

        locationsList.add(fryer);
        locationNameList.add(fryer.getLocationName());

        locationsList.add(grill);
        locationNameList.add(grill.getLocationName());

        locationsList.add(freezer);
        locationNameList.add(freezer.getLocationName());

        locationsList.add(counter);
        locationNameList.add(counter.getLocationName());

        // Starting location
        currLocation = kitchen;

        System.out.println("\nWelcome to the Krusty Krab!");
        System.out.println("All of your friends are here, waiting for fresh Krabby Patties.");
        System.out.println("But your spatula is missing! Search around the kitchen to find it.\n");
        System.out.println("Commands: [1] Search, [2] Move <Location>, [3] Take,");
        System.out.println("          [4] See Inventory, [5] Quit, 'Open Door'");
    }

    /**
     * The final room is complete once the Golden Spatula is obtained
     * and completeRoom() has been called.
     */
    @Override
    public boolean checkCompletionOfRoom() {
        return isRoomComplete();
    }

    /**
     * Main loop for the Krusty Krab.
     * Handles searching, moving, taking items, checking inventory,
     * and triggering the quiz-door challenge.
     */
    @Override
    public void runRoom() {

        while (!isRoomComplete()) {

            System.out.println("\nYou are in the " + currLocation.getLocationName());
            System.out.println("What would you like to do?");
            System.out.println("[1] Search");
            System.out.println("[2] Move <Location>");
            System.out.println("[3] Take");
            System.out.println("[4] See Inventory");
            System.out.println("[5] Quit");
            System.out.println("'Open Door' (if you think you've found something special)");

            System.out.print("> ");
            String choice = input.nextLine().trim();

            if (choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("Search")) {
                boolean found = currLocation.search();
                if (found && currLocation.hasItem()) {
                    System.out.println("Use [3] or 'Take' to pick up this item.");
                }
            }
            else if (choice.toLowerCase().startsWith("move") || choice.startsWith("2")) {
                moveMethod(choice);
            }
            else if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("Take")) {
                takeItem();
            }
            else if (choice.equalsIgnoreCase("4") || choice.equalsIgnoreCase("See Inventory")) {
                player.getInventory().printInventory();
            }
            else if (choice.equalsIgnoreCase("5") || choice.equalsIgnoreCase("Quit")) {
                System.out.println("You chose to stop exploring the Krusty Krab.");
                return; // Return control to Game
            }
            else if (choice.equalsIgnoreCase("Open Door")) {
                openDoorChallenge();
            }
            else {
                System.out.println("Invalid command. Try again!");
            }
        }
    }

    /**
     * Handles moving between locations based on user input.
     *
     * @param userChoice the raw input string from the user
     */
    private void moveMethod(String userChoice) {
        String targetName = "";

        // "Move Grill"
        if (userChoice.toLowerCase().startsWith("move")) {
            if (userChoice.length() > 4) {
                targetName = userChoice.substring(4).trim();
            }
        }
        // "2 Grill"
        else if (userChoice.startsWith("2")) {
            if (userChoice.length() > 2) {
                targetName = userChoice.substring(2).trim();
            }
        }

        if (targetName.isEmpty()) {
            System.out.println("Specify a location to move to.");
            return;
        }

        if (!locationNameList.contains(targetName)) {
            System.out.println("That location does not exist. Use 'Search' to see nearby locations.");
            return;
        }

        int index = locationNameList.indexOf(targetName);
        Location target = locationsList.get(index);

        if (currLocation.getsubLocations().contains(target)) {
            currLocation = target;
            System.out.println("You moved to " + currLocation.getLocationName() + ".");
        } else {
            System.out.println("You cannot move there from your current location.");
        }
    }

    /**
     * Handles picking up an item from the current location.
     */
    private void takeItem() {
        if (currLocation.hasItem()) {
            Item item = currLocation.getItems();
            player.getInventory().addItem(item);
            System.out.println(item.getName() + " added to your inventory.");
            currLocation.removeItem(item.getName());
        } else {
            System.out.println("There is nothing to take here.");
        }
    }

    /**
     * Triggers the Golden Spatula door challenge.
     * Requires:
     *  - The player to be standing at the Freezer Door
     *  - The player to have already found the regular spatula
     * If those are satisfied, the player must then answer 5 trivia
     * questions correctly to obtain the Golden Spatula.
     */
    private void openDoorChallenge() {

        // Must be at the Freezer Door to find the hidden door
        if (!currLocation.getLocationName().equalsIgnoreCase("Freezer Door")) {
            System.out.println("You look around, but there's no hidden door here.");
            System.out.println("Maybe try at the Freezer Door...");
            return;
        }

        // Must have the basic spatula first
        if (!player.getInventory().contains(SPATULA)) {
            System.out.println("You feel around the freezer but nothing happens.");
            System.out.println("Maybe you need your regular spatula first.");
            return;
        }

        System.out.println("\nYou notice a strange outline behind the freezer shelves...");
        System.out.println("Using your spatula, you pry open a hidden door!");
        System.out.println("To open it fully and reach the Golden Spatula, you must answer 5 questions correctly.\n");

        String[] questions = {
            "Question 1: What color is SpongeBob? (Yellow/Blue/Red) ",
            "Question 2: How many legs does Patrick have? (2/6/8) ",
            "Question 3: What is Mr. Krabs' favorite thing? (Money/Boats/Patty) ",
            "Question 4: Which sport does Sandy enjoy? (Karate/Football/Swimming) ",
            "Question 5: What instrument does Squidward play? (Clarinet/Piano/Drums) "
        };

        String[] answers = {
            "yellow",
            "2",
            "money",
            "karate",
            "clarinet"
        };

        for (int i = 0; i < questions.length; i++) {
            System.out.print(questions[i]);
            String ans = input.nextLine().trim().toLowerCase();

            if (!ans.equals(answers[i])) {
                System.out.println("Wrong answer! The door slams shut and the challenge resets.");
                System.out.println("You can try again by typing 'Open Door' when you're ready.");
                return; // End this challenge attempt
            }
        }

        // All correct → player gets Golden Spatula
        Item goldenSpatula = new Item(
            GOLDEN_SPATULA,
            "The ultimate spatula to make Krabby Patties!",
            null,
            null
        );

        player.getInventory().addItem(goldenSpatula);

        System.out.println("\nYou unlocked the hidden door and obtained the Golden Spatula!");
        System.out.println("Back in the kitchen, you flip Krabby Patties with incredible style.");
        System.out.println("All your friends cheer as you serve them the best patties ever.");
        System.out.println("This truly is SpongeBob's Best Day Ever!");

        // Mark this room done
        completeRoom();
        System.out.println("\nType 'next' to continue.");
    }
}
