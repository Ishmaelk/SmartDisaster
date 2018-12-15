package c.smartdisaster.smartdisasternew;

public class Connection {
    int id;
    int bandwidth;
    CPU destination; // where the connection leads to | ALWAYS a CPU or derived class of CPU
    Connection (int i, int b) {
        id = i;
        bandwidth = b;
        destination = null;
    }
}
