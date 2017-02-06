package jobselection.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adrian_radulescu1997 on 26.02.2016.
 */
public class JobHashTable implements JobTable {
    
    private Map<String,Job> jobMap;

    /**
     * Constructs an empty jobHashTable
     */

    public JobHashTable(){

        this.jobMap = new HashMap<String, Job>();
    }

    /**
     * Constructs an jobHashTable with
     * @param _job
     */

    public JobHashTable(Job _job){

        this.jobMap = new HashMap<String, Job>();
        jobMap.put(_job.getID(),_job);
    }

    /**
     * Constructs an jobHashMap form a given ArrayList
     * @param _jobList
     */

    public JobHashTable(ArrayList<Job> _jobList){

        this.jobMap = new HashMap<String, Job>();

        for (Job it : _jobList)
            jobMap.put(it.getID(),it);

    }

    /**
     * Constructs an jobHashMap form a given Array
     * @param _jobList
     */

    public JobHashTable(Job[] _jobList){

        this.jobMap = new HashMap<String, Job>();

        for (Job it : _jobList)
            jobMap.put(it.getID(),it);

    }

    /**
     * Checks if the table is empty or not
     * @return true if the table is empty , false otherwise
     */

    @Override
    public boolean isEmpty() {
        return jobMap.isEmpty();
    }

    /**
     * inserts an element into the table
     */

    @Override
    public void add(Job _job) {

        jobMap.put(_job.getID(),_job);
    }

    /**
     * deletes an element from the table
     */

    @Override
    public void remove(String _jobID) {
        jobMap.remove(_jobID);
    }

    /**
     * deletes an element from the table
     */

    @Override
    public void remove(Job _job) {
        jobMap.remove(_job.getID());
    }

    /**
     * sees if the table contains the job with the given ID
     *
     * @param _jobID = the ID of the job
     */
    @Override
    public boolean contains(String _jobID) {
        return jobMap.containsKey(_jobID);
    }

    /**
     * finds an element in the table
     *
     * @param _jobID
     * @return the job object with the given key or null if it does not exist
     */
    @Override
    public Job get(String _jobID) {
        return jobMap.get(_jobID);
    }

    /**
     * @return the size of the table
     */
    @Override
    public int size() {
        return jobMap.size();
    }

    /**
     * @return a collection of the elements in the table
     */
    @Override
    public Collection<Job> values() {
        return jobMap.values();
    }

    @Override
    public String toString(){

        String returnString = "";

        for (Map.Entry<String,Job> entry : jobMap.entrySet()) {
            returnString = returnString.concat(entry.getValue().toString());
        }

        return returnString;
    }

}
