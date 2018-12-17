package c.smartdisaster.networkingfinal;

import java.util.Random;

public class Device {
    int id;
    int priority;
    int amountStart;
    Device(int i, int priority){
        id = i;
        this.priority = priority;
    }

    public Job CreateJob (int jobId) { // creates job and places itself in jobPool in DisasterNetwork class
        Random rand = new Random ();
        int size = rand.nextInt(50) + 10;
        return new Job (jobId, priority, size, "Device"+id);
    }

}
