package importer;

import adt.Mesh;
import adt.Polygon;
import adt.Vertex;
import adt.neighbours.DelaunayNeighbourhood;
import ca.mcmaster.cas.se2aa4.a2.io.*;

import java.util.Set;

public class Importer {
    public Importer() {}

    public Mesh run(Structs.Mesh input_mesh) {
        int mesh_width = calculateMeshWidth(input_mesh);
        int mesh_height = calculateMeshHeight(input_mesh);
        Mesh meshADT = new Mesh(mesh_width,mesh_height);

        registerPolygons(input_mesh, meshADT);

        meshADT.populateNeighbours(new DelaunayNeighbourhood());

        return meshADT;
    }

    public int calculateMeshWidth(Structs.Mesh input_mesh){
        double max_x = Double.MIN_VALUE;
        for (Structs.Vertex v: input_mesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
        }
        return (int) max_x;
    }

    public int calculateMeshHeight(Structs.Mesh input_mesh){
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: input_mesh.getVerticesList()) {
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        return (int) max_y;
    }

    public void registerPolygons(Structs.Mesh input_mesh, Mesh meshToRegister){
        for (Structs.Polygon polygon_io: input_mesh.getPolygonsList()){
            Polygon converted_polygon = convertPolygon(polygon_io, input_mesh);
            meshToRegister.register(converted_polygon);
        }
    }
    public Polygon convertPolygon(Structs.Polygon polygon_io, Structs.Mesh input_mesh){
        Polygon converted_polygon = new Polygon();

        IOHull ioHull = new IOHull();
        for(Integer segmentIdx: polygon_io.getSegmentIdxsList()) {
            ioHull.add(input_mesh.getSegments(segmentIdx), input_mesh);
        }
        Set<Structs.Vertex> vertices_io = ioHull.getVertices();

        for (Structs.Vertex vertex_io: vertices_io){
            Vertex vertex_adt = new Vertex((float) vertex_io.getX(), (float) vertex_io.getY());
            converted_polygon.add(vertex_adt);
        }
        return converted_polygon;
    }
}
