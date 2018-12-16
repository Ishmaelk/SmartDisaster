package c.smartdisaster.smartdisasternew;

import java.util.ArrayList;

public class CPU {
    String name;
    float power;
    float minPower; //  the minimum computePerJob this CPU is willing to tolerated

    Channel remoteConnection; // connection to remote center | only used by localCenter class
    DisasterNetwork network;
    RemoteCenter remoteCenter; // reference to remote center object //
    ArrayList<Job> jobList; // the list of all jobs being computed at this CPI //
    ArrayList<Job> transferringJobs; // the jobs being transferred to remote //


    // Default Constructor //
    CPU () { power = 100; minPower = 10; jobList = new ArrayList<Job>(); }

    CPU (String n, float p) {
        name = n;
        power = p;
        minPower = 5;
        remoteConnection = new Channel(50, -1);
    }

    CPU (String n, float p, float m) {
        name = n;
        power = p;
        minPower = m;
        remoteConnection = new Channel(50, -1);
    }

    public void AddJob (Job j) { // adds job to job list or transfer list depending on CPU power
        if (power / jobList.size() < minPower) {
            j.channel = remoteConnection;
            transferringJobs.add(j);
        } else {
            jobList.add(j);
        }
    }

    void TransferToRemote () { // transfers to remote center | only used by the localCenter c.smartdisaster.smartdisasternew.CPU object
        for (int i = 0; i < transferringJobs.size(); i++) {
            Job j = transferringJobs.get(i);
            j.progress += j.channel.bandwidth;
            if (j.progress >= j.totalPayLoad) {
                j.progress = 0;
                j.channel = null;
                transferringJobs.remove(i);
                remoteCenter.AddJob(j);
            }
        }
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
