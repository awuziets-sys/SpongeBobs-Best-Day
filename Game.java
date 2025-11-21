import java.util.Scanner;

/**
 * Class: Game
 * @author Sam Adeyemo, Testimony Awuzie, Andrew ALbert
 * @version 1.0
 * Course : CSE 201 Fall 2025
 * Written: November 19, 2025
 *
 * Purpose: This class will control the flow of the game. It manages the player,
 * Keeps track of the current room, and handle the transition between the rooms.
 * 
 */
public class Game {
	private Room currentRoom; // Tracks the current room SpongeBob is in
	private Player player; // The player object
	private boolean isGameRunning; // Keeps the game active or inactive
	private Scanner userInput;	// Reading the user input
	
	/**
	 * Constructor — Initializes scanner and default state.
	 */
	public Game() {
		this.isGameRunning = false;
		this.userInput = new Scanner(System.in);
		setupGame();
	}
	
	/**
     * Sets up all rooms, links them in order, and initializes player.
     */
	private void setupGame() {
		System.out.println("Initializing SpongeBob’s Best Day Ever...\n");
		Inventory inventory = new Inventory();

        // Room object declarations
		 Room spongebobsHouse = new SpongebobsHouse();
//		 Room squidwardsHouse = new SquidwardsHouse();
//		 Room patricksRock = new PatricksRock();
//		 Room sandysDome = new SandysDome();
//		 Room krustyKrab = new KrustyKrab();
//
//		// Link rooms in order of progression
//		 spongebobsHouse.setNextRoom(squidwardsHouse);
//		 squidwardsHouse.setNextRoom(patricksRock);
//		 patricksRock.setNextRoom(sandysDome);
//		 sandysDome.setNextRoom(krustyKrab);
//		 krustyKrab.setNextRoom(null);
		
        // Set starting point for the game   
		this.currentRoom = spongebobsHouse;
		this.player = new Player(currentRoom, inventory, "001", "SpongeBob"); // initialize the player
		
	}

	/**
	 * This function starts the game by making the playing and loading them 
	 * into the first room. It prints an introduction and then the main game
	 * loop begins
	 */
	public void startGame() {	
		printIntro();
		isGameRunning = true; // Starting the main loop

		System.out.println("\nWould you like to begin the game and enter SpongeBob’s House?");
        System.out.println("Type 'enter' to begin, or 'quit' to end the game.");

        while (isGameRunning) {
            System.out.print("> ");
            String command = userInput.nextLine().trim().toLowerCase();

            handleCommand(command);        
		}	
	}
	
	/**
	 * Handles Player Commands
	 * @param command
	 */
	private void handleCommand(String command) {
		switch (command) {
        case "enter":
            enterRoom();
            break;
        case "next":
            moveToNextRoom();
            break;
        case "progress":
            checkProgress();
            break;
        case "inventory":
            checkInventory();
            break;
        case "describe":
            describeItems();
            break;
        case "help":
            printHelp();
            break;
        case "quit":
            endGame();
            break;
        default:
            System.out.println("Unknown command. Type 'help' for a list of commands.");
            break;
		}
	}

	/**
     * Prints the list of available commands.
     */
    private void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println(" enter     - Enter the current room");
        System.out.println(" next      - Move to the next room (if current is complete)");
        System.out.println(" progress  - Check current room progress");
        System.out.println(" inventory - Check items in SpongeBob’s inventory");
        System.out.println(" describe  - Get item descriptions");
        System.out.println(" help      - Show this help menu");
        System.out.println(" quit      - End the game\n");
    }
	
	/**
	 * 
	 */
	private void printIntro() {
		System.out.println("Welcome to SpongeBob's Best Day Ever!");
        System.out.println("----------------------------------------------------------");
		System.out.println("Spongebob's end goal is to celebrate with everyone at the Krusty Krab.\n"
				+ "He has to go to multiple locations in Bikini Bottom, "
				+ "completing different tasks for his friends.\n"
				+ "At the final location, if he completes his tasks successfully, he wins.\n"
				+ "Help him have his Best Day Ever!");
		
        System.out.println("\nType 'help' to view all commands.");
	}
	
	/**
	 * 
	 */
	public void moveToNextRoom() {
		if (currentRoom != null && currentRoom.isRoomComplete()) {
			Room next = currentRoom.getNextRoom();
	        if (next == null) {
	            System.out.println("\nAll rooms complete! Congratulations on completing the game!");
	            endGame();
	        } else {
	            System.out.println("\nMoving to Next Room: " + next.getRoomName());
	            currentRoom = next;
	        }   
        } else {
        	System.out.println("You cannot move yet! Finish this room first.");
        }
	}
	
	/**
	 * Prints SpongeBob's Current Inventory
	 */
	private void checkInventory() {
        System.out.println("\nChecking inventory...");
        player.getInventory().printInventory();
    }
	
	/**
	 * Triggers the Logic inside the Current Room
	 * Entering the Room and displays it
	 */
	public void enterRoom() {
		if (currentRoom == null) {
            System.out.println("There are no rooms available!");
            return;
        }

        System.out.println("\nEntering " + currentRoom.getRoomName() + "...");
        currentRoom.enterRoom(player);
        currentRoom.runRoom(); // Each room implements its own logic
    }
	
	/**
     * Prints item descriptions (if available).
     */
    private void describeItems() {
        System.out.println("\nItem Descriptions:");
        player.getInventory().printAllItemDescriptions();
    }
    
	/**
	 * Checks the Progress of the Current Room the Player is in
	 */
	public void checkProgress() {
		System.out.println("/nGame Progress");
		System.out.println("Checking Room Progress...");
		if (currentRoom.checkCompletionOfRoom()) {
			System.out.println("Room is Complete. Well Done!");
		} else {
			System.out.println("There are Tasks left in This Room.");
		}
	}
	
	/**
	 * Displays a Thank you Message if the player finishes or stops the game
	 */
	public void endGame() {
		System.out.println("Thank you for playing SpongeBob's Best Day Ever!");
		this.isGameRunning = false;
		userInput.close();	
	}
}
