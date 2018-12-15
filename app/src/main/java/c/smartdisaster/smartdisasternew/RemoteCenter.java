package c.smartdisaster.smartdisasternew;

public class RemoteCenter extends CPU {
    RemoteCenter () {
        power = 200;
        minPower = -1;
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
