package c.smartdisaster.networkingfinal;

import java.util.ArrayList;

public class RemoteCenter extends CPU {

    RemoteCenter () {
        jobList = new ArrayList<Job>();
        name = "Remote Center";
        power = 20;
        minPower = -1;
    }

    public void AddJob (Job j) { // adds job to job list or transfer list depending on CPU power
        j.location = "Remote Center";
        jobList.add(j);
    }

    // Exactly like CPU Compute, but does not divide compute power among number of jobs //
    public void Compute () {
        if (jobList.size() == 0) return;
        float computePerJob = power;
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

}
