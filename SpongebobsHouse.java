import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpongebobsHouse extends Room {

    List<Location> locationsList = new ArrayList<>();
    List<String> locationNameList = new ArrayList<>();
    Player player;
    Location currLocation;
    
    public SpongebobsHouse(String roomName, String roomDescription, boolean roomComplete, Room nextRoom) {
        super(roomName, roomDescription, roomComplete, nextRoom);
        // TODO Auto-generated constructor stub
    }
    
    public SpongebobsHouse() {
        this("Spongebob's House", "The first level of the game!", false, null);
    }

    @Override
    public void enterRoom(Player player) {
        //Creating the items to collect
        Item hat = new Item("Work Hat", "Your work hat, you should put it on", null, null);
        Item food = new Item("Gary's Food", "Gary is hungry, feed it to him before you leave", null, null);
        
        //Creating all the rooms of the house
        Location bedroom = new Location("Bedroom", null);
        Location closet = new Location("Closet", hat);
        Location kitchen = new Location("Kitchen", food);
        Location livingroom = new Location("Livingroom", null);
        Location stairs = new Location("Staircase", null);
        Location bathroom = new Location("Bathroom", null);
        
        //Linking the locations
        bedroom.addSubLocation(closet);
        bedroom.addSubLocation(stairs);
        
        closet.addSubLocation(bedroom);
        
        stairs.addSubLocation(bedroom);
        stairs.addSubLocation(livingroom);
        
        livingroom.addSubLocation(stairs);
        livingroom.addSubLocation(kitchen);
        livingroom.addSubLocation(bathroom);
        
        kitchen.addSubLocation(livingroom);
        
        bathroom.addSubLocation(livingroom);
        
        //Adding locations to the list
        locationsList.add(bedroom);
        locationNameList.add(bedroom.getLocationName());
        locationsList.add(kitchen);
        locationNameList.add(kitchen.getLocationName());
        locationsList.add(closet);
        locationNameList.add(closet.getLocationName());
        locationsList.add(stairs);
        locationNameList.add(stairs.getLocationName());
        locationsList.add(livingroom);
        locationNameList.add(livingroom.getLocationName());
        locationsList.add(bathroom);
        locationNameList.add(bathroom.getLocationName());
        
        //setting the player for ease of access
        this.player = player;
        
        //setting the start room
        currLocation = bedroom;

        System.out.println("Good morning Spongebob! It's another exciting day in Bikini Bottom.\n" 
                          + "Before you can take on the world you need to get ready.\n"
                          + "Put on your Work Hat and feed Gary before moving on with your day.\n");
    }
    
    @Override
    public boolean checkCompletionOfRoom() {
        if (player.getInventory().contains("Work Hat") && player.getInventory().contains("Gary's Food")) {
            return true;
        }
        
        return false;
    }

    /**
     * Main loop method to read userInput and run all required methods
    */
    @Override
    public void runRoom() {
        Scanner userInput = new Scanner(System.in);
        
        while(!super.isRoomComplete()) {
            System.out.println("\nYou are in " + currLocation.getLocationName());
            System.out.println("What would you like to do?\n"
                    + "1] Search\n2] Move [Location Name]\n3] Take \n4] See Inventory");
            System.out.print("i> ");
            
            String choice = userInput.nextLine();
            
            if (choice.equals("Search") || choice.equals("1")) {
                currLocation.search();
            }
            if (choice.contains("Move") || choice.contains("2")) {
                moveMethod(choice);
            }
            if (choice.equals("Take") || choice.equals("3")) {
                if (currLocation.hasItem()) {
                    player.getInventory().addItem(currLocation.getItems());
                    currLocation.removeItem(currLocation.getItems().getName());
                }
                else {
                    System.out.println("This location doesn't have anything to take.");
                }
            }
            if (choice.equals("Inventory") || choice.equals("See Inventory") || choice.equals("4")) {
                System.out.println("You are holding: " + player.getInventory());
            }
            
            if (checkCompletionOfRoom()) {
                System.out.println("Spongebob feeds Gary his food and puts on his trusty hat "
                        + "before heading off to begin the day!");
                player.getInventory().removeItem("Gary's Food");
                userInput.close();
                return;
            }
        }
    }
    
    /**
     * Helper method to run the move input
     * @param choice: User input
     */
    private void moveMethod(String choice) {
        if (choice.length() >= 6) {
            if (locationNameList.contains(choice.substring(5))) {
                int i = locationNameList.indexOf(choice.substring(5));
                Location l = locationsList.get(i);
                
                if (currLocation.getsubLocations().contains(l)) {
                    currLocation = l;
                    System.out.println("You moved to " + l.getLocationName());
                }
                else {
                    System.out.println("That location is not accessible.");
                }
                
            }
            else if (locationNameList.contains(choice.substring(2))) {
                int i = locationNameList.indexOf(choice.substring(2));
                Location l = locationsList.get(i);
                
                if (currLocation.getsubLocations().contains(l)) {
                    currLocation = l;
                    System.out.println("You moved to " + l.getLocationName());
                }
                else {
                    System.out.println("That location is not accessible.");
                }
            }
            else {
                System.out.println("That location is not valid.\nUse Search to see nearby locations.");
            }
        }
        else {
            System.out.println("That is not a valid form of the Move command. Please say Move Location Name");
        }
    }
}

