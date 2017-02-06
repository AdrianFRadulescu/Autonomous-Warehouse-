package jobselection.item;

import java.util.Collection;

/**
 * Created by adrian_radulescu1997 on 25.02.2016.
 */
public interface ItemTable {


    public boolean isEmpty();

    /**
     * inserts an element into the table
     */

    public void add(Item _item);

    /**
     * deletes the element with the given key from the table
     */

    public void remove(String _name);

    /**
     * deletes the given element from the table
     */

    public void remove(Item _item);

    /**
     * sees if the table contains the item with the given name
     * @param _itemName = the name of the item
     */

    public boolean contains(String _itemName);

    /**
     * finds an element in the table
     * @return the Item object with the given key or null if it does not exist
     */

    public Item get(String _itemName);

    /**
     * @return the size of the table
     */

    public int size();

    /**
     * sets the location of the element
     */

    public void setLocation(String _name,Integer x_coord,Integer y_coord);

    /**
     * @return a collection of the elements in the table
     */

    public Collection<Item> getValues();

}
