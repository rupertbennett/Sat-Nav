package satnav;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class RouteInfo implements RouteInfoInterface{

   
    @Override
    public double GetDistanceInMiles(vertex current) {
        double totalDistance = 0;
        totalDistance = current.fScore/1609.34;
        return totalDistance;
    }

    @Override
    public double GetTimeInMinues(vertex current) {
        double Time = 0;
        double TimeM = 0;
        int averageSpeedLimit = 60;
        Time = current.fScore / averageSpeedLimit;
        return Time;
    }

    @Override
    public int[] GetRouteVertexIDs(vertex current) {
        int count = 0;
        String[] roadName = new String[40];
        String[] roadNameOrdered = new String[40];
        int[] vertexId = new int[40];
        int c[] = new int[40];
        
        while (current.previous != null)
        {
            vertexId[count] = current.cityNum;
            roadName[count] = current.edgelist[0].label;
            current = current.previous;
            count++;
        }
        int count2 = 0;
        while(count > 0)
        {
            c[count2] = vertexId[count-1];
            roadNameOrdered[count2] = roadName[count-1];
            count2 ++;
            count --;
            System.out.println(c[count2-1]);
        }
        return c;
    }

    @Override
    public int GetNoOfExploredVertices(vertex current) {
        int TotalNum = 1;
        while (current.previous != null)
        {
            TotalNum++;
            current = current.previous;
        }
        return TotalNum;
    }

    @Override
    public String GetRoute(vertex current, boolean incvertices) 
    {
        int count = 0;
        String[] roadName = new String[40];
        String[] roadNameOrdered = new String[40];
        int[] vertexId = new int[40];
        int c[] = new int[40];
        int count2 = 0;
        System.out.print("start ");
        if (incvertices == true)
        {
            while (current.previous != null)
            {
                vertexId[count] = current.cityNum;
                roadName[count] = current.edgelist[0].label;
                current = current.previous;
                count++;
            }
             while(count > 0)
            {
            c[count2] = vertexId[count-1];
            roadNameOrdered[count2] = roadName[count-1];
            count2 ++;
            count --;
            //System.out.print(" --> " + roadNameOrdered[count2-1] + "("+c[count2-1]+")");
            }
             for (int i = 0; i < count2; i++)
             {
                 if (i == 0)
                     System.out.print(" --> " + roadNameOrdered[i] + "("+c[count2-i]+")");
                 else if (c[i] == c[i-1])
                 {
                     
                 }
                 else if (c[i] != c[i-1])
                     System.out.print(" --> " + roadNameOrdered[i] + "("+c[count2-i]+")");
             }
        }
        else
        {
            while (current.previous != null)
            {
                vertexId[count] = current.cityNum;
                roadName[count] = current.edgelist[0].label;
                current = current.previous;
                count++;
            }
             while(count > 0)
            {
            c[count2] = vertexId[count-1];
            roadNameOrdered[count2] = roadName[count-1];
            count2 ++;
            count --;
            System.out.print(" --> " + roadNameOrdered[count2-1]);
            }
        }
        System.out.println(" --> end");
        return null;
        
    }
}
