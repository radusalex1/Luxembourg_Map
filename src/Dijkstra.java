/*
public class Dijkstra {

    public void Djekstra(Nodes start, Nodes end)
    {
        final long startTime = System.nanoTime();
        for(int i=0;i<m_nodeList.size();i++)
        {
            m_nodeList.get(i).setParent(-1);
            m_nodeList.get(i).setVisited(false);
            m_nodeList.get(i).setValue(Integer.MAX_VALUE);
        }
        start.setValue(0);
        PriorityQueue<Nodes> iteration = new PriorityQueue<Nodes>(100000,new NodeComparator());
        iteration.add(start);


        while(iteration.size()>0)
        {
            Nodes current = iteration.poll();
            m_nodeList.get(current.getNumber()).setVisited(true);
            Vector<Point> neighboors = current.getNeighboors(); // get all the neighboors
            for(int i=0;i<neighboors.size();i++)                // see if there is a neighboor not visited
            {
                int cost = neighboors.get(i).y+current.getValue();
                if(cost<m_nodeList.get(neighboors.get(i).x).getValue())
                {
                    Nodes addToIteration =m_nodeList.get(neighboors.get(i).x);
                    m_nodeList.get(neighboors.get(i).x).setValue(cost);
                    m_nodeList.get(neighboors.get(i).x).setParent(current.getNumber());
                    if(!iteration.contains(addToIteration))
                    {
                        if(!addToIteration.getVisited())
                            updateQueue(iteration,addToIteration);

                    }
                }
            }
        }


//			 Nodes currentRev = end;
//			 while(currentRev!=start)
//			 {
//				System.out.print(currentRev.getNumber()+" ");
//				currentRev=m_nodeList.get(currentRev.getParent());
//			 }
        final long duration = System.nanoTime() - startTime;
        System.out.println("djekstra: "+duration/1000000000.);
    }
}
*/

import java.util.Vector;
import java.util.*;
class Node
{
    int vertex, weight;

    public Node(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
}


class Dijkstra{

    private static int sourceId;
    private static int nodes;
    private static List<List<Arc>> adjList;
    private static int destinationId;
    private static  List<Integer> route = new ArrayList<>();

    public Dijkstra(List<List<Arc>> adjList, int sourceId,int destinationId, int nodes) {
        this.adjList = adjList;
        this.sourceId = sourceId;
        this.nodes = nodes;
        this.destinationId=destinationId;
    }
    private static void getRoute(int[] prev, int destinationId, List<Integer> route)
    {
        if (destinationId >= 0)
        {
            getRoute(prev, prev[destinationId], route);
            route.add(destinationId);
        }
    }
    public static void findShortestPath()
    {
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        minHeap.add(new Node(sourceId, 0));

        List<Integer> dist;
        dist = new ArrayList<>(Collections.nCopies(nodes,Integer.MAX_VALUE));

        dist.set(sourceId,0);

        boolean[] done = new boolean[nodes];
        done[sourceId]=true;

        int[] prev = new int[nodes];
        prev[sourceId]=-1;
        boolean ok=false;
        while(!minHeap.isEmpty())
        {
            Node node = minHeap.poll();
            int u = node.vertex;
            for(Arc arc:adjList.get(u))
            {
                int v=arc.getDestinationId();
                int weight = arc.getCost();
                if(!done[v] && (dist.get(u)+weight)<dist.get(v))
                {
                    dist.set(v,dist.get(u)+weight);
                    prev[v]=u;
                    minHeap.add(new Node(v,dist.get(v)));
                }
            }
            done[u]=true;
        }


        getRoute(prev,destinationId,route);

        System.out.print(route);
       /* for (int i = 0; i < nodes; i++)
        {
            if (i != sourceId && dist.get(i) != Integer.MAX_VALUE)
            {
                getRoute(prev, destinationId, route);
                System.out.printf("Path (%d —> %d): Minimum cost = %d, Route = %s\n",
                        sourceId, i, dist.get(i), route);
                route.clear();
            }
        }*/
    }

    public static List<Integer> getRoute() {
        return route;
    }
}