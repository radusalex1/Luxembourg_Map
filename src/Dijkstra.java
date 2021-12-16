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
