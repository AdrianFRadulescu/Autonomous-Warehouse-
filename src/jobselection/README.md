Job selection

The Job selection part consists of a Main class :
        
    JobSelectionMain - a thread which at the beginning calls the InputReader
        (a method responsible for reading the files about items ,jobs and locations 
        interpreting and storing the data).In this class there is a collection of jobs
        (TreeSet / Priority Queue) in which the jobs are stored and deleted after they are completed.
        After each job is done the cost of the other jobs is recalculated,
        taking into account the new position of the robot(s),
        after which they are again stored into the TreeSet/Priority Queue
          
        After that it will constantly receive messages form the user input(about cancelations cancellations) 
        and executes them accordingly, and from the robot input
        (the status of the job that's currently executed) and when a job has been completed 
        it decides which other job will be the next to be sent to the robot.
and 3 packages : 
    
    A.reading :
        1.FileContentReader - a class with static methods that read the content form each input file,
            converts it to its appropriate form(Items,Jobs) and sends the information to the InputReader class.
            For the job reading it uses a TreeSet to store the most rewarding jobs.
        2.InputReader - a class that receives the information for each file and stores it into tables for quick access.
    B.item :
        1.Item : a class representing the decription of an item.As the locations are in a diffrent file,
            the constructor only initialises the weight and reward parameters.
            For the location there are setters wich are called later when the location file is read.
        2.ItemTable : an interface for the the table of items
        3.ItemHashTable : a implementation of the ItemTable interface using a HashMap
    C.job :
        1.Job : a class representing the description of a job.
        		It contains fileds for the minimum overall cost of the job , the total reward 
        		and the list of picks in the job  as well as getters and setters fo those values. 
        		It also contains a method for calculating the oder of the picks in the job(which is executed by the instances of other two classes) 
        		and a method for calculating the cancellation chances.
        		The comparison criteria between jobs is the reward over cost ratio.
        2.JobTable : an interface for the table of jobs
        3.JobHashTable : a implementation of the JobTable interface using a HashMap
        4.Two classes that are implemented in order to provide the order of the picks during a Job(by solving the Traveling Salesman Problem with heuristics):
            4.1 SingleRobotTSP - which calculates the solution for a single robot
            4.2 MultiRobotWeightLimitTSP - uses an instance of the SingleRobotTSP at each step for the auctioning of the picks
        
        
The path of the folder containing the inputs will be diffrent for each computer
the programs will run on so it should be modified acordingly.

