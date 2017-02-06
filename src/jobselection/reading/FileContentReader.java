package jobselection.reading;


import jobselection.WorldEntry;
import jobselection.item.Item;
import jobselection.item.ItemHashTable;
import jobselection.item.ItemTable;
import jobselection.job.Drop;
import jobselection.job.Job;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;


/**
 * Created by adrian_radulescu1997 on 24.02.2016.
 */
public class FileContentReader {

    private static String filePath;

    /**
     * @param _filePath = the path to the file we need to read
     */

    protected FileContentReader(String _filePath){
       this.filePath = _filePath;
    }

    /**
     * Counts the lines in the file
     * @return the number of lines in the file
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static Long countLines(String _filePath) throws FileNotFoundException , IOException{

        BufferedReader reader = new BufferedReader(new FileReader(_filePath));

        long lineCount = reader.lines().count();

        reader.close();

        return lineCount;
    }


    /**
     * Reads the content of a file line by line
     * @param _filePath
     * @return
     * @throws IOException
     */

    public static ItemTable getItemFileContent(String _filePath) throws IOException , FileNotFoundException {

        ItemTable fileContent = new ItemHashTable();

        BufferedReader reader = new BufferedReader(new FileReader(_filePath));


        reader.lines().forEach(
                (String line) -> fileContent.add(new Item(line))
        );

        reader.close();

        return fileContent;
    }

    /**
     * reads the content of the file containing the locations to the itemTable
     * @param _table - the table to be modified
     * @return a new itemTable containing the new locations for each item
     */

    public static ItemTable addLocationsFileContent(String _filePath, ItemTable _table) throws IOException , FileNotFoundException {

        ItemTable itemTable = _table;

        BufferedReader reader = new BufferedReader(new FileReader(_filePath));

        reader.lines().forEach(
                (String line) -> {
                    //split the line containing the location in 3 and then set this location
                    // for the element described

                    String[] lineSplit = line.split(",");

                    //set the location of the item in the table

                    itemTable.setLocation(
                            lineSplit[2],
                            Integer.parseInt(lineSplit[0]),
                            Integer.parseInt(lineSplit[1])
                    );
                }
        );


        reader.close();

        return itemTable;
    }


    /**
     * Reads form the file containing the jobs and return them in a priority queue
     * at each step check the size of the priority queue
     * If the size is greater then 10000 then keep only the first 100 jobs(the most rewarding)
     * @param _filePath the path to the file containing the Jobs
     * @return a collection containing the jobs
     * @throws IOException
     */

    public static Collection<Job> getJobFileContent(String _filePath, ItemTable _itemTable, WorldEntry _world,LinkedList<Drop> dropList) throws IOException , FileNotFoundException{

        //using a treeSet to keep the jobs sorted
        TreeSet<Job> jobSet = new TreeSet<Job>();

        BufferedReader reader = new BufferedReader(new FileReader(_filePath));
        String line;

        while((line = reader.readLine()) != null){

            Job aux = new Job(line,_itemTable);
            Integer[] auxArr = new Integer[1];
            auxArr[0] = -1;
            aux.orderPicking(_world,_itemTable,auxArr,auxArr,dropList);
            jobSet.add(aux);

            if(jobSet.size() > 100){
                jobSet.remove(jobSet.first());
            }

        }


        reader.close();

        return jobSet;
    }

    /**
     * Reads the content of the file containing the drop locations
     * @param _filePath the path to the file containing the Dropping Points
     * @return a colection containing the dropping locations
     * @throws IOException
     * @throws FileNotFoundException
     */

    public static LinkedList<Drop> getDropFileContent(String _filePath) throws IOException,FileNotFoundException{

        BufferedReader reader = new BufferedReader(new FileReader(_filePath));

        LinkedList<Drop> content = new LinkedList<>();

        reader.lines().forEach(
                (String e) -> content.add(new Drop(e))
        );

        reader.close();
        return content;

    }
}
