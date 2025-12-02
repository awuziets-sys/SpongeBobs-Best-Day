/**
 * Class: SquidwardsHouse
 * @author: Nuria Jurjo
 * Course: CSE 201
 *
 * Represents Squidward's house in the game.
 * The player must find the correct way to enter.
 * ONLY tunneling underground (option 3) will succeed.
 */
public class SquidwardsHouse extends Room {

    // Tracks whether the player managed to enter successfully
    private boolean enteredSuccessfully = false;

    public SquidwardsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
    }

    public SquidwardsHouse() {
        this("Squidward's House", "The second level of the game!", false, null);
    }

    @Override
    public void enterRoom(Player player) {
        this.player = player;

        System.out.println("\nYou arrive at " + getRoomName() + ".");
        System.out.println(getRoomDescription());
        System.out.println("\nYou knock on the door. Silence.");
        System.out.println("Worried, you decide to find a way inside to check on Squidward.");
        System.out.println("\nHow will you try to enter?");
        System.out.println("1. Blow up the door");
        System.out.println("2. Catapult yourself through the window");
        System.out.println("3. Tunnel underground to the living room");
        System.out.println("\n(Type 1, 2, or 3)");
    }

    @Override
    public void runRoom() {

        while (!isRoomComplete()) {
            System.out.print("> ");
            String inputLine = input.nextLine().trim();

            switch (inputLine) {
            case "1":
                System.out.println(
                    "\nYou try to blow up the door. " +
                    "The explosion backfires and you're covered in soot.\n" +
                    "Squidward is NOT impressed (wherever he is)."
                );
                break;

            case "2":
                System.out.println(
                    "\nYou launch yourself with a catapult and slam into the window.\n" +
                    "It doesn't break, but your dignity might have."
                );
                break;

            case "3":
                System.out.println(
                    "\nYou dig a tunnel underground and pop up in Squidward’s living room."
                );
                System.out.println("Squidward: \"Barnacles! SPONGEBOB!! What are you doing in my house?!\"");
                System.out.println("SpongeBob: \"I was just checking to see if you were okay!\"");
                System.out.println("Squidward: \"I would be... IF YOU WEREN'T HERE!\"");
                System.out.println("SpongeBob: \"Great! Off to see Patrick!\"");
                System.out.println("*SpongeBob tunnels back out and heads toward Patrick’s Rock...*");

                enteredSuccessfully = true;
                completeRoom();
                System.out.println("\nType 'next' to travel to Patrick's Rock.");
                return;

            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                break;
            }

            if (!isRoomComplete()) {
                System.out.println("\nTry a different method:");
                System.out.println("1. Blow up the door");
                System.out.println("2. Catapult yourself through the window");
                System.out.println("3. Tunnel underground to the living room");
            }
        }
    }

    @Override
    public boolean checkCompletionOfRoom() {
        return enteredSuccessfully;
    }
}
