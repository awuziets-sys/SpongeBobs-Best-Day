import java.util.Arrays;

/**
 * Class: Inventory
 * @author: Andrew Albert, Testimony Awuzie
 * Purpose: Manages the player’s collected items. Handles adding, removing,
 * checking, and printing item info.
 */
public class Inventory {
    private Item[] items;
    private int size;
    private final int DEFAULT_SIZE = 10;

    public Inventory() {
        this.items = new Item[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * Adds an item to the inventory.
     * @param item the item to add
     */
    public void addItem(Item item) {
        if (size == items.length) {
            items = Arrays.copyOf(items, size * 2);
        }

        items[size] = item;
        size++;
    }

    /**
     * Removes an item by name and returns it.
     * @param itemName the name of the item to remove
     * @return the removed item, or null if not found
     */
    public Item removeItem(String itemName) {
        int index = getIndex(itemName);

        if (index == -1) {
            System.out.println("Item not found in inventory.");
            return null;
        }

        Item removed = items[index];

        // Shift remaining items left
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }

        items[--size] = null;
        return removed;
    }

    /**
     * Checks whether an item exists in the inventory.
     * @param itemName the item to search for
     * @return true if found, false otherwise
     */
    public boolean contains(String itemName) {
        return getIndex(itemName) != -1;
    }

    /**
     * Returns description of a specific item.
     * @param itemName the item name
     * @return the description if found, otherwise message
     */
    public String printInfo(String itemName) {
        int index = getIndex(itemName);

        if (index == -1) {
            return "Item not found.";
        }

        return " " + items[index].getName() + ": " + items[index].getDescription();
    }

    /**
     * Helper to find an item’s index.
     * @param itemName name of the item
     * @return index or -1 if not found
     */
    private int getIndex(String itemName) {
        for (int i = 0; i < this.size; i++) {
            if (this.items[i].getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Prints all items currently in the inventory.
     * @return 
     */
    public void printInventory() {
        if (size == 0) {
            System.out.println("Inventory is empty!");
            return;
        }

        System.out.println("Current Inventory:");
        for (int i = 0; i < size; i++) {
            System.out.println("  - " + items[i].getName());
        }
    }

    /**
     * Prints descriptions for all items in inventory.
     */
    public void printAllItemDescriptions() {
        if (size == 0) {
            System.out.println("No items to describe — your inventory is empty!");
            return;
        }

        System.out.println("Item Descriptions:");
        for (int i = 0; i < size; i++) {
            System.out.println("• " + items[i].getName() + " — " + items[i].getDescription());
        }
    }
}
