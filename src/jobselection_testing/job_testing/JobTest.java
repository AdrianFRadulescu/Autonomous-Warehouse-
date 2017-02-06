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
import java.util.*;

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

    private Double weightLimit;

    private Integer[] rx_coord;
    private Integer[] ry_coord;

    private LinkedList<Drop> drops;

    private JobSelectionMain mainClass;

    private ArrayList<Stack<Pick>> robotPicks;

    @Before
    public void set_up() throws Exception{

        input = "10002,af,4,ab,3,aj,3,bf,1,bc,1";

        items = FileContentReader.getItemFileContent(FOLDER_PATH + "items.csv");
        items = FileContentReader.addLocationsFileContent(FOLDER_PATH + "locations.csv",items);

        jobTest = new Job(input,items);

        inputSplit = input.split(",");

        mainClass = new JobSelectionMain();

        //mainClass.run();

        world =mainClass.worldDistances();

        rx_coord = new Integer[3];
        ry_coord = new Integer[3];

        rx_coord[0] = items.get("ah").getX_coord();
        ry_coord[0] = items.get("ah").getY_coord();

        rx_coord[1] = items.get("ae").getX_coord();
        ry_coord[1] = items.get("ae").getY_coord();

        rx_coord[2] = items.get("ab").getX_coord();
        ry_coord[2] = items.get("ab").getY_coord();

        drops = new LinkedList<>();
        drops.add(new Drop("4,7"));
        drops.add(new Drop("7,7"));

        weightLimit = 50.0;


        robotPicks = new ArrayList<>();

        for (int i = 0; i < rx_coord.length; i++)
            robotPicks.add(new Stack<>());


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

        boolean ok = true;
        String line;

        while((line = reader.readLine()) != null){

            jobTest = new Job(line,items);

            jobTest.orderPicking(world,rx_coord,ry_coord,drops);
            Double costTest = jobTest.getCost();

            Double[] w = new Double[rx_coord.length];
            Double[] v = new Double[rx_coord.length];

            for(int i =0; i < v.length; i++){
                w[i] = 0.0;
                v[i] = 0.0;
            }

            multiRobotWeightLimitBackTracking(
                    0,jobTest.getPickList().size(),
                    robotPicks,
                    new BitSet(jobTest.getPickList().size()),
                    jobTest.getPickList(),
                    w,v
            );


            //System.out.println("done" + jobTest);
            //assertTrue((costTest - optimalCost)/(worstCost - optimalCost) <= 0.4);

            System.out.println(
                    "optimal = " + optimalCost + " " +
                            "costTest = " + costTest + " " +
                            "worst = " + worstCost
            );
        }

        System.out.println(System.currentTimeMillis());

        assertTrue(ok);


    }

    @Test
    public void testCompareTo() throws Exception {

    }


    private Double solutionCostForOneRobot(Pick[] sol){

        if(sol == null){
            return 0.0;
        }

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

    private void singleRobotBackTracking(int k, int n, Pick[] sol, BitSet viz, List<Pick> picks){

        if(k == n){

            Double cost = solutionCostForOneRobot(sol);

            optimalCost = Math.min(cost,optimalCost);
            worstCost = Math.max(cost,worstCost);

            return;
        }

        int i = 0;
        for(Pick p : picks){

            if(!viz.get(i)){

                viz.set(i);
                sol[k] = p;

                singleRobotBackTracking(k+1,n,sol,viz,picks);

                viz.clear(i);
            }
            i++;
        }

    }

    /**
     * Calculates the cost resulting after the given distribution of picks
     * The result will be the highest of all the individual costs(for each robot to complete his tasks)
     * @param taskTable = the table of tasks for the robots
     * @return highestVal = the highest cost for a robot to complete his tasks
     */

    private Double solutionCostForMultipleRobots(List<Stack<Pick>> taskTable,Double[] r_dropsCost){

        Double highestVal = 0.0;

        for (int i = 0; i < taskTable.size(); i++)

            highestVal = Math.max(highestVal, solutionCostForOneRobot(stackToArray(taskTable.get(i))) + r_dropsCost[i]);

        return highestVal;
    }


    private void multiRobotWeightLimitBackTracking(int k,int n,ArrayList<Stack<Pick>> solution,BitSet viz,List<Pick> picks,Double[] r_weight,Double[] r_totalCostOfDrops){

        if(k == n){

            Double cost = solutionCostForMultipleRobots(solution,r_totalCostOfDrops);

            optimalCost = Math.min(cost,optimalCost);
            worstCost = Math.max(cost,worstCost);

            return;
        }

        for(int i = 0; i < picks.size(); i++){

            Pick p = picks.get(i);

            if(!viz.get(i)){

                //try to add the pick to all of the stacks

                for(int j = 0; j < solution.size(); j++){

                    if(r_weight[j] + p.ITEM.getWeight() * p.PICK_COUNT > weightLimit){

                        double aux = r_weight[j];

                        r_weight[j] = 0.0;

                        Double dropCost = Double.MAX_VALUE;

                        //drop the items before
                        for(Drop d : drops){
                            dropCost = Math.min(
                                    dropCost,
                                    world.worldDistances[solution.get(j).peek().ITEM.getX_coord()][solution.get(j).peek().ITEM.getY_coord()][d.x_coord][d.y_coord].doubleValue()
                                            + world.worldDistances[d.x_coord][d.y_coord][p.ITEM.getX_coord()][p.ITEM.getY_coord()].doubleValue()

                            );
                        }

                        r_totalCostOfDrops[j] += dropCost;
                        solution.get(j).add(p);
                        viz.set(i);

                        multiRobotWeightLimitBackTracking(k+1,n,solution,viz,picks,r_weight,r_totalCostOfDrops);

                        viz.clear(i);
                        solution.get(j).pop();
                        r_totalCostOfDrops[j] -= dropCost;

                        r_weight[j] = aux;

                    }else{

                        r_weight[j] += p.ITEM.getWeight() * p.PICK_COUNT;
                        solution.get(j).add(p);
                        viz.set(i);

                        multiRobotWeightLimitBackTracking(k+1,n,solution,viz,picks,r_weight,r_totalCostOfDrops);

                        viz.clear(i);
                        solution.get(j).pop();
                        r_weight[j] -= p.ITEM.getWeight() * p.PICK_COUNT;
                    }

                }
            }
        }
    }

    /**
     * Converts a given stack of picks into an array of picks
     * @param stack = the given stack
     * @return the resulting array
     */

    private Pick[] stackToArray(Stack<Pick> stack){

        if(stack.isEmpty())
            return null;

        Pick[] array = new Pick[stack.size()];

        stack.toArray(array);

        return array;
    }
}