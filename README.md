# SmartDisaster
Networking Project- Smart Disaster

THE PROBLEM & APPROACH:

  SmartDisaster is a simulation of a cloudbased network environment where devices generate jobs, which require channels and compute power to transmit and process.

  Devices of differing priority levels are randomly chosen every second to generate a Job. These jobs occupy a pool until a channel is available to transmit them. Channels have varying bandwidths and are also contained in a pool. We always allocate the best channels (highest bandwidth) to the highest priority job (lowest priority id = highest priority job) in the pool using priority queue structures.

  After transmission, jobs are ready for computation. There exist two centers in our cloud network capable of computation. One is the local center, the other called the remote center. The local center has limited compute capacity, and divides up its compute power among all jobs currently being computed. The remote center has no limit on compute power based on the number of jobs being computed, and we allocate a set amount of compute to EVERY job, regardless of how many jobs are being computed at the local center. Since the local center has limited compute capacity, we tolerate a compute per job of 3.33 at any point in time. We set the compute power of the local center to be 10. So at any point in time the local center can only compute at most 3 jobs. 

  What happens to the other jobs that arrive after the first 3? Jobs that arrive after the minimum compute per job threshold has been reached are sent through a high speed channel we call the remote channel. This incurs a time penalty of course, as another network transfer must be made to send the job to the remote center. Once at the remote center, the job is allocated the remote center's compute power every second and computation is complete.

INSTRUCTIONS TO RUN:

  Download and install android studio, then allow android studio to update entirely. This process takes a lot of time as android studio is a large program.

  Download the project found here on github and open the project folder in android studio.
  After android studio properly syncs the project:
    1) click on the green "Play" button (sideways arrow) at the top of the IDE
    2) Select Create a new virtual device in the popup and select Android TV (720p) API 28
    3) click ok and another popup containing a virtualized device will appear that will display the app.

USING THE APP:
  By default, one job per second is generated, this variable can be controlled via the increase and decrease buttons on the right side of the screen.
  The number of channels can also be altered. Similarly, use the button dials on the left side of the screen.
  On the far left, a text labeled local center contains the compute per job calculation of the local center. This can only take on values of 10, 5, and 3.33.
  A table on the upper part of the screen displays relevant network information, such as job id, device id, priority, state (waiting, transferring, interrupted, computing, completed), network time, compute time, location (job pool, channel, local center, remote center), and progress.
  A pause button is also implemented to allow the user to see the entire table without changes being applied every second

Finding the code:
  
  From the start of the project directory go to: "app/src/main/java/c/smartdisaster/networkingfinal"

  Android studio utilizes activity files that handle onClick and update events that we use to display our backend logic.
    MainActivity.java
  Our Backend logic classes:
    Channel.java
    CPU.java // used for local center
    Device.java
    DisasterNetwork.java
    Job.java
    RemoteCenter.java // inherits from CPU
    
