import java.util.Scanner;

/**
 * Class: SquidwardsHouse
 * Author: Nuria Jurjo
 * Course: CSE 201
 * 
 * Represents Squidward's house in the game.
 * The player must find the correct way to enter.
 * ONLY tunneling underground (option 3) will succeed.
 */
public class SquidwardsHouse extends Room {

    // Tracks whether the player managed to enter successfully
    private boolean enteredSuccessfully = false;

    /**
     * Full constructor
     */
    public SquidwardsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }

    /**
     * Default constructor
     */
    public SquidwardsHouse() {
        this("Squidward's House", "The second level of the game!", false, null);
    }

    /**
     * Called when the player first enters Squidward's House.
     * This method prints the introduction and choices.
     */
    @Override
    public void enterRoom(Player player) {
        System.out.println("\nYou arrive at " + getRoomName() + ".");
        System.out.println(getRoomDescription());

        // Give player choices
        System.out.println("\nHow will you try to enter?");
        System.out.println("1. Blow up the door");
        System.out.println("2. Catapult yourself through the window");
        System.out.println("3. Tunnel underground to the living room");

        System.out.println("\n(Type 1, 2, or 3)");
    }

    /**
     * Main logic loop for this room.
     * Keeps running until the correct choice is made.
     */
    @Override
    public void runRoom() {

        Scanner userInput = new Scanner(System.in);

        while (!super.isRoomComplete()) {

            System.out.print("> ");
            String input = userInput.nextLine().trim();

            switch (input) {

                case "1":
                    System.out.println(
                        "\nYou try to blow up the door. " +
                        "The explosion backfires and you're covered in soot.\n" +
                        "Squidward is NOT impressed."
                    );
                    break;

                case "2":
                    System.out.println(
                        "\nYou launch yourself with a catapult and crash into the window.\n" +
                        "Ouch! That definitely didn’t work."
                    );
                    break;

                case "3":
                    System.out.println(
                        "\nYou dig a tunnel underground and pop up in Squidward’s living room."
                    );
                    System.out.println("Squidward: SPONGEBOB! What are you doing in my house?!");
                    System.out.println("SpongeBob: I was just checking to see if you were okay, Squidward!");
                    System.out.println("Squidward: I was. Until five seconds ago.");
                    System.out.println("SpongeBob: Great! Have a SUPER sparkly day!");
                    System.out.println("*SpongeBob happily tunnels back out*");
                    // Mark as successful
                    enteredSuccessfully = true;

                    // Mark the room complete using the parent method
                    super.completeRoom();

                    return;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }

            // If not complete yet, remind the user
            if (!super.isRoomComplete()) {
                System.out.println("\nTry a different method.");
                System.out.println("1. Blow up the door");
                System.out.println("2. Catapult yourself through the window");
                System.out.println("3. Tunnel underground to the living room");
            }
        }
    }

    /**
     * Returns whether this room has been successfully completed.
     */
    @Override
    public boolean checkCompletionOfRoom() {
        return enteredSuccessfully;
    }
}
