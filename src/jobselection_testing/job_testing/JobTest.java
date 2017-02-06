package jobselection_testing.job_testing;

import jobselection.JobSelectionMain;
import jobselection.WorldEntry;
import jobselection.item.ItemTable;
import jobselection.job.Drop;
import jobselection.job.Job;
import jobselection.job.Pick;
import jobselection.reading.FileContentReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by adrian_radulescu1997 on 04.03.2016.
 */
public class JobTest {

    private Job jobTest;

    private ItemTable items;

    private final String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";

    private String input;
    private String[] inputSplit;
    private String test;

    private WorldEntry world;

    private Double optimalCost = Double.MAX_VALUE;
    private Double worstCost = Double.MIN_VALUE;


    private Integer[] rx_coord;
    private Integer[] ry_coord;

    private LinkedList<Drop> drops;

    private JobSelectionMain mainClass;

    @Before
    public void set_up() throws Exception{

        input = "10002,af,4,ab,3,aj,3,bf,1,bc,1";

        items = FileContentReader.getItemFileContent(FOLDER_PATH + "items.csv");
        items = FileContentReader.addLocationsFileContent(FOLDER_PATH + "locations.csv",items);

        jobTest = new Job(input,items);

        inputSplit = input.split(",");

        mainClass = new JobSelectionMain();

        mainClass.run();

        world =mainClass.worldDistances();

        rx_coord = new Integer[1];
        ry_coord = new Integer[1];

        rx_coord[0] = items.get("ah").getX_coord();
        ry_coord[0] = items.get("ah").getY_coord();

        drops = new LinkedList<>();
        drops.add(new Drop("4,7"));


    }

    @After
    public void tear_down(){
        System.out.println(test+" test Passed");
    }

    @Test
    public void testAdd() throws Exception {
        test = "Add";
    }

    @Test
    public void testGetID() throws Exception {
        test = "ID";
        assert (jobTest.getID().equals(inputSplit[0]));
    }

    @Test
    public void testGetReward() throws Exception {

        test = "Reward";
        Double expected = 0.0;

        for(int i = 1; i < inputSplit.length; i += 2){
            expected += items.get(inputSplit[i]).getReward()
                        * Integer.parseInt(inputSplit[i+1]);
        }

        assert (expected.equals(jobTest.getReward()));

    }

    @Test
    public void testGetPickList() throws Exception {
        test = "PickList";
    }

    /**
     * Tests how close the heuristic approach is to the optimal cost
     * by comparing its result to a brute force optimal approach
     * @throws Exception
     */

    @Test
    public void testOrderPicking() throws Exception {

        test = "Pick Ordering";


        BufferedReader reader = new BufferedReader(new FileReader(FOLDER_PATH + "jobs.csv"));

        int count = 0;
        boolean ok = true;
        String line;

        System.out.println(System.currentTimeMillis());

        while((line = reader.readLine()) != null){

            jobTest = new Job(line,items);

            jobTest.orderPicking(world,items,rx_coord,ry_coord,drops);
            Double costTest = jobTest.getCost();

            backTracking(
                    0,jobTest.getPickList().size(),
                    new Pick[jobTest.getPickList().size()],
                    new BitSet(jobTest.getPickList().size()),
                    jobTest.getPickList()
            );


            assertTrue((costTest - optimalCost)/(worstCost - optimalCost) <= 0.4);


        }

        System.out.println(System.currentTimeMillis());

        assertTrue(ok);


    }

    @Test
    public void testCompareTo() throws Exception {

    }


    private Double solutionCost(Pick[] sol){

        Double result = world.worldDistances[sol[0].ITEM.getX_coord()][sol[0].ITEM.getY_coord()][rx_coord[0]][ry_coord[0]].doubleValue();

        for(int i = 0; i < sol.length - 1; i++){

            result += world.worldDistances[sol[i].ITEM.getX_coord()][sol[i].ITEM.getY_coord()][sol[i+1].ITEM.getX_coord()][sol[i+1].ITEM.getY_coord()];

        }

        result += world.worldDistances[sol[sol.length - 1].ITEM.getX_coord()][sol[sol.length - 1].ITEM.getY_coord()][drops.get(0).x_coord][drops.get(0).y_coord];

        return result;
    }

    /**
     * The method using backtracking for the brute force ordering algorithm
     * @param k = the size of the current version of the solution
     * @param n = the number of elements a solution must contain
     * @param viz = a BitSet indicating the elements that have been added to the current solution
     */

    private void backTracking(int k, int n, Pick[] sol, BitSet viz, List<Pick> picks){

        if(k == n){

            Double cost = solutionCost(sol);

            optimalCost = Math.min(cost,optimalCost);
            worstCost = Math.max(cost,worstCost);

            return;
        }

        int i = 0;
        for(Pick p : picks){

            if(!viz.get(i)){

                viz.set(i);
                sol[k] = p;

                backTracking(k+1,n,sol,viz,picks);

                viz.clear(i);
            }
            i++;
        }

    }

}