package c.smartdisaster.smartdisasternew;

import java.util.Random;

public class Device {
    int id;
    int channelNum;
    int priority;
    int amountStart;
    Integer totalTime = 0;
    Integer amountTransfered = 0;
    Integer transferedSoFar = 0;
    Device(int priority, int amountStart){
        id = 1;
        this.priority = priority;
        this.amountStart = amountStart;
    }

    public Job CreateJob (int jobId) { // creates job and places itself in jobPool in DisasterNetwork class
        Random rand = new Random ();
        int size = rand.nextInt(300) + 10;
        return new Job (jobId, priority, size, "Device"+id);
    }

}
