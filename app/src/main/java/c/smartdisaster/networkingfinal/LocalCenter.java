package c.smartdisaster.networkingfinal;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalCenter extends ComputeCenter {



    RemoteCenter remoteCenter; // reference to remote center object //
    Channel remoteConnection;

    LocalCenter(DisasterNetwork d_network) {
        super(d_network);
        cpuIndex = 0;
        name = "Local Center";
        cpus = new ArrayList<CPU>(Arrays.asList(
                new CPU("CPU1", 10),
                new CPU("CPU2", 10)));
        transferringJobs = new ArrayList<Job>();
        remoteConnection = new Channel(50, -1);
        remoteCenter = network.remoteCenter;
    }

    // adds job to job list or transfer list depending on CPU power
    public void AddJob (Job j) {
        if (AllFull() || j.totalPayLoad > 80) { // jobs that are too large will go straight to the remote center
            j.progress = 0;
            j.state = "Transferring";
            j.location = "Remote Channel";
            j.channel = remoteConnection;
            transferringJobs.add(j);
            return;
        }
        CPU cpu = cpus.get(cpuIndex % cpus.size());
        if (!cpu.CanTake()) { // if we are servicing too many jobs at local
            j.progress = 0;
            j.state = "Transferring";
            j.location = "Remote Channel";
            j.channel = remoteConnection;
            transferringJobs.add(j);
        } else { // otherwise local center can handle the task
            j.location = name + Integer.toString(cpuIndex%cpus.size());
            j.state = "Computing";
            cpu.jobList.add(j);
        }
        cpuIndex++;
    }

    // Transfers jobs to remote center if computePerJob is too low on arrival //
    void TransferToRemote () {
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

}
