package jobselection.job;

/**
 * Created by adrian_radulescu1997 on 24.03.2016.
 */
public class PickListSizePredictions {

    private Integer[] cancelledCount;
    private Integer[] jobCount;

    public PickListSizePredictions(Integer _size){

        this.cancelledCount = new Integer[_size +2];
        this.jobCount = new Integer[_size + 2];

    }

    public void setCancelledCount(int index) {
        this.cancelledCount[index] ++;
    }

    public void setJobCount(int index) {
        if(index < jobCount.length)
            return;
        this.jobCount[index] ++;
    }
}
