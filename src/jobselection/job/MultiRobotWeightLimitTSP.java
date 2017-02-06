package jobselection.job;

import javafx.beans.DefaultProperty;

import java.util.*;

/**
 * The class representing the object which is responsible for the
 * allocation of picks for the robots when executing a job
 * It does that by solving the Traveling Salesman Problem for multiple robots
 * and then selecting the cost of the job as being the highest of the
 * individual costs for each robot to finish his job
 * Created by Adrian Radulescu on 19.03.2016.
 */

@SuppressWarnings("unchecked")
public class MultiRobotWeightLimitTSP {

    private Integer [][][][] w_distances;

    private Set<Pick> frontier;
    private List<Drop> drops;

    private Double resultCost;

    private final Integer[] rx_coord;
    private final Integer[] ry_coord;

    private ArrayList<List<Pick>> robotPicks;

    private Double weightLimit; // the limit of the weight a robot can transport

    /**
     * Construct the solver object for the capacitated TSP for multiple robots
     * @param _rx_coord = the x coordinates of the robots
     * @param _ry_coord = the y coordinate of the robots
     * @param _w_distances = the distances between different places in the world of the robots
     * @param _frontier = the frontier of picks the robots must be assigned
     * @param _drops = the droping locations for those picks
     */

    protected MultiRobotWeightLimitTSP(Integer[] _rx_coord, Integer[] _ry_coord, Integer[][][][] _w_distances, Set<Pick> _frontier, List<Drop> _drops,Double _weightLimit){

        this.w_distances = _w_distances;

        this.frontier = _frontier;
        this.drops = _drops;

        this.rx_coord = _rx_coord;
        this.ry_coord = _ry_coord;

        this.weightLimit = _weightLimit;

        robotPicks = new ArrayList<>();

        for(int i = 0; i < rx_coord.length; i++){
            robotPicks.add(new ArrayList<>());

        }

        this.resultCost = 0.0;

    }


    /**
     * The class that has the features for the object coordinating
     * the auctioning process
     */

    @DefaultProperty("Auctioneer")
    private class Auctioneer{

        private Set<Pick> wares; // the set of picks that will be auctioned to the robots
        private PriorityQueue<Bid> bids;


        private ArrayList<List<Pick>> r_picks;
        private LinkedList<Double> r_picksCost; // the cost to do the picks from a robot's pickList

        /**
         * Construct the Auctioner object
         * @param _wares = the set of picks that will be auctioned
         */

        protected Auctioneer(Set<Pick> _wares){

            this.wares = (HashSet<Pick>)((HashSet<Pick>)_wares).clone();

            r_picks = new ArrayList<>();
            r_picksCost = new LinkedList<>();

            for(int i = 0; i < rx_coord.length; i++) {
                r_picks.add(new LinkedList<>());
                r_picksCost.add(0.0);
            }

            bids = new PriorityQueue<>();
        }

        /**
         * Starts and coordinates the auction for picks
         */
        protected void commenceAuction(){

            while(!wares.isEmpty()){

                //for each robot bid on the closest pick and insert the bids into a queue
                for (int i = 0; i < rx_coord.length; i++){

                    bids.add(new Bid(i, rx_coord[i], ry_coord[i], r_picks.get(i), wares));
                }

                //extract the head of the queue and update the status of the winning robot
                wares.remove(bids.peek().bidPick);

                r_picks.set(bids.peek().r_id,bids.peek().resultingPickList);

                r_picksCost.set(bids.peek().r_id, bids.peek().bidValue);

                //after that clear the queue
                bids.clear();
            }


        }
    }

    /**
     * The class representing the bid a robot offers during an auction
     *
     */

    private class Bid implements Comparable<Bid>{

        // the external parameters of the bid a robot makes
        private Integer r_id;
        private Pick bidPick;
        private Double bidValue;

        // the internal parameters of the bid(form which the external ones are calculated)
        private Integer rx_coord; // the coordinates of the bidding robot
        private Integer ry_coord;

        private List<Pick> currentPicks; // the list of picks this robot will surely execute
        private List<Pick> resultingPickList; // the resulting list(if the bid were to be the winning one)
        private Set<Pick> auctionWares; // the frontier of picks form which a robot can make a bid

        private Pick auctionProduct; // pick the robot will auction for

        /**
         * Constructs a bid made by a robot during the auctioning
         * @param _r_id = the id of the bidding robot
         * @param _rx_coord = the x coordinate of the bidding robot
         * @param _ry_coord = the y coordinate of the bidding robot
         * @param _currentPicks = the list of the picks the robot has already won
         * @param _wares = the set of picks that are available
         */

        protected Bid(int _r_id,Integer _rx_coord,Integer _ry_coord,List<Pick> _currentPicks,Set<Pick> _wares){

            this.r_id = _r_id;
            this.rx_coord = _rx_coord;
            this.ry_coord = _ry_coord;
            this.currentPicks = _currentPicks;
            this.auctionWares = _wares;

            bidPick = null;
            bidValue = Double.MAX_VALUE;


            for (Pick p : auctionWares){

                //check all the available picks and select the one that costs the least for bidding
                Set<Pick> testFrontier = new HashSet<>(currentPicks);
                testFrontier.add(p);

                SingleRobotWeightLimitTSP organizer = new SingleRobotWeightLimitTSP(rx_coord, ry_coord, w_distances, testFrontier, drops,weightLimit);

                organizer.solveTSPUsingHeuristicApproach();

                //compare with the current option
                if (organizer.getCost() < bidValue) {
                    bidValue = organizer.getCost();
                    bidPick = p;
                    resultingPickList = organizer.getPickList();
                }

            }

        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         */
        @Override
        public int compareTo(Bid o) {
            return bidValue.compareTo(o.bidValue);
        }
    }


    /**
     * Solve the TSP capacitated problem for more than one robot
     * using auctioning.
     * After the auction is over iterate over the cost array
     * for the Auctioneer class(containing the cost for each robot to finish the picks it auctioned for)
     * and select the highest one as this is the only one that actually matters
     * since the other robots will have most likely finished their tasks until
     * the robot with the highest cost will finish
     */

    public void solveTSPUsingAuctioning(){

        Auctioneer NoBribesHere = new Auctioneer(frontier);

        NoBribesHere.commenceAuction();

        robotPicks =  NoBribesHere.r_picks;

        //get the highest cost for a robot to perform his task
        for(Double i : NoBribesHere.r_picksCost){
            resultCost = Math.max(resultCost,i);
        }


    }

    /**
     * @return a list for each robot containing the picks he has to do
     */

    protected ArrayList<List<Pick>> getPickListForAllRobots(){
        return robotPicks;
    }


    public Double getResultCost() {
        return resultCost;
    }
}
