package PATH;

import adt.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class FIndPath {




    public  Graph MakeAPath(Mesh iMesh,Structs.Mesh.Builder MeshBulider) {
        Graph graph = new Graph();
        List<Structs.Vertex> vertices = MeshBulider.getVerticesList();
        Structs.Vertex centroid1;
        Node Node1;

        for (Polygon tile : iMesh){
            if (tile.getTileTerrain() != TerrainType.LAKE && tile.getTileTerrain() != TerrainType.LAGOON && tile.getTileTerrain() != TerrainType.OCEAN) {
                 centroid1 = Structs.Vertex.newBuilder().setX(tile.centroid().x()).setY(tile.centroid().y()).build();
                Node1 = new Node((float) centroid1.getX(), (float) centroid1.getY());

               int Centroidindx=vertices.indexOf(centroid1);

                Node1.setPosition(Centroidindx);
                graph.nodeMap.put(Centroidindx, Node1);


            }
        }
        Structs.Vertex centroidOne;
        Node Nodeone;
        Structs.Vertex centroidoneTwo;
        Structs.Segment segment;

        for (Polygon p : iMesh){
            if (p.getTileTerrain() != TerrainType.LAKE && p.getTileTerrain() != TerrainType.OCEAN && p.getTileTerrain() != TerrainType.LAGOON) {
                 centroidOne = Structs.Vertex.newBuilder().setX(p.centroid().x()).setY(p.centroid().y()).build();
                 Nodeone = new Node((float) centroidOne.getX(), (float) centroidOne.getY());

                for (Polygon pNeigh : p.neighbours()) {

                    if (pNeigh.getTileTerrain() != TerrainType.LAKE && pNeigh.getTileTerrain() != TerrainType.OCEAN && pNeigh.getTileTerrain() != TerrainType.LAGOON) {

                         centroidoneTwo = Structs.Vertex.newBuilder().setX(pNeigh.centroid().x()).setY(pNeigh.centroid().y()).build();

                         segment = Structs.Segment.newBuilder().setV1Idx(vertices.indexOf(centroidOne)).setV2Idx(vertices.indexOf(centroidoneTwo)).build();

                        MeshBulider.addSegments(segment);
                        double XLength= Math.pow((p.centroid().x() - pNeigh.centroid().x()), 2);
                        double YLength=Math.pow((p.centroid().y() - pNeigh.centroid().y()), 2);
                        double Length = Math.sqrt(XLength+ YLength);


                        graph.addEdge(graph.nodeMap.get(vertices.indexOf(centroidOne)),graph.nodeMap.get(vertices.indexOf(centroidoneTwo)), Length);
                    }
                }
            }
        }
        return graph;
    }
}








