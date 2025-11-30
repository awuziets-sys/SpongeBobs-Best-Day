import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class: SandysDome
 * @author: Testimony Awuzie
 * @version 1.0
 * Course: CSE 201 Fall 2025
 * Written: November 21st, 2025
 *
 * Purpose:
 * Room 4 of the game. SpongeBob must help Sandy
 * build a rocket ship by giving her the correct
 * tools and parts. The player must provide a sequence
 * of correct tools; two mistakes cause the rocket to explode
 * and the room to restart.
 */
public class SandysDome extends Room {

    // Reference to the player in this room
    private Player player;

    // Scanner for reading user input in this room
    // NOTE: We intentionally do NOT close this Scanner, to avoid
    // closing System.in for the entire game.
    private Scanner input;

    // List of all possible tools Sandy might ask for
    private List<String> possibleTools;

    // Subset of tools that are correct for this run
    private List<String> correctTools;

    // Counters for correct and incorrect tools provided
    private int correctCount;
    private int incorrectCount;

    // Number of correct items required to complete the rocket
    private int requiredCorrect;

    // Random generator used to choose correct tools
    private Random random;

    /**
     * Full constructor.
     *
     * @param roomName        name of this room
     * @param roomDescription description of this room
     * @param roomComplete    whether the room is already complete
     * @param nextRoom        the next room in the sequence
     */
    public SandysDome(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);

        // Initialize reusable objects
        input = new Scanner(System.in);
        random = new Random();

        // Initialize state
        correctCount = 0;
        incorrectCount = 0;
        requiredCorrect = 4; // Room requires 4 correct tools in a row
    }

    /**
     * Default constructor with preset room name and description.
     */
    public SandysDome() {
        this("Sandy's Tree Dome",
             "Help Sandy build her rocket ship by giving her the right tools.",
             false,
             null);
    }

    /**
     * Called when the player first enters this room.
     * Sets up the list of possible tools and chooses a random
     * subset of tools that will be required for this run.
     *
     * @param player the Player (SpongeBob) object
     */
    @Override
    public void enterRoom(Player player) {
        this.player = player;

        System.out.println("\nYou entered Sandy's Tree Dome.");
        System.out.println("Sandy is in the middle of building a rocket ship.");
        System.out.println("You must hand her the correct tools and parts.");
        System.out.println("Be careful: if you give her the wrong thing twice, the rocket explodes.\n");

        // Initialize the total set of tools Sandy can ask for
        possibleTools = new ArrayList<>();
        possibleTools.add("Hammer");
        possibleTools.add("Wrench");
        possibleTools.add("Screwdriver");
        possibleTools.add("Saw");
        possibleTools.add("Gear");
        possibleTools.add("Screw");
        possibleTools.add("Nail");
        possibleTools.add("Bolt");

        // Select a random subset of tools needed for this specific run
        correctTools = new ArrayList<>();

        while (correctTools.size() < requiredCorrect) {
            String tool = possibleTools.get(random.nextInt(possibleTools.size()));
            if (!correctTools.contains(tool)) {
                correctTools.add(tool);
            }
        }

        // Reset counters whenever the room is (re)entered
        correctCount = 0;
        incorrectCount = 0;

        System.out.println("Sandy needs " + requiredCorrect + " correct items to finish the rocket.");
    }

    /**
     * Main logic loop for Sandy's Dome.
     * Prompts the player to enter tools, checks correctness,
     * and handles win/lose conditions.
     */
    @Override
    public void runRoom() {

        // Continue until the room is marked complete
        while (!super.isRoomComplete()) {

            // Get the next required tool from the list
            String request = correctTools.get(correctCount);

            System.out.println("\nSandy says: \"SpongeBob! Hand me the " + request + "!\"");
            System.out.print("Enter the tool name: ");

            String playerInput = input.nextLine().trim();

            checkIfCorrect(playerInput, request);

            // If player has made too many mistakes, the rocket explodes
            if (incorrectCount >= 2) {
                explodeAndRestart();
                return; // End this run of the room; Game can re-enter it if needed
            }

            // If enough correct tools have been provided, the rocket is built
            if (correctCount >= requiredCorrect) {
                buildRocket();
                return;
            }
        }
    }

    /**
     * Checks whether the room has been completed.
     * The room is complete when correctCount reaches requiredCorrect,
     * and completeRoom() has been called.
     *
     * @return true if room goals are met, false otherwise
     */
    @Override
    public boolean checkCompletionOfRoom() {
        return correctCount >= requiredCorrect;
    }

    /**
     * Checks if the player's provided tool matches the requested tool.
     *
     * @param userTool     the tool name entered by the player
     * @param requiredTool the correct tool Sandy requested
     */
    private void checkIfCorrect(String userTool, String requiredTool) {

        if (userTool.equalsIgnoreCase(requiredTool)) {

            System.out.println("Correct! Sandy uses the " + requiredTool + " on the ship.");
            correctCount++;

        } else {

            incorrectCount++;
            System.out.println("Wrong tool! Sandy stares at you in disappointment...");
            System.out.println("Mistakes: " + incorrectCount + "/2");
        }

        System.out.println("Correct tools given: " + correctCount + "/" + requiredCorrect);
    }

    /**
     * Handles the rocket explosion when the player makes two mistakes.
     * Resets the internal counters so the room can be restarted.
     */
    private void explodeAndRestart() {

        System.out.println("\nBOOOOM!");
        System.out.println("The rocket explodes because of your mistakes...");
        System.out.println("Sandy sends you outside and starts over.\n");

        // Reset state so the room can be tried again
        correctCount = 0;
        incorrectCount = 0;

        System.out.println("You failed this attempt. Re-enter Sandy's Dome to try again.");
    }

    /**
     * Handles successful construction of the rocket.
     * Marks the room as complete and prints the transition text.
     */
    private void buildRocket() {

        System.out.println("\nSUCCESS!");
        System.out.println("The rocket ship is complete!");

        System.out.println("\nSandy: \"Great work, SpongeBob! Hop in!\"");
        System.out.println("The rocket blasts off toward the Krusty Krab.");

        // Mark the room as complete via the parent class
        super.completeRoom();

        System.out.println("\nYou have completed Sandy's Dome. You can now travel to the Krusty Krab.");
    }
}
