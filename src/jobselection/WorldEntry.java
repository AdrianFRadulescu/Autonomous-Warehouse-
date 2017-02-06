package jobselection;

import jobselection.job.Job;
import jobselection.job.PickListSizePredictions;

import java.util.Set;

/**
 * A class containing the parameters describing
 * the distances and routes between different places in the world
 */

public class WorldEntry{

    public final Integer[][][][] worldDistances;
    public final String[][][][] worldRoutes;
    public Set<Job> cancellations;
    public PickListSizePredictions sizePredictions;

    protected WorldEntry(Integer[][][][] _distances,String[][][][] _routes){
        worldDistances = _distances;
        worldRoutes = _routes;

        sizePredictions = new PickListSizePredictions(70);
    }

    /**
     * Adds a cancelled job to the set
     * @param _job = the cancelled job
     */

    public void cancelJob(Job _job){
        cancellations.add(_job);
    }


}
