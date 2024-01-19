package islandGen;

import adt.Polygon;
import adt.TerrainType;

public class VolcanicTerrainAssigner implements TerrainAssigner {
    /*
        =========================================================
        Move the bottom stuff to a biome class instead of this elevation class
        =========================================================
         */


    public void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height) {
        //Design choice: the code goes over the island and generates a volcano that goes outwards from the center of island
        double island_circle_radius = Math.min(mesh_width, mesh_height) * 0.8 / 2; //island takes ~80% of mesh
        double distanceOfCentroidToFromCenter = Math.sqrt(Math.pow((polygon.centroid().x() - mesh_width / 2), 2) + Math.pow((polygon.centroid().y() - mesh_height / 2), 2));

        if (polygon.getTileTerrain() == TerrainType.OCEAN) { return; } //Volcanos do not go over ocean

        if (distanceOfCentroidToFromCenter <= island_circle_radius * 0.1) {
            polygon.assignTileTerrain(TerrainType.VOLCANO_PEAK);
            assignPeakNeighbours(polygon);
        } else if (distanceOfCentroidToFromCenter <= island_circle_radius * 0.6) {
            polygon.assignTileTerrain(TerrainType.ROCK);
        }
        else if (distanceOfCentroidToFromCenter <= island_circle_radius * 0.8) {
            polygon.assignTileTerrain(TerrainType.FOREST);
        }
    }

    public void assignPeakNeighbours(Polygon polygon){
        for (Polygon neighbour : polygon.neighbours()){
            neighbour.assignTileTerrain(TerrainType.ROCK);  //TOP Rock
        }
    }
}

