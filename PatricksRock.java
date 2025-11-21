import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: PatricksRock
 * Author: Sam Adeyemo
 * Course: CSE 201
 * 
 * Purpose: Room 3 of the game. SpongeBob must cheer Patrick up by making him
 * an ice cream cone. Throughout this level, the player will go through different
 * locations to find the required ingredients to make the cone, and then give it
 * to Patrick.
 */
public class PatricksRock extends Room {
	// the list of the locations inside Patricks rock
	private List<Location> locationsList = new ArrayList<Location>();
	private List<String> locationNameList = new ArrayList<String>();
	
	private Player player;
	private Location currLocation;
	// the ingredients needed for the icecream names
	private static final String Waffle_Cone_Name = "Waffle Cone";
	private static final String Ice_Cream_Name = "Vanilla Ice Cream";
	private static final String Sprinkles_Name = "Chocolate Sprinkles";
	private static final String Ice_Cream_Cone_Name = "Ice Cream Cone";
	
	/**
	 * 
	 * @param roomName the room name
	 * @param roomDescription room description
	 * @param roomComplete true if the room's complete, else false
	 * @param nextRoom moves to the next room
	 */
	public PatricksRock(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
		super(roomName, roomDescription, roomComplete, nextRoom);
	}
	
	public PatricksRock() {
		this("Patrick's Rock", "Patrick is crying on his couch. Make him an"
				+ "Ice Cream Cone to cheer him up.", false, null);
	}
	/**
	 * overrides when the player first enters patricks rock the sets the
	 * location, the items and the story.
	 * @param player Spongebob
	 */
	@Override
	public void enterRoom(Player player) {
		this.player = player;
		// making the required items
		Item waffleCone = new Item(Waffle_Cone_Name, "crunchy waffle cone", null, null);
		Item vanillaIceCream = new Item(Ice_Cream_Name, "vanilla ice cream", null, null);
		Item chocolateSprinkles = new Item(Sprinkles_Name, "chocolate sprinkles", null, null);
		
		// locations inside patricks house
		Location livingRoom = new Location("Living Room", chocolateSprinkles);
		Location kitchen = new Location("Kitchen", vanillaIceCream);
		Location pantry = new Location("Pantry", waffleCone);
		Location closet = new Location("Closet", null);
		
		//linking locations
		livingRoom.addSubLocation(kitchen);
		livingRoom.addSubLocation(closet);
		kitchen.addSubLocation(livingRoom);
		kitchen.addSubLocation(pantry);
		pantry.addSubLocation(kitchen);
		closet.addSubLocation(livingRoom);
		
		//adding the locations to the list
		locationsList.add(livingRoom);
		locationNameList.add(livingRoom.getLocationName());
		locationsList.add(kitchen);
		locationNameList.add(kitchen.getLocationName());
		locationsList.add(pantry);
		locationNameList.add(pantry.getLocationName());
		locationsList.add(closet);
		locationNameList.add(closet.getLocationName());
		
		// the starting location
		currLocation = livingRoom;
		
		//starting story description for patricks room
		System.out.println("You've entered Patrick's Rock.\n");
		System.out.println("******** Objectives ********");
		System.out.println("Patrick is on the couch, bawling loudly while watching TV.");
		System.out.println("'Spongebob, I don't have my boating license.' \nYou"
				+ " decide to cheer him up by making him an ice cream cone. \n"
				+ "\nFind the waffle cone, vanilla ice cream, and chocolate sprinkles"
				+ " then make the ice cream and give it to Patrick.");
		
		System.out.println("Useful Commands: \n"
				+ "[1] or 'Search'\n[2] <Location Name> or Move <Location Name>\n"
				+ "[3] or 'Take'\n[4] or 'See Inventory'\n'Make Ice Cream'\n"
				+ "'Give Ice Cream Cone'\n");
		
	}
	/**
	 * checks if the room is complete. The room is completed after the ice 
	 * cream cone has been given to Patrick.
	 */
	@Override
	public boolean checkCompletionOfRoom() {
		 return isRoomComplete();
	}
	
	/**
	 * main loop for this room and will handle things like searching, moving,
	 * taking items, making the ice cream cone and giving it to patrick.
	 */
	@Override
	public void runRoom() {
		Scanner userInput = new Scanner(System.in);
		
		while (!isRoomComplete()) {
			System.out.println("\nYou are in the " + currLocation.getLocationName() + ".");
			System.out.println("What would you like to do?");
			System.out.println("[1] Search\n[2] Move <Location>\n[3] Take"
					+ "\n[4] See Inventory\nOr if you have your Ice Cream ingredients,"
					+ " type: 'Make Ice Cream' or 'Give Ice Cream Cone'");
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
				//System.out.println("\n You are holding: " + player.getInventory());
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
		userInput.close();
	}

	/**
	 * gives the ice cream cone to patrick and completes the room.
	 */
	private void giveIceCreamCone() {
		if (player.getInventory().contains(Ice_Cream_Cone_Name)) {
			player.getInventory().removeItem(Ice_Cream_Cone_Name);
			
			System.out.println("\nPatrick stops crying and looks at the Ice Cream in your hand.\n"
					+ "He lights up and rushes towards you. He grabs the cone and"
					+ " inhales it in one bite.\n"
					+ "'Thanks Spongebob!! I feel so much better now.'");
			completeRoom();
		}
		else {
			System.out.println("You need to make the Ice Cream Cone before you "
					+ "give it to Patrick.");
		}
	}

	/**
	 * makes the ice cream with the 3 required ingredients
	 */
	private void makeIceCream() {
		boolean hasCone = player.getInventory().contains(Waffle_Cone_Name);
		boolean hasIceCream = player.getInventory().contains(Ice_Cream_Name);
		boolean hasSprinkles = player.getInventory().contains(Sprinkles_Name);
		
		if (hasCone && hasIceCream && hasSprinkles) {
			player.getInventory().removeItem(Waffle_Cone_Name);
			player.getInventory().removeItem(Ice_Cream_Name);
			player.getInventory().removeItem(Sprinkles_Name);
			
			//making the ice cream cone
			Item iceCreamCone = new Item (Ice_Cream_Cone_Name, "a delicious ice"
					+ " cream cone for Patrick", null, null);
			player.getInventory().addItem(iceCreamCone);
			System.out.println("You combined the ingredients and made an Ice Cream Cone!");
		}else {
			System.out.println("You don't have all the ingredients yet.");
			System.out.println("You still need a waffle cone, vanilla ice cream,"
					+ " and chocolate sprinkles.");
		}
	}
	
	/**
	 * handles picking up an item at the current location
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
	 * handles the player movement based on the input
	 * @param userChoice the user choice that they input
	 */
	private void moveMethod(String userChoice) {
		String targetName = "";
		// if user typed move "kitchen/closet"
		if (userChoice.toLowerCase().startsWith("move")) {
			if (userChoice.length() > 4) {
				targetName = userChoice.substring(4).trim();
			}
		}
		// if user type "2 kitchen/closet"
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
			System.out.println("That location does not exist. Use 'Search'"
					+ " to see nearby locations.");
			return;
		}
		int index = locationNameList.indexOf(targetName);
		Location targetLocation = locationsList.get(index);
		if (currLocation.getsubLocations().contains(targetLocation)) {
			currLocation = targetLocation;
			System.out.println("You've entered the " + targetLocation.getLocationName() + ".");
		} else {
			System.out.println("You cannot move there from your current location.");
		}
	}
	
}

