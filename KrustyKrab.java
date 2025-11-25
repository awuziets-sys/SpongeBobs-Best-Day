import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: KrustyKrab 
 * Version: 1.0
 * Author: Talan Malicote 
 * Course: CSE 201
 * 
 * Purpose: This class represents the final room of the "SpongeBob's Best Day Ever" game.
 * SpongeBob arrives at the Krusty Krab to serve his friends, but the spatula
 * he needs to make Krabby Patties is missing. The player must explore different
 * locations within the Krusty Krab (Kitchen, Fryer, Grill, Freezer Door, Counter)
 * to find the spatula and then complete a trivia-based challenge to unlock the
 * Golden Spatula. Completing this room marks the end of the game.
 */
public class KrustyKrab extends Room {

	private List<Location> locationsList = new ArrayList<>();
	private List<String> locationNameList = new ArrayList<>();

	private Player player;
	private Location currLocation;

	private static final String SPATULA = "Spatula";
	private static final String GOLDEN_SPATULA = "Golden Spatula";

	public KrustyKrab() {
		super("Krusty Krab", "The final room! Find the missing spatula and serve your friends Krabby Patties!", false,null);
	}

	@Override
	public void enterRoom(Player player) {
		this.player = player;

		// Items in the kitchen
		Item spatula = new Item(SPATULA, "A regular spatula used for making Krabby Patties", null, null);

		// Locations
		Location kitchen = new Location("Kitchen", null);
		Location fryer = new Location("Fryer", null);
		Location grill = new Location("Grill", null);
		Location freezer = new Location("Freezer Door", spatula); // the spatula is in the freezer
		Location counter = new Location("Counter", null);

		// Link locations
		kitchen.addSubLocation(fryer);
		kitchen.addSubLocation(grill);
		kitchen.addSubLocation(counter);

		fryer.addSubLocation(kitchen);
		grill.addSubLocation(kitchen);
		counter.addSubLocation(kitchen);
		freezer.addSubLocation(counter);
		counter.addSubLocation(freezer);

		// Add to lists
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

		System.out.println("Welcome to the Krusty Krab! Your friends are waiting for Krabby Patties.");
		System.out.println("Oh no! Your spatula is missing! Search around the kitchen to find it.");
		System.out.println(
				"Commands: [1] Search, [2] Move <Location>, [3] Take, [4] See Inventory, [5] Quit, 'Open Door'");
	}

	@Override
	public boolean checkCompletionOfRoom() {
		return isRoomComplete();
	}

	@Override
	public void runRoom() {
		Scanner userInput = new Scanner(System.in);

		while (!isRoomComplete()) {
			System.out.println("\nYou are in the " + currLocation.getLocationName());
			System.out.println("What would you like to do?");
			System.out.println("[1] Search\n[2] Move <Location>\n[3] Take\n[4] See Inventory\n[5] Quit\n'Open Door'");

			System.out.print("> ");
			String choice = userInput.nextLine().trim();

			if (choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("Search")) {
				boolean found = currLocation.search();
				if (found && currLocation.hasItem()) {
					System.out.println("Use [3] or 'Take' to pick up this item.");
				}
			} else if (choice.toLowerCase().startsWith("move") || choice.startsWith("2")) {
				moveMethod(choice);
			} else if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("Take")) {
				takeItem();
			} else if (choice.equalsIgnoreCase("4") || choice.equalsIgnoreCase("See Inventory")) {
				player.getInventory().printInventory();
			} else if (choice.equalsIgnoreCase("5") || choice.equalsIgnoreCase("Quit")) {
				System.out.println("You chose to quit the Krusty Krab.");
				return;
			} else if (choice.equalsIgnoreCase("Open Door")) {
				openDoorChallenge();
			} else {
				System.out.println("Invalid command. Try again!");
			}
		}
	}

	private void moveMethod(String userChoice) {
		String targetName = "";

		if (userChoice.toLowerCase().startsWith("move")) {
			if (userChoice.length() > 4)
				targetName = userChoice.substring(4).trim();
		} else if (userChoice.startsWith("2")) {
			if (userChoice.length() > 2)
				targetName = userChoice.substring(2).trim();
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
			System.out.println("Cannot move there from current location.");
		}
	}

	private void takeItem() {
		if (currLocation.hasItem()) {
			Item item = currLocation.getItems();
			player.getInventory().addItem(item);
			System.out.println(item.getName() + " added to your inventory.");
			currLocation.removeItem(item.getName());
		} else {
			System.out.println("Nothing to take here.");
		}
	}

	private void openDoorChallenge() {
		if (!player.getInventory().contains(SPATULA)) {
			System.out.println("You need a regular spatula first to open the hidden door.");
			return;
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("To open the hidden door and get the Golden Spatula, answer 5 questions correctly!");

		String[] questions = { "Question 1: What color is SpongeBob? (Yellow/Blue/Red) ",
				"Question 2: How many legs does Patrick have? (4/6/8) ",
				"Question 3: What is Mr. Krabs' favorite thing? (Money/Boats/Patty) ",
				"Question 4: Which sport does Sandy enjoy? (Karate/Football/Swimming) ",
				"Question 5: What's Squidward's instrument? (Clarinet/Piano/Drums) " };

		String[] answers = { "yellow", "4", "money", "karate", "clarinet" };

		for (int i = 0; i < questions.length; i++) {
			System.out.print(questions[i]);
			String ans = scanner.nextLine().trim().toLowerCase();
			if (!ans.equals(answers[i])) {
				System.out.println("Wrong answer! The challenge restarts.");
				return; // restart challenge
			}
		}

		// All correct → player gets Golden Spatula
		Item goldenSpatula = new Item(GOLDEN_SPATULA, "The ultimate spatula to make Krabby Patties!", null, null);
		player.getInventory().addItem(goldenSpatula);
		System.out.println("You unlocked the hidden door and obtained the Golden Spatula!");
		System.out.println(
				"Congratulations! You can now make Krabby Patties for your friends and celebrate your Best Day Ever!");
		completeRoom();
	}
}
