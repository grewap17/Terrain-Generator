package Path;

import adt.Edge;
import adt.Graph;
import adt.Node;

import java.util.*;

public class BFS implements FindPath {


    public List<Node> FindThePath(Graph graph,Node start, Node end) {


        Set<Node> nextNodeSet  = new HashSet<>();
        LinkedList<Node> Shortest = new LinkedList<>();
        Map<Node, Double> distanceMap = new HashMap<>();

        Map<Node, Node> Visited= new HashMap<>();

        Comparator<Node> nodeDistanceCompare = Comparator.comparingDouble(nodes -> distanceMap.getOrDefault(nodes, Double.MAX_VALUE));
        PriorityQueue<Node> PrioQue = new PriorityQueue<>(nodeDistanceCompare);

        PrioQue.add(start);
        distanceMap.put(start, (double) 0);

        for (Node nodess : graph.getAdjacencyList().keySet()){
            if (nodess != null){
                double Length = (nodess.equals(start)) ? 0: Double.MAX_VALUE;
                distanceMap.put(nodess,Length);
                nextNodeSet .add(nodess);

            }
        }
        boolean reachedEnd = false;
        while(PrioQue.peek() != null && !reachedEnd){
            Node CurNode = PrioQue.poll();

            nextNodeSet.remove(CurNode);

            if (CurNode.equals(end)) {
                reachedEnd = true;
            }

            for (Edge Neighbours : graph.getEdges(CurNode)){
                Node FirstNeigh= Neighbours.getDestination();

                if(nextNodeSet .contains(FirstNeigh)){

                    double leng = distanceMap.get(CurNode) + Neighbours.getWeight();


                    if (leng < distanceMap.get(FirstNeigh)){

                        Visited.put(FirstNeigh, CurNode);

                        distanceMap.put(FirstNeigh, leng);
                        PrioQue.add(FirstNeigh);
                    }
                }
            }
        }

        Node CitiesOnPath = end;
        while (CitiesOnPath != null) {
            Shortest.addFirst(CitiesOnPath);
            CitiesOnPath = Visited.get(CitiesOnPath);
        }
        if (Shortest.isEmpty() || !Shortest.getFirst().equals(start)) {
            return null;
        } else {
            return Shortest;
        }    }


}