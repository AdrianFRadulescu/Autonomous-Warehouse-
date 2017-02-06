package jobselection.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by adrian_radulescu1997 on 19.03.2016.
 */
class MultiRobotWeightLimitTSP {

    private Integer [][][][] w_distances;

    private Set<Pick> frontier;
    private List<Pick> result;
    private List<Drop> drops;

    private Double cost = Double.MAX_VALUE;
    private Integer resultCost = 0;

    private Integer[] rx_coord;
    private Integer[] ry_coord;

    private ArrayList<List<Pick>> robotPicks;

    private Double weightLimit; // the limit of the weight a robot can transport

    protected MultiRobotWeightLimitTSP(Integer[] _rx_coord, Integer[] _ry_coord, Integer[][][][] _p_distances, Set<Pick> _frontier, List<Drop> _drops){

        this.w_distances = _p_distances;

        this.frontier = _frontier;
        this.drops = _drops;


        this.rx_coord = _rx_coord;
        this.ry_coord = _ry_coord;

        robotPicks = new ArrayList<>();

    }


    /**
     * The class that stores the features for the object coordinating
     * the auctioning process
     */

    private class Auctioner{

        public void selectWinnerFromBidders(){

        }

    }

    /**
     * The class representing the bid a robot offers during an auction
     *
     */

    private class Bid{

        // the external parameters of the bid a robot makes
        private Pick bidPick;
        private Double bidValue;

        // the internal parameters of the bid(form which the external ones are calculated)
        private Integer rx_coord; // the coordinates of the bidding robot
        private Integer ry_coord;

        private Double currentWeight; // the weight of the items the robot is carrying till now
        private List<Pick> currentPicks; // the list of picks this robot will surely execute
        private Set<Pick> frontier; // the frontier of picks form which a robot can make a bid

        private Pick auctionProduct; // pick the robot will auction for

        /**
         * Constructs a bid made by a robot during the auctioning
         * @param _rx_coord
         * @param _ry_coord
         * @param _currentWeight
         * @param _currentPicks
         */

        protected Bid(Integer _rx_coord,Integer _ry_coord,Double _currentWeight,List<Pick> _currentPicks,Set<Pick> _frontier){

            this.rx_coord = _rx_coord;
            this.ry_coord = _ry_coord;
            this.currentWeight = _currentWeight;
            this.currentPicks = _currentPicks;
            this.frontier = _frontier;

            bidPick = null;
            bidValue = Double.MAX_VALUE;

            for (Pick p : frontier){

                Double newCost =


            }


        }



        protected Pick getBidPick() {
            return bidPick;
        }

        protected Double getBidValue() {
            return bidValue;
        }

    }


    /**
     * Solve the TSP capacitated problem for more than one robot
     * using auctioning
     */

    public void solveTSPUsingAuctioning(){

    }

    /**
     * Get the closeset pick to the robot
     * @param r_number = the number of the robot
     * @return
     */

    private Pick getClosest(int r_number){

        Pick closestPick = null;
        Integer closestPickDistance = Integer.MAX_VALUE;

        for(Pick p : frontier){
            if(closestPickDistance > distance(rx_coord[r_number],ry_coord[r_number],p)){
                closestPickDistance = distance(rx_coord[r_number],ry_coord[r_number],p);
                closestPick = p;
            }
        }

        return closestPick;
    }

    /**
     * Get the pick that is the closest to one of the picks in the result
     * @param _from = the set of picks form which we take the closest pick to
     * the result set
     * @param _to = the list in which we will put the closest pick
     * @return
     */

    private Pick getClosest(Set<Pick> _from,List<Pick> _to){

        Pick closestPick = null;
        Integer closestPickDistance = Integer.MAX_VALUE;

        for(Pick fp : _from){

            for(Pick tp: _to){

                if(closestPickDistance > distance(fp,tp)){
                    closestPickDistance = distance(fp,tp);
                    closestPick = fp;
                }

            }

        }

        return closestPick;
    }

    /**
     * Inserts a pick into a list on the position that generates the lowest cost
     * @param p
     * @param list
     * @return
     */

    private Integer insert(Pick p,List<Pick> list){

        Integer auxCost = Integer.MAX_VALUE;

        int insertPose = 0; // the position at which the pick will be inserted in the end


        //insert it at the front(initially)
        auxCost = distance(p,list.get(0)) + resultCost
                - distance(rx_coord[0],ry_coord[0],list.get(0))
                + distance(rx_coord[0],ry_coord[0],p);

        //try insert it at the back
        if(auxCost > resultCost + distance(p,list.get(list.size()-1)) + distance(drops.get(0),p) - distance(drops.get(0),list.get(list.size()-1))){
            auxCost = resultCost + distance(p,list.get(list.size()-1))
                    + distance(drops.get(0),p)
                    - distance(drops.get(0),list.get(list.size()-1));
            insertPose = list.size();
        }



        //iterate over the list and try to insert it between two picks

        for(int i = 1; i < list.size(); i++){

            //the two picks between which we are trying to put the robot
            Pick prev = list.get(i-1);
            Pick next = list.get(i);

            if(auxCost > resultCost - distance(next,prev) + distance(p,prev) + distance(p,next)){
                auxCost = resultCost - distance(next,prev) + distance(p,prev) + distance(p,next);
                insertPose = i;
            }

        }

        list.add(insertPose,p);

        return auxCost;
    }

    /**
     * @param first = a pick
     * @param second = a pick
     * @return the distace between the two picks
     */

    private Integer distance(Pick first,Pick second){
        return w_distances[first.ITEM.getX_coord()][first.ITEM.getY_coord()][second.ITEM.getX_coord()][second.ITEM.getY_coord()];
    }

    /**
     * Calculates the distance between robot and pick
     * @param rx_coord
     * @param ry_coord
     * @param _pick
     * @return
     */

    private Integer distance(Integer rx_coord,Integer ry_coord,Pick _pick){
        return w_distances[rx_coord][ry_coord][_pick.ITEM.getX_coord()][_pick.ITEM.getY_coord()];
    }


    private Integer distance(Drop d,Pick p){
        return w_distances[d.x_coord][d.y_coord][p.ITEM.getX_coord()][p.ITEM.getY_coord()];
    }

    /**
     * @return a list for each robot containing the picks he has to do
     */

    protected ArrayList<List<Pick>> getPickListForEachRobot(){
        return robotPicks;
    }

}
