package islandGenTest;

import adt.Polygon;
import adt.TerrainType;
import adt.Vertex;
import islandGen.islandElevation.LinearPlateauAltitudeAssigner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearPlateauAltitudeAssignerTest {
    static double mesh_width;
    static double mesh_height;
    static LinearPlateauAltitudeAssigner linearPlateauAltitudeAssigner;
    static Polygon highAltitudePolygon;
    static Polygon lowAltitudePolygon;
    static Polygon onLinePolygon;
    static Polygon boundaryPolygon1;
    static Polygon boundaryPolygon2;
    static Polygon oceanPolygon;

    @BeforeAll
    public static void initLinearPlateauAltitudeAssigner(){
        mesh_width = 100;
        mesh_height = 100;
        linearPlateauAltitudeAssigner = new LinearPlateauAltitudeAssigner(mesh_width,mesh_height);
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
        lowAltitudePolygon.add(new Vertex(0,10));
        lowAltitudePolygon.add(new Vertex(10,10));
        lowAltitudePolygon.add(new Vertex(10,0));
        lowAltitudePolygon.add(new Vertex(0,0));
    }

    @BeforeAll
    public static void initOnLinePolygon(){
        onLinePolygon = new Polygon();
        onLinePolygon.add(new Vertex(90,10));
        onLinePolygon.add(new Vertex(100,10));
        onLinePolygon.add(new Vertex(100,0));
        onLinePolygon.add(new Vertex(90,0));
    }

    @BeforeAll
    public static void initBoundaryPolygon1(){
        //polygon centroid is on the outline of mesh
        boundaryPolygon1 = new Polygon();
        boundaryPolygon1.add(new Vertex(-5,5));
        boundaryPolygon1.add(new Vertex(5,5));
        boundaryPolygon1.add(new Vertex(5,-5));
        boundaryPolygon1.add(new Vertex(-5,-5));
    }
    @BeforeAll
    public static void initBoundaryPolygon2(){
        //polygon centroid is outside of mesh
        boundaryPolygon2 = new Polygon();
        boundaryPolygon2.add(new Vertex(0,-10));
        boundaryPolygon2.add(new Vertex(-10,-10));
        boundaryPolygon2.add(new Vertex(-10,0));
        boundaryPolygon2.add(new Vertex(0,0));
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
        linearPlateauAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        assertTrue(highAltitudePolygon.getAltitude() <= 75); //Upper Bound
        assertTrue(highAltitudePolygon.getAltitude() >= 60); //Lower Bound
    }
    @Test
    public void testShortHeight() throws Exception{
        linearPlateauAltitudeAssigner.assignPolygonAltitude(lowAltitudePolygon);
        assertTrue(lowAltitudePolygon.getAltitude() <= 20); //Upper Bound
        assertTrue(lowAltitudePolygon.getAltitude() >= 0); //Lower Bound
    }

    @Test
    public void testBoundaryHeight1() throws Exception{
        linearPlateauAltitudeAssigner.assignPolygonAltitude(boundaryPolygon1);
        assertEquals(boundaryPolygon1.getAltitude(),0,0.01);
    }

    @Test
    public void testBoundaryHeight2() throws Exception{
        linearPlateauAltitudeAssigner.assignPolygonAltitude(boundaryPolygon2);
        assertEquals(boundaryPolygon2.getAltitude(),0,0.01);
    }

    @Test
    public void testConstantOceanAltitude() throws Exception{
        double initialAltitude = oceanPolygon.getAltitude();

        linearPlateauAltitudeAssigner.assignPolygonAltitude(oceanPolygon);
        double finalAltitude = oceanPolygon.getAltitude();

        assertEquals(initialAltitude,finalAltitude);
    }

    @Test
    public void testMeshDimensionDependency() throws Exception{
        //Test if linearPlateauAltitudeAssigner is dependent on mesh size
        linearPlateauAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        double altitudeSmallMesh = highAltitudePolygon.getAltitude();

        double bigger_mesh_width = 1000;
        double bigger_mesh_height = 1000;

        LinearPlateauAltitudeAssigner bigMeshAltitudeAssigner = new LinearPlateauAltitudeAssigner(bigger_mesh_width,bigger_mesh_height);
        bigMeshAltitudeAssigner.assignPolygonAltitude(highAltitudePolygon);
        double altitudeBigMesh = highAltitudePolygon.getAltitude();

        assertNotEquals(altitudeSmallMesh, altitudeBigMesh);
        assertTrue(altitudeBigMesh < altitudeSmallMesh);
    }

    @Test
    public void testCalculatePolygonDistanceFromPeak() throws Exception{
        double highAltitudePolygonDistanceFromPeak = linearPlateauAltitudeAssigner.calculatePolygonDistanceFromPeak(highAltitudePolygon);
        assertEquals(highAltitudePolygonDistanceFromPeak,0,0.5);

        double lowAltitudePolygonDistanceFromPeak = linearPlateauAltitudeAssigner.calculatePolygonDistanceFromPeak(lowAltitudePolygon);
        assertEquals(lowAltitudePolygonDistanceFromPeak,63.6, 0.5);
    }

    @Test
    public void testOnLineCalculatePolygonDistanceFromPeak() throws Exception{
        double onLineAltitudePolygonDistanceFromPeak = linearPlateauAltitudeAssigner.calculatePolygonDistanceFromPeak(onLinePolygon);
        assertEquals(onLineAltitudePolygonDistanceFromPeak,0,0.5);
    }

    @Test
    public void testBoundaryCalculatePolygonDistanceFromPeak() throws Exception{
        double boundaryPolygon1DistanceFromPeak = linearPlateauAltitudeAssigner.calculatePolygonDistanceFromPeak(boundaryPolygon1);
        assertEquals(boundaryPolygon1DistanceFromPeak,70.7,0.5);

        double boundaryPolygon2DistanceFromPeak = linearPlateauAltitudeAssigner.calculatePolygonDistanceFromPeak(boundaryPolygon2);
        assertEquals(boundaryPolygon2DistanceFromPeak,77.7, 0.5);
    }
}
