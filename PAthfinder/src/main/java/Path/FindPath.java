package Path;

import adt.Graph;
import adt.Node;

import java.util.List;

public interface FindPath {
    List<Node> FindThePath(Graph graph, Node start, Node end);
}
