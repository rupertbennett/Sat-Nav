package satnav;

public class vertex{
    int cityNum;
    int x, y, distance;
    int noofedges;
    double priority;
    Boolean visited;
    Edge edgelist[] = new Edge [6];
    double gScore;
    double fScore;
    vertex previous;
    public vertex(int vertexid, int x, int y){
        this.cityNum = vertexid;
        this.x = x;
        this.y = y;
        noofedges= 0;
        priority = 999;//2147483647;    
    }
}
