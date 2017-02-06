package jobselection.item;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adrian_radulescu1997 on 26.02.2016.
 */
public class ItemHashTable implements ItemTable {

    private Map<String,Item> itemMap;

    /**
     * Constructs an empty ItemHashTable
     */

    public ItemHashTable(){

        this.itemMap = new HashMap<String, Item>();
    }

    /**
     * Constructs an ItemHashTable with
     * @param _item
     */

    public ItemHashTable(Item _item){

        this.itemMap = new HashMap<String, Item>();
        itemMap.put(_item.getName(),_item);
    }

    /**
     * Constructs an ItemHashMap form a given ArrayList
     * @param _itemList
     */

    public ItemHashTable(ArrayList<Item> _itemList){

        this.itemMap = new HashMap<String, Item>();

        for (Item it : _itemList)
            itemMap.put(it.getName(),it);

    }

    /**
     * Constructs an ItemHashMap form a given Array
     * @param _itemList
     */

    public ItemHashTable(Item[] _itemList){

        this.itemMap = new HashMap<String,Item>();

        for (Item it : _itemList)
            itemMap.put(it.getName(),it);

    }

    /**
     * Checks if the table is empty or not
     * @return true if the table is empty , false otherwise
     */

    @Override
    public boolean isEmpty() {
        return itemMap.isEmpty();
    }

    /**
     * inserts an element into the table
     */

    @Override
    public void add(Item _item) {
        itemMap.put(_item.getName(),_item);
    }

    /**
     * deletes an element from the table
     */

    @Override
    public void remove(String _name) {
        itemMap.remove(_name);
    }

    /**
     * deletes an element from the table
     */

    @Override
    public void remove(Item _item) {
        itemMap.remove(_item.getName());
    }

    /**
     * sees if the table contains the item with the given name
     *
     * @param _itemName = the name of the item
     */
    @Override
    public boolean contains(String _itemName) {
        return itemMap.containsKey(_itemName);
    }

    /**
     * finds an element in the table
     *
     * @param _itemName
     * @return the Item object with the given key or null if it does not exist
     */
    @Override
    public Item get(String _itemName) {
        return itemMap.get(_itemName);
    }

    /**
     * @return the size of the table
     */
    @Override
    public int size() {
        return itemMap.size();
    }

    /**
     * sets the location of the element
     */
    @Override
    public void setLocation(String _name,Integer x_coord,Integer y_coord) {


        Item itemValue = itemMap.get(_name);

        itemValue.setX_coord(x_coord);
        itemValue.setY_coord(y_coord);

        itemMap.remove(_name);
        itemMap.put(_name,itemValue);

    }

    /**
     * @return a collection of the elements in the table
     */
    @Override
    public Collection<Item> getValues() {
        return itemMap.values();
    }
}
