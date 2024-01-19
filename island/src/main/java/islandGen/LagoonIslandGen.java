package islandGen;

import adt.Mesh;
import adt.Polygon;
import adt.TerrainType;

public class LagoonIslandGen implements IslandGen{
    public void islandEnricher(Mesh iMesh, String args[]){
        double mesh_width = iMesh.getWidth();
        double mesh_height = iMesh.getHeight();

        buildIslandBase(iMesh, mesh_width, mesh_height);
        buildSecondaryTerrain(iMesh, mesh_width, mesh_height);
    }

    private void buildIslandBase(Mesh iMesh, double mesh_width, double mesh_height){
        TerrainAssigner baseShapeTerrainAssigner = new CircleTerrain();
        //Build the base circle island. Assign each polygon a base terrain.
        for (Polygon polygon : iMesh) {
            baseShapeTerrainAssigner.assignPolygonTerrain(polygon, mesh_width, mesh_height);
        }
    }

    private void buildSecondaryTerrain(Mesh iMesh, double mesh_width, double mesh_height){
        TerrainAssigner secondaryTerrainAssigner = new LagoonTerrainAssigner();
        //Fill the middle of the island with lagoon.
        for (Polygon polygon : iMesh) {
            secondaryTerrainAssigner.assignPolygonTerrain(polygon, mesh_width, mesh_height);
        }

        //surround lagoon with beach
        for (Polygon polygon: iMesh){
            SurroundLagoonBeach(polygon);
        }
    }

    private void SurroundLagoonBeach(Polygon polygon){
        if (polygon.getTileTerrain() == TerrainType.LAGOON){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND){
                    neighbour.assignTileTerrain(TerrainType.BEACH);
                }
            }
        }
    }
}
