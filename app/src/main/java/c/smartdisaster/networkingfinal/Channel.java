package c.smartdisaster.networkingfinal;

import java.util.Comparator;

public class Channel {
    int id;
    int bandwidth; // Bytes transfered per second

    Channel(int bandwidth, int Id){
        this.bandwidth = bandwidth;
        this.id = Id;
    }
}

class ChannelComparator implements Comparator<Channel> {
    public int compare(Channel c1, Channel c2) {
        if (c1.bandwidth < c2.bandwidth)
            return 1;
        else if (c1.bandwidth  > c2.bandwidth)
            return -1;
        return 0;
    }
}
