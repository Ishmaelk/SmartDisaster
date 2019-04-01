package c.smartdisaster.networkingfinal;

import java.util.ArrayList;

public class ComputeCenter {
    DisasterNetwork network;
    int cpuIndex;
    String name;
    ArrayList<CPU> cpus;
    ArrayList<Job> transferringJobs; // the jobs being transferred to remote //


    ComputeCenter(DisasterNetwork d_network){
        network = d_network;
        cpuIndex = 0;
        name = "";
        cpus = new ArrayList<CPU>();
        transferringJobs = new ArrayList<Job>();
    }

    void ComputeJobs() {
        for (int i = 0; i < cpus.size(); i++) {
            CPU cpu = cpus.get(i);
            cpu.Compute();
        }
    }




    boolean AllFull() {
        for (int i = cpuIndex; i < cpuIndex+cpus.size(); i++) {
            CPU cpu = cpus.get(i%cpus.size());
            if (cpu.CanTake()) { cpuIndex = i; return false; }
        } return true;
    }
}
