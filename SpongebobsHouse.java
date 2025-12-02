/**
 * Class: SpongebobsHouse
 * Author: Andrew Albert (refactored with shared Room state)
 * Course: CSE 201
 *
 * Purpose:
 * Room 1 of the game. SpongeBob must find:
 *  - His Work Hat
 *  - Gary's Food
 */
public class SpongebobsHouse extends Room {

    public SpongebobsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }

    public SpongebobsHouse() {
        this("Spongebob's House", "The first level of the game!", false, null);
    }

    /**
     * Sets up locations and items inside SpongeBob’s house and prints intro text.
     */
    @Override
    public void enterRoom(Player player) {
        this.player = player;

        // Creating the items to collect
        Item hat = new Item("Work Hat", "Your work hat, you should put it on", null, null);
        Item food = new Item("Gary's Food", "Gary is hungry, feed it to him before you leave", null, null);

        // Creating all the rooms of the house
        Location bedroom = new Location("Bedroom", null);
        Location closet = new Location("Closet", hat);
        Location kitchen = new Location("Kitchen", food);
        Location livingroom = new Location("Livingroom", null);
        Location stairs = new Location("Staircase", null);
        Location bathroom = new Location("Bathroom", null);

        // Linking the locations
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

        // Register locations via helper
        addLocation(bedroom);
        addLocation(kitchen);
        addLocation(closet);
        addLocation(stairs);
        addLocation(livingroom);
        addLocation(bathroom);

        // Set starting location
        currLocation = bedroom;

        System.out.println("Good morning Spongebob! It's another exciting day in Bikini Bottom.");
        System.out.println("Before you can take on the world, you need to get ready.");
        System.out.println("Put on your Work Hat and feed Gary before moving on with your day.\n");
    }

    @Override
    public boolean checkCompletionOfRoom() {
        return player.getInventory().contains("Work Hat")
            && player.getInventory().contains("Gary's Food");
    }

    /**
     * Main loop for SpongeBob's house.
     * Handles searching, moving, taking items, and checking completion.
     */
    @Override
    public void runRoom() {

        while (!isRoomComplete()) {
            System.out.println("\nYou are in " + currLocation.getLocationName());
            System.out.println("What would you like to do?");
            System.out.println("1] Search");
            System.out.println("2] Move <Location Name>");
            System.out.println("3] Take");
            System.out.println("4] See Inventory");
            System.out.println("5] Quit this room");
            System.out.print("> ");

            String choice = input.nextLine().trim();

            switch (choice.toLowerCase()) {
            case "1":
            case "search":
                currLocation.search();
                break;

            case "3":
            case "take":
                if (currLocation.hasItem()) {
                    Item item = currLocation.getItems();
                    player.getInventory().addItem(item);
                    currLocation.removeItem(item.getName());
                } else {
                    System.out.println("This location doesn't have anything to take.");
                }
                break;

            case "4":
            case "see inventory":
                player.getInventory().printAllItemDescriptions();
                break;

            case "5":
            case "quit":
                System.out.println("You leave SpongeBob's house for now.");
                return;

            default:
                // Check for movement commands
                if (choice.toLowerCase().startsWith("move") || choice.startsWith("2")) {
                    handleMoveCommand(choice);
                } else {
                    System.out.println("That is not a valid choice. Try again!");
                }
                break;
            }

            if (checkCompletionOfRoom()) {
                System.out.println("Spongebob feeds Gary his food and puts on his trusty hat "
                        + "before heading off to begin the day!");
                player.getInventory().removeItem("Gary's Food");
                completeRoom();
                System.out.println("\nType 'next' to travel to Squidward's Tiki House.");
                return;
            }
        }
    }
}
