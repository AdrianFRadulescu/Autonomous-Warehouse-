package jobselection;

import jobselection.item.ItemTable;
import jobselection.job.Drop;
import jobselection.job.Job;
import jobselection.job.JobHashTable;
import jobselection.job.JobTable;
import jobselection.reading.InputReader;
import routeplanning.AStar;

import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * The Main class for the JobSelection part of the project
 * It first takes the information about the world(the dimentions of the world) from the central part of the system
 * then calls the RoutePlanning in order to calculate the shortest ways between
 * different places
 * Secondly, it uses those datas in order to select the most rewarding jobs
 * Thirdly,it enters a while loop in which it receives messages about the status of the assigned jobs
 *
 */

@SuppressWarnings("unchecked")
public class JobSelectionMain extends Thread {

    private ItemTable itemTable;
    private JobTable jobTable;
    private PriorityQueue<Job> jobQueue; //the collection in which the jobs are stored

    private boolean suppressed; //indicates whether this thread should continue or not

    public JobSelectionMain(){
        this.jobQueue = new PriorityQueue<Job>();
    }

    private int worldXSize = 14; // the parameters indicating the sizes of the world
    private int worldYSize = 14;

    private WorldEntry world; //the data type containing the information
                             //about the world in which the robot is moving

    private Integer[] rx_coord; //the parameters indicating the x & y coordinates of the robot(s)
    private Integer[] ry_coord;

    private LinkedList<Drop> drops;

    /**
     * The part that interacts with the system
     */

    @Override
    public void run(){

        //calculate the shortest ways between places
        world = worldDistances();

        InputReader.main(this,world);

        /*
        while(!suppressed){

        }
        * */


    }

    /**
     * Set the x coordinates of the robots
     * @param _rx_coord
     */

    public void setRx_coord(Integer[] _rx_coord){
        this.rx_coord = _rx_coord;
    }

    /**
     * Set the y coordinates of the robots
     * @param _ry_coord
     */

    public void setRy_coord(Integer[] _ry_coord){
        this.ry_coord = _ry_coord;
    }

    /**
     * sets the suppressed parameter
     */

    public void setSuppressed(){
        this.suppressed = true;
    }

    /**
     * sets a given table as the item-table used in this class
     * To be called ONLY from InputReader Class
     * @param _table = value the table will have
     */

    public void setItemTable(ItemTable _table){
        this.itemTable = _table;
    }

    /**
     * sets a given table as the job-table used in this class
     * TO BE ACCESSED ONLY FROM InputReader Class
     * @param _table a table of jobs
     */

    public void setJobTable(JobTable _table){
        this.jobTable = _table;
    }

    /**
     * adds a colection of jobs to the jobTable
     * TO BE ACCESSED ONLY FROM InputReader Class
     * @param _jobCollection
     */

    public void setJobTable(Collection<Job> _jobCollection){

        jobTable = new JobHashTable();

        for(Job it : _jobCollection) {
            jobTable.add(it);
        }
    }

    /**
     * Creates the job queue utilised for passing jobs to the central system
     * called in the InputReader class
     */

    public void setJobQueue(){

        jobTable.values().stream().forEach(e -> jobQueue.add(e));

    }

    /**
     * Recalculates the costs of the jobs according to the
     * new locations of the robots
     */

    public void resetJobQueue(){

        PriorityQueue<Job> aux = new PriorityQueue<>();

        for(Job j : jobQueue){

            Job auxJob = j;

            auxJob.orderPicking(world,itemTable,rx_coord,ry_coord,drops);
            aux.add(auxJob);
        }

        jobQueue = aux;

    }


    /**
     * The method used for passing the next job to the system(to be further passed to the robots)
     * It should be called in the Main class of the project
     * @return the most rewarding job (from a reward/cost point of view)
     */

    public Job selectJob(){
        return jobQueue.poll();
    }

    public Collection<Job> getJobCollection(){
        return this.jobQueue;
    }

    /**
     * The method used for giving the information about the xSize of the world
     * the robots are moving in
     * It should be called in the Main class of the Project
     * @param _size = the xSize of the world
     */

    public void setWorldXSize(int _size){
        this.worldXSize = _size;
    }


    /**
     * The method used for giving the information about the ySize of the world
     * the robots are moving in
     * It should also be called in the Main class of the Project
     * @param _size = the ySize of the world
     */

    public void setWorldYSize(int _size) {
        this.worldYSize = _size;
    }

    /**
     * Calculate the distances between all the places in the world and
     * store them and the movements into an entry consisting of a 4D Integer Array
     * and a 4D String Array
     * @return an WorldEntry containing the two 4D Arrays
     */

    public WorldEntry worldDistances(){

        Integer[][][][] distances = new Integer[worldXSize][worldYSize][worldXSize][worldYSize];
        String[][][][] routes = new String[worldXSize][worldYSize][worldXSize][worldYSize];

        //the s before the iterator represents start and the g represnts goal

        int i = 0;

        for(int si = 0; si < worldXSize; si++){
            for(int sj = 0; sj < worldYSize; sj++){
                for(int gi = 0; gi < worldXSize; gi++){
                    for(int gj = 0; gj < worldYSize; gj++){

                        AStar as = new AStar(new routeplanning.State(si,sj),new routeplanning.State(gi,gj));
                        //routes[si][sj][gi][gj] = as.findPath();
                        //distances[si][sj][gi][gj] = routes[si][sj][gi][gj].length();
                        distances[si][sj][gi][gj] = as.manhatanDistace(new routeplanning.State(si,sj),new routeplanning.State(gi,gj));

                    }
                }
            }
        }

        return new WorldEntry(distances,routes);

    }


    public void setDrops(LinkedList<Drop> _drops){
        this.drops = _drops;
    }

}
