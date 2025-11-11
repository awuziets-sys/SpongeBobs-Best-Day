/**
 * Class: Inventory
 * Author: Andrew
 * Purpose: manages the players collected items. Handles adding
 * removing, checking, and printing the item info.
 */

import java.util.Arrays;

public class Inventory {
    Item[] items;
    int size;
    int DEFAULT_SIZE = 10;
    
    public Inventory() {
        this.items = new Item[DEFAULT_SIZE];
        this.size = 0;
    }
    
    /**
     * Function to add an item to the items arraw 
     * @param item: the item to add
     */
    public void addItem(Item item) {
        if (size == items.length) {
            items = Arrays.copyOf(items, size * 2);
        }
        
        items[size] = item;
        size++;
    }
    
    /**
     * Function to return and remove an item from the array
     * @param itemName: the name of the item to remove
     * @return: the item that was removed
     */
    public Item removeItem(String itemName) {
        int index = getIndex(itemName);
        
        if (index == -1) {
            return null;
        }
        
        Item res = items[index];
        
        size--;
        for (int i = index; i < size; i++) {
            items[i] = items[i + 1];
        }
        items[size] = null;
        
        return res;
    }
    
    /**
     * Function to check if the array contains a certain item
     * @param itemName: The name of the item to check
     * @return: whether or not the inventory contains the item
     */
    public boolean contains(String itemName) {
        return getIndex(itemName) != -1;
    }
    
    /**
     * Function to print the name of an item.
     * @param itemName: the name of the item to find
     * @return: The items name or description
     */
    public String printInfo(String itemName) {
        int index  = getIndex(itemName);
        
        if (index == -1) {
        	return "Item not found";
        }
        
        return items[index].getName();
        // change to description maybe?
    }
    
    /**
     * Helper method to find the index of an item
     * @param itemName: The name of the item
     * @return: The index that contains the item
     */
    private int getIndex(String itemName) {
        for (int i = 0; i < this.size; i++) {
            if (this.items[i].getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
}
