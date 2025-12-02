import java.util.Scanner;

/**
 * Class: Game
 * @author: Sam Adeyemo, Testimony Awuzie, Andrew Albert
 * @version 2.1
 * Course : CSE 201 Fall 2025
 *
 * Purpose:
 * Controls the flow of the game. Manages the player,
 * tracks the current room, and handles transitions between rooms.
 */
public class Game {

    private Room currentRoom;      // Tracks the current room SpongeBob is in
    private Player player;         // The player object
    private boolean isGameRunning; // Keeps the game active or inactive
    private Scanner userInput;     // Shared scanner for user input

    /**
     * Constructor — Initializes scanner and default state.
     */
    public Game() {
        this.isGameRunning = false;
        this.userInput = new Scanner(System.in);
        setupGame();
    }

    /**
     * Sets up all rooms, links them in order, initializes player,
     * and injects the shared Scanner into each room.
     */
    private void setupGame() {
        System.out.println("Initializing SpongeBob’s Best Day Ever...\n");
        Inventory inventory = new Inventory();

        // Instantiate all rooms
        Room spongebobsHouse = new SpongebobsHouse();
        Room squidwardsHouse = new SquidwardsHouse();
        Room patricksRock   = new PatricksRock();
        Room sandysDome     = new SandysDome();
        Room krustyKrab     = new KrustyKrab();

        // Link rooms in order of progression
        spongebobsHouse.setNextRoom(squidwardsHouse);
        squidwardsHouse.setNextRoom(patricksRock);
        patricksRock.setNextRoom(sandysDome);
        sandysDome.setNextRoom(krustyKrab);
        krustyKrab.setNextRoom(null); // last room

        // Inject shared scanner into each room
        spongebobsHouse.setScanner(userInput);
        squidwardsHouse.setScanner(userInput);
        patricksRock.setScanner(userInput);
        sandysDome.setScanner(userInput);
        krustyKrab.setScanner(userInput);

        // Set starting point: SpongeBob's House
        this.currentRoom = spongebobsHouse;

        // Initialize the player
        this.player = new Player(currentRoom, inventory, "001", "SpongeBob");
    }

    /**
     * Starts the game by loading the player into the first room,
     * printing an introduction, and then entering the main command loop.
     */
    public void startGame() {
        printIntro();
        isGameRunning = true;

        System.out.println("\nWould you like to begin the game and enter SpongeBob’s House?");
        System.out.println("Type 'enter' to begin, or 'quit' to end the game.");

        while (isGameRunning) {
            System.out.print("> ");
            String command = userInput.nextLine().trim().toLowerCase();
            handleCommand(command);
        }

        // Clean up scanner when game ends
        userInput.close();
    }

    /**
     * Returns true if a valid player and room are set up.
     * Used to prevent commands that assume the game has started.
     */
    private boolean hasStartedFirstRoom() {
        return (currentRoom != null && player != null && player.getInventory() != null);
    }

    /**
     * Handles top-level player commands at the Game level.
     *
     * @param command the command string entered by the user.
     */
    private void handleCommand(String command) {
        // Commands that require the game to actually be started (room + player ready)
        boolean needsActiveRoom =
                command.equals("next")
             || command.equals("progress")
             || command.equals("inventory")
             || command.equals("describe");

        if (needsActiveRoom && !hasStartedFirstRoom()) {
            System.out.println("You haven't started the game yet. Type 'enter' to begin.");
            return;
        }

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
     * Prints the list of available global commands.
     */
    private void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println(" enter     - Enter or re-enter the current room");
        System.out.println(" next      - Move to the next room (if current is complete)");
        System.out.println(" progress  - Check current room progress");
        System.out.println(" inventory - Check items in SpongeBob’s inventory");
        System.out.println(" describe  - Get item descriptions");
        System.out.println(" help      - Show this help menu");
        System.out.println(" quit      - End the game\n");
    }

    /**
     * Prints the game introduction and story context.
     */
    private void printIntro() {
        System.out.println("Welcome to SpongeBob's Best Day Ever!");
        System.out.println("----------------------------------------------------------");
        System.out.println("SpongeBob's end goal is to celebrate with everyone at the Krusty Krab.");
        System.out.println("He has to go to multiple locations in Bikini Bottom,");
        System.out.println("completing different tasks for his friends.");
        System.out.println("At the final location, if he completes his tasks successfully, he wins.");
        System.out.println("Help him have his Best Day Ever!");
        System.out.println("\nType 'help' to view all commands.");
    }

    /**
     * Moves to the next room if the current room is complete.
     * Otherwise, informs the player they must finish the room first.
     */
    public void moveToNextRoom() {
        if (currentRoom == null) {
            System.out.println("There is no current room to move from.");
            return;
        }

        // Can't move if room isn't finished
        if (!currentRoom.isRoomComplete()) {
            System.out.println("You cannot move yet! Finish this room first.");
            return;
        }

        // Move to next room
        Room next = currentRoom.getNextRoom();

        if (next == null) {
            System.out.println("\nAll rooms complete! You finished the game!");
            endGame();
            return;
        }

        // Move pointer
        currentRoom = next;

        // Clear prompt so player knows what to do next
        System.out.println("\n----------------------------------------");
        System.out.println("You arrive at: " + currentRoom.getRoomName());
        System.out.println("----------------------------------------");
        System.out.println(currentRoom.getRoomDescription());
        System.out.println();
        System.out.println("Type 'enter' to go inside the room.");
        System.out.println("Or type 'quit' to end the game.");
        printHUD();
    }

    /**
     * Prints a small status HUD so the player always knows what they can do next.
     */
    private void printHUD() {
        System.out.println("\n[ HUD ] ---------------------------------");
        System.out.println("Current Room: " + currentRoom.getRoomName());
        System.out.println("Available Commands:");
        System.out.println("  enter     - Enter the current room");
        System.out.println("  next      - Move to the next room (if complete)");
        System.out.println("  progress  - Check room progress");
        System.out.println("  inventory - Show SpongeBob’s items");
        System.out.println("  describe  - Show item descriptions");
        System.out.println("  help      - List all commands");
        System.out.println("  quit      - End the game");
        System.out.println("------------------------------------------");
    }

    /**
     * Prints SpongeBob's current inventory.
     */
    private void checkInventory() {
        if (player == null || player.getInventory() == null) {
            System.out.println("You don't have an inventory yet. Enter a room to begin.");
            return;
        }

        System.out.println("\nChecking inventory...");
        player.getInventory().printInventory();
    }

    /**
     * Triggers the logic inside the current room.
     * Calls enterRoom and then runRoom().
     */
    public void enterRoom() {
        if (currentRoom == null) {
            System.out.println("There are no rooms to enter!");
            return;
        }

        System.out.println("\nEntering " + currentRoom.getRoomName() + "...");
        printHUD();
        currentRoom.enterRoom(player);
        currentRoom.runRoom();
    }

    /**
     * Prints item descriptions (if available).
     */
    private void describeItems() {
        if (player == null || player.getInventory() == null) {
            System.out.println("You don't have any items yet. Enter a room to start collecting items.");
            return;
        }

        System.out.println("\nItem Descriptions:");
        player.getInventory().printAllItemDescriptions();
    }

    /**
     * Checks the progress of the current room the player is in.
     */
    public void checkProgress() {
        if (currentRoom == null) {
            System.out.println("There is no current room to check.");
            return;
        }

        System.out.println("\nGame Progress");
        System.out.println("Current Room: " + currentRoom.getRoomName());
        System.out.println("Checking Room Progress...");

        boolean complete = false;
        try {
            complete = currentRoom.checkCompletionOfRoom();
        } catch (Exception e) {
            // Defensive catch in case individual rooms are not fully initialized
            System.out.println("This room is not ready to report progress yet. Try entering it first.");
            return;
        }

        if (complete) {
            System.out.println("Room is complete. Well done!");
        } else {
            System.out.println("There are tasks left in this room.");
        }
    }

    /**
     * Displays a closing message and ends the game.
     */
    public void endGame() {
        System.out.println("Thank you for playing SpongeBob's Best Day Ever!");
        this.isGameRunning = false;
    }

    /**
     * Entry point for the game.
     */
    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.startGame();
    }
}
