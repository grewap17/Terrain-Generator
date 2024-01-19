package islandGenTest;

import adt.Polygon;
import adt.TerrainType;
import adt.Vertex;
import islandGen.islandElevation.FlatAltitudeAssigner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatAltitudeAssignerTest {

    static double mesh_width;
    static double mesh_height;
    static FlatAltitudeAssigner flatAltitudeAssigner;
    static Polygon highAltitudePolygon;
    static Polygon lowAltitudePolygon;
    static Polygon boundaryPolygon1;
    static Polygon boundaryPolygon2;
    static Polygon oceanPolygon;

    @BeforeAll
    public static void initFlatAltitudeAssigner(){
        mesh_width = 100;
        mesh_height = 100;
        flatAltitudeAssigner = new FlatAltitudeAssigner(mesh_width,mesh_height);
    }
    @BeforeAll
    public static void initHighPolygon(){
        highAltitudePolygon = new Polygon();
        highAltitudePolygon.add(new Vertex(45,55));
        highAltitudePolygon.add(new Vertex(55,55));
        highAltitudePolygon.add(new Vertex(55,45));
        highAltitudePolygon.add(new Vertex(45,45));
    }

    @BeforeAll
    public static void initLowPolygon(){
        lowAltitudePolygon = new Polygon();
        lowAltitudePolygon.add(new Vertex(0,55));
        lowAltitudePolygon.add(new Vertex(10,55));
        lowAltitudePolygon.add(new Vertex(10,45));
        lowAltitudePolygon.add(new Vertex(0,45));
    }

    @BeforeAll
    public static void initBoundaryPolygon1(){
        //polygon centroid is on the outline of mesh
        boundaryPolygon1 = new Polygon();
        boundaryPolygon1.add(new Vertex(-5,55));
        boundaryPolygon1.add(new Vertex(5,55));
        boundaryPolygon1.add(new Vertex(5,45));
        boundaryPolygon1.add(new Vertex(-5,45));
    }
    @BeforeAll
    public static void initBoundaryPolygon2(){
        //polygon centroid is outside of mesh
        boundaryPolygon2 = new Polygon();
        boundaryPolygon2.add(new Vertex(-0,55));
        boundaryPolygon2.add(new Vertex(-10,55));
        boundaryPolygon2.add(new Vertex(-10,45));
        boundaryPolygon2.add(new Vertex(-0,45));
    }
    @BeforeAll
    public static void initOceanPolygon(){
        oceanPolygon = new Polygon();
        oceanPolygon.add(new Vertex(45,55));
        oceanPolygon.add(new Vertex(55,55));
        oceanPolygon.add(new Vertex(55,45));
        oceanPolygon.add(new Vertex(45,45));
        oceanPolygon.assignTileTerrain(TerrainType.OCEAN);
    }

    @Test
    public void testTallHeight() throws Exception{
        flatAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        assertTrue(highAltitudePolygon.getAltitude() <= 40); //Upper Bound
        assertTrue(highAltitudePolygon.getAltitude() >= 20); //Lower Bound
    }
    @Test
    public void testShortHeight() throws Exception{
        flatAltitudeAssigner.assignPolygonAltitude(lowAltitudePolygon);
        assertTrue(lowAltitudePolygon.getAltitude() <= 20); //Upper Bound
        assertTrue(lowAltitudePolygon.getAltitude() >= 0); //Lower Bound
    }

    @Test
    public void testBoundaryHeight1() throws Exception{
        flatAltitudeAssigner.assignPolygonAltitude(boundaryPolygon1);
        assertEquals(boundaryPolygon1.getAltitude(),0,0.01);
    }

    @Test
    public void testBoundaryHeight2() throws Exception{
        flatAltitudeAssigner.assignPolygonAltitude(boundaryPolygon2);
        assertEquals(boundaryPolygon2.getAltitude(),0,0.01);
    }

    @Test
    public void testConstantOceanAltitude() throws Exception{
        double initialAltitude = oceanPolygon.getAltitude();

        flatAltitudeAssigner.assignPolygonAltitude(oceanPolygon);
        double finalAltitude = oceanPolygon.getAltitude();

        assertEquals(initialAltitude,finalAltitude);
    }

    @Test
    public void testMeshDimensionDependency() throws Exception{
        //Test if flatAltitudeAssigner is dependent on mesh size
        flatAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        double altitudeSmallMesh = highAltitudePolygon.getAltitude();

        double bigger_mesh_width = 1000;
        double bigger_mesh_height = 1000;

        FlatAltitudeAssigner bigMeshAltitudeAssigner = new FlatAltitudeAssigner(bigger_mesh_width,bigger_mesh_height);
        bigMeshAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        double altitudeBigMesh = highAltitudePolygon.getAltitude();

        assertNotEquals(altitudeSmallMesh, altitudeBigMesh);
        assertTrue(altitudeBigMesh < altitudeSmallMesh);
    }

    @Test
    public void testCalculatePolygonDistanceFromPeak() throws Exception{
        double highAltitudePolygonDistanceFromPeak = flatAltitudeAssigner.calculatePolygonDistanceFromPeak(highAltitudePolygon);
        assertEquals(highAltitudePolygonDistanceFromPeak,0,0.5);

        double lowAltitudePolygonDistanceFromPeak = flatAltitudeAssigner.calculatePolygonDistanceFromPeak(lowAltitudePolygon);
        assertEquals(lowAltitudePolygonDistanceFromPeak,45, 0.5);
    }

    @Test
    public void testBoundaryCalculatePolygonDistanceFromPeak() throws Exception{
        double boundaryPolygon1DistanceFromPeak = flatAltitudeAssigner.calculatePolygonDistanceFromPeak(boundaryPolygon1);
        assertEquals(boundaryPolygon1DistanceFromPeak,50,0.5);

        double boundaryPolygon2DistanceFromPeak = flatAltitudeAssigner.calculatePolygonDistanceFromPeak(boundaryPolygon2);
        assertEquals(boundaryPolygon2DistanceFromPeak,55, 0.5);
    }
}
