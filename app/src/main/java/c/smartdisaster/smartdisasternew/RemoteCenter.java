package c.smartdisaster.smartdisasternew;

import java.util.ArrayList;

public class RemoteCenter extends CPU {
    RemoteCenter () {

        jobList = new ArrayList<Job>();
        name = "Remote Center";
        power = 20;
        minPower = -1;
    }

    public void AddJob (Job j) { // adds job to job list or transfer list depending on CPU power
        jobList.add(j);
    }

    // Exactly like CPU Compute, but does not divide compute power among number of jobs //
    public void Compute () {
        if (jobList.size() == 0) return;
        float computePerJob = power;
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
