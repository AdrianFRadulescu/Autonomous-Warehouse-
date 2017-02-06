package jobselection;

import jobselection.job.Job;

import java.util.Stack;
import java.util.TreeSet;

public class Test {

    public static String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";

    /**
     * For the time being the selection works only for a single robot
     * with no weight limit.I think tomorrow it will work
     * for multiple weight-limited robots
     * the methods will not change.
     * @param args
     */

    public static void main(String[] args){

        //create a new instance of the JobSelectionMain class
        JobSelectionMain a = new JobSelectionMain();

        System.out.println(System.currentTimeMillis());

        Integer[] x = new Integer[3];

        Integer[] y = new Integer[3];

        x[0] = 5;
        y[0] = 5;
        x[1] = 1; y[1] = 1;
        x[2] = 3; y[2] = 5;

        a.setRx_coord(x);
        a.setRy_coord(y);

        //start the class
        a.run();

        System.out.println("size = " + a.getJobCollection().size());


        //Use the selectJob() method to tell my class to give you a job
        //it will return a job object which contains the description of a job :
        //reward,cost and the pickList which has to be executed by the robot
        //the order of the picks in the pickList is already the one in
        //which the robot must pick the items

        //when there will be more robots the situation will change a little bit
        //because every robot will have its own pickList(there will soon be a method
        //in the job class to get the picklist for a certain robot)

        //System.out.println(a.selectJob());

        System.out.println(System.currentTimeMillis());




        //after a job is completed,or canceled and the JobSelection part
        //needs to give another job for the robot(s) there are two conditions
        //that need to completed before using the selectJob() method again :

        //Firstly :
        //you need to update the arrays containing the locations of the robots
        //using the setRx_coord() and setRy_coord() methods using the localisation part

        //here is an example(no localisation operation included though =)) )



        //sets the x coordinates for the robot(s)
        a.setRx_coord(x);

        //sets the y coordinates for the robot(s)
        a.setRy_coord(y);

        //Secondly :
        //you need to reorganize the collection which actually passes the jobs
        //this will be done automatically by the resetJobQueue() method

        a.resetJobQueue();


        //now you can take another job from the queue and after it is completed
        //you can do the same for the next one
        //my advise is to have your own getNewJob() method in which you do all the
        //described steps and return the result of the selectJob() method
        System.out.println(a.selectJob());

        a.resetJobQueue();
        System.out.println(a.selectJob());

        System.out.println(System.currentTimeMillis());

        Job j = new Job("20014,ae,2,cf,5,ch,2,ad,2,aj,1,ci,1,bi,2",a.getItemTable());

        j.orderPicking(a.getWorld(),x,y,a.getDrops());

        System.out.println(j);


        System.out.println("---------------------------------------------------------------------------------------------------------");


        System.out.println(((TreeSet<Job>)a.getJobCollection()).first().getPickList());

        for (Job jb : a.getJobCollection()){
            System.out.println(jb);
            break;
        }

        Stack<Integer> st = new Stack<>();

        st.add(1);
        st.add(2);
        st.add(3);
        st.add(4);
        st.add(5);

        System.out.println(st.get(0));

        System.out.println( "sz = " +
                a.getItemTable().getValues().stream().filter(
                        e -> !e.getJobCount().equals(0)
                ).count()
        );
    }

}
