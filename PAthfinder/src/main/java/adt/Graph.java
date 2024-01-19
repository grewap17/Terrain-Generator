package adt;

import Path.BFS;

import java.util.*;

public class Graph {
    Map<Node, List<Edge>> adjList;
    public HashMap<Integer, Node> nodeMap = new HashMap<>();

    public Graph() {
        this.adjList = new HashMap<>();
    }

    public List<Edge> getEdges(Node node) {
        return adjList.get(node);
    }

    public void addEdge(Node source, Node destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        if (!adjList.containsKey(source)) {
            adjList.put(source, new LinkedList<>());
        }
        adjList.get(source).add(edge);

        Edge opposite = new Edge(destination, source, weight);
        if (!adjList.containsKey(destination)) {
            adjList.put(destination, new LinkedList<>());
        }
        adjList.get(source).add(opposite);
    }



    public Map<Node, List<Edge>> getAdjacencyList(){

        return adjList;
    }



    }


