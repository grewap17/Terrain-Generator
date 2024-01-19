package PATH;

import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class MakePath {



    public void ColorPath(Structs.Mesh.Builder MeshBuilder, List<Node> ShortestPath){
        Structs.Property RoadWidth = Structs.Property.newBuilder().setKey("WidthOfRoad").setValue(String.valueOf(4)).build();
        Structs.Property Road = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
        List<Structs.Vertex> vertices = MeshBuilder.getVerticesList();

        for (int Node = 0; Node < ShortestPath.size()-1;Node++){
            Node Node1 = new Node(ShortestPath.get(Node).x(),ShortestPath.get(Node).y());
            Node Node2 = new Node(ShortestPath.get(Node+1).x(),ShortestPath.get(Node+1).y());

            Structs.Vertex v1 = Structs.Vertex.newBuilder().setX(Node1.x()).setY(Node1.y()).build();
            Structs.Vertex v2 = Structs.Vertex.newBuilder().setX(Node2.x()).setY(Node2.y()).build();
            MeshBuilder.addVertices(v1);
            MeshBuilder.addVertices(v2);

            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(vertices.indexOf(v1)).setV2Idx(vertices.indexOf(v2)).addProperties(Road).addProperties(RoadWidth).build();
            MeshBuilder.addSegments(s);
        }
    }
}
