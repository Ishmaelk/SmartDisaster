package c.smartdisaster.smartdisasternew;

import java.util.ArrayList;

public class CPU {
    String name;
    float power;
    float minPower; //  the minimum computePerJob this c.smartdisaster.smartdisasternew.CPU is willing to tolerated
    //Channel connection; // connection to remote center | only used by localCenter class
    DisasterNetwork network;
    ArrayList<Job> jobList; // the list of all jobs being computed at this CPI //

    // Default Constructor //
    CPU () { power = 100; minPower = 10; jobList = new ArrayList<Job>(); }

    CPU (String n, float p) {
        name = n;
        power = p;
        minPower = -1;
    }

    CPU (String n, float p, float m) {
        name = n;
        power = p;
        minPower = m;
    }

    public void AddJob (Job j) {
        jobList.add(j);
    }

    void TransferToRemote () { // transfers to remote center | only used by the localCenter c.smartdisaster.smartdisasternew.CPU object

    }


    public void Compute () {
        if (jobList.size() == 0) return;
        float computePerJob = power / jobList.size();
        for (int i = 0; i < jobList.size(); i++) {
            Job j = jobList.get(i);
            j.progress += computePerJob;
            if (j.progress >= j.totalPayLoad) {
                network.completedJobs.add (j);
                jobList.remove(i);
            }
        }
    }



}
