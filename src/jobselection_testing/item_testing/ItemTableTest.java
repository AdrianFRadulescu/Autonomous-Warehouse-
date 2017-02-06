package jobselection_testing.item_testing;

import jobselection.item.Item;
import jobselection.item.ItemHashTable;
import jobselection.item.ItemTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Created by adrian_radulescu1997 on 04.03.2016.
 */
public class ItemTableTest {

    ItemTable itemTable;
    String itemValue = "aa,12.78,0.36";

    private final String filePath = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/items.csv";

    private String test;

    @Before
    public void set_up(){
        itemTable = new ItemHashTable();
    }

    @After
    public void tear_down(){
        System.out.println(test+" test passed");
    }

    @Test
    public void testIsEmpty() throws Exception {
        test = "isEmpty";
        assert (itemTable.isEmpty());
    }

    @Test
    public void testAdd() throws Exception {

        test = "Add";

        itemTable.add(new Item(itemValue));
        assert((new Item(itemValue)).compareTo(itemTable.get("aa") ) == 0);
    }

    @Test
    public void testRemove() throws Exception {

        test = "Remove";

        itemTable.add(new Item(itemValue));
        itemTable.remove(new Item(itemValue).getName());

        assert (itemTable.size() == 0);

    }

    @Test
    public void testRemove1() throws Exception {

        test = "Remove1";

        itemTable.add(new Item(itemValue));
        itemTable.remove(new Item(itemValue));

        assert (itemTable.size() == 0);

    }

    @Test
    public void testContains() throws Exception {

        test = "Contains";

        itemTable.add(new Item(itemValue));
        assert (itemTable.contains(new Item(itemValue).getName()));
    }

    @Test
    public void testGet() throws Exception {

        test = "Get";

        itemTable.add(new Item(itemValue));
        assert (
                (new Item(itemValue)).compareTo(
                        itemTable.get(new Item(itemValue).getName())
                ) == 0
        );
    }

    @Test
    public void testSize() throws Exception {

        test = "Size";

        for (int i = 0; i < 5; i++)
            itemTable.add(new Item(itemValue));

        assert (itemTable.size() == 1);

        itemTable.remove(new Item(itemValue).getName());

        assert (itemTable.size() == 0);

    }

    @Test
    public void testSetLocation() throws Exception {

        test = "SetLocation";

        itemTable.add(new Item(itemValue));
        itemTable.setLocation(new Item(itemValue).getName(),5,3);

        assert (
                itemTable.get(new Item(itemValue).getName()).getX_coord() == 5
                && itemTable.get(new Item(itemValue).getName()).getY_coord() == 3
        );

    }

    @Test
    public void testValues() throws Exception {

        test = "Values";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        //a standard map for storing the elements form the file
        Map<String,Item> itemMap = new HashMap<>();

        String line;

        while((line = reader.readLine()) != null){
            itemTable.add(new Item(line));
            itemMap.put(new  Item(line).getName(),new Item(line));
        }

        reader.close();

        //check if all the elements form the itemTable are in the file
        //and wether all the elements form the file are in the table

        assertFalse(
                itemTable.getValues().stream().noneMatch(
                        (Item e) -> itemMap.containsKey(e.getName())
                ) && itemMap.values().stream().noneMatch(
                        (Item e) -> itemTable.contains(e.getName())
                )
        );

    }
}