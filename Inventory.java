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
     * 
     * @param item
     * @return
     */
    public boolean addItem(Item item) {
        if (size == items.length) {
            items = Arrays.copyOf(items, size * 2);
        }
        
        items[size] = item;
        size++;
        
        return true;
    }
    
    /**
     * 
     * @param itemName
     * @return
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
     * 
     * @param itemName
     * @return
     */
    public boolean contains(String itemName) {
        return getIndex(itemName) != -1;
    }
    
    /**
     * 
     * @param itemName
     * @return
     */
    public String printInfo(String itemName) {
        int index  = getIndex(itemName);
        
        if (index == -1) {
        	return "Item not found";
        }
        
        return items[index].getName();
    }
    
    /**
     * 
     * @param itemName
     * @return
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
