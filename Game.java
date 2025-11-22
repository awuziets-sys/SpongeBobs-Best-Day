import java.util.Scanner;

/**
 * Class: Game
 * @author Sam Adeyemo, Testimony Awuzie, Andrew Albert
 * @version 2.1
 * Course : CSE 201 Fall 2025
 * Written: November 19, 2025
 *
 * Purpose:
 * This class controls the flow of the game. It manages the player,
 * keeps track of the current room, and allows the player to decide
 * whether to enter the next room or quit the game after completing one.
 */
public class Game {

    private Room currentRoom;      // Tracks the current room
    private Player player;          // The player object
    private boolean isGameRunning;  // Keeps the game active or inactive
    private Scanner userInput;      // Reads user input

    /**
     * Constructor — Initializes scanner and default state.
     */
    public Game() {
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
        Room squidwardsHouse = new SquidwardsHouse();
        Room patricksRock = new PatricksRock();
        Room sandysDome = new SandysDome();
        Room krustyKrab = new KrustyKrab();

        // Link rooms in order of progression
        spongebobsHouse.setNextRoom(squidwardsHouse);
        squidwardsHouse.setNextRoom(patricksRock);
        patricksRock.setNextRoom(sandysDome);
        sandysDome.setNextRoom(krustyKrab);
        krustyKrab.setNextRoom(null);

        // Initialize the starting room and player
        currentRoom = spongebobsHouse;
        player = new Player(currentRoom, inventory, "001", "SpongeBob");
    }

    /**
     * This function starts the game. It prints an introduction
     * and loads the player into the first room.
     */
    public void startGame() {
        printIntro();
        isGameRunning = true;

        while (isGameRunning && currentRoom != null) {

            System.out.println("\nLocation: " + currentRoom.getRoomName());
            System.out.println("--------------------------------");

            // Enter and run the logic for the current room
            currentRoom.enterRoom(player);
            currentRoom.runRoom();

            // If the room is complete, ask the player what to do next
            if (currentRoom.isRoomComplete()) {
                promptNextAction();
            }
        }

        endGame();
    }

    /**
     * Gives the player the option to continue to the next room
     * or quit the game after completing a room.
     */
    private void promptNextAction() {
        Room next = currentRoom.getNextRoom();

        if (next == null) {
            System.out.println("\nAll rooms complete. You beat the game!");
            isGameRunning = false;
            return;
        }

        while (true) {
            System.out.println("\nWould you like to enter the next room or quit?");
            System.out.println("Type 'enter' to continue or 'quit' to end the game.");
            System.out.print("> ");

            String choice = userInput.nextLine().trim().toLowerCase();

            if (choice.equals("enter")) {
                currentRoom = next;
                player.enterRoom(currentRoom);
                break;
            }

            if (choice.equals("quit")) {
                isGameRunning = false;
                break;
            }

            System.out.println("Invalid input. Please type 'enter' or 'quit'.");
        }
    }

    /**
     * Displays the introduction text for the game.
     */
    private void printIntro() {
        System.out.println("Welcome to SpongeBob's Best Day Ever!");
        System.out.println("----------------------------------------------------------");
        System.out.println("Spongebob's end goal is to celebrate with everyone at the Krusty Krab.\n"
                + "He has to go to multiple locations in Bikini Bottom, "
                + "completing different tasks for his friends.\n"
                + "At the final location, if he completes his tasks successfully, he wins.\n"
                + "Help him have his Best Day Ever!");
    }

    /**
     * Displays a Thank you Message if the player finishes or stops the game
     */
    public void endGame() {
        System.out.println("\nThank you for playing SpongeBob's Best Day Ever!");
        userInput.close();
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
