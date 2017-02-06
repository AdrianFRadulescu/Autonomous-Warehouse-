package jobselection.job;

import java.util.*;

/**
 * The class that solves the Traveling Salesman Problem for one robot
 *
 * Created by adrian_radulescu1997 on 20.03.2016.
 */
class SingleRobotWeightLimitTSP {

    private Integer[][][][] w_distances;

    private Set<Pick> frontier;
    private List<Pick> result;
    private List<Drop> drops;

    private Double weightLimit;
    private Double cost = Double.MAX_VALUE;
    private Integer resultCost = 0;

    private final Integer rx_coord;
    private final Integer ry_coord;

    protected SingleRobotWeightLimitTSP(Integer _rx_coord, Integer _ry_coord, Integer[][][][] _p_distances, Set<Pick> _frontier, List<Drop> _drops,Double _weightLimit){

        this.w_distances = _p_distances;

        this.frontier = _frontier;
        this.drops = _drops;


        this.rx_coord = _rx_coord;
        this.ry_coord = _ry_coord;

        this.weightLimit = _weightLimit;
    }

    /**
     * Solves the Traveling Salesman Problem for a single robot
     * using the Heuristic Approach
     */

    protected void solveTSPUsingHeuristicApproach(){

        result = new LinkedList<>();

        Pick closestPick = getClosest();
        result.add(closestPick);
        frontier.remove(closestPick);

        resultCost = distance(getClosestDroppingLocation(closestPick,drops), closestPick)
                + distance(rx_coord, ry_coord, closestPick);

        while(!frontier.isEmpty()){

            closestPick = getClosest(frontier,result);
            frontier.remove(closestPick);

            resultCost = insert(closestPick,result);
        }


        cost = resultCost.doubleValue();
    }

    /**
     * Get the closest pick to the robot
     */

    private Pick getClosest(){

        Pick closestPick = null;
        Integer closestPickDistance = Integer.MAX_VALUE;

        for(Pick p : frontier){
            if(closestPickDistance > distance(rx_coord,ry_coord,p)){
                closestPickDistance = distance(rx_coord,ry_coord,p);
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
     * Finds the closest dropping point for the given pick
     * @param p = the given pick
     * @param drops = the list of available dropping points
     * @return a Drop object representing the closest dropping location
     */

    private Drop getClosestDroppingLocation(Pick p , List<Drop> drops){

        Drop location = null;
        Integer smallestDistanceToDrop = Integer.MAX_VALUE;

        for(Drop d : drops){

            if(distance(d,p) < smallestDistanceToDrop){
                smallestDistanceToDrop = distance(d,p);
                location = d;
            }
        }

        return location;
    }


    /**
     * Inserts a pick into a list on the position that generates the lowest cost
     * @param p
     * @param list
     * @return
     */

    private Integer insert(Pick p,List<Pick> list){

        int insertPose = 0; // the position at which the pick will be inserted in the end


        //insert it at the front(initially)
        Integer auxCost = distance(p,list.get(0)) + resultCost
                - distance(rx_coord,ry_coord,list.get(0))
                + distance(rx_coord,ry_coord,p);

        //try insert it at the back

        Drop closestDrop = getClosestDroppingLocation(p,drops);

        if(auxCost > resultCost + distance(p,list.get(list.size()-1)) + distance(closestDrop,p) - distance(closestDrop,list.get(list.size()-1))){
            auxCost = resultCost + distance(p,list.get(list.size()-1))
                    + distance(closestDrop,p)
                    - distance(closestDrop,list.get(list.size()-1));
            insertPose = list.size();
        }



        //iterate over the list and try to insert it between two picks
        Double currentWeight = list.get(0).ITEM.getWeight() * list.get(0).PICK_COUNT;

        for(int i = 1; i < list.size(); i++){

            //the two picks between which we are trying to put the robot
            Pick prev = list.get(i-1);
            Pick next = list.get(i);

            if(auxCost > resultCost - distance(next,prev) + distance(p,prev) + distance(p,next) + checkWeight(currentWeight,p,prev,next)){
                auxCost = resultCost - distance(next,prev) + distance(p,prev) + distance(p,next) + checkWeight(currentWeight,p,prev,next);
                insertPose = i;
            }

            if(currentWeight + list.get(i).ITEM.getWeight() * list.get(i).PICK_COUNT > weightLimit)
                currentWeight = list.get(i).ITEM.getWeight() * list.get(i).PICK_COUNT;
            else
                currentWeight += list.get(i).ITEM.getWeight() * list.get(i).PICK_COUNT;
        }

        list.add(insertPose,p);

        return auxCost;
    }

    /**
     * Check whether we need to make a drop in case we insert p between left and right
     * Also check whether a drop was already planned between left and right
     * @param currentValue
     * @param p
     * @param left
     * @param right
     * @return
     */

    private Integer checkWeight(Double currentValue,Pick p,Pick left,Pick right){

        if(currentValue >= weightLimit){

            Drop dropPoint = getOptimalDropLocationForTwoPicks(left,p);
            return distance(dropPoint,left) + distance(dropPoint,p) - distance(left,p);

        } else if(currentValue + p.ITEM.getWeight() * p.PICK_COUNT < weightLimit){

            return 0;

        } else{

            Drop dropPoint = getOptimalDropLocationForTwoPicks(p,right);
            return distance(dropPoint,p) + distance(dropPoint,right) - distance(p,right);

        }
    }

    private Drop getOptimalDropLocationForTwoPicks(Pick left,Pick right){

        Drop location = null;
        Integer smallestDistanceToDrop = Integer.MAX_VALUE;

        for(Drop d : drops){

            if(distance(d,left) + distance(d,right) < smallestDistanceToDrop){
                smallestDistanceToDrop = distance(d,left) + distance(d,right);
                location = d;
            }
        }

        return location;


    }

    /**
     * @param first = a pick
     * @param second = a pick
     * @return the distance between the two picks
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

    /**
     * Calculates the distance between the location of a given drop and the location of a given pick
     * @param d = the given drop
     * @param p = the given pick
     * @return
     */

    private Integer distance(Drop d,Pick p){
        return w_distances[d.x_coord][d.y_coord][p.ITEM.getX_coord()][p.ITEM.getY_coord()];
    }

    /**
     * @return the minimum cost for picking all the items
     */

    protected Double getCost(){
        return cost;
    }

    /**
     * @return pickList
     */

    protected List<Pick> getPickList(){
        return result;
    }

}
