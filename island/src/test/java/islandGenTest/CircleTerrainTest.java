package islandGenTest;

import adt.Polygon;
import adt.TerrainType;
import adt.Vertex;
import islandGen.CircleTerrain;
import islandGen.RectangleTerrain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircleTerrainTest {
    static CircleTerrain circleTerrain;
    static double mesh_width;
    static double mesh_height;
    static Polygon landPolygon;
    static Polygon oceanPolygon;
    static Polygon beachPolygon;
    static Polygon nullTerrainPolygon;
    static Polygon middlePolygon;
    static Polygon cornerPolygon;
    static Polygon neighbourPolygon;
    static Polygon testPolygon;

    @BeforeAll
    public static void initCircleTerrain(){
        circleTerrain = new CircleTerrain();
    }

    @BeforeAll
    public static void initMeshDimensions(){
        mesh_width = 100;
        mesh_height = 100;
    }
    @BeforeAll
    public static void initLandPolygon(){
        landPolygon = new Polygon();
        landPolygon.assignTileTerrain(TerrainType.LAND);
    }
    @BeforeAll
    public static void initOceanPolygon(){
        oceanPolygon = new Polygon();
        oceanPolygon.assignTileTerrain(TerrainType.OCEAN);
    }
    @BeforeAll
    public static void initBeachPolygon(){
        beachPolygon = new Polygon();
        beachPolygon.assignTileTerrain(TerrainType.BEACH);
    }
    @BeforeAll
    public static void initNullTerrainPolygon(){
        nullTerrainPolygon = new Polygon();
    }
    @BeforeAll
    public static void initMiddlePolygon(){
        middlePolygon = new Polygon();
        middlePolygon.add(new Vertex(45,55));
        middlePolygon.add(new Vertex(55,55));
        middlePolygon.add(new Vertex(55,45));
        middlePolygon.add(new Vertex(45,45));
    }
    @BeforeAll
    public static void initCornerPolygon(){
        cornerPolygon = new Polygon();
        cornerPolygon.add(new Vertex(0,20));
        cornerPolygon.add(new Vertex(20,20));
        cornerPolygon.add(new Vertex(20,0));
        cornerPolygon.add(new Vertex(0,0));
    }
    @BeforeAll
    public static void initCornerNeighbourPolygon(){
        neighbourPolygon = new Polygon();
        neighbourPolygon.add(new Vertex(20,20));
        neighbourPolygon.add(new Vertex(40,20));
        neighbourPolygon.add(new Vertex(40,0));
        neighbourPolygon.add(new Vertex(20,0));
    }

    @BeforeAll
    public static void initTestPolygon(){
        testPolygon = new Polygon();
        testPolygon.add(new Vertex(45,55));
        testPolygon.add(new Vertex(55,55));
        testPolygon.add(new Vertex(55,45));
        testPolygon.add(new Vertex(45,45));
    }

    @BeforeEach
    public void initNeighbours(){
        neighbourPolygon.registerAsNeighbour(cornerPolygon);
        neighbourPolygon.registerAsNeighbour(middlePolygon);
        cornerPolygon.registerAsNeighbour(neighbourPolygon);
        middlePolygon.registerAsNeighbour(neighbourPolygon);

        landPolygon.registerAsNeighbour(beachPolygon);
        oceanPolygon.registerAsNeighbour(beachPolygon);
        beachPolygon.registerAsNeighbour(oceanPolygon);
        beachPolygon.registerAsNeighbour(landPolygon);
        nullTerrainPolygon.registerAsNeighbour(landPolygon);
        landPolygon.registerAsNeighbour(nullTerrainPolygon);
    }

    @Test
    public void testForMiddleLand() throws Exception{
        circleTerrain.assignPolygonTerrain(middlePolygon, mesh_width, mesh_height);
        assertEquals(middlePolygon.getTileTerrain(),TerrainType.LAND);
    }
    @Test
    public void testForCornerOcean() throws Exception{
        circleTerrain.assignPolygonTerrain(cornerPolygon, mesh_width, mesh_height);
        assertEquals(cornerPolygon.getTileTerrain(),TerrainType.OCEAN);
    }
    @Test
    public void testForNeighbourBeach() throws Exception{
        circleTerrain.assignPolygonTerrain(neighbourPolygon, mesh_width, mesh_height);
        assertEquals(neighbourPolygon.getTileTerrain(),TerrainType.BEACH);
    }

    @Test
    public void testTerrainAssignmentMeshDimensionDependency() throws Exception{
        //Test if assignPolygonTerrain is dependent on mesh size
        circleTerrain.assignPolygonTerrain(testPolygon, mesh_width, mesh_height);
        TerrainType smallMeshPolygonTerrain = testPolygon.getTileTerrain();

        double bigger_mesh_width = 1000;
        double bigger_mesh_height = 1000;

        circleTerrain.assignPolygonTerrain(testPolygon, bigger_mesh_width, bigger_mesh_height);
        TerrainType bigMeshPolygonTerrain = testPolygon.getTileTerrain();

        assertEquals(bigMeshPolygonTerrain,TerrainType.OCEAN);
        assertNotEquals(smallMeshPolygonTerrain, bigMeshPolygonTerrain);
    }

    @Test
    public void regularTestCheckForNeighbouringTerrain() throws Exception{
        assertTrue(circleTerrain.checkForNeighbouringTerrain(landPolygon,TerrainType.BEACH));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(landPolygon,TerrainType.OCEAN));

        assertTrue(circleTerrain.checkForNeighbouringTerrain(oceanPolygon,TerrainType.BEACH));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(oceanPolygon,TerrainType.LAND));

        assertTrue(circleTerrain.checkForNeighbouringTerrain(beachPolygon,TerrainType.LAND));
        assertTrue(circleTerrain.checkForNeighbouringTerrain(beachPolygon,TerrainType.OCEAN));
    }

    @Test
    public void boundaryTestCheckForNeighbouringTerrain() throws Exception{
        assertFalse(circleTerrain.checkForNeighbouringTerrain(landPolygon,TerrainType.LAND));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(oceanPolygon,TerrainType.OCEAN));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(beachPolygon,TerrainType.BEACH));
    }

    @Test
    public void nullTestCheckForNeighbouringTerrain() throws Exception{
        //checking with null
        assertTrue(circleTerrain.checkForNeighbouringTerrain(nullTerrainPolygon,TerrainType.LAND));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(nullTerrainPolygon,TerrainType.OCEAN));
        assertFalse(circleTerrain.checkForNeighbouringTerrain(nullTerrainPolygon,TerrainType.BEACH));
    }

    @Test
    public void crosscheckCheckForNeighbouringTerrain() throws Exception{
        //crosscheck with rectangleTerrain
        RectangleTerrain rectangleTerrain = new RectangleTerrain();
        assertEquals(circleTerrain.checkForNeighbouringTerrain(landPolygon,TerrainType.BEACH),
                rectangleTerrain.checkForNeighbouringTerrain(landPolygon,TerrainType.BEACH));
        assertEquals(circleTerrain.checkForNeighbouringTerrain(oceanPolygon,TerrainType.LAND),
                rectangleTerrain.checkForNeighbouringTerrain(oceanPolygon,TerrainType.LAND));
    }
}
