package c.smartdisaster.smartdisasternew;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class DisasterNetwork {

    // General variables //
    public boolean paused; // if we have paused passage of time
    public double elapsedTime; // time we have spent unpaused //
    int jobId; // the id of the latest generated job
    int jobsPerSecond; // how many jobs are generated per second

    // Compute Variables
    public ArrayList<Job> completedJobs;
    CPU localCenter;
    RemoteCenter remoteCenter;

    // Network Variables //
    public PriorityQueue<Job> jobPool; // job pool for network transfers | sorted by priority
    public PriorityQueue<Channel> channelPool; // channelPool for network transfers | sorted by bandwidth
    public ArrayList<Device> deviceList;



    DisasterNetwork () {
        jobId = 1;
        jobsPerSecond = 1;
        paused = false;
        completedJobs = new ArrayList<Job>();
        jobPool = new PriorityQueue<Job>(5, new JobComparator());
        channelPool = new PriorityQueue<Channel>(5, new ChannelComparator());
    }

    // Creates a channel with a random bandwidth between 1 and 10 //
    void CreateChannel () {
        //Channel c = new Channel()
    }

    // Destroys a channel //
    void DestroyChannel () {

    }

    void GenerateJobs () { // generates jobs every second | Called in Workflow.java
        for (int i = 0; i < jobsPerSecond; i++) {
            Random rand = new Random();
            int deviceID = rand.nextInt(100) % deviceList.size();
            jobPool.offer(deviceList.get(deviceID).CreateJob(jobId++));
        }
    }

    // pulls jobs and channels from respective pools and begins transfer //
    void LoadJobs () {
        while (!jobPool.isEmpty() && !channelPool.isEmpty()) {
            Job j = jobPool.poll();
            Channel c = channelPool.poll();
            j.channel = c;
        }
    }

    void TransferJobs () { // Transfers jobs from devices to local center
        for (int i = 0; i < Channel.jobsTransferring.size(); i++) {
            Job j = Channel.jobsTransferring.get(i);
            j.channel.TransferData(j);
            if (j.progress >= j.totalPayLoad) {
                j.progress = 0;
                Channel.jobsTransferring.remove(j);
                channelPool.add(j.channel);
                j.channel = null;
                localCenter.AddJob(j);
            }
        }
    }


}
