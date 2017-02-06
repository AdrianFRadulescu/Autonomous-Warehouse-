package jobselection.job;

import jobselection.item.Item;

/**
 * A class describing a Pick in a Job
 */
public class Pick {


    public final Item ITEM;           //the item in the pick
    public final Integer PICK_COUNT; //the number of copies of this item
                                    //that the robot needs to pick

    public Pick(Item _item,int _pickCount){

        this.ITEM = _item;
        this.PICK_COUNT = _pickCount;
    }

    public String toString(){
        return ITEM + " " + PICK_COUNT;
    }


    public int hashcode(){

        return Integer.hashCode(
                ((ITEM.getX_coord() + ITEM.getY_coord())
                        *(1 + ITEM.getX_coord() + ITEM.getY_coord()))/2 + ITEM.getY_coord()
        );
    }
}
