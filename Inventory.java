/**
 * Class: Inventory
 * Author: Andrew Albert
 * Purpose: manages the players collected items. Handles adding
 * removing, checking, and printing the item info.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory {
    List<Item> items = new ArrayList<>();
    
    public Inventory() {
        
    }
    
    /**
     * Function to add an item to the items arraw 
     * @param item: the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Function to return and remove an item from the array
     * @param itemName: the name of the item to remove
     * @return: the item that was removed
     */
    public Item removeItem(String itemName) {
        int index = getIndex(itemName);
        
        if (index != -1) {
            return items.remove(index);
        }
        
        return null;
    }
    
    /**
     * Function to check if the array contains a certain item
     * @param itemName: The name of the item to check
     * @return: whether or not the inventory contains the item
     */
    public boolean contains(String itemName) {
        return getIndex(itemName) != -1;
    }
    
    public Item getItem(String itemName) {
        int index  = getIndex(itemName);
        
        if (index == -1) {
            return null;
        }
        
        return items.get(index);
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
        
        return items.get(index).getDescription();
        // change to description maybe?
    }
    
    /**
     * Helper method to find the index of an item
     * @param itemName: The name of the item
     * @return: The index that contains the item
     */
    private int getIndex(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
    
    public String toString() {
        String res = "";
        for (Item i : items) {
            res += i.getName() + "\n";
        }
        return res;
    }
}
