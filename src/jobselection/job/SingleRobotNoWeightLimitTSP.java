package jobselection.job;

import java.util.*;


/**
 *  The class that solves the Traveling Salesman Problem for a single
 *  robot and no weight limit
 */
class SingleRobotNoWeightLimitTSP {

    private Integer[][][][] w_distances;

    private Set<Pick> frontier;
    private List<Pick> result;
    private List<Drop> drops;

    private Double cost = Double.MAX_VALUE;
    private Integer resultCost = 0;

    private Integer rx_coord;
    private Integer ry_coord;

    protected SingleRobotNoWeightLimitTSP(Integer _rx_coord, Integer _ry_coord, Integer[][][][] _p_distances, Set<Pick> _frontier, List<Drop> _drops){

        this.w_distances = _p_distances;

        this.frontier = _frontier;
        this.drops = _drops;


        this.rx_coord = _rx_coord;
        this.ry_coord = _ry_coord;

    }

    /**
     * Solves the Traveling Salesman Problem for n robots
     * using Exhaustive Search
     */

    protected void solveTSPUsingExhaustiveSearch(){

        backTracking(
                0,frontier.size(),
                new Pick[frontier.size()],
                new BitSet(frontier.size()),
                frontier
        );

    }



    private Double solutionCost(Pick[] sol){

        Double costResult = w_distances[sol[0].ITEM.getX_coord()][sol[0].ITEM.getY_coord()][rx_coord][ry_coord].doubleValue();

        for(int i = 0; i < sol.length - 1; i++){

            costResult += w_distances[sol[i].ITEM.getX_coord()][sol[i].ITEM.getY_coord()][sol[i+1].ITEM.getX_coord()][sol[i+1].ITEM.getY_coord()];

        }

        costResult += w_distances[sol[sol.length - 1].ITEM.getX_coord()][sol[sol.length - 1].ITEM.getY_coord()][getClosestDroppingLocation(sol[sol.length-1],drops).x_coord][getClosestDroppingLocation(sol[sol.length-1],drops).y_coord];

        return costResult;
    }

    /**
     * The method using backtracking for the brute force ordering algorithm
     * @param k = the size of the current version of the solution
     * @param n = the number of elements a solution must contain
     * @param viz = a BitSet indicating the elements that have been added to the current solution
     */

    private void backTracking(int k, int n, Pick[] sol, BitSet viz, Collection<Pick> picks){

        if(k == n){

            Double currentCost = solutionCost(sol);

            if(cost > currentCost){
                cost = currentCost;
            }

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



    /**
     * Solves the Traveling Salesman Problem for a single robot
     * using the Heuristic Approach
     */

    protected void solveTSPUsingHeuristicApproach(){

        result = new LinkedList<>();

        Pick closestPick = getClosest();
        result.add(closestPick);
        frontier.remove(closestPick);

        try {

            resultCost = distance(getClosestDroppingLocation(closestPick,drops), closestPick)
                    + distance(rx_coord, ry_coord, closestPick);
        }catch (Exception e){
            System.out.println(drops);
            e.printStackTrace();
        }

        while(!frontier.isEmpty()){

            closestPick = getClosest(frontier,result);
            frontier.remove(closestPick);

            resultCost = insert(closestPick,result);
        }


        cost = (Double) resultCost.doubleValue();
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

        Integer auxCost = Integer.MAX_VALUE;

        int insertPose = 0; // the position at which the pick will be inserted in the end


        //insert it at the front(initially)
        auxCost = distance(p,list.get(0)) + resultCost
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
