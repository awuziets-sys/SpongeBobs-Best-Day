import java.util.Scanner;

/**
 * Class: Game
* @author Sam Adeyemo
* @version 1.0
* Course : CSE 201 Fall 2025
* Written: November 5, 2025
*
* Purpose: Thic clss will control the flow of the game. It manages the player,
* keeps track of the current room, and handle the transition between the rooms.
* 
 */
public class Game {
	
	private Room currentRoom; // tracks the current room SpongeBob is in
	private Player player; // the player object
	private boolean isGameRunning; // keeps the game active or inactive
	private Scanner userInput;	// reading the user input
	
	/**
	 *  this function is initialize the scanner and the initial game state
	 */
	public Game() {
		this.isGameRunning = false;
		this.userInput = new Scanner(System.in);
		startGame();
	}
	
	/**
	 * This function starts the game by making the playing and loading them 
	 * into the first room. It prints an introduction and then the main game
	 * loop begins
	 */
	public void startGame() {
		
		System.out.println("Thank you for playing SpongeBob's Best Day Ever!");
		System.out.println("Help SpongeBob complete his task and make it to the "
				+ "Krusty Krab.");
		
		Inventory inventory = new Inventory(); //new empty inventory for the  user
		
		// TODO: replace with the actual room sequence once all the roomsubclasses are done.
		// should be like this:
		Room spongebobsHouse = new SpongebobsHouse();
		// Room squidwardsHouse = new SquidwardsHouse();
		// Room patricksRock = new PatricksRock();
		
		
		// link rooms in order of progression
		// spongebobsHouse.setNextRoom(squidwardsHouse);
		// squidwardsHouse.setNextRoom(patricksRock);
		
		
		// Starting point for the game
		this.currentRoom = spongebobsHouse;
		
		this.player = new Player(currentRoom, inventory, "001", "SpongeBob"); // initialize the player
		this.isGameRunning = true; //starting the main loop
		runGameLoop();		
	}
	
	
	/**
	 * the main loop of the game and runs until the player decides to stop
	 */
	private void runGameLoop() {
		while (isGameRunning) {
			System.out.println("Current Room: " + currentRoom.getRoomName());
			currentRoom.enterRoom(player);
			currentRoom.runRoom();
			moveToNextRoom();
		}
	}
	
	public void moveToNextRoom() {
		if (currentRoom.getNextRoom() != null) {
			currentRoom = currentRoom.getNextRoom();
			System.out.println("You are moving to room: " + currentRoom.getRoomName() + ".");
		} else {
			System.out.println("There are no more rooms. Congratulations on completing the game!");
			endGame();
		}
	}
	
	/**
	 * entering the rooom and displays it
	 */
	public void enterRoom() {
		
		System.out.println("Entering " + currentRoom.getClass());
		//currentRoom.enterRoom();
	}
	
	/**
	 * checks the progress of the current room the player is in
	 */
	public void checkProgress() {
		System.out.println("Game Progress");
		System.out.println("Current Room: " + currentRoom.getClass());
		System.out.println("Checking if this room is complete...");
		currentRoom.checkCompletionOfRoom();
		
	}
	
	/**
	 * displays a thank you message if the player finishes or stops the game
	 */
	public void endGame() {
		System.out.println("Thank you for playing SpongeBob's Best Day Ever!");
		this.isGameRunning = false;
		userInput.close();
		
	}

}
