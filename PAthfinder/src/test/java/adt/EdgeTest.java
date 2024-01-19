package adt;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
class EdgeTest {

    @Test
    public void testEdgeWithZeroWeight() {
        Node node1 = new Node(1, 2);
        Node node2 = new Node(3, 4);
        Edge edge = new Edge(node1, node2, 0.0);
        assertEquals(0.0, edge.getWeight());
    }

    @Test
    public void testInverseEdge() {
        Node node1 = new Node(1, 2);
        Node node2 = new Node(3, 4);
        Edge edge1 = new Edge(node1, node2, 5.0);
        Edge edge2 = new Edge(node2, node1, 5.0);
        assertEquals(edge1.getWeight(), edge2.getWeight());
    }


    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void testPerformance() {
        Node node1 = new Node(1, 2);
        Node node2 = new Node(3, 4);
        for (int i = 0; i < 1000000; i++) {
            Edge edge = new Edge(node1, node2, 5.0);
        }
    }


}

