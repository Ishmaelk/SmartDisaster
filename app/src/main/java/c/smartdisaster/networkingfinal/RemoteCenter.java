package c.smartdisaster.networkingfinal;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoteCenter extends ComputeCenter {



    RemoteCenter (DisasterNetwork d_network) {
        super(d_network);
        name = "Remote Center";
        cpus = new ArrayList<CPU>(Arrays.asList(
                new CPU("CPU1", 35),
                new CPU("CPU2", 35)));
        transferringJobs = new ArrayList<Job>();
    }

    public void AddJob (Job j) { // adds job to job list or transfer list depending on CPU power
        int index = cpuIndex % cpus.size();
        j.location = "Remote Center"+Integer.toString(index);
        cpus.get(index).jobList.add(j);
        cpuIndex++;
    }

    public void AddCPU () {
        cpus.add(new CPU("CPU" + Integer.toString(cpus.size()), 35));
    }

    // Exactly like CPU Compute, but does not divide compute power among number of jobs //
    public void ComputeJobs () {
        for (int i = 0; i < cpus.size(); i++) {
            CPU cpu = cpus.get(i);
            if (cpu.jobList.size() == 0) return;
            float computePerJob = cpu.power;
            for (int k = 0; k < cpu.jobList.size(); k++) {
                Job j = cpu.jobList.get(k);
                j.progress = (int) Math.min(j.progress + computePerJob, j.totalPayLoad);
                if (j.progress >= j.totalPayLoad) {
                    j.state = "Completed";
                    DisasterNetwork.completedJobs.add(j);
                    DisasterNetwork.activeJobs.remove(j);
                    cpu.jobList.remove(j);
                }
            }
        }
    }

}
