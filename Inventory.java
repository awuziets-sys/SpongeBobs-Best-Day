import java.util.Arrays;

public class Inventory {
    Item[] items;
    int size;
    int DEFAULT_SIZE = 10;
    
    public Inventory() {
        this.items = new Item[DEFAULT_SIZE];
        this.size = 0;
    }
    
    public boolean addItem(Item item) {
        if (this.size == this.size.length) {
            items = Arrays.copyOf(items, size * 2);
        }
        
        items[size] = item;
        size++;
        
        return true;
    }
    
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
    
    public boolean contains(String itemName) {
        return getIndex(itemName) != -1;
    }
    
    public String printInfo(String itemName) {
        int index  = getIndex(itemName);
        
        return items[index].getName();
    }
    
    private int getIndex(String itemName) {
        for (int i = 0; i < this.size; i++) {
            if (this.items[i].getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
}
