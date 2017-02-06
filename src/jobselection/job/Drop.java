package jobselection.job;

/**
 * Created by adrian_radulescu1997 on 15.03.2016.
 */
public class Drop {

    public Integer x_coord;
    public Integer y_coord;

    public Drop(String _args){
        x_coord = Integer.parseInt(_args.split(",")[0]);
        y_coord = Integer.parseInt(_args.split(",")[1]);
    }

    public String toString(){
        return x_coord + " " + y_coord;
    }

}
