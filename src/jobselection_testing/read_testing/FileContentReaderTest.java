package jobselection_testing.read_testing;

import jobselection.item.ItemTable;
import jobselection.reading.FileContentReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.assertTrue;

/**
 * Created by adrian_radulescu1997 on 03.03.2016.
 */
public class FileContentReaderTest {

    private String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";

    private ItemTable items; // the table we will test

    @Before
    public void setUp() throws Exception {

        items = FileContentReader.getItemFileContent(FOLDER_PATH + "items.csv");
        items = FileContentReader.addLocationsFileContent(FOLDER_PATH + "locations.csv",items);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetItemFileContent() throws Exception {

        BufferedReader itemReader = new BufferedReader(new FileReader(FOLDER_PATH + "items.csv"));


        assertTrue(
                itemReader.lines().allMatch(
                        (String line) -> {
                            String[] lineSplit = line.split(",");

                            Double expected_reward = Double.parseDouble(lineSplit[1]);
                            Double expected_weight = Double.parseDouble(lineSplit[2]);

                            return expected_reward.equals(items.get(lineSplit[0]).getReward())
                                    && expected_weight.equals(items.get(lineSplit[0]).getWeight());
                        }
                )
        );

        itemReader.close();
    }

    @Test
    public void testAddLocationsFileContent() throws Exception {

        BufferedReader locationReader = new BufferedReader(new FileReader(FOLDER_PATH + "locations.csv"));

        assertTrue(
                locationReader.lines().allMatch(
                        (String line) -> {
                            String[] lineSplit = line.split(",");

                            Integer expected_x = Integer.parseInt(lineSplit[0]);
                            Integer expected_y = Integer.parseInt(lineSplit[1]);

                            return expected_x.equals(items.get(lineSplit[2]).getX_coord())
                                    && expected_y.equals(items.get(lineSplit[2]).getY_coord());
                        }
                )
        );

        locationReader.close();
    }
    /*
    @Test
    public void testGetJobFileContent() throws Exception {
        BufferedReader jobReader = new BufferedReader(new FileReader(FOLDER_PATH + "jobs.csv"));

        Collection jobs = FileContentReader.getJobFileContent(FOLDER_PATH + "jobs.csv",items);

        assert (
            jobReader.lines().filter(
                    e -> jobs.contains(new Job(e,items))
            ).count() == jobs.size()
        );

    }*/
}