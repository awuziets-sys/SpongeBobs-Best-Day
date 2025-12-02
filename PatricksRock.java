/**
 * Class: PatricksRock
 * Author: Sam Adeyemo (refactored)
 * Course: CSE 201
 * 
 * Purpose:
 * Room 3 of the game. SpongeBob must cheer Patrick up by making him
 * an ice cream cone. The player explores locations inside Patrick's rock
 * to find the required ingredients, then makes and gives the cone to Patrick.
 */
public class PatricksRock extends Room {

    // The ingredients needed
    private static final String WAFFLE_CONE_NAME   = "Waffle Cone";
    private static final String ICE_CREAM_NAME     = "Vanilla Ice Cream";
    private static final String SPRINKLES_NAME     = "Chocolate Sprinkles";
    private static final String ICE_CREAM_CONE_NAME= "Ice Cream Cone";

    public PatricksRock(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }

    public PatricksRock() {
        this("Patrick's Rock",
             "Patrick is crying on his couch. Make him an ice cream cone to cheer him up.",
             false,
             null);
    }

    @Override
    public void enterRoom(Player player) {
        this.player = player;

        // Create required items
        Item waffleCone       = new Item(WAFFLE_CONE_NAME, "crunchy waffle cone", null, null);
        Item vanillaIceCream  = new Item(ICE_CREAM_NAME, "vanilla ice cream", null, null);
        Item chocolateSprinkles = new Item(SPRINKLES_NAME, "chocolate sprinkles", null, null);

        // Create locations
        Location livingRoom = new Location("Living Room", chocolateSprinkles);
        Location kitchen    = new Location("Kitchen", vanillaIceCream);
        Location pantry     = new Location("Pantry", waffleCone);
        Location closet     = new Location("Closet", null);

        // Link locations
        livingRoom.addSubLocation(kitchen);
        livingRoom.addSubLocation(closet);

        kitchen.addSubLocation(livingRoom);
        kitchen.addSubLocation(pantry);

        pantry.addSubLocation(kitchen);
        closet.addSubLocation(livingRoom);

        // Register locations
        addLocation(livingRoom);
        addLocation(kitchen);
        addLocation(pantry);
        addLocation(closet);

        // Set starting location
        currLocation = livingRoom;

        // Intro story
        System.out.println("You've entered Patrick's Rock.\n");
        System.out.println("******** Objectives ********");
        System.out.println("Patrick is on the couch, bawling loudly while watching TV.");
        System.out.println("Patrick: 'Spongebob, I don't have my boating license.'");
        System.out.println("Spongebob decides to cheer him up by making him an ice cream cone.\n");
        System.out.println("Find the waffle cone, vanilla ice cream, and chocolate sprinkles,");
        System.out.println("then make the ice cream and give it to Patrick.\n");

        System.out.println("Useful Commands:");
        System.out.println("[1] or 'Search'");
        System.out.println("[2] <Location Name> or 'Move <Location Name>'");
        System.out.println("[3] or 'Take'");
        System.out.println("[4] or 'See Inventory'");
        System.out.println("'Make Ice Cream'");
        System.out.println("'Give Ice Cream Cone'\n");
    }

    @Override
    public boolean checkCompletionOfRoom() {
        return isRoomComplete();
    }

    @Override
    public void runRoom() {

        while (!isRoomComplete()) {
            System.out.println("\nYou are in the " + currLocation.getLocationName() + ".");
            System.out.println("What would you like to do?");
            System.out.println("[1] Search");
            System.out.println("[2] Move <Location>");
            System.out.println("[3] Take");
            System.out.println("[4] See Inventory");
            System.out.println("Or, if you have your ingredients:");
            System.out.println("  'Make Ice Cream' or 'Give Ice Cream Cone'");
            System.out.print("i> ");

            String userChoice = input.nextLine().trim();

            if (userChoice.equals("1") || userChoice.equalsIgnoreCase("Search")) {
                boolean found = currLocation.search();
                if (found && currLocation.hasItem()) {
                    System.out.println("Use [3] or 'Take' to pick up this item.");
                }
            }
            else if (userChoice.toLowerCase().startsWith("move") || userChoice.startsWith("2")) {
                handleMoveCommand(userChoice);
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

    private void giveIceCreamCone() {
        if (player.getInventory().contains(ICE_CREAM_CONE_NAME)) {
            player.getInventory().removeItem(ICE_CREAM_CONE_NAME);

            System.out.println("\nPatrick stops crying and looks at the ice cream in your hand.");
            System.out.println("He lights up and rushes towards you. He grabs the cone and");
            System.out.println("inhales it in one bite.");
            System.out.println("'Thanks Spongebob!! I feel so much better now.'");

            completeRoom();
            System.out.println("\nType 'next' to travel to Sandy's Tree Dome.");
        } else {
            System.out.println("You need to make the Ice Cream Cone before you give it to Patrick.");
        }
    }

    private void makeIceCream() {
        boolean hasCone      = player.getInventory().contains(WAFFLE_CONE_NAME);
        boolean hasIceCream  = player.getInventory().contains(ICE_CREAM_NAME);
        boolean hasSprinkles = player.getInventory().contains(SPRINKLES_NAME);

        if (hasCone && hasIceCream && hasSprinkles) {
            player.getInventory().removeItem(WAFFLE_CONE_NAME);
            player.getInventory().removeItem(ICE_CREAM_NAME);
            player.getInventory().removeItem(SPRINKLES_NAME);

            Item iceCreamCone = new Item(
                ICE_CREAM_CONE_NAME,
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
}
