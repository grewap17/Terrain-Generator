package adt;

import java.util.HashSet;
import java.util.Set;

public class Edge{
    private Node destVertex;
    private Node startVertex;
    private Double cost;

    Set<Node> edge;

    public Edge(Node startVertex,Node destVertex, Double cost) {
        this.startVertex = startVertex;
        this.destVertex=destVertex;
        this.cost=cost;
    }


    public Node getDestination() {
        return destVertex;
    }
    public Node getStartVertex() {
        return startVertex;
    }
    public double getWeight() {
        return cost;
    }
}





