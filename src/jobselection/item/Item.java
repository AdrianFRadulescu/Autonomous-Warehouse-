package jobselection.item;

/**
 * Created by adrian_radulescu1997 on 25.02.2016.
 */
public class Item implements Comparable<Item> {

    private String name;

    private Double weight;
    private Double reward;

    private int x_coord;
    private int y_coord;

    /**
     * Takes a string(containingh an item description)
     * and transforms it into an Item object
     * @param _args
     * @return
     */


    public Item(String _args){

        String[] argsSplit = _args.split(",");

        this.name = argsSplit[0];

        this.reward = Double.parseDouble(argsSplit[1]);
        this.weight = Double.parseDouble(argsSplit[2]);

    }

    public String getName(){
        return name;
    }

    /**
     * Getters
     * @return name,weight,reward,x_coord,y_coord
     */

    public double getWeight(){
        return weight;
    }

    public double getReward(){
        return reward;
    }

    public int getX_coord(){
        return x_coord;
    }

    public int getY_coord(){
        return y_coord;
    }

    /**
     * Setters
     */

    public void setName(String _name){
        name = _name;
    }

    public void setWeight(double _art1){
        weight = _art1;
    }

    public void setReward(double _reward){
        reward = _reward;
    }

    public void setX_coord(int _x_coord){
        x_coord = _x_coord;
    }

    public void setY_coord(int _y_coord){
        y_coord = _y_coord;
    }

    public String toString(){
        return name + " " + reward + " " + weight + " " + x_coord + " " + y_coord;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Item o) {
        return (
                (this.name.equals(o.getName())
                        && this.reward.equals(o.reward)
                        && this.weight.equals(o.getWeight())
                        ? 0 : -1
                )
        );
    }
}
