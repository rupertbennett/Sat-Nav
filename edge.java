package satnav;

/**
 *
 * @author rupert2
 */
public class Edge {
    String label;
    int fromid, toid, speedlimit;
    double distance;
    boolean oneway;

    Edge(String label, int fromid, int toid, double distance, boolean oneway, int speedlimit) 
    {
        this.label = label;
        this.fromid = fromid;
        this.toid = toid;
        this.distance = distance;
        this.oneway = oneway;
        this.speedlimit = speedlimit;
    }
}