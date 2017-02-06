package jobselection.job;

import jobselection.WorldEntry;
import jobselection.item.ItemTable;

import java.util.*;

/**
 * Created by adrian_radulescu1997 on 24.02.2016.
 */
public class Job implements Comparable<Job> {

    private String ID;

    private Double reward = 0.0;
    private Double cost = 1.0;

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
        this.pickList = new LinkedList<>();

        for(int i = 1; i < argsSplit.length; i+= 2){
            pickList.add(
                    new Pick(
                            _itemTable.get(argsSplit[i]),
                            Integer.parseInt(argsSplit[i+1])
                    )
            );
        }

        pickList.stream().forEach(e -> reward += e.ITEM.getReward() * e.PICK_COUNT);

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
     * @return the list of picks in the job
     */

    public List<Pick> getPickList(){
        return this.pickList;
    }

    /**
     * orders the picks in this job based on the information about the world
     */

    public void orderPicking(WorldEntry _world,ItemTable _items,Integer[] rx_coord,Integer[] ry_coord,LinkedList<Drop> drops){

        if(rx_coord.length == 1 && rx_coord[0].equals(-1)){
            rx_coord[0] = pickList.get(0).ITEM.getX_coord();
            ry_coord[0] = pickList.get(0).ITEM.getY_coord();
        }
        Set<Pick> frontier = new HashSet<>();

        for (Pick p : pickList) {
            frontier.add(p);
        }

        SingleRobotTSP organizer = new SingleRobotTSP(rx_coord, ry_coord, _world.worldDistances, frontier, drops);

        organizer.solveTSPUsingHeuristicApproach();
        //organizer.solveTSPUingExahustiveSearch();


        pickList = organizer.getPickList();
        cost = organizer.getCost();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */

    @Override
    public int compareTo(Job o) {
        return ((Double)(reward/cost)).compareTo((Double)(o.getReward()/o.getCost()));
    }

    @Override
    public String toString(){
        return ID + " " + reward + " " + cost + " " + pickList.toString() ;
    }

}
