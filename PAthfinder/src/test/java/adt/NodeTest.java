package adt;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class NodeTest {

    @Test
    public void testGetPosition() {
        Node node1 = new Node(1.0f, 2.0f);
        Node node2 = new Node(3.0f, 4.0f);

        node1.setPosition(1);
        node2.setPosition(2);

        assertEquals(1, node1.getPosition());
        assertEquals(2, node2.getPosition());
    }
    @Test
    void testEquals() {
        Node node1 = new Node(1.234f, 2.345f);
        Node node2 = new Node(1.234f, 2.345f);
        Node node3 = new Node(3.456f, 4.567f);

        assertTrue(node1.equals(node2));
        assertFalse(node1.equals(node3));
    }
    @Test
    void testRange() {
        Node node = new Node(1.23f, 4.56f);

        node = new Node(10.123f, 20.456f);
        assertEquals(10.12f, node.x());
        assertEquals(20.46f, node.y());

        node = new Node(0.1f, 0.2f);
        assertEquals(.10f, node.x());
        assertEquals(.20f, node.y());
    }
    @Test
    public void testNodeExists() {
        Node node = new Node(1.23f, 4.56f);
        assertNotNull(node);
    }


}





