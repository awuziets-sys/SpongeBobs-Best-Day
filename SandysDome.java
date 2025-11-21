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
 * tools and parts.
 */
public class SandysDome extends Room {

    private Player player;
    private Scanner input;

    private List<String> possibleTools;
    private List<String> correctTools;

    private int correctCount;
    private int incorrectCount;
    private int requiredCorrect;

    private Random random;

    /**
     * 
     * @param roomName
     * @param roomDescription
     * @param roomComplete
     * @param nextRoom
     */
    public SandysDome(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
        input = new Scanner(System.in);
        random = new Random();

        correctCount = 0;
        incorrectCount = 0;
        requiredCorrect = 4; // 4 - 5 required
    }

    public SandysDome() {
        this("Sandy’s Tree Dome",
                "Help Sandy build her rocket ship by giving her the right tools.",
                false,
                null);
    }

    @Override
    /**
     * 
     */
    public void enterRoom(Player player) {
        this.player = player;

        System.out.println("\nYou entered Sandy's Tree Dome.");
        System.out.println("Sandy is building a rocket ship!");
        System.out.println("Give her the CORRECT tools / parts.");
        System.out.println("If you get 2 wrong, the rocket explodes!\n");

        // All possible things Sandy could ask for
        possibleTools = new ArrayList<>();
        possibleTools.add("Hammer");
        possibleTools.add("Wrench");
        possibleTools.add("Screwdriver");
        possibleTools.add("Saw");
        possibleTools.add("Gear");
        possibleTools.add("Screw");
        possibleTools.add("Nail");
        possibleTools.add("Bolt");

        // Tools that are actually correct this round
        correctTools = new ArrayList<>();

        while (correctTools.size() < requiredCorrect) {
            String tool = possibleTools.get(random.nextInt(possibleTools.size()));
            if (!correctTools.contains(tool)) {
                correctTools.add(tool);
            }
        }

        System.out.println("Sandy needs " + requiredCorrect + " correct items to finish the rocket.");
    }

    @Override
    /**
     * 
     */
    public void runRoom() {

        while (!super.isRoomComplete()) {

            String request = correctTools.get(correctCount);

            System.out.println("\nSandy says: \"SpongeBob! Hand me the " + request + "!\"");
            System.out.print("Enter the tool name: ");

            String playerInput = input.nextLine().trim();

            checkIfCorrect(playerInput, request);

            if (incorrectCount >= 2) {
                explodeAndRestart();
                return;
            }

            if (correctCount >= requiredCorrect) {
                buildRocket();
                return;
            }
        }
    }

    @Override
    public boolean checkCompletionOfRoom() {
        return correctCount >= requiredCorrect;
    }

    /**
     * Checks if user's tool matches the requested tool
     * @param userTool
     * @param requiredTool
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
     * Handles rocket explosion
     */
    private void explodeAndRestart() {

        System.out.println("\nBOOOOM");
        System.out.println("The rocket explodes because of your mistakes...");
        System.out.println("Sandy sends you outside and starts over.\n");

        correctCount = 0;
        incorrectCount = 0;

        System.out.println("Type 'enter' to try Sandy's Dome again.");
    }

    /**
     * Handles success
     */
    private void buildRocket() {

        System.out.println("\nSUCCESS!");
        System.out.println("The rocket ship is complete!");

        System.out.println("\nSandy: \"Great work, SpongeBob! Hop in!\"");
        System.out.println("The rocket takes off toward the Krusty Krab!");

        super.completeRoom();

        System.out.println("\nType 'next' to travel to the Krusty Krab.");
    }
}
