package importerTest;

import adt.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import adt.Vertex;
import adt.Polygon;
import importer.Importer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImporterTest {
    static Importer importer;
    static List<Structs.Polygon> dummyIOPolygons;
    static Structs.Mesh dummyIOMesh;

    @BeforeAll
    public static void initImporter() {
        importer = new Importer();
    }

    @BeforeAll
    public static void initDummyMesh(){

        List<Structs.Vertex> dummyVertices = new ArrayList<>();
        dummyVertices.add(Structs.Vertex.newBuilder().setX(0).setY(0).build());
        dummyVertices.add(Structs.Vertex.newBuilder().setX(0).setY(20).build());
        dummyVertices.add(Structs.Vertex.newBuilder().setX(20).setY(0).build());
        dummyVertices.add(Structs.Vertex.newBuilder().setX(20).setY(20).build());

        List<Structs.Segment> dummySegments = new ArrayList<>();
        dummySegments.add(Structs.Segment.newBuilder().setV1Idx(0).setV2Idx(1).build());
        dummySegments.add(Structs.Segment.newBuilder().setV1Idx(0).setV2Idx(2).build());
        dummySegments.add(Structs.Segment.newBuilder().setV1Idx(2).setV2Idx(3).build());
        dummySegments.add(Structs.Segment.newBuilder().setV1Idx(3).setV2Idx(1).build());


        dummyIOPolygons = new ArrayList<>();
        dummyIOPolygons.add(Structs.Polygon.newBuilder()
                .addSegmentIdxs(0)
                .addSegmentIdxs(1)
                .addSegmentIdxs(2)
                .addSegmentIdxs(3).build());

        dummyIOMesh = Structs.Mesh.newBuilder()
                .addAllVertices(dummyVertices)
                .addAllSegments(dummySegments)
                .addAllPolygons(dummyIOPolygons).build();
    }

    @Test
    public void testCalculateMeshWidth() throws Exception{
        assertEquals(importer.calculateMeshWidth(dummyIOMesh), 20);
    }
    @Test
    public void testCalculateMeshHeight() throws Exception{
        assertEquals(importer.calculateMeshHeight(dummyIOMesh), 20);
    }

    @Test
    public void testConvertPolygon() throws Exception{
        Polygon expected_polygon = new Polygon();
        expected_polygon.add(new Vertex(0,20));
        expected_polygon.add(new Vertex(20,20));
        expected_polygon.add(new Vertex(20,0));
        expected_polygon.add(new Vertex(0,0));

        Polygon actual_polygon = importer.convertPolygon(dummyIOPolygons.get(0), dummyIOMesh);

        assertEquals(expected_polygon,actual_polygon);
    }

    @Test
    public void testRegisterPolygons() throws Exception{
        Polygon expected_polygon = new Polygon();
        expected_polygon.add(new Vertex(0,20));
        expected_polygon.add(new Vertex(20,20));
        expected_polygon.add(new Vertex(20,0));
        expected_polygon.add(new Vertex(0,0));

        Mesh expected_mesh = new Mesh(20,20);
        expected_mesh.register(expected_polygon);

        Mesh actual_mesh1 = new Mesh(20,20);
        importer.registerPolygons(dummyIOMesh,actual_mesh1);
        assertEquals(actual_mesh1,expected_mesh);

        Mesh actual_mesh2 = new Mesh(10,20);
        importer.registerPolygons(dummyIOMesh,actual_mesh2);
        assertNotEquals(actual_mesh2,expected_mesh);
    }

    @Test
    public void testRun() throws Exception{
        Polygon expected_polygon = new Polygon();
        expected_polygon.add(new Vertex(0,20));
        expected_polygon.add(new Vertex(20,20));
        expected_polygon.add(new Vertex(20,0));
        expected_polygon.add(new Vertex(0,0));

        Mesh expected_mesh = new Mesh(20,20);
        expected_mesh.register(expected_polygon);

        Mesh actual_mesh = importer.run(dummyIOMesh);
        assertEquals(actual_mesh,expected_mesh);
    }
}
