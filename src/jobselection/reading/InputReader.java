package jobselection.reading;

import jobselection.JobSelectionMain;
import jobselection.WorldEntry;
import jobselection.item.ItemTable;
import jobselection.job.Drop;
import jobselection.job.Job;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;


/**
 * The class reading the inputs reagrding the jobs,items and their locations
 */
public class InputReader {

    //public static String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/workspace/Warehouse-3/src/inputs/";
    public static final String  p = File.separator;
    public static final String FOLDER_PATH = System.getProperty("user.dir") + p + "src" + p + "inputs" + p;

    public static void main(JobSelectionMain mainClass , WorldEntry world,Integer[] rx_coord,Integer[] ry_coord) {

        try{

            ItemTable itemTable;

            try {
                //read the file containing the items and put them into a table
                itemTable = FileContentReader.getItemFileContent(FOLDER_PATH + "items.csv");

            }catch (IOException e) {
                System.out.println("IOException---");
                itemTable = null;
            }


            try{
                //read the file containing the locations of the items and set them in the table
                itemTable = FileContentReader.addLocationsFileContent(FOLDER_PATH + "locations.csv",itemTable);

                mainClass.setItemTable(itemTable);

            }catch (IOException e){

                System.out.println("IOException");
                itemTable = null;
            }

            LinkedList<Drop> drops;

            try{
                //read the file containing the drop locations and put them into a collection
                drops = FileContentReader.getDropFileContent(FOLDER_PATH + "drops.csv");
                mainClass.setDrops(drops);

            }catch (IOException e){

                drops = null;
                System.out.println(e.getCause());
            }

            try{
                //read the file containing the job descriptions and put them into a collection
                Collection<Job> jobOrder = FileContentReader.getJobFileContent(FOLDER_PATH + "jobs.csv",itemTable,world,drops,rx_coord,ry_coord);

                mainClass.setJobTable(jobOrder);
                mainClass.setJobQueue(jobOrder);

            }catch (IOException e){
                System.out.println(e.getCause());
            }

        }catch (NullPointerException e){
            System.out.println("The code has bugs.Fix them!");
            e.printStackTrace();
            System.out.println(rx_coord);
            return;
        }

    }

}
