package satnav;

public class SatNavEngine implements SatNavEngineInterface
{
    //RouteInfo route=new RouteInfo();
    int vertexSize = 30;
    vertex nodes[] = new vertex[vertexSize]; 
    int numberOfCities = 0;
    int grow = vertexSize * 2;
    int closedCounter = 0;
    int openCounter = 0;
    int setSize = 3000;
    int left = 0;
    boolean qsort = false;
    int c = 1;
    
    @Override
    public String GetSHUID()
    {
    return "b3006623";
    }

    @Override
    public void AddVertex(int vertexid, int x, int y)
    {
        vertex node = new vertex(vertexid, x, y);
        if (vertexSize == numberOfCities)
        {
            vertex vertexArray2[] = new vertex[numberOfCities+grow];
            System.arraycopy(nodes, 0, vertexArray2, 0, vertexSize);
            nodes = vertexArray2;
            vertexSize = vertexSize+grow;
            nodes[numberOfCities] = node;
        }
        else
        {
        nodes[numberOfCities] = node;
        }
        numberOfCities++;
    }

    public void qSort(int left, int right)
    {
      int place = left;
      int pivot = (left + right)/2;
      Swap (pivot, right);
      int newPivot = right;
      for (int i = left; i <right; i++)
      {
          if (nodes[i].cityNum < (nodes[newPivot].cityNum))
          {
              Swap(i, place);
              place++;
          }
      }
      Swap(place, newPivot);
      if ((place-left) > 1)
      {
           qSort(left, place-1);
          
      }
      if ((right-place+1) > 1)
      {
           qSort(place+1, right);
      }
      qsort = true;
    }
  
    public void Swap(int index1, int index2)
    {
      vertex temp = new vertex(0,0,0);
      temp = nodes[index1];
      nodes[index1] = nodes[index2];
      nodes[index2] = temp;
    }

    public void AddEdge(String label, int fromid, int toid, double distance, boolean oneway, int speedlimit)
    {
        int right = numberOfCities-1;
        if (qsort == false)
            qSort(left, right);
      //System.out.println("sorted");
        int sourceNum = -1;
        int destNum = -1;
        
        sourceNum = bsearch(fromid);
        
        destNum = bsearch(toid);
        
        Edge Edge = new Edge (label, fromid, toid, distance, oneway, speedlimit);
        nodes[sourceNum].edgelist[nodes[sourceNum].noofedges++] = Edge;
        //nodes[sourceNum].noofedges++;
        if ((nodes[sourceNum].edgelist[nodes[sourceNum].noofedges-1].oneway) == false)
        {
            Edge EdgeBack = new Edge(label, toid, fromid, distance, oneway, speedlimit);
            nodes[destNum].edgelist[nodes[destNum].noofedges++] = EdgeBack;
        }
    }
  
    public int bsearch(int fromid)
    {
      int min = 0;
      int max = numberOfCities-1;
      int id = -1;
      boolean found = false;
      while (found == false)
      {
        if (min <= max)
        {
            int guess = ((min+max)/2);
            if (nodes[guess].cityNum == fromid)
            {
                found = true;
                id = guess;
            }
            else if (nodes[guess].cityNum < fromid)
            {
                min = guess+1;
            }
            else if (nodes[guess].cityNum > fromid)
            {
                max = guess-1;
            }
        }
      }
      return id;
    }
  
    @Override
    public RouteInfoInterface GetRoute(String[] start, String[] end, int routetype)
    {
    RouteInfoInterface routeInfo = new RouteInfo(); 
    int source = 0;
    boolean s0 = false, s1 = false, e0 = false, e1 = false;
    int start0 = 0, start1 = 0, end0 = 0, end1 = 0;
    vertex edges[] = new vertex[6];
    vertex begin = new vertex(0,0,0);
    int destination = 0;
    int cd = 0;
    boolean found = false;
    System.out.println(start[0] + ", " + start[1]);
    System.out.println(end[0] + ", " + end[1]);
    vertex temp = new vertex(0, 0 ,0);
    boolean thisf = false;
    boolean start00 = false;
    boolean start11 = false;
    for (int i = 0; i < numberOfCities; i++)
    {
        if (thisf == false)
        {
            for (int j = 0; j < nodes[i].noofedges; j++)
            {
                if (start00 == false)
                {
                    if (nodes[i].edgelist[j].label.equalsIgnoreCase(start[0])) 
                    {
                        start00 = true;
                        start0 = i;
                    }
                }
                if (start11 == false)
                {
                    if (nodes[i].edgelist[j].label.equalsIgnoreCase(start[1]))
                    {
                        start11 = true;
                        start1 = i;
                    }
                }
                if ((j == nodes[i].noofedges-1) && ((start00 == false) || (start11 == false)))
                {
                    s0 = false;//not neeeded
                    start11 = false;
                    s1 = false;
                    start00 = false;
                }
                if ((start00 == true) && (start11 == true) && (start0 == start1))
                {
                   //System.out.println(cd++);
                   thisf = true;
                   source = nodes[i].cityNum;
                   break;
                }
            }
        }
    }
    if (source == 0)
        System.out.println("Cannot find source node");
    else
        System.out.println(source);
    //source = 435711;
    
    boolean ethisf = false;
    boolean end00 = false;
    boolean end11 = false;
    for (int i = 0; i < numberOfCities; i++)
    {
        if (ethisf == false)
        {
            for (int j = 0; j < nodes[i].noofedges; j++)
            {
                if (end00 == false)
                {
                    if (nodes[i].edgelist[j].label.equalsIgnoreCase(end[0])) 
                    {
                        end00 = true;
                        end0 = i;
                    }
                }
                if (end11 == false)
                {
                    if (nodes[i].edgelist[j].label.equalsIgnoreCase(end[1]))
                    {
                        end11 = true;
                        end1 = i;
                    }
                }
                if ((j == nodes[i].noofedges-1) && ((end00 == false) || (end11 == false)))
                {
                    e0 = false;
                    end11 = false;
                    e1 = false;
                    end00 = false;
                }
                if ((end00 == true) && (end11 == true) && (end0 == end1))
                {
                   
                   ethisf = true;
                   destination = nodes[i].cityNum;
                   break;
                }
            }
        }
    }
    if (destination == 0)
        System.out.println("Cannot find destination node");
    else
        System.out.println(destination); //nodes[111]
    //320198
    
    //-----------------------------------------------astar-------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
      long startTime = System.nanoTime();
      
        vertex closedSet[] = new vertex[setSize];
        
        double totalDistance = 0;
        vertex openSet[] = new vertex[setSize];
        vertex current = new vertex (1,1,1);
        int unvisitedsize = numberOfCities;
        vertex city = new vertex (1,1,1);
        vertex neighbour;
        double tGScore = 0;
        
        openSet[openCounter++] = nodes[bsearch(source)];
        
        
        //came from
        /* for (int i = 0; i < numberOfCities; i++) //through the cities
        {
            for (int j = 0; j < nodes[i].noofedges; j++) //through that cities edgelist
            {
                for (int k = 0; k < numberOfCities; k++) //comapre edgelist to cities
                {
                    if (nodes[k].cityNum == nodes[i].edgelist[j].toid)
                    {
                        //nodes[k].camedfromA = nodes[i].edgelist[j].source;
                        nodes[k].gScore = nodes[i].edgelist[j].distance;
                    }
                }
            }
            System.out.println(c++);
        }*/
        //gSocre
        for (int i = 0; i < numberOfCities; i++) 
        {
            nodes[i].gScore = (nodes[i].cityNum == source ? 0 : 2147483647);//Assign to every node a tentative distance value: zero for start node and infinity for others
        }
        
        //fScore
        for (int i = 0; i < numberOfCities; i++) 
        {
            if (nodes[i].cityNum == source)
                nodes[i].fScore = heuristic(source, destination);
            else 
                nodes[i].fScore = nodes[i].gScore + heuristic(nodes[i].cityNum, destination);  
        }
       
        while (openSet[0] != null) //stopping criteria 
        {
            
            int smallest = 0;
            for (int i = 0; i < openCounter; i++)
            {
                if (openSet[i].fScore <= openSet[smallest].fScore)
                    current = openSet[i]; //current = smallest fScore in openSet
            }
            if (current.cityNum == 1)
                current = openSet[0];
            
            if (current.previous == null)
                current.previous = temp;
            temp = current;
            
            if (current.noofedges >= 1)
                System.out.println ("visited point " + current.edgelist[0].label);
            
            if (current.cityNum == destination)
            {
                System.out.println("from " + source + " you have arrived at destination " + destination);
                break;
            }
            
            remove(openSet, current);
            openCounter--;
            
            add(closedSet, current);
            closedCounter++;
            for (int i = 0; i < current.noofedges; i++)
            {
                for (int j = 0; j < numberOfCities; j++)
                {
                    if (nodes[j].cityNum == (current.edgelist[i].toid))
                    {
                        city = nodes[j]; //find neighbout in nodes[]
                        break;
                    }
                }
                if (exists(city.cityNum , closedSet) == false)
                {   
                    double distance = getDistance(current, city);
                    tGScore = current.gScore + distance;
                    if (exists(city.cityNum , openSet) == false) //current.edgelist[i].destination
                    {
                        if (exists(current.edgelist[i].toid , closedSet) == false)
                        {
                        add(openSet, city);
                        openCounter++;
                        }
                    }
                    else if (tGScore >= city.gScore)
                    {
                        //continue
                        //System.out.println("found destination");
                    }

                    city.gScore = tGScore;
                    double h = heuristic(city.cityNum, destination);
                    city.fScore = city.gScore + h;
                }
            } 
        }
        long endTime = System.nanoTime();
        System.out.println("that took " + ((endTime - startTime)/1000000) + "." + ((endTime - startTime)%1000000) + " milliseconds");
        
        RouteInfo RouteInfo = new RouteInfo();
        if (routetype == 1)
        {
            System.out.printf("The total distance is %.2f", RouteInfo.GetDistanceInMiles(current));
            System.out.print(" miles\n");
        }
        else if (routetype == 2)
        {
            System.out.printf("The total time will take %.2f", RouteInfo.GetTimeInMinues(current));
            System.out.print(" minutes\n");
        }
        else if (routetype == 3)
        {
            System.out.println("\nVertices traversed are:\n");
            RouteInfo.GetRouteVertexIDs(current);
        }
        else if (routetype == 4)
        {
            RouteInfo.GetNoOfExploredVertices(current);
        }
        else if (routetype == 5)
        {
            boolean incvertices = true;
            RouteInfo.GetRoute(current, incvertices);
        }
        
        return routeInfo;
    }
  
    public double getDistance(vertex current, vertex neighbour)
    {
        double distance = 0;
        double edge = 0;
        for (int i = 0 ; i < current.noofedges; i++)
        {
            if (current.edgelist[i].toid == neighbour.cityNum)
            {
                edge = current.edgelist[i].distance;
                break;
            }
        }
        distance = current.distance + edge;
        return distance;
    }
    
    public double heuristic(int source, int destination)
    {
        double distance =0;
        vertex start = new vertex(1,1,1);
        vertex end = new vertex(1,1,1);
        start = nodes[bsearch(source)];
        end = nodes[bsearch(destination)];
        int x = (start.x-end.x);
        int y = (start.y-end.y);
        double heuristic = ((x*x) + (y*y));
        if (heuristic < 0)
            heuristic = heuristic * -1;
        distance = Math.sqrt(heuristic);
        return distance;
    }
    
    public void add(vertex[] set, vertex current)
    {
        for (int i = 0; i < numberOfCities; i++)
        {
            if (set[i] == null)
            {
                set[i] = current;
                closedCounter++;
                break;
            }
            else if (set[i].cityNum == current.cityNum)
                break;
        }
    }
    
    public void remove(vertex[] set, vertex current)
    {
        System.out.print("2");
        if (openCounter > 1)
        {
            qSortset(0, openCounter-1, set);
           
            set[bsearchset(current.cityNum, set)] = set[openCounter-1]; 
        }
        else
            set[bsearchset(current.cityNum, set)] = set[setSize-1];
    }
    
    public void qSortset(int left, int right, vertex set[])
    {
      int place = left;
      int pivot = (left + right)/2;
      Swapend (pivot, right, set);
      int newPivot = right;
      for (int i = left; i <right; i++)
      {
          if (set[i].cityNum < (set[newPivot].cityNum))
          {
              Swapend(i, place, set);
              place++;
          }
      }
      Swapend(place, newPivot,set);
      if ((place-left) > 1)
      {
           qSortset(left, place-1, set);
          
      }
      if ((right-place+1) > 1)
      {
           qSortset(place+1, right, set);
      }
      qsort = true;
    }
    
    public void Swapend(int index1, int index2, vertex set[])
    {
      vertex temp = new vertex(0,0,0);
      temp = set[index1];
      set[index1] = set[index2];
      set[index2] = temp;
    }
    
   
    public int bsearchset(int fromid, vertex set[])
    {
      int min = 0;
      int max = openCounter-1;
      int id = -1;
      boolean found = false;
      while (found == false)
      {
        if (min <= max)
        {
            int guess = ((min+max)/2);
            if (set[guess].cityNum == fromid)
            {
                found = true;
                id = guess;
            }
            else if (set[guess].cityNum < fromid)
            {
                min = guess+1;
            }
            else if (set[guess].cityNum > fromid)
            {
                max = guess-1;
            }
        }
      }
      return id;
    }
    
    public boolean exists(int neighbour, vertex[] Set)
    {
        boolean found = false;
        for (int i = 0; i < closedCounter; i++)
        {
            if (Set[i] == null)
                break;
            else if (Set[i].cityNum == neighbour)
            {
                found = true;
                break;
            }
            else 
                found = false;
        }
        return found;
    }
    
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    
}
