package jobselection_testing.job_testing;

import jobselection.item.ItemTable;
import jobselection.job.Job;
import jobselection.job.JobHashTable;
import jobselection.job.JobTable;
import jobselection.reading.FileContentReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertFalse;

/**
 * Created by adrian_radulescu1997 on 04.03.2016.
 */
public class JobTableTest {

    private JobTable testTable; // the table we are testing
    private Map<String,Job> jobMap; //an auxiliary map used in the testing

    private ItemTable itemTable;

    private String jobFilePath = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";
    private String itemFilePath = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/items.csv";

    private String test;

    @Before
    public void set_up() throws Exception{

        itemTable = FileContentReader.getItemFileContent(itemFilePath);

        testTable = new JobHashTable();

        BufferedReader reader = new BufferedReader(new FileReader(jobFilePath + "jobs.csv"));

        //a standard map for storing the elements form the file
        jobMap = new TreeMap<>();

        String line;

        while((line = reader.readLine()) != null){

            Job newJob = new Job(line,itemTable);

            jobMap.put(newJob.getID(),newJob);
        }

        reader.close();

        jobMap.values().stream().forEach(e -> testTable.add(e));


    }

    @After
    public void tear_down(){

        System.out.println(test + " test passed");
    }

    @Test
    public void testIsEmpty() throws Exception {

        test = "isEmpty";

        assert (testTable.isEmpty());
    }

    @Test
    public void testAdd() throws Exception {

        test = "Add";
        testTable.add(jobMap.get("10024"));

        assertFalse(testTable.isEmpty());

    }

    @Test
    public void testRemove() throws Exception {

        test = "Remove";
        testTable.add(jobMap.get("10024"));

        assertFalse(testTable.isEmpty());

        testTable.remove("10024");

        assert (testTable.isEmpty());

    }

    @Test
    public void testRemove1() throws Exception {

        test = "Remove1";
        testTable.add(jobMap.get("10024"));

        assertFalse(testTable.isEmpty());

        testTable.remove(jobMap.get("10024"));

        assert (testTable.isEmpty());
    }

    @Test
    public void testContains() throws Exception {

        test = "contains";
        assertFalse(
                jobMap.values().stream().noneMatch(
                        e -> testTable.contains(e.getID())
                ) && testTable.values().stream().noneMatch(
                        e -> jobMap.containsKey(e.getID())
                                && jobMap.containsValue(e)
                )
        );

    }

    @Test
    public void testGet() throws Exception {

        test = "Get";
        assertFalse(
                jobMap.values().stream().noneMatch(e -> e.compareTo(testTable.get(e.getID())) == 0)
        );
    }

    @Test
    public void testSize() throws Exception {

        test = "Size";
        assert (jobMap.size() == testTable.size());

    }

    @Test
    public void testValues() throws Exception {

        test = "Values";
        assertFalse(
                jobMap.values().stream().noneMatch(
                        e -> testTable.contains(e.getID())
                ) && testTable.values().stream().noneMatch(
                        e -> jobMap.containsValue(e)
                                && jobMap.containsKey(e.getID())
                )
        );

    }
}