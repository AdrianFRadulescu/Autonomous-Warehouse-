27.02.2016

The Jobselection part consists of a Main class(JobSelectionMain - a thread) 
and 3 packages : 
    A.reading :
        1.FileContentReader - a class with static methods that read the content form each input file,converts it to its apropriate form(Items,Jobs) and sends the information to the InputReader class.
        2.InputReader - a class that receives the information for each file and stores it into tables for quick acces
    B.item :
        1.Item : a class representing the decription of an item.As the locations are in a diffrent file,the constructor only initialises the weight and reward
                   parameters.For the location there are setters wich are called later when the location file is read.
        2.ItemTable : an interface for the the table of items
        3.ItemHashTable : a implementation of the ItemTable interface using a HashMap
    C.job :
        1.Job : a class representing the description of a job
        2.JobTable : an interfeace for the table of jobs
        3.JobHashTable : a implementation of the JobTable interface using a HashMap
        
        
        
        
The path of the folder containing the inputs will be diffrent for each computer
the programs will run on so it should be modified acordingly.