package jobselection_testing;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by adrian_radulescu1997 on 03.03.2016.
 */
public class JobSelectionMainTest {

    private final String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";


    @Before
    public void set_up() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(FOLDER_PATH+"jobs.csv"));



    }

    @Test
    public void testRun() throws Exception {

    }

    @Test
    public void testSetItemTable() throws Exception {

    }

    @Test
    public void testSetJobTable() throws Exception {

    }

    @Test
    public void testSetJobTable1() throws Exception {

    }

    @org.junit.Test
    public void testSetJobQueue() throws Exception {

    }

    @org.junit.Test
    public void testSelectJob() throws Exception {

    }

    @org.junit.Test
    public void testGetJobCollection() throws Exception {

    }
}