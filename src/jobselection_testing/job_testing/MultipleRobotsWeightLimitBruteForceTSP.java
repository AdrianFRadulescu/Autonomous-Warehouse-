package jobselection_testing.job_testing;

import jobselection.job.Drop;
import jobselection.job.Pick;

import java.util.*;

/**
 * A class that is meant for testing the heuristic approach
 * It will generate the optimal distribution of picks for a job
 * by testing all the possible distributions
 * Created by adrian_radulescu1997 on 23.03.2016.
 */
public class MultipleRobotsWeightLimitBruteForceTSP {

    private Double threshold;

    private Integer[] rx_coord;
    private Integer[] ry_coord;

    private List<Pick> frontier; // the list of picks in the job
    private List<Drop> drops; // the list of available dropping points

    private Double optimalCost;
    private Double worstPossibleCost;

    private Integer[][][][] w_distances;

    private ArrayList< Stack<Pick> > solution;
    private ArrayList< Stack<Pick> > optimalSolution;

    private BitSet used; // a BitSet which keeps the evidence
                        // on the picks that have been that have been already distributed


    /**
     * Construct the solver object for the Weight limited TSP for multiple robots
     * @param _rx_coord     = the array containing the x coordinates of the robots
     * @param _ry_coord     = the array containing the y coordinates of the robots
     * @param _frontier     = the list of picks in the job
     * @param _drops        = the dropping
     * @param _w_distances  = the distances between different places in the world in which the robots are moving
     * @param _threshold     = the weight limit for this problem
     */

    public MultipleRobotsWeightLimitBruteForceTSP(Integer[] _rx_coord, Integer[] _ry_coord, List<Pick> _frontier,List<Drop> _drops, Integer[][][][] _w_distances, Double _threshold){

        this.rx_coord = _rx_coord;
        this.ry_coord = _ry_coord;

        this.frontier = _frontier;
        this.drops = _drops;

        this.optimalCost = Double.MAX_VALUE;
        this.worstPossibleCost = Double.MIN_VALUE;

        this.w_distances = _w_distances;

        //initialise the solution array for given number of robots
        this.solution = new ArrayList<>();

        for(int i = 0; i < rx_coord.length; i++){
            solution.add(new Stack<>());
        }

        //initialise the BitSet that keeps track of the available picks
        this.used = new BitSet(frontier.size()+2);

        this.threshold = _threshold;

    }

    /**
     * Calls the other methods for solving the TSP
     */

    public void solveTSP(){


    }

    /**
     * The method that implements the exhaustive backtracking search algorithm
     * to solve the TSP by searching for the optimal distribution of picks
     * among the robots
     * @param k = the current number of picks that have already been assigned
     *          at this step(of course since the lists are indexed from 0 ,
     *          the real number of assigned picks is k+1)
     * @param n = the total number of picks in the job
     */

    private void multiRobotWeightLimitBackTracking(int k, int n) throws Exception{

        if(k == n){

            /*
             * A new solution has been generated
             * Calculate its cost and compare it with optimalCost and worstPossibleCost
             */

            Double solCost = solutionCost(solution).doubleValue();

            optimalCost = Math.min(optimalCost,solCost);
            worstPossibleCost = Math.max(worstPossibleCost,solCost);

            return;
        }

        for(int i = 0; i < frontier.size(); i++){

            if(used.get(i))
                continue;

            Pick p = frontier.get(i);


            //try to put the item to any robot

            for(int j = 0; j < solution.size(); j++){

                    used.set(i);
                    solution.get(i).push(p);

                    multiRobotWeightLimitBackTracking(k+1,n);

                    solution.get(i).pop();
                    used.clear(i);

            }

        }

    }


    /**
     * @param solution = the current distribution of picks among robots
     * @return highest of the cost of each robot's stack
     */

    private Integer solutionCost(ArrayList<Stack<Pick>> solution){

        int highestCost = 0;

        for(Stack<Pick> st : solution){

            Double carryWeight = 0.0;
            Double cost = 0.0;

            Pick previous = st.get(0);

            for(Pick it : st){

                if(carryWeight + it.ITEM.getWeight() * it.PICK_COUNT > threshold){
                    cost += lowestDroppingCost(previous,it).doubleValue();
                    carryWeight = 0.0;
                }else{
                    cost += distance(previous,it).doubleValue();
                }

                previous = it;
            }


        }

        return highestCost;
    }

    /**
     * Calculates the optimal cost for a drop between picks
     * @param prev
     * @param next
     * @return
     */

    private Integer lowestDroppingCost(Pick prev,Pick next){

        Drop location = null;
        Integer lowestCost = Integer.MAX_VALUE;

        for(Drop d : drops){

            if(distance(d,prev) + distance(d,next) < lowestCost){
                lowestCost = distance(d,prev) + distance(d,next);
                location = d;
            }

        }

        return lowestCost;
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
     * @param first = a pick
     * @param second = a pick
     * @return the distance between the two picks
     */

    private Integer distance(Pick first,Pick second){
        return w_distances[first.ITEM.getX_coord()][first.ITEM.getY_coord()][second.ITEM.getX_coord()][second.ITEM.getY_coord()];
    }

    /**
     * Returns the lowest possible cost a solution might reach
     * @return optimalCost
     */

    public Double getOptimalCost() {
        return optimalCost;
    }

    /**
     * Returns the highest possible cost a solution might reach
     * @return worstPossibleCost
     */

    public Double getWorstPossibleCost() {
        return worstPossibleCost;
    }

    /**
     * Returns the optimal distribution of picks
     * @return
     */

    public ArrayList<Stack<Pick>> getOptimalSolution() {
        return optimalSolution;
    }
}
