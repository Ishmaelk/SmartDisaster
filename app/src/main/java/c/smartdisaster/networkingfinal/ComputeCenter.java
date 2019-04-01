package c.smartdisaster.networkingfinal;

import java.util.ArrayList;
import java.util.Random;

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

    public void ComputeJobs() {
        for (int i = 0; i < cpus.size(); i++) {
            CPU cpu = cpus.get(i);
            cpu.Compute();
        }
    }

    public void AddCPU() {}

    public void RemoveCPU() {
        if (cpus.size() <= 1) return; // cant go below 1 cpu
        Random rand = new Random();
        int index = rand.nextInt(1000) % cpus.size();
        cpus.get(index).StopJobs();
        cpus.remove(index);
    }

    public void IncreaseCPUSpeeds() {
        for (int i = 0; i < cpus.size(); i++) {
            CPU cpu = cpus.get(i);
            cpu.power += 5;
        }
    }

    public void DecreaseCPUSpeeds() {
        for (int i = 0; i < cpus.size(); i++) {
            CPU cpu = cpus.get(i);
            if (cpu.power <= cpu.startingPower/2)
                continue;
            cpu.power -= 5;
        }
    }

    boolean AllFull() {
        for (int i = cpuIndex; i < cpuIndex+cpus.size(); i++) {
            CPU cpu = cpus.get(i%cpus.size());
            if (cpu.CanTake()) { cpuIndex = i; return false; }
        } return true;
    }
}
