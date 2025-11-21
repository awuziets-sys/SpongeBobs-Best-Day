import java.util.Scanner;

/**
 * Class: SquidwardsHouse
 * Author: Nuria Jurjo
 * Course: CSE 201
 * 
 * Represents Squidward's house in the game. This room allows the player to try
 * different methods of entering. Only tunneling underground allows a successful
 * entry and completes the room.
 */
public class SquidwardsHouse extends Room {

	 // tracks whether the player managed to enter successfully
	private boolean enteredSuccessfully;

    /**
     * Creates a SquidwardsHouse room.
     *
     * @param roomName        the name of the room
     * @param roomDescription a description of the room
     * @param roomComplete    whether the room is already complete
     * @param nextRoom        the next room in the sequence
     */
	public SquidwardsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
		super(roomName, roomDescription, roomComplete, nextRoom);
	}
	
	public SquidwardsHouse() {
        this("Squidwards's House", "The second level of the game!", false, null);
    }

    /**
     * Prints descriptions and the available options for entering Squidward's house.
     * This method is called when the player arrives at the room.
     */
	@Override
	public void enterRoom(Player player) {
		System.out.println("You arrive at " + getRoomName() + ".");
		System.out.println(getRoomDescription());
		System.out.println("Options to enter:");
		System.out.println("1. Blow up the door");
		System.out.println("2. Catapult yourself through the window");
		System.out.println("3. Tunnel underground to the living room\n");
        System.out.print("> ");
	}

	/**
	 * Attempt to enter Squidward's house using a chosen method.
	 * 
	 * @param choice the method number (1, 2, or 3)
	 */
	@Override
	public void runRoom() {
		Scanner userInput = new Scanner(System.in);
		int choice = userInput.nextInt(); //
		switch (choice) {
		case 1:
			System.out.println(
					"You try to blow up the door. The explosion backfires and you’re covered in soot. Squidward is NOT impressed.");
			break;
		case 2:
			System.out.println("You launch yourself with a catapult and crash headfirst into the window. Ouch!");
			break;
		case 3:
			System.out.println("You dig a tunnel underground, and pop up right in Squidward’s living room.");
			System.out.println("Squidward: SPONGEBOB! What are you doing in my house?");
			enteredSuccessfully = true;
			completeRoom();
			break;
		default:
			System.out.println("That’s not a valid option. Try again.");
		}
	}

    /**
     * Checks whether the player has successfully completed the room.
     *
     * @return true if the player entered using method 3; false otherwise
     */
	@Override
	public boolean checkCompletionOfRoom() {
		return enteredSuccessfully;
	}
}
