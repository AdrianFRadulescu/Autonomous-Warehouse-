package jobselection.job;

import java.util.Collection;

/**
 * Created by adrian_radulescu1997 on 24.02.2016.
 */
public interface JobTable {

    /**
     * Checks if the table is empty or not
     */

    public boolean isEmpty();

    /**
     * inserts an element into the table
     */

    public void add(Job _job);

    /**
     * deletes the element with the given key from the table
     */

    public void remove(String _jobID);

    /**
     * deletes the given element from the table
     */

    public void remove(Job _job);

    /**
     * sees if the table contains the job with the given name
     * @param _jobID = the name of the job
     */

    public boolean contains(String _jobID);

    /**
     * finds an element in the table
     * @return the job object with the given key or null if it does not exist
     */

    public Job get(String _jobID);

    /**
     * @return the size of the table
     */

    public int size();

    /**
     * @return a collection of the elements in the table
     */

    public Collection<Job> values();

    /**
     * Prints the jobTable
     * @return
     */

    public String toString();

}
