package c.smartdisaster.networkingfinal;

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
    CPU () {
        power = 100;
        minPower = 10;
        jobList = new ArrayList<Job>();
        transferringJobs = new ArrayList<Job>();
        remoteConnection = new Channel(50, -1);
    }

    CPU (String n, float p) {
        name = n;
        power = p;
        minPower = 5;
        jobList = new ArrayList<Job>();
        transferringJobs = new ArrayList<Job>();
        remoteConnection = new Channel(50, -1);
    }

    CPU (String n, float p, float m) {
        name = n;
        power = p;
        minPower = m;
        jobList = new ArrayList<Job>();
        transferringJobs = new ArrayList<Job>();
        remoteConnection = new Channel(50, -1);
    }

    public void AddJob (Job j) { // adds job to job list or transfer list depending on CPU power
        if ( GetComputePerJob() < minPower) {
            j.progress = 0;
            j.state = "Transferring";
            j.location = "Remote Channel";
            j.channel = remoteConnection;
            transferringJobs.add(j);
        } else {
            j.location = "Local Center";
            j.state = "Computing";
            jobList.add(j);
        }
    }

    void TransferToRemote () { // transfers to remote center | only used by the localCenter c.smartdisaster.smartdisasternew.CPU object
        for (int i = 0; i < transferringJobs.size(); i++) {
            Job j = transferringJobs.get(i);
            j.progress = Math.min(j.progress + j.channel.bandwidth, j.totalPayLoad);
            if (j.progress >= j.totalPayLoad) {
                j.progress = 0;
                j.state = "Computing";
                j.location = "Remote Center";
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
            j.progress = (int) Math.min(j.progress + computePerJob, j.totalPayLoad);
            if (j.progress >= j.totalPayLoad) {
                j.state = "Completed";
                network.completedJobs.add (j);
                network.activeJobs.remove(i);
                jobList.remove(i);
            }
        }
    }

    float GetComputePerJob () {
        if (jobList.size() == 0)
            return power;
        float computePerJob = (float) power / jobList.size();
        return computePerJob;
    }

    public float GetUsage () {
        float usage = GetComputePerJob() * jobList.size() / power * 100;
        return usage;
    }

}
