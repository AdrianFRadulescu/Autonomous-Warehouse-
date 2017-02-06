package jobselection_testing.item_testing;

import jobselection.item.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by adrian_radulescu1997 on 04.03.2016.
 */
public class ItemTest {

    String itemValue = "aa,12.78,0.36";

    Item it;

    @Before
    public void setUp() throws Exception {
        this.it = new Item(itemValue);

        it.setX_coord(5);
        it.setY_coord(3);
    }

    @After
    public void tearDown() throws Exception {

        System.out.println("All tests passed");

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("aa",it.getName());
        System.out.println("Name test passed");
    }

    @Test
    public void testGetWeight() throws Exception {
        assertEquals(0.36,it.getWeight(),0.0);
        System.out.println("Weight test passed");
    }

    @Test
    public void testGetReward() throws Exception {
        assertEquals(12.78,it.getReward(),0.0);
        System.out.println("Reward passed");
    }

    @Test
    public void testGetX_coord() throws Exception {
        assert (it.getX_coord() == 5);
        System.out.println("x_coord test passed");
    }

    @Test
    public void testGetY_coord() throws Exception {
        assert (it.getY_coord() == 3);
        System.out.println("y_coord test passed");
    }

    @Test
    public void testSetName() throws Exception {
        it.setName("Lelouch");
        assert (it.getName().equals("Lelouch"));
        System.out.println("SetName test passed");
    }

    @Test
    public void testSetWeight() throws Exception {
        it.setWeight(11.1);
        assert (it.getWeight() == 11.1);
        System.out.println("SetWeight test passed");
    }

    @Test
    public void testSetReward() throws Exception {
        it.setReward(11.1);
        assert (it.getReward() == 11.1);
        System.out.println("SetReward test passed");
    }

    @Test
    public void testSetX_coord() throws Exception {
        it.setX_coord(4);
        assert (it.getX_coord() == 4);
        System.out.println("SetX_coord test passed");
    }

    @Test
    public void testSetY_coord() throws Exception {
        it.setY_coord(4);
        assert (it.getY_coord() == 4);
        System.out.println("SetY_coord test passed");
    }
}