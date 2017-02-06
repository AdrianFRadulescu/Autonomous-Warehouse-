package jobselection;

/**
 * A class containing the parameters describing
 * the distances and routes between different places in the world
 */

public class WorldEntry{

    public final Integer[][][][] worldDistances;
    public final String[][][][] worldRoutes;

    protected WorldEntry(Integer[][][][] _distances,String[][][][] _routes){
        worldDistances = _distances;
        worldRoutes = _routes;
    }

}
