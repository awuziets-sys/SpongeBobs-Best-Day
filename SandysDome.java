import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class: SandysDome
 * Author: Testimony Awuzie
 * Version: 1.0
 * Course: CSE 201 Fall 2025
 *
 * Purpose:
 * Room 4 of the game. SpongeBob must help Sandy build a rocket ship by giving
 * her the correct tools and parts. Sandy requests items one at a time, and the
 * player must enter the correct tool name. Two mistakes cause the rocket to
 * explode and the room to reset.
 */
public class SandysDome extends Room {

    // All possible tools Sandy might request
    private List<String> possibleTools;

    // Randomly chosen REQUIRED tools for this round
    private List<String> correctTools;

    // Counters for gameplay
    private int correctCount;     // Number of correct tools given so far
    private int incorrectCount;   // Number of mistakes made
    private int requiredCorrect;  // How many correct items Sandy needs

    private Random random;

    /**
     * Full constructor (rarely used since default is simpler).
     */
    public SandysDome(String roomName, String roomDescription,
                      boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
        random = new Random();
        requiredCorrect = 4;  // Sandy needs at least 4 correct tools
    }

    /**
     * Default constructor — sets name, description, and required values.
     */
    public SandysDome() {
        this("Sandy's Tree Dome",
             "Help Sandy build her rocket ship by giving her the right tools.",
             false,
             null);
    }

    /**
     * Called when the player enters Sandy's dome.
     * Sets up:
     *  - Tool lists
     *  - Random required tools
     *  - Intro story
     */
    @Override
    public void enterRoom(Player player) {
        this.player = player;

        System.out.println("\nYou entered Sandy's Tree Dome.");
        System.out.println("Sandy is in the middle of building a rocket ship.");
        System.out.println("You must hand her the correct tools and parts.");
        System.out.println("Be careful: if you give her the wrong thing twice, the rocket explodes.\n");

        /
