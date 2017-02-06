package jobselection.job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian_radulescu1997 on 23.03.2016.
 */
public class SingleRobotWeightLimitBruteForceTSP {

    private Integer[] rx_coord;
    private Integer[] ry_coord;

    private List<Pick> frontier;

    private Integer optimalCost;
    private Integer worstPossibleCost;

    private ArrayList<Pick> solution;
    private ArrayList<Pick> optimalSolution;

    public SingleRobotWeightLimitBruteForceTSP(Integer[] _rx_coord, Integer[] _ry_coord, List<Pick> _frontier){

    }

}
