package jobselection.job;

import jobselection.WorldEntry;
import jobselection.item.ItemTable;

import java.util.*;

/**
 * The class representing the job object and its behaviors
 * Created by Adrian Radulescu on 24.02.2016.
 */
public class Job implements Comparable<Job> {

    private String ID;

    private Double reward = 0.0;
    private Double cost = 1.0;
    private Double totalWeight = 0.0;

    private List<Pick> pickList;

    private ArrayList<List<Pick>> robotPicks;

    /**
     * Creates a new job
     * @param _args = the string containing the job description
     * @param _itemTable = the table containing the descriptions for all
     *                   the items on the greed
     */

    public Job(String _args, ItemTable _itemTable){

        String[] argsSplit = _args.split(",");

        this.ID = argsSplit[0];
        this.pickList = new ArrayList<>();

        for(int i = 1; i < argsSplit.length; i+= 2){
            pickList.add(
                    new Pick(
                            _itemTable.get(argsSplit[i]),
                            Integer.parseInt(argsSplit[i+1])
                    )
            );
        }

        pickList.stream().forEach(e -> {reward += e.ITEM.getReward() * e.PICK_COUNT; totalWeight += e.ITEM.getWeight() * e.PICK_COUNT;});

        robotPicks = new ArrayList<>();
    }

    /**
     * adds a pick to the list of picks(doc subject to change)
     */

    public void add(Pick _pick){
        pickList.add(_pick);
    }

    /**
     * Returns the ID of the Job
     * @return
     */

    public String getID(){
        return ID;
    }

    /**
     * @return the reward of the job
     */

    public Double getReward(){
        return reward;
    }

    /**
     * @return the minimum cost of the job
     */

    public Double getCost() {
        return cost;
    }

    /**
     * @return the total weight of the items
     */

    public Double getTotalWeight() {
        return totalWeight;
    }

    /**
     * @return the list of picks in the job
     */

    public List<Pick> getPickList(){
        return this.pickList;
    }

    /**
     * @return and ArrayList<ArrayList<Picks>> containing the picks for each robot
     */

    public ArrayList<List<Pick>> getRobotPicks() {
        return robotPicks;
    }

    /**
     * Orders the picks in this job based on the information about the world
     * For this purpose it is using the Traveling Salesman Problem for multiple robots
     * @param _world = the entry containing the data about the world in which the robots are moving
     * @param rx_coord = the array containing the x coordinates of the robots
     * @param ry_coord = the array containing the y coordinates of the robots
     * @param _drops = the list of dropping locations
     */

    public void orderPicking(WorldEntry _world,Integer[] rx_coord,Integer[] ry_coord,LinkedList<Drop> _drops){

        Set<Pick> frontier = new HashSet<>();

        for (Pick p : pickList) {
            frontier.add(p);
        }

        robotPicks.clear();

        MultiRobotWeightLimitTSP organizer = new MultiRobotWeightLimitTSP(rx_coord,ry_coord,_world.worldDistances,frontier,_drops,50.0);

        organizer.solveTSPUsingAuctioning();

        robotPicks = organizer.getPickListForAllRobots();
        cost = organizer.getResultCost();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */

    @Override
    public int compareTo(Job o) {

        if(((Double)(reward/cost)).compareTo(o.getReward()/o.getCost()) == 0){
            return (-1) * ((Double)(reward/totalWeight)).compareTo(o.getReward()/o.getTotalWeight());
        }else
            return (-1)*((Double)(reward/cost)).compareTo(o.getReward()/o.getCost());
    }

    @Override
    public String toString(){
        //return ID + " " + reward + " " + cost + "\n" + pickList.toString() ;

        String msg = "";

        int i = 1;
        for(List<Pick> list : robotPicks){

            msg = msg + "number " + i + " " + list + "\n";
            i++;
        }

        return ID + " " + reward + " " + cost + "\n" + msg;
    }

}
