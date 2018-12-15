package c.smartdisaster.smartdisasternew;

import java.util.Comparator;

public class Job {
    int id; // the ID of the c.smartdisaster.smartdisasternew.Job
    public int priority; // the priority of the job
    int progress; // the amount of bits currently  transferred/computed
    int totalPayLoad; // the total size of the job
    String location; // the current location of the job | channel_1, Local Center, Remote Center etc
    String state; // the current state of the job | waiting, transferring, computing, completed
    Job (int i, int p, int size, String loc) { //  location initialized with device job started at
        id = i;
        priority = p;
        progress = 0;
        totalPayLoad = size;
        location = loc;
        state = "waiting"; // Jobs always start as waiting
    }

}

class JobComparator implements Comparator<Job> {
    public int compare(Job j1, Job j2) {
        if (j1.priority < j2.priority)
            return 1;
        else if (j1.priority > j2.priority)
            return -1;
        return 0;
    }
}

