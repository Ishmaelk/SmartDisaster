package c.smartdisaster.networkingfinal;

import java.util.ArrayList;

public class CPU {
    String name;
    float power;
    float startingPower;
    float minPower; //  the minimum computePerJob this CPU is willing to tolerated

    RemoteCenter remoteCenter; // reference to remote center object //
    ArrayList<Job> jobList; // the list of all jobs being computed at this CPI //


    // Default Constructor //
    CPU () {
        power = 100;
        startingPower = 100;
        minPower = 10;
        jobList = new ArrayList<Job>();
    }

    CPU (String n, float p) {
        name = n;
        power = p;
        minPower = 5;
        startingPower = p;
        jobList = new ArrayList<Job>();
    }

    CPU (String n, float p, float m) {
        name = n;
        power = p;
        startingPower = p;
        minPower = m;
        jobList = new ArrayList<Job>();
    }

    // Allocates computePerJob to all currently computing devices //
    public void Compute () {
        if (jobList.size() == 0) return;
        float computePerJob = power / jobList.size();
        for (int i = 0; i < jobList.size(); i++) {
            Job j = jobList.get(i);
            j.progress = (int) Math.min(j.progress + computePerJob, j.totalPayLoad);
            if (j.progress >= j.totalPayLoad) {
                j.state = "Completed";
                DisasterNetwork.completedJobs.add (j);
                DisasterNetwork.activeJobs.remove(j);
                jobList.remove(j);
            }
        }
    }

    public void StopJobs() {
        for (int i = 0; i < jobList.size(); i++) {
            Job j = jobList.get(i);
            jobList.remove(j);
            DisasterNetwork.activeJobs.remove(j);
        }
    }

    boolean CanTake() {
        return GetComputePerJob() < minPower ? false : true;
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
