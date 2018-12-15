package c.smartdisaster.smartdisasternew;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class DisasterNetwork {
    boolean paused; // if we have paused passage of time

    // Compute Variables
    public ArrayList<Job> completedJobs;
    CPU localCenter;


    // Network Variables
    public PriorityQueue<Job> jobPool; // job pool for network transfers | sorted by priority
    //public ArrayList<Device> deviceList;
    //public PriorityQueue<Channel> channelPool;
    DisasterNetwork () {
        paused = false;
        completedJobs = new ArrayList<Job>();
        jobPool = new PriorityQueue<Job>(5, new JobComparator());
    }

    // Creates a channel with a random bandwidth between 1 and 10 //
    void CreateChannel () {

    }

    // Destroys a channel //
    void DestroyChannel () {

    }


}
